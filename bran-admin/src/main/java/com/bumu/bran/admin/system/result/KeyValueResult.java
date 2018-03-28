package com.bumu.bran.admin.system.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2017/10/25
 * @email 351264830@qq.com
 */
@ApiModel
public class KeyValueResult {

    @ApiModelProperty
    private String key;
    @ApiModelProperty
    private String value;

    public KeyValueResult() {
    }
    public KeyValueResult(String key, String value) {
        this.key = key;
        this.value = value;
    }

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
}
