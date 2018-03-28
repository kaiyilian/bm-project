package com.bumu.arya.admin.misc.controller.command;/* @author CuiMengxin
 * @date 2015/11/25
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CreateAryaUserCommand implements Serializable {
	@JsonProperty("phone_no")
	String phoneNo;

	@JsonProperty("real_name")
	String realName;

	String gender;

	@JsonProperty("login_pwd")
	String loginPwd;

	@JsonProperty("idcard_no")
	String idcardNo;

	String email;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CreateAryaUserCommand() {

	}
}
