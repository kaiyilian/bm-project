package com.bumu.arya.admin.misc.result;

import com.bumu.arya.response.SimpleIdNameResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by CuiMengxin on 2016/10/17.
 */
public class AryaUserInfoResult extends SimpleIdNameResult {

    @JsonProperty("idcard_no")
    String idcardNo;

    @JsonProperty("nick_name")
    String nickName;

    @JsonProperty("phone_no")
    String phoneNo;

    String hukou;

    String corp;

    String sex;

    @JsonProperty("short_name")
    String shortName;

    @JsonProperty("use_phone_no")
    String usePhoneNo;


    @JsonProperty("create_time")
    long createTime;

    @JsonProperty("last_login_time")
    long lastLoginTime;

    BigDecimal balance;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getHukou() {
        return hukou;
    }

    public void setHukou(String hukou) {
        this.hukou = hukou;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }

    public String getShortName(){return shortName;}

    public void setShortName(String shortName){this.shortName = shortName;}

    public String getUsePhoneNo(){return usePhoneNo;}

    public void setUsePhoneNo(String usePhoneNo){this.usePhoneNo = usePhoneNo;}

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
