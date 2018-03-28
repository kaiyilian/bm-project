package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecuteSoinImportCommand {

	@JsonProperty("file_id")
	String fileId;

	@JsonProperty("district_id")
	String districtId;

	@JsonProperty("soin_type_id")
	String soinTypeId;

	@JsonProperty("corporation_id")
	String  corporationId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getSoinTypeId() {
		return soinTypeId;
	}

	public void setSoinTypeId(String soinTypeId) {
		this.soinTypeId = soinTypeId;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public ExecuteSoinImportCommand() {

	}
}
