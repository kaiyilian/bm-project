package com.bumu.arya.admin.system.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * Created by CuiMengxin on 2016/10/12.
 */
@ApiModel
public class SimpleUrlResult {

	@ApiModelProperty(value = "完整的URL")
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
