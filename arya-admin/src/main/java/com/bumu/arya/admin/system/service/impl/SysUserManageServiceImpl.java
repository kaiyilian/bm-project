package com.bumu.arya.admin.system.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.system.controller.command.SysUserCommand;
import com.bumu.arya.admin.system.service.SysUserManageService;
import com.bumu.arya.common.SysOpConstants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysJournalRepository;
import com.bumu.arya.response.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Allen 2018-01-23
 **/
@Service
public class SysUserManageServiceImpl implements SysUserManageService {

    Logger log = LoggerFactory.getLogger(SysUserManageServiceImpl.class);

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysJournalRepository sysJournalRepository;

    /**
     * 创建或更新系统用户
     *
     * @param command
     * @param curUser
     * @return
     */
    public HttpResponse createOrUpdateSysUser(SysUserCommand command, SysUserModel curUser) {
        log.debug(command.toString());

        boolean isCreate = StringUtils.isBlank(command.getUid());

        String loginName = command.getLoginName().trim();

        try {

            SysUserEntity entity = sysUserDao.findByLoginName(loginName);

            if (entity != null) {
                if (isCreate) {
                    // 角色名称存在不能创建
                    return new HttpResponse(ErrorCode.CODE_SYS_USER_EXIST);
                }
                else {
                    if (entity.getId().equals(command.getUid())) {
                        // 可以编辑
                    }
                    else {
                        // 该角色名称已存在，不能改名
                        return new HttpResponse(ErrorCode.CODE_SYS_USER_EXIST);
                    }
                }
            }
            else {
                if (isCreate) {
                    // 可以新建
                    entity = new SysUserEntity();
                    entity.setId(Utils.makeUUID());
                    entity.setCreateTime(System.currentTimeMillis());
                }
                else {
                    // 可以编辑
                    entity = sysUserDao.find(command.getUid());
                }
            }
            entity.setLoginName(loginName);
            entity.setRealName(command.getRealName());
            entity.setEmail(command.getEmail());
            entity.setRoleNames("");
            entity.setStatus(1);
            entity.setIsActive(1);

            // 密码特殊处理
            if (isCreate) {
                String pwd = command.getLoginPwd();
                if (StringUtils.isNotBlank(pwd)) {
                    entity.setLoginPwd(SysUtils.encryptPassword(pwd.trim()));
                }
            }
            else {
                String pwd = command.getLoginPwd();
                if (StringUtils.isNotBlank(pwd)) {
                    entity.setLoginPwd(SysUtils.encryptPassword(pwd.trim()));
                }
            }
            sysUserDao.createOrUpdate(entity);

            Map<String, Object> m = new HashMap<>();
            m.put("ID", entity.getId());
            m.put("登录名", entity.getLoginName());
            m.put("姓名", entity.getRealName());

            int opType = 0;
            if (isCreate) {
                opType = SysOpConstants.OP_TYPE_CREATE_SYS_USER;
            }
            else {
                opType = SysOpConstants.OP_TYPE_UPDATE_SYS_USER;
            }

            sysJournalRepository.success(opType, curUser.getId(), curUser.getLoginName(), curUser.getRealName(), m);

        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(
                    StringUtils.isBlank(command.getUid()) ? ErrorCode.CODE_SYS_USER_CREATE_FAIL : ErrorCode.CODE_SYS_USER_UPDATE_FAIL);
        }

        return new HttpResponse();
    }

}
