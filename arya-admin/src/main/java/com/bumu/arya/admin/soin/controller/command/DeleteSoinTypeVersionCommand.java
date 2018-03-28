package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2017/3/13.
 */
public class DeleteSoinTypeVersionCommand {

	@ApiModelProperty(value = "版本Id")
	@JsonProperty("type_version_id")
	String typeVersionId;

	public DeleteSoinTypeVersionCommand(){
	}

	public String getTypeVersionId() {
		return typeVersionId;
	}

	public void setTypeVersionId(String typeVersionId) {
		this.typeVersionId = typeVersionId;
	}
}
