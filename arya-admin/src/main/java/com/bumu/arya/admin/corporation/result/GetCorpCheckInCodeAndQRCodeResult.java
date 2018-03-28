package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 16/7/21.
 */
public class GetCorpCheckInCodeAndQRCodeResult {

    String code;

    @JsonProperty("qrcode_url")
    String qrcodeUrl;

    @JsonProperty("is_effective")
    int isEffective;

    public GetCorpCheckInCodeAndQRCodeResult() {
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }
}
