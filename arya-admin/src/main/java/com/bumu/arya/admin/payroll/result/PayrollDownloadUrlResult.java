package com.bumu.arya.admin.payroll.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by liangjun on 17-7-26.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PayrollDownloadUrlResult {
    @ApiModelProperty(value = "下载的url")

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
