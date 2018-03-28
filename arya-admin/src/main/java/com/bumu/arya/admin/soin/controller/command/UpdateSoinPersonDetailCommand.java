package com.bumu.arya.admin.soin.controller.command;
/* @author CuiMengxin
 * @date 2015/11/18
 */

import com.bumu.arya.cache.CacheConfig;
import com.bumu.arya.command.HttpCommand;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateSoinPersonDetailCommand extends HttpCommand {

	@JsonProperty("insured_person_id")
	String insuredPersionId;

	@JsonProperty("verify_status")
	int verifyStatus;

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getInsuredPersionId() {
		return insuredPersionId;
	}

	public void setInsuredPersionId(String insuredPersionId) {
		this.insuredPersionId = insuredPersionId;
	}

	public UpdateSoinPersonDetailCommand() {
	}

	public UpdateSoinPersonDetailCommand(String url) {
		super(url);
	}

	public UpdateSoinPersonDetailCommand(String url, CacheConfig cacheConfig, boolean cacheOnly) {
		super(url, cacheConfig, cacheOnly);
	}
}
