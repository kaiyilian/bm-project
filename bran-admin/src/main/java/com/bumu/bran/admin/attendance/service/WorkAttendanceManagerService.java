package com.bumu.bran.admin.attendance.service;

import com.bumu.bran.attendance.command.WorkAttendanceManagerCommand;
import com.bumu.bran.attendance.result.WorkAttendanceManagerResult;
import com.bumu.common.SessionInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DaiAoXiang on 2017/4/3.
 */
@Transactional
public interface WorkAttendanceManagerService {

	/**
	 * 获取绑定的管理员信息
	 * @return
	 */
	WorkAttendanceManagerResult getManagerInfo() throws Exception;

	/**
	 * 绑定管理员
	 *
	 * @param command
	 * @return
	 */
	WorkAttendanceManagerResult bindManager(WorkAttendanceManagerCommand command, SessionInfo sessionInfo) throws Exception;
}
