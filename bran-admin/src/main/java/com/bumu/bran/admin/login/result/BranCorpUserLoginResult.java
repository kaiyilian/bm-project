package com.bumu.bran.admin.login.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CuiMengxin
 * @date 2016/6/16
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel
public class BranCorpUserLoginResult {
	@ApiModelProperty(value = "响应码", example = "1000")
	int code;
	@ApiModelProperty(value = "验证码url", example = "http ... url")
	@JsonProperty("captcha_url")
	String captchaUrl;

	public BranCorpUserLoginResult() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCaptchaUrl() {
		return captchaUrl;
	}

	public void setCaptchaUrl(String captchaUrl) {
		this.captchaUrl = captchaUrl;
	}
}
