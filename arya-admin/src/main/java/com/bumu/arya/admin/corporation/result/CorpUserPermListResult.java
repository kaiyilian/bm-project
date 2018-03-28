package com.bumu.arya.admin.corporation.result;

import com.bumu.bran.admin.model.entity.CorpPermissionEntity;
import com.bumu.common.result.PaginationResult;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/10/11.
 */
public class CorpUserPermListResult extends PaginationResult {

    List<CorpPermissionEntity> permissions;

    public List<CorpPermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<CorpPermissionEntity> permissions) {
        this.permissions = permissions;
    }
}
