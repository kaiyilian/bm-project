package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

public class DeleteSoinDistrictCommand implements Serializable{
	@JsonProperty("district_id")
	String districtId;

	public DeleteSoinDistrictCommand() {
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
}
