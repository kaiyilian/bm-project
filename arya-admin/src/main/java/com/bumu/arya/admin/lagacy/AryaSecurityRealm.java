package com.bumu.arya.admin.lagacy;

import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.admin.service.SysUserCommonService;
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
 * 只能处理当前容器的安全问题
 * Created by allen on 15/10/9.
 * @deprecated
 */
public class AryaSecurityRealm extends AuthorizingRealm {

    private Logger log = org.slf4j.LoggerFactory.getLogger(AryaSecurityRealm.class);

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysUserCommonService sysUserCommonService;

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

//		if ("admin".equals(token.getUsername()) && "admin".equals(String.valueOf(token.getPassword()))) {
//			return new SimpleAuthenticationInfo("admin", "admin", "admin");
//		}

        SysUserEntity sysUser = null;
        try {
            sysUser = sysUserService.verifySysUser(token.getUsername(), String.valueOf(token.getPassword()));
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            throw new AuthenticationException(e.getMessage());
        }

//		if (sysUser == null) {
//			throw new AuthenticationException(ErrorCode.getErrorMessage(ErrorCode.CODE_SYS_USER_NOT_EXIST));
//		}

        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(
                        sysUser.getLoginName(), // 用户名
                        token.getPassword(), // 密码
                        sysUser.getLoginName() + sysUser.getId() //
                );

        try {
            // 保存操作人的ID
            SecurityUtils.getSubject().getSession().setAttribute("user_id", sysUser.getId());
            SecurityUtils.getSubject().getSession().setAttribute("account", sysUser.getLoginName());
            SecurityUtils.getSubject().getSession().setAttribute("real_name", sysUser.getRealName());
            SecurityUtils.getSubject().getSession().setAttribute("nick_name", sysUser.getNickName());
        } catch (InvalidSessionException e) {
            log.error(e.getMessage(), e);
        }

        return simpleAuthenticationInfo;
    }

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
            return sysUserCommonService.makeAuthInfoFromDB(principalCollection);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
