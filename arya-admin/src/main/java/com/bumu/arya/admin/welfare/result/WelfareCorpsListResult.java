package com.bumu.arya.admin.welfare.result;

import com.bumu.arya.admin.misc.result.SimpleResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by DaiAoXiang on 2016/11/29.
 */
@ApiModel
public class WelfareCorpsListResult {

	@ApiModelProperty("企业名称列表")
	List<SimpleResult> corps;

	public List<SimpleResult> getCorps() {
		return corps;
	}

	public void setCorps(List<SimpleResult> corps) {
		this.corps = corps;
	}

}
