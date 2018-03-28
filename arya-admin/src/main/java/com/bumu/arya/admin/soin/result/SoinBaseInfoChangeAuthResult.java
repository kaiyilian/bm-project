package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 2016/11/15.
 */
public class SoinBaseInfoChangeAuthResult {

	@JsonProperty("has_auth")
	int hasAuth;

	public int getHasAuth() {
		return hasAuth;
	}

	public void setHasAuth(int hasAuth) {
		this.hasAuth = hasAuth;
	}
}
