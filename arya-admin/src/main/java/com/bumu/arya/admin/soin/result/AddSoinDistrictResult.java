package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by CuiMengxin on 2015/11/4.
 */
public class AddSoinDistrictResult implements Serializable {
	@JsonProperty("is_exist")
	Boolean isExist;

	@JsonProperty("is_success")
	Boolean isSuccess;

	public AddSoinDistrictResult() {
	}

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
