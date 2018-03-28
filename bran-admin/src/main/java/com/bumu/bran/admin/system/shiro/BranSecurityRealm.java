package com.bumu.bran.admin.system.shiro;

import com.bumu.bran.admin.login.service.BranCorpUserService;
import com.bumu.admin.model.dao.BranCorpUserDao;
import com.bumu.admin.model.entity.BranCorpUserEntity;
import com.bumu.exception.AryaServiceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统安全域，处理系统用户认证和授权
 * Created by allen on 15/10/9.
 */
public class BranSecurityRealm extends AuthorizingRealm {

	@Autowired
	BranCorpUserDao sysUserDao;
	@Autowired
	BranCorpUserService branCorpUserService;
	private Logger log = org.slf4j.LoggerFactory.getLogger(BranSecurityRealm.class);

	/**
	 * Shiro 获取授权信息
	 *
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		log.info("验证用户" + token.getUsername());

		BranCorpUserEntity sysUser = null;
		try {
			sysUser = branCorpUserService.verifySysUser(token.getUsername(), String.valueOf(token.getPassword()));
		} catch (AryaServiceException e) {
			e.printStackTrace();
			throw new AuthenticationException(e.getMessage());
		}

		SimpleAuthenticationInfo simpleAuthenticationInfo =
				new SimpleAuthenticationInfo(
						sysUser.getLoginName(), // 用户名
						token.getPassword(), // 密码
						sysUser.getLoginName() + sysUser.getId() //
				);

		try {
			// 保存操作人的ID
			SecurityUtils.getSubject().getSession().setAttribute("user_id", sysUser.getId());
			SecurityUtils.getSubject().getSession().setAttribute("bran_corp_id", sysUser.getBranCorpId());
			SecurityUtils.getSubject().getSession().setAttribute("last_login_time", sysUser.getLastLoginTime());
			SecurityUtils.getSubject().getSession().setAttribute("last_login_ip", sysUser.getLastLoginIp());

			branCorpUserService.saveUserPermissionsToShiroSession(sysUser.getLoginName());

        } catch (InvalidSessionException e) {
			e.printStackTrace();
		}
		try {
			sysUser.setLastLoginTime(System.currentTimeMillis());
			sysUser.setLastLoginIp(token.getHost());
			sysUserDao.update(sysUser);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改管理平台用户的登录时间失败！");
		}

		return simpleAuthenticationInfo;
	}

//	private void saveUserPermissionsToShiroSession(String loginName) {
//        List<String> userCorpPermissions = branCorpUserService.getUserCorpPermissions(loginName);
//        log.info(String.format("用户 %s 的权限：%s", loginName, StringUtils.join(userCorpPermissions, ',')));
//        SecurityUtils.getSubject().getSession().setAttribute("user_permissions", StringUtils.join(userCorpPermissions, ','));
//    }

	/**
	 * Shiro获取授权信息
	 *
	 * @param principalCollection
	 * @return
	 */
	@Override
	@Transactional
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		try {
			return branCorpUserService.makeAuthInfoFromDB(principalCollection);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
