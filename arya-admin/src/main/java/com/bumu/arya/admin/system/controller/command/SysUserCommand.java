package com.bumu.arya.admin.system.controller.command;

import com.bumu.arya.admin.system.controller.validate.CreateEntityValidateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Allen 2018-01-23
 **/
public class SysUserCommand {
    /**
     * 用户ID，编辑时使用
     */
    @JsonProperty("uid")
    String uid;

    @NotEmpty
    @Length(min = 4, max = 32)
    @JsonProperty("login_name")
    String loginName;

    @NotEmpty(groups = {CreateEntityValidateGroup.class})
    @Length(min = 6, max = 32)
    @JsonProperty("login_pwd")
    String loginPwd;

    @JsonProperty("real_name")
    String realName;

    @NotEmpty
    @Email
    @JsonProperty("email")
    String email;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
