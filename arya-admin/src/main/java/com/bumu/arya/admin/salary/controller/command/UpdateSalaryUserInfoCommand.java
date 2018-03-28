package com.bumu.arya.admin.salary.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 16/8/26.
 */
public class UpdateSalaryUserInfoCommand {

    @JsonProperty("salary_id")
    String id;

    String name;

    @JsonProperty("phone_no")
    String phoneNo;

    @JsonProperty("idcard_no")
    String idcardNo;

    @JsonProperty("bank_account")
    String bankAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
