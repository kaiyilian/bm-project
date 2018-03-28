package com.bumu.arya.admin.system.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.impl.BaseAdminService;
import com.bumu.arya.admin.model.PermissionDao;
import com.bumu.arya.admin.model.RoleDao;
import com.bumu.arya.admin.model.SysUserDao;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.model.entity.PermissionEntity;
import com.bumu.arya.admin.model.entity.RoleEntity;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.system.service.SystemRoleService;
import com.bumu.arya.common.SysOpConstants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysJournalRepository;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户角色处理
 * Created by allen on 15/11/17.
 */
@Service
public class SystemRoleServiceImpl extends BaseAdminService implements SystemRoleService {
    Logger log = LoggerFactory.getLogger(SystemRoleServiceImpl.class);

    @Autowired
    RoleDao roleDao;

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    AryaAdminConfigService adminConfigService;

    @Autowired
    SysJournalRepository sysJournalRepository;

    @Override
    public List<RoleEntity> findSysRoleList() {
        List<RoleEntity> roles = roleDao.findAll();

        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return roles;
    }

    @Override
    public List<RoleEntity> findSysRoleListWithUserPermission() {
        List<RoleEntity> roles = roleDao.findAll();

        if (roles == null || roles.isEmpty()) {
            return null;
        }
        for (RoleEntity role : roles) {
            role.getSysUsers().size();// FORCE LAZY LOAD
            role.getSysPermissions().size();// FORCE LAZY LOAD
        }
        return roles;
    }

    @Override
    public List<RoleEntity> findSysRoleListWithUserPermission(int len, int page) {
        List<RoleEntity> roles = roleDao.findAllByPagination(len, page);

        if (roles == null || roles.isEmpty()) {
            return null;
        }
        for (RoleEntity role : roles) {
            role.getSysUsers().size();// FORCE LAZY LOAD
            role.getSysPermissions().size();// FORCE LAZY LOAD
        }
        return roles;
    }

    @Override
    public List<RoleEntity> findSysRoleListWithUser() {
        List<RoleEntity> roles = roleDao.findAll();

        if (roles == null || roles.isEmpty()) {
            return null;
        }
        for (RoleEntity role : roles) {
            role.getSysUsers().size();// LAZY LOAD
        }
        return roles;
    }

    @Override
    public Set<PermissionEntity> findSysRolePermission(String roleId) {
        RoleEntity roleEntity = roleDao.find(roleId);
        Set<PermissionEntity> sysPermissions = roleEntity.getSysPermissions();
        if (sysPermissions == null || sysPermissions.isEmpty()) {
            return null;
        }
        return sysPermissions;
    }

    @Override
    public Set<SysUserEntity> findSysRoleUser(String roleId) {
        RoleEntity roleEntity = roleDao.find(roleId);
        Set<SysUserEntity> sysUsers = roleEntity.getSysUsers();
        if (sysUsers == null || sysUsers.isEmpty()) {
            return null;
        }
        return sysUsers;
    }

//    @Override
//    public void initSysRoles(Collection<RoleEntity> roles) {
//        if (roles != null) {
//            try {
//                log.info("开始初始化系统用户角色权限");
//                for (RoleEntity roleDef : roles) {
//                    RoleEntity checkRoleEntity = roleDao.findByRoleName(roleDef.getRoleName());
//                    if (checkRoleEntity == null) {
//                        log.info(String.format("创建新角色: %s[%s]", roleDef.getRoleName(), roleDef.getRoleDesc()));
////						role.setId(Utils.makeUUID());
//                        roleDef.setCreateTime(System.currentTimeMillis());
//
//                        // 创建角色预分配的用户
//                        if (roleDef.getSysUsers() != null) {
//                            for (SysUserEntity sysUserEntity : roleDef.getSysUsers()) {
//                                sysUserEntity.setId(Utils.makeUUID());
//                                sysUserEntity.setCreateTime(System.currentTimeMillis());
//                                if (!StringUtils.isBlank(sysUserEntity.getLoginPwd())) {
//                                    sysUserEntity.setLoginPwd(SysUtils.encryptPassword(sysUserEntity.getLoginPwd()));
//                                }
//                            }
//                        }
//                        roleDao.createOrUpdate(roleDef);
//                    }
//                    else {
//                        log.info("尝试更新" + checkRoleEntity.getRoleName() + "的权限");
////                        roleDao.updateRolePermissions(checkRoleEntity.getId(), roleDef.getSysPermissions());
//                        roleDao.updateRolePermissions(checkRoleEntity, roleDef.getSysPermissions());
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void addSysUserToRole(String roleId, String sysUserId) throws AryaServiceException {

        if (StringUtils.isAnyBlank(roleId, sysUserId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        RoleEntity roleEntity = roleDao.find(roleId);
        if (roleEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_NOT_EXIST);
        }

        SysUserEntity sysUserEntity = sysUserDao.find(sysUserId);

        if (roleEntity.getSysUsers().contains(sysUserEntity)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_ALREADY_HAS_USER);
        }

        if (roleEntity.getSysUsers() == null) {
            roleEntity.setSysUsers(new HashSet<>());
        }

        roleEntity.getSysUsers().add(sysUserEntity);

        // 更新系统用户中冗余的角色信息
        if (!sysUserEntity.getRoleNames().contains(roleEntity.getRoleDesc())) {
            if (StringUtils.isAnyBlank(sysUserEntity.getRoleNames())) {
                sysUserEntity.setRoleNames(roleEntity.getRoleDesc());
            }
            else {
                sysUserEntity.setRoleNames(sysUserEntity.getRoleNames() + "," + roleEntity.getRoleDesc());
            }
        }
        roleDao.update(roleEntity);

        logRoleOps(SysOpConstants.OP_TYPE_ASSIGN_SYS_USER_ROLE, roleEntity, sysUserEntity, null, null);
    }

