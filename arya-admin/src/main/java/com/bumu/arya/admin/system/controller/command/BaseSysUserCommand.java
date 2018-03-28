package com.bumu.arya.admin.system.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Allen 2018-01-23
 **/
public abstract class BaseSysUserCommand {
    /**
     * 用户ID，编辑时使用
     */
    @JsonProperty("uid")
    String uid;
    @JsonProperty("real_name")
    String realName;
    @Email
    @JsonProperty("email")
    String email;
    @NotEmpty
	@Length(min = 4, max = 32)
	@JsonProperty("login_name")
	String loginName;


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
}
