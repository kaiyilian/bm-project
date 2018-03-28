package com.bumu.bran.admin.attendance.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysLogDao;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceManagerDao;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceUserSynDao;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceWorkShiftDao;
import com.bumu.attendance.model.entity.WorkAttendanceDeviceManagerEntity;
import com.bumu.attendance.model.entity.WorkAttendanceDeviceWorkShiftEntity;
import com.bumu.bran.admin.attendance.result.WorkAttendanceDeviceResult;
import com.bumu.bran.admin.attendance.service.WorkAttendanceSettingService;
import com.bumu.bran.admin.corporation.result.WorkShiftListResult;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import com.bumu.bran.attendance.command.setting.WorkAttendanceAddSettingCommand;
import com.bumu.bran.attendance.command.setting.WorkAttendanceDeviceWorkShiftCommand;
import com.bumu.bran.attendance.model.dao.AttendanceSettingDao;
import com.bumu.bran.attendance.model.dao.AttendancesFeedBackDao;
import com.bumu.bran.attendance.model.dao.WorkAttendanceClockSettingDao;
import com.bumu.bran.attendance.model.dao.mybatis.WorkAttendanceMybatisDao;
import com.bumu.bran.attendance.result.WorkAttendanceSettingPageResult;
import com.bumu.bran.attendance.result.WorkAttendanceSettingResult;
import com.bumu.bran.attendance.service.WorkAttendanceRangeService;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.model.entity.WorkShiftEntity;
import com.bumu.bran.model.entity.attendance.WorkAttendanceClockSettingEntity;
import com.bumu.bran.model.entity.attendance.WorkAttendanceFeedBackEntity;
import com.bumu.bran.model.entity.attendance.WorkAttendanceSettingEntity;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.ModelResult;
import com.bumu.common.result.Pager;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by DaiAoXiang on 2017/3/29.
 */
