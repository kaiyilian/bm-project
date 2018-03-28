package com.bumu.bran.admin.login.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/13
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel
public class SigninCommand {

	@ApiModelProperty(value = "账号, 1.必填", required = true, example = "admin")
	String account;

	@ApiModelProperty(value = "密码, 1.必填", required = true, example = "123456")
	String password;

	@ApiModelProperty(value = "验证码, 账号密码输入3之后,必填",  example = "ABCE")
	String captcha;

	String browserSessionId;

	String ip;

	public SigninCommand() {

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowserSessionId() {
		return browserSessionId;
	}

	public void setBrowserSessionId(String browserSessionId) {
		this.browserSessionId = browserSessionId;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
