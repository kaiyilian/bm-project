package com.bumu.arya.admin.system.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by CuiMengxin on 2015/11/2.
 */
@ApiModel
public class LoginResult implements Serializable {

	@ApiModelProperty(value = "是否登录成功")
    Boolean isPassed;

    @ApiModelProperty(value = "尝试登录次数")
    @JsonProperty("try_login_times")
    int tryLoginTimes;

    public LoginResult() {
    }

    public int getTryLoginTimes() {
        return tryLoginTimes;
    }

    public void setTryLoginTimes(int tryLoginTimes) {
        this.tryLoginTimes = tryLoginTimes;
    }

    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }
}
