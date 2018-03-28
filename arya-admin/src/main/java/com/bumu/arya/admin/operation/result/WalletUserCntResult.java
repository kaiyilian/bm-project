package com.bumu.arya.admin.operation.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
@ApiModel
public class WalletUserCntResult {

    @ApiModelProperty("钱包用户名称")
    private String validUserName;

    @ApiModelProperty("钱包手机号")
    private String phone;

    @ApiModelProperty("APP手机号")
    private String appPhone;

    @ApiModelProperty("钱包用户ID")
    private String walletUserId;

    @ApiModelProperty("钱包用户身份证")
    private String validCardNo;

    @ApiModelProperty("绑定银行卡数量")
    private Integer bankCardNum;

    public String getValidUserName() {
        return validUserName;
    }

    public void setValidUserName(String validUserName) {
        this.validUserName = validUserName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAppPhone() {
        return appPhone;
    }

    public void setAppPhone(String appPhone) {
        this.appPhone = appPhone;
    }

    public String getWalletUserId() {
        return walletUserId;
    }

    public void setWalletUserId(String walletUserId) {
        this.walletUserId = walletUserId;
    }

    public String getValidCardNo() {
        return validCardNo;
    }

    public void setValidCardNo(String validCardNo) {
        this.validCardNo = validCardNo;
    }

    public Integer getBankCardNum() {
        return bankCardNum;
    }

    public void setBankCardNum(Integer bankCardNum) {
        this.bankCardNum = bankCardNum;
    }
}
