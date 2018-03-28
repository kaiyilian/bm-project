package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/3/9
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateSoinTypeVersionCommand {

	@JsonProperty("type_id")
	String typeId;

	@JsonProperty("effect_year")
	Integer effectYear;

	@JsonProperty("effect_month")
	Integer effectMonth;

	@JsonProperty("base_accordant")
	int baseAccordant;

	Integer versionType;//版本类型 是补缴还是正常缴纳

	public CreateSoinTypeVersionCommand() {
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getEffectYear() {
		return effectYear;
	}

	public void setEffectYear(Integer effectYear) {
		this.effectYear = effectYear;
	}

	public Integer getEffectMonth() {
		return effectMonth;
	}

	public void setEffectMonth(Integer effectMonth) {
		this.effectMonth = effectMonth;
	}

	public int getBaseAccordant() {
		return baseAccordant;
	}

	public void setBaseAccordant(int baseAccordant) {
		this.baseAccordant = baseAccordant;
	}

	public Integer getVersionType() {
		return versionType;
	}

	public void setVersionType(Integer versionType) {
		this.versionType = versionType;
	}
}
