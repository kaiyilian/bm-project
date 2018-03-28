package com.bumu.arya.admin.corporation.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 16/7/20.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateUpdateCorpAdminCommand {

    String id;

    String account;

    String password;

    @JsonProperty("nick_name")
    String nickName;

    @JsonProperty("corp_id")
    String corpId;

    String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public CreateUpdateCorpAdminCommand() {

    }
}
