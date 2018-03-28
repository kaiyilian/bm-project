package com.bumu.arya.admin.system.service;

import com.bumu.arya.admin.model.entity.PermissionEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Allen 2017-12-25
 **/
@Transactional
public interface SystemPermissionService {

    /**
     * 初始化系统权限
     * @param permissions
     */
    void initSystemPermissions(Collection<PermissionEntity> permissions);
}
