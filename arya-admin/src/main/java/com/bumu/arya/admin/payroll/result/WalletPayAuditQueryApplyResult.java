package com.bumu.arya.admin.payroll.result;

import com.bumu.paysalary.enums.BatchTradeStatusEnum;
import com.bumu.paysalary.model.entity.WalletPaySalaryApplyEntity;

import java.util.Date;

public class WalletPayAuditQueryApplyResult {
    private Long id;//ID

    private String batchNo;//批次号

    private String projectName;//企业名称

    private String totalAmount;//申请支付总额

    private Long totalPerson;//发薪人数

    private Date applyTime;//申请时间

    private String status;//状态

    private String statusDesc;//状态描述

    private String payTime;//发薪时间

    public void copy(WalletPaySalaryApplyEntity apply){
        id=apply.getId();
        batchNo=apply.getBatchNo();
        totalAmount=apply.getTotalAmount();
        totalPerson=apply.getTotalPerson();
        applyTime=apply.getApplyTime();
        status=apply.getStatus();
        statusDesc= BatchTradeStatusEnum.getDesc(apply.getStatus());
        payTime=apply.getPayTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(Long totalPerson) {
        this.totalPerson = totalPerson;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
