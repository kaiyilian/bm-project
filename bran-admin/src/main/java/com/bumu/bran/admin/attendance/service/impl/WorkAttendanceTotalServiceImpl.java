package com.bumu.bran.admin.attendance.service.impl;

import com.bumu.SysUtils;
import com.bumu.approval.result.ApprovalTypeSettingResult;
import com.bumu.arya.Utils;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysLogDao;
import com.bumu.arya.model.entity.WorkShiftTypeEntity;
import com.bumu.bran.admin.approval.service.ApprovalTypeSettingService;
import com.bumu.bran.admin.attendance.AttendanceConstants;
import com.bumu.bran.admin.attendance.result.WorkAttendanceToTalCalculateResult;
import com.bumu.bran.admin.attendance.service.WorkAttendanceTotalService;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.approval.result.ApprovalType;
import com.bumu.bran.attendance.command.WorkAttendanceTotalPublishCommand;
import com.bumu.bran.attendance.command.WorkAttendanceTotalQueryCommand;
import com.bumu.bran.attendance.model.dao.*;
import com.bumu.bran.attendance.model.dao.mybatis.WorkAttendanceMybatisDao;
import com.bumu.bran.attendance.result.*;
import com.bumu.bran.attendance.service.WorkAttendanceRangeService;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.LeaveEmployeeDao;
import com.bumu.attendance.constant.WorkAttendanceEnum;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.bran.model.entity.WorkShiftEntity;
import com.bumu.bran.model.entity.attendance.*;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeDao;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.Pager;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.leave_emp.model.entity.LeaveEmployeeEntity;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by DaiAoXiang on 2017/4/7.
 */
