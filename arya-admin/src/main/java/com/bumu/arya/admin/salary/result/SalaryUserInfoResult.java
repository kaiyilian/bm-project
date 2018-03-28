package com.bumu.arya.admin.salary.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 16/8/25.
 */
public class SalaryUserInfoResult {

    @JsonProperty("salary_id")
    String salaryId;

    String name;

    @JsonProperty("idcard_no")
    String idcardNo;

    @JsonProperty("phone_no")
    String phoneNo;

    @JsonProperty("bank_account")
    String bankAccount;

    public String getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
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
}
