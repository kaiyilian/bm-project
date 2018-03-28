package com.bumu.arya.admin.payroll.controller.command;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by liangjun on 17-7-26.
 */
@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class ESalaryCommand {

    @ApiModelProperty(value = "查询的关键字，查询全部时 该字段为空")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
