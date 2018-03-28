package com.bumu.bran.admin.approval.result;

import com.bumu.approval.model.dao.ApprovalTypeDao;
import com.bumu.approval.model.entity.ApprovalEntity;
import com.bumu.approval.result.ApprovalResult;
import com.bumu.arya.model.entity.WorkShiftTypeEntity;
import com.bumu.bran.attendance.model.dao.WorkAttendanceDao;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.result.EmpCorpInfoResult;
import com.bumu.bran.model.entity.attendance.WorkAttendanceEntity;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeDao;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.function.VoConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * 审批管理返回对象
 *
 * @author majun
 * @date 2017/10/16
 * @email 351264830@qq.com
 */
@ApiModel
public class ApprovalManagerResult implements VoConverter<ApprovalEntity> {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private EmployeeDao employeeDao;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private ApprovalTypeDao approvalTypeDao;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private WorkAttendanceDao workAttendanceDao;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private BranWorkShiftTypeDao workShiftTypeDao;
    @ApiModelProperty(value = "审批信息")
    private ApprovalResult approvalInfo;
    @ApiModelProperty(value = "员工信息")
    private EmpCorpInfoResult empInfo;
    @ApiModelProperty(value = "班次信息")
    private ApprovalWorkShiftTypeResult workShiftType;

    public ApprovalManagerResult() {
    }

    public ApprovalResult getApprovalInfo() {
        return approvalInfo;
    }

    public void setApprovalInfo(ApprovalResult approvalInfo) {
        this.approvalInfo = approvalInfo;
    }

    public EmpCorpInfoResult getEmpInfo() {
        return empInfo;
    }

    public void setEmpInfo(EmpCorpInfoResult empInfo) {
        this.empInfo = empInfo;
    }

    @Override
    public void convert(ApprovalEntity approvalEntity) {
        if (approvalEntity == null) {
            return;
        }
        // 审批
        ApprovalResult approvalResult = new ApprovalResult();
        approvalResult.setApprovalTypeDao(approvalTypeDao);
        approvalResult.convert(approvalEntity);
        this.setApprovalInfo(approvalResult);
        // 员工
        EmployeeEntity employeeEntity = employeeDao.findByIdNotDelete(approvalEntity.getEmpId());
        if (employeeEntity == null) {
            return;
        }
        EmpCorpInfoResult empCorpInfoResult = new EmpCorpInfoResult();
        empCorpInfoResult.convert(employeeEntity);
        this.setEmpInfo(empCorpInfoResult);
        // 考勤
        if (StringUtils.isNotBlank(approvalEntity.getWorkAttendanceId())) {
            WorkAttendanceEntity workAttendanceEntity = workAttendanceDao.findByIdNotDelete(approvalEntity.getWorkAttendanceId());
            if (workAttendanceEntity != null && workAttendanceEntity.getWorkShiftTypeId() != null) {
                WorkShiftTypeEntity workShiftTypeEntity = workShiftTypeDao.findByIdNotDelete(workAttendanceEntity.getWorkShiftTypeId());
                if (workShiftTypeEntity != null) {
                    ApprovalWorkShiftTypeResult workShiftType = new ApprovalWorkShiftTypeResult();
                    workShiftType.setId(workShiftTypeEntity.getId());
                    workShiftType.setName(workShiftTypeEntity.getName());
                    workShiftType.setStartTime(workShiftTypeEntity.getWorkStartTime());
                    workShiftType.setEndTime(workShiftTypeEntity.getWorkEndTime());
                    this.setWorkShiftType(workShiftType);
                }
            }
        }
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public ApprovalTypeDao getApprovalTypeDao() {
        return approvalTypeDao;
    }

    public void setApprovalTypeDao(ApprovalTypeDao approvalTypeDao) {
        this.approvalTypeDao = approvalTypeDao;
    }

    public WorkAttendanceDao getWorkAttendanceDao() {
        return workAttendanceDao;
    }

    public void setWorkAttendanceDao(WorkAttendanceDao workAttendanceDao) {
        this.workAttendanceDao = workAttendanceDao;
    }

    public BranWorkShiftTypeDao getWorkShiftTypeDao() {
        return workShiftTypeDao;
    }

    public void setWorkShiftTypeDao(BranWorkShiftTypeDao workShiftTypeDao) {
        this.workShiftTypeDao = workShiftTypeDao;
    }

    public ApprovalWorkShiftTypeResult getWorkShiftType() {
        return workShiftType;
    }

    public void setWorkShiftType(ApprovalWorkShiftTypeResult workShiftType) {
        this.workShiftType = workShiftType;
    }
}
