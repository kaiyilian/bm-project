package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class DeleteSoinTypeCommand implements Serializable {
	@ApiModelProperty(value = "社保类型ID")
	@JsonProperty("type_id")
	String typeId;

	public DeleteSoinTypeCommand() {
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
