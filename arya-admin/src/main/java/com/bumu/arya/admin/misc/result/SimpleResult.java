package com.bumu.arya.admin.misc.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * Created by CuiMengxin on 16/8/8.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SimpleResult {

	@ApiModelProperty(value = "ID")
	String id;

	@ApiModelProperty(value = "名称")
	String name;

	public SimpleResult() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
