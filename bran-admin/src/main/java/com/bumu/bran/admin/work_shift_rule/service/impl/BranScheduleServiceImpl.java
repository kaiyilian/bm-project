package com.bumu.bran.admin.work_shift_rule.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.command.PagerCommand;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.*;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.push.vo.CheckWorkShiftIsConflictModel;
import com.bumu.bran.admin.work_shift_rule.controller.command.BranOneDayWorkShiftRuleCommand;
import com.bumu.bran.admin.work_shift_rule.controller.command.BranWorkShiftRuleCommand;
import com.bumu.bran.admin.work_shift_rule.helper.WorkShiftRuleHelper;
import com.bumu.bran.admin.work_shift_rule.result.AvailableWorkShiftEmpResult;
import com.bumu.bran.admin.work_shift_rule.result.AvailableWorkShiftResult;
import com.bumu.bran.admin.work_shift_rule.result.BranRuleResult;
import com.bumu.bran.admin.work_shift_rule.service.BranScheduleService;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.mybatis.EmployeeMybatisDao;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.bran.model.entity.WorkShiftEntity;
import com.bumu.bran.service.impl.BranCommonScheduleServiceImpl;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.bran.workshift.command.WorkShiftRuleCommand;
import com.bumu.bran.workshift.command.WorkShiftRuleQueryCommand;
import com.bumu.bran.workshift.model.dao.BranEmpWorkShiftTypeRelationDao;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeDao;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeRelationDao;
import com.bumu.bran.workshift.result.BranWorkShiftResult;
import com.bumu.bran.workshift.result.ScheduleViewResult;
import com.bumu.bran.workshift.result.WorkShiftRuleDetailResult;
import com.bumu.bran.workshift.result.WorkShiftTypeResult;
import com.bumu.bran.workshift.util.BranScheduleColorUtils;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.ModelResult;
import com.bumu.common.result.Pager;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author majun
 * @date 2016/12/19
 */
@Service
public class BranScheduleServiceImpl extends BranCommonScheduleServiceImpl implements BranScheduleService {

    private static final String FORMAT = "yyyy-MM";
    private Logger logger = LoggerFactory.getLogger(BranScheduleServiceImpl.class);
    // 公司
    @Autowired
    private BranCorporationDao branCorporationDao;
    // 班次
    @Autowired
    private BranWorkShiftTypeDao branWorkShiftTypeDao;
    // 班组
    @Autowired
    private WorkShiftDao workShiftDao;
    // 导出工具类
    @Autowired
    private ExcelExportHelper excelExportHelper;
    // 配置类
    @Autowired
    private BranAdminConfigService branConfigService;
    // 工具类
    @Autowired
    private BranScheduleColorUtils branScheduleColorUtils;

    // 正式员工 班次 中间表
    @Autowired
    private BranEmpWorkShiftTypeRelationDao branEmpWorkShiftTypeRelationDao;

    @Autowired
    private BranWorkShiftTypeRelationDao branWorkShiftTypeRelationDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeMybatisDao employeeMybatisDao;

    @Autowired
    private WorkShiftRuleHelper workShiftRuleHelper;

