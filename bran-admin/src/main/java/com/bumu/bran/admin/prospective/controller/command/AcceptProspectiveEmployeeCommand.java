package com.bumu.bran.admin.prospective.controller.command;

import com.bumu.bran.admin.system.command.IdVersionsCommand;
import com.bumu.bran.validated.AcceptProspectiveValidatedGroup;
import com.bumu.prospective.validated.ValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author CuiMengxin
 * @date 2016/5/16
 */
@ApiModel
public class AcceptProspectiveEmployeeCommand extends IdVersionsCommand {

	@JsonProperty("contract_start_time")
	private long contractStartTime;

	@JsonProperty("contract_end_time")
	private long contractEndTime;

	@ApiModelProperty(value = "起始工号", required = true)
	@JsonProperty("start_work_sn")
	@NotBlank(message = "起始工号" + ValidationMessages.NOT_BLANK, groups = AcceptProspectiveValidatedGroup.class)
	private String startWorkSn;

	@JsonProperty("end_work_sn")
	private String endWorkSn;

	@JsonProperty("work_sn_prefix_name")
	private String workSnPrefixName;

    @ApiModelProperty("试用期应该在0-6个月之间")
	@Range(min = 0, max = 6, message = "试用期应该在0-6个月之间", groups = AcceptProspectiveValidatedGroup.class)
	@NotNull(message = "试用期" + ValidationMessages.NOT_BLANK, groups = AcceptProspectiveValidatedGroup.class)
	private Integer probation;

	private Integer count;

    @ApiModelProperty("工号ID，如果没有起始工号则传递'empty'字符串")
	@JsonProperty("work_sn_prefix_id")
	@NotNull(message = "工号ID" + ValidationMessages.NOT_BLANK, groups = AcceptProspectiveValidatedGroup.class)
	private String workSnPrefixId;

	// 面试日期
    @ApiModelProperty("面试日期")
	@JsonProperty("interview_date")
	private Long interviewDate;

	// 供应来源
    @ApiModelProperty("供应来源")
	@Length(max = 40, message = "供应来源" + ValidationMessages.MAX_LENGTH + "40字",
			groups = AcceptProspectiveValidatedGroup.class)
	@JsonProperty("source_of_supply")
	private String sourceOfSupply;

	// 员工性质
    @ApiModelProperty("员工性质")
	@Length(max = 40, message = "员工性质" + ValidationMessages.MAX_LENGTH + "40字",
			groups = AcceptProspectiveValidatedGroup.class)
	@JsonProperty("employee_nature")
	private String employeeNature;

	public AcceptProspectiveEmployeeCommand() {

	}

	public String getStartWorkSn() {
		return startWorkSn;
	}

	public void setStartWorkSn(String startWorkSn) {
		this.startWorkSn = startWorkSn;
	}

	public long getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(long contractStartTime) {
		this.contractStartTime = contractStartTime;
	}

	public long getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(long contractEndTime) {
		this.contractEndTime = contractEndTime;
	}

	public Integer getProbation() {
		return probation;
	}

	public void setProbation(Integer probation) {
		this.probation = probation;
	}

	public String getEndWorkSn() {
		return endWorkSn;
	}

	public void setEndWorkSn(String endWorkSn) {
		this.endWorkSn = endWorkSn;
	}

	public String getWorkSnPrefixName() {
		return workSnPrefixName;
	}

	public void setWorkSnPrefixName(String workSnPrefixName) {
		this.workSnPrefixName = workSnPrefixName;
	}

	public Integer getCount() {
		return count == null ? 0 : count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getWorkSnPrefixId() {
		return workSnPrefixId;
	}

	public void setWorkSnPrefixId(String workSnPrefixId) {
		this.workSnPrefixId = workSnPrefixId;
	}

	public Long getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Long interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getSourceOfSupply() {
		return sourceOfSupply;
	}

	public void setSourceOfSupply(String sourceOfSupply) {
		this.sourceOfSupply = sourceOfSupply;
	}

	public String getEmployeeNature() {
		return employeeNature;
	}

	public void setEmployeeNature(String employeeNature) {
		this.employeeNature = employeeNature;
	}
}
