package com.bumu.arya.admin.payroll.result;

import com.bumu.paysalary.enums.TradeStatusEnum;
import com.bumu.paysalary.model.entity.WalletPaySalaryApplyDetailEntity;

public class WalletPayAuditQueryDetailResult {
    private Long id;//ID

    private String userName;//员工姓名

    private String cardNo;//员工身份证号码

    private String walletUserId;//员工钱包ID

    private String amount;//支付金额

    private String tradeStatus;//交易状态

    private String tradeStatusDesc;//交易状态

    private String tradeMsg;//交易结果描述

    private String projectName;

    public void copy(WalletPaySalaryApplyDetailEntity apply){
        id=apply.getId();
        userName=apply.getUserName();
        cardNo=apply.getCardNo();
        walletUserId=apply.getWalletUserId();
        amount=apply.getAmount();
        tradeStatus=apply.getTradeStatus();
        tradeStatusDesc= TradeStatusEnum.getDesc(apply.getTradeStatus());
        tradeMsg=apply.getTradeMsg();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getWalletUserId() {
        return walletUserId;
    }

    public void setWalletUserId(String walletUserId) {
        this.walletUserId = walletUserId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeStatusDesc() {
        return tradeStatusDesc;
    }

    public void setTradeStatusDesc(String tradeStatusDesc) {
        this.tradeStatusDesc = tradeStatusDesc;
    }

    public String getTradeMsg() {
        return tradeMsg;
    }

    public void setTradeMsg(String tradeMsg) {
        this.tradeMsg = tradeMsg;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