    @Override
    public void removeSysUserFromRole(String roleId, String sysUserId) throws AryaServiceException {
        if (StringUtils.isAnyBlank(roleId, sysUserId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        RoleEntity roleEntity = roleDao.find(roleId);
        if (roleEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_NOT_EXIST);
        }

        SysUserEntity sysUser = sysUserDao.find(sysUserId);
        if ("admin".equals(sysUser.getLoginName()) && "admin".equals(roleEntity.getRoleName())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_USER_ADMIN_ROLE);
        }

        StringBuffer buf = new StringBuffer();
        for (SysUserEntity sysUserEntity : roleEntity.getSysUsers()) {
            if (sysUserEntity.getId().equals(sysUserId)) {
                roleEntity.getSysUsers().remove(sysUserEntity);
                roleDao.update(roleEntity);
                buf.append(sysUserEntity.getLoginName()).append(", ");
                break;
            }
        }

        logRoleOps(SysOpConstants.OP_TYPE_UNASSIGN_SYS_USER_ROLE, roleEntity, null, null, buf.toString());
    }

    public void addSysPermissionToRole(String roleId, String permId) throws AryaServiceException {

        if (StringUtils.isAnyBlank(roleId, permId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        RoleEntity roleEntity = roleDao.find(roleId);
        if (roleEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_NOT_EXIST);
        }

        PermissionEntity permissionEntity = permissionDao.find(permId);
        if (roleEntity.getSysPermissions().contains(permissionEntity)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_ALREADY_HAS_PERMISSION);
        }

        if (roleEntity.getSysPermissions() == null) {
            roleEntity.setSysPermissions(new HashSet<>());
        }

        roleEntity.getSysPermissions().add(permissionEntity);

        roleDao.update(roleEntity);

        logRoleOps(SysOpConstants.OP_TYPE_ASSIGN_ROLE_PERMISSION, roleEntity, null, permissionEntity, null);
    }

