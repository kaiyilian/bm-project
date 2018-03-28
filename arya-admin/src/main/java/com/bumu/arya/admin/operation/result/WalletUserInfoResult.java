package com.bumu.arya.admin.operation.result;

import com.bumu.arya.wallet.constants.WalletConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
@ApiModel
public class WalletUserInfoResult {

    @ApiModelProperty("用户信息")
    private UserInfo userInfo = new UserInfo();

    @ApiModelProperty("银行卡列表")
    private List<BankCardInfo> bankCardInfoList = new ArrayList<>();

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<BankCardInfo> getBankCardInfoList() {
        return bankCardInfoList;
    }

    public void setBankCardInfoList(List<BankCardInfo> bankCardInfoList) {
        this.bankCardInfoList = bankCardInfoList;
    }

    @ApiModel
    public class UserInfo{
        @ApiModelProperty("钱包用户账号")
        private String walletUserId;

        @ApiModelProperty("钱包注册手机号")
        private String phone;

        @ApiModelProperty("钱包实名姓名")
        private String validUserName;

        @ApiModelProperty("钱包实名身份证号")
        private String validCardNo;

        public String getWalletUserId() {
            return walletUserId;
        }

        public void setWalletUserId(String walletUserId) {
            this.walletUserId = walletUserId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getValidUserName() {
            return validUserName;
        }

        public void setValidUserName(String validUserName) {
            this.validUserName = validUserName;
        }

        public String getValidCardNo() {
            return validCardNo;
        }

        public void setValidCardNo(String validCardNo) {
            this.validCardNo = validCardNo;
        }
    }

    @ApiModel
    public class BankCardInfo{

        @ApiModelProperty("银行名称")
        private String bankName;

        @ApiModelProperty("银行卡号")
        private String bankCardNo;

        @ApiModelProperty("银行卡类型")
        private String bankType;

        @ApiModelProperty("绑定时间")
        private Long bindTime;

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankCardNo() {
            return bankCardNo;
        }

        public void setBankCardNo(String bankCardNo) {
            if(StringUtils.isNotBlank(bankCardNo)) {
                String newBankCardNo = bankCardNo.substring(0,4);
                for (int i = 0; i < bankCardNo.length() - 8; i++) {
                    newBankCardNo += "*";
                }
                newBankCardNo += bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length());
                this.bankCardNo = newBankCardNo;
            }
            //this.bankCardNo = bankCardNo;
        }

        public String getBankType() {
            return bankType;
        }

        public void setBankType(String bankType) {
            if (StringUtils.isNotBlank(bankType) && WalletConstants.BankTypeEnum.SAVING.getBankCardTypeCode().equals(bankType)) {
                this.bankType = WalletConstants.BankTypeEnum.SAVING.getBankCardTypeName();
            }
        }

        public Long getBindTime() {
            return bindTime;
        }

        public void setBindTime(Long bindTime) {
            this.bindTime = bindTime;
        }
    }

}
