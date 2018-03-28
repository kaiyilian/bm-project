package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.admin.soin.result.CreateSoinTypeResult;
import com.bumu.arya.command.HttpCommand;
import com.bumu.arya.response.HttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by CuiMengxin on 2015/10/22.
 */
@ApiModel
public class CreateSoinTypeCommand extends HttpCommand{

	@ApiModelProperty(value = "社保地区ID",required = true)
	@JsonProperty("district_id")
	String  districtId;

	@ApiModelProperty(value = "社保类型名字",required = true)
	String name;

	@ApiModelProperty(value = "复制的社保类型ID")
	@JsonProperty("copy_soin_type_id")
	String copySoinTypeId;

	@Override
	public void init() {
		this.setResponseTypeReference(new TypeReference<HttpResponse<CreateSoinTypeResult>>() {
		});
	}

	public CreateSoinTypeCommand() {
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCopySoinTypeId() {
		return copySoinTypeId;
	}

	public void setCopySoinTypeId(String copySoinTypeId) {
		this.copySoinTypeId = copySoinTypeId;
	}
}
