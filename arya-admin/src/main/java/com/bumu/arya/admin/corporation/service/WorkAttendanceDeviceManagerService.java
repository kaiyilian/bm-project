package com.bumu.arya.admin.corporation.service;

import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.command.WorkAttendanceDeviceManagerCommand;
import com.bumu.common.result.BaseResult;
import com.bumu.attendance.result.WorkAttendanceDeviceManagerResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2017/3/21
 */
@Transactional
public interface WorkAttendanceDeviceManagerService {

	BaseResult.IDResult add(WorkAttendanceDeviceManagerCommand.Add command, SessionInfo sessionInfo);

	BaseResult.IDResult update(WorkAttendanceDeviceManagerCommand.Update command, SessionInfo sessionInfo);

	void batchDelete(BaseCommand.BatchIds command, SessionInfo sessionInfo);

	List<WorkAttendanceDeviceManagerResult> getList(SessionInfo sessionInfo);
}
