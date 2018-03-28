package com.bumu.arya.admin.payroll.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by liangjun on 17-7-26.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PayrollDetailResult {
    @ApiModelProperty(value = "该条薪资的id")
    private String salaryId;


    //导入时间
    @ApiModelProperty(value = "导入时间")
    private String importTime;
    //发送时间
    @ApiModelProperty(value = "发送时间")
    private String sendTime;
    //导入人数
    @ApiModelProperty(value = "发送人数")
    private String sendNum;
    //下载的url
    @ApiModelProperty(value = "下载的url")
    private String url;

    public String getImportTime() {
        return importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }
}
