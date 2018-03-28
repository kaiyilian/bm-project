package com.bumu.arya.salary.rbac;

import com.bumu.arya.admin.model.PermissionDao;
import com.bumu.arya.admin.model.entity.PermissionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * 初始化系统默认的权限
 * Created by allen on 15/11/18.
 */
public class SysPermissionInitializer {

    private Logger log = LoggerFactory.getLogger(SysPermissionInitializer.class);


    @Autowired
    PermissionDao permissionDao;


    /**
     * 配置的权限
     */
    private Collection<PermissionEntity> permissions;

    @PostConstruct
    public void init() {
        if (permissions != null) {
            log.info("开始初始化系统权限");
            for (PermissionEntity permission : permissions) {
                PermissionEntity checkEntity = permissionDao.find(permission.getId());
                if (checkEntity == null) {
                    log.info(String.format("创建权限: %s[%s]", permission.getPermissionCode(), permission.getDesc()));

                    permission.setCreateTime(System.currentTimeMillis());
                    permissionDao.create(permission);
                }
            }
        }

    }

    public Collection<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<PermissionEntity> permissions) {
        this.permissions = permissions;
    }
}
