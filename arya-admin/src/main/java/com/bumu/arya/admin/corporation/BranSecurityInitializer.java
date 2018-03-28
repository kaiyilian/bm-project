package com.bumu.arya.admin.corporation;

import com.bumu.bran.admin.model.CorpPermissionDao;
import com.bumu.bran.admin.model.entity.CorpPermissionEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * 初始化企业用户，角色以及权限
 * Created by allen on 15/11/18.
 */
public class BranSecurityInitializer {
    private Logger log = LoggerFactory.getLogger(BranSecurityInitializer.class);

    @Autowired
    CorpPermissionDao corpPermissionDao;
    /**
     * 配置的权限
     */
    private Collection<CorpPermissionEntity> permissions;

    @PostConstruct
    @Transactional
    public void init() {
        log.info("初始化企业管理平台权限");
//        if (permissions == null || permissions.isEmpty()) {
//            log.warn("没有找到企业权限初始化数据");
//        }
//        else {
//            for (CorpPermissionEntity permission : permissions) {
//                CorpPermissionEntity checkEntity = corpPermissionDao.find(permission.getId());
//                if (checkEntity == null) {
//                    log.info(String.format("Create corporation permission: %s[%s] - %s", permission.getPermissionCode(), permission.getDesc(), permission.getIsDefault()));
//                    permission.setCreateTime(System.currentTimeMillis());
//                    corpPermissionDao.create(permission);
//                }
//                else {
//                    log.info(String.format("Update corporation permission: %s[%s] - %s", permission.getPermissionCode(), permission.getDesc(), permission.getIsDefault()));
//                    checkEntity.setIsDefault(permission.getIsDefault());
//                    checkEntity.setDesc(permission.getDesc());
//                    checkEntity.setIsNoAssign(permission.getIsNoAssign());
//                    checkEntity.setPermissionCode(permission.getPermissionCode());
//                    corpPermissionDao.update(checkEntity);
//                }
//            }
//        }
        //initPermissions(permissions);
    }

    private void initPermissions(Collection<CorpPermissionEntity> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return;
        }
        for (CorpPermissionEntity permDef : permissions) {
            CorpPermissionEntity checkEntity = corpPermissionDao.find(permDef.getId());
            if (checkEntity == null) {
                log.info(String.format("Create corporation permission: %s[%s] - %s", permDef.getPermissionCode(), permDef.getDesc(), permDef.getPermissionCode()));
                permDef.setCreateTime(System.currentTimeMillis());
                corpPermissionDao.create(permDef);
            }
            else {
                log.info(String.format("Update corporation permission: %s[%s] - %s", permDef.getPermissionCode(), permDef.getDesc(), permDef.getPermissionCode()));
                checkEntity.setPermissionCode(permDef.getPermissionCode()); // code也要强制更新
                checkEntity.setIsDefault(permDef.getIsDefault());
                if (StringUtils.isBlank(permDef.getPermissionName())) {
                    checkEntity.setPermissionName(permDef.getDesc()); // migration workaround
                }
                else {
                    checkEntity.setPermissionName(permDef.getPermissionName());
                }
                checkEntity.setDesc(permDef.getDesc());
                checkEntity.setIsNoAssign(permDef.getIsNoAssign());
                checkEntity.setPermissionCode(permDef.getPermissionCode());
                checkEntity.setIsDeprecated(permDef.getIsDeprecated());
                checkEntity.setUpdateTime(System.currentTimeMillis());
                corpPermissionDao.update(checkEntity);
            }
            // 处理子集
            initPermissions(permDef.getCorpPermissions());
        }
    }

    public Collection<CorpPermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<CorpPermissionEntity> permissions) {
        this.permissions = permissions;
    }
}
