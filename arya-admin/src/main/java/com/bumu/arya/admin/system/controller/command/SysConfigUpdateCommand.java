package com.bumu.arya.admin.system.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * Created by CuiMengxin on 2017/2/14.
 */
@ApiModel
public class SysConfigUpdateCommand {

	String id;

	String value;

	String memo;

	//为了方便小米所以将Integer类型改为String，直接传yes或no
	@JsonProperty("is_deprecated")
	String isDeprecated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIsDeprecated() {
		return isDeprecated;
	}

	public void setIsDeprecated(String isDeprecated) {
		this.isDeprecated = isDeprecated;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
