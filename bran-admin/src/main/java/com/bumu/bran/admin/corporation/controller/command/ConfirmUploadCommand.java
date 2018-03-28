package com.bumu.bran.admin.corporation.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/19
 */
public class ConfirmUploadCommand {

	@JsonProperty("file_name")
	String fileName;

	public ConfirmUploadCommand() {

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
