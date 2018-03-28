package com.bumu.arya.admin.system;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.model.PermissionDao;
import com.bumu.arya.admin.model.RoleDao;
import com.bumu.arya.admin.model.entity.PermissionEntity;
import com.bumu.arya.admin.model.entity.RoleEntity;
import com.bumu.arya.admin.model.entity.SysUserEntity;
import com.bumu.arya.admin.system.service.SystemPermissionService;
import com.bumu.arya.admin.system.service.SystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * 初始化系统默认的用户，角色以及权限
 * Created by allen on 15/11/18.
 */
public class AryaSysRoleInitializer {

    private Logger log = LoggerFactory.getLogger(AryaSysRoleInitializer.class);

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    SystemPermissionService systemPermissionService;

    @Autowired
    SystemRoleService systemRoleService;

    /**
     * 配置的角色
     */
    private Collection<RoleEntity> roles;

    /**
     * 配置的权限
     */
    private Collection<PermissionEntity> permissions;

    @PostConstruct
    public void init() {
        systemPermissionService.initSystemPermissions(permissions);
//        if (permissions != null) {
//            log.info("开始初始化系统权限");
//            for (PermissionEntity permission : permissions) {
//                PermissionEntity checkEntity = permissionDao.find(permission.getId());
//                if (checkEntity == null) {
//                    log.info(String.format("创建权限: %s[%s]", permission.getPermissionCode(), permission.getDesc()));
//
//                    permission.setCreateTime(System.currentTimeMillis());
//                    permissionDao.create(permission);
//                }
//                else {
//                    if (!Objects.equals(checkEntity.getDesc(), permission.getDesc())
//                            || !Objects.equals(checkEntity.getIsObsolete(), permission.getIsObsolete())) {
//                        checkEntity.setIsObsolete(permission.getIsObsolete());
//                        checkEntity.setDesc(permission.getDesc());
//                        checkEntity.setUpdateTime(System.currentTimeMillis());
//                        permissionDao.update(checkEntity);
//                    }
//                }
//            }
//        }

//        systemRoleService.initSysRoles(roles); // 暂不需要这么做，因为事务的范围的原因

        if (roles != null) {
            try {
                log.info("开始初始化系统用户角色权限");
                for (RoleEntity roleDef : roles) {
                    RoleEntity checkRoleEntity = roleDao.findByRoleName(roleDef.getRoleName());
                    if (checkRoleEntity == null) {
                        log.info(String.format("创建新角色: %s[%s]", roleDef.getRoleName(), roleDef.getRoleDesc()));
//						role.setId(Utils.makeUUID());
                        roleDef.setCreateTime(System.currentTimeMillis());

                        // 创建角色预分配的用户
                        if (roleDef.getSysUsers() != null) {
                            for (SysUserEntity sysUserEntity : roleDef.getSysUsers()) {
                                sysUserEntity.setId(Utils.makeUUID());
                                sysUserEntity.setCreateTime(System.currentTimeMillis());
                                if (!StringUtils.isBlank(sysUserEntity.getLoginPwd())) {
                                    sysUserEntity.setLoginPwd(SysUtils.encryptPassword(sysUserEntity.getLoginPwd()));
                                }
                            }
                        }
                        roleDao.createOrUpdate(roleDef);
                    }
                    else {
                        log.info("尝试更新" + checkRoleEntity.getRoleName() + "的权限");
                        roleDao.updateRolePermissions(checkRoleEntity.getId(), roleDef.getSysPermissions());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }

    public Collection<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<PermissionEntity> permissions) {
        this.permissions = permissions;
    }
}