    @Override
    public Map<String, Object> getScheduleColors() throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("colors", branScheduleColorUtils.generateScheduleColors());
        return resultMap;
    }

    @Override
    public void setRules(BranWorkShiftRuleCommand workShiftRuleCommand, SessionInfo sessionInfo) throws Exception {
        //1.保存排版规则
        BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(sessionInfo.getCorpId());
        WorkShiftRuleEntity workShiftRuleEntity = new WorkShiftRuleEntity();
        if (StringUtils.isNotBlank(workShiftRuleCommand.getId())) {
            workShiftRuleEntity = branWorkShiftRuleDao.findByIdNotDelete(workShiftRuleCommand.getId());
            if (null == workShiftRuleEntity) {
                throw new AryaServiceException("规则id查询失败 id 为: " + workShiftRuleCommand.getId());
            }
            if (workShiftRuleEntity.getIsPublished() == Constants.TRUE) {
                logger.info("该排班规律已经发布，系统自动生成新的排班规律");
                //设置已缓存
                //排班规律已缓存
                workShiftRuleEntity.setIsCache(Constants.TRUE);
                Boolean isOpen = workShiftRuleEntity.getOpen();
                branWorkShiftRuleDao.update(workShiftRuleEntity);

                //新建一个排班
                workShiftRuleEntity = new WorkShiftRuleEntity();
                workShiftRuleCommand.setId(Utils.makeUUID());
                workShiftRuleEntity.setCreateUser(sessionInfo.getUserId());
                workShiftRuleEntity.setBranCorp(branCorporationEntity);
                workShiftRuleEntity.setOpen(isOpen);
            } else {
                logger.info("删除排班规律" + workShiftRuleCommand.getName() + "的中间表");
                //删除排班规律-班组中间表
                workShiftScheduleUtils.deleteRuleRelation(workShiftRuleEntity);
            }
        } else {
            workShiftRuleCommand.setId(Utils.makeUUID());
            workShiftRuleEntity.setCreateUser(sessionInfo.getCorpId());
            workShiftRuleEntity.setBranCorp(branCorporationEntity);
            workShiftRuleEntity.setOpen(false);
        }
        SysUtils.copyProperties(workShiftRuleEntity, workShiftRuleCommand);
        if (workShiftRuleCommand.getIsLoopAround() == Constants.TRUE) {
            workShiftRuleEntity.setLoopAround(true);
            workShiftRuleEntity.setExecuteEndTime(null);
        } else {
            workShiftRuleEntity.setLoopAround(false);
            if (null == workShiftRuleCommand.getEndTime()) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "请选择排班结束时间");
            }
            if (DateUtils.parseDate(workShiftRuleCommand.getEndTime(), DateTimeUtils.YYYYMMDDPattern)
                    .before(DateUtils.parseDate(workShiftRuleCommand.getStartTime(), DateTimeUtils.YYYYMMDDPattern))) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "排班结束时间不能比开始时间早");
            }
        }
        Date startTime = DateUtils.parseDate(workShiftRuleCommand.getStartTime(), DateTimeUtils.YYYYMMDDPattern);
        Date endTime = null == workShiftRuleCommand.getEndTime() || Constants.TRUE == workShiftRuleCommand.getIsLoopAround()
                ? null : DateUtils.parseDate(workShiftRuleCommand.getEndTime(), DateTimeUtils.YYYYMMDDPattern);
        workShiftRuleEntity.setExecuteTime(startTime);
        workShiftRuleEntity.setExecuteEndTime(endTime);
        branWorkShiftRuleDao.createOrUpdate(workShiftRuleEntity);

        //创建班组中间表
        for (BranWorkShiftRuleCommand.SetRuleWorkShiftModel model : workShiftRuleCommand.getWorkShifts()) {
            //检查选择的班组是否与其他的排班时间冲突
            CheckWorkShiftIsConflictModel checkWorkShiftIsConflictModel = checkWorkShiftIsConflict(startTime, endTime,
                    workShiftRuleEntity.getLoopAround(), model.getId(), workShiftRuleEntity.getId());
            if (checkWorkShiftIsConflictModel.getConflict()) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "当前排班设置的时间班组-"
                        + checkWorkShiftIsConflictModel.getConflictWorkShiftName() + "已有安排，请检查;");
            }
            WorkShiftEntity workShiftEntity = workShiftDao.findByIdNotDelete(model.getId());
            WorkShiftRoleRelationEntity roleRelationEntity = new WorkShiftRoleRelationEntity();
            roleRelationEntity.setId(Utils.makeUUID());
            roleRelationEntity.setWorkShiftRule(workShiftRuleEntity);
            roleRelationEntity.setExecuteTime(DateUtils.parseDate(workShiftRuleCommand.getStartTime(), DateTimeUtils.YYYYMMDDPattern));
            roleRelationEntity.setWorkShift(workShiftEntity);
            branWorkShiftRelationDao.persist(roleRelationEntity);
        }

        //创建班次中间表
        Integer order = 0;
        for (BranWorkShiftRuleCommand.SetRuleWorkShiftTypeModel model : workShiftRuleCommand.getWorkShiftTypes()) {
            WorkShiftTypeEntity workShiftTypeEntity = branWorkShiftTypeDao.findByIdNotDelete(model.getId());
            WorkShiftTypeRoleRelationEntity workShiftTypeRoleRelationEntity = new WorkShiftTypeRoleRelationEntity();
            workShiftTypeRoleRelationEntity.setId(Utils.makeUUID());
            workShiftTypeRoleRelationEntity.setOrder(order);
            workShiftTypeRoleRelationEntity.setWorkShiftRule(workShiftRuleEntity);
            workShiftTypeRoleRelationEntity.setWorkShiftType(workShiftTypeEntity);
            branWorkShiftTypeRelationDao.persist(workShiftTypeRoleRelationEntity);
            order++;
        }
    }

    private CheckWorkShiftIsConflictModel checkWorkShiftIsConflict(Date startTime, Date endTime, Boolean isLoop, String workShiftId, String workShiftRuleId) {
        CheckWorkShiftIsConflictModel checkResult = new CheckWorkShiftIsConflictModel();
        checkResult.setConflict(false);
        List<WorkShiftRoleRelationEntity> workShiftRoleRelationEntityList = branWorkShiftRelationDao.findWorkingShiftById(workShiftId);
        // 时间年-月-日格式默认时间是8点,手动设置为0
        startTime = DateUtils.setHours(startTime, 0);
        endTime = null == endTime ? null : DateUtils.setHours(endTime, 0);
        try {
            for (WorkShiftRoleRelationEntity entity : workShiftRoleRelationEntityList) {
                if (entity.getWorkShiftRule().getId().equals(workShiftRuleId)) {
                    checkResult = new CheckWorkShiftIsConflictModel(true, entity.getWorkShift().getId(), entity.getWorkShift().getShiftName());
                    break;
                }
                if (isLoop) {
                    if (entity.getWorkShiftRule().getLoopAround()) {
                        checkResult = new CheckWorkShiftIsConflictModel(true, entity.getWorkShift().getId(), entity.getWorkShift().getShiftName());
                        break;
                    } else {
                        if (!entity.getWorkShiftRule().getExecuteEndTime().before(startTime)) {
                            checkResult = new CheckWorkShiftIsConflictModel(true, entity.getWorkShift().getId(), entity.getWorkShift().getShiftName());
                            break;
                        }
                    }
                } else {
                    if (entity.getWorkShiftRule().getLoopAround()) {
                        if (!endTime.before(entity.getWorkShiftRule().getExecuteTime())) {
                            checkResult = new CheckWorkShiftIsConflictModel(true, entity.getWorkShift().getId(), entity.getWorkShift().getShiftName());
                            break;
                        }
                    } else {
                        if (endTime.after(entity.getWorkShiftRule().getExecuteTime())
                                && startTime.before(entity.getWorkShiftRule().getExecuteEndTime())) {
                            checkResult = new CheckWorkShiftIsConflictModel(true, entity.getWorkShift().getId(), entity.getWorkShift().getShiftName());
                            break;
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "所选班组有排班时间异常");
        }
        return checkResult;
    }

    @Override
    public Pager<ScheduleViewResult> getRules(WorkShiftRuleQueryCommand workShiftRuleQueryCommand, PagerCommand pagerCommand, SessionInfo sessionInfo) throws Exception {
        return getViews(workShiftRuleQueryCommand, pagerCommand);
    }

    @Override
    public void publishRules(WorkShiftRuleCommand workShiftRuleCommand) throws Exception {
        // 查询cache rule
        List<WorkShiftRuleEntity> cacheList = branWorkShiftRuleDao.findByCorpIdAndCacheStatus(
                workShiftRuleCommand.getBranCorpId(), 1);
        if (!ListUtils.checkNullOrEmpty(cacheList)) {
            // 删除已发布缓存的规则
            workShiftScheduleUtils.deleteCacheRule(cacheList);
        }
        // 查询未发布的规则
        List<WorkShiftRuleEntity> notPublished = branWorkShiftRuleDao.findNotPublishedByCorpId(
                workShiftRuleCommand.getBranCorpId());

        // 如果没有可发布的,提示:暂无可发布的排班
        if (ListUtils.checkNullOrEmpty(notPublished)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "暂无可发布的排班");
        }

        for (WorkShiftRuleEntity entity : notPublished) {
            entity.setIsPublished(1);
            branWorkShiftRuleDao.update(entity);
        }
    }

    @Override
    public void export(WorkShiftRuleQueryCommand workShiftRuleCommand, HttpServletResponse httpServletResponse) throws Exception {
//        Map<String, Object> resultMap = new HashedMap();
//
//        List<ScheduleViewResult> resultList = getViews(workShiftRuleCommand);
//        resultMap.put("list", resultList);
//        if (resultList != null && !resultList.isEmpty()) {
//            resultMap.put("details", resultList.get(0).getSchedule().getDetail());
//        }
//        excelExportHelper.export(
//                branConfigService.getExcelTemplateLocation() + BranAdminConfigService.SCHEDULE_EXCEL_TEMPLATE,
//                "排班视图导出",
//                resultMap,
//                httpServletResponse
//        );

    }

    @Override
    public List<BranRuleResult> getRuleList(WorkShiftRuleCommand workShiftRuleCommand) {
        List<BranRuleResult> results = new ArrayList<>();
        List<WorkShiftRuleEntity> list = branWorkShiftRuleDao.findByCorpIdAndCacheStatus(workShiftRuleCommand.getBranCorpId(),
                0);
        if (list == null || list.isEmpty()) {
            return results;
        }
        list.forEach(e -> {
            results.add(new BranRuleResult(e.getId(), null, e.getTxVersion(), e.getIsPublished()));
        });

        return results;

    }

    @Override
    public WorkShiftRuleDetailResult getRuleDetails(String id, SessionInfo sessionInfo) throws Exception {
        WorkShiftRuleDetailResult result = new WorkShiftRuleDetailResult();

        //1.获取排班对象
        WorkShiftRuleEntity workShiftRuleEntity = branWorkShiftRuleDao.findByIdNotDelete(id);
        if (null == workShiftRuleEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "无该排班信息");
        }
        SysUtils.copyProperties(result, workShiftRuleEntity);
        result.setIsLoopAround(workShiftRuleEntity.getLoopAround() ? Constants.TRUE : Constants.FALSE);
        //2.获取班组信息
        List<WorkShiftRoleRelationEntity> workShiftEntityList = branWorkShiftRelationDao.findByWorkShiftRuleId(id);
        if (CollectionUtils.isEmpty(workShiftEntityList)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "无该排班的班组信息");
        }
        workShiftEntityList.stream().forEach(entity -> {
            BranWorkShiftResult branWorkShiftResult = new BranWorkShiftResult();
            branWorkShiftResult.setId(entity.getWorkShift().getId());
            branWorkShiftResult.setName(entity.getWorkShift().getShiftName());
            List<EmployeeEntity> employeeEntities = employeeDao.findByWorkShiftIdOrName(sessionInfo.getCorpId(), entity.getWorkShift().getId(), null);
            branWorkShiftResult.setStaffNumber(employeeEntities.size());
            result.getWorkShifts().add(branWorkShiftResult);
        });
        //3.获取排班规律
        List<WorkShiftTypeRoleRelationEntity> workShiftTypeRoleRelationEntityList = branWorkShiftTypeRelationDao.findByRuleId(id);
        if (CollectionUtils.isEmpty(workShiftTypeRoleRelationEntityList)) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "无该排班的班次信息");
        }
        workShiftTypeRoleRelationEntityList.stream().forEach(entity -> {
            WorkShiftTypeResult workShiftTypeResult = new WorkShiftTypeResult();
            workShiftTypeResult.setId(entity.getWorkShiftType().getId());
            workShiftTypeResult.setColor(entity.getWorkShiftType().getColor());
            workShiftTypeResult.setShortName(entity.getWorkShiftType().getShortName());
            result.getWorkShiftTypes().add(workShiftTypeResult);
        });
        return result;

    }

    @Override
    public List<AvailableWorkShiftResult> getAvailableWorkShifts(String workShiftRuleId, Long executeTime, Long executeEndTime, Boolean isLoopAround, SessionInfo sessionInfo) {
        //检查参数
        if (null == executeTime) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "请先选择排班开始时间");
        }
        if (null == executeEndTime && (null == isLoopAround || false == isLoopAround)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "请先选择排班结束时间时间");
        }
        List<AvailableWorkShiftResult> list = new ArrayList<>();
        //获取企业下所有的班组
        List<WorkShiftEntity> workShiftEntityList = workShiftDao.findAllWorkShiftsByBranCorpId(sessionInfo.getCorpId());
        //获取时间范围内 已经有安排的班组
        List<WorkShiftRoleRelationEntity> workShiftRoleRelationInWorkList = branWorkShiftRelationDao.findWorkingShiftByTime(null == executeTime ? null : new Date(executeTime)
                , null == executeEndTime ? null : new Date(executeEndTime), isLoopAround);
        //将班组ID收集
        Set<String> workingShiftSet = new HashSet<>();
        for (WorkShiftRoleRelationEntity workShiftRoleRelationEntity : workShiftRoleRelationInWorkList) {
            workingShiftSet.add(workShiftRoleRelationEntity.getWorkShift().getId());
        }

        //当前排班已经选择的班组
        if (StringUtils.isNotBlank(workShiftRuleId)) {
            List<WorkShiftRoleRelationEntity> workShiftRoleRelationEntityList = branWorkShiftRelationDao.findByWorkShiftRuleId(workShiftRuleId);
            for (WorkShiftRoleRelationEntity entity : workShiftRoleRelationEntityList) {
                workingShiftSet.remove(entity.getWorkShift().getId());
            }
        }
        for (WorkShiftEntity workShiftEntity : workShiftEntityList) {
            if (!workingShiftSet.contains(workShiftEntity.getId())) {
                //获取该班组下所有成员
                List<EmployeeEntity> employeeEntities = employeeDao.findByWorkShiftIdOrName(sessionInfo.getCorpId(), workShiftEntity.getId(), null);
                List<AvailableWorkShiftEmpResult> availableWorkShiftEmpReulstList = new ArrayList<>();
                for (EmployeeEntity employeeEntity : employeeEntities) {
                    AvailableWorkShiftEmpResult availableWorkShiftEmpResult = new AvailableWorkShiftEmpResult();
                    availableWorkShiftEmpResult.setDepartmentName(employeeEntity.getDepartmentName());
                    availableWorkShiftEmpResult.setEmpName(employeeEntity.getRealName());
                    availableWorkShiftEmpResult.setPositionName(employeeEntity.getPositionName());
                    availableWorkShiftEmpReulstList.add(availableWorkShiftEmpResult);
                }
                AvailableWorkShiftResult result = new AvailableWorkShiftResult();
                result.setId(workShiftEntity.getId());
                result.setName(workShiftEntity.getShiftName());
                result.setEmps(availableWorkShiftEmpReulstList);
                result.setStaffNumber(availableWorkShiftEmpReulstList.size());
                list.add(result);
            }
        }
        return list;
    }

    @Override
    public String setOneDayRule(BranOneDayWorkShiftRuleCommand branOneDayWorkShiftRuleCommand, SessionInfo sessionInfo) throws Exception {

        WorkShiftTypeEntity workShiftEntity = branWorkShiftTypeDao.findByIdNotDelete(branOneDayWorkShiftRuleCommand.getWorkShiftTypeId());
        Assert.notNull(workShiftEntity, "没有查询到班次");
        logger.info("修改的班次名字: " + workShiftEntity.getName());

        // 查询排班规律
        WorkShiftRuleEntity workShiftRuleEntity = branWorkShiftRuleDao.findByIdNotDelete(branOneDayWorkShiftRuleCommand.getWorkShiftRuleId());
        Assert.notNull(workShiftRuleEntity, "没有查询到排班规律: " + branOneDayWorkShiftRuleCommand.getWorkShiftRuleId());

        logger.info("参数: " + branOneDayWorkShiftRuleCommand.toString());
        logger.info("日期+员工id查询中间表");
        EmpWorkShiftTypeRelationEntity empWorkShiftTypeRelationEntity = branEmpWorkShiftTypeRelationDao.findByEmpIdAndModifyDate(branOneDayWorkShiftRuleCommand.getEmpId(),
                new Date(branOneDayWorkShiftRuleCommand.getModifyDate()), branOneDayWorkShiftRuleCommand.getWorkShiftRuleId());

        // 如果未发布的,新增/更新
        if (workShiftRuleEntity.getIsPublished() == 0) {
            if (empWorkShiftTypeRelationEntity == null) {
                logger.info("新增");
                empWorkShiftTypeRelationEntity = new EmpWorkShiftTypeRelationEntity();
                empWorkShiftTypeRelationEntity.setId(Utils.makeUUID());
            } else {
                logger.info("更新");
            }
            empWorkShiftTypeRelationEntity.setEmpId(branOneDayWorkShiftRuleCommand.getEmpId());
            empWorkShiftTypeRelationEntity.setModifyDate(new Date(branOneDayWorkShiftRuleCommand.getModifyDate()));
            empWorkShiftTypeRelationEntity.setWorkShiftTypeId(workShiftEntity.getId());
            empWorkShiftTypeRelationEntity.setBranCorpId(sessionInfo.getCorpId());
            empWorkShiftTypeRelationEntity.setWorkShiftRuleId(branOneDayWorkShiftRuleCommand.getWorkShiftRuleId());
            // 更新或者修改 个人排班数据
            branEmpWorkShiftTypeRelationDao.createOrUpdate(empWorkShiftTypeRelationEntity);
            return workShiftRuleEntity.getId();
        }

        // 已发布
        // 复制
        WorkShiftRuleEntity newWorkShiftRule = new WorkShiftRuleEntity();
        workShiftRuleHelper.copyWorkShift(newWorkShiftRule, workShiftRuleEntity);
        workShiftRuleHelper.copyEmp(newWorkShiftRule, workShiftRuleEntity);

        logger.info("参数: " + branOneDayWorkShiftRuleCommand.toString());
        logger.info("日期+员工id查询中间表");
        empWorkShiftTypeRelationEntity = branEmpWorkShiftTypeRelationDao.findByEmpIdAndModifyDate(branOneDayWorkShiftRuleCommand.getEmpId(),
                new Date(branOneDayWorkShiftRuleCommand.getModifyDate()), newWorkShiftRule.getId());

        if (empWorkShiftTypeRelationEntity == null) {
            logger.info("新增");
            empWorkShiftTypeRelationEntity = new EmpWorkShiftTypeRelationEntity();
            empWorkShiftTypeRelationEntity.setId(Utils.makeUUID());
        } else {
            logger.info("更新");
        }
        empWorkShiftTypeRelationEntity.setEmpId(branOneDayWorkShiftRuleCommand.getEmpId());
        empWorkShiftTypeRelationEntity.setModifyDate(new Date(branOneDayWorkShiftRuleCommand.getModifyDate()));
        empWorkShiftTypeRelationEntity.setWorkShiftTypeId(workShiftEntity.getId());
        empWorkShiftTypeRelationEntity.setBranCorpId(sessionInfo.getCorpId());
        empWorkShiftTypeRelationEntity.setWorkShiftRuleId(newWorkShiftRule.getId());
        // 更新或者修改 个人排班数据
        branEmpWorkShiftTypeRelationDao.createOrUpdate(empWorkShiftTypeRelationEntity);
        return newWorkShiftRule.getId();
    }

    @Override
    public String setRuleRevert(BranOneDayWorkShiftRuleCommand branOneDayWorkShiftRuleCommand, SessionInfo sessionInfo) throws Exception {

        WorkShiftTypeEntity workShiftEntity = branWorkShiftTypeDao.findByIdNotDelete(branOneDayWorkShiftRuleCommand.getWorkShiftTypeId());
        Assert.notNull(workShiftEntity, "没有查询到班次");
        logger.info("修改的班次名字: " + workShiftEntity.getName());

        // 查询排班规律
        WorkShiftRuleEntity workShiftRuleEntity = branWorkShiftRuleDao.findByIdNotDelete(branOneDayWorkShiftRuleCommand.getWorkShiftRuleId());
        Assert.notNull(workShiftRuleEntity, "没有查询到排班规律: " + branOneDayWorkShiftRuleCommand.getWorkShiftRuleId());

        logger.info("参数: " + branOneDayWorkShiftRuleCommand.toString());
        logger.info("日期+员工id查询中间表");
        EmpWorkShiftTypeRelationEntity empWorkShiftTypeRelationEntity = branEmpWorkShiftTypeRelationDao.findByEmpIdAndModifyDate(branOneDayWorkShiftRuleCommand.getEmpId(),
                new Date(branOneDayWorkShiftRuleCommand.getModifyDate()), branOneDayWorkShiftRuleCommand.getWorkShiftRuleId());

        // 如果未发布的,新增/更新
        if (workShiftRuleEntity.getIsPublished() == 0) {
            if (empWorkShiftTypeRelationEntity != null) {
                // 删除个人数据,还原
                branEmpWorkShiftTypeRelationDao.delete(empWorkShiftTypeRelationEntity);
            }
            return workShiftRuleEntity.getId();
        }

        // 已发布
        // 复制
        WorkShiftRuleEntity newWorkShiftRule = new WorkShiftRuleEntity();
        workShiftRuleHelper.copyWorkShift(newWorkShiftRule, workShiftRuleEntity);
        workShiftRuleHelper.copyEmp(newWorkShiftRule, workShiftRuleEntity);

        logger.info("参数: " + branOneDayWorkShiftRuleCommand.toString());
        logger.info("日期+员工id查询中间表");
        empWorkShiftTypeRelationEntity = branEmpWorkShiftTypeRelationDao.findByEmpIdAndModifyDate(branOneDayWorkShiftRuleCommand.getEmpId(),
                new Date(branOneDayWorkShiftRuleCommand.getModifyDate()), newWorkShiftRule.getId());
        if (empWorkShiftTypeRelationEntity != null) {
            // 删除个人数据,还原
            branEmpWorkShiftTypeRelationDao.delete(empWorkShiftTypeRelationEntity);
        }
        return newWorkShiftRule.getId();
    }

    @Override
    public List<ModelResult> getRuleNames(SessionInfo sessionInfo) {
        List<ModelResult> results = new ArrayList<>();

        logger.info("根据branCorpId查询排班: " + sessionInfo.getCorpId());
        logger.info(String.format("条件 isDelete : %s isCache: %s branCorpId: %s", 0, 0, sessionInfo.getCorpId()));

        List<WorkShiftRuleEntity> list = branWorkShiftRuleDao.findByCorpIdAndCacheStatus(sessionInfo.getCorpId(), 0);
        if (ListUtils.checkNullOrEmpty(list)) {
            logger.info("没有查询到排班信息");
            return results;
        }

        return list
                .stream()
                .map(rule -> new ModelResult(rule.getId(), rule.getName(), rule.getTxVersion()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailableWorkShiftEmpResult> getAvailableWorkShiftEmpList(SessionInfo sessionInfo, String workShitId) {
        List<EmployeeEntity> employeeEntities = employeeDao.findByWorkShiftIdOrName(sessionInfo.getCorpId(), workShitId, null);
        List<AvailableWorkShiftEmpResult> availableWorkShiftEmpReulstList = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            AvailableWorkShiftEmpResult availableWorkShiftEmpResult = new AvailableWorkShiftEmpResult();
            availableWorkShiftEmpResult.setDepartmentName(employeeEntity.getDepartmentName());
            availableWorkShiftEmpResult.setEmpName(employeeEntity.getRealName());
            availableWorkShiftEmpResult.setPositionName(employeeEntity.getPositionName());
            availableWorkShiftEmpReulstList.add(availableWorkShiftEmpResult);
        }
        return availableWorkShiftEmpReulstList;
    }
}
