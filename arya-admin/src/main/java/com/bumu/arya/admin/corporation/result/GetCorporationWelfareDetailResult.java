package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by DaiAoXiang on 2017/4/27.
 */
@ApiModel
public class GetCorporationWelfareDetailResult {

	@ApiModelProperty("公司id")
	String id;

	@ApiModelProperty("福库公司名称")
	@JsonProperty("welfare_corp_name")
	String welfareCorpName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWelfareCorpName() {
		return welfareCorpName;
	}

	public void setWelfareCorpName(String welfareCorpName) {
		this.welfareCorpName = welfareCorpName;
	}
}
