package com.bumu.arya.admin.system.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * Created by CuiMengxin on 2017/2/14.
 */
@ApiModel
public class SysConfigCreateCommand {

    String key;

    String value;

    String memo;

    @JsonProperty("is_deprecated")
    String isDeprecated;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsDeprecated() {
        return isDeprecated;
    }

    public void setIsDeprecated(String isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
