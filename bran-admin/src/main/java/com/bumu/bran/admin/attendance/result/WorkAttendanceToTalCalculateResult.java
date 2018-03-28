package com.bumu.bran.admin.attendance.result;

import com.bumu.attendance.constant.WorkAttendanceEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DaiAoXiang on 2017/4/10.
 */
public class WorkAttendanceToTalCalculateResult {

	int lackCount = 0;//缺卡次数;
	int attendanceDays = 0;//应出勤天数
	int actualAttendanceDays = 0;//实际出勤
	int attendanceHours = 0;//出勤时长
	int restCount = 0;//休息天数
	int lateCount = 0;//迟到次数
	int lateHours = 0;//迟到时长
	int unFullCount = 0;//早退次数
	int unFullHours = 0;//早退时长
	int leaveHours = 0;//请假时长
	int leaveTimes = 0;
	int overTimeHours = 0;//加班时长
	double absentDays = 0;//旷工天数
	int absentTimes = 0;
	int normalHours = 0;//正常工时
	Map<Integer, Long> approvalType = new HashMap<>();
	WorkAttendanceEnum.ClockType clockType;
	public WorkAttendanceToTalCalculateResult(){
	}

	public Map<Integer, Long> getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(Map<Integer, Long> approvalType) {
		this.approvalType = approvalType;
	}

	public int getActualAttendanceDays() {
		return actualAttendanceDays;
	}

	public void setActualAttendanceDays(int actualAttendanceDays) {
		this.actualAttendanceDays = actualAttendanceDays;
	}

	public int getNormalHours() {
		return normalHours;
	}

	public void setNormalHours(int normalHours) {
		this.normalHours = normalHours;
	}

	public WorkAttendanceEnum.ClockType getClockType() {
		return clockType;
	}

	public void setClockType(WorkAttendanceEnum.ClockType clockType) {
		this.clockType = clockType;
	}

	public int getLeaveTimes() {
		return leaveTimes;
	}

	public void setLeaveTimes(int leaveTimes) {
		this.leaveTimes = leaveTimes;
	}

	public int getAbsentTimes() {
		return absentTimes;
	}

	public void setAbsentTimes(int absentTimes) {
		this.absentTimes = absentTimes;
	}

	public int getLackCount() {
		return lackCount;
	}

	public void setLackCount(int lackCount) {
		this.lackCount = lackCount;
	}

	public int getAttendanceDays() {
		return attendanceDays;
	}

	public void setAttendanceDays(int attendanceDays) {
		this.attendanceDays = attendanceDays;
	}

	public int getAttendanceHours() {
		return attendanceHours;
	}

	public void setAttendanceHours(int attendanceHours) {
		this.attendanceHours = attendanceHours;
	}

	public int getRestCount() {
		return restCount;
	}

	public void setRestCount(int restCount) {
		this.restCount = restCount;
	}

	public int getLateCount() {
		return lateCount;
	}

	public void setLateCount(int lateCount) {
		this.lateCount = lateCount;
	}

	public int getLateHours() {
		return lateHours;
	}

	public void setLateHours(int lateHours) {
		this.lateHours = lateHours;
	}

	public int getUnFullCount() {
		return unFullCount;
	}

	public void setUnFullCount(int unFullCount) {
		this.unFullCount = unFullCount;
	}

	public int getUnFullHours() {
		return unFullHours;
	}

	public void setUnFullHours(int unFullHours) {
		this.unFullHours = unFullHours;
	}

	public int getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(int leaveHours) {
		this.leaveHours = leaveHours;
	}

	public int getOverTimeHours() {
		return overTimeHours;
	}

	public void setOverTimeHours(int overTimeHours) {
		this.overTimeHours = overTimeHours;
	}

	public double getAbsentDays() {
		return absentDays;
	}

	public void setAbsentDays(double absentDays) {
		this.absentDays = absentDays;
	}


}
