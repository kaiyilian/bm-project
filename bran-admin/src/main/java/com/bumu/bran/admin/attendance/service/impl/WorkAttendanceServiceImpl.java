package com.bumu.bran.admin.attendance.service.impl;

import com.bumu.SysUtils;
import com.bumu.approval.helper.ApprovalHelper;
import com.bumu.approval.model.dao.ApprovalDao;
import com.bumu.approval.model.entity.ApprovalEntity;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.WorkShiftTypeEntity;
import com.bumu.bran.admin.attendance.AttendanceConstants;
import com.bumu.bran.admin.attendance.service.WorkAttendanceService;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.attendance.command.NewWorkAttendanceCleanCommand;
import com.bumu.bran.attendance.command.NewWorkAttendanceUpdateCommand;
import com.bumu.bran.attendance.command.WorkAttendanceCommand;
import com.bumu.bran.attendance.command.WorkAttendanceQueryCommand;
import com.bumu.bran.attendance.engine.WorkAttendanceGetRecordsHelper;
import com.bumu.bran.attendance.engine.builder.modify.WorkAttendanceBuildable;
import com.bumu.bran.attendance.engine.builder.modify.WorkAttendanceModifyBuilderManager;
import com.bumu.bran.attendance.model.dao.*;
import com.bumu.bran.attendance.model.dao.mybatis.WorkAttendanceMybatisDao;
import com.bumu.bran.attendance.result.*;
import com.bumu.bran.attendance.service.WorkAttendanceEmpService;
import com.bumu.bran.attendance.service.WorkAttendanceLeaveService;
import com.bumu.bran.attendance.service.WorkAttendanceOvertimeService;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.LeaveEmployeeDao;
import com.bumu.attendance.constant.WorkAttendanceEnum;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.bran.model.entity.WorkShiftEntity;
import com.bumu.bran.model.entity.attendance.*;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.bran.workshift.command.WorkShiftRuleQueryCommand;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeDao;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.command.DateTimeRange;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.WorkAttendanceHttpRecordsResult;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author majun
 * @date 2017/3/21
 */
