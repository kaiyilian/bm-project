package com.bumu.arya.admin.misc.result;

import com.bumu.common.result.PaginationResult;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/10/18.
 */
public class AryaUserInfoListResult extends PaginationResult {

    List<AryaUserInfoResult> users;

    public List<AryaUserInfoResult> getUsers() {
        return users;
    }

    public void setUsers(List<AryaUserInfoResult> users) {
        this.users = users;
    }
}
