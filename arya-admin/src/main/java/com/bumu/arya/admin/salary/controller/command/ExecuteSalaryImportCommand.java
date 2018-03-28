/* @author CuiMengxin
 * @date 2015/11/25
 */
package com.bumu.arya.admin.salary.controller.command;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ExecuteSalaryImportCommand implements Serializable {
	@JsonProperty("corporation_id")
	String corporationId;

	@JsonProperty("file_id")
	String fileId;

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public ExecuteSalaryImportCommand() {

	}
}
