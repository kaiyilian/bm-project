package com.bumu.bran.admin.attendance.service;

import com.bumu.attendance.result.WorkAttendanceDeviceManagerResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.command.WorkAttendanceDeviceManagerCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2017/3/21
 */
@Transactional
public interface WorkAttendanceDeviceManagerService {

	void add(WorkAttendanceDeviceManagerCommand.Add command, SessionInfo sessionInfo);

	void update(WorkAttendanceDeviceManagerCommand.Update command, SessionInfo sessionInfo);

	void batchDelete(BaseCommand.BatchIds command, SessionInfo sessionInfo);

	List<WorkAttendanceDeviceManagerResult> getList(SessionInfo sessionInfo);
}
