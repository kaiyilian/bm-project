package com.bumu.bran.admin.attendance.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysLogDao;
import com.bumu.bran.admin.attendance.service.WorkAttendanceManagerService;
import com.bumu.bran.attendance.command.WorkAttendanceManagerCommand;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.bran.attendance.model.dao.AttendancesManagerDao;
import com.bumu.bran.model.entity.attendance.WorkAttendanceManagerEntity;
import com.bumu.bran.attendance.result.WorkAttendanceManagerResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.util.StringUtil;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DaiAoXiang on 2017/4/3.
 */
@Service
public class WorkAttendanceManagerServiceImpl implements WorkAttendanceManagerService {

	private static Logger logger = LoggerFactory.getLogger(WorkAttendanceManagerService.class);

	@Autowired
	AttendancesManagerDao attendancesManagerDao;

	@Autowired
	BranOpLogDao branOpLogDao;

	@Override
	public WorkAttendanceManagerResult getManagerInfo() throws Exception {
		WorkAttendanceManagerResult workAttendanceManagerResult = new WorkAttendanceManagerResult();
		List<WorkAttendanceManagerEntity> workAttendanceManagerEntities = attendancesManagerDao.findAllNotDelete();
		if (workAttendanceManagerEntities.size() == 0) {
			return workAttendanceManagerResult;
		}
		WorkAttendanceManagerEntity workAttendanceManagerEntity = workAttendanceManagerEntities.get(0);
		workAttendanceManagerResult.setId(workAttendanceManagerEntity.getId());
		workAttendanceManagerResult.setName(workAttendanceManagerEntity.getName());
		workAttendanceManagerResult.setTel(workAttendanceManagerEntity.getTel());

		return workAttendanceManagerResult;
	}

	@Override
	public WorkAttendanceManagerResult bindManager(WorkAttendanceManagerCommand command, SessionInfo sessionInfo) throws Exception {
		WorkAttendanceManagerEntity workAttendanceManagerEntity = new WorkAttendanceManagerEntity();
		WorkAttendanceManagerResult workAttendanceManagerResult = new WorkAttendanceManagerResult();
		SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
		if (command.getName().length() > 32) {
			throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_MANAGER_NAME_LENGTH_TOOLONG);//联系人名称过长
		}
		if (!StringUtil.isMobileNumber(command.getTel())) {
			throw new AryaServiceException(ErrorCode.CODE_VALID_PHONENO_WRONG);
		}
		if (!StringUtils.isAnyBlank(command.getId())) {
			workAttendanceManagerEntity = attendancesManagerDao.findManageById(command.getId());
			if (workAttendanceManagerEntity == null) {
				throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_MANAGER_BIND_FILED);
			}
		}
		workAttendanceManagerEntity.setName(command.getName());
		workAttendanceManagerEntity.setTel(command.getTel());
		if (StringUtils.isAnyBlank(command.getId())) {
			logger.info("绑定管理员" + command.getName());
			info.setMsg("绑定管理员" + command.getName());
			workAttendanceManagerEntity.setId(Utils.makeUUID());
			workAttendanceManagerEntity.setCreateTime(System.currentTimeMillis());
			workAttendanceManagerEntity.setCreateUser(sessionInfo.getUserId());
			workAttendanceManagerEntity.setBranCorpId(sessionInfo.getCorpId());
			try {
				attendancesManagerDao.create(workAttendanceManagerEntity);
				//branOpLogDao.success(BranOpLogEntity.OP_MODULE_BIND_WORK_ATTENDANCE_MANAGER, BranOpLogEntity.OP_TYPE_ADD, sessionInfo.getUserId(), info);
			} catch (Exception e) {
				throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_MANAGER_BIND_FILED);
			}
		} else {
			logger.info("更新管理员" + command.getName());
			info.setMsg("更新管理员" + command.getName());
			workAttendanceManagerEntity.setUpdateUser(sessionInfo.getUserId());
			workAttendanceManagerEntity.setUpdateTime(System.currentTimeMillis());
			try {
				attendancesManagerDao.update(workAttendanceManagerEntity);
				//branOpLogDao.success(BranOpLogEntity.OP_MODULE_BIND_WORK_ATTENDANCE_MANAGER, BranOpLogEntity.OP_TYPE_UPDATE, sessionInfo.getUserId(), info);
			} catch (Exception e) {
				throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_MANAGER_BIND_FILED);
			}
		}
		workAttendanceManagerResult.setId(workAttendanceManagerEntity.getId());
		workAttendanceManagerResult.setName(workAttendanceManagerEntity.getName());
		workAttendanceManagerResult.setTel(workAttendanceManagerEntity.getTel());
		return workAttendanceManagerResult;
	}
}
