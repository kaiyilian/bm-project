package com.bumu.arya.admin.welfare.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by CuiMengxin on 16/9/14.
 */
@ApiModel
public class WelfareUploadImageResult {

    @ApiModelProperty("图片id")
    String id;

    @ApiModelProperty("图片url")
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
