package com.bumu.arya.salary.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class UpdateSalaryUserInfoCommand {

    @ApiModelProperty(value = "用户ID", name = "userId")
    @NotBlank(message = "用户ID必填")
    private String userId;

    @ApiModelProperty(value = "用户姓名", name = "userName")
    @NotBlank(message = "用户姓名必填")
    private String userName;

    @ApiModelProperty(value = "身份证", name = "idCardNo")
    @NotBlank(message = "身份证必填")
    private String idCardNo;

    @ApiModelProperty(value = "电话号码", name = "phoneNo")
    private String phoneNo;

    @ApiModelProperty(value = "银行账号", name = "bankAccount")
    private String bankAccount;

    @ApiModelProperty(value = "开户行名称", name = "bankName")
    private String bankName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
