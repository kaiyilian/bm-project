package com.bumu.bran.admin.login.service.impl;

import com.bumu.SysUtils;
import com.bumu.admin.constant.CorpConstants;
import com.bumu.admin.model.dao.BranCorpUserDao;
import com.bumu.admin.model.entity.BranCorpUserEntity;
import com.bumu.admin.service.BranCorpUserCommonService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.corporation.result.CorpUserInfoResult;
import com.bumu.bran.admin.login.controller.command.ChangePasswordCommand;
import com.bumu.bran.admin.login.controller.command.SigninCommand;
import com.bumu.bran.admin.login.result.BranCorpUserLoginResult;
import com.bumu.bran.admin.login.service.BranCorpUserService;
import com.bumu.bran.admin.model.entity.CorpPermissionEntity;
import com.bumu.bran.admin.model.entity.CorpRoleEntity;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.common.model.UserTryLoginTimesModel;
import com.bumu.common.service.CaptchaService;
import com.bumu.common.service.RedisService;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.common.util.ValidateUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.ExceptionModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 企业用户
 */
@Service
public class BranCorpUserServiceImpl implements BranCorpUserService {

	static final String BRAN_CORP_USER_TRY_LOGIN_TIME_STR = "login_try_";
	static final String BROWSER_CAPTCHA = "captcha_";

	@Autowired
	BranCorpUserDao cropUserDao;
	@Autowired
	BranCorporationDao branCorporationDao;
	@Autowired
	BranAdminConfigService branAdminConfigService;
	@Autowired
	BranCorpUserCommonService branCorpUserCommonService;
	@Autowired
	CaptchaService captchaService;
	@Autowired
	Producer captchaProducer;

	@Autowired
	RedisService redisService;
	private Logger log = org.slf4j.LoggerFactory.getLogger(BranCorpUserServiceImpl.class);


