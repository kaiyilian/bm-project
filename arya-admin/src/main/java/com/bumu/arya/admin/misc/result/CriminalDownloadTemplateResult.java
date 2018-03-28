package com.bumu.arya.admin.misc.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by liangjun on 2017/3/10.
 */
@ApiModel(value = "下载批量查询模板")
public class CriminalDownloadTemplateResult {
	@ApiModelProperty(value = "下载的url")
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
