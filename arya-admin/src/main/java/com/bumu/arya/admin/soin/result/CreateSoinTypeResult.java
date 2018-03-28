package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by CuiMengxin on 2015/10/22.
 */
public class CreateSoinTypeResult implements Serializable {
	@JsonProperty("soin_type_id")
	String typeId;

	public CreateSoinTypeResult() {
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
