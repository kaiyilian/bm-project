package com.bumu.arya.admin.corporation.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 添加或删除企业用户和权限的关联
 * Created by allen on 16/5/13.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CorpPermissionToUserCommand {

    @JsonProperty("corp_user_id")
    private String corpUserId;

    @JsonProperty("corp_perm_id")
    private String corpPermissionId;

    public String getCorpPermissionId() {
        return corpPermissionId;
    }

    public void setCorpPermissionId(String corpPermissionId) {
        this.corpPermissionId = corpPermissionId;
    }

    public String getCorpUserId() {
        return corpUserId;
    }

    public void setCorpUserId(String corpUserId) {
        this.corpUserId = corpUserId;
    }
}
