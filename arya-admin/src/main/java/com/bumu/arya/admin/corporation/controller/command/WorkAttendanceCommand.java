package com.bumu.arya.admin.corporation.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class WorkAttendanceCommand {

	@JsonProperty("bran_corp_id")
	String branCorpId;

	@JsonProperty("date_time")
	String dateTime;

	public WorkAttendanceCommand() {
	}

	public String getBranCorpId() {
		return branCorpId;
	}

	public void setBranCorpId(String branCorpId) {
		this.branCorpId = branCorpId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
