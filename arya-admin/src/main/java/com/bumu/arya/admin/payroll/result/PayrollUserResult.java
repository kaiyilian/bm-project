package com.bumu.arya.admin.payroll.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by liangjun on 17-7-26.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PayrollUserResult {

    @ApiModelProperty(value = "公司的id")
    private String id ;

    @ApiModelProperty(value = "公式名称")
    private String corpName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "首次使用日期")
    private String time;


    @ApiModelProperty(value = "使用的次数")
    private Integer useTimes;

    @ApiModelProperty(value = "发薪人数")
    private Integer payrollNumber;

    @ApiModelProperty(value = "登录账号")
    private String loginName;

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(Integer useTimes) {
        this.useTimes = useTimes;
    }

    public Integer getPayrollNumber() {
        return payrollNumber;
    }

    public void setPayrollNumber(Integer payrollNumber) {
        this.payrollNumber = payrollNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
