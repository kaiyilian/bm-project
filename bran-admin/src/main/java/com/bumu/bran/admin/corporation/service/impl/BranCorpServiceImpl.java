package com.bumu.bran.admin.corporation.service.impl;

import com.bumu.admin.constant.CorpConstants;
import com.bumu.admin.model.dao.BranCorpUserDao;
import com.bumu.admin.model.entity.BranCorpUserEntity;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.SysLogDao;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.admin.corporation.controller.command.UpdateCheckinMessageCommand;
import com.bumu.bran.admin.corporation.controller.command.UpdateDepartmentCommand;
import com.bumu.bran.admin.corporation.helper.DepartmentNodeHelper;
import com.bumu.bran.admin.corporation.helper.NodeCoreHelper;
import com.bumu.bran.admin.corporation.result.*;
import com.bumu.bran.admin.corporation.service.BranCorpService;
import com.bumu.bran.admin.corporation.service.FileService;
import com.bumu.bran.admin.employee.service.EmployeeService;
import com.bumu.bran.admin.notification.controller.command.PostNewNotificationCommand;
import com.bumu.bran.admin.notification.result.NotificationListResult;
import com.bumu.bran.admin.push.vo.CheckinMessageSendTimeModel;
import com.bumu.bran.attendance.model.dao.WorkAttendanceClockSettingDao;
import com.bumu.bran.common.Constants;
import com.bumu.bran.common.command.CorpOpLogQueryCommand;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.bran.corporation.model.dao.BranCorpCheckinMsgDao;
import com.bumu.bran.corporation.model.dao.CorpNoticeDao;
import com.bumu.bran.corporation.model.entity.BranCorpCheckinMessageEntity;
import com.bumu.bran.corporation.model.entity.CorpNoticeEntity;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.LeaveEmployeeDao;
import com.bumu.bran.employee.model.dao.ProspectiveEmployeeDao;
import com.bumu.bran.model.AryaUserQueryModel;
import com.bumu.bran.model.dao.BranCorpImageDao;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.*;
import com.bumu.bran.model.entity.attendance.WorkAttendanceClockSettingEntity;
import com.bumu.bran.service.ScheduleService;
import com.bumu.bran.setting.model.dao.*;
import com.bumu.common.Node;
import com.bumu.common.result.TxVersionResult;
import com.bumu.common.service.PushManyNoticeThread;
import com.bumu.common.service.PushService;
import com.bumu.common.util.ListUtils;
import com.bumu.common.util.TxVersionUtil;
import com.bumu.employee.constant.EmployeeConstants;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.ExceptionModel;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.bumu.arya.model.SysLogDao.SysLogExtInfo;
import static com.bumu.bran.admin.corporation.controller.command.UpdateDepartmentCommand.UpdateDepartmentCMD;
import static com.bumu.bran.admin.corporation.result.CorpOperationListResult.CorpOperationResult;
import static com.bumu.bran.admin.notification.result.NotificationListResult.NotificationResult;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Service
public class BranCorpServiceImpl implements BranCorpService {

    private static Logger logger = LoggerFactory.getLogger(BranCorpServiceImpl.class);

    @Autowired
    BranCorporationDao corporationDao;

    @Autowired
    BranCorpUserDao corpUserDao;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    WorkLineDao workLineDao;

    @Autowired
    WorkShiftDao workShiftDao;

    @Autowired
    PositionDao positionDao;

    @Autowired
    ProspectiveEmployeeDao prospectiveEmployeeDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    LeaveEmployeeDao leaveEmployeeDao;

    @Autowired
    LeaveReasonDao leaveReasonDao;

    @Autowired
    CorpNoticeDao corpNoticeDao;

    @Autowired
    BranUserDao branUserDao;

    @Autowired
    CorporationDao aryaCorporationDao;

    @Autowired
    BranCorpImageDao corpImageDao;

    @Autowired
    FileService fileService;

    @Autowired
    BranCorpCheckinMsgDao branCorpCheckinMsgDao;

    @Autowired
    AryaUserDao aryaUserDao;

    @Autowired
    PushService pushService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    BranOpLogDao branOpLogDao;

    @Autowired
    private WorkAttendanceClockSettingDao workAttendanceClockSettingDao;

    private NodeCoreHelper nodeCoreHelper = new DepartmentNodeHelper();

    @Override
    public String getBranCorpIdByCorpUserId(String corpUserId) throws AryaServiceException {
        BranCorpUserEntity corpUserEntity = corpUserDao.findCorpUserById(corpUserId);
        return corpUserEntity.getBranCorpId();
    }

    @Override
    public DepartmentListResult getCorpDepartments(String branCorpId, String parentDepartmentId) throws AryaServiceException {
        DepartmentListResult results = new DepartmentListResult();
        List<DepartmentEntity> departmentEntities;
        if (StringUtils.isAnyBlank(parentDepartmentId)) {
            departmentEntities = departmentDao.findCorpAllDepartments(branCorpId);
        } else {
            departmentEntities = departmentDao.findCorpSubDepartments(parentDepartmentId);
        }
        for (DepartmentEntity departmentEntity : departmentEntities) {
            DepartmentListResult.DepartmentResult result = new DepartmentListResult.DepartmentResult();
            result.setParentId(departmentEntity.getParentId());
            result.setDepartmentId(departmentEntity.getId());
            result.setDepartmentName(departmentEntity.getDepartmentName());
            result.setVersion(departmentEntity.getTxVersion());
            results.add(result);
        }
        return results;
    }

    @Override
    public Boolean isCorpDepartmentCanDelete(String branCorpId, String departmentId) throws AryaServiceException {
        //检查部门是否有下级部门，有则不能删除
        if (departmentDao.isCorpDepartmentHasSubDepartment(departmentId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_HAS_SUBDEPATMENT_CAN_NOT_DELETE);
        }
        //检查部门是否被使用
        if (prospectiveEmployeeDao.isDepartmentBeUsed(departmentId) || employeeDao.isDepartmentBeUsed(departmentId) || leaveEmployeeDao.isDepartmentBeUsed(departmentId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_BEEN_USED_CAN_NOT_DELETE);
        }
        return true;
    }

    @Override
    public WorkLineListResult getCorpAllWorkLines(String branCorpId) throws Exception {
        WorkLineListResult results = new WorkLineListResult();
        List<WorkLineEntity> workLineEntities = workLineDao.findCorpAllWorkLines(branCorpId);
        for (WorkLineEntity workLineEntity : workLineEntities) {
            WorkLineListResult.WorkLineResult result = new WorkLineListResult.WorkLineResult();
            result.setWorkLineId(workLineEntity.getId());
            result.setWorkLineName(workLineEntity.getLineName());
            result.setVersion(workLineEntity.getTxVersion());
            results.add(result);
        }
        return results;
    }

    @Override
    public Boolean isCorpWorkLineCanDelete(String branCorpId, String workLineId) throws AryaServiceException {
        //检查工段是否被使用
        if (prospectiveEmployeeDao.isWorkLineBeUsed(workLineId) || employeeDao.isWorkLineBeUsed(workLineId) || leaveEmployeeDao.isWorkLineBeUsed(workLineId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_LINE_BEEN_USED_CAN_NOT_DELETE);
        }
        return true;
    }

