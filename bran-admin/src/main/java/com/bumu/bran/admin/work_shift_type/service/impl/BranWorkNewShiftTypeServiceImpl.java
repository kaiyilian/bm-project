package com.bumu.bran.admin.work_shift_type.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.WorkShiftTypeEntity;
import com.bumu.arya.model.entity.WorkShiftTypeRoleRelationEntity;
import com.bumu.bran.admin.work_shift_type.service.BranWorkNewShiftTypeService;
import com.bumu.bran.common.Constants;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.workshifttype.*;
import com.bumu.bran.workshift.command.*;
import com.bumu.bran.workshift.model.dao.AbsentSettingDao;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeDao;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeRelationDao;
import com.bumu.bran.workshift.model.dao.RestTimeSettingDao;
import com.bumu.bran.workshift.result.*;
import com.bumu.bran.workshift.util.WorkShiftTypeUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Service
public class BranWorkNewShiftTypeServiceImpl implements BranWorkNewShiftTypeService {

    private Logger log = LoggerFactory.getLogger(BranWorkNewShiftTypeServiceImpl.class);

    @Autowired
    private BranWorkShiftTypeDao branWorkShiftTypeDao;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Autowired
    private WorkShiftTypeUtils workShiftTypeUtils;

    @Autowired
    private RestTimeSettingDao restTimeSettingDao;

    @Autowired
    private AbsentSettingDao absentSettingDao;
    @Autowired
    private BranWorkShiftTypeRelationDao branWorkShiftTypeRelationDao;

    @Override
    public void add(WorkShiftTypeNewCommand workShiftTypeCommand) {
        String id = workShiftTypeCommand.getId();
        WorkShiftTypeEntity e = null;
        boolean isUpdate = false;
        if (StringUtils.isBlank(id)) {
            e = new WorkShiftTypeEntity();
            isUpdate = false;
        } else {
            e = branWorkShiftTypeDao.findById(id);
            isUpdate = true;
        }
        setEntivity(e, workShiftTypeCommand);
        if (isUpdate) {
            branWorkShiftTypeDao.update(e);
        } else {
            branWorkShiftTypeDao.persist(e);
        }
    }

    @Override
    public List<BranWorkShiftTypeNewResult> get(WorkShiftTypeCommand workShiftTypeCommand) {
        List<BranWorkShiftTypeNewResult> results = new LinkedList<>();
        BranWorkShiftTypeNewResult r = null;
        List<WorkShiftTypeEntity> byCorpId = branWorkShiftTypeDao.findByCorpId(workShiftTypeCommand.getBranCorpId());
        int num = byCorpId != null ? byCorpId.size() : 0;
        log.info("查询到全部的班次信息---->  " + num);
        for (WorkShiftTypeEntity e : byCorpId) {
            if (1 == (e.getCreateType() == null ? 0 : e.getCreateType())) {
                continue;
            }
            r = new BranWorkShiftTypeNewResult();
            String id = e.getId();
            r.setId(id);
            r.setName(e.getName());
            r.setShortName(e.getShortName());
            r.setColor(e.getColor());
            r.setBeginTime(e.getWorkStartTime());
            r.setEndTime(e.getWorkEndTime());
            r.setIsNextDay(e.getIsNextDay());
            List<RestTimeSettingEntity> byIdShiftId = restTimeSettingDao.findByIdShiftId(id);
            log.info("休息设置总条数-----> " + byIdShiftId.size());
            //休息
            int is = 0;
            int count = 0;
            for (RestTimeSettingEntity restTimeSettingEntity : byIdShiftId) {
                if (1 == restTimeSettingEntity.getIsOpen()) {
                    is = 1;
                    count++;
                }
            }
            log.info("休息是否开启----> " + is);
            log.info("休息设置的条数----> " + count);
            r.setRestOpen(is);
            r.setRestCount(count);
            //旷工
            List<AbsentSettingEntity> byIdShiftId1 = absentSettingDao.findByIdShiftId(id);
            log.info("旷工设置总条数-----> " + byIdShiftId.size());
            is = 0;
            count = 0;
            for (AbsentSettingEntity absentSettingEntity : byIdShiftId1) {
                if (1 == absentSettingEntity.getIsOpen()) {
                    is = 1;
                    count++;
                }
            }
            log.info("旷工是否开启----> " + is);
            r.setAbsenteeismOpen(is);
            //加班
            OverTimeWorkHoursSettingEntity overTime = e.getOverTimeWorkHoursSettingEntity();
            if (overTime != null) {
                r.setOverTimeOpen(overTime.getOverTimeWorkHoursSettingIsOpen());
                log.info("加班状态----> " + overTime.getOverTimeWorkHoursSettingIsOpen());
            }

            //早退
            HumanityClockTimeSettingEntity h = e.getHumanityClockTimeSettingEntity();
            if (h != null) {
                r.setLeaveEarlyOpen(h.getHumanityClockTimeSettingIsOpen());
                r.setLeaveEarlyCount(h.getAllowEarlyLeaveMinutes());
                //迟到
                r.setLateOpen(h.getHumanityClockTimeSettingIsOpen());
                r.setLateCount(h.getAllowLateMinutes());
                log.info("设置早退的数据----> ");
            }

            List<WorkShiftTypeRoleRelationEntity> work = branWorkShiftTypeRelationDao.findByWorkTypeId(id);
            int isUse = 0;
            for (WorkShiftTypeRoleRelationEntity entity : work) {
                if (!(null!=entity.getWorkShiftType().getCreateType()
                        &&1==entity.getWorkShiftType().getCreateType())) {
                    if (Constants.FALSE == entity.getWorkShiftRule().getIsDelete()) {
                        isUse = 1;
                        break;
                    }
                }
            }
            log.info("该班次是否使用----> " + isUse);
            r.setIsUse(isUse);

            results.add(r);
        }

        return results;
    }

