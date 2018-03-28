package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CreateSoinDistrictCommand implements Serializable{

	@JsonProperty("district_id")
	String districtId;

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public CreateSoinDistrictCommand() {
	}
}
