package com.bumu.bran.admin.attendance.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业考勤机result
 */
@ApiModel
public class WorkAttendanceDeviceResult {

	@ApiModelProperty("设备编号")
	private String deviceNo;

	@ApiModelProperty("设备名称")
	private String deviceName;

	@ApiModelProperty("关联班组")
	private List<WorkShiftResult> workShiftResults = new ArrayList<>();

	public WorkAttendanceDeviceResult() {

	}

	public WorkAttendanceDeviceResult(String deviceNo, String deviceName) {
		this.deviceNo = deviceNo;
		this.deviceName = deviceName;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public List<WorkShiftResult> getWorkShiftResults() {
		return workShiftResults;
	}

	public void setWorkShiftResults(List<WorkShiftResult> workShiftResults) {
		this.workShiftResults = workShiftResults;
	}

	@ApiModel
	public class WorkShiftResult{
		@ApiModelProperty("班组ID")
		private String workShiftId;

		@ApiModelProperty("班组名称")
		private String workShiftName;

		public WorkShiftResult(String workShiftId, String workShiftName){
			this.workShiftId = workShiftId;
			this.workShiftName = workShiftName;
		}

		public String getWorkShiftId() {
			return workShiftId;
		}

		public void setWorkShiftId(String workShiftId) {
			this.workShiftId = workShiftId;
		}

		public String getWorkShiftName() {
			return workShiftName;
		}

		public void setWorkShiftName(String workShiftName) {
			this.workShiftName = workShiftName;
		}
	}
}