    @Override
    public BranNewWorkShiftTypeResult getById(WorkShiftIdCommand workShiftIdCommand) {
        String id = workShiftIdCommand.getId().trim();
        if (StringUtils.isBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "id必填");
        }
        WorkShiftTypeEntity e = branWorkShiftTypeDao.findById(id);
        BranNewWorkShiftTypeResult r = new BranNewWorkShiftTypeResult();
        if (e != null) {
            entivityToResult(r, e);
        } else {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "没有找到该班次");
        }

        return r;
    }

    @Override
    public List<BranScheduleRestTimeResult> getRest(WorkShiftIdCommand workShiftIdCommand) {
        List<BranScheduleRestTimeResult> results = new LinkedList<>();
        BranScheduleRestTimeResult rest = null;
        String id = workShiftIdCommand.getId();
        if (StringUtils.isBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "id必填");
        }
        List<RestTimeSettingEntity> res = restTimeSettingDao.findByIdShiftId(id);
        for (RestTimeSettingEntity re : res) {
            if (1 == re.getIsOpen()) {
                rest = new BranScheduleRestTimeResult();
                rest.setId(re.getId());
                rest.setEndTime(re.getEndTime());
                rest.setStartTime(re.getStartTime());
                Integer isEndTimeNextDay = re.getIsEndTimeNextDay() == null ? 0 : re.getIsEndTimeNextDay();
                rest.setIsEndNextDy(isEndTimeNextDay);
                Integer isStartTimeNextDay = re.getIsStartTimeNextDay() == null ? 0 : re.getIsStartTimeNextDay();
                rest.setIsStartNextDay(isStartTimeNextDay);
                results.add(rest);
            }
        }
        return results;
    }

    @Override
    public List<BranScheduleAbsenteeismResult> getAbs(WorkShiftIdCommand workShiftIdCommand) {
        List<BranScheduleAbsenteeismResult> results = new LinkedList<>();
        BranScheduleAbsenteeismResult result = null;
        String id = workShiftIdCommand.getId();
        if (StringUtils.isBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "id必填");
        }
        List<AbsentSettingEntity> abs = absentSettingDao.findByIdShiftId(id);
        for (AbsentSettingEntity ab : abs) {
            if (1 == ab.getIsOpen()) {
                result = new BranScheduleAbsenteeismResult();
                result.setId(ab.getId());
                result.setIsUse(ab.getIsOpen());
                Double absentDays = ab.getAbsentDays();
                if (absentDays == null) {
                    result.setLackTimeDay(0F);
                } else {
                    result.setLackTimeDay(absentDays.floatValue());
                }
                result.setAbsentType(ab.getAbsentType());
                Integer minutes = ab.getMinutes();
                if (minutes != null) {
                    result.setTimeMin(minutes);
                }
                results.add(result);
            }
        }

        results.sort(new Comparator<BranScheduleAbsenteeismResult>() {
            @Override
            public int compare(BranScheduleAbsenteeismResult o1, BranScheduleAbsenteeismResult o2) {
                return o1.getAbsentType() - o2.getAbsentType();
            }
        });

        return results;
    }

    @Override
    public BranScheduleOvertimeResult getOvertime(WorkShiftIdCommand workShiftIdCommand) {
        BranScheduleOvertimeResult result = new BranScheduleOvertimeResult();
        String id = workShiftIdCommand.getId();
        if (StringUtils.isBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "id必填");
        }
        WorkShiftTypeEntity byId = branWorkShiftTypeDao.findById(id);

        OverTimeWorkHoursSettingEntity over = byId.getOverTimeWorkHoursSettingEntity();
        if (over != null) {
            result.setIsOpen(over.getOverTimeWorkHoursSettingIsOpen());
            result.setNotesTime(over.getOverTimeWorkHoursSettingStartTime() + "");
            result.setIntervalTime(over.getIntervalMinutes() + "");
            result.setType(over.getType());
        } else {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "加班设置没有设置");
        }


        return result;
    }

    @Override
    public void delete(WorkShiftTypeCommand workShiftTypeCommand) {
        //判断该班次在排版中是否有使用
        String id = workShiftTypeCommand.getId();
        if (StringUtils.isBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "id必填");
        }
        List<WorkShiftTypeRoleRelationEntity> work = branWorkShiftTypeRelationDao.findByWorkTypeId(id);
        int isUse = 0;
        for (WorkShiftTypeRoleRelationEntity entity : work) {
            if (Constants.FALSE == entity.getWorkShiftRule().getIsDelete()) {
                isUse = 1;
                break;
            }
        }
        if (1 == isUse) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "该班次正在使用中");
        }
        WorkShiftTypeEntity typeDaoById = branWorkShiftTypeDao.findById(id);
        if (typeDaoById != null) {
            typeDaoById.setIsDelete(Constants.TRUE);
            branWorkShiftTypeDao.update(typeDaoById);
        } else {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "删除的班次不存在");
        }
    }


    private WorkShiftTypeEntity setEntivity(
            WorkShiftTypeEntity e,
            WorkShiftTypeNewCommand w) {

        if (e == null) {
            e = new WorkShiftTypeEntity();
        }

        String branCorpId = w.getBranCorpId();

        e.setBranCorp(branCorporationDao.findCorpById(branCorpId));
        // 验证名字重复
        //班次名称
        String shiftTypeName = w.getName().trim();
        //班次简称
        String shortName = w.getShortName().trim();

        String id = "";
        if (StringUtils.isBlank(e.getId())) {
            id = Utils.makeUUID();
            workShiftTypeUtils.verifyNameUsed(shiftTypeName,
                    shortName,
                    branCorpId);
        } else {
            id = e.getId();
            String shortName1 = e.getShortName().trim();
            String name = e.getName().trim();
            if (!shiftTypeName.equals(name) && branWorkShiftTypeDao.findByName(shiftTypeName, branCorpId) != null) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "班次名称重复");
            }

            if (!shortName.equals(shortName1) && branWorkShiftTypeDao.findByShortName(shortName, branCorpId) != null) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "简称重复");
            }

        }
        e.setId(id);

        e.setName(shiftTypeName);
        e.setShortName(shortName);
        e.setColor(w.getColor());

        //开始时间  结束时间   是否是次日
        String workStartTime = w.getWorkStartTime();
        String workEndTime = w.getWorkEndTime();
        int workTypeIsNextDay = w.getWorkTypeIsNextDay();
        log.info("上班开始时间----> " + workStartTime);
        log.info("下班开始时间----> " + workEndTime);
        log.info("下班时间是否跨天----> " + workTypeIsNextDay);
        //判断时间
        workShiftTypeUtils.verifyTime(workStartTime, workEndTime,
                workTypeIsNextDay);
        e.setWorkStartTime(workStartTime);
        e.setWorkEndTime(workEndTime);
        e.setIsNextDay(workTypeIsNextDay);


        String workMiddle = w.getWorkMiddle();
        int workMiddleIsNextDay = w.getWorkMiddleIsNextDay();
        log.info("打卡中间点时间----> " + workMiddle);
        log.info("打卡中间点是否跨天----> " + workMiddleIsNextDay);

        long startTimeLong = workShiftTypeUtils.strTimeToDate(workStartTime, false).getMillis();
        log.info("上班时间----> " + startTimeLong);
        boolean isNestDay = false;
        if (1 == workTypeIsNextDay) {
            log.info("下班时间跨天");
            isNestDay = true;
        }
        long endTimeLong = workShiftTypeUtils.strTimeToDate(workEndTime, isNestDay).getMillis();
        log.info("下班时间----> " + endTimeLong);

        isNestDay = false;
        if (1 == workMiddleIsNextDay) {
            log.info("中间点跨天");
            isNestDay = true;
        }
        long midTimeLong = workShiftTypeUtils.strTimeToDate(workMiddle, isNestDay).getMillis();
        log.info("打卡中间点的时间----> " + midTimeLong);

        //验证中间点时间是否在开始开始和结束时间中间

        try {
            if (0 == workTypeIsNextDay && 0 == workMiddleIsNextDay) {
                workShiftTypeUtils.verifyTime(workStartTime, workMiddle,
                        0);
                workShiftTypeUtils.verifyTime(workMiddle, workEndTime,
                        0);
            } else if (1 == workTypeIsNextDay && 1 == workMiddleIsNextDay) {

                workShiftTypeUtils.verifyTime(workStartTime, workMiddle,
                        1);
                workShiftTypeUtils.verifyTime(workMiddle, workEndTime,
                        0);

            } else if (workTypeIsNextDay > workMiddleIsNextDay) {//上下班跨天 中间点不跨天
                workShiftTypeUtils.verifyTime(workStartTime, workMiddle,
                        0);
            } else {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "打卡中间点时间，超出上下班时间");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "打卡中间点时间，超出上下班时间");
        }
        log.info("开始设置打卡中间点    start");
        MidClockTimeSettingEntity midClockTimeSettingEntity = e.getMidClockTimeSettingEntity();
        if (midClockTimeSettingEntity == null) {
            midClockTimeSettingEntity = new MidClockTimeSettingEntity();
            log.info("打卡中间点第一次设置");
        }
        midClockTimeSettingEntity.setIsNextDay(workMiddleIsNextDay);
        e.setMidClockTimeSettingEntity(midClockTimeSettingEntity);
        midClockTimeSettingEntity.setStartTime(workMiddle);
        log.info("开始设置打卡中间点    end");

        log.info("设置有效打卡时间   start");
        //有效打卡时间
        ValidClockTimeSettingEntity validClockTimeSettingEntity = e.getValidClockTimeSettingEntity();
        if (validClockTimeSettingEntity == null) {
            validClockTimeSettingEntity = new ValidClockTimeSettingEntity();
            log.info("第一次次设置 有效打卡");
        }
        validClockTimeSettingEntity.setBeforeMinutes((int) (w.getWorkStratVaild() * 60));
        validClockTimeSettingEntity.setAfterMinutes((int) (w.getWorkEndVaild() * 60));
        e.setValidClockTimeSettingEntity(validClockTimeSettingEntity);
        log.info("设置有效打卡时间   end");

        log.info("设置有效打卡  start");
        //人性打卡
        HumanityClockTimeSettingEntity humanityClockTimeSettingEntity = e.getHumanityClockTimeSettingEntity();
        if (humanityClockTimeSettingEntity == null) {
            humanityClockTimeSettingEntity = new HumanityClockTimeSettingEntity();
            log.info("第一次设置人性打卡");
        }
        int activePunchcardIsUse = w.getActivePunchcardIsUse();
        log.info("人性打开的开启状态----> " + activePunchcardIsUse);
        humanityClockTimeSettingEntity.setHumanityClockTimeSettingIsOpen(activePunchcardIsUse);
        if (1 == activePunchcardIsUse) {

            int activePunchcardEarly = w.getActivePunchcardEarly();
            if (activePunchcardEarly <= 0) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "人性打卡，允许早退时间应该大于0");
            }
            int activePunchcardLate = w.getActivePunchcardLate();
            if (activePunchcardLate <= 0) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "人性打卡，允许迟到时间应该大于0");

            }
            int startMin = (int) (midTimeLong - startTimeLong) / (1000 * 60);
            int endMin = (int) (endTimeLong - midTimeLong) / (1000 * 60);
            log.info("上班总时间----> " + startMin);
            log.info("下班总时间----> " + endMin);
            log.info("人性打卡 迟到----> " + activePunchcardLate);
            log.info("人性打卡 早退----> " + activePunchcardEarly);
            if (activePunchcardLate > startMin) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "迟到人性打卡时间已经超出了上班总时间");
            }
            if (endMin < activePunchcardEarly) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "早退人性打卡时间已经超出了下班总时间");

            }
            humanityClockTimeSettingEntity.setAllowEarlyLeaveMinutes(activePunchcardEarly);
            humanityClockTimeSettingEntity.setAllowLateMinutes(activePunchcardLate);
            e.setHumanityClockTimeSettingEntity(humanityClockTimeSettingEntity);
        }
        log.info("设置有效打卡  end");

        //工时计算
        setWorkTimeSetting(e, w,endTimeLong-startTimeLong);
        //加班设置
        setOvertimeSetting(e, w);
        //休息时间设置
        setRestSetting(w, id, startTimeLong, endTimeLong);
        //旷工定义
        setAbsSetting(w, id);
        // 全部记录为 工作日加班
        e.setApprovalTypeDetail(w.getApprovalTypeDetail());
        return e;
    }

    /**
     * 工时计算
     *
     * @param e
     * @param w
     * @param timeMis  上下班时间间隔
     */
    private void setWorkTimeSetting(WorkShiftTypeEntity e, WorkShiftTypeNewCommand w,long timeMis) {
        log.info("设置计算工时  start");
        //工时计算
        WorkTimeCommand workTime = w.getWorkTime();
        LaborSettingEntity laborSettingEntity = e.getLaborSettingEntity();


        Integer timeType = workTime.getType();
        if (timeType == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "加班工时必须设置");
        }
        int overTime = timeType;

        if (0 == overTime) {
            laborSettingEntity.setCalculateTo(0);
        } else if (1 == overTime) {
            laborSettingEntity.setCalculateTo(1);
        } else if (2 == overTime) {
            Float time = workTime.getTime();
            int min = 0;
            if (time != null) {
                min = (int) (time * 60);
                if (min*60L*1000>timeMis) {
                    throw  new AryaServiceException(ErrorCode.CODE_SYS_ERR,"上班工时，记为减半工时总时间不能大于全天上班总时间");
                }

            }
            laborSettingEntity.setCalculateTo(2);
            laborSettingEntity.setOverTimeMinutes(min);
            Integer exceportTime = workTime.getExceportTime();
            if (exceportTime == null) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "加班工时设置，早退/迟到/请假优先扣除 必填");
            }

            if (1 == exceportTime) {
                laborSettingEntity.setPriorityDeductTo(0);
            } else if (2 == exceportTime) {
                laborSettingEntity.setPriorityDeductTo(1);
            }
        }

        e.setLaborSettingEntity(laborSettingEntity);
        log.info("设置计算工时  end");
    }

    /**
     * 加班设置
     *
     * @param e
     * @param w
     */
    private void setOvertimeSetting(WorkShiftTypeEntity e, WorkShiftTypeNewCommand w) {
        //加班设置 OverTimeWorkHoursSettingEntity
        log.info("加班设置   start");


        OvertimeSetting overtimeSetting = w.getOvertimeSetting();
        OverTimeWorkHoursSettingEntity overTimeWorkHoursSettingEntity = e.getOverTimeWorkHoursSettingEntity();
        if (overTimeWorkHoursSettingEntity == null) {
            overTimeWorkHoursSettingEntity = new OverTimeWorkHoursSettingEntity();
            log.info("第一次设置加班设置");
        }
        int isOpen = overtimeSetting.getIsOpen();
        log.info("加班设置开启状态----> " + isOpen);
        overTimeWorkHoursSettingEntity.setOverTimeWorkHoursSettingIsOpen(isOpen);
        if (1 == isOpen) {
            overTimeWorkHoursSettingEntity.setType(overtimeSetting.getType());
            Integer notesTime = overtimeSetting.getNotesTime();
            if (notesTime==null||(notesTime!=null&&notesTime<=0)) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR,"加班设置，开始记录时间必填且大于0分钟");
            }
            overTimeWorkHoursSettingEntity.setOverTimeWorkHoursSettingStartTime(notesTime);
            Integer intervalTime = overtimeSetting.getIntervalTime();
            if (intervalTime==null||(intervalTime!=null&&intervalTime<=0)) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR,"加班设置，间隔时间必填且大于0分钟");
            }
            overTimeWorkHoursSettingEntity.setIntervalMinutes(intervalTime);
            e.setOverTimeWorkHoursSettingEntity(overTimeWorkHoursSettingEntity);
        }
        log.info("加班设置   end");
    }

    /**
     * 旷工定义设置
     *
     * @param w
     * @param id
     */
    private void setAbsSetting(WorkShiftTypeNewCommand w, String id) {
        log.info("旷工定义   start");
        //旷工定义  AbsentSettingEntity
        List<LackWorkCommand> lackDef = w.getLackDef();
        boolean isAllDay = false;
        for (LackWorkCommand l : lackDef) {
            AbsentSettingEntity abs = new AbsentSettingEntity();
            int isUse = l.getIsUse();
            if (StringUtils.isBlank(l.getId())) {//新建数据
                abs.setId(Utils.makeUUID());
                abs.setWorkShiftTypeId(id);
                abs.setIsOpen(isUse);
                abs.setAbsentType(l.getAbsentType());
                if (1 == isUse) {
                    Float lackTimeDay = l.getLackTimeDay();
                    abs.setAbsentDays(lackTimeDay.doubleValue());
                    Integer type = l.getAbsentType();
                    if (type != null && (type == 0 || type == 1)) {
                        Integer timeMin = l.getTimeMin();
                        if (timeMin != null) {
                            abs.setMinutes(timeMin);
                        } else {
                            if (!(0 < timeMin)) {
                                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "旷工设置，分钟数必须大于0分钟");
                            }
                        }
                    } else {
                        if (type != null) {
                            if (lackTimeDay == null) {
                                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "旷工设置，缺卡旷工必选");
                            }
                        }
                    }
                }
                absentSettingDao.persist(abs);
                log.info("新建数据  旷工天数----> " + l.getLackTimeDay());
            } else {//修改数据
                boolean isUpdate = false;
                if (!StringUtils.isBlank(l.getId())) {
                    abs = absentSettingDao.findById(l.getId());
                    isUpdate = true;
                } else {
                    abs.setWorkShiftTypeId(id);
                    abs.setId(Utils.makeUUID());
                    isUpdate = false;
                }
                abs.setIsOpen(isUse);
                log.info("旷工定义的开启状态----> " + isUse);
                Integer absentType = l.getAbsentType();
                Integer timeMin = l.getTimeMin();
                log.info("新建数据  旷工天数----> " + l.getLackTimeDay());
                if (isUse == 1) {
                    abs.setMinutes(timeMin);
                    abs.setAbsentType(absentType);
                    abs.setAbsentDays(l.getLackTimeDay().doubleValue());
                    if (absentType != null && (1 == absentType || 0 == absentType)) {
                        if (!(timeMin > 0)) {
                            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "旷工设置，分钟数必须大于0分钟");
                        }
                    }
                }
                if (isUpdate) {
                    absentSettingDao.update(abs);
                    log.info("更新数据");
                } else {
                    absentSettingDao.persist(abs);
                    log.info("新建数据");
                }
            }
        }
        log.info("旷工定义   end");
    }

    /**
     * 休息时间设置
     *
     * @param w
     * @param id
     * @param startTimeLong
     * @param endTimeLong
     */
    private void setRestSetting(WorkShiftTypeNewCommand w, String id, long startTimeLong, long endTimeLong) {
        log.info("休息时间设置   start");
        //休息时间  RestTimeSettingEntity
        List<RestTimeCommand> restTime = w.getRestTime();
        validRest(restTime);
        LinkedList<Long> longs = new LinkedList<>();
        for (RestTimeCommand restTimeCommand : restTime) {
            Integer isUse = restTimeCommand.getIsUse();
            Integer endIsNextDay = restTimeCommand.getEndIsNextDay();
            Integer startIsNextDay = restTimeCommand.getStartIsNextDay();
            boolean isNextDayTemp = false;
            if (isUse != null && 1 == isUse) {
                if (startIsNextDay != null && 1 == startIsNextDay) {//开始时间跨天
                    isNextDayTemp = true;
                } else {
                    isNextDayTemp = false;

                }
                try {
                    longs.add(workShiftTypeUtils.strTimeToDate(restTimeCommand.getStartTime(), isNextDayTemp).getMillis());
                } catch (Exception e1) {
                    throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "休息设置，开始时间设置不对");
                }
                if (endIsNextDay != null && 1 == endIsNextDay) {//开始时间跨天
                    isNextDayTemp = true;
                } else {
                    isNextDayTemp = false;
                }
                try {
                    longs.add(workShiftTypeUtils.strTimeToDate(restTimeCommand.getEndTime(), isNextDayTemp).getMillis());
                } catch (Exception e1) {
                    throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "休息设置，结束时间设置不对");
                }

            }
        }
        Collections.sort(longs);

        if (longs.size() > 0) {
            long start = longs.get(0);
            long end = longs.get(longs.size() - 1);

            if (!(start >= startTimeLong && endTimeLong >= end)) {
                throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "休息设置，开始或结束时间超出了上下班时间");
            }
        }

        for (RestTimeCommand r : restTime) {
            RestTimeSettingEntity rest = new RestTimeSettingEntity();
            if (StringUtils.isBlank(r.getId())) {//新建数据
                rest.setId(Utils.makeUUID());
                rest.setWorkShiftTypeId(id);
                rest.setIsOpen(r.getIsUse());
                rest.setEndTime(r.getEndTime());
                rest.setStartTime(r.getStartTime());
                rest.setIsStartTimeNextDay(r.getStartIsNextDay());
                rest.setIsEndTimeNextDay(r.getEndIsNextDay());
                rest.setIsAddToNormalWorkHours(r.getIsNormalWorkTime());
                restTimeSettingDao.persist(rest);
            } else {//修改数据
                boolean isUpdate = false;
                if (!StringUtils.isBlank(r.getId())) {
                    rest = restTimeSettingDao.findById(r.getId());
                    isUpdate = true;
                } else {
                    rest.setId(Utils.makeUUID());
                    rest.setWorkShiftTypeId(id);
                    isUpdate = false;
                }
                int isUse = r.getIsUse();
                log.info("本次编辑开启状态----> " + isUse);
                rest.setIsOpen(isUse);
                if (1 == isUse) {
                    rest.setEndTime(r.getEndTime());
                    rest.setStartTime(r.getStartTime());
                    rest.setIsStartTimeNextDay(r.getStartIsNextDay());
                    rest.setIsEndTimeNextDay(r.getEndIsNextDay());
                    rest.setIsAddToNormalWorkHours(r.getIsNormalWorkTime());
                }
                if (isUpdate) {
                    restTimeSettingDao.update(rest);
                } else {
                    restTimeSettingDao.persist(rest);
                }

            }
        }
        log.info("休息时间设置   end");
    }


    private void entivityToResult(BranNewWorkShiftTypeResult r, WorkShiftTypeEntity e) {
        if (r == null) {
            r = new BranNewWorkShiftTypeResult();
        }
        String id = e.getId();
        r.setId(id);
        r.setColor(e.getColor());
        r.setShiftTypeName(e.getName());
        r.setShortName(e.getShortName());
        r.setWorkStartTime(e.getWorkStartTime());
        r.setWorkEndTime(e.getWorkEndTime());
        r.setWorkTypeIsNextDay(e.getIsNextDay());
        MidClockTimeSettingEntity midClock = e.getMidClockTimeSettingEntity();
        if (midClock != null) {
            Integer day = midClock.getIsNextDay();
            if (0 == day) {
                r.setWorkMiddleIsNextDay(0);

            } else if (1 == day) {
                r.setWorkMiddleIsNextDay(1);
            }
            r.setWorkMiddle(midClock.getStartTime());
        }

        //有效时间

        ValidClockTimeSettingEntity valid = e.getValidClockTimeSettingEntity();
        if (valid == null) {

        } else {
            BigDecimal b = new BigDecimal(valid.getBeforeMinutes() / 60f);
            r.setWorkStratVaild(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
            b = new BigDecimal(valid.getAfterMinutes() / 60f);
            r.setWorkEndVaild(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        }


        HumanityClockTimeSettingEntity h = e.getHumanityClockTimeSettingEntity();
        if (h != null) {
            r.setActivePunchcardEarly(h.getAllowEarlyLeaveMinutes());
            r.setActivePunchcardIsUse(h.getHumanityClockTimeSettingIsOpen());
            r.setActivePunchcardLate(h.getAllowLateMinutes());
        }

        //休息

        List<RestTimeSettingEntity> dao = restTimeSettingDao.findByIdShiftId(id);
        log.info("休息设置---> " + dao.size());
        List<RestTimeCommand> list = new LinkedList();
        for (RestTimeSettingEntity res : dao) {
            RestTimeCommand c = new RestTimeCommand();
            c.setId(res.getId());
            c.setIsUse(res.getIsOpen());
            c.setStartTime(res.getStartTime());
            c.setEndTime(res.getEndTime());
            Integer isStartTimeNextDay = res.getIsStartTimeNextDay();
            if (isStartTimeNextDay == null) {
                c.setStartIsNextDay(0);
            } else {
                c.setStartIsNextDay(isStartTimeNextDay);
            }

            Integer isEndTimeNextDay = res.getIsEndTimeNextDay();
            if (isEndTimeNextDay == null) {
                c.setEndIsNextDay(0);
            } else {
                c.setEndIsNextDay(isEndTimeNextDay);
            }
            Integer isAddToNormalWorkHours = res.getIsAddToNormalWorkHours();
            if (isAddToNormalWorkHours == null) {
                c.setIsNormalWorkTime(0);
            } else {
                c.setIsNormalWorkTime(isAddToNormalWorkHours);
            }
            list.add(c);
        }
        r.setRestTime(list);
        //旷工
        List<AbsentSettingEntity> abs = absentSettingDao.findByIdShiftId(id);
        log.info("旷工定义-----> " + abs.size());
        LinkedList<LackWorkCommand> lackDef = new LinkedList<>();

        for (AbsentSettingEntity ab : abs) {
            LackWorkCommand c = new LackWorkCommand();
            c.setId(ab.getId());
            c.setIsUse(ab.getIsOpen());
            Double absentDays = ab.getAbsentDays();
            if (absentDays == null) {
                c.setLackTimeDay(0f);
            } else {
                c.setLackTimeDay(absentDays.floatValue());
            }
            c.setAbsentType(ab.getAbsentType());
            c.setTimeMin(ab.getMinutes());
            lackDef.add(c);
        }
        r.setLackDef(lackDef);
        //加班
        OverTimeWorkHoursSettingEntity over = e.getOverTimeWorkHoursSettingEntity();
        if (over != null) {
            OvertimeSetting overtimeSetting = new OvertimeSetting();
            overtimeSetting.setIsOpen(over.getOverTimeWorkHoursSettingIsOpen());
            overtimeSetting.setType(over.getType());
            overtimeSetting.setNotesTime(over.getOverTimeWorkHoursSettingStartTime());
            overtimeSetting.setIntervalTime(over.getIntervalMinutes());
            r.setOvertimeSetting(overtimeSetting);
        }

        //上班工时
        LaborSettingEntity lab = e.getLaborSettingEntity();
        if (lab != null) {
            Integer calculateTo = lab.getCalculateTo();
            WorkTimeCommand workTime = new WorkTimeCommand();
            if (calculateTo != null) {
                if (2 == calculateTo) {
                    workTime.setType(2);
                    Integer overTimeMinutes = lab.getOverTimeMinutes();
                    if (!(overTimeMinutes != null && overTimeMinutes > 0)) {
                        throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "上班工时，记加班工时必须大于0");
                    }
                    workTime.setTime(overTimeMinutes / 60F);
                    if (lab.getPriorityDeductTo() != null) {
                        workTime.setExceportTime(lab.getPriorityDeductTo() + 1);
                    } else {
                        workTime.setExceportTime(1);
                    }
                } else if (0 <= calculateTo && calculateTo < 2) {
                    workTime.setType(calculateTo);
                }
            }

            r.setApprovalTypeDetail(e.getApprovalTypeDetail());
            r.setWorkTime(workTime);
        }
    }


    public boolean validRest(List<RestTimeCommand> value) {

        if (value.size() > 1) {
            long start = 0L;
            long end = 0L;
            for (int i = 0; i < value.size(); i++) {
                RestTimeCommand r = value.get(i);
                if (r.getIsUse() != null && 1 == r.getIsUse()) {
                    Integer s = r.getStartIsNextDay();
                    Integer e = r.getEndIsNextDay();
                    boolean isNextDay = false;
                    if (s != null && 1 == s) {//开始时间跨天
                        isNextDay = true;
                    }
                    long start1 = 0L;
                    try {
                        start1 = workShiftTypeUtils.strTimeToDate(r.getStartTime(), isNextDay).getMillis();
                    } catch (Exception e1) {
                        throw  new  AryaServiceException(ErrorCode.CODE_SYS_ERR,"休息设置，开始时间设置不对");
                    }
                    isNextDay = false;
                    if (e != null && 1 == e) {//结束时间跨天
                        isNextDay = true;
                    }
                    long end1 = 0L;
                    try {
                        end1 = workShiftTypeUtils.strTimeToDate(r.getEndTime(), isNextDay).getMillis();
                    } catch (Exception e1) {
                        throw  new  AryaServiceException(ErrorCode.CODE_SYS_ERR,"休息设置，结束时间设置格式不对");
                    }

                    if (end1 < start1) {
                        throw  new  AryaServiceException(ErrorCode.CODE_SYS_ERR,"休息设置，结束时间应该大于开始时间");
                    }
                    if (!(start1 > end || start > end1)) {
                        throw  new  AryaServiceException(ErrorCode.CODE_SYS_ERR,"休息设置，两段时间存在时间重叠");
                    }
                    start = start1;
                    end = end1;
                }
            }
        }
        else {
            throw  new  AryaServiceException(ErrorCode.CODE_SYS_ERR,"休息设置，休息时间必须设置");
        }
        return true;

    }

}
