package com.bumu.arya.admin.corporation.controller.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 更新考勤打卡方式
 * Created by DaiAoXiang on 2017/5/4.
 */
@ApiModel
public class CorpAttendanceClockTypeCommand {

	@ApiModelProperty("公司Id")
	String corpId;

	@ApiModelProperty("打卡类型 0.没有选择打卡方式 1.考勤机打卡 2.手机打卡")
	List<ClockType> clockTypes;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public List<ClockType> getClockTypes() {
		return clockTypes;
	}

	public void setClockTypes(List<ClockType> clockTypes) {
		this.clockTypes = clockTypes;
	}

	public static class ClockType{
		int type;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
	}
}