    @Override
    public WorkShiftListResult getCorpAllWorkShifts(String branCorpId) throws Exception {
        WorkShiftListResult results = new WorkShiftListResult();
        List<WorkShiftEntity> shiftEntities = workShiftDao.findAllWorkShiftsByBranCorpId(branCorpId);
        for (WorkShiftEntity shiftEntity : shiftEntities) {
            WorkShiftListResult.WorkShiftResult result = new WorkShiftListResult.WorkShiftResult();
            result.setWorkShiftId(shiftEntity.getId());
            result.setWorkShiftName(shiftEntity.getShiftName());
            result.setVersion(shiftEntity.getTxVersion());
            results.add(result);
        }
        return results;
    }

    @Override
    public Boolean isCorpWorkShiftCanDelete(String branCorpId, String workShiftId) throws AryaServiceException {
        //检查班组是否被使用
        if (prospectiveEmployeeDao.isWorkShiftBeUsed(workShiftId) || employeeDao.isWorkShiftBeUsed(workShiftId) || leaveEmployeeDao.isWorkShiftBeUsed(workShiftId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_BEEN_USED_CAN_NOT_DELETE);
        }
        List<WorkAttendanceClockSettingEntity> list = workAttendanceClockSettingDao.findByCorpIdAndWorkShiftId(branCorpId, workShiftId);
        if (!ListUtils.checkNullOrEmpty(list)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_BEEN_USED_CAN_NOT_DELETE);
        }
        return true;
    }

    @Override
    public PositionListResult getCorpAllPositions(String branCorpId) throws Exception {
        PositionListResult results = new PositionListResult();
        List<PositionEntity> positionEntities = positionDao.findCorpAllPostionsById(branCorpId);
        for (PositionEntity positionEntity : positionEntities) {
            PositionListResult.PositionResult result = new PositionListResult.PositionResult();
            result.setPositionId(positionEntity.getId());
            result.setPositionName(positionEntity.getPositionName());
            result.setVersion(positionEntity.getTxVersion());
            results.add(result);
        }
        return results;
    }

    @Override
    public Boolean isCorpPositionCanDelete(String branCorpId, String positionId) throws AryaServiceException {
        //检查职位是否被使用
        if (prospectiveEmployeeDao.isPositionBeUsed(positionId) || employeeDao.isPositionBeUsed(positionId) || leaveEmployeeDao.isPositionBeUsed(positionId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_POSITION_BEEN_USED_CAN_NOT_DELETE);
        }
        return true;
    }

    @Override
    public void departmentsCUD(UpdateDepartmentCommand command, String branCorpId, String operator) throws AryaServiceException {
        //新增或修改
        if (command.getUpdateDepartments().size() > 0) {
            Map<String, UpdateDepartmentCMD> updateDepartmentMap = new HashMap<>();
            Collection<String> departmentIds = new ArrayList<>();
            for (UpdateDepartmentCMD cmd : command.getUpdateDepartments()) {
                if (!departmentIds.contains(cmd.getDepartmentId())) {
                    departmentIds.add(cmd.getDepartmentId());
                }
                if (!updateDepartmentMap.containsKey(cmd.getDepartmentId())) {
                    updateDepartmentMap.put(cmd.getDepartmentId(), cmd);
                }
            }
            if (departmentIds.size() == 0) {
                return;
            }
            //修改
            List<DepartmentEntity> needUpdateDepartments = departmentDao.findCorpDepartmentByIds(departmentIds, branCorpId);
            List<DepartmentEntity> updateDepartmentEntities = new ArrayList<>();
            for (DepartmentEntity updateDepartmentEntity : needUpdateDepartments) {
                UpdateDepartmentCMD cmd = updateDepartmentMap.get(updateDepartmentEntity.getId());
                updateDepartmentEntity.setDepartmentName(cmd.getDepartmentName());
                updateDepartmentEntity.setUpdateUser(operator);
                updateDepartmentEntity.setUpdateTime(System.currentTimeMillis());
                updateDepartmentMap.remove(cmd.getDepartmentId());
                updateDepartmentEntities.add(updateDepartmentEntity);
            }
            //新增
            List<DepartmentEntity> createDepartmentEntities = new ArrayList<>();
            for (String key : updateDepartmentMap.keySet()) {
                UpdateDepartmentCMD cmd = updateDepartmentMap.get(key);
                DepartmentEntity newDepartmentEntity = new DepartmentEntity();
                newDepartmentEntity.setId(Utils.makeUUID());
                newDepartmentEntity.setDepartmentName(cmd.getDepartmentName());
                newDepartmentEntity.setParentId(cmd.getParentId());
                newDepartmentEntity.setIsDelete(Constants.FALSE);
                newDepartmentEntity.setBranCorpId(branCorpId);
                newDepartmentEntity.setCreateTime(System.currentTimeMillis());
                newDepartmentEntity.setCreateUser(operator);
                newDepartmentEntity.setUpdateUser(operator);
                newDepartmentEntity.setUpdateTime(System.currentTimeMillis());
                createDepartmentEntities.add(newDepartmentEntity);
            }
            try {
                if (updateDepartmentEntities.size() > 0) {
                    departmentDao.update(updateDepartmentEntities);
                    for (DepartmentEntity departmentEntity : updateDepartmentEntities) {
                        prospectiveEmployeeDao.updateCorpDepartment(departmentEntity.getId(), departmentEntity.getDepartmentName(), branCorpId);
                        employeeDao.updateCorpDepartment(departmentEntity.getId(), departmentEntity.getDepartmentName(), branCorpId);
                        leaveEmployeeDao.updateCorpDepartment(departmentEntity.getId(), departmentEntity.getDepartmentName(), branCorpId);
                    }
                }

                departmentDao.create(createDepartmentEntities);
            } catch (Exception e) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_UPDATE_FAILED);
            }
        }

        //删除部门
        if (command.getDeleteDepartmentIds().length != 0) {
            List<DepartmentEntity> needDeleteDepartments = departmentDao.findCorpDepartmentByIds(command.getDeleteDepartmentIds(), branCorpId);
            if (needDeleteDepartments.size() > 0) {
                for (DepartmentEntity departmentEntity : needDeleteDepartments) {
                    departmentEntity.setIsDelete(Constants.TRUE);
                }
                try {
                    departmentDao.update(needDeleteDepartments);
                } catch (Exception e) {
                    throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_DELETE_FAILED);
                }
            }
        }
    }

    @Override
    public CreateWorkLineResult addCorpWorkLine(String workLineName, String branCorpId, String operator) throws AryaServiceException {
        if (StringUtils.isAnyBlank(workLineName)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CONTENT_EMPTY);
        }
        if (workLineName.length() > 32) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PARAM_TOO_LONG);
        }
        int workLineTotalCount = workLineDao.findCorpWorkLineTotalCount(branCorpId);
        if (workLineTotalCount > 90) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_LINE_COUNT_LIMITE);
        }
        if (workLineDao.findCorpWorkLineIdByName(workLineName, branCorpId) != null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_LINE_NAME_EXIST);
        }
        WorkLineEntity entity = new WorkLineEntity();
        entity.setId(Utils.makeUUID());
        entity.setCreateUser(operator);
        entity.setCreateTime(System.currentTimeMillis());
        entity.setBranCorpId(branCorpId);
        entity.setIsDelete(Constants.FALSE);
        entity.setLineName(workLineName);
        try {
            workLineDao.create(entity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("新增了" + entity.getLineName() + "工段。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORKLINE, BranOpLogEntity.OP_TYPE_ADD, operator, info);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_LINE_ADD_FAILED);
        }
        CreateWorkLineResult result = new CreateWorkLineResult();
        result.setWorkLineId(entity.getId());
        result.setVersion(entity.getTxVersion());
        return result;
    }

    @Override
    public TxVersionResult updateWorkLine(String workLineId, String workLineName, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        TxVersionResult versionResult = new TxVersionResult();
        WorkLineEntity workLineEntity = workLineDao.findCorpWorkLineByIdThrow(workLineId, branCorpId);
        TxVersionUtil.compireVersion(workLineEntity.getTxVersion(), txVersion);
        String workLineOldName = workLineEntity.getLineName();
        if (StringUtils.isNotBlank(workLineName) && workLineName.equals(workLineOldName)) {
            versionResult.setVersion(txVersion);
            return versionResult;
        }
        WorkLineEntity find = workLineDao.findCorpWorkLineIdByName(workLineName, branCorpId);
        if (find != null) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "工段重复");
        }
        workLineEntity.setLineName(workLineName);
        workLineEntity.setUpdateUser(operator);
        workLineEntity.setUpdateTime(System.currentTimeMillis());
        try {
            workLineEntity.setTxVersion(workLineEntity.getTxVersion() + 1);
            workLineDao.update(workLineEntity);
            prospectiveEmployeeDao.updateCorpWorkLine(workLineId, workLineName, branCorpId);
            employeeDao.updateCorpWorkLine(workLineId, workLineName, branCorpId);
            leaveEmployeeDao.updateCorpWorkLine(workLineId, workLineName, branCorpId);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("修改工段" + workLineOldName + "为" + workLineEntity.getLineName() + "。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORKLINE, BranOpLogEntity.OP_TYPE_UPDATE, operator, info);
            versionResult.setVersion(workLineEntity.getTxVersion());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_UPDATE_WORK_LINE_FAILED);
        }
        return versionResult;
    }

    @Override
    public void deleteCorpWorkLine(String workLineId, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        WorkLineEntity entity = workLineDao.findCorpWorkLineByIdThrow(workLineId, branCorpId);
        TxVersionUtil.compireVersion(entity.getTxVersion(), txVersion);
        if (!isCorpWorkLineCanDelete(branCorpId, workLineId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_LINE_CAN_NOT_DELETE);
        }
        entity.setIsDelete(Constants.TRUE);
        entity.setUpdateUser(operator);
        entity.setUpdateTime(System.currentTimeMillis());
        try {
            workLineDao.update(entity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("删除了" + entity.getLineName() + "工段。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORKLINE, BranOpLogEntity.OP_TYPE_DELETE, operator, info);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_LINE_DELETE_FAILED);
        }
    }

    @Override
    public CreateWorkShiftResult addCorpWorkShift(String workShift, String branCorpId, String operator) throws AryaServiceException {
        if (StringUtils.isAnyBlank(workShift)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CONTENT_EMPTY);
        }
        if (workShift.length() > 32) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PARAM_TOO_LONG);
        }

        int workShiftTotalCount = workShiftDao.findCorpWorkShiftTotalCount(branCorpId);
        if (workShiftTotalCount > 90) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_COUNT_LIMITE);
        }
        if (workShiftDao.findCorpWorkShiftByNameAndBranCorpId(workShift, branCorpId) != null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_NAME_EXIST);
        }
        WorkShiftEntity entity = new WorkShiftEntity();
        entity.setId(Utils.makeUUID());
        entity.setCreateUser(operator);
        entity.setCreateTime(System.currentTimeMillis());
        entity.setBranCorpId(branCorpId);
        entity.setIsDelete(Constants.FALSE);
        entity.setShiftName(workShift);
        try {
            workShiftDao.create(entity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("新增了" + entity.getShiftName() + "班组。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORKSHIFT, BranOpLogEntity.OP_TYPE_ADD, operator, info);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_ADD_FAILED);
        }
        CreateWorkShiftResult result = new CreateWorkShiftResult();
        result.setWorkShiftId(entity.getId());
        result.setVersion(entity.getTxVersion());
        return result;
    }

    @Override
    public TxVersionResult updateWorkShift(String workShiftId, String workShiftName, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        TxVersionResult versionResult = new TxVersionResult();
        WorkShiftEntity workShiftEntity = workShiftDao.findCorpWorkShiftByIdThrow(workShiftId, branCorpId);
        TxVersionUtil.compireVersion(workShiftEntity.getTxVersion(), txVersion);
        String shiftOldName = workShiftEntity.getShiftName();
        if (StringUtils.isNotBlank(workShiftName) && workShiftName.equals(shiftOldName)) {
            versionResult.setVersion(txVersion);
            return versionResult;
        }
        WorkShiftEntity find = workShiftDao.findCorpWorkShiftByNameAndBranCorpId(workShiftName, branCorpId);
        if (find != null) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "班组重复");
        }

        workShiftEntity.setShiftName(workShiftName);
        workShiftEntity.setUpdateTime(System.currentTimeMillis());
        workShiftEntity.setUpdateUser(operator);
        try {
            workShiftEntity.setTxVersion(workShiftEntity.getTxVersion() + 1);
            workShiftDao.update(workShiftEntity);
            prospectiveEmployeeDao.updateCorpWorkShift(workShiftId, workShiftName, branCorpId);
            employeeDao.updateCorpWorkShift(workShiftId, workShiftName, branCorpId);
            leaveEmployeeDao.updateCorpWorkShift(workShiftId, workShiftName, branCorpId);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("修改班组" + shiftOldName + "为" + workShiftEntity.getShiftName() + "。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORKSHIFT, BranOpLogEntity.OP_TYPE_UPDATE, operator, info);
            versionResult.setVersion(workShiftEntity.getTxVersion());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_UPDATE_WORK_SHIFT_FAILED);
        }
        return versionResult;
    }

    @Override
    public void deleteCorpWorkShift(String workShiftId, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        WorkShiftEntity workShiftEntity = workShiftDao.findCorpWorkShiftByIdThrow(workShiftId, branCorpId);
        TxVersionUtil.compireVersion(workShiftEntity.getTxVersion(), txVersion);
        if (!isCorpWorkShiftCanDelete(branCorpId, workShiftId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_CAN_NOT_DELETE);
        }
        workShiftEntity.setIsDelete(Constants.TRUE);
        workShiftEntity.setUpdateTime(System.currentTimeMillis());
        workShiftEntity.setUpdateUser(operator);
        try {
            workShiftDao.update(workShiftEntity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("删除了" + workShiftEntity.getShiftName() + "班组。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_WORKSHIFT, BranOpLogEntity.OP_TYPE_DELETE, operator, info);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SHIFT_DELETE_FAILED);
        }
    }

    @Override
    public CreatePositionResult addCorpPosition(String positionName, String branCorpId, String operator) throws AryaServiceException {
        if (StringUtils.isAnyBlank(positionName)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CONTENT_EMPTY);
        }
        if (positionName.length() > 32) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PARAM_TOO_LONG);
        }

        int positionTotalCount = positionDao.findCorpPositionTotalCount(branCorpId);
        if (positionTotalCount > 90) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_POSITION_COUNT_LIMITE);
        }
        if (positionDao.findCorpPostionByNameAndBranCorpId(positionName, branCorpId) != null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_POSITION_NAME_EXIST);
        }
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setId(Utils.makeUUID());
        positionEntity.setCreateUser(operator);
        positionEntity.setCreateTime(System.currentTimeMillis());
        positionEntity.setBranCorpId(branCorpId);
        positionEntity.setIsDelete(Constants.FALSE);
        positionEntity.setPositionName(positionName);
        try {
            positionDao.create(positionEntity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("新增了" + positionName + "职位。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_POSITION, BranOpLogEntity.OP_TYPE_ADD, operator, info);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_POSITION_ADD_FAILED);
        }
        CreatePositionResult result = new CreatePositionResult();
        result.setPositionId(positionEntity.getId());
        result.setVersion(positionEntity.getTxVersion());
        return result;
    }

    @Override
    public TxVersionResult updatePosition(String positionId, String positionName, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        TxVersionResult versionResult = new TxVersionResult();
        PositionEntity positionEntity = positionDao.findCorpPositionByIdThrow(branCorpId, positionId);
        TxVersionUtil.compireVersion(positionEntity.getTxVersion(), txVersion);
        String positionOldName = positionEntity.getPositionName();

        if (StringUtils.isNotBlank(positionName) && positionName.equals(positionOldName)) {
            versionResult.setVersion(txVersion);
            return versionResult;
        }
        PositionEntity find = positionDao.findCorpPostionByNameAndBranCorpId(positionName, branCorpId);
        if (find != null) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "职位重复");
        }
        positionEntity.setPositionName(positionName);
        positionEntity.setUpdateTime(System.currentTimeMillis());
        positionEntity.setUpdateUser(operator);
        try {
            positionEntity.setTxVersion(positionEntity.getTxVersion() + 1);
            positionDao.update(positionEntity);
            prospectiveEmployeeDao.updateCorpPosition(positionId, positionName, branCorpId);
            employeeDao.updateCorpPosition(positionId, positionName, branCorpId);
            leaveEmployeeDao.updateCorpPosition(positionId, positionName, branCorpId);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("修改职位" + positionOldName + "为" + positionName + "。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_POSITION, BranOpLogEntity.OP_TYPE_ADD, operator, info);
            versionResult.setVersion(positionEntity.getTxVersion());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_UPDATE_POSITION_FAILED);
        }
        return versionResult;
    }

    @Override
    public void deleteCorpPosition(String positionId, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        PositionEntity positionEntity = positionDao.findCorpPositionByIdThrow(branCorpId, positionId);
        TxVersionUtil.compireVersion(positionEntity.getTxVersion(), txVersion);
        if (!isCorpPositionCanDelete(branCorpId, positionId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_POSITION_CAN_NOT_DELETE);
        }
        positionEntity.setIsDelete(Constants.TRUE);
        positionEntity.setUpdateTime(System.currentTimeMillis());
        positionEntity.setUpdateUser(operator);
        try {
            positionDao.update(positionEntity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("删除了" + positionEntity.getPositionName() + "职位。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_POSITION, BranOpLogEntity.OP_TYPE_DELETE, operator, info);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_POSITION_DELETE_FAILED);
        }
    }

    @Override
    public LeaveReasonListResult getCorpAllLeaveReason(String branCorpId) throws AryaServiceException {
        LeaveReasonListResult results = new LeaveReasonListResult();
        List<LeaveReasonEntity> leaveReasonEntities = leaveReasonDao.findCorpAllLeaveReason(branCorpId);
        for (LeaveReasonEntity leaveReasonEntity : leaveReasonEntities) {
            LeaveReasonListResult.LeaveReasonResult result = new LeaveReasonListResult.LeaveReasonResult();
            result.setLeaveReasonId(leaveReasonEntity.getId());
            result.setLeaveReasonName(leaveReasonEntity.getReasonName());
            result.setIsNotGood(leaveReasonEntity.getIsNotGood());
            result.setVersion(leaveReasonEntity.getTxVersion());
            results.add(result);
        }
        return results;
    }

    @Override
    public CreateLeaveReasonResult addCorpLeaveReason(String leaveReasonName, int isNotGood, String branCorpId, String operator) throws AryaServiceException {
        if (StringUtils.isAnyBlank(leaveReasonName)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CONTENT_EMPTY);
        }
        if (leaveReasonName.length() > 32) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PARAM_TOO_LONG);
        }
        int leaveReasonTotalCount = leaveReasonDao.findCorpLeaveReasonTotalCount(branCorpId);
        if (leaveReasonTotalCount > 90) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_LEAVE_REASON_COUNT_LIMITE);
        }
        if (leaveReasonDao.findLeaveReasonByName(leaveReasonName, branCorpId) != null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_LEAVE_REASON_EXIST);
        }
        LeaveReasonEntity leaveReasonEntity = new LeaveReasonEntity();
        leaveReasonEntity.setId(Utils.makeUUID());
        leaveReasonEntity.setCreateUser(operator);
        leaveReasonEntity.setCreateTime(System.currentTimeMillis());
        leaveReasonEntity.setBranCorpId(branCorpId);
        leaveReasonEntity.setIsNotGood(isNotGood);
        leaveReasonEntity.setIsDelete(Constants.FALSE);
        leaveReasonEntity.setReasonName(leaveReasonName);
        try {
            leaveReasonDao.create(leaveReasonEntity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("新增了" + leaveReasonName + (isNotGood == Constants.TRUE ? "不良" : "") + "离职原因。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_LEAVE_REASON, BranOpLogEntity.OP_TYPE_ADD, operator, info);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_POSITION_ADD_FAILED);
        }
        CreateLeaveReasonResult result = new CreateLeaveReasonResult();
        result.setLeaveReasonId(leaveReasonEntity.getId());
        result.setVersion(leaveReasonEntity.getTxVersion());
        return result;
    }

    @Override
    public TxVersionResult updateLeaveReason(String leaveReasonId, String leaveReasonName, Integer isBadReason, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        TxVersionResult versionResult = new TxVersionResult();
        LeaveReasonEntity leaveReasonEntity = leaveReasonDao.findCorpLeaveReasonByIdThrow(leaveReasonId, branCorpId);
        TxVersionUtil.compireVersion(leaveReasonEntity.getTxVersion(), txVersion);
        String leaveReasonOldName = leaveReasonEntity.getReasonName();

        if (StringUtils.isNotBlank(leaveReasonName) && leaveReasonName.equals(leaveReasonOldName)) {
            versionResult.setVersion(txVersion);
            return versionResult;
        }
        LeaveReasonEntity find = leaveReasonDao.findLeaveReasonByName(leaveReasonName, branCorpId);
        if (find != null) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "离职原因重复");
        }

        List<String> logMsgs = new ArrayList<>();
        if (!leaveReasonEntity.getReasonName().equals(leaveReasonName)) {
            logMsgs.add("名称为" + leaveReasonName);
            leaveReasonEntity.setReasonName(leaveReasonName);
        }
        if (leaveReasonEntity.getIsNotGood() != isBadReason) {
            if (isBadReason == Constants.TRUE) {
                logMsgs.add("标记为不良离职原因");
            } else {
                logMsgs.add("取消标记为不良离职原因");
            }
            leaveReasonEntity.setIsNotGood(isBadReason);
        }
        leaveReasonEntity.setUpdateUser(operator);
        leaveReasonEntity.setUpdateTime(System.currentTimeMillis());
        try {
            leaveReasonEntity.setTxVersion(leaveReasonEntity.getTxVersion() + 1);
            leaveReasonDao.update(leaveReasonEntity);
            if (logMsgs.size() > 0) {
                SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
                info.setMsg("修改离职原因：" + leaveReasonOldName + "," + StringUtils.join(logMsgs, "，") + "。");
                branOpLogDao.success(BranOpLogEntity.OP_MODULE_LEAVE_REASON, BranOpLogEntity.OP_TYPE_UPDATE, operator, info);
            }
            versionResult.setVersion(leaveReasonEntity.getTxVersion());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_UPDATE_LEAVE_REASON_FAILED);
        }
        return versionResult;
    }

    @Override
    public void deleteCorpLeaveReason(String leaveReasonId, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        LeaveReasonEntity leaveReasonEntity = leaveReasonDao.findCorpLeaveReasonByIdThrow(leaveReasonId, branCorpId);
        TxVersionUtil.compireVersion(leaveReasonEntity.getTxVersion(), txVersion);
        if (!isCorpLeaveReasonCanDelete(branCorpId, leaveReasonId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_LEAVE_REASON_BEEN_USED_CAN_NOT_DELETE);
        }
        leaveReasonEntity.setIsDelete(Constants.TRUE);
        try {
            leaveReasonDao.update(leaveReasonEntity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("删除了" + leaveReasonEntity.getReasonName() + "离职原因。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_LEAVE_REASON, BranOpLogEntity.OP_TYPE_UPDATE, operator, info);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_LEAVE_REASON_DELETE_FAILED);
        }
    }

    @Override
    public Boolean isCorpLeaveReasonCanDelete(String branCorpId, String leaveReasonId) throws AryaServiceException {
        //检查离职原因是否被使用
        if (leaveEmployeeDao.isLeaveReasonBeUsed(leaveReasonId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_LEAVE_REASON_BEEN_USED_CAN_NOT_DELETE);
        }
        return true;
    }

    @Override
    public NotificationListResult getCorpNotificationList(int page, int pageSize, String branCorpId) throws AryaServiceException {
        NotificationListResult result = new NotificationListResult();
        List<CorpNoticeEntity> noticeEntities = corpNoticeDao.findCorpPagnationNoticesByCorpId(page, pageSize, branCorpId);
        if (noticeEntities.size() == 0) {
            result.setPages(0);
            result.setTotalRows(0);
            result.setFilterRows(0);
            return result;
        }
        int noticesCount = corpNoticeDao.findCorpPagnationNoticesCountByCorpId(branCorpId);
        List<NotificationListResult.NotificationResult> results = new ArrayList<>();
        for (CorpNoticeEntity noticeEntity : noticeEntities) {
            NotificationResult result1 = new NotificationResult();
            result1.setNotificationId(noticeEntity.getId());
            result1.setNotificationTitle(noticeEntity.getNoticeTitle());
            result1.setNotificationContent(noticeEntity.getNoticeContent());
            result1.setPosterName(noticeEntity.getSender());
            result1.setPostTime(noticeEntity.getCreateTime());
            // 查询部门
            if (StringUtils.isNotBlank(noticeEntity.getDepartmentId())) {
                logger.info("查询部门");
                logger.info("noticeEntity.getDepartmentId()" + noticeEntity.getDepartmentId());
                logger.info("branCorpId" + branCorpId);
                DepartmentEntity department = departmentDao.findCorpDepartmentById(noticeEntity.getDepartmentId(),
                        branCorpId);
                if (department != null) {
                    result1.setDepartmentId(department.getId());
                    result1.setDepartmentName(department.getDepartmentName());
                }

            }
            results.add(result1);
        }
        result.setTotalRows(noticesCount);
        result.setFilterRows(noticesCount);
        result.setPages(Utils.calculatePages(noticesCount, pageSize));
        result.setNotifications(results);
        return result;
    }

    @Override
    public void deleteCorpNotifications(String[] ids, String operator) throws AryaServiceException {
        List<CorpNoticeEntity> noticeEntities = corpNoticeDao.findCorpNoticesByIds(ids);
        List<String> logMsg = new ArrayList<>();
        for (CorpNoticeEntity noticeEntity : noticeEntities) {
            logMsg.add(noticeEntity.getNoticeTitle());
            noticeEntity.setIsDelete(Constants.TRUE);
        }
        try {
            corpNoticeDao.update(noticeEntities);
            if (logMsg.size() > 0) {
                SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
                info.setMsg("删除" + logMsg.get(0) + (logMsg.size() > 1 ? "等一批共" + logMsg.size() + "条" : "") + "历史推送消息。");
                branOpLogDao.success(BranOpLogEntity.OP_MODULE_NOTIFICATION, BranOpLogEntity.OP_TYPE_DELETE, operator, info);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_NOTICE_DELETE_FAILED);
        }
    }

    @Override
    public void postNewNotification(PostNewNotificationCommand command) throws AryaServiceException {
        if (command.getNotificationTitle().length() > 32) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PUSH_TITLE_TOO_LONG);
        }
        if (command.getNotificationContent().length() > 256) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PUSH_CONTENT_TOO_LONG);
        }
        if (command.getPoster().length() > 32) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PUSH_POSTER_TOO_LONG);
        }
        // 判断departmentId
        if (StringUtils.isBlank(command.getDepartmentId())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        CorpNoticeEntity noticeEntity = new CorpNoticeEntity();
        noticeEntity.setId(Utils.makeUUID());
        noticeEntity.setIsDelete(Constants.FALSE);
        noticeEntity.setCorpId(command.getBranCorpId());
        noticeEntity.setCreateUser(command.getCurrentUserId());
        noticeEntity.setCreateTime(System.currentTimeMillis());
        noticeEntity.setUpdateTime(System.currentTimeMillis());
        noticeEntity.setUpdateUser(command.getCurrentUserId());
        noticeEntity.setNoticeTitle(command.getNotificationTitle());
        noticeEntity.setNoticeContent(command.getNotificationContent());
        noticeEntity.setSender(command.getPoster());
        noticeEntity.setDepartmentId(command.getDepartmentId());
        try {
            corpNoticeDao.create(noticeEntity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("推送了一条标题为《" + noticeEntity.getNoticeTitle() + "》的新消息。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_NOTIFICATION, BranOpLogEntity.OP_TYPE_SEND, command.getCurrentUserId(), info);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_NOTICE_CREATE_FAILED);
        }
        try {
            List<String> branUserIds = employeeDao.findCorpBranUserIds(command.getBranCorpId());
            if (branUserIds.size() == 0) {
                return;
            }
            List<String> aryaUserIds = branUserDao.findAryaUserIdsByBranUserIds(branUserIds);
            if (aryaUserIds.size() == 0) {
                return;
            }
            List<AryaUserQueryModel> aryaUserQueryEntities = aryaUserDao.findAryaUserIdAndClientTypes(aryaUserIds);
            if (aryaUserQueryEntities.size() == 0) {
                return;
            }
            //挑出android用户
            List<String> androidUserIds = new ArrayList<>();
            for (int i = 0; i < aryaUserQueryEntities.size(); i++) {
                AryaUserQueryModel aryaUserQueryModel = aryaUserQueryEntities.get(i);
                if (aryaUserQueryModel.getLAST_CLIENT_TYPE() == com.bumu.arya.common.Constants.CLINET_ANDROID) {
                    androidUserIds.add(aryaUserQueryModel.getID());
                    aryaUserQueryEntities.remove(i--);
                }
            }
            if (androidUserIds.size() > 0) {
                //pushAndroid
                PushManyNoticeThread pushManyNoticeThread = new PushManyNoticeThread(pushService, command.getNotificationTitle()
                        , command.getNotificationContent(), androidUserIds, com.bumu.arya.common.Constants.CLINET_ANDROID, Constants.CORP_NOTICE_JUMP_CORP_BROADCAST, command.getCurrentUserId());
                pushManyNoticeThread.run();
            }
            //挑出iOS用户
            List<String> iosUserIds = new ArrayList<>();
            for (int i = 0; i < aryaUserQueryEntities.size(); i++) {
                AryaUserQueryModel aryaUserQueryModel = aryaUserQueryEntities.get(i);
                if (aryaUserQueryModel.getLAST_CLIENT_TYPE() == com.bumu.arya.common.Constants.CLINET_IOS) {
                    iosUserIds.add(aryaUserQueryModel.getID());
                    aryaUserQueryEntities.remove(i--);
                }
            }
            if (iosUserIds.size() > 0) {
                //pushiOS
                PushManyNoticeThread pushManyNoticeThread = new PushManyNoticeThread(pushService, command.getNotificationTitle()
                        , command.getNotificationContent(), iosUserIds, com.bumu.arya.common.Constants.CLINET_IOS, Constants.CORP_NOTICE_JUMP_CORP_BROADCAST, command.getCurrentUserId());
                pushManyNoticeThread.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_PUSH_FAILED);
        }
    }

    @Override
    public CorpInfoResult getCorpInfoDetail(String branCorpId) throws Exception {
        BranCorporationEntity branCorporationEntity = corporationDao.findCorpByIdThrow(branCorpId);
        CorporationEntity corporationEntity = aryaCorporationDao.findCorpByBranCorpIdThrow(branCorpId);
        List<BranCorpImageEntity> imageEntities = corpImageDao.findBranCorpImagesByBranCorpId(branCorpId);
        CorpInfoResult result = new CorpInfoResult();
        result.setName(corporationEntity.getName());
        result.setEmail(corporationEntity.getEmail());
        result.setFax(corporationEntity.getFax());
        result.setTelephone(corporationEntity.getTelephone());
        result.setType(CorpConstants.getCorpTypeByCode(branCorporationEntity.getCorpType()));
        if (!StringUtils.isAnyBlank(branCorporationEntity.getProvinceName())) {
            result.setAddress(branCorporationEntity.getProvinceName());
        }
        if (!StringUtils.isAnyBlank(branCorporationEntity.getCityName())) {
            result.setAddress(result.getAddress() + branCorporationEntity.getCityName());
        }
        if (!StringUtils.isAnyBlank(branCorporationEntity.getCountyName())) {
            result.setAddress(result.getAddress() + branCorporationEntity.getCountyName());
        }
        if (!StringUtils.isAnyBlank(branCorporationEntity.getAddress())) {
            result.setAddress(StringUtils.isNotBlank(result.getAddress()) ? result.getAddress() : "" + branCorporationEntity.getAddress());
        }
        if (imageEntities.size() > 0) {
            String[] imageUrls = new String[imageEntities.size()];
            for (int i = 0; i < imageEntities.size(); i++) {
                BranCorpImageEntity imageEntity = imageEntities.get(i);
                imageUrls[i] = fileService.getBranCorpImageUrl(imageEntity.getFileName());
            }
            result.setImages(imageUrls);
        }
        result.setQrCodeUrl(fileService.generateCorpCheckinQRCodeUrl(branCorporationEntity.getCheckinCode()));
        result.setCheckinCode(branCorporationEntity.getCheckinCode());
        return result;
    }

    @Override
    public CheckinMessageResult getCorpCheckinMessage(String branCorpId) throws AryaServiceException {
        CheckinMessageResult result = new CheckinMessageResult();
        BranCorpCheckinMessageEntity messageEntity = branCorpCheckinMsgDao.findCheckinMessageByBranCorpId(branCorpId);
        if (messageEntity == null) {
            return result;
        }
        if (StringUtils.isAnyBlank(messageEntity.getMessageContent())) {
            result.setMessageContent(EmployeeConstants.CORP_CHECKIN_MESSAGE);
        } else {
            result.setMessageContent(messageEntity.getMessageContent());
        }
        result.setBeforeCheckinDay(messageEntity.getBeforeCheckinDay());
        result.setIsWorking(messageEntity.getIsWorking());
        result.setPostHour(messageEntity.getPostHour());
        result.setVersion(messageEntity.getVersion());
        return result;
    }

    @Override
    public TxVersionResult updateCorpCheckinMessage(UpdateCheckinMessageCommand command, String operator, String branCorpId) throws AryaServiceException {
        TxVersionResult versionResult = new TxVersionResult();
        if (command.getBeforeCheckinDay() > 10) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CHECKIN_MESSAGE_BEFORE_DAY_OUT);
        }

//		if (command.getPostHour() < 6 || command.getPostHour() > 22) {
//			throw new AryaServiceException(ErrorCode.CODE_CORP_CHECKIN_MESSAGE_SEND_TIME_OUT);
//		}
        List<String> logMsgs = new ArrayList<>();
        BranCorpCheckinMessageEntity messageEntity = branCorpCheckinMsgDao.findCheckinMessageByBranCorpId(branCorpId);
        TxVersionUtil.compireVersion(messageEntity.getVersion(), command.getVersion());
        if (messageEntity == null) {
            messageEntity = new BranCorpCheckinMessageEntity();
        }
        //判断是否需要修改所有待入职员工的提醒任务
        if (!Objects.equals(command.getBeforeCheckinDay(), messageEntity.getBeforeCheckinDay())
                || !Objects.equals(command.getPostHour(), messageEntity.getPostHour())
                || !Objects.equals(command.getIsWorking(), messageEntity.getIsWorking()))
            try {
                List<ProspectiveEmployeeEntity> prospectiveEmployeeEntities = prospectiveEmployeeDao.findCorpAllProspectiveEmployees(branCorpId);
                if (!Objects.equals(command.getIsWorking(), messageEntity.getIsWorking())) {
                    //打开或关闭提醒任务
                    if (command.getIsWorking() != null && command.getIsWorking() == Constants.FALSE) {
                        //关闭
                        logMsgs.add("关闭了入职提醒");
                        logger.info("【入职提醒】开始关闭" + branCorpId + "公司的所有入职提醒任务。");
                        logger.info("【入职提醒】总共" + prospectiveEmployeeEntities.size() + "人。");
                        for (ProspectiveEmployeeEntity prospectiveEmployeeEntity : prospectiveEmployeeEntities) {
                            employeeService.deleteCheckinMessageJob(prospectiveEmployeeEntity.getPhoneNo());
                        }
                        logger.info("【入职提醒】关闭" + branCorpId + "公司的所有入职提醒任务完成。");
                    } else if (command.getIsWorking() != null && command.getIsWorking() == Constants.TRUE) {
                        //打开
                        logMsgs.add("打开了入职提醒");
                        int postBeforeDay = 0;
                        if (command.getBeforeCheckinDay() != null && !Objects.equals(command.getBeforeCheckinDay(), messageEntity.getBeforeCheckinDay())) {
                            postBeforeDay = command.getBeforeCheckinDay();
                            logMsgs.add("入职前" + messageEntity.getBeforeCheckinDay() + "天修改为" + command.getBeforeCheckinDay() + "天");
                        } else {
                            postBeforeDay = messageEntity.getBeforeCheckinDay();
                        }

                        int postHour = 0;
                        if (command.getPostHour() != null && !Objects.equals(command.getPostHour(), messageEntity.getPostHour())) {
                            postHour = command.getPostHour();
                            logMsgs.add("发送时间" + messageEntity.getPostHour() + "点改为" + command.getPostHour() + "点");
                        } else {
                            postHour = messageEntity.getPostHour();
                        }
                        //开启
                        logger.info("【入职提醒】开始打开" + branCorpId + "公司的所有入职提醒任务。");
                        logger.info("【入职提醒】总共" + prospectiveEmployeeEntities.size() + "人。");
                        for (ProspectiveEmployeeEntity prospectiveEmployeeEntity : prospectiveEmployeeEntities) {
                            CheckinMessageSendTimeModel sendTimeModel = employeeService.calculateCheckinMessageSendTime(prospectiveEmployeeEntity.getCheckinTime(), postBeforeDay, postHour);
                            scheduleService.scheduleCheckinNotification(branCorpId, prospectiveEmployeeEntity.getPhoneNo(), prospectiveEmployeeEntity.getCheckinTime(), sendTimeModel.getYear(), sendTimeModel.getMonth(), sendTimeModel.getDay(), sendTimeModel.getHour(), sendTimeModel.getMinute());
                        }
                        logger.info("【入职提醒】打开" + branCorpId + "公司的所有入职提醒任务完成。");
                    }
                } else if (Objects.equals(command.getIsWorking(), messageEntity.getIsWorking()) && command.getIsWorking() != null && command.getIsWorking() == Constants.TRUE) {
                    //如果只是改变任务时间
                    int postBeforeDay = 0;
                    int postHour = 0;
                    if (command.getBeforeCheckinDay() != null) {
                        postBeforeDay = command.getBeforeCheckinDay();
                        logMsgs.add("入职前" + messageEntity.getBeforeCheckinDay() + "天修改为" + command.getBeforeCheckinDay() + "天");
                    } else {
                        postBeforeDay = messageEntity.getBeforeCheckinDay();
                    }
                    if (command.getPostHour() != null) {
                        postHour = command.getPostHour();
                        logMsgs.add("发送时间" + messageEntity.getPostHour() + "点改为" + command.getPostHour() + "点");
                    } else {
                        postHour = messageEntity.getPostHour();
                    }
                    logger.info("【入职提醒】开始修改" + branCorpId + "公司的所有入职提醒任务。");
                    logger.info("【入职提醒】总共" + prospectiveEmployeeEntities.size() + "人。");
                    for (ProspectiveEmployeeEntity prospectiveEmployeeEntity : prospectiveEmployeeEntities) {
                        CheckinMessageSendTimeModel sendTimeModel = employeeService.calculateCheckinMessageSendTime(prospectiveEmployeeEntity.getCheckinTime(), postBeforeDay, postHour);
                        scheduleService.rescheduleCheckinNotification(branCorpId, prospectiveEmployeeEntity.getPhoneNo(), prospectiveEmployeeEntity.getCheckinTime(), sendTimeModel.getYear(), sendTimeModel.getMonth(), sendTimeModel.getDay(), sendTimeModel.getHour(), sendTimeModel.getMinute());
                    }
                    logger.info("【入职提醒】修改" + branCorpId + "公司的所有入职提醒任务完成。");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("更新所有待入职员工的入职提醒任务失败。");
            }

        messageEntity.setBranCorpId(branCorpId);
        messageEntity.setMessageContent("xxx提醒您，距离xxxx年xx月xx日入职还有x天。");
        messageEntity.setPostHour(command.getPostHour());
        messageEntity.setIsWorking(command.getIsWorking());
        messageEntity.setBeforeCheckinDay(command.getBeforeCheckinDay());
        messageEntity.setVersion(messageEntity.getVersion() + 1);
        versionResult.setVersion(messageEntity.getVersion());
        branCorpCheckinMsgDao.updateCheckinMessage(messageEntity);
        if (logMsgs.size() > 0) {
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("修改了入职提醒配置：" + StringUtils.join(logMsgs, "，") + "。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_CHECKIN_MESSAGE, BranOpLogEntity.OP_TYPE_UPDATE, operator, info);
        }
        return versionResult;
    }

    @Override
    public CorpOperationListResult getCorpOperateLog(CorpOpLogQueryCommand command) throws Exception {
        List<BranOpLogEntity> logEntities = branOpLogDao.findFilterPagnationLogs(command);
        CorpOperationListResult listResult = new CorpOperationListResult();
        List<CorpOperationResult> results = new ArrayList<>();
        listResult.setLogs(results);
        if (logEntities.size() == 0) {
            listResult.setTotalRows(0);
            listResult.setFilterRows(0);
            listResult.setPages(0);
            return listResult;
        }
        for (BranOpLogEntity logEntity : logEntities) {
            CorpOperationResult result = new CorpOperationResult();
            result.setId(logEntity.getId());
            result.setOperatorId(logEntity.getOperatorId());
            if (StringUtils.isNotBlank(logEntity.getOpRealName())) {
                result.setOperatorName(logEntity.getOpRealName());
            } else {
                result.setOperatorName(logEntity.getOpLoginName());
            }
            ObjectMapper mapper = new ObjectMapper();
            SysLogExtInfo info = null;
            try {
                info = mapper.readValue(logEntity.getExtInfo(), SysLogExtInfo.class);
                result.setLog(info.getMsg());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("读取企业操作日志失败！");
                continue;
            }
            result.setTime(logEntity.getCreateTime());
            results.add(result);
        }
        listResult.setFilterRows(branOpLogDao.coutFilterLogs(command));
        listResult.setTotalRows(branOpLogDao.coutCorpTotalLogs(command.getBranCorpId()));
        listResult.setPages(Utils.calculatePages(listResult.getFilterRows(), command.getPageSize()));
        return listResult;
    }

    @Override
    public OpModuleListResult getCorpOpModuleList(String branCorpId) throws Exception {
        OpModuleListResult listResult = new OpModuleListResult();
        List<OpModuleListResult.OpModuleResult> results = new ArrayList<>();
        listResult.setModules(results);
        for (Integer key : BranOpLogEntity.OP_MODULE_MAP.keySet()) {
            OpModuleListResult.OpModuleResult result = new OpModuleListResult.OpModuleResult();
            result.setId(key.toString());
            result.setName(BranOpLogEntity.OP_MODULE_MAP.get(key));
            results.add(result);
        }
        return listResult;
    }

    @Override
    public OpTypeListResult getCorpOpTypeList(String moduleId, String branCorpId) throws Exception {
        OpTypeListResult listResult = new OpTypeListResult();
        List<OpTypeListResult.OpTypeResult> results = new ArrayList<>();
        listResult.setTypes(results);
        Map<Integer, String> typeMap = BranOpLogEntity.OP_TYPE_MAP.get(Integer.parseInt(moduleId));
        for (Integer key : typeMap.keySet()) {
            OpTypeListResult.OpTypeResult result = new OpTypeListResult.OpTypeResult();
            result.setId(key.toString());
            result.setName(typeMap.get(key));
            results.add(result);
        }
        return listResult;
    }

    @Override
    public List<Node> export(String branCorpId) throws Exception {
        logger.debug("export start ...");
        // 查询改公司下面的所有部门
        List<DepartmentEntity> list = departmentDao.findCorpAllDepartments(branCorpId);
        // 把entity转换成为node对象
        List<DepartmentNode> nodes = nodeCoreHelper.nodeConverter(list);
        // 生成树的json数据
        List<DepartmentNode> results = nodeCoreHelper.generateNodeTree(nodes);
        // 生成echarts能够识别的json数据
        return nodeCoreHelper.echartsNodeConverter(results, "部门架构图");

    }

    @Override
    public List<DepartmentListResult.DepartmentResult> getCorpDepartments(String branCorpId) throws Exception {

        List<DepartmentListResult.DepartmentResult> results = new ArrayList<>();
        Session session = departmentDao.getHbSession();
        SQLQuery queryNamed = (SQLQuery) session.getNamedQuery("findDepartmentPersonCount");
        queryNamed.setString("branCorpId", branCorpId);

        queryNamed.addScalar("id", StandardBasicTypes.STRING);
        queryNamed.addScalar("name", StandardBasicTypes.STRING);
        queryNamed.addScalar("parentId", StandardBasicTypes.STRING);
        queryNamed.addScalar("count", StandardBasicTypes.INTEGER);
        queryNamed.addScalar("version", StandardBasicTypes.INTEGER);
        queryNamed.setResultTransformer(Transformers.aliasToBean(Node.class));
        List<Node> list = queryNamed.list();
        if (list == null) {
            return null;
        }
        try {
            list = nodeCoreHelper.generateNodeTree(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        for (Node node : list) {
            DepartmentListResult.DepartmentResult result = new DepartmentListResult.DepartmentResult();
            result.setVersion(node.getVersion());
            result.setParentId(node.getParentId());
            result.setDepartmentCount(node.getCount());
            result.setDepartmentId(node.getId());
            result.setDepartmentName(node.getName());
            results.add(result);
        }
        return results;
    }

    @Override
    public CreateDepartmentResult addCorpDepartment(String parentDepartmentId, String departmentName, String branCorpId, String operator) throws AryaServiceException {
        if (StringUtils.isAnyBlank(departmentName)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CONTENT_EMPTY);
        }
        if (departmentName.length() > 32) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PARAM_TOO_LONG);
        }
        int corpDepartmentTotalCount = departmentDao.findCorpDepartmentCount(branCorpId);
        if (corpDepartmentTotalCount > 150) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_COUNT_LIMITE);
        }
        if (departmentDao.findCorpDepartmentIdByName(departmentName, branCorpId) != null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPATMENT_NAME_EXIST);
        }

        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(Utils.makeUUID());
        departmentEntity.setIsDelete(Constants.FALSE);
        departmentEntity.setBranCorpId(branCorpId);
        departmentEntity.setCreateUser(operator);
        departmentEntity.setCreateTime(System.currentTimeMillis());
        departmentEntity.setParentId(parentDepartmentId);
        departmentEntity.setDepartmentName(departmentName);
        try {
            departmentDao.create(departmentEntity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("新建了" + departmentName + "部门。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_DEPARTMENT, BranOpLogEntity.OP_TYPE_ADD, operator, info);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_ADD_FAILED);
        }
        CreateDepartmentResult result = new CreateDepartmentResult();
        result.setDepartmentId(departmentEntity.getId());
        result.setVersion(departmentEntity.getTxVersion());
        return result;
    }

    @Override
    public TxVersionResult updateDepartment(String departmentId, String departmentName, String branCorpId,
                                            String operator, long txVersion) throws AryaServiceException {
        ExceptionModel exceptionModel = new ExceptionModel();
        TxVersionResult versionResult = new TxVersionResult();
        if (StringUtils.isAnyBlank(departmentName)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CONTENT_EMPTY);
        }
        if (departmentName.length() > 32) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PARAM_TOO_LONG);
        }
        DepartmentEntity departmentEntity = departmentDao.findCorpDepartmentByIdThrow(departmentId, branCorpId);
        TxVersionUtil.compireVersion(departmentEntity.getTxVersion(), txVersion);
        // 修改时候如果与原来的名字相同
        String departmentOldName = departmentEntity.getDepartmentName();
        if (departmentName.trim().equals(departmentOldName)) {
            exceptionModel.setError(ErrorCode.CODE_PARAMS_ERROR);
            exceptionModel.setMsg("修改的名字与原始名字相同,请检查后重新修改");
            throw new AryaServiceException(exceptionModel);
        }
        // 修改时候如果存在相同的名字
        if (departmentDao.findCorpDepartmentIdByName(departmentName, branCorpId) != null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPATMENT_NAME_EXIST);
        }
        departmentEntity.setDepartmentName(departmentName);
        departmentEntity.setUpdateTime(System.currentTimeMillis());
        departmentEntity.setUpdateUser(operator);
        departmentEntity.setTxVersion(departmentEntity.getTxVersion() + 1);
        try {
            departmentDao.update(departmentEntity);
            prospectiveEmployeeDao.updateCorpDepartment(departmentEntity.getId(), departmentEntity.getDepartmentName(), branCorpId);
            employeeDao.updateCorpDepartment(departmentEntity.getId(), departmentEntity.getDepartmentName(), branCorpId);
            leaveEmployeeDao.updateCorpDepartment(departmentEntity.getId(), departmentEntity.getDepartmentName(), branCorpId);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("修改部门：" + departmentOldName + "为" + departmentEntity.getDepartmentName() + "。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_DEPARTMENT, BranOpLogEntity.OP_TYPE_UPDATE, operator, info);
            versionResult.setVersion(departmentEntity.getTxVersion());
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_UPDATE_FAILED);
        }
        return versionResult;
    }

    @Override
    public void deleteCorpDepartment(String departmentId, String branCorpId, String operator, long txVersion) throws AryaServiceException {
        DepartmentEntity departmentEntity = departmentDao.findCorpDepartmentByIdThrow(departmentId, branCorpId);
        TxVersionUtil.compireVersion(departmentEntity.getTxVersion(), txVersion);
        if (!isCorpDepartmentCanDelete(branCorpId, departmentId)) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_CAN_NOT_DELETE);
        }
        departmentEntity.setIsDelete(Constants.TRUE);
        departmentEntity.setUpdateTime(System.currentTimeMillis());
        departmentEntity.setUpdateUser(operator);
        try {
            departmentDao.update(departmentEntity);
            SysLogDao.SysLogExtInfo info = new SysLogExtInfo();
            info.setMsg("删除了" + departmentEntity.getDepartmentName() + "部门。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_DEPARTMENT, BranOpLogEntity.OP_TYPE_DELETE, operator, info);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_DEPARTMENT_DELETE_FAILED);
        }
    }

}