	@Override
	public BranCorpUserLoginResult login(SigninCommand command) throws Exception {
		// 账号 hibernate validation
		if (StringUtils.isAnyBlank(command.getAccount())) {
			throw new AryaServiceException(ErrorCode.CODE_USER_ACCOUNT_EMPTY);
		}
		// 密码 hibernate validation
		if (StringUtils.isAnyBlank(command.getPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_USER_PASSWORD_EMPTY);
		}
		// 根据用户名查询 admin12
		String userId = getCorpUserIdByAccountThrow(command.getAccount());
		// 尝试登陆次数
		int tryTimes = getCorpUserTryLoginTimes(userId);
		BranCorpUserLoginResult result = new BranCorpUserLoginResult();
		// 如果登陆超过10次
		if (tryTimes >= 10) {
			result.setCode(CorpConstants.BRAN_CORP_USER_LOGIN_FROZEN);
			return result;
			// 如果登陆超过3次 有验证码
		} else if (tryTimes >= 3 && StringUtils.isAnyBlank(command.getCaptcha())) {
			result.setCode(CorpConstants.BRAN_CORP_USER_LOGIN_NEED_CAPTCHAT);
			// 返回验证码url
			result.setCaptchaUrl(getCaptchaURL(command.getBrowserSessionId()));
			return result;
		}
		// tryTimes  == 3 返回验证码
		// 验证码判断
		// 超过3次 没有验证码
		if (tryTimes >= 3) {
			if (StringUtils.isAnyBlank(command.getCaptcha())) {
				throw new AryaServiceException(ErrorCode.CODE_USER_CAPTCHA_EMPTY);
			}
			//验证验证码
			Boolean checkCaptchaResult = checkCaptcha(command.getBrowserSessionId(), command.getCaptcha());
			if (checkCaptchaResult == null) {
				throw new AryaServiceException(ErrorCode.CODE_USER_CAPTCHA_EXPIRED);
			}
			if (!checkCaptchaResult) {
				//验证码错误
				throw new AryaServiceException(ErrorCode.CODE_USER_CAPTCHA_WRONG);
			}
		}
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 保存sessionId
			currentUser.login(new UsernamePasswordToken(command.getAccount(), command.getPassword(), command.getIp()));
			//
			if (tryTimes > 0) {
				clearCorpUserTryLoginTimes(userId);
			}
		} catch (AuthenticationException e) {
			log.info("【登录】用户" + userId + "登录失败。");
			addCorpUserTryLoginTimes(userId);
			e.printStackTrace();
			throw new AryaServiceException(ErrorCode.CODE_SIGNIN_FAIL);
		}
		return result;
	}

	@Override
	public BranCorpUserEntity verifySysUser(String name, String pwd) throws AryaServiceException {

		BranCorpUserEntity corpUser = null;
		try {
			corpUser = cropUserDao.findByUniqueParam(BranCorpUserEntity.COL_NAME_LOGIN_NAME, name);
			if (corpUser == null) {
				throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_EXIST);
			} else if (!corpUser.getLoginPwd().equals(SysUtils.encryptPassword(pwd))) {
				throw new AryaServiceException(ErrorCode.CODE_SIGNIN_FAIL);
			}
		} catch (DataAccessException e) {
			throw new AryaServiceException(ErrorCode.CODE_SIGNIN_FAIL);
		}
		return corpUser;
	}

	@Override
    public void saveUserPermissionsToShiroSession(String loginName) {
        List<String> userCorpPermissions = getUserCorpPermissions(loginName);
        log.info(String.format("用户 %s 的权限：%s", loginName, StringUtils.join(userCorpPermissions, ',')));
        SecurityUtils.getSubject().getSession().setAttribute("user_permissions", StringUtils.join(userCorpPermissions, ','));
    }

    @Override
    public List<String> getUserCorpPermissions(String loginName) {
	    log.info(String.format("查询用户 %s 的权限", loginName));
        BranCorpUserEntity corpUserEntity = cropUserDao.findByUniqueParam(BranCorpUserEntity.COL_NAME_LOGIN_NAME, loginName);
        if (corpUserEntity == null) {
            log.debug("没有查询到企业用户");
            return null;
        }

        List<String> ret = new ArrayList<>();
        for (CorpRoleEntity roleEntity : corpUserEntity.getCorpRoles()) {
            log.info(String.format("  角色：%s, %d个权限", roleEntity.getRoleName(), roleEntity.getCorpPermissions().size()));
            for (CorpPermissionEntity permissionEntity : roleEntity.getCorpPermissions()) {
                ret.add(permissionEntity.getPermissionCode());
            }
        }
        return ret;
    }

    @Override
	public SimpleAuthorizationInfo makeAuthInfoFromDB(PrincipalCollection principalCollection) {
		log.info("授权信息");
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		for (Object principal : principalCollection) {

			String loginName = (String) principal;

			log.info("  用户：" + loginName);

			BranCorpUserEntity corpUserEntity = cropUserDao.findByUniqueParam(BranCorpUserEntity.COL_NAME_LOGIN_NAME, loginName);
			if (corpUserEntity == null) {
				continue;
			}

			for (CorpRoleEntity roleEntity : corpUserEntity.getCorpRoles()) {
				log.info(String.format("  角色：%s, %d个权限", roleEntity.getRoleName(), roleEntity.getCorpPermissions().size()));
				for (CorpPermissionEntity permissionEntity : roleEntity.getCorpPermissions()) {
//                    log.debug("    " + permissionEntity.getPermissionCode());
					simpleAuthorizationInfo.addStringPermission(permissionEntity.getPermissionCode());
				}
			}
		}

		if (simpleAuthorizationInfo.getStringPermissions() != null) {
			log.info("    共有" + simpleAuthorizationInfo.getStringPermissions().size() + "个企业权限");
		}


		return simpleAuthorizationInfo;
	}

	@Override
	public List<BranCorpUserEntity> getSysUserWithRoleAndPermission() {
		throw new NotImplementedException("");
	}

	@Override
	public String getCorpUserIdByAccountThrow(String account) throws AryaServiceException {
		BranCorpUserEntity userEntity = cropUserDao.findBranCorpUserByAccount(account);
		if (userEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_EXIST);
		}
		return userEntity.getId();
	}

	@Override
	public String getCorpUserIdByAccount(String account) {
		BranCorpUserEntity userEntity = cropUserDao.findBranCorpUserByAccount(account);
		if (userEntity == null) {
			return null;
		}
		return userEntity.getId();
	}

	@Override
	public void clearCorpUserTryLoginTimes(String branCorpUserId) {
		branCorpUserCommonService.clearCorpUserTryLoginTimes(branCorpUserId);
	}

	@Override
	public int addCorpUserTryLoginTimes(String branCorpUserId) {
        Jedis jedis = redisService.getJedis();
        String id = BRAN_CORP_USER_TRY_LOGIN_TIME_STR + branCorpUserId;
		byte[] bytes = jedis.get(id.getBytes());
		UserTryLoginTimesModel timesModel = new UserTryLoginTimesModel();
		if (bytes == null) {
			timesModel.setUserId(branCorpUserId);
			timesModel.setTimes(1);
		} else {
			try {
				timesModel = new ObjectMapper().readValue(new String(bytes), UserTryLoginTimesModel.class);
				timesModel.setTimes(timesModel.getTimes() + 1);
			} catch (IOException e) {
				log.error("【企业用户登录】将redis的JSON转对象失败！");
				e.printStackTrace();
			}
		}
		try {
			try {
				//到23:59:59失效
                jedis.setex(id.getBytes(), (int) ((DateTimeUtils.getOneDayLastTime(System.currentTimeMillis()) - System.currentTimeMillis()) / 1000), new ObjectMapper().writeValueAsString(timesModel).getBytes());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        jedis.close();
		return timesModel.getTimes();
	}

	@Override
	public int getCorpUserTryLoginTimes(String branCorpUserId) {
		return branCorpUserCommonService.getCorpUserTryLoginTimes(branCorpUserId);
	}

	@Override
	public CorpUserInfoResult getCorpUserInfo(String corpUserId, Long lastLoginTime, String lastLoginIp) {
		BranCorpUserEntity corpUserEntity = cropUserDao.find(corpUserId);
		BranCorporationEntity corporationEntity = branCorporationDao.findCorpByIdThrow(corpUserEntity.getBranCorpId());
		CorpUserInfoResult result = new CorpUserInfoResult();
		result.setId(corpUserEntity.getId());
		if (!StringUtils.isAnyBlank(corpUserEntity.getNickName())) {
			result.setName(corpUserEntity.getNickName());
		} else {
			result.setName(corpUserEntity.getLoginName());
		}
		result.setCorpName(corporationEntity.getCorpName());
		result.setLastLoginTime(lastLoginTime);
		result.setLastLoginIp(lastLoginIp);
		return result;
	}

	@Override
	public String getUserCaptchaURL(String account, String browserSessionId) throws Exception {
		String userId = getCorpUserIdByAccount(account);
		if (userId == null) {
			return null;
		}
		int tryTimes = getCorpUserTryLoginTimes(userId);
		if (tryTimes >= 3 && tryTimes < 10) {
			return getCaptchaURL(browserSessionId);
		}
		return null;
	}

	@Override
	public String getCaptchaURL(String browserSessionId) {
		return captchaService.getCaptchaURL(captchaProducer.createText(), browserSessionId, "captcha/image?cap_id=", BROWSER_CAPTCHA);
	}

	@Override
	public String getCaptchaText(String captchaRedisKey) {
		return captchaService.getCaptchaText(captchaRedisKey);
	}

	@Override
	public Boolean checkCaptcha(String browserSessionId, String captcha) {
		return captchaService.checkCaptcha(browserSessionId, captcha, BROWSER_CAPTCHA);
	}

	@Override
	public void changePassword(ChangePasswordCommand command) throws Exception {
		ExceptionModel exceptionModel = new ExceptionModel();
		if (StringUtils.isAnyBlank(command.getId())) {
			throw new AryaServiceException(ErrorCode.CODE_USER_ACCOUNT_EMPTY);
		}
		if (StringUtils.isAnyBlank(command.getOldPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_USER_PASSWORD_EMPTY);
		}
		if (StringUtils.isAnyBlank(command.getNewPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_USER_PASSWORD_EMPTY);
		}

		if (command.getNewPassword().length() > 32 || command.getOldPassword().length() > 32) {
			exceptionModel.setError(ErrorCode.CODE_SYS_ERR);
			exceptionModel.setMsg("密码超过32位");
			throw new AryaServiceException(exceptionModel);
		}

		BranCorpUserEntity corpUserEntity = cropUserDao.find(command.getId());
		if (corpUserEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_EXIST);
		}
		// 判断密码复杂度
		if (!ValidateUtils.vefityPassword(command.getNewPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_VALID_LOGIN_PWD_FORMAT);
		}
		// 比对旧密码
		if (!corpUserEntity.getLoginPwd().equals(SysUtils.encryptPassword(command.getOldPassword()))) {
			exceptionModel.setError(ErrorCode.CODE_SYS_ERR);
			exceptionModel.setMsg("旧密码不正确,请重新输入");
			throw new AryaServiceException(exceptionModel);
		}

		// 新密码与旧密码相同
		if (command.getOldPassword().equals(command.getNewPassword())) {
			exceptionModel.setError(ErrorCode.CODE_SYS_ERR);
			exceptionModel.setMsg("新密码与旧密码相同,请重新输入");
			throw new AryaServiceException(exceptionModel);
		}

		corpUserEntity.setLoginPwd(SysUtils.encryptPassword(command.getNewPassword()));
		cropUserDao.update(corpUserEntity);
	}
}