@Service
public class WorkAttendanceServiceImpl implements WorkAttendanceService {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceServiceImpl.class);
    @Autowired
    private WorkAttendanceMybatisDao workAttendanceMybatisDao;

    @Autowired
    private WorkAttendanceDao workAttendanceDao;

    @Autowired
    private AttendceDetailDao worAttendceDetailDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private WorkAttendanceGetRecordsHelper workAttendanceGetRecordsHelper;

    @Autowired
    private WorkAttendanceModifyBuilderManager builderManager;

    @Autowired
    private AttendanceAppealFeedBackDao attendanceAppealFeedBackDao;

    @Autowired
    private AttendancesFeedBackDao attendancesFeedBackDao;

    @Autowired
    private ExcelExportHelper excelExportHelper;

    @Autowired
    private BranAdminConfigService configService;

    @Autowired
    private WorkAttendanceLeaveDao workAttendanceLeaveDao;

    @Autowired
    private WorkAttendanceOvertimeDao workAttendanceOvertimeDao;

    @Autowired
    private WorkAttendanceEmpService workAttendanceEmpService;

    @Autowired
    private BranWorkShiftTypeDao branWorkShiftTypeDao;

    @Autowired
    private AttendanceSettingDao attendanceSettingDao;

    @Autowired
    private WorkShiftDao workShiftDao;

    @Autowired
    private WorkAttendanceLeaveService workAttendanceLeaveService;

    @Autowired
    private WorkAttendanceOvertimeService workAttendanceOvertimeService;

    @Autowired
    private ApprovalDao approvalDao;

    @Autowired
    private ApprovalHelper approvalHelper;

    @Autowired
    private LeaveEmployeeDao leaveEmployeeDao;

    @Override
    public WorkAttendanceStateResult getState() {
        List<BaseCommand.IDNameCommand> all = Arrays.stream(WorkAttendanceAllState.values())
                .map(one -> {
                    return new BaseCommand.IDNameCommand(String.valueOf(one.ordinal()), one.name());
                })
                .collect(Collectors.toList());

        all.add(new BaseCommand.IDNameCommand("10", "请假"));
        all.add(new BaseCommand.IDNameCommand("11", "请假"));


        return new WorkAttendanceStateResult(
                Arrays.stream(WorkAttendanceModifyState.values())
                        .map(one -> {
                            return new BaseCommand.IDNameCommand(String.valueOf(one.getState()), one.name());
                        })
                        .collect(Collectors.toList()), all);

    }

    @Override
    public WorkAttendanceOneMonthResult getOneDayOrMonthList(WorkAttendanceQueryCommand command, SessionInfo sessionInfo) {
        logger.info("getOneDayOrMonthList ... ");
       /* if (command.getQueryType() != null && command.getQueryType() == WorkAttendanceQueryCommand.QueryType.day) {
            command.setQueryOneMonth(command.getQueryOneMonth().withTime(0, 0, 0, 0));
        }*/
        logger.info("查询时间: " + command.getQueryOneMonth().toDate());
        command.setBranCorpId(sessionInfo.getCorpId());
        command.initDateTimeRange();
        return new WorkAttendanceOneMonthResult(workAttendanceMybatisDao.getOneDayOrMonthList(command));
    }

    @Override
    public void updateOne(WorkAttendanceCommand command, SessionInfo sessionInfo) {
        logger.info("查询考勤");
        WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByIdNotDelete(command.getId());
        if (workAttendanceEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有查询到考勤记录");
        }
        logger.info("设置被管理员修改...");
        workAttendanceEntity.setModify(true);

        logger.info("查询考勤详情");
        WorkAttendanceDetailEntity detailEntity = worAttendceDetailDao.findByDetailId(
                workAttendanceEntity.getWorkAttendanceDetailId());

        logger.info("查询申诉");
        List<WorkAttendanceAppealFeedBackEntity> appeals = attendanceAppealFeedBackDao.findByWorkAttendanceId(workAttendanceEntity.getId());

        logger.info("appeals: " + appeals.toString());

        // 上班申诉
        WorkAttendanceAppealFeedBackEntity in = null;

        // 下班申诉
        WorkAttendanceAppealFeedBackEntity out = null;

        if (!ListUtils.checkNullOrEmpty(appeals)) {
            Optional<WorkAttendanceAppealFeedBackEntity> optionalIn = appeals
                    .stream()
                    .filter(WorkAttendanceAppealFeedBackEntity::isInClockType)
                    .findFirst();

            if (optionalIn.isPresent()) {
                in = optionalIn.get();
                logger.info("上班申诉: " + in);
            }

            Optional<WorkAttendanceAppealFeedBackEntity> optionalOut = appeals
                    .stream()
                    .filter(WorkAttendanceAppealFeedBackEntity::isOutClockType)
                    .findFirst();

            if (optionalOut.isPresent()) {
                out = optionalOut.get();
                logger.info("下班申诉: " + out);
            }
        }

        // 查询正式员工
        EmployeeEntity employeeEntity = employeeDao.findEmployeeById(workAttendanceEntity.getEmpId());
        // 查询当天已发布的班次
        WorkShiftRuleQueryCommand workShiftRuleQueryCommand = new WorkShiftRuleQueryCommand();
        workShiftRuleQueryCommand.setBranCorpId(sessionInfo.getCorpId());
        workShiftRuleQueryCommand.setPublishedState(1);
        workShiftRuleQueryCommand.setQueryDate(command.getAttendStartTime().getMillis());
        workShiftRuleQueryCommand.setEmpId(employeeEntity.getId());

        WorkShiftTypeEntity workShiftTypeEntity = workAttendanceGetRecordsHelper.getWorkTypeByDateTime(workShiftRuleQueryCommand);
        if (workShiftTypeEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请先设置排班班次并且已发布");
        }
        workShiftTypeEntity.setTargetTime(new DateTime(workAttendanceEntity.getAttendDay()));
        // start build
        WorkAttendanceBuildable builder = null;

        if ("休".equals(workShiftTypeEntity.getShortName())) {
            logger.info("休息只能编辑加班和清空  ... ");
            if (command.getState() == WorkAttendanceEntity.State.clear) {
                builder = builderManager.getModifyBuilder(WorkAttendanceEntity.State.clear);
            } else {
                builder = builderManager.getModifyBuilder(WorkAttendanceEntity.State.restOverTime);
            }
            builder.setUp(
                    workShiftTypeEntity,
                    sessionInfo,
                    employeeEntity,
                    new DateTimeRange(command.getAttendStartTime(), command.getAttendEndTime()),
                    workAttendanceEntity,
                    detailEntity,
                    in,
                    out
            );
            builder.build();
            return;

        }
        builder = builderManager.getModifyBuilder(command.getState());
        builder.setUp(
                workShiftTypeEntity,
                sessionInfo,
                employeeEntity,
                new DateTimeRange(command.getAttendStartTime(), command.getAttendEndTime()),
                workAttendanceEntity,
                detailEntity,
                in,
                out
        );
        builder.build();
    }

    @Override
    public void clean(NewWorkAttendanceCleanCommand command, SessionInfo sessionInfo) {
        logger.info("查询考勤");
        WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByIdNotDelete(command.getId());
        if (workAttendanceEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有查询到考勤记录");
        }
        logger.info("设置被管理员修改...");
        workAttendanceEntity.setModify(true);

        logger.info("查询考勤详情");
        WorkAttendanceDetailEntity detailEntity = worAttendceDetailDao.findByDetailId(
                workAttendanceEntity.getWorkAttendanceDetailId());

        logger.info("查询申诉");
        List<WorkAttendanceAppealFeedBackEntity> appeals = attendanceAppealFeedBackDao.findByWorkAttendanceId(workAttendanceEntity.getId());

        logger.info("appeals: " + appeals.toString());

        // 上班申诉
        WorkAttendanceAppealFeedBackEntity in = null;

        // 下班申诉
        WorkAttendanceAppealFeedBackEntity out = null;

        if (!ListUtils.checkNullOrEmpty(appeals)) {
            Optional<WorkAttendanceAppealFeedBackEntity> optionalIn = appeals
                    .stream()
                    .filter(WorkAttendanceAppealFeedBackEntity::isInClockType)
                    .findFirst();

            if (optionalIn.isPresent()) {
                in = optionalIn.get();
                logger.info("上班申诉: " + in);
            }

            Optional<WorkAttendanceAppealFeedBackEntity> optionalOut = appeals
                    .stream()
                    .filter(WorkAttendanceAppealFeedBackEntity::isOutClockType)
                    .findFirst();

            if (optionalOut.isPresent()) {
                out = optionalOut.get();
                logger.info("下班申诉: " + out);
            }
        }

        // 查询正式员工
        EmployeeEntity employeeEntity = employeeDao.findEmployeeById(workAttendanceEntity.getEmpId());
        // 查询当天班次
        if (StringUtils.isAnyBlank(workAttendanceEntity.getWorkShiftTypeId())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请确认该考勤的班次信息");
        }
        WorkShiftTypeEntity workShiftTypeEntity = branWorkShiftTypeDao.findByIdNotDelete(workAttendanceEntity.getWorkShiftTypeId());
        if (workShiftTypeEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请先设置排班周期");
        }
        workShiftTypeEntity.setTargetTime(new DateTime(workAttendanceEntity.getAttendDay()));
        // start build
        WorkAttendanceBuildable builder = null;

        builder = builderManager.getModifyBuilder(WorkAttendanceEntity.State.clear);
        builder.setUp(
                workShiftTypeEntity,
                sessionInfo,
                employeeEntity,
                new DateTimeRange(),
                workAttendanceEntity,
                detailEntity,
                null,
                null
        );
        builder.build();
        return;
    }

    @Override
    public void update(NewWorkAttendanceUpdateCommand command, SessionInfo sessionInfo) throws Exception {

        logger.info("参数: " + command.toString());

        WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByIdNotDelete(command.getId());
        Assert.notNull(workAttendanceEntity, "没有查询的到考勤信息");
        if (StringUtils.isAnyBlank(workAttendanceEntity.getWorkShiftTypeId())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "数据无班次信息，请校验是否是历史数据");
        }
        if (workAttendanceEntity.getAttendDay().after(new Date())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "不可修改今天及以后的考勤数据");
        }
        WorkShiftTypeEntity workShiftTypeEntity = branWorkShiftTypeDao.findByIdNotDelete(workAttendanceEntity.getWorkShiftTypeId());
        Assert.notNull(workShiftTypeEntity, "无该考勤班次信息，请校验是否是历史数据");
        if (StringUtils.isNotBlank(command.getWorkShiftTypeId())) {
            workShiftTypeEntity = branWorkShiftTypeDao.findById(command.getWorkShiftTypeId());
            workAttendanceEntity.setHasModifyWorkShift(Constants.TRUE);
        }

        workAttendanceEntity.setWorkShiftTypeId(command.getWorkShiftTypeId());
        workShiftTypeEntity.setTargetTime(new DateTime(workAttendanceEntity.getAttendDay()));
        if ((!"休".equals(workShiftTypeEntity.getShortName())) && !checkIsMidClock(workShiftTypeEntity.getMidClockTime(), workAttendanceEntity.getAttendDay(), command)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请确认修改时间是否在中间时间点");
        }
        if (checkIsApprovaling(workAttendanceEntity.getEmpId(), workAttendanceEntity.getAttendDay())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "有未处理的审批，无法修改考勤");
        }


        workShiftTypeEntity.setTargetTime(new DateTime(workAttendanceEntity.getAttendDay()));

        final List<WorkAttendanceHttpRecordsResult> records = new ArrayList<>();
        WorkAttendanceHttpRecordsResult startResult = new WorkAttendanceHttpRecordsResult();
        WorkAttendanceHttpRecordsResult endResult = new WorkAttendanceHttpRecordsResult();

        Date start = null;
        Date end = null;
        if (command.getAttendStartModifyState() == 0) {
            // 不修改用原来的打卡时间
            start = workAttendanceEntity.getAttendStartTime();


        } else if (command.getAttendStartModifyState() == 1) {
            // 修改的话用传入的时间
            if (command.getAttendStartTime() != null) {
                start = DateTimeUtils.generateFromHourAndMinutesStr(new DateTime(workAttendanceEntity.getAttendDay()), command.getAttendStartTime()).toDate();
                if (command.getAttendStartMorrow() != null && command.getAttendStartMorrow() == 1) {
                    start = new DateTime(start).plusDays(1).toDate();
                }
            }

        } else if (command.getAttendStartModifyState() == 2) {
            // 缺卡的话null
            start = null;
        }

        if (command.getAttendEndModifyState() == 0) {
            // 不修改用原来的打卡时间
            end = workAttendanceEntity.getAttendEndTime();


        } else if (command.getAttendEndModifyState() == 1) {
            // 修改的话用传入的时间
            if (command.getAttendEndTime() != null) {
                end = DateTimeUtils.generateFromHourAndMinutesStr(new DateTime(workAttendanceEntity.getAttendDay()), command.getAttendEndTime()).toDate();
                if (command.getAttendEndMorrow() != null && command.getAttendEndMorrow() == 1) {
                    end = new DateTime(end).plusDays(1).toDate();
                }
            }

        } else if (command.getAttendEndModifyState() == 2) {
            // 缺卡的话null
            end = null;
        }

        logger.info("start: " + start);
        logger.info("end: " + end);
        if (start != null) {
            startResult.setAttendDate(start.getTime());
        }
        if (end != null) {
            endResult.setAttendDate(end.getTime());
        }
        records.add(startResult);
        records.add(endResult);


        EmployeeEntity employeeEntity = employeeDao.findEmployeeById(workAttendanceEntity.getEmpId());
        WorkAttendanceDetailEntity workAttendanceDetailEntity = worAttendceDetailDao.findByDetailId(workAttendanceEntity.getWorkAttendanceDetailId());
        Assert.notNull(workAttendanceDetailEntity, "没有查询到考勤详情");

        if (!"休".equals(workShiftTypeEntity.getShortName())) {
            workAttendanceLeaveService.addLeave(workAttendanceEntity.getEmpId(), workAttendanceEntity.getAttendDay(), workShiftTypeEntity, command.getLeaveCommandList(), sessionInfo);
            workAttendanceOvertimeService.addOvertime(workAttendanceEntity.getEmpId(), workAttendanceEntity.getAttendDay(), workShiftTypeEntity, command.getOvertimeCommandList(), sessionInfo);
        } else {
            //如果修改休，则删除原有的请假数据
            workAttendanceLeaveService.deleteLeaves(workAttendanceEntity.getEmpId(), workAttendanceEntity.getAttendDay());
            command.getLeaveCommandList().clear();
            workAttendanceOvertimeService.addOvertime(workAttendanceEntity.getEmpId(), workAttendanceEntity.getAttendDay(), workShiftTypeEntity, command.getOvertimeCommandList(), sessionInfo);
        }

        // 初始化
        workAttendanceDetailEntity.toInitState();
        workAttendanceEntity.toInitState();

        if ("休".equals(workShiftTypeEntity.getShortName())) {

            WorkAttendanceBuildable rest = builderManager.getModifyBuilder(WorkAttendanceEntity.State.restOverTime);
            rest.setUp(
                    workShiftTypeEntity,
                    sessionInfo,
                    employeeEntity,
                    new DateTimeRange(start == null ? null : new DateTime(start), end == null ? null : new DateTime(end)),
                    workAttendanceEntity,
                    workAttendanceDetailEntity,
                    null,
                    null
            );
            rest.build();
            return;
        }

        // 请假
        WorkAttendanceBuildable workAttendanceModifyLeaveBuilder = null;

        workAttendanceModifyLeaveBuilder = builderManager.getModifyBuilder(WorkAttendanceEntity.State.leave);
        workAttendanceModifyLeaveBuilder.setUp(
                workShiftTypeEntity,
                sessionInfo,
                employeeEntity,
                new DateTimeRange(start == null ? null : new DateTime(start), end == null ? null : new DateTime(end)),
                workAttendanceEntity,
                workAttendanceDetailEntity,
                null,
                null
        );

        workAttendanceEmpService.syncWorkAttendanceRecord(workShiftTypeEntity, records, employeeEntity,
                workAttendanceEntity, workAttendanceDetailEntity);

        workAttendanceModifyLeaveBuilder.build();


        //删除申诉数据
        List<WorkAttendanceFeedBackEntity> feedBackEntityList = attendancesFeedBackDao.findListByAttendanceDataAndId(workAttendanceEntity.getAttendDay(), workAttendanceEntity.getEmpId());
        for (WorkAttendanceFeedBackEntity feedBackEntity : feedBackEntityList) {
            feedBackEntity.setIsDelete(Constants.TRUE);
            attendancesFeedBackDao.update(feedBackEntity);
        }
    }

    /**
     * 查询员工当天是否有未处理的审批数据
     *
     * @param empId
     * @param attendanceDay
     * @return
     */
    public Boolean checkIsApprovaling(String empId, Date attendanceDay) {
        //查询当天 改员工 待处理的审批数据
        List<ApprovalEntity> approvalEntityList = approvalHelper.getStateList(empId, attendanceDay, 0);
        return CollectionUtils.isEmpty(approvalEntityList) ? false : true;
    }

    /**
     * 确认是否是中间打卡时间点打卡异常
     *
     * @param midClockTime
     * @param attendanceDay
     * @param command
     * @return
     * @throws Exception
     */
    public Boolean checkIsMidClock(DateTime midClockTime, Date attendanceDay, NewWorkAttendanceUpdateCommand command) throws Exception {
        //开始时间
        if (command.getAttendStartModifyState() == 1) {
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtils.parseDate(command.getAttendStartTime(), "HH:mm"));
            Date startTimeDate = DateUtils.setHours(attendanceDay, c.get(Calendar.HOUR_OF_DAY));
            startTimeDate = DateUtils.setMinutes(startTimeDate, c.get(Calendar.MINUTE));
            Long midStartTime = Constants.TRUE == command.getAttendStartMorrow() ? DateUtils.addDays(startTimeDate, 1).getTime() : startTimeDate.getTime();
            if (midStartTime > midClockTime.getMillis()) {
                return false;
            }
        }

        //结束时间点
        if (command.getAttendEndModifyState() == 1) {
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtils.parseDate(command.getAttendEndTime(), "HH:mm"));
            Date endTimeDate = DateUtils.setHours(attendanceDay, c.get(Calendar.HOUR_OF_DAY));
            endTimeDate = DateUtils.setMinutes(endTimeDate, c.get(Calendar.MINUTE));
            Long midEndTime = Constants.TRUE == command.getAttendEndMorrow() ? DateUtils.addDays(endTimeDate, 1).getTime() : endTimeDate.getTime();
            if (midEndTime < midClockTime.getMillis()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public NewWorkAttendanceViewResult view(String workAttendId) throws Exception {
        NewWorkAttendanceViewResult result = new NewWorkAttendanceViewResult();
        logger.info("查询考勤");
        WorkAttendanceEntity entity = workAttendanceDao.findByIdNotDelete(workAttendId);
        if (null == entity) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有查询到考勤记录");
        }
        if (DateUtils.isSameDay(entity.getAttendDay(), new Date()) || entity.getAttendDay().after(new Date())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "不可修改今天及以后的考勤数据");
        }
        //获取考勤数据
        if (StringUtils.isNotBlank(entity.getWorkAttendanceDetailId())) {
            WorkAttendanceDetailEntity detailEntity = worAttendceDetailDao.findByDetailId(entity.getWorkAttendanceDetailId());
            if (null != detailEntity) {
                result.initResult(entity, detailEntity);
            }
        }

        //查找员工信息
        EmployeeEntity employeeEntity = employeeDao.findEmployeeById(entity.getEmpId());
        if (null == employeeEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有找到该员工信息");
        }
        result.initResult(employeeEntity);

        //班次信息;
        Long workShiftTypeInTime = 0L;
        Long workShiftTypeOutTime = 0L;
        if (StringUtils.isNotBlank(entity.getWorkShiftTypeId())) {
            WorkShiftTypeEntity workShiftTypeEntity = branWorkShiftTypeDao.findByIdNotDelete(entity.getWorkShiftTypeId());
            if (null != workShiftTypeEntity) {
                result.initResult(workShiftTypeEntity);
                if (!("休").equals(workShiftTypeEntity.getShortName())) {
                    //计算当天的上班时间点和 下班时间点
                    workShiftTypeInTime = workShiftTypeEntity.getInTimeValue(DateUtils.parseDate(result.getAttendDay(), "yyyy-MM-dd"));
                    //班次下班时间
                    workShiftTypeOutTime = workShiftTypeEntity.getOutTimeValue(DateUtils.parseDate(result.getAttendDay(), "yyyy-MM-dd"));
                } else {
                    result.setAttendStartState("休息");
                    result.setAttendEndState("休息");
                }
            }
        }

        //查询该用户的请假和加班信息
        List<WorkAttendanceLeaveEntity> leaveEntityList = workAttendanceLeaveDao.getListByEmpIdAndAttendDay(entity.getEmpId(), entity.getAttendDay());
        List<WorkAttendanceOvertimeEntity> overtimeEntityList = workAttendanceOvertimeDao.getListByEmpIdAndAttendDay(entity.getEmpId(), entity.getAttendDay());
        List<NewWorkAttendanceLeaveResult> leaveResultList = new ArrayList<>();
        List<NewWorkAttendanceOvertimeResult> overtimeResultList = new ArrayList<>();
        for (WorkAttendanceLeaveEntity leave : leaveEntityList) {
            NewWorkAttendanceLeaveResult leaveResult = new NewWorkAttendanceLeaveResult();
            SysUtils.copyProperties(leaveResult, leave);
            if (leave.getLeaveStartTime().getTime() == workShiftTypeInTime && workShiftTypeInTime > 0) {
                result.setAttendStartState("请假");
                result.setAttendStartTime("");
                result.setAttendStartIsMorrow(Constants.FALSE);
            }
            if (leave.getLeaveTimeEnd().getTime() == workShiftTypeOutTime && workShiftTypeOutTime > 0) {
                result.setAttendEndState("请假");
                result.setAttendEndTime("");
                result.setAttendEndIsMorrow(Constants.FALSE);
            }
            leaveResult.init(leave);
            ApprovalEntity approvalEntity = StringUtils.isAnyBlank(leaveResult.getApprovalId()) ? null : approvalDao.findByIdNotDelete(leaveResult.getApprovalId());
            if (null != approvalEntity) {
                leaveResult.setApprovalNo(approvalEntity.getApprovalNo());
            }
            leaveResultList.add(leaveResult);
        }
        for (WorkAttendanceOvertimeEntity overtime : overtimeEntityList) {
            NewWorkAttendanceOvertimeResult overtimeResult = new NewWorkAttendanceOvertimeResult();
            SysUtils.copyProperties(overtimeResult, overtime);
            overtimeResult.init(overtime);
            ApprovalEntity approvalEntity = StringUtils.isAnyBlank(overtimeResult.getApprovalId()) ? null : approvalDao.findByIdNotDelete(overtimeResult.getApprovalId());
            if (null != approvalEntity) {
                overtimeResult.setApprovalNo(approvalEntity.getApprovalNo());
            }
            overtimeResultList.add(overtimeResult);
        }
        result.setWorkAttendanceLeaveResultList(leaveResultList);
        result.setWorkAttendanceOvertimeResultList(overtimeResultList);
        return result;
    }

    @Override
    public List<NewWorkAttendanceModifyResult> modifyList(String workAttendanceId) {
        return null;
    }

    @Override
    public FileUploadFileResult getExporUrl(String empId, Long yearMonth, String settingId, Integer isOnJob) throws Exception {
        /*WorkAttendanceEmpMonthResult queryResult = getEmpMonthDataList(empId, yearMonth, sessionInfo);
        String exportFilePath = configService.getExcelTemplateLocation() + BranAdminConfigService.WORK_ATTENDANCE_TEMPLATE;
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        excelExportHelper.export(
                exportFilePath,
                yearMonth + "考勤明细",
                new HashMap() {{
                    put("list", queryResult.getAttendances());
                    put("attendanceDate", yearMonth);
                    put("exportTime", format.format(System.currentTimeMillis()));
                }},
                response
        );*/
        EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete(empId);
        if (null == employeeEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有查到该员工");
        }
        FileUploadFileResult result = new FileUploadFileResult();
        result.setUrl(AttendanceConstants.ATTENDANCE_DETAIL_EXPORT_URL + "?empId=" + empId + "&yearMonth=" + yearMonth + "&settingId=" + settingId + "&isOnJob=" + isOnJob);
        return result;
    }

    @Override
    public void exportList(String empId, Long yearMonth, String settingId, Integer isOnJob, SessionInfo sessionInfo, HttpServletResponse response) throws Exception {
        WorkAttendanceEmpMonthResult queryResult = getEmpMonthDataList(yearMonth, settingId, empId, isOnJob, sessionInfo);
        EmployeeEntity employeeEntity = employeeDao.findEmployeeById(empId);
        String exportFilePath = configService.getExcelTemplateLocation() + BranAdminConfigService.WORK_ATTENDANCE_TEMPLATE;
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        logger.info("文件路径》》" + exportFilePath);
        logger.info("queryResult is null:-》" + queryResult);
        excelExportHelper.export(
                exportFilePath,
                "考勤明细_" + (null != employeeEntity ? employeeEntity.getRealName() : "") + "_"
                        + (null == queryResult ? "" : DateTimeUtils.format(new DateTime(queryResult.getQueryStartTime()), "yyyyMMdd")) + "_"
                        + (null == queryResult ? "" : DateTimeUtils.format(new DateTime(queryResult.getQueryEndTime()), "yyyyMMdd")),
                new HashMap() {{
                    put("list", null == queryResult || CollectionUtils.isEmpty(queryResult.getAttendances()) ? new ArrayList<NewWorkAttendanceResult>() : queryResult.getAttendances());
                    put("attendanceDate", yearMonth);
                    put("exportTime", format.format(System.currentTimeMillis()));
                }},
                response
        );
    }

    @Override
    public WorkAttendanceEmpMonthResult getEmpMonthDataList(Long yearMonth, String settingId, String empId, Integer isOnJob, SessionInfo sessionInfo) throws Exception {
        logger.info("getOneDayOrMonthList ... ");

        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findAttendEntityById(settingId);
        if (settingEntity == null) {
            return null;
        }
        if (StringUtils.isAnyBlank(empId)) {
            return null;
            //throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "请选择一个员工");
        }
        //查询月份开始时间和结束时间
        Long startDate = transformStartTime(yearMonth, settingEntity.getCycleStart());
        Long endDate = transformEndTime(yearMonth, settingEntity.getCycleEnd(), settingEntity.getCycleStart(), settingEntity.getIsNextMonth());

        EmployeeEntity employeeEntity = employeeDao.findByUniqueParam("id", empId);
        if (null == employeeEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有查到该员工");
        }

        WorkAttendanceQueryCommand queryCommand = new WorkAttendanceQueryCommand();
        queryCommand.setBranCorpId(sessionInfo.getCorpId());
        queryCommand.setQueryOneMonth(new DateTime(yearMonth));
        queryCommand.setQueryTimeStart(new Date(startDate));
        queryCommand.setQueryTimeEnd(new Date(endDate));
        queryCommand.setSettingId(settingId);
        queryCommand.setEmpId(employeeEntity.getId());
        queryCommand.initDateTimeRange();
        //记录考勤设置下的班组
        Boolean isInWorkShift = false;

        List<WorkShiftEntity> workShiftEntityList = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(settingId, sessionInfo.getCorpId());
        for (WorkShiftEntity entity : workShiftEntityList) {
            if (employeeEntity.getWorkShiftId().equals(entity.getId())) {
                isInWorkShift = true;
                break;
            }
        }
        if (!isInWorkShift) {
            return null;
        }

        List<NewWorkAttendanceResult> resultList = workAttendanceMybatisDao.getEmpMonthData(queryCommand);
        for (NewWorkAttendanceResult result : resultList) {
            if (result.getClockType() == WorkAttendanceEnum.ClockType.device) {
                result.setAttendAddressEnd("-");
                result.setAttendAddressStart("-");
            } else {
                result.setDeviceNo("-");
                if (StringUtils.isAnyBlank(result.getAttendStartTime())) {
                    result.setAttendAddressStart("-");
                }
                if (StringUtils.isAnyBlank(result.getAttendEndTime())) {
                    result.setAttendAddressEnd("-");
                }
            }
            if (StringUtils.isNotBlank(result.getWorkShiftTypeId())) {
                WorkShiftTypeEntity workShiftTypeEntity = branWorkShiftTypeDao.findByIdNotDelete(result.getWorkShiftTypeId());
                if (workShiftTypeEntity.getShortName().equals("休")) {
                    continue;
                }
                checkInOutState(result, workShiftTypeEntity);
            }

        }
        WorkAttendanceEmpMonthResult result = new WorkAttendanceEmpMonthResult(resultList);
        result.setQueryStartTime(startDate);
        result.setQueryEndTime(endDate);
        return result;
    }

    public void checkInOutState(NewWorkAttendanceResult result, WorkShiftTypeEntity workShiftTypeEntity) throws Exception {
        logger.info("查询日期->" + DateUtils.parseDate(result.getAttendDay(), "yyyy-MM-dd"));
        List<WorkAttendanceLeaveEntity> leaveEntityList = workAttendanceLeaveDao.getListByEmpIdAndAttendDay(result.getEmpId(), DateUtils.parseDate(result.getAttendDay(), "yyyy-MM-dd"));
        logger.info("请假记录数为" + leaveEntityList.size());
        logger.info("上下班的状态结果为" + result.getAttendStartStateString() + "," + result.getAttendEndStateString());
        Long workShiftTypeInTime = workShiftTypeEntity.getInTimeValue(DateUtils.parseDate(result.getAttendDay(), "yyyy-MM-dd"));
        Long workShiftTypeOutTime = workShiftTypeEntity.getOutTimeValue(DateUtils.parseDate(result.getAttendDay(), "yyyy-MM-dd"));
        for (WorkAttendanceLeaveEntity leaveEntity : leaveEntityList) {
            if (leaveEntity.getLeaveStartTime().getTime() == workShiftTypeInTime) {
                result.setAttendStartStateString("请假");
                result.setAttendStartTime("");
            }
            if (leaveEntity.getLeaveTimeEnd().getTime() == workShiftTypeOutTime) {
                result.setAttendEndStateString("请假");
                result.setAttendEndTime("");
            }
        }
    }

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
}
