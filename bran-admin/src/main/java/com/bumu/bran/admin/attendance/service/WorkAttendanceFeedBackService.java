package com.bumu.bran.admin.attendance.service;

import com.bumu.bran.attendance.command.WorkAttendanceFeedBackQueryCommand;
import com.bumu.bran.attendance.result.WorkAttendanceFeedBackResult;
import com.bumu.common.SessionInfo;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by DaiAoXiang on 2017/4/4.
 */
@Transactional
public interface WorkAttendanceFeedBackService {

	/**
	 * 查询考勤反馈
	 * @param command
	 * @return
	 */
	WorkAttendanceFeedBackResult getFeeBackList (SessionInfo sessionInfo, WorkAttendanceFeedBackQueryCommand command) throws Exception;

	/**
	 * 考勤反馈重新确认
	 * @param feeBackId
	 * @throws Exception
	 */
	void  reconfirmFeedBack(String feeBackId,SessionInfo sessionInfo) throws Exception;
}
