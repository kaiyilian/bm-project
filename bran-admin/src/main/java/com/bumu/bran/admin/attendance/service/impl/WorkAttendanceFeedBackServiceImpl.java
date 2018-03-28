package com.bumu.bran.admin.attendance.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.admin.attendance.service.WorkAttendanceFeedBackService;
import com.bumu.bran.attendance.command.WorkAttendanceFeedBackQueryCommand;
import com.bumu.bran.attendance.model.dao.AttendancesFeedBackDao;
import com.bumu.bran.attendance.result.WorkAttendanceFeedBackResult;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.LeaveEmployeeDao;
import com.bumu.bran.model.entity.attendance.WorkAttendanceFeedBackEntity;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.Pager;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.leave_emp.model.entity.LeaveEmployeeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by DaiAoXiang on 2017/4/4.
 */
@Service
public class WorkAttendanceFeedBackServiceImpl implements WorkAttendanceFeedBackService{

	private static Logger logger = LoggerFactory.getLogger(WorkAttendanceFeedBackServiceImpl.class);

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private AttendancesFeedBackDao attendancesFeedBackDao;

	@Autowired
	private LeaveEmployeeDao leaveEmployeeDao;

	@Override
	public WorkAttendanceFeedBackResult getFeeBackList (SessionInfo sessionInfo, WorkAttendanceFeedBackQueryCommand command) throws Exception{
		WorkAttendanceFeedBackResult result = new WorkAttendanceFeedBackResult();
		List<WorkAttendanceFeedBackResult.WorkAttendanceFeedBacks> workAttendanceFeedBackResults = new ArrayList<>();
		int day = (int)(command.getQueryEndDate()-command.getQueryStartDate())/(24*60*60*1000);
		if (day > 31){
			throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_ENDTIME_GREATERTHAN_STARTTIME);
		}
		Date queryStartDate = new Date(SysUtils.getOneDayStartTime(command.getQueryStartDate()));
		Date quaryEndData = new Date(SysUtils.getOneDayStartTime(command.getQueryEndDate()));

		List<EmployeeEntity> employeeEntities = employeeDao.findByWorkShiftIdOrName(sessionInfo.getCorpId(),command.getWorkShiftId(),command.getEmployeeName());
		List<String> leaveIds = new ArrayList<>();
		List<String> onJobIds = new ArrayList<>();
		List<String> ids = new ArrayList<>();
		if (employeeEntities.size() != 0){
			for (EmployeeEntity employeeEntity : employeeEntities){
				if (employeeEntity.getIsDelete() == 0){
					onJobIds.add(employeeEntity.getId());
				}else {
					leaveIds.add(employeeEntity.getId());
				}
				ids.add(employeeEntity.getId());
			}
		}else {
			return result;
		}

		Pager<WorkAttendanceFeedBackEntity> workAttendanceFeedBackEntityPager = attendancesFeedBackDao.findByQuaryDataAndKey(queryStartDate,quaryEndData,ids,command);
		if (workAttendanceFeedBackEntityPager.getResult().size() == 0) {
			return result;
		}
		for (WorkAttendanceFeedBackEntity workAttendanceFeedBackEntity : workAttendanceFeedBackEntityPager.getResult()){
			WorkAttendanceFeedBackResult.WorkAttendanceFeedBacks workAttendanceFeedBackResult = new WorkAttendanceFeedBackResult.WorkAttendanceFeedBacks();
			workAttendanceFeedBackResult.setId(workAttendanceFeedBackEntity.getId());
			workAttendanceFeedBackResult.setStarTime(String.valueOf(workAttendanceFeedBackEntity.getStartTime()));
			workAttendanceFeedBackResult.setEndTime(String.valueOf(workAttendanceFeedBackEntity.getEndTime()));
			workAttendanceFeedBackResult.setState(workAttendanceFeedBackEntity.getState().ordinal());
			int isOnjob = Arrays.binarySearch(onJobIds.toArray(), workAttendanceFeedBackEntity.getEmpId());
			if (isOnjob >= 0){
				EmployeeEntity employeeEntity = employeeDao.findEmployeeById(workAttendanceFeedBackEntity.getEmpId());
				if (employeeEntity != null){
					workAttendanceFeedBackResult.setWorkShiftName(employeeEntity.getWorkShiftName());
					workAttendanceFeedBackResult.setName(employeeEntity.getRealName());
					workAttendanceFeedBackResult.setWorkAttendanceNo(employeeEntity.getWorkAttendanceNo());
				}
			}else {
				LeaveEmployeeEntity leaveEmployeeEntity = leaveEmployeeDao.findLeaveEmployeeByEmpId(workAttendanceFeedBackEntity.getEmpId());
				if (leaveEmployeeEntity != null){
					workAttendanceFeedBackResult.setWorkShiftName(leaveEmployeeEntity.getWorkShiftName());
					workAttendanceFeedBackResult.setName(leaveEmployeeEntity.getRealName());
					workAttendanceFeedBackResult.setWorkAttendanceNo(leaveEmployeeEntity.getWorkAttendanceNo());
				}
			}
			workAttendanceFeedBackResults.add(workAttendanceFeedBackResult);
		}
		result.setWorkAttendanceFeedBackses(workAttendanceFeedBackResults);
		result.setPages(Utils.calculatePages(workAttendanceFeedBackEntityPager.getRowCount(), workAttendanceFeedBackEntityPager.getPageSize()));
		return result;
	}

	@Override
	public void  reconfirmFeedBack(String feeBackId,SessionInfo sessionInfo) throws Exception{
		if (feeBackId == null){
			throw new AryaServiceException(ErrorCode.CODE_VALIDATION_ID_BLANK);
		}
		WorkAttendanceFeedBackEntity workAttendanceFeedBackEntity = attendancesFeedBackDao.findById(feeBackId);
		if (workAttendanceFeedBackEntity != null && workAttendanceFeedBackEntity.getState() == WorkAttendanceFeedBackEntity.State.Appeals){
			workAttendanceFeedBackEntity.setState(WorkAttendanceFeedBackEntity.State.unconfirmed);
			workAttendanceFeedBackEntity.setUpdateUser(sessionInfo.getUserId());
			workAttendanceFeedBackEntity.setUpdateTime(System.currentTimeMillis());
		}
		try {
			attendancesFeedBackDao.update(workAttendanceFeedBackEntity);
		}catch (Exception e){
			throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_FEE_BACK_RECONFIRM);
		}
	}
}
