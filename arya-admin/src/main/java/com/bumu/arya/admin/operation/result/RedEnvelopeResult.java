package com.bumu.arya.admin.operation.result;

import com.bumu.SysUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
@ApiModel
public class RedEnvelopeResult {

    @ApiModelProperty(value = "领取时间")
    private Long getTime;
    @ApiModelProperty(value = "用户名")
    private String realName;
    @ApiModelProperty(value = "手机号码")
    private String phone;
    @ApiModelProperty(value = "红包金额 单位/元")
    private String redEnvelopeBalance;
    @ApiModelProperty(value = "余额时长 单位/秒")
    private Long balanceTotalTime;
    @ApiModelProperty(value = "当时余额 单位/元")
    private String curTimeBalance;
    @ApiModelProperty(value = "发送状态 0:成功 1:失败")
    private Integer sendStatus;

    // 格式化数据,在导出的时候用到
    @ApiModelProperty(value = "领取时间 格式化")
    private String getTimeFormatStr;
    @ApiModelProperty(value = "红包金额 单位/元 格式化")
    private String redEnvelopeBalanceFormatStr;
    @ApiModelProperty(value = "余额时长 单位/秒 格式化")
    private String balanceTotalTimeFormatStr;
    @ApiModelProperty(value = "当时余额 单位/元 格式化")
    private String curTimeBalanceFormatStr;
    @ApiModelProperty(value = "发送状态 0:成功 1:失败 格式化")
    private String sendStatusFormatStr;

    public Long getGetTime() {
        return getTime;
    }

    public void setGetTime(Long getTime) {
        if (getTime != null) {
            setGetTimeFormatStr(SysUtils.getDateStringFormTimestamp(getTime, "yyyy-MM-dd HH:mm:ss"));
        }
        this.getTime = getTime;
    }

    public String getRealName() {
        if (StringUtils.isBlank(realName) || realName.length() == 1) {
            return "**";
        }
        if (realName.length() == 2) {
            return "*" + realName.substring(1);
        }
        if (realName.length() == 3) {
            return realName.substring(0, 1) + "*" + realName.substring(realName.length() - 1, realName.length());
        }
        if (realName.length() > 3) {
            String prefix = realName.substring(0, 1);
            String suffix = realName.substring(realName.length() - 1, realName.length());
            return StringUtils.rightPad(prefix, realName.length() - 1, "*") + suffix;
        }

        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone.substring(0, 2) + "***" + phone.substring(5, phone.length());
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        if (sendStatus == null) {
            this.sendStatus = 0;
        } else {
            this.sendStatus = sendStatus;
        }

        setSendStatusFormatStr(getSendStatus() == 0 ? "到账失败" : "成功到账");
    }

    public String getRedEnvelopeBalance() {
        return redEnvelopeBalance;
    }

    public void setRedEnvelopeBalance(String redEnvelopeBalance) {
        if (StringUtils.isBlank(redEnvelopeBalance)) {
            this.redEnvelopeBalance = "0";
        } else {
            this.redEnvelopeBalance = redEnvelopeBalance;
        }

        setRedEnvelopeBalanceFormatStr(getRedEnvelopeBalance() + "元");

    }

    public Long getBalanceTotalTime() {
        return balanceTotalTime;
    }

    public void setBalanceTotalTime(Long balanceTotalTime) {
        if (balanceTotalTime == null) {
            this.balanceTotalTime = 0L;
        } else {
            this.balanceTotalTime = balanceTotalTime;
        }

        if (balanceTotalTime < 0) {
            setBalanceTotalTimeFormatStr(0 + "h" + 0 + "m" + 0 + "s");
            return;
        }

        balanceTotalTime /= 1000;

        long h = balanceTotalTime / 60 / 60;
        long m = (balanceTotalTime - h * 60 * 60) / 60;
        long s = balanceTotalTime - h * 60 * 60 - m * 60;

        setBalanceTotalTimeFormatStr(h + "h" + m + "m" + s + "s");
    }

    public String getCurTimeBalance() {
        return curTimeBalance;
    }

    public void setCurTimeBalance(String curTimeBalance) {
        if (StringUtils.isBlank(curTimeBalance)) {
            this.curTimeBalance = "0";
        } else {
            this.curTimeBalance = curTimeBalance;
        }

        setCurTimeBalanceFormatStr(getCurTimeBalance() + "元");

    }

    public String getGetTimeFormatStr() {
        return getTimeFormatStr;
    }

    private void setGetTimeFormatStr(String getTimeFormatStr) {
        this.getTimeFormatStr = getTimeFormatStr;
    }

    public String getRedEnvelopeBalanceFormatStr() {
        return redEnvelopeBalanceFormatStr;
    }

    private void setRedEnvelopeBalanceFormatStr(String redEnvelopeBalanceFormatStr) {
        this.redEnvelopeBalanceFormatStr = redEnvelopeBalanceFormatStr;
    }

    public String getBalanceTotalTimeFormatStr() {
        return balanceTotalTimeFormatStr;
    }

    private void setBalanceTotalTimeFormatStr(String balanceTotalTimeFormatStr) {

        this.balanceTotalTimeFormatStr = balanceTotalTimeFormatStr;
    }

    public String getCurTimeBalanceFormatStr() {
        return curTimeBalanceFormatStr;
    }

    private void setCurTimeBalanceFormatStr(String curTimeBalanceFormatStr) {
        this.curTimeBalanceFormatStr = curTimeBalanceFormatStr;
    }

    public String getSendStatusFormatStr() {
        return sendStatusFormatStr;
    }

    private void setSendStatusFormatStr(String sendStatusFormatStr) {
        this.sendStatusFormatStr = sendStatusFormatStr;
    }
}