@Service
public class WorkAttendanceTotalServiceImpl implements WorkAttendanceTotalService {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceTotalServiceImpl.class);
    @Autowired
    AttendanceSettingDao attendanceSettingDao;
    @Autowired
    WorkShiftDao workShiftDao;
    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    AttendceDetailDao attendceDetailDao;
    @Autowired
    AttendancesMonthDao attendancesMonthDao;
    @Autowired
    AttendancesFeedBackDao attendancesFeedBackDao;
    @Value(value = "${bran.admin.workattendance.company.id}")
    private String workAttendanceCompanyId;
    @Autowired
    private BranOpLogDao branOpLogDao;
    @Autowired
    private ExcelExportHelper excelExportHelper;
    @Autowired
    private BranAdminConfigService configService;
    @Autowired
    private WorkAttendanceRangeService workAttendanceRangeService;
    @Autowired
    private LeaveEmployeeDao leaveEmployeeDao;
    @Autowired
    private WorkAttendanceLeaveDao workAttendanceLeaveDao;
    @Autowired
    private WorkAttendanceOvertimeDao workAttendanceOvertimeDao;
    @Autowired
    private WorkAttendanceDao workAttendanceDao;

    @Autowired
    private BranWorkShiftTypeDao branWorkShiftTypeDao;

    @Autowired
    private ApprovalTypeSettingService approvalTypeSettingService;

    @Autowired
    private WorkAttendanceMybatisDao workAttendanceMybatisDao;

    @Override
    public WorkAttendanceTotalGetSettingResult getSettingList(SessionInfo sessionInfo) throws Exception {
        WorkAttendanceTotalGetSettingResult workAttendanceTotalGetSettingResult = new WorkAttendanceTotalGetSettingResult();
        List<WorkAttendanceTotalGetSettingResult.settingList> settingLists = new ArrayList<>();

        List<WorkAttendanceSettingEntity> workAttendanceSettingEntities = attendanceSettingDao.findAttendanceSetting(sessionInfo.getCorpId());
        if (ListUtils.checkNullOrEmpty(workAttendanceSettingEntities)) {
            return workAttendanceTotalGetSettingResult;
        }
        for (WorkAttendanceSettingEntity workAttendanceSettingEntity : workAttendanceSettingEntities) {
            WorkAttendanceTotalGetSettingResult.settingList settingList = new WorkAttendanceTotalGetSettingResult.settingList();
            settingList.setId(workAttendanceSettingEntity.getId());
            settingList.setCycleStart(workAttendanceSettingEntity.getCycleStart());
            settingList.setCycleEnd(workAttendanceSettingEntity.getCycleEnd());
            settingList.setIsNextMonth(workAttendanceSettingEntity.getIsNextMonth());
            settingLists.add(settingList);
        }
        workAttendanceTotalGetSettingResult.setSettingLists(settingLists);
        return workAttendanceTotalGetSettingResult;
    }


    @Override
    public NewWorkAttendanceTotalResult list(WorkAttendanceTotalQueryCommand command, SessionInfo sessionInfo) throws Exception {
        return null;
    }

    @Override
    public WorkAttendanceTotalResult getTotalPageList(WorkAttendanceTotalQueryCommand command, SessionInfo sessionInfo) throws Exception {
        WorkAttendanceTotalResult result = new WorkAttendanceTotalResult();
        if (command.getIsOnJob() == null) {
            command.setIsOnJob(1);
        }
        //查询公司是否开通手机打卡
        int isOpenPhoneClock = workAttendanceRangeService.isOpenPhoneClock(sessionInfo.getCorpId());

        if (StringUtils.isAnyBlank(command.getSettingId())) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_CYCLE_IS_NULL);
        }
        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(command.getSettingId());
        if (settingEntity == null) {
            return null;
        }
        //查询月份开始时间和结束时间
        Long starDate = transformStartTime(command.getQueryDate(), settingEntity.getCycleStart());
        Long endDate = transformEndTime(command.getQueryDate(), settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String attendanceStarDate = formatter.format(starDate);
        String attendanceEndDate = formatter.format(endDate);
        Date queryStartDate = new Date(SysUtils.getOneDayStartTime(starDate));
        Date queryEndData = new Date(SysUtils.getOneDayStartTime(endDate));

        WorkAttendanceSettingEntity workAttendanceSettingEntity = attendanceSettingDao.findByCorpIdAndWorkAttendanceTime(sessionInfo.getCorpId(), settingEntity.getCycleStart(), settingEntity.getCycleEnd());
        logger.debug("配置id:" + workAttendanceSettingEntity.getId());
        if (workAttendanceSettingEntity == null) {
            return null;
        }
        //获取企业配置的加班请假-动态列
        AttendanceTotalApprovalTypeResult approvalTypeResult = getApprovalType(sessionInfo);
        //周期下所有的班组
        List<String> workShiftIds = new ArrayList<>();
        //查询条件的班组ID
        List<String> workShiftId = new ArrayList<>();
        //通过branId和setId查询考勤周期范围内班组List
        List<WorkShiftEntity> workShiftEntities = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(workAttendanceSettingEntity.getId(), sessionInfo.getCorpId());
        for (WorkShiftEntity workShiftEntity : workShiftEntities) {
            logger.debug("班组Id：" + workShiftEntity.getId() + "班组名字" + workShiftEntity.getShiftName());
            if (command.getWorkShiftId() != null) {
                if (workShiftEntity.getId().equals(command.getWorkShiftId())) {
                    workShiftId.add(command.getWorkShiftId());
                }
            }
            workShiftIds.add(workShiftEntity.getId());
        }

        //查询班组。时间范围内的排班

        //选择员工 + 确认发布状态下的人员ID
        List<String> empIds = new ArrayList<>();
        //如果发布状态、确认状态不为空，则需要过滤一部分员工
        if ((null != command.getIsPublish() && command.getIsPublish() == Constants.FALSE) && null != command.getSureState()) {
            return null;
        }
        if ((null != command.getIsPublish() && command.getIsPublish() == Constants.TRUE) || null != command.getSureState()) {
            //根据确认状态查询
            List<WorkAttendanceFeedBackEntity> feedBackEntityList = attendancesFeedBackDao.findListByState(queryStartDate, queryEndData, command.getIsPublish(), command.getSureState());
            Set<String> empSet = new HashSet<>();
            for (WorkAttendanceFeedBackEntity entity : feedBackEntityList) {
                empSet.add(entity.getEmpId());
            }
            if (empSet.isEmpty()) {
                return null;
            }
            empIds.addAll(empSet);
        }

        //员工
        Pager<EmployeeEntity> employeeEntities = null;
        //离职员工
        Pager<LeaveEmployeeEntity> leaveEmployeeEntities = null;
        //如果没有选择班组，则按使用此考勤周期的班组查询
        if (StringUtils.isAnyBlank(command.getWorkShiftId())) {
            // 周期下没有班组 则直接返回
            if (CollectionUtils.isEmpty(workShiftIds)) {
                return null;
            }
            if (command.getIsOnJob() == 0) {
                leaveEmployeeEntities = leaveEmployeeDao.findAttendanceTotalList(workShiftIds, command.getEmpId(), empIds, command.getIsPublish(), command.getDepartmentId(), command.getPage(), command.getPage_size());
            } else {
                employeeEntities = employeeDao.findByWorkShiftId(workShiftIds, command.getEmpId(), empIds, isOpenPhoneClock, command.getIsPublish(), command.getDepartmentId(), command.getPage(), command.getPage_size());
            }
        } else {
            //如果选择了班组，且此班组不在考勤周期班组中，直接返回空
            if (CollectionUtils.isEmpty(workShiftId)) {
                return null;
            } else { //如果选择了班组，且此班组在考勤周期班组中，则按此班组查询
                if (command.getIsOnJob() == 0) {
                    leaveEmployeeEntities = leaveEmployeeDao.findAttendanceTotalList(workShiftId, command.getEmpId(), empIds, command.getIsPublish(), command.getDepartmentId(), command.getPage(), command.getPage_size());
                } else {
                    employeeEntities = employeeDao.findByWorkShiftId(workShiftId, command.getEmpId(), empIds, isOpenPhoneClock, command.getIsPublish(), command.getDepartmentId(), command.getPage(), command.getPage_size());
                }
            }
        }
        if (command.getIsOnJob() == 0) {
            if (leaveEmployeeEntities.getResult().size() == 0) {
                return result;
            }
        } else {
            if (employeeEntities.getResult().size() == 0) {
                return result;
            }
        }
        //班组排班
        Map<String, Integer> workShiftWorkDays = workShiftCntMap(attendanceStarDate, attendanceEndDate, workShiftIds);

        List<String> empCntIds = new ArrayList<>();
        if (null != employeeEntities) {
            employeeEntities.getResult().stream().forEach(entity -> empCntIds.add(entity.getId()));
        }
        if (null != leaveEmployeeEntities) {
            leaveEmployeeEntities.getResult().stream().forEach(entity -> empCntIds.add(entity.getEmpId()));
        }
        //个人修改的排班
        Map<String, Integer> empWorkDays = empCntMap(attendanceStarDate, attendanceEndDate, empCntIds, sessionInfo.getCorpId());

        List<WorkAttendanceTotalResult.WorkAttendanceTotals> workAttendanceTotalses = new ArrayList<>();
        if (command.getIsOnJob() == 0) {
            for (LeaveEmployeeEntity leaveEmployeeEntity : leaveEmployeeEntities.getResult()) {
                WorkAttendanceTotalResult.WorkAttendanceTotals workAttendanceTotalResult = totalResult(leaveEmployeeEntity.getEmpId(), queryStartDate, queryEndData, approvalTypeResult, sessionInfo);
                workAttendanceTotalResult.setId(leaveEmployeeEntity.getEmpId());
                workAttendanceTotalResult.setName(leaveEmployeeEntity.getRealName());
                workAttendanceTotalResult.setWorkShiftName(leaveEmployeeEntity.getWorkShiftName());
                workAttendanceTotalResult.setWorkAttendanceNo(leaveEmployeeEntity.getWorkAttendanceNo());
                workAttendanceTotalResult.setAttendanceDate(attendanceStarDate + "~" + attendanceEndDate);
                workAttendanceTotalResult.setDepartmentName(leaveEmployeeEntity.getDepartmentName());
                workAttendanceTotalResult.setAttendanceDays(
                        (null == workShiftWorkDays.get(leaveEmployeeEntity.getWorkShiftId()) ? 0 : workShiftWorkDays.get(leaveEmployeeEntity.getWorkShiftId()) -
                        (null == empWorkDays.get(leaveEmployeeEntity.getEmpId()) ? 0 : empWorkDays.get(leaveEmployeeEntity.getEmpId()))));
                checkState(queryStartDate, queryEndData, leaveEmployeeEntity.getEmpId(), workAttendanceTotalResult);
                workAttendanceTotalses.add(workAttendanceTotalResult);
            }
        } else {
            for (EmployeeEntity employeeEntity : employeeEntities.getResult()) {
                WorkAttendanceTotalResult.WorkAttendanceTotals  workAttendanceTotalResult = totalResult(employeeEntity.getId(), queryStartDate, queryEndData, approvalTypeResult, sessionInfo);
                workAttendanceTotalResult.setId(employeeEntity.getId());
                workAttendanceTotalResult.setName(employeeEntity.getRealName());
                workAttendanceTotalResult.setWorkShiftName(employeeEntity.getWorkShiftName());
                workAttendanceTotalResult.setWorkAttendanceNo(employeeEntity.getWorkAttendanceNo());
                workAttendanceTotalResult.setAttendanceDate(attendanceStarDate + "~" + attendanceEndDate);
                workAttendanceTotalResult.setDepartmentName(employeeEntity.getDepartmentName());
                workAttendanceTotalResult.setAttendanceDays(
                        (null == workShiftWorkDays.get(employeeEntity.getWorkShiftId()) ? 0 : workShiftWorkDays.get(employeeEntity.getWorkShiftId()) -
                                (null == empWorkDays.get(employeeEntity.getId()) ? 0 : empWorkDays.get(employeeEntity.getId()))));
                checkState(queryStartDate, queryEndData, employeeEntity.getId(), workAttendanceTotalResult);
                workAttendanceTotalses.add(workAttendanceTotalResult);
            }
        }
        result.setWorkAttendanceTotalses(workAttendanceTotalses);
        if (command.getIsOnJob() == 0) {
            result.setPages(Utils.calculatePages(leaveEmployeeEntities.getRowCount(), leaveEmployeeEntities.getPageSize()));
            result.setTotal(leaveEmployeeEntities.getRowCount());
        } else {
            result.setPages(Utils.calculatePages(employeeEntities.getRowCount(), employeeEntities.getPageSize()));
            result.setTotal(employeeEntities.getRowCount());
        }
        result.setDataJson(new Gson().toJson(result.getWorkAttendanceTotalses()));
        return result;
    }

    private Map<String, Integer> workShiftCntMap(String attendanceStarDate, String attendanceEndDate, List<String> workShiftIds) {
        //计算所有的班组
        Map<String, Object> workShiftParams = new HashMap<>();
        workShiftParams.put("startString", attendanceStarDate);
        workShiftParams.put("endString", attendanceEndDate);
        workShiftParams.put("workShiftIds", workShiftIds);
        //key 班组id value 班组排班工作天数
        List<WorkShiftDaysCountResult> workShiftCountList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(workShiftIds)) {
            workShiftCountList = workAttendanceMybatisDao.countWorkShiftWorkDays(workShiftParams);
        }
        Map<String, Integer> workShiftWorkDays = new HashMap<>();
        Map<String, Map<String, Integer>> workShiftWorkCntMap = new HashMap<>();
        workShiftCountList.stream().forEach(tmp -> {
            if (null == workShiftWorkCntMap.get(tmp.getWorkShiftId())) {
                Map<String, Integer> tmpMap = new HashMap();
                tmpMap.put(tmp.getRuleId(), tmp.getWorkAttendanceDays() - tmp.getWorkRestDays());
                workShiftWorkCntMap.put(tmp.getWorkShiftId(), tmpMap);
            } else {
                if (null == workShiftWorkCntMap.get(tmp.getWorkShiftId()).get(tmp.getRuleId())) {
                    workShiftWorkCntMap.get(tmp.getWorkShiftId()).put(tmp.getRuleId(), tmp.getWorkAttendanceDays() - tmp.getWorkRestDays());
                } else {
                    workShiftWorkCntMap.get(tmp.getWorkShiftId()).put(tmp.getRuleId(),
                            workShiftWorkCntMap.get(tmp.getWorkShiftId()).get(tmp.getRuleId()) - tmp.getWorkRestDays());
                }
            }
        });
        workShiftWorkCntMap.entrySet().stream().forEach(entry -> {
            Integer workDays = 0;
            Iterator<Map.Entry<String, Integer>> i = entry.getValue().entrySet().iterator();
            while(i.hasNext()) {
                workDays += i.next().getValue();
            }
            workShiftWorkDays.put(entry.getKey(), workDays);
        });
        return workShiftWorkDays;
    }

    private Map<String, Integer> empCntMap(String attendanceStarDate, String attendanceEndDate, List<String> empCntIds, String branCorpId) {
        Map<String, Object> empParams = new HashMap<>();
        empParams.put("startString", attendanceStarDate);
        empParams.put("endString", attendanceEndDate);
        empParams.put("empIds", empCntIds);
        empParams.put("branCorpId", branCorpId);
        List<EmpDaysCountResult> empDaysCountResultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(empCntIds)) {
            empDaysCountResultList = workAttendanceMybatisDao.countEmpRestDays(empParams);
        }
        Map<String, Map<String, Integer>> empWorkDaysCheck = new HashMap<>();
        empDaysCountResultList.stream().forEach(tmp -> {
            if (null == empWorkDaysCheck.get(tmp.getEmpId())) {
                Map<String, Integer> tmpMap = new HashMap();
                tmpMap.put(tmp.getModifyDate(), tmp.getIsRestDay());
                empWorkDaysCheck.put(tmp.getEmpId(), tmpMap);
            } else if (null == empWorkDaysCheck.get(tmp.getEmpId()).get(tmp.getModifyDate()) || null == tmp.getFromAttend() ||tmp.getFromAttend() == Constants.TRUE) {
                empWorkDaysCheck.get(tmp.getEmpId()).put(tmp.getModifyDate(), tmp.getIsRestDay());
            }
        });
        logger.info(empWorkDaysCheck.toString());

        Map<String, Integer> empWorkDays = new HashMap<>();
        empWorkDaysCheck.entrySet().stream().forEach( entry -> {
            Iterator i = entry.getValue().values().iterator();
            Integer restDays = 0;
            while (i.hasNext()) {
                restDays += (Integer) i.next();
            }
            logger.info("员工:" + entry.getKey(), "休息日为:" + restDays);
            empWorkDays.put(entry.getKey(), restDays);
        });
        return empWorkDays;
    }

    private void checkState(Date startDate, Date endDate, String empId, WorkAttendanceTotalResult.WorkAttendanceTotals result) {
        List<WorkAttendanceFeedBackEntity> feedBackEntityList = attendancesFeedBackDao.findListByQueryDataAndId(startDate, endDate, empId);
        if (CollectionUtils.isEmpty(feedBackEntityList)) {
            result.setIsPublish(Constants.FALSE);
            result.setSureState(null);
        } else {
            result.setIsPublish(Constants.TRUE);
            Boolean isAppeals = false;
            for (WorkAttendanceFeedBackEntity entity : feedBackEntityList) {
                if (entity.getState() == WorkAttendanceFeedBackEntity.State.confirmed) {
                    result.setSureState(WorkAttendanceFeedBackEntity.State.confirmed);
                    return;
                }
                if (entity.getState() == WorkAttendanceFeedBackEntity.State.Appeals) {
                    isAppeals = true;
                }
            }
            result.setSureState(isAppeals ? WorkAttendanceFeedBackEntity.State.Appeals : WorkAttendanceFeedBackEntity.State.unconfirmed);
        }
    }


    /**
     * 汇总统计
     * @param empId 员工ID
     * @param queryStart 查询开始时间
     * @param queryEnd 查询结束时间
     * @param sessionInfo
     * @return
     * @throws Exception
     */
    public WorkAttendanceTotalResult.WorkAttendanceTotals totalResult(String empId, Date queryStart, Date queryEnd, AttendanceTotalApprovalTypeResult approvalTypeResult, SessionInfo sessionInfo) throws Exception {
        WorkAttendanceToTalCalculateResult workAttendanceToTalCalculateResult;
        workAttendanceToTalCalculateResult = calculateWorkAttendance(empId, queryStart, queryEnd, approvalTypeResult);
        WorkAttendanceTotalResult.WorkAttendanceTotals workAttendanceTotalResult = new WorkAttendanceTotalResult.WorkAttendanceTotals();
        workAttendanceTotalResult.setLackCount(workAttendanceToTalCalculateResult.getLackCount());
        workAttendanceTotalResult.setActualAttendanceDays(workAttendanceToTalCalculateResult.getActualAttendanceDays());
        workAttendanceTotalResult.setLateCount(workAttendanceToTalCalculateResult.getLateCount());
        workAttendanceTotalResult.setLateHours(workAttendanceToTalCalculateResult.getLateHours());
        workAttendanceTotalResult.setAttendanceHours(workAttendanceToTalCalculateResult.getAttendanceHours());
        workAttendanceTotalResult.setNormalHours(workAttendanceToTalCalculateResult.getNormalHours());
        workAttendanceTotalResult.setLeaveHours(workAttendanceToTalCalculateResult.getLeaveHours());
        workAttendanceTotalResult.setOverTimeHours(workAttendanceToTalCalculateResult.getOverTimeHours());
        workAttendanceTotalResult.setRestCount(workAttendanceToTalCalculateResult.getRestCount());
        workAttendanceTotalResult.setUnFullCount(workAttendanceToTalCalculateResult.getUnFullCount());
        workAttendanceTotalResult.setUnFullHours(workAttendanceToTalCalculateResult.getUnFullHours());
        workAttendanceTotalResult.setClockType(workAttendanceToTalCalculateResult.getClockType());
        workAttendanceTotalResult.setLeaveTimes(workAttendanceToTalCalculateResult.getLeaveTimes());
        workAttendanceTotalResult.setAbsentTimes(workAttendanceToTalCalculateResult.getAbsentTimes());
        workAttendanceTotalResult.setAbsentDays(workAttendanceToTalCalculateResult.getAbsentDays());
        workAttendanceTotalResult.setActualAttendanceDays(workAttendanceToTalCalculateResult.getActualAttendanceDays());
        workAttendanceToTalCalculateResult.getApprovalType().forEach((k,v) -> workAttendanceTotalResult.getExtra().put(k.toString(), String.valueOf(v)));
        return workAttendanceTotalResult;
    }


	/*@Override
	public void publish(WorkAttendanceTotalQueryCommand command, SessionInfo sessionInfo) throws Exception {
		SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
//		List<WorkAttendanceManagerEntity> workAttendanceManagerEntities = attendancesManagerDao.findAllNotDelete();
//		if (ListUtils.checkNullOrEmpty(workAttendanceManagerEntities)) {
//			throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_PUBLISH_FAILED_NOT_MANAGER);
//		}

		WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(command.getSettingId());
		if (settingEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_FIND_FAILED);
		}
		Long starDate = command.getQueryDate() + (settingEntity.getCycleStart() - 1) * 24 * 60 * 60 * 1000L;
		Long endDate = transformEndTime(command.getQueryDate(), settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

		Date queryStartDate = new Date(SysUtils.getOneDayStartTime(starDate));
		Date queryEndDate = new Date(SysUtils.getOneDayStartTime(endDate));

		List<String> ids = new ArrayList<>();
		if (command.getIsOnJob() == 0){
			List<LeaveEmployeeEntity> leaveEmpEntities = (List<LeaveEmployeeEntity>) getEmployeeEntities(command, settingEntity, sessionInfo).get("leaveJob");
			if (leaveEmpEntities == null) {
				throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_NOT_FOUND);
			}
			for (LeaveEmployeeEntity leaveEmpEntitity : leaveEmpEntities) {
				WorkAttendanceFeedBackEntity workAttendanceFeedBackEntity = attendancesFeedBackDao.findByQuaryDataAndId(queryStartDate, queryEndDate, leaveEmpEntitity.getEmpId());
				if (workAttendanceFeedBackEntity == null) {
					ids.add(leaveEmpEntitity.getEmpId());
				}
			}
		}else {
			List<EmployeeEntity> employeeEntities = (List<EmployeeEntity>) getEmployeeEntities(command, settingEntity, sessionInfo).get("onJob");
			if (employeeEntities == null) {
				throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_NOT_FOUND);
			}
			for (EmployeeEntity employeeEntity : employeeEntities) {
				WorkAttendanceFeedBackEntity workAttendanceFeedBackEntity = attendancesFeedBackDao.findByQuaryDataAndId(queryStartDate, queryEndDate, employeeEntity.getId());
				if (workAttendanceFeedBackEntity == null) {
					ids.add(employeeEntity.getId());
				}
			}
		}*//*
		if (ListUtils.checkNullOrEmpty(ids)) {
			throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_TOTAL_ALREADY_PUBLISH);
		}
		List<WorkAttendanceFeedBackEntity> workAttendanceFeedBackEntities = new ArrayList<>();
		for (String id : ids) {
			WorkAttendanceFeedBackEntity workAttendanceFeedBackEntity = new WorkAttendanceFeedBackEntity();
			workAttendanceFeedBackEntity.setId(Utils.makeUUID());
			workAttendanceFeedBackEntity.setEmpId(id);
			workAttendanceFeedBackEntity.setStartTime(queryStartDate);
			workAttendanceFeedBackEntity.setEndTime(queryEndDate);
			workAttendanceFeedBackEntity.setState(WorkAttendanceFeedBackEntity.State.unconfirmed);
			workAttendanceFeedBackEntity.setCreateUser(sessionInfo.getUserId());
			workAttendanceFeedBackEntity.setCreateTime(System.currentTimeMillis());
			workAttendanceFeedBackEntity.setIsDelete(Constants.FALSE);
			workAttendanceFeedBackEntities.add(workAttendanceFeedBackEntity);
			logger.info("新增考勤反馈" + workAttendanceFeedBackEntity.getId());
			info.setMsg("新增考勤反馈" + workAttendanceFeedBackEntity.getId());
		}
		try {
			attendancesFeedBackDao.create(workAttendanceFeedBackEntities);
			//branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORK_ATTENDANCE_FEE_BACK, BranOpLogEntity.OP_TYPE_ADD, sessionInfo.getUserId(), info);
		} catch (Exception e) {
			throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_FEE_BACK_CREATE_FAILED);
		}
	}*/

    @Override
    public void publish(WorkAttendanceTotalPublishCommand command, SessionInfo sessionInfo) throws Exception {
        SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(command.getSettingId());
        if (settingEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_FIND_FAILED);
        }
        Long starDate = command.getQueryDate() + (settingEntity.getCycleStart() - 1) * 24 * 60 * 60 * 1000L;
        Long endDate = transformEndTime(command.getQueryDate(), settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

        Date queryStartDate = new Date(SysUtils.getOneDayStartTime(starDate));
        Date queryEndDate = new Date(SysUtils.getOneDayStartTime(endDate));
        List<WorkAttendanceFeedBackEntity> workAttendanceFeedBackEntities = new ArrayList<>();
        CirA:
        for (String id : command.getIds()) {
            //处理已发布数据
            List<WorkAttendanceFeedBackEntity> feedBackEntityList = attendancesFeedBackDao.findListByQueryDataAndId(queryStartDate, queryEndDate, id);
            List<WorkAttendanceFeedBackEntity> deleteFeedBackEntityList = new ArrayList<>();
            for (WorkAttendanceFeedBackEntity feedBackEntity : feedBackEntityList) {
                //如果有已确认数据则忽略本数据
                if (feedBackEntity.getState() == WorkAttendanceFeedBackEntity.State.confirmed) {
                    continue CirA;
                } else {
                    deleteFeedBackEntityList.add(feedBackEntity);
                }
            }
            //删除其他的申诉数据
            for (WorkAttendanceFeedBackEntity feedBackEntity : deleteFeedBackEntityList) {
                feedBackEntity.setIsDelete(Constants.TRUE);
                attendancesFeedBackDao.update(feedBackEntity);
            }

            WorkAttendanceFeedBackEntity workAttendanceFeedBackEntity = new WorkAttendanceFeedBackEntity();
            workAttendanceFeedBackEntity.setId(Utils.makeUUID());
            workAttendanceFeedBackEntity.setEmpId(id);
            workAttendanceFeedBackEntity.setStartTime(queryStartDate);
            workAttendanceFeedBackEntity.setEndTime(queryEndDate);
            workAttendanceFeedBackEntity.setState(WorkAttendanceFeedBackEntity.State.unconfirmed);
            workAttendanceFeedBackEntity.setCreateUser(sessionInfo.getUserId());
            workAttendanceFeedBackEntity.setCreateTime(System.currentTimeMillis());
            workAttendanceFeedBackEntity.setIsDelete(Constants.FALSE);
            workAttendanceFeedBackEntities.add(workAttendanceFeedBackEntity);
            logger.info("新增考勤反馈" + workAttendanceFeedBackEntity.getId());
            info.setMsg("新增考勤反馈" + workAttendanceFeedBackEntity.getId());
        }
        try {
            attendancesFeedBackDao.create(workAttendanceFeedBackEntities);
            //branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORK_ATTENDANCE_FEE_BACK, BranOpLogEntity.OP_TYPE_ADD, sessionInfo.getUserId(), info);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_FEE_BACK_CREATE_FAILED);
        }
    }

    /**
     * 豪力士考勤汇总计算
     *
     * @param workAttendanceEntities
     * @return
     */
    /*WorkAttendanceToTalCalculateResult calculateWorkAttendance(List<WorkAttendanceEntity> workAttendanceEntities) {
        WorkAttendanceToTalCalculateResult workAttendanceToTalCalculateResult = new WorkAttendanceToTalCalculateResult();
        for (WorkAttendanceEntity workAttendanceEntity : workAttendanceEntities) {
            //打卡方式
            workAttendanceToTalCalculateResult.setClockType(workAttendanceEntity.getClockType());
            //缺卡次数
            if (workAttendanceEntity.getAttendEndState() == WorkAttendanceEntity.State.lack) {
                workAttendanceToTalCalculateResult.setLackCount(workAttendanceToTalCalculateResult.getLackCount() + 1);
            }
            if (workAttendanceEntity.getAttendStartState() == WorkAttendanceEntity.State.lack) {
                workAttendanceToTalCalculateResult.setLackCount(workAttendanceToTalCalculateResult.getLackCount() + 1);
            }
            if (workAttendanceEntity.getAttendCurDayState() == WorkAttendanceEntity.State.invalid) {
                workAttendanceToTalCalculateResult.setLackCount(workAttendanceToTalCalculateResult.getLackCount() + 2);
            }
            if (workAttendanceEntity.getAttendCurDayState() == WorkAttendanceEntity.State.clear) {
                workAttendanceToTalCalculateResult.setLackCount(workAttendanceToTalCalculateResult.getLackCount() + 2);
            }
            //休息天数
            if (workAttendanceEntity.getAttendCurDayState() == WorkAttendanceEntity.State.rest) {
                workAttendanceToTalCalculateResult.setRestCount(workAttendanceToTalCalculateResult.getRestCount() + 1);
            }
            //出勤天数
            if (workAttendanceEntity.getAttendCurDayState() != WorkAttendanceEntity.State.rest) {
                workAttendanceToTalCalculateResult.setAttendanceDays(workAttendanceToTalCalculateResult.getAttendanceDays() + 1);
            }
            WorkAttendanceDetailEntity workAttendanceDetailEntity = attendceDetailDao.findDetalsById(workAttendanceEntity.getWorkAttendanceDetailId());
            if (workAttendanceDetailEntity != null) {
                //请假时长
                if (workAttendanceDetailEntity.getLeave() != 0) {
                    workAttendanceToTalCalculateResult.setLeaveHours(workAttendanceToTalCalculateResult.getLeaveHours() + workAttendanceDetailEntity.getLeave());
                }
                //请假次数
                List<WorkAttendanceLeaveEntity> workAttendanceLeaveEntityList = workAttendanceLeaveDao.getListByEmpIdAndAttendDay(workAttendanceEntity.getEmpId(), workAttendanceEntity.getAttendDay());
                workAttendanceToTalCalculateResult.setLeaveTimes(CollectionUtils.isEmpty(workAttendanceLeaveEntityList) ? 0 : workAttendanceLeaveEntityList.size());
                if (workAttendanceDetailEntity.getLeaveEarly() != 0) {
                    //早退时长
                    workAttendanceToTalCalculateResult.setUnFullHours(workAttendanceToTalCalculateResult.getUnFullHours() + workAttendanceDetailEntity.getLeaveEarly());
                    //早退次数
                    workAttendanceToTalCalculateResult.setUnFullCount(workAttendanceToTalCalculateResult.getUnFullCount() + 1);
                }
                if (workAttendanceDetailEntity.getOverTime() != 0) {
                    //加班时长
                    workAttendanceToTalCalculateResult.setOverTimeHours(workAttendanceToTalCalculateResult.getOverTimeHours() + workAttendanceDetailEntity.getOverTime());
                }
                if (workAttendanceDetailEntity.getLate() != 0) {
                    //迟到时长
                    workAttendanceToTalCalculateResult.setLateHours(workAttendanceToTalCalculateResult.getLateHours() + workAttendanceDetailEntity.getLate());
                    //迟到次数
                    workAttendanceToTalCalculateResult.setLateCount(workAttendanceToTalCalculateResult.getLateCount() + 1);
                }
                if (workAttendanceDetailEntity.getWorkTotalHours() != 0) {
                    //出勤时长
                    workAttendanceToTalCalculateResult.setAttendanceHours(workAttendanceToTalCalculateResult.getAttendanceHours() + workAttendanceDetailEntity.getWorkTotalHours());
                }
            }
        }
        return workAttendanceToTalCalculateResult;
    }*/

    /**
     * 汇总统计
     * @param empId 员工ID
     * @param queryStart 查询开始时间
     * @param queryEnd 查询结束时间
     * @return
     */
    WorkAttendanceToTalCalculateResult calculateWorkAttendance(String empId, Date queryStart, Date queryEnd, AttendanceTotalApprovalTypeResult approvalTypeResult) {
        WorkAttendanceToTalCalculateResult workAttendanceToTalCalculateResult = new WorkAttendanceToTalCalculateResult();

        //请假、加班的细分字段
        Map<Integer, Long> attendanceTypeMap = new HashMap<>();
        if (CollectionUtils.isEmpty(approvalTypeResult.getLeaveTypes()) || CollectionUtils.isEmpty(approvalTypeResult.getOvertimeTypes())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请配置出勤类型中的加班 ");
        }
        approvalTypeResult.getLeaveTypes().stream().forEach(approvalType -> attendanceTypeMap.put(approvalType.getApprovalTypeDetail(), 0l));
        approvalTypeResult.getOvertimeTypes().stream().forEach(approvalType -> attendanceTypeMap.put(approvalType.getApprovalTypeDetail(), 0l));
        workAttendanceToTalCalculateResult.setApprovalType(attendanceTypeMap);

        //查询时间范围内的考勤数据
        List<WorkAttendanceEntity> workAttendanceEntities = attendancesMonthDao.findByEmpIdAndAttendDay(empId, queryStart, queryEnd);
        if (CollectionUtils.isEmpty(workAttendanceEntities)) {
            return workAttendanceToTalCalculateResult;
        }
        //收集考勤的明细数据
        List<String> workAttendDetailList = new ArrayList<>();
        workAttendanceEntities.stream().forEach(entity -> {
            workAttendDetailList.add(entity.getWorkAttendanceDetailId());
        });
        List<WorkAttendanceDetailEntity> workAttendDetailEntityLit = attendceDetailDao.findDetalsByIds(workAttendDetailList);

        Map<String, WorkAttendanceDetailEntity> workAttendDetailEntityMap = new HashMap<>();
        workAttendDetailEntityLit.stream().forEach(entity -> {
            workAttendDetailEntityMap.put(entity.getId(), entity);
        });

        //收集时间范围内请假数据
        List<WorkAttendanceLeaveEntity> workAttendanceLeaveEntityList = workAttendanceLeaveDao.getListByEmpId(empId, queryStart.getTime(), queryEnd.getTime());
        //手机时间范围内的加班数据
        //List<WorkAttendanceOvertimeEntity> workAttendanceOvertimeEntityList = workAttendanceOvertimeDao.getListByEmpId(empId, queryStart.getTime(), queryEnd.getTime());
        for (WorkAttendanceEntity workAttendanceEntity : workAttendanceEntities) {
            //班次
            //WorkShiftTypeEntity workShiftTypeEntity = workShiftTypeMap.get(workAttendanceEntity.getWorkShiftTypeId());
            //打卡方式
            workAttendanceToTalCalculateResult.setClockType(workAttendanceEntity.getClockType());
            //缺卡次数
            if (workAttendanceEntity.getAttendCurDayState() == WorkAttendanceEntity.State.lack) {
                Integer startLackTime = workAttendanceEntity.getAttendStartState() == WorkAttendanceEntity.State.lack ? 1 : 0 ;
                Integer endLackTime = workAttendanceEntity.getAttendEndState() == WorkAttendanceEntity.State.lack ? 1 : 0;
                //如果上下班都缺卡 则+2 否则+1
                workAttendanceToTalCalculateResult.setLackCount(workAttendanceToTalCalculateResult.getLackCount() + startLackTime + endLackTime);
            }
            if (workAttendanceEntity.getAttendCurDayState() == WorkAttendanceEntity.State.invalid) {
                workAttendanceToTalCalculateResult.setLackCount(workAttendanceToTalCalculateResult.getLackCount() + 1);
            }
            //休息天数
            if (workAttendanceEntity.getAttendCurDayState() == WorkAttendanceEntity.State.rest) {
                workAttendanceToTalCalculateResult.setRestCount(workAttendanceToTalCalculateResult.getRestCount() + 1);
            }
            /* //应出勤天数
            if (workAttendanceEntity.getAttendCurDayState() != WorkAttendanceEntity.State.rest) {
                workAttendanceToTalCalculateResult.setAttendanceDays(workAttendanceToTalCalculateResult.getAttendanceDays() + 1);
            }*/
            WorkAttendanceDetailEntity workAttendanceDetailEntity = workAttendDetailEntityMap.get(workAttendanceEntity.getWorkAttendanceDetailId());
            if (workAttendanceDetailEntity != null) {
                //请假时长
                if (workAttendanceDetailEntity.getLeave() != 0) {
                    workAttendanceToTalCalculateResult.setLeaveHours(workAttendanceToTalCalculateResult.getLeaveHours() + workAttendanceDetailEntity.getLeave());
                }
                //请假次数
                for (WorkAttendanceLeaveEntity workAttendanceLeaveEntity : workAttendanceLeaveEntityList) {
                    if (DateUtils.isSameDay(workAttendanceLeaveEntity.getAttendDay(), workAttendanceEntity.getAttendDay())) {
                        workAttendanceToTalCalculateResult.setLeaveTimes(workAttendanceToTalCalculateResult.getLeaveTimes() + 1);
                        if (null != attendanceTypeMap.get(workAttendanceLeaveEntity.getLeaveType())) {
                            attendanceTypeMap.put(workAttendanceLeaveEntity.getLeaveType(),
                                    attendanceTypeMap.get(workAttendanceLeaveEntity.getLeaveType())
                                            + (workAttendanceLeaveEntity.getLeaveTimeEnd().getTime() - workAttendanceLeaveEntity.getLeaveStartTime().getTime()) /(1000 * 60));
                        }
                    }
                }
                //加班
               /* for (WorkAttendanceOvertimeEntity workAttendanceOvertimeEntity : workAttendanceOvertimeEntityList) {
                    if (DateUtils.isSameDay(workAttendanceOvertimeEntity.getAttendDay(), workAttendanceEntity.getAttendDay())) {
                        if (null != attendanceTypeMap.get(workAttendanceOvertimeEntity.getOvertimeType())) {
                            attendanceTypeMap.put(workAttendanceOvertimeEntity.getOvertimeType(),
                                    attendanceTypeMap.get(workAttendanceOvertimeEntity.getOvertimeType())
                                            + (workAttendanceOvertimeEntity.getOvertimeEnd().getTime() - workAttendanceOvertimeEntity.getOvertimeStart().getTime()) /(1000 * 60));
                        }
                    }
                }*/
                if (workAttendanceDetailEntity.getOverTime() != 0) {
                    //加班时长
                    workAttendanceToTalCalculateResult.setOverTimeHours(workAttendanceToTalCalculateResult.getOverTimeHours() + workAttendanceDetailEntity.getOverTime());
                    attendanceTypeMap.put(workAttendanceEntity.getApprovalTypeDetail(),
                            (null == attendanceTypeMap.get(workAttendanceEntity.getApprovalTypeDetail()) ? 0 : attendanceTypeMap.get(workAttendanceEntity.getApprovalTypeDetail()))
                                    + workAttendanceDetailEntity.getOverTime());
                }
                if (workAttendanceDetailEntity.getLeaveEarly() != 0) {
                    //早退时长
                    workAttendanceToTalCalculateResult.setUnFullHours(workAttendanceToTalCalculateResult.getUnFullHours() + workAttendanceDetailEntity.getLeaveEarly());
                    //早退次数
                    workAttendanceToTalCalculateResult.setUnFullCount(workAttendanceToTalCalculateResult.getUnFullCount() + 1);
                }
                if (workAttendanceDetailEntity.getLate() != 0) {
                    //迟到时长
                    workAttendanceToTalCalculateResult.setLateHours(workAttendanceToTalCalculateResult.getLateHours() + workAttendanceDetailEntity.getLate());
                    //迟到次数
                    workAttendanceToTalCalculateResult.setLateCount(workAttendanceToTalCalculateResult.getLateCount() + 1);
                }
                if (workAttendanceDetailEntity.getAbsenteeism() != 0) {
                    //旷工天数
                    workAttendanceToTalCalculateResult.setAbsentDays(workAttendanceToTalCalculateResult.getAbsentDays() + workAttendanceDetailEntity.getAbsenteeism());
                    //旷工次数
                    workAttendanceToTalCalculateResult.setAbsentTimes(workAttendanceToTalCalculateResult.getAbsentTimes() + 1);
                }
                if (workAttendanceDetailEntity.getWorkTotalHours() != 0) {
                    //出勤时长
                    workAttendanceToTalCalculateResult.setNormalHours(workAttendanceToTalCalculateResult.getNormalHours() + workAttendanceDetailEntity.getWorkTotalHours());
                }
                //只要有打卡记录，且这一天不为
                if ((null != workAttendanceEntity.getAttendStartTime() || null != workAttendanceEntity.getAttendEndTime())
                        && (workAttendanceEntity.getAttendStartState() != WorkAttendanceEntity.State.leave && workAttendanceEntity.getAttendEndState() != WorkAttendanceEntity.State.leave)) {
                    //实际出勤天数
                    workAttendanceToTalCalculateResult.setActualAttendanceDays(workAttendanceToTalCalculateResult.getActualAttendanceDays() + 1);
                }

            }
        }
        workAttendanceToTalCalculateResult.setAttendanceHours(workAttendanceToTalCalculateResult.getNormalHours() + workAttendanceToTalCalculateResult.getOverTimeHours());
        return workAttendanceToTalCalculateResult;
    }


    @Override
    public void exportWorkAttendanceTotal(Long queryDate, String workShiftId, String settingId, String empId, Integer isOnJob, String departmentId, Integer isPublish, Integer sureState,
                                          SessionInfo sessionInfo, HttpServletResponse response) throws Exception {
        WorkAttendanceTotalQueryCommand command = new WorkAttendanceTotalQueryCommand();

        command.setWorkShiftId(workShiftId);
        command.setQueryDate(queryDate);
        command.setEmpId(empId);
        command.setIsOnJob(isOnJob);
        command.setSettingId(settingId);
        command.setDepartmentId(departmentId);
        command.setIsPublish(isPublish);
        command.setSureState(sureState);

        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(settingId);
        if (settingEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_FIND_FAILED);
        }

        Long starDate = command.getQueryDate() + (settingEntity.getCycleStart() - 1) * 24 * 60 * 60 * 1000L;
        Long endDate = transformEndTime(command.getQueryDate(), settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());
        Date queryStartDate = new Date(SysUtils.getOneDayStartTime(starDate));
        Date queryEndDate = new Date(SysUtils.getOneDayStartTime(endDate));

        //获取企业配置的加班请假-动态列
        AttendanceTotalApprovalTypeResult approvalTypeResult = getApprovalType(sessionInfo);

        //周期下所有的班组
        List<String> workShiftIds = new ArrayList<>();
        //查询条件的班组ID
        List<String> workShiftIdList = new ArrayList<>();
        //通过branId和setId查询考勤周期范围内班组List
        List<WorkShiftEntity> workShiftEntities = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(settingEntity.getId(), sessionInfo.getCorpId());
        for (WorkShiftEntity workShiftEntity : workShiftEntities) {
            logger.debug("班组Id：" + workShiftEntity.getId() + "班组名字" + workShiftEntity.getShiftName());
            if (command.getWorkShiftId() != null) {
                if (workShiftEntity.getId().equals(command.getWorkShiftId())) {
                    workShiftIdList.add(command.getWorkShiftId());
                }
            }
            workShiftIds.add(workShiftEntity.getId());
        }

        List<String> empIds = new ArrayList<>();
        //如果发布状态、确认状态不为空，则需要过滤一部分员工
        if (null != command.getIsPublish() || null != command.getSureState()) {
            //根据确认状态查询
            List<WorkAttendanceFeedBackEntity> feedBackEntityList = attendancesFeedBackDao.findListByState(queryStartDate, queryEndDate, command.getIsPublish(), command.getSureState());
            Set<String> empSet = new HashSet<>();
            for (WorkAttendanceFeedBackEntity entity : feedBackEntityList) {
                empSet.add(entity.getEmpId());
            }
            empIds.addAll(empSet);
        }

        List<WorkAttendanceExportTotalResult> workAttendanceExportTotalResults = new ArrayList<>();
        List<EmployeeEntity> employeeEntities = null;
        List<LeaveEmployeeEntity> leaveEmployeeEntities = null;
        if (command.getIsOnJob() == 0) {
            leaveEmployeeEntities = (List<LeaveEmployeeEntity>) getEmployeeEntities(command, empIds, settingEntity, sessionInfo).get("leaveJob");
        } else {
            employeeEntities = (List<EmployeeEntity>) getEmployeeEntities(command, empIds, settingEntity, sessionInfo).get("onJob");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String attendanceStarDate = formatter.format(starDate);
        String attendanceEndDate = formatter.format(endDate);

        //计算所有的班组
        Map<String, Object> workShiftParams = new HashMap<>();
        workShiftParams.put("startString", attendanceStarDate);
        workShiftParams.put("endString", attendanceEndDate);
        workShiftParams.put("workShiftIds", workShiftIds);
        //key 班组id value 班组排班工作天数
        Map<String, Integer> workShiftWorkDays = new HashMap<>();
        List<WorkShiftDaysCountResult> workShiftCountList = workAttendanceMybatisDao.countWorkShiftWorkDays(workShiftParams);
        workShiftCountList.stream().forEach(tmp -> {
            workShiftWorkDays.put(tmp.getWorkShiftId(),
                    null == workShiftWorkDays.get(tmp.getWorkShiftId()) ? tmp.getWorkAttendanceDays() : workShiftWorkDays.get(tmp.getWorkShiftId()) - tmp.getWorkRestDays());
        });
        List<String> empCntIds = new ArrayList<>();
        if (null != employeeEntities) {
            employeeEntities.stream().forEach(entity -> empCntIds.add(entity.getId()));
        }
        if (null != leaveEmployeeEntities) {
            leaveEmployeeEntities.stream().forEach(entity -> empCntIds.add(entity.getEmpId()));
        }
        //个人修改的排班
        Map<String, Object> empParams = new HashMap<>();
        empParams.put("startString", attendanceStarDate);
        empParams.put("endString", attendanceEndDate);
        empParams.put("empIds", empCntIds);
        empParams.put("branCorpId", sessionInfo.getCorpId());
        Map<String, Map<String, Integer>> empWorkDaysCheck = new HashMap<>();
        List<EmpDaysCountResult> empDaysCountResultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(empCntIds)) {
            empDaysCountResultList = workAttendanceMybatisDao.countEmpRestDays(empParams);
        }
        empDaysCountResultList.stream().forEach(tmp -> {
            if (null == empWorkDaysCheck.get(tmp.getEmpId())) {
                Map<String, Integer> tmpMap = new HashMap<String, Integer>();
                tmpMap.put(tmp.getModifyDate(), tmp.getIsRestDay());
                empWorkDaysCheck.put(tmp.getEmpId(), tmpMap);
            } else if (null == empWorkDaysCheck.get(tmp.getEmpId()).get(tmp.getModifyDate()) || tmp.getFromAttend() == Constants.TRUE) {
                empWorkDaysCheck.get(tmp.getEmpId()).put(tmp.getModifyDate(), tmp.getIsRestDay());
            }
        });

        Map<String, Integer> empWorkDays = new HashMap<>();
        empWorkDaysCheck.entrySet().stream().forEach( entry -> {
            Iterator i = entry.getValue().values().iterator();
            Integer restDays = 0;
            while (i.hasNext()) {
                restDays += (Integer) i.next();
            }
            empWorkDays.put(entry.getKey(), restDays);
        });


        if (command.getIsOnJob() == 0) {
            for (LeaveEmployeeEntity leaveEmployeeEntity : leaveEmployeeEntities) {
                if (null != command.getIsPublish() && command.getIsPublish() == Constants.TRUE && !empIds.contains(leaveEmployeeEntity.getEmpId())) {
                    continue;
                }
                if (null != command.getIsPublish() && command.getIsPublish() == Constants.FALSE && empIds.contains(leaveEmployeeEntity.getEmpId())) {
                    continue;
                }
                WorkAttendanceExportTotalResult workAttendanceExportTotalResult = exportTotalResult(leaveEmployeeEntity.getEmpId(), queryStartDate, queryEndDate, approvalTypeResult, sessionInfo);
                workAttendanceExportTotalResult.setId(leaveEmployeeEntity.getId());
                workAttendanceExportTotalResult.setName(leaveEmployeeEntity.getRealName());
                workAttendanceExportTotalResult.setWorkSN(leaveEmployeeEntity.getWorkSn());
                workAttendanceExportTotalResult.setWorkAttendanceNo(leaveEmployeeEntity.getWorkAttendanceNo());
                workAttendanceExportTotalResult.setWorkShiftName(leaveEmployeeEntity.getWorkShiftName());
                workAttendanceExportTotalResult.setDepartmentName(leaveEmployeeEntity.getDepartmentName());
                workAttendanceExportTotalResult.setAttendanceDays(String.valueOf(
                        (null == workShiftWorkDays.get(leaveEmployeeEntity.getWorkShiftId()) ? 0 : workShiftWorkDays.get(leaveEmployeeEntity.getWorkShiftId()) -
                                (null == empWorkDays.get(leaveEmployeeEntity.getEmpId()) ? 0 : empWorkDays.get(leaveEmployeeEntity.getEmpId())))));
                WorkAttendanceTotalResult.WorkAttendanceTotals workAttendanceTotalResult = new WorkAttendanceTotalResult.WorkAttendanceTotals();
                checkState(queryStartDate, queryEndDate, leaveEmployeeEntity.getEmpId(), workAttendanceTotalResult);
                workAttendanceExportTotalResult.setIsPublish(workAttendanceTotalResult.getIsPublish());
                workAttendanceExportTotalResult.setSureState(workAttendanceTotalResult.getSureState());
                workAttendanceExportTotalResults.add(workAttendanceExportTotalResult);
            }
        } else {
            for (EmployeeEntity employeeEntity : employeeEntities) {
                if (null != command.getIsPublish() && command.getIsPublish() == Constants.TRUE && !empIds.contains(employeeEntity.getId())) {
                    continue;
                }
                if (null != command.getIsPublish() && command.getIsPublish() == Constants.FALSE && empIds.contains(employeeEntity.getId())) {
                    continue;
                }
                WorkAttendanceExportTotalResult workAttendanceExportTotalResult = exportTotalResult(employeeEntity.getId(), queryStartDate, queryEndDate, approvalTypeResult, sessionInfo);
                workAttendanceExportTotalResult.setId(employeeEntity.getId());
                workAttendanceExportTotalResult.setName(employeeEntity.getRealName());
                workAttendanceExportTotalResult.setWorkSN(employeeEntity.getWorkSn());
                workAttendanceExportTotalResult.setWorkAttendanceNo(employeeEntity.getWorkAttendanceNo());
                workAttendanceExportTotalResult.setWorkShiftName(employeeEntity.getWorkShiftName());
                workAttendanceExportTotalResult.setDepartmentName(employeeEntity.getDepartmentName());
                workAttendanceExportTotalResult.setAttendanceDays(String.valueOf(
                        (null == workShiftWorkDays.get(employeeEntity.getWorkShiftId()) ? 0 : workShiftWorkDays.get(employeeEntity.getWorkShiftId()) -
                                (null == empWorkDays.get(employeeEntity.getId()) ? 0 : empWorkDays.get(employeeEntity.getId())))));
                WorkAttendanceTotalResult.WorkAttendanceTotals workAttendanceTotalResult = new WorkAttendanceTotalResult.WorkAttendanceTotals();
                checkState(queryStartDate, queryEndDate, employeeEntity.getId(), workAttendanceTotalResult);
                workAttendanceExportTotalResult.setIsPublish(workAttendanceTotalResult.getIsPublish());
                workAttendanceExportTotalResult.setSureState(workAttendanceTotalResult.getSureState());
                workAttendanceExportTotalResults.add(workAttendanceExportTotalResult);
            }
        }
        String exportFilePath = configService.getExcelTemplateLocation() + BranAdminConfigService.WORK_ATTENDANCE_TOTLA_TEMPLATE;
        excelExportHelper.export(
                exportFilePath,
                "考勤月度汇总表",
                new HashMap() {{
                    put("approvalTypeResult", approvalTypeResult);
                    put("list", CollectionUtils.isEmpty(workAttendanceExportTotalResults) ? new ArrayList<WorkAttendanceExportTotalResult>() : workAttendanceExportTotalResults);
                    put("attendanceDate", attendanceStarDate + "~" + attendanceEndDate);
                    put("exportTime", format.format(System.currentTimeMillis()));
                }},
                response
        );
    }

    public WorkAttendanceExportTotalResult exportTotalResult(String empId, Date queryStart, Date queryEnd, AttendanceTotalApprovalTypeResult approvalTypeResult, SessionInfo sessionInfo) throws Exception {
        WorkAttendanceToTalCalculateResult workAttendanceToTalCalculateResult;
		/*if (sessionInfo.getCorpId().equals(getWorkAttendanceCompanyId(workAttendanceCompanyId, sessionInfo))) {
			workAttendanceToTalCalculateResult = calculateWorkAttendance2(workAttendanceEntities);
		} else {
			workAttendanceToTalCalculateResult = calculateWorkAttendance(workAttendanceEntities);
		}*/
        workAttendanceToTalCalculateResult = calculateWorkAttendance(empId, queryStart, queryEnd, approvalTypeResult);
        WorkAttendanceExportTotalResult workAttendanceExportTotalResult = new WorkAttendanceExportTotalResult();
        workAttendanceExportTotalResult.setLackCount(String.valueOf(workAttendanceToTalCalculateResult.getLackCount()));
        workAttendanceExportTotalResult.setActualAttendanceDays(String.valueOf(workAttendanceToTalCalculateResult.getActualAttendanceDays()));
        workAttendanceExportTotalResult.setLateCount(String.valueOf(workAttendanceToTalCalculateResult.getLateCount()));
        workAttendanceExportTotalResult.setLateHours(minToHourAndMin(workAttendanceToTalCalculateResult.getLateHours()));
        workAttendanceExportTotalResult.setAttendanceHours(minToHourAndMin(workAttendanceToTalCalculateResult.getAttendanceHours()));
        workAttendanceExportTotalResult.setLeaveHours(minToHourAndMin(workAttendanceToTalCalculateResult.getLeaveHours()));
        workAttendanceExportTotalResult.setOverTimeHours(minToHourAndMin(workAttendanceToTalCalculateResult.getOverTimeHours()));
        workAttendanceExportTotalResult.setRestCount(String.valueOf(workAttendanceToTalCalculateResult.getRestCount()));
        workAttendanceExportTotalResult.setUnFullCount(String.valueOf(workAttendanceToTalCalculateResult.getUnFullCount()));
        workAttendanceExportTotalResult.setUnFullHours(minToHourAndMin(workAttendanceToTalCalculateResult.getUnFullHours()));
        workAttendanceExportTotalResult.setClockType(workAttendanceToTalCalculateResult.getClockType() == WorkAttendanceEnum.ClockType.device ? "考勤机打卡" : "手机打卡");
        workAttendanceExportTotalResult.setLeaveTimes(String.valueOf(workAttendanceToTalCalculateResult.getLeaveTimes()));
        workAttendanceExportTotalResult.setAbsentTimes(String.valueOf(workAttendanceToTalCalculateResult.getAbsentTimes()));
        workAttendanceExportTotalResult.setNormalHours(workAttendanceToTalCalculateResult.getNormalHours());
        workAttendanceExportTotalResult.setAttendanceHoursTotal(minToHourAndMin(workAttendanceToTalCalculateResult.getAttendanceHours() + workAttendanceToTalCalculateResult.getOverTimeHours()));
        workAttendanceExportTotalResult.setAbsentDays(String.valueOf(workAttendanceToTalCalculateResult.getAbsentDays()));
        workAttendanceToTalCalculateResult.getApprovalType().forEach((k, v) -> workAttendanceExportTotalResult.getExtra().put("approval_" + k.toString(),
                        null == v ? "0m" : minToHourAndMin(Integer.parseInt(String.valueOf(v)))));
        return workAttendanceExportTotalResult;
    }

    /**
     * 根据查询条件 查询出员工实体类
     *
     * @param command
     * @param sessionInfo
     * @return
     * @throws Exception
     */
    public Map<String, Object> getEmployeeEntities(WorkAttendanceTotalQueryCommand command, List<String> empIds, WorkAttendanceSettingEntity settingEntity, SessionInfo sessionInfo) throws Exception {
        //查询公司是否开通手机打卡
        int isOpenPhoneClock = workAttendanceRangeService.isOpenPhoneClock(sessionInfo.getCorpId());
        if (command.getIsOnJob() == null) {
            command.setIsOnJob(1);
        }
        WorkAttendanceSettingEntity workAttendanceSettingEntity = attendanceSettingDao.findByCorpIdAndWorkAttendanceTime(sessionInfo.getCorpId(), settingEntity.getCycleStart(), settingEntity.getCycleEnd());
        if (workAttendanceSettingEntity == null) {
            return null;
        }
        List<String> workShiftIds = new ArrayList<>();
        List<String> workShiftId = new ArrayList<>();
        List<WorkShiftEntity> workShiftEntities = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(workAttendanceSettingEntity.getId(), sessionInfo.getCorpId());
        for (WorkShiftEntity workShiftEntity : workShiftEntities) {
            if (command.getWorkShiftId() != null) {
                if (workShiftEntity.getId().equals(command.getWorkShiftId())) {
                    workShiftId.add(command.getWorkShiftId());
                }
            }
            workShiftIds.add(workShiftEntity.getId());
        }
        List<EmployeeEntity> employeeEntities = null;
        List<LeaveEmployeeEntity> leaveEmployeeEntities = null;
        //如果没有选择班组，则按使用此考勤周期的班组查询
        //如果没有选择班组，则按使用此考勤周期的班组查询
        if (StringUtils.isAnyBlank(command.getWorkShiftId())) {
            // 周期下没有班组 则直接返回
            if (CollectionUtils.isEmpty(workShiftIds)) {
                return null;
            }
            if (command.getIsOnJob() == 0) {
                leaveEmployeeEntities = leaveEmployeeDao.findAttendanceTotalList(workShiftIds, command.getEmpId(), empIds, command.getIsPublish(), command.getDepartmentId());
            } else {
                employeeEntities = employeeDao.findByWorkShiftId(workShiftIds, command.getEmpId(), empIds, isOpenPhoneClock, command.getIsPublish(), command.getDepartmentId());
            }
        } else {
            //如果选择了班组，且此班组不在考勤周期班组中，直接返回空
            if (CollectionUtils.isEmpty(workShiftId)) {
                return null;
            } else { //如果选择了班组，且此班组在考勤周期班组中，则按此班组查询
                if (command.getIsOnJob() == 0) {
                    leaveEmployeeEntities = leaveEmployeeDao.findAttendanceTotalList(workShiftIds, command.getEmpId(), empIds, command.getIsPublish(), command.getDepartmentId());
                } else {
                    employeeEntities = employeeDao.findByWorkShiftId(workShiftIds, command.getEmpId(), empIds, isOpenPhoneClock, command.getIsPublish(), command.getDepartmentId());
                }
            }
        }
        Map map = new HashedMap();
        map.put("onJob", employeeEntities);
        map.put("leaveJob", leaveEmployeeEntities);
        return map;
    }

    public WorkAttendanceTotalExportUrlResult getExportTotalUrl(WorkAttendanceTotalQueryCommand command) {
        WorkAttendanceTotalExportUrlResult urlResult = new WorkAttendanceTotalExportUrlResult();
        if (command.getIsOnJob() == null) {
            command.setIsOnJob(1);
        }
        urlResult.setUrl(AttendanceConstants.ATTENDANCE_SUMMARY_EXPORT_URL + "?" + command.urlParamString());
		/*if (StringUtils.isAnyBlank(command.getWorkShiftId())) {
			if (StringUtils.isAnyBlank(command.getEmpId())) {
				urlResult.setUrl(ATTENDANCE_SUMMARY_EXPORT_URL + "?queryDate=" + command.getQueryDate() + "&settingId=" + command.getSettingId() + "&isOnJob=" + command.getIsOnJob());
			} else {
				urlResult.setUrl(ATTENDANCE_SUMMARY_EXPORT_URL + "?queryDate=" + command.getQueryDate() + "&settingId=" + command.getSettingId() + "&empId=" + command.getEmpId() + "&isOnJob=" + command.getIsOnJob());
			}
		} else {
			if (StringUtils.isAnyBlank(command.getEmpId())) {
				urlResult.setUrl(ATTENDANCE_SUMMARY_EXPORT_URL + "?queryDate=" + command.getQueryDate() + "&settingId=" + command.getSettingId() + "&workShiftId=" + command.getWorkShiftId() + "&isOnJob=" + command.getIsOnJob());
			} else {
				urlResult.setUrl(ATTENDANCE_SUMMARY_EXPORT_URL + "?queryDate=" + command.getQueryDate() + "&settingId=" + command.getSettingId() + "&workShiftId=" + command.getWorkShiftId() + "&empId=" + command.getEmpId()  + "&isOnJob=" + command.getIsOnJob());
			}
		}*/
        return urlResult;
    }

    /**
     * 结束时间转换
     *
     * @param queryDate
     * @param cycleEnd
     * @param isNextMonth
     * @return
     */
    Long transformEndTime(Long queryDate, Integer cycleEnd, Integer cycleStart, Integer isNextMonth) {
        Long starDate = queryDate + (cycleStart - 1) * 24 * 60 * 60 * 1000L;
        Long endDate = null;
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatterMon = new SimpleDateFormat("MM");
        String queryYear = formatterYear.format(queryDate);
        if (isNextMonth == 1) {
            String queryMon = String.valueOf((Integer.parseInt(formatterMon.format(queryDate)) + 1));
            String queryTime = queryYear + "-" + queryMon;
            endDate = SysUtils.getTimestampFormDateString(queryTime + "-" + cycleEnd.toString(), "yyyy-MM-dd");
            if ((endDate - starDate) / (24 * 60 * 60 * 1000L) > 31) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_ENDTIME_GREATERTHAN_STARTTIME);
            }
        } else {
            if (cycleEnd < cycleStart) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_ENDTIME_LESSTHAN_STARTTIME);
            }
            int queryMonth = Integer.parseInt(formatterMon.format(queryDate));
            int year = Integer.parseInt(queryYear);
            cycleEnd = getCycleEnd(queryMonth, year, cycleEnd);
            endDate = queryDate + (cycleEnd - 1) * 24 * 60 * 60 * 1000L;
        }
        return endDate;
    }

    /**
     * 开始时间转换
     *
     * @param queryDate
     * @param cycleStart
     * @return
     */
    Long transformStartTime(Long queryDate, Integer cycleStart) {
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatterMon = new SimpleDateFormat("MM");
        int queryMonth = Integer.parseInt(formatterMon.format(queryDate));
        int queryYear = Integer.parseInt(formatterYear.format(queryDate));
        int start = getCycleEnd(queryMonth, queryYear, cycleStart);
        Long starDate = queryDate + (start - 1) * 24 * 60 * 60 * 1000L;

        return starDate;
    }

    public int getCycleEnd(int month, int year, Integer cycleEnd) {
        int[] bigMonth = {1, 3, 5, 7, 8, 10, 12};
        int bigMonResult = Arrays.binarySearch(bigMonth, month);
        int[] smallMonth = {2, 4, 6, 9, 11};
        int smallMonResult = Arrays.binarySearch(smallMonth, month);
        if (cycleEnd == 31) {
            if (bigMonResult >= 0) {
                cycleEnd = 31;
            } else if (smallMonResult > 0) {
                cycleEnd = 30;
            } else if (smallMonResult == 0) {
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    cycleEnd = 29;
                } else {
                    cycleEnd = 28;
                }
            }
        }
        return cycleEnd;
    }

    public String minToHourAndMin(int min) {
        if (min < 60) {
            return String.valueOf(min) + "m";
        }
        if (min == 60) {
            return 1 + "h";
        }
        if (min > 60) {
            return String.valueOf(min / 60) + "h" + String.valueOf(min % 60) + "m";
        }
        return null;
    }

    /**
     * 获取有旷工字段统计的公司
     *
     * @param workAttendanceCompanyId
     * @param sessionInfo
     * @return
     * @throws Exception
     */
    public String getWorkAttendanceCompanyId(String workAttendanceCompanyId, SessionInfo sessionInfo) throws Exception {
        if (StringUtils.isAnyBlank(workAttendanceCompanyId)) {
            return null;
        }
        String[] arr = workAttendanceCompanyId.split(",");
        for (String companyId : arr) {
            if (companyId.trim().equals(sessionInfo.getCorpId()))
                return companyId.trim();
        }
        return null;
    }

    /**
     * 获取离职员工列表ids
     *
     * @param workShiftIds
     * @param workShiftId
     * @param command
     * @return
     */
    public List<String> getDepartureEmpList(List<String> workShiftIds, List<String> workShiftId, WorkAttendanceTotalQueryCommand command, SessionInfo sessionInfo) {
        List<String> empIds = new ArrayList<>();
        List<LeaveEmployeeEntity> leaveEmployeeEntities;
        if (StringUtils.isAnyBlank(command.getWorkShiftId())) {
            leaveEmployeeEntities = leaveEmployeeDao.findByWorkShiftId(workShiftIds, command.getEmpId(), sessionInfo.getCorpId());
            //logger.debug("班组："+leaveEmployeeEntities.get(0));
        } else {
            if (ListUtils.checkNullOrEmpty(workShiftId)) {
                return null;
            } else {
                leaveEmployeeEntities = leaveEmployeeDao.findByWorkShiftId(workShiftId, command.getEmpId(), sessionInfo.getCorpId());
            }
        }
        if (!ListUtils.checkNullOrEmpty(leaveEmployeeEntities)) {
            for (LeaveEmployeeEntity leaveEmployeeEntity : leaveEmployeeEntities) {
                logger.debug("离职员工id" + leaveEmployeeEntity.getId() + "姓名:" + leaveEmployeeEntity.getRealName());
                empIds.add(leaveEmployeeEntity.getEmpId());
            }
        }
        return empIds;
    }

    @Override
    public List<WorkAttendanceLeaveResult> leaveList(String empId, String settingId, Long yearMonth) {
        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(settingId);
        if (settingEntity == null) {
            return null;
        }
        //查询月份开始时间和结束时间
        Long starDate = transformStartTime(yearMonth, settingEntity.getCycleStart());
        Long endDate = transformEndTime(yearMonth, settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

        /*List<WorkAttendanceEntity> workAttendanceEntityList = workAttendanceDao.findListByEmpIdAndAttendDate(empId, new Date(starDate), new Date(endDate));
        List<String> attendanceIds = new ArrayList<>();
        for (WorkAttendanceEntity entity : workAttendanceEntityList) {
            attendanceIds.add(entity.getId());
        }*/
        List<WorkAttendanceLeaveEntity> leaveEntityList = workAttendanceLeaveDao.getListByEmpId(empId, starDate, endDate);
        List<WorkAttendanceLeaveResult> leaveResultList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        DateTime today = new DateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), 0, 0 ,0);
        for (WorkAttendanceLeaveEntity leave : leaveEntityList) {
            if (!leave.getAttendDay().before(today.toDate())){
                continue;
            }
            WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByEmpIdAndAttendDate(leave.getEmpId(), new DateTime(leave.getAttendDay()));
            if (null == workAttendanceEntity) {
                continue;
            }
            WorkAttendanceLeaveResult leaveResult = new WorkAttendanceLeaveResult();
            leaveResult.setWorkAttendanceDate(leave.getAttendDay().getTime());
            leaveResult.setLeaveMins((leave.getLeaveTimeEnd().getTime() - leave.getLeaveStartTime().getTime()) / (1000 * 60));
            leaveResultList.add(leaveResult);
        }

        return leaveResultList;
    }

    @Override
    public List<WorkAttendanceLateResult> lateList(String empId, String settingId, Long yearMonth) {
        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(settingId);
        if (settingEntity == null) {
            return null;
        }
        //查询月份开始时间和结束时间
        Long starDate = transformStartTime(yearMonth, settingEntity.getCycleStart());
        Long endDate = transformEndTime(yearMonth, settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

        List<WorkAttendanceEntity> workAttendanceEntityList = workAttendanceDao.findListByEmpIdAndAttendDate(empId, new Date(starDate), new Date(endDate));
        List<WorkAttendanceLateResult> lateResultList = new ArrayList<>();
        List<WorkAttendanceLeaveResult> leaveResultList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        DateTime today = new DateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), 0, 0 ,0);
        for (WorkAttendanceEntity entity : workAttendanceEntityList) {
            if (!entity.getAttendDay().before(today.toDate())) {
                continue;
            }
            WorkAttendanceDetailEntity detailEntity = attendceDetailDao.findByDetailId(entity.getWorkAttendanceDetailId());
            if (null != detailEntity && detailEntity.getLate() > 0) {
                WorkAttendanceLateResult result = new WorkAttendanceLateResult();
                result.setWorkAttendanceDate(entity.getAttendDay().getTime());
                result.setLateMins(detailEntity.getLate());
                lateResultList.add(result);
            }
        }
        return lateResultList;
    }

    @Override
    public List<WorkAttendanceNoFullResult> noFullList(String empId, String settingId, Long yearMonth) {
        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(settingId);
        if (settingEntity == null) {
            return null;
        }
        //查询月份开始时间和结束时间
        Long starDate = transformStartTime(yearMonth, settingEntity.getCycleStart());
        Long endDate = transformEndTime(yearMonth, settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        DateTime today = new DateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), 0, 0 ,0);

        List<WorkAttendanceEntity> workAttendanceEntityList = workAttendanceDao.findListByEmpIdAndAttendDate(empId, new Date(starDate), new Date(endDate));
        List<WorkAttendanceNoFullResult> noFullResultList = new ArrayList<>();
        for (WorkAttendanceEntity entity : workAttendanceEntityList) {
            if (!entity.getAttendDay().before(today.toDate())) {
                continue;
            }
            WorkAttendanceDetailEntity detailEntity = attendceDetailDao.findByDetailId(entity.getWorkAttendanceDetailId());
            if (null != detailEntity && detailEntity.getLeaveEarly() > 0) {
                WorkAttendanceNoFullResult result = new WorkAttendanceNoFullResult();
                result.setWorkAttendanceDate(entity.getAttendDay().getTime());
                result.setNoFullMins(detailEntity.getLeaveEarly());
                noFullResultList.add(result);
            }
        }
        return noFullResultList;
    }

    @Override
    public List<WorkAttendanceAbsentResult> absentList(String empId, String settingId, Long yearMonth) {
        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(settingId);
        if (settingEntity == null) {
            return null;
        }
        //查询月份开始时间和结束时间
        Long starDate = transformStartTime(yearMonth, settingEntity.getCycleStart());
        Long endDate = transformEndTime(yearMonth, settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        DateTime today = new DateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), 0, 0 ,0);

        List<WorkAttendanceEntity> workAttendanceEntityList = workAttendanceDao.findListByEmpIdAndAttendDate(empId, new Date(starDate), new Date(endDate));
        List<WorkAttendanceAbsentResult> absentResultList = new ArrayList<>();
        for (WorkAttendanceEntity entity : workAttendanceEntityList) {
            if (!entity.getAttendDay().before(today.toDate())) {
                continue;
            }
            WorkAttendanceDetailEntity detailEntity = attendceDetailDao.findByDetailId(entity.getWorkAttendanceDetailId());
            if (null != detailEntity && detailEntity.getAbsenteeism() > 0) {
                WorkAttendanceAbsentResult result = new WorkAttendanceAbsentResult();
                result.setWorkAttendanceDate(entity.getAttendDay().getTime());
                result.setAbsentDays(detailEntity.getAbsenteeism());
                absentResultList.add(result);
            }
        }
        return absentResultList;
    }

    @Override
    public List<WorkAttendanceLackResult> lackList(String empId, String settingId, Long yearMonth) {
        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(settingId);
        if (settingEntity == null) {
            return null;
        }
        //查询月份开始时间和结束时间
        Long starDate = transformStartTime(yearMonth, settingEntity.getCycleStart());
        Long endDate = transformEndTime(yearMonth, settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        DateTime today = new DateTime(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), 0, 0 ,0);

        List<WorkAttendanceEntity> workAttendanceEntityList = workAttendanceDao.findListByEmpIdAndAttendDate(empId, new Date(starDate), new Date(endDate));
        List<WorkAttendanceLackResult> lackResultList = new ArrayList<>();
        for (WorkAttendanceEntity entity : workAttendanceEntityList) {
            if (!entity.getAttendDay().before(today.toDate())) {
                continue;
            }

            WorkShiftTypeEntity workShiftTypeEntity = branWorkShiftTypeDao.findByUniqueParam("id", entity.getWorkShiftTypeId());
            List<WorkAttendanceLeaveEntity> workAttendanceLeaveEntityList = workAttendanceLeaveDao.getListByEmpIdAndAttendDay(entity.getEmpId(), entity.getAttendDay());

            String startLackInfo = WorkAttendanceEntity.State.lack == entity.getAttendStartState() ? "上班缺卡" : "";
            String endLackInfo = WorkAttendanceEntity.State.lack == entity.getAttendEndState() ? "下班缺卡" : "";

            if (!workShiftTypeEntity.getShortName().equals("休")) {
                Long workInTime = workShiftTypeEntity.getInTimeValue(entity.getAttendDay());
                Long workOutTime = workShiftTypeEntity.getOutTimeValue(entity.getAttendDay());
                for (WorkAttendanceLeaveEntity leaveEntity : workAttendanceLeaveEntityList) {
                    if (workInTime == leaveEntity.getLeaveStartTime().getTime()) {
                        startLackInfo = "";
                    }
                    if (workOutTime == leaveEntity.getLeaveTimeEnd().getTime()) {
                        endLackInfo = "";
                    }
                }
            }

            if (StringUtils.isNotBlank(startLackInfo) || StringUtils.isNotBlank(endLackInfo)) {
                WorkAttendanceLackResult result = new WorkAttendanceLackResult();
                result.setWorkAttendanceDate(entity.getAttendDay().getTime());
                result.setLackInfo(startLackInfo + (StringUtils.isNotBlank(startLackInfo) && StringUtils.isNotBlank(endLackInfo) ? "," : "") + endLackInfo);
                lackResultList.add(result);
            }
        }
        return lackResultList;
    }

    @Override
    public AttendanceTotalApprovalTypeResult getApprovalType(SessionInfo sessionInfo) {
        AttendanceTotalApprovalTypeResult result = new AttendanceTotalApprovalTypeResult();
        //1.获取企业配置的所有的请假
        List<ApprovalTypeSettingResult> leaveList = approvalTypeSettingService.get(0, sessionInfo);
        if (CollectionUtils.isEmpty(leaveList)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请配置出勤类型中的加班");
        }
        List<ApprovalType> leaveTypeReusltList = new ArrayList<>();
        for (ApprovalTypeSettingResult leave : leaveList) {
            ApprovalType approvalType = new ApprovalType();
            approvalType.setTypeCode("0");
            approvalType.setApprovalTypeDetail(leave.getApprovalTypeDetail());
            approvalType.setApprovalTypeDetailName(ApprovalTypeSettingResult.APPROVAL_TYPE_MAP.get(leave.getApprovalTypeDetail()));
            leaveTypeReusltList.add(approvalType);
        }
        List<ApprovalTypeSettingResult> overtimeList = approvalTypeSettingService.get(1, sessionInfo);
        if (CollectionUtils.isEmpty(leaveList)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请配置出勤类型中的");
        }
        List<ApprovalType> overtimeTypeResult = new ArrayList<>();
        for (ApprovalTypeSettingResult overtime : overtimeList) {
            ApprovalType approvalType = new ApprovalType();
            approvalType.setTypeCode("1");
            approvalType.setApprovalTypeDetail(overtime.getApprovalTypeDetail());
            approvalType.setApprovalTypeDetailName(ApprovalTypeSettingResult.APPROVAL_TYPE_MAP.get(overtime.getApprovalTypeDetail()));
            overtimeTypeResult.add(approvalType);
        }
        result.setLeaveTypes(leaveTypeReusltList);
        result.setOvertimeTypes(overtimeTypeResult);
        return result;
    }
}
