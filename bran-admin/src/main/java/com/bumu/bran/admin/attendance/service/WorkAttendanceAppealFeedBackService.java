package com.bumu.bran.admin.attendance.service;

import com.bumu.bran.attendance.command.WorkAttendanceAppealDealCommand;
import com.bumu.bran.attendance.result.WorkAttendanceAppealBackResult;
import com.bumu.common.SessionInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DaiAoXiang on 2017/5/11.
 */
@Transactional
public interface WorkAttendanceAppealFeedBackService {

	/**
	 * 查询申诉反馈列表
	 * @param workShiftId
	 * @param empName
	 * @param appealType
	 * @param dealState
	 * @param attendTime
	 * @param sessionInfo
	 * @return
	 * @throws Exception
	 */
	WorkAttendanceAppealBackResult getFeeBackList(String workShiftId,String empName,Integer appealType,Integer dealState,Long attendTime,Integer page,Integer pageSize,SessionInfo sessionInfo) throws Exception;

	/**
	 * 处理申诉状态
	 * @param command
	 * @param sessionInfo
	 * @throws Exception
	 */
	void dealAppealback(WorkAttendanceAppealDealCommand command, SessionInfo sessionInfo)throws Exception;
}
