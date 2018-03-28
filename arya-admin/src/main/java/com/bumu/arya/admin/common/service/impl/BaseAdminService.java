package com.bumu.arya.admin.common.service.impl;

import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.common.service.impl.BaseBumuService;
import com.bumu.exception.AryaServiceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * 管理服务基类，所有 admin 的服务都要继承此类
 * @author allen
 */
public abstract class BaseAdminService extends BaseBumuService {

    /**
     * 获取当前登录用户的信息
     * @return
     * @throws AryaServiceException
     */
    public SysUserModel getCurrentSysUser() throws AryaServiceException {
        try {
            Session session = SecurityUtils.getSubject().getSession();
            SysUserModel model = new SysUserModel();
            model.setId(session.getAttribute("user_id").toString());
            model.setLoginName(session.getAttribute("account").toString());
            model.setRealName(session.getAttribute("real_name") != null ? session.getAttribute("real_name").toString() : null);
            model.setNickName(session.getAttribute("nick_name") != null ? session.getAttribute("nick_name").toString() : null);
            return model;
        } catch (Exception e) {
            elog.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_GET_CURRENT_SYSUSER_FAILED);
        }
    }
}
