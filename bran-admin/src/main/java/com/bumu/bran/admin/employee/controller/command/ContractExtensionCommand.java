package com.bumu.bran.admin.employee.controller.command;

import com.bumu.bran.admin.system.command.IdVersionsCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/20
 */
public class ContractExtensionCommand extends IdVersionsCommand {

	@JsonProperty("contract_start_time")
	Long contractStartTime;

	@JsonProperty("contract_end_time")
	Long contractEndTime;

	public ContractExtensionCommand() {

	}

	public Long getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(Long contractStartTime) {
		this.contractStartTime = contractStartTime;
	}

	public Long getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(Long contractEndTime) {
		this.contractEndTime = contractEndTime;
	}
}
