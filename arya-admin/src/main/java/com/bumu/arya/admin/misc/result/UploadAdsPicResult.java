package com.bumu.arya.admin.misc.result;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/6/14
 */
public class UploadAdsPicResult {

	@JsonProperty("file_name")
	String fileName;

	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public UploadAdsPicResult() {

	}
}
