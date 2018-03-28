package com.bumu.arya.admin.system.service.impl;

import com.bumu.arya.admin.model.PermissionDao;
import com.bumu.arya.admin.model.entity.PermissionEntity;
import com.bumu.arya.admin.system.service.SystemPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * @author Allen 2017-12-25
 **/
@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {

    Logger log = LoggerFactory.getLogger(SystemPermissionService.class);


    @Autowired
    PermissionDao permissionDao;

    @Override
    public void initSystemPermissions(Collection<PermissionEntity> permissions) {
        if (permissions != null) {
            log.info("开始初始化系统权限");
            for (PermissionEntity permission : permissions) {
                if (!"0".equals(permission.getId())) {
                    PermissionEntity checkEntity = permissionDao.find(permission.getId());
                    if (checkEntity == null) {
                        log.info(String.format("创建权限: %s[%s]", permission.getPermissionCode(), permission.getDesc()));

                        permission.setCreateTime(System.currentTimeMillis());
                        permissionDao.create(permission);
                    }
                    else {
                        if (!Objects.equals(checkEntity.getDesc(), permission.getDesc())
                                || !Objects.equals(checkEntity.getIsObsolete(), permission.getIsObsolete())) {
                            checkEntity.setIsObsolete(permission.getIsObsolete());
                            checkEntity.setDesc(permission.getDesc());
                            checkEntity.setUpdateTime(System.currentTimeMillis());
                            permissionDao.update(checkEntity);
                        }
                    }
                }
                // 处理子集合
                Set<PermissionEntity> subPermissions = permission.getSubPermissions();
                if (subPermissions != null && !subPermissions.isEmpty()) {
                    initSystemPermissions(subPermissions);
                }
            }
        }
    }
}
