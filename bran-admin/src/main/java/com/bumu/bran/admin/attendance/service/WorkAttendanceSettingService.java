package com.bumu.bran.admin.attendance.service;

import com.bumu.bran.admin.attendance.result.WorkAttendanceDeviceResult;
import com.bumu.bran.admin.corporation.result.WorkShiftListResult;
import com.bumu.bran.attendance.command.setting.WorkAttendanceAddSettingCommand;
import com.bumu.bran.attendance.command.setting.WorkAttendanceDeviceWorkShiftCommand;
import com.bumu.bran.attendance.result.WorkAttendanceSettingPageResult;
import com.bumu.bran.attendance.result.WorkAttendanceSettingResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.ModelResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by DaiAoXiang on 2017/3/29.
 */
@Transactional
public interface WorkAttendanceSettingService {

	/**
	 * 查询当前公司的全部配置信息
	 *
	 * @param workShiftId
	 * @param sessionInfo
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	WorkAttendanceSettingPageResult getCorpAllSetting(String workShiftId, SessionInfo sessionInfo, int page, int pageSize) throws Exception;

	/**
	 * 查询班组的出勤配置信息
	 *
	 * @param workShiftId
	 * @param sessionInfo
	 * @return
	 * @throws Exception
	 */
	WorkAttendanceSettingResult  getSetting(String workShiftId, SessionInfo sessionInfo) throws Exception;

	/**
	 * 查询当前公司下的所有班组
	 *
	 * @param sessionInfo
	 * @return
	 * @throws Exception
	 */
	List<ModelResult> getAvailableWorkShiftList(SessionInfo sessionInfo) throws Exception;

	/**
	 * 考勤配置保存
	 *
	 * @param command
	 * @param sessionInfo
	 * @throws Exception
	 */
    void saveSetting(WorkAttendanceAddSettingCommand command,SessionInfo sessionInfo) throws Exception;

	/**
	 * 获取详细出勤配置
	 *
	 * @param id
	 * @return
	 */
	WorkAttendanceSettingResult getDetail(String id,SessionInfo sessionInfo);

	/**
	 * 删除出勤配置
	 *
	 * @param id
	 * @param sessionInfo
	 */
	void deleteSetting (String id, SessionInfo sessionInfo);

	/**
	 * 公司下所有的考勤机
	 * @param sessionInfo
	 * @return
	 */
	List<WorkAttendanceDeviceResult> deviceList(SessionInfo sessionInfo);

	/**
	 * 保存企业班组-考勤机
	 * @param command
	 * @param sessionInfo
	 */
	void deviceWorkShiftSave(WorkAttendanceDeviceWorkShiftCommand command, SessionInfo sessionInfo);

	/**
	 * 获取考勤机选择班组
	 * @param sessionInfo
	 * @return
	 */
	WorkShiftListResult getDeviceWorkShifts(SessionInfo sessionInfo) throws Exception;


}
