package com.bumu.arya.admin.corporation.result;

import com.bumu.common.result.PaginationResult;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/10/9.
 */
@ApiModel
public class CorpUserListResult extends PaginationResult {

    List<CorpAdminListResult.CorpAdminResult> users;

    public List<CorpAdminListResult.CorpAdminResult> getUsers() {
        return users;
    }

    public void setUsers(List<CorpAdminListResult.CorpAdminResult> users) {
        this.users = users;
    }
}
