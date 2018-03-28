package com.bumu.bran.admin.attendance.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.admin.attendance.service.WorkAttendanceAppealFeedBackService;
import com.bumu.bran.attendance.command.WorkAttendanceAppealDealCommand;
import com.bumu.bran.attendance.model.dao.AttendanceAppealFeedBackDao;
import com.bumu.attendance.constant.WorkAttendanceEnum;
import com.bumu.bran.model.entity.attendance.WorkAttendanceAppealFeedBackEntity;
import com.bumu.bran.attendance.result.WorkAttendanceAppealBackResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.Pager;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DaiAoXiang on 2017/5/11.
 */
@Service
public class WorkAttendanceAppealFeedBackServiceImpl implements WorkAttendanceAppealFeedBackService{

	Logger log =LoggerFactory.getLogger(WorkAttendanceAppealFeedBackServiceImpl.class);
	@Autowired
	AttendanceAppealFeedBackDao attendanceAppealFeedBackDao;

	@Override
	public WorkAttendanceAppealBackResult getFeeBackList(String workShiftId,String empName,Integer appealType,Integer dealState,Long attendTime,Integer page, Integer pageSize,SessionInfo sessionInfo) throws Exception{
		WorkAttendanceAppealBackResult workAttendanceAppealBackResult = new WorkAttendanceAppealBackResult();
		List<WorkAttendanceAppealBackResult.AppealFeedBack> appealFeedBacks = new ArrayList<>();
		Pager<WorkAttendanceAppealFeedBackEntity> workAttendanceAppealFeedBackEntityPager = attendanceAppealFeedBackDao.findFeedBackEntityByKey(workShiftId,empName,appealType,dealState,attendTime,page,pageSize,sessionInfo);
		if (workAttendanceAppealFeedBackEntityPager.getResult().size() == 0){
			return workAttendanceAppealBackResult;
		}
		for (WorkAttendanceAppealFeedBackEntity workAttendanceAppealFeedBackEntity : workAttendanceAppealFeedBackEntityPager.getResult()){
			WorkAttendanceAppealBackResult.AppealFeedBack appealFeedBack = new WorkAttendanceAppealBackResult.AppealFeedBack();
			appealFeedBack.setId(workAttendanceAppealFeedBackEntity.getId());
			appealFeedBack.setAppealTime(workAttendanceAppealFeedBackEntity.getAppealTime().getTime());
			appealFeedBack.setAppealType(workAttendanceAppealFeedBackEntity.getAppealType().ordinal());
			appealFeedBack.setDealState(workAttendanceAppealFeedBackEntity.getDealState().ordinal());
			appealFeedBack.setShiftName(workAttendanceAppealFeedBackEntity.getWorkShiftName());
			appealFeedBack.setEmpName(workAttendanceAppealFeedBackEntity.getEmpName());
			if (workAttendanceAppealFeedBackEntity.getAttendTime() == null){
				appealFeedBack.setClockInTime(null);
			}else {
				appealFeedBack.setClockInTime(workAttendanceAppealFeedBackEntity.getAttendTime().getTime());
			}
			appealFeedBack.setFeedBackReason(workAttendanceAppealFeedBackEntity.getReason());
			appealFeedBacks.add(appealFeedBack);
		}
		workAttendanceAppealBackResult.setAppealFeedBacks(appealFeedBacks);
		workAttendanceAppealBackResult.setPages(Utils.calculatePages(workAttendanceAppealFeedBackEntityPager.getRowCount(), workAttendanceAppealFeedBackEntityPager.getPageSize()));
		return workAttendanceAppealBackResult;
	}

	@Override
	public void dealAppealback(WorkAttendanceAppealDealCommand command, SessionInfo sessionInfo)throws Exception{
		if (StringUtils.isAnyBlank(command.getId())){
			throw new AryaServiceException(ErrorCode.CODE_CODE_WORK_ATTENDANCE_APPEAL_FEED_BACK_ID_NOT_NULL);
		}
		log.info("处理申述反馈的id----> "+ command.getId());
		WorkAttendanceAppealFeedBackEntity workAttendanceAppealFeedBackEntity = attendanceAppealFeedBackDao.findFeedBackEntityById(command.getId());
		if (workAttendanceAppealFeedBackEntity != null){
			if (command.getDealState() == null){
				log.info("处理申述反馈结果为空  ----> ");

				workAttendanceAppealFeedBackEntity.setDealState(WorkAttendanceEnum.DealState.values()[0]);
			}
			log.info("处理申述反馈的id----> "+ command.getDealState());

			workAttendanceAppealFeedBackEntity.setDealState(WorkAttendanceEnum.DealState.values()[command.getDealState()]);
		}
		try {
			attendanceAppealFeedBackDao.update(workAttendanceAppealFeedBackEntity);
		}catch (Exception e){
			throw new AryaServiceException(ErrorCode.CODE_CODE_WORK_ATTENDANCE_APPEAL_FEED_BACK_DEAL_FAILED);
		}
	}
}
