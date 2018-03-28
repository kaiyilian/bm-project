package com.bumu.bran.admin.attendance.service;

import com.bumu.bran.attendance.result.*;
import com.bumu.bran.attendance.command.WorkAttendanceTotalPublishCommand;
import com.bumu.bran.attendance.command.WorkAttendanceTotalQueryCommand;
import com.bumu.common.SessionInfo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by DaiAoXiang on 2017/4/7.
 */
@Transactional
public interface WorkAttendanceTotalService {

	/**
	 * 考勤配置获取
	 *
	 * @return
	 */
	WorkAttendanceTotalGetSettingResult getSettingList(SessionInfo sessionInfo) throws Exception;

	/**
	 * 考勤汇总查询列表
	 * @param command
	 * @param sessionInfo
	 * @return
	 */
	WorkAttendanceTotalResult getTotalPageList(WorkAttendanceTotalQueryCommand command, SessionInfo sessionInfo) throws Exception;

	/**
	 * @param command
	 * @param sessionInfo
	 * @return
	 * @throws Exception
	 */
	NewWorkAttendanceTotalResult list(WorkAttendanceTotalQueryCommand command, SessionInfo sessionInfo) throws Exception;
	/**
	 * 考勤汇总发布
	 *
	 * @param command
	 * @param sessionInfo
	 */
	//void publish(WorkAttendanceTotalQueryCommand command, SessionInfo sessionInfo) throws Exception;

	/**
	 * 考勤汇总发布
	 * @param command
	 * @param sessionInfo
	 * @throws Exception
	 */
	void publish(WorkAttendanceTotalPublishCommand command, SessionInfo sessionInfo) throws Exception;

	/**
	 * 考勤汇总导出
	 *
	 * @param queryDate
	 * @param workShiftId
	 * @param settingId
	 * @param sessionInfo
	 * @param response
	 * @throws Exception
	 */
	void exportWorkAttendanceTotal(Long queryDate, String workShiftId,String settingId,String empId,Integer isOnJob, String departmentId, Integer isPublish, Integer sureState, SessionInfo sessionInfo,HttpServletResponse response) throws Exception;

	/**
	 * 汇总列表导出url
	 *
	 * @return
	 */
	WorkAttendanceTotalExportUrlResult getExportTotalUrl(WorkAttendanceTotalQueryCommand command);

	/**
	 * @param empId
	 * @param settingId
	 * @param yearMonth
	 * @return
	 */
	List<WorkAttendanceLeaveResult> leaveList(String empId, String settingId, Long yearMonth);

	/**
	 * @param empId
	 * @param settingId
	 * @param yearMonth
	 * @return
	 */
	List<WorkAttendanceLateResult> lateList(String empId, String settingId, Long yearMonth);

	/**
	 * @param empId
	 * @param settingId
	 * @param yearMonth
	 * @return
	 */
	List<WorkAttendanceNoFullResult> noFullList(String empId, String settingId, Long yearMonth);

	/**
	 * @param empId
	 * @param settingId
	 * @param yearMonth
	 * @return
	 */
	List<WorkAttendanceAbsentResult> absentList(String empId, String settingId, Long yearMonth);

	/**
	 * @param empId
	 * @param settingId
	 * @param yearMonth
	 * @return
	 */
	List<WorkAttendanceLackResult> lackList(String empId, String settingId, Long yearMonth);

	/**
	 * 获取企业配置的加班请假，动态生成列
	 * @param sessionInfo
	 * @return
	 */
	AttendanceTotalApprovalTypeResult getApprovalType(SessionInfo sessionInfo);
}
