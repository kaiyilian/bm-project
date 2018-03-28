package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CuiMengxin
 * @date 2016/7/13
 */
@ApiModel
public class AryaDepartmentDetailResult {

	@ApiModelProperty("部门Id")
	@JsonProperty("department_id")
	String departmentId;

	@ApiModelProperty("部门名称")
	String name;

	@ApiModelProperty("创建时间")
	@JsonProperty("create_time")
	Long createTime;

	@ApiModelProperty("更新时间")
	@JsonProperty("update_time")
	Long updateTime;

	public AryaDepartmentDetailResult() {
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}
