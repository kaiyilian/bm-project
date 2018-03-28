package com.bumu.arya.admin.system.service;

import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.system.controller.command.SysUserCommand;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;

/**
 * 系统用户管理服务
 * @author Allen 2018-01-23
 **/
public interface SysUserManageService {

    /**
     * 创建或更新系统用户
     * @param command
     * @param curUser
     * @return
     * @throws AryaServiceException
     */
    HttpResponse createOrUpdateSysUser(SysUserCommand command, SysUserModel curUser) throws AryaServiceException;
}