    public void removePermissionFromRole(String roleId, String permId) throws AryaServiceException {

        if (StringUtils.isAnyBlank(roleId, permId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        RoleEntity roleEntity = roleDao.find(roleId);
        if (roleEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_NOT_EXIST);
        }

        StringBuffer buf = new StringBuffer();
        for (PermissionEntity permEntity : roleEntity.getSysPermissions()) {
            if (permEntity.getId().equals(permId)) {
                roleEntity.getSysPermissions().remove(permEntity);
                roleDao.update(roleEntity);
                buf.append(permEntity.getPermissionCode()).append(permEntity.getDesc()).append(", ");
                break;
            }
        }
        logRoleOps(SysOpConstants.OP_TYPE_UNASSIGN_ROLE_PERMISSION, roleEntity, null, null, buf.toString());
    }

    @Override
    public boolean isSalesmanRole(String userId) throws AryaServiceException {
        SysUserEntity sysUserEntity = sysUserDao.findSysUserById(userId);
        if (sysUserEntity != null) {
            Set<RoleEntity> roleEntities = sysUserEntity.getSysRoles();
            for (RoleEntity roleEntity : roleEntities) {
                if (roleEntity.getId().equals(adminConfigService.getSalesmanRoleId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isSalesmanRole(SysUserEntity sysUserEntity) throws AryaServiceException {
        Set<RoleEntity> roleEntities = sysUserEntity.getSysRoles();
        for (RoleEntity roleEntity : roleEntities) {
            if (roleEntity.getId().equals(adminConfigService.getSalesmanRoleId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void createOrUpdateRole(String roleId, String roleName, String roleDesc) throws AryaServiceException {
        try {
            boolean isCreate = StringUtils.isBlank(roleId);
            RoleEntity entity = null;
            entity = roleDao.findByRoleName(roleName);

            if (entity != null) {
                if (isCreate) {
                    // 角色名称存在不能创建
                    throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_EXIST);
                }
                else {
                    if (entity.getId().equals(roleId)) {
                        // 可以编辑
                    }
                    else {
                        // 该角色名称已存在，不能改名
                        throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_EXIST);
                    }
                }
            }
            else {
                if (isCreate) {
                    // 可以新建
                    entity = new RoleEntity();
                    entity.setId(Utils.makeUUID());
                    entity.setCreateTime(System.currentTimeMillis());
                }
                else {
                    // 可以编辑
                    entity = roleDao.find(roleId);
                }
            }
            entity.setRoleName(roleName);
            entity.setRoleDesc(roleDesc);

            roleDao.createOrUpdate(entity);

            logRoleOps(SysOpConstants.OP_TYPE_CREATE_ROLE, entity, null, null, null);

        } catch (Exception e) {
            throw new AryaServiceException(
                    StringUtils.isBlank(roleId) ? ErrorCode.CODE_SYS_ROLE_CREATE_FAIL : ErrorCode.CODE_SYS_ROLE_UPDATE_FAIL,
                    "保存角色失败");
        }
    }

    @Override
    public void deleteRoleSafely(String roleId) {
        RoleEntity roleEntity = roleDao.find(roleId);

        // 管理员无法删除
        if (roleEntity == null || "admin".equals(roleEntity.getRoleName())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_DELETE_FAIL);
        }

        // 存在用户无法删除
        if (roleEntity.getSysUsers() != null && roleEntity.getSysUsers().size() > 0) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_DELETE_FAIL, "存在属于该角色的用户，无法删除");
        }

        if (roleEntity.getSysPermissions() != null && roleEntity.getSysPermissions().size() > 0) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_DELETE_FAIL, "存在权限绑定，无法删除，先解除权限绑定");
        }

        try {
            roleDao.delete(roleId);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_SYS_ROLE_DELETE_FAIL);
        }
        logRoleOps(SysOpConstants.OP_TYPE_DELETE_ROLE, roleEntity, null, null, null);
    }

    @Override
    public String logRoleOps(int opType, RoleEntity roleEntity, SysUserEntity userEntity, PermissionEntity permissionEntity, String msg) {
        SysUserModel curSysUser = super.getCurrentSysUser();
        Map<String, Object> m = new HashMap();

        if (roleEntity != null) {
            m.put("角色ID", roleEntity.getId());
            m.put("角色", roleEntity.getRoleName());
        }
        if (userEntity != null) {
            m.put("用户ID", userEntity.getId());
            m.put("登录名", userEntity.getLoginName());
            m.put("姓名", userEntity.getRealName());
        }
        if (permissionEntity != null) {
            m.put("权限ID", permissionEntity.getId());
            m.put("权限代码", permissionEntity.getPermissionCode());
            m.put("权限描述", permissionEntity.getDesc());
        }
        if (StringUtils.isNotBlank(msg)) {
            m.put("msg", msg);
        }

        return sysJournalRepository.success(opType, curSysUser.getId(), curSysUser.getLoginName(), curSysUser.getRealName(), m);
    }
}