@Service
public class WorkAttendanceSettingServiceImpl implements WorkAttendanceSettingService {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceSettingServiceImpl.class);

    @Autowired
    private WorkShiftDao workShiftDao;

    @Autowired
    private AttendanceSettingDao attendanceSettingDao;

    @Autowired
    private BranOpLogDao branOpLogDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private AttendancesFeedBackDao attendancesFeedBackDao;

    @Autowired
    private WorkAttendanceRangeService workAttendanceRangeService;

    @Autowired
    private WorkAttendanceDeviceManagerDao workAttendanceDeviceManagerDao;

    @Autowired
    private WorkAttendanceDeviceWorkShiftDao workAttendanceDeviceWorkShiftDao;

    @Autowired
    private BranCorpService branCorpService;

    @Autowired
    private WorkAttendanceClockSettingDao workAttendanceClockSettingDao;

    @Autowired
    private WorkAttendanceDeviceUserSynDao workAttendanceDeviceUserSynDao;

    @Autowired
    private WorkAttendanceMybatisDao workAttendanceMybatisDao;

    @Override
    public WorkAttendanceSettingPageResult getCorpAllSetting(String workShiftId, SessionInfo sessionInfo, int page, int pageSize) throws Exception {
        WorkAttendanceSettingPageResult workAttendanceSettingPageResult = new WorkAttendanceSettingPageResult();
        List<WorkAttendanceSettingPageResult.WorkAttendaceSetting> workAttendanceSettingResults = new ArrayList<>();
        if (sessionInfo.getCorpId() == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        Pager<WorkAttendanceSettingEntity> workAttendanceSettingEntityPager = null;
        if (StringUtils.isAnyBlank(workShiftId)) {
            workAttendanceSettingEntityPager = attendanceSettingDao.findAttendEntityByCorpId(sessionInfo.getCorpId(), page, pageSize);
        } else {
            WorkShiftEntity workShiftEntity = workShiftDao.findWorkAttendanceSettingIdsById(workShiftId);
            if (workShiftEntity.getWorkAttendanceSettingId() == null) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_SHIFT_NOT_HAVE_WORK_ATTENDANCE_SETTING);
            }
            workAttendanceSettingEntityPager = attendanceSettingDao.findAttendendEntityById(workShiftEntity.getWorkAttendanceSettingId(), page, pageSize);
        }
        if (workAttendanceSettingEntityPager.getResult().size() == 0) {
            return workAttendanceSettingPageResult;
        }
        for (WorkAttendanceSettingEntity workAttendanceSettingEntity : workAttendanceSettingEntityPager.getResult()) {
            WorkAttendanceSettingPageResult.WorkAttendaceSetting workAttendanceSettingResult = new WorkAttendanceSettingPageResult.WorkAttendaceSetting();
            workAttendanceSettingResult.setId(workAttendanceSettingEntity.getId());
            workAttendanceSettingResult.setCycleStart(workAttendanceSettingEntity.getCycleStart());
            workAttendanceSettingResult.setCycleEnd(workAttendanceSettingEntity.getCycleEnd());
            workAttendanceSettingResult.setIsNextMonth(workAttendanceSettingEntity.getIsNextMonth());
            List<WorkShiftEntity> shiftEntities = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(workAttendanceSettingEntity.getId(), sessionInfo.getCorpId());
            List<WorkAttendanceSettingPageResult.WorkShift> workShifts = new ArrayList<>();
            if (shiftEntities != null) {
                for (WorkShiftEntity entity : shiftEntities) {
                    WorkAttendanceSettingPageResult.WorkShift workShift = new WorkAttendanceSettingPageResult.WorkShift();
                    workShift.setWorkShiftId(entity.getId());
                    workShift.setWorkShiftName(entity.getShiftName());
                    workShifts.add(workShift);
                }
                workAttendanceSettingResult.setWorkShifts(workShifts);
            }
            workAttendanceSettingResults.add(workAttendanceSettingResult);
        }
        workAttendanceSettingPageResult.setWorkAttendaceSettings(workAttendanceSettingResults);
        workAttendanceSettingPageResult.setPages(Utils.calculatePages(workAttendanceSettingEntityPager.getRowCount(), workAttendanceSettingEntityPager.getPageSize()));
        workAttendanceSettingPageResult.setRowCount(workAttendanceSettingEntityPager.getRowCount());
        return workAttendanceSettingPageResult;
    }

    @Override
    public WorkAttendanceSettingResult getSetting(String workShiftId, SessionInfo sessionInfo) throws Exception {
        if (StringUtils.isAnyBlank(workShiftId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_NOT_FOUND);
        }
        WorkAttendanceSettingEntity workAttendanceSettingEntity = null;
        WorkShiftEntity workShiftEntity = workShiftDao.findWorkAttendanceSettingIdsById(workShiftId);
        if (workShiftEntity.getWorkAttendanceSettingId() != null) {
            workAttendanceSettingEntity = attendanceSettingDao.findAttendEntityById(workShiftEntity.getWorkAttendanceSettingId());
        }
        if (workAttendanceSettingEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_SHIFT_NOT_HAVE_WORK_ATTENDANCE_SETTING);
        }
        WorkAttendanceSettingResult workAttendanceSettingResult = setSettingResult(workAttendanceSettingEntity, sessionInfo);
        return workAttendanceSettingResult;
    }

    @Override
    public List<ModelResult> getAvailableWorkShiftList(SessionInfo sessionInfo) throws Exception {
        String corpId = sessionInfo.getCorpId();
        List<ModelResult> result = new ArrayList<>();
        List<WorkShiftEntity> workShiftEntities = null;
        if (corpId != null) {
            workShiftEntities = workShiftDao.findtAvailableWorkShiftByBranCorpId(corpId);
        }
        if (workShiftEntities != null) {
            for (WorkShiftEntity entity : workShiftEntities) {
                ModelResult modelResult = new ModelResult();
                modelResult.setId(entity.getId());
                modelResult.setName(entity.getShiftName());
                result.add(modelResult);
            }
        }
        return result;
    }

    @Override
    public void saveSetting(WorkAttendanceAddSettingCommand command, SessionInfo sessionInfo) throws Exception {
        WorkAttendanceSettingEntity workAttendanceSettingEntity = new WorkAttendanceSettingEntity();
        if (command.getWorkShiftId().size() == 0 || command.getWorkShiftId() == null) {
            throw new AryaServiceException(ErrorCode.CODECODE_ATTENDANCE_SETTING_WORK_SHIFT_ID_NOT_NULL);
        }
        SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
        if (command.getIsNextMonth() == Constants.FALSE) {
            if (command.getCycleEnd() < command.getCycleStart()) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_ENDTIME_LESSTHAN_STARTTIME);
            }
        } else {
            if (command.getCycleEnd() > command.getCycleStart()) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_ENDTIME_GREATERTHAN_STARTTIME);
            }
        }

        WorkAttendanceSettingEntity settingEntity = attendanceSettingDao.findByCorpIdAndWorkAttendanceTime(sessionInfo.getCorpId(), command.getCycleStart(), command.getCycleEnd());
        if (StringUtils.isAnyBlank(command.getId())) {
            if (settingEntity != null && settingEntity.getIsNextMonth() == command.getIsNextMonth()) {
                workAttendanceSettingEntity.setId(settingEntity.getId());
            } else {
                workAttendanceSettingEntity.setId(Utils.makeUUID());
            }
        } else {
            workAttendanceSettingEntity = attendanceSettingDao.findAttendEntityById(command.getId());
            if (workAttendanceSettingEntity == null) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_FIND_FAILED);
            }
            List<WorkShiftEntity> shiftEntities = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(workAttendanceSettingEntity.getId(), sessionInfo.getCorpId());
            List<WorkShiftEntity> workShiftEntities = new ArrayList<>();
            if (shiftEntities != null) {
                for (WorkShiftEntity entity : shiftEntities) {
                    entity.setWorkAttendanceSettingId(null);
                    workShiftEntities.add(entity);
                }
                workShiftDao.update(workShiftEntities);
            }
            if (settingEntity != null && settingEntity.getIsNextMonth() == command.getIsNextMonth()) {
                workAttendanceSettingEntity = settingEntity;
                WorkAttendanceSettingEntity attendanceSettingEntity = attendanceSettingDao.findAttendEntityById(command.getId());
                attendanceSettingEntity.setIsDelete(1);
                attendanceSettingDao.update(attendanceSettingEntity);
            }
        }
        List<WorkShiftEntity> workShiftEntities = new ArrayList<>();
        for (String workShiftId : command.getWorkShiftId()) {
            WorkShiftEntity workShiftEntity = workShiftDao.findCorpWorkShiftByIdThrow(workShiftId, sessionInfo.getCorpId());
            if (workShiftEntity == null) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_NOT_FOUND);
            }
            if (workShiftEntity.getWorkAttendanceSettingId() != null) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_SHIFT_ATTENDANCE_SETTING_ID_NOT_NULL);
            }
            workShiftEntity.setWorkAttendanceSettingId(workAttendanceSettingEntity.getId());
            workShiftEntities.add(workShiftEntity);
        }
        workAttendanceSettingEntity.setBranCorpId(sessionInfo.getCorpId());
        workAttendanceSettingEntity.setCycleStart(command.getCycleStart());
        workAttendanceSettingEntity.setCycleEnd(command.getCycleEnd());
        workAttendanceSettingEntity.setIsNextMonth(command.getIsNextMonth());
        workAttendanceSettingEntity.setIsDelete(Constants.FALSE);

        if (StringUtils.isAnyBlank(command.getId())) {
            logger.info("新增考勤配置" + workAttendanceSettingEntity.getId());
            info.setMsg("新增考勤配置" + workAttendanceSettingEntity.getId());
            workAttendanceSettingEntity.setCreateUser(sessionInfo.getUserId());
            workAttendanceSettingEntity.setCreateTime(System.currentTimeMillis());
            try {
                if (settingEntity == null) {
                    attendanceSettingDao.create(workAttendanceSettingEntity);
                }
                workShiftDao.update(workShiftEntities);
                //branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORK_ATTENDANCE_SETTING, BranOpLogEntity.OP_TYPE_ADD, sessionInfo.getUserId(), info);
            } catch (Exception e) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_SAVE_FAILED);
            }
        } else {
            //查询公司是否开通手机打卡
            int isOpenPhoneClock = workAttendanceRangeService.isOpenPhoneClock(sessionInfo.getCorpId());
            logger.info("编辑考勤配置" + command.getId());
            info.setMsg("编辑考勤配置" + command.getId());
            List<WorkShiftEntity> shiftEntities = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(workAttendanceSettingEntity.getId(), sessionInfo.getCorpId());
            if (!ListUtils.checkNullOrEmpty(shiftEntities)) {
                for (WorkShiftEntity workShiftEntity : shiftEntities) {
                    List<String> workShiftId = new ArrayList<>();
                    workShiftId.add(workShiftEntity.getId());
                    List<EmployeeEntity> employeeEntities = employeeDao.findByWorkShiftIds(workShiftId, null, isOpenPhoneClock);
                    for (EmployeeEntity employeeEntity : employeeEntities) {
                        WorkAttendanceFeedBackEntity workAttendanceFeedBackEntity = attendancesFeedBackDao.findByEmployeeId(employeeEntity.getId());
                        if (workAttendanceFeedBackEntity != null) {
                            if (workAttendanceFeedBackEntity.getState() != WorkAttendanceFeedBackEntity.State.confirmed) {
                                throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_CAN_NOT_EDIT, new String[]{workShiftEntity.getShiftName()});
                            }
                        }
                    }
                }
            }

            workAttendanceSettingEntity.setUpdateUser(sessionInfo.getUserId());
            workAttendanceSettingEntity.setUpdateTime(System.currentTimeMillis());
            try {
                attendanceSettingDao.update(workAttendanceSettingEntity);
                workShiftDao.update(workShiftEntities);
                //branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORK_ATTENDANCE_SETTING, BranOpLogEntity.OP_TYPE_UPDATE, sessionInfo.getUserId(), info);
            } catch (Exception e) {
                throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_SAVE_FAILED);
            }
        }
    }

    @Override
    public WorkAttendanceSettingResult getDetail(String id, SessionInfo sessionInfo) {
        if (StringUtils.isAnyBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_FIND_FAILED);
        }
        WorkAttendanceSettingEntity workAttendanceSettingEntity = attendanceSettingDao.findAttendEntityById(id);
        if (workAttendanceSettingEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_NOT_QUERY);
        }
        WorkAttendanceSettingResult workAttendanceSettingResult = setSettingResult(workAttendanceSettingEntity, sessionInfo);

        List<ModelResult> modelResults = new ArrayList<>();
        try {
            modelResults = getAvailableWorkShiftList(sessionInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (modelResults.size() != 0) {
            for (ModelResult modelResult : modelResults) {
                WorkAttendanceSettingResult.WorkShift workShift = new WorkAttendanceSettingResult.WorkShift();
                workShift.setWorkShiftId(modelResult.getId());
                workShift.setWorkShiftName(modelResult.getName());
                workShift.setIsUsed("0");
                workAttendanceSettingResult.getWorkShifts().add(workShift);
            }
        }

        return workAttendanceSettingResult;
    }

    /**
     * 为出勤配置返回信息赋值
     *
     * @param workAttendanceSettingEntity
     * @return
     */
    WorkAttendanceSettingResult setSettingResult(WorkAttendanceSettingEntity workAttendanceSettingEntity, SessionInfo sessionInfo) {
        WorkAttendanceSettingResult workAttendanceSettingResult = new WorkAttendanceSettingResult();
        if (workAttendanceSettingEntity != null) {
            workAttendanceSettingResult.setId(workAttendanceSettingEntity.getId());
            workAttendanceSettingResult.setCycleStart(workAttendanceSettingEntity.getCycleStart());
            workAttendanceSettingResult.setCycleEnd(workAttendanceSettingEntity.getCycleEnd());
            workAttendanceSettingResult.setIsNextMonth(workAttendanceSettingEntity.getIsNextMonth());
        }

        List<WorkAttendanceSettingResult.WorkShift> workShifts = new ArrayList<>();
        List<WorkShiftEntity> shiftEntities = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(workAttendanceSettingEntity.getId(), sessionInfo.getCorpId());
        if (shiftEntities != null) {
            for (WorkShiftEntity entity : shiftEntities) {
                WorkAttendanceSettingResult.WorkShift workShift = new WorkAttendanceSettingResult.WorkShift();
                workShift.setWorkShiftId(entity.getId());
                workShift.setWorkShiftName(entity.getShiftName());
                workShift.setIsUsed("1");
                workShifts.add(workShift);
            }
            workAttendanceSettingResult.setWorkShifts(workShifts);
        }
        return workAttendanceSettingResult;
    }



    @Override
    public void deleteSetting(String id, SessionInfo sessionInfo) {
        if (StringUtils.isAnyBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODECODE_ATTENDANCE_SETTING_WORK_SHIFT_ID_NOT_NULL);
        }
        WorkAttendanceSettingEntity workAttendanceSettingEntity = attendanceSettingDao.findAttendEntityById(id);
        SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
        if (workAttendanceSettingEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_FIND_FAILED);
        }
        List<WorkShiftEntity> shiftEntities = new ArrayList<>();
        List<WorkShiftEntity> workShiftEntities = workShiftDao.findworkShiftIdsByWorkAttendanceSettingId(id, sessionInfo.getCorpId());
        if (workShiftEntities != null) {
            //查询公司是否开通手机打卡
            int isOpenPhoneClock = workAttendanceRangeService.isOpenPhoneClock(sessionInfo.getCorpId());
            for (WorkShiftEntity workShiftEntity : workShiftEntities) {
                List<String> workShiftId = new ArrayList<>();
                workShiftId.add(workShiftEntity.getId());
                List<EmployeeEntity> employeeEntities = employeeDao.findByWorkShiftIds(workShiftId, null, isOpenPhoneClock);
                for (EmployeeEntity employeeEntity : employeeEntities) {
                    WorkAttendanceFeedBackEntity workAttendanceFeedBackEntity = attendancesFeedBackDao.findByEmployeeId(employeeEntity.getId());
                    if (workAttendanceFeedBackEntity != null) {
                        if (workAttendanceFeedBackEntity.getState() != WorkAttendanceFeedBackEntity.State.confirmed) {
                            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_CAN_NOT_DELETE, new String[]{workShiftEntity.getShiftName()});
                        }
                    }
                }
                workShiftEntity.setWorkAttendanceSettingId(null);
                shiftEntities.add(workShiftEntity);
            }
        }
        logger.info("删除考勤配置" + workAttendanceSettingEntity.getId());
        info.setMsg("删除考勤配置" + workAttendanceSettingEntity.getId());
        workAttendanceSettingEntity.setIsDelete(Constants.TRUE);
        workAttendanceSettingEntity.setUpdateTime(System.currentTimeMillis());
        workAttendanceSettingEntity.setUpdateUser(sessionInfo.getUserId());
        try {
            workShiftDao.update(workShiftEntities);
            attendanceSettingDao.update(workAttendanceSettingEntity);
            //branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORK_ATTENDANCE_SETTING, BranOpLogEntity.OP_TYPE_DELETE, sessionInfo.getUserId(), info);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_WORK_ATTENDANCE_SETTING_DELETE_FAILED);
        }
    }

    @Override
    public List<WorkAttendanceDeviceResult> deviceList(SessionInfo sessionInfo) {
        List<WorkAttendanceDeviceResult> result = new ArrayList<>();
        //1.查出企业下关联的考勤机
        List<WorkAttendanceDeviceManagerEntity> deviceManagerEntityList = workAttendanceDeviceManagerDao.findByBranCorpId(sessionInfo.getCorpId());
        if(CollectionUtils.isEmpty(deviceManagerEntityList)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "暂无考勤机");
        }

        Map<String, WorkAttendanceDeviceResult> workAttendanceDeviceMap = new HashedMap();
        deviceManagerEntityList.forEach(entity -> workAttendanceDeviceMap.put(entity.getDeviceNo(), new WorkAttendanceDeviceResult(entity.getDeviceNo(), entity.getDeviceName())));
        List<String> deviceNos = new ArrayList<>();
        deviceNos.addAll(workAttendanceDeviceMap.keySet());
        //获取该该企业 设备、班组关系
        List<WorkAttendanceDeviceWorkShiftEntity> workAttendanceDeviceWorkShiftList = workAttendanceDeviceWorkShiftDao.findByCorpIdDeviceNos(sessionInfo.getCorpId(), deviceNos);

        for (WorkAttendanceDeviceWorkShiftEntity workAttendanceDeviceWorkShiftEntity : workAttendanceDeviceWorkShiftList) {
            if (null == workAttendanceDeviceMap.get(workAttendanceDeviceWorkShiftEntity.getDeviceNo())) {
                //workAttendanceDeviceMap.put(workAttendanceDeviceWorkShiftEntity.getDeviceNo(), new ArrayList<>());
                continue;
            }
            WorkAttendanceDeviceResult deviceResult = workAttendanceDeviceMap.get(workAttendanceDeviceWorkShiftEntity.getDeviceNo());
            deviceResult.getWorkShiftResults().add(deviceResult.new WorkShiftResult(workAttendanceDeviceWorkShiftEntity.getWorkShiftId(), workAttendanceDeviceWorkShiftEntity.getWorkShiftName()));
        }
        result.addAll(workAttendanceDeviceMap.values());
        return result;
    }


    @Override
    public WorkShiftListResult getDeviceWorkShifts(SessionInfo sessionInfo) throws Exception{
        //查询所有班组
        WorkShiftListResult allWorkShifts = branCorpService.getCorpAllWorkShifts(sessionInfo.getCorpId());
        //手机打卡设置的班组
        List<WorkAttendanceClockSettingEntity> clocksettingWorkShifts = workAttendanceClockSettingDao.findByCorpId(sessionInfo.getCorpId());
        Map<String, WorkShiftListResult.WorkShiftResult> resultMap = new HashMap<>();
        allWorkShifts.forEach(tmp -> resultMap.put(tmp.getWorkShiftId(), tmp));
        logger.info("删除手机打卡选择的班组");
        clocksettingWorkShifts.forEach(clockSettingEntity -> resultMap.remove(clockSettingEntity.getWorkShiftId()));

        WorkShiftListResult result = new WorkShiftListResult();
        result.addAll(resultMap.values());
        return result;
    }

    @Override
    public void deviceWorkShiftSave(WorkAttendanceDeviceWorkShiftCommand command, SessionInfo sessionInfo) {
        if (StringUtils.isAnyBlank(command.getDeviceNo())) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "考勤机编号不能为空");
        }
        if (StringUtils.isNotBlank(command.getDeviceName()) && command.getDeviceName().length() > 10) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "考勤机名称不能超过十位");
        }
        //更新名称
        WorkAttendanceDeviceManagerEntity workAttendanceDeviceManagerEntity = workAttendanceDeviceManagerDao.findByBranCorpIdAndDeviceNo(command.getDeviceNo(), sessionInfo.getCorpId());
        workAttendanceDeviceManagerEntity.setDeviceName(command.getDeviceName());
        workAttendanceDeviceManagerDao.update(workAttendanceDeviceManagerEntity);

        List<String> deviceNos = new ArrayList<>();
        deviceNos.add(command.getDeviceNo());
        logger.info("查询出企业下所有的班组");
        List<WorkShiftEntity> workShiftList = workShiftDao.findAllWorkShiftsByBranCorpId(sessionInfo.getCorpId());
        //将班组list转化成MAP
        Map<String, WorkShiftEntity> workShiftEntityMap = new HashedMap();
        workShiftList.forEach(entity -> workShiftEntityMap.put(entity.getId(), entity));

        logger.info("查询企业下设备和班组关联关系");
        List<WorkAttendanceDeviceWorkShiftEntity> deviceWorkShiftEntityList = workAttendanceDeviceWorkShiftDao.findByCorpIdDeviceNos(sessionInfo.getCorpId(), deviceNos);
        List<String> workShiftIds = new ArrayList<>();
        deviceWorkShiftEntityList.forEach(entity -> workShiftIds.add(entity.getWorkShiftId()));
        workShiftIds.addAll(command.getWorkShiftIds());
        //查询所有的班组
        List<EmployeeEntity> oldEmps = employeeDao.findByWorkShiftId(workShiftIds, null, null, Constants.TRUE, Constants.TRUE, null);
        if (CollectionUtils.isNotEmpty(oldEmps)) {
            logger.info("更新旧关联班组员工的状态");
          //  empStatusMaps.forEach(map -> workAttendanceMybatisDao.updateDeviceUserStatus(map));
            oldEmps.forEach(employeeEntity -> {
                employeeEntity.setWorkAttendanceAddState(EmployeeEntity.WorkAttendanceAddState.initial);
                employeeDao.update(employeeEntity);
            });
        }
        //查询本次关联班组下的
        //查询原来关联班组的员工的状态
        /*Map<String, Object> params = new HashMap<>();
        params.put("branCorpId", sessionInfo.getCorpId());
        params.put("deviceNo", command.getDeviceNo());
        params.put("workShiftList", command.getWorkShiftIds());
        if (CollectionUtils.isNotEmpty(command.getWorkShiftIds())) {
            List<Map<String, String>> empNewStatusMaps = workAttendanceMybatisDao.findDeviceWithoutUsers(params);
            if (CollectionUtils.isNotEmpty(empNewStatusMaps)) {
                logger.info("更新新关联班组员工的状态");
                empNewStatusMaps.forEach(map -> workAttendanceMybatisDao.updateEmployeeAttendanceState(map));
            }
        }*/

        logger.info("将这些班组关系废弃");
        deviceWorkShiftEntityList.forEach(entity -> {
            entity.setIsDelete(Constants.TRUE);
        });
        workAttendanceDeviceWorkShiftDao.update(deviceWorkShiftEntityList);
        logger.info("插入新的设备-班组关系");
        for (String workShiftId : command.getWorkShiftIds()) {
            if (null == workShiftEntityMap.get(workShiftId)) {
                continue;
            }
            WorkAttendanceDeviceWorkShiftEntity entity = new WorkAttendanceDeviceWorkShiftEntity();
            entity.setDeviceNo(command.getDeviceNo());
            entity.setDeviceName(command.getDeviceName());
            entity.setId(Utils.makeUUID());
            entity.setCreateTime(new Date().getTime());
            entity.setBranCorpId(sessionInfo.getCorpId());
            entity.setWorkShiftId(workShiftId);
            entity.setWorkShiftName(workShiftEntityMap.get(workShiftId).getShiftName());
            workAttendanceDeviceWorkShiftDao.create(entity);
        }

    }
}
