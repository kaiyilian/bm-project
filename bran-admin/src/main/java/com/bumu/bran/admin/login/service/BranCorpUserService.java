package com.bumu.bran.admin.login.service;

import com.bumu.bran.admin.corporation.result.CorpUserInfoResult;
import com.bumu.bran.admin.login.controller.command.ChangePasswordCommand;
import com.bumu.bran.admin.login.controller.command.SigninCommand;
import com.bumu.bran.admin.login.result.BranCorpUserLoginResult;
import com.bumu.admin.model.entity.BranCorpUserEntity;
import com.bumu.exception.AryaServiceException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by allen on 15/10/9.
 */
@Transactional
public interface BranCorpUserService {

	/**
	 * 登录
	 *
	 * @param command
	 * @return
	 * @throws AryaServiceException
	 */
	BranCorpUserLoginResult login(SigninCommand command) throws Exception;

	/**
	 * 验证系统用户身份
	 *
	 * @param name
	 * @param pwd
	 * @return
	 * @throws AryaServiceException
	 */
	BranCorpUserEntity verifySysUser(String name, String pwd) throws AryaServiceException;


    void saveUserPermissionsToShiroSession(String loginName) throws AryaServiceException;

    List<String> getUserCorpPermissions(String loginName) throws AryaServiceException;

	/**
	 * 获取用户的授权信息
	 *
	 * @param principalCollection
	 * @return
	 */
	SimpleAuthorizationInfo makeAuthInfoFromDB(PrincipalCollection principalCollection);

	/**
	 * @return
	 * @deprecated
	 */
	List<BranCorpUserEntity> getSysUserWithRoleAndPermission();

	/**
	 * 根据账号查询企业用户Id
	 *
	 * @param account
	 * @return
	 */
	String getCorpUserIdByAccountThrow(String account) throws AryaServiceException;

	/**
	 * 根据账号查询企业用户Id
	 *
	 * @param account
	 * @return
	 */
	String getCorpUserIdByAccount(String account);

	/**
	 * 清空用户尝试登录次数
	 *
	 * @param branCorpUserId
	 */
	void clearCorpUserTryLoginTimes(String branCorpUserId);

	/**
	 * 递增用户尝试登录的次数
	 *
	 * @param branCorpUserId
	 * @return
	 */
	int addCorpUserTryLoginTimes(String branCorpUserId);

	/**
	 * 获取企业用户尝试登录的次数
	 *
	 * @param branCorpUserId
	 * @return
	 */
	int getCorpUserTryLoginTimes(String branCorpUserId);

	/**
	 * 获取企业用户个人信息
	 *
	 * @param corpUserId
	 * @return
	 */
	CorpUserInfoResult getCorpUserInfo(String corpUserId, Long lastLoginTime, String lastLoginIp);

	/**
	 * 先判断用户是否需要验证码，再返回URL
	 *
	 * @param account
	 * @param browserSessionId
	 * @return
	 */
	String getUserCaptchaURL(String account, String browserSessionId) throws Exception;

	/**
	 * 获取浏览器的验证码URL
	 *
	 * @param browserSessionId
	 * @return
	 */
	String getCaptchaURL(String browserSessionId);

	/**
	 * 获取验证码文字内容
	 *
	 * @param captchaRedisKey
	 * @return
	 */
	String getCaptchaText(String captchaRedisKey);

	/**
	 * 校验验证码
	 *
	 * @param browserSessionId
	 * @param captcha
	 * @return
	 */
	Boolean checkCaptcha(String browserSessionId, String captcha);

	/**
	 * 修改密码
	 *
	 * @param command
	 * @throws AryaServiceException
	 */
	void changePassword(ChangePasswordCommand command) throws Exception;
}
