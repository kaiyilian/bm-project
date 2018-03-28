package com.bumu.arya.admin.corporation.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/10/11.
 */
@ApiModel
public class AdjustCorpUserPermCommand {

    @JsonProperty("corp_user_id")
    private String userId;

    @JsonProperty("perms")
    private List<CorpUserPermCommand> perms;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CorpUserPermCommand> getPerms() {
        return perms;
    }

    public void setPerms(List<CorpUserPermCommand> perms) {
        this.perms = perms;
    }
}
