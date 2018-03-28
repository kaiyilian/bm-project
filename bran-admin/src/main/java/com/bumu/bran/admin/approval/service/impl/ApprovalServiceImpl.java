package com.bumu.bran.admin.approval.service.impl;

import com.bumu.approval.command.ApprovalManagerQueryCommand;
import com.bumu.approval.model.dao.ApprovalDao;
import com.bumu.approval.model.dao.ApprovalTypeDao;
import com.bumu.approval.model.entity.ApprovalEntity;
import com.bumu.arya.command.PagerCommand;
import com.bumu.bran.admin.approval.result.ApprovalManagerResult;
import com.bumu.bran.admin.approval.service.ApprovalService;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.approval.engine.ApprovalEngine;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeDao;
import com.bumu.bran.attendance.model.dao.WorkAttendanceDao;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BatchCommand;
import com.bumu.common.result.Pager;
import com.bumu.common.util.ListUtils;
import com.bumu.econtract.PagerHelper;
import com.bumu.exception.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author majun
 * @date 2017/10/14
 * @email 351264830@qq.com
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private ApprovalDao approvalDao;
    @Autowired
    private ExcelExportHelper excelExportHelper;
    @Autowired
    private BranAdminConfigService branAdminConfigService;
    @Autowired
    private ApprovalEngine approvalEngine;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private ApprovalTypeDao approvalTypeDao;
    @Autowired
    private WorkAttendanceDao workAttendanceDao;
    @Autowired
    private BranWorkShiftTypeDao branWorkShiftTypeDao;


    @Override
    public Pager<ApprovalManagerResult> get(ApprovalManagerQueryCommand approvalManagerQueryCommand, PagerCommand pagerCommand) {

        Pager<ApprovalManagerResult> resultPager = PagerHelper.mapTo(
                approvalDao.findViewPagerByCommand(approvalManagerQueryCommand, pagerCommand),
                one -> {
                    ApprovalManagerResult approvalManagerResult = new ApprovalManagerResult();
                    approvalManagerResult.setEmployeeDao(employeeDao);
                    approvalManagerResult.setApprovalTypeDao(approvalTypeDao);
                    approvalManagerResult.setWorkAttendanceDao(workAttendanceDao);
                    approvalManagerResult.setWorkShiftTypeDao(branWorkShiftTypeDao);
                    approvalManagerResult.convert(one);
                    return approvalManagerResult;
                });
        return resultPager;
    }

    @Override
    public ApprovalManagerResult detail(String approvalId, SessionInfo sessionInfo) {
        // 查询
        Assert.notBlank(approvalId, "审批id必填");
        ApprovalEntity approvalEntity = approvalDao.findByIdNotDelete(approvalId);
        // 转化为vo对象
        ApprovalManagerResult approvalManagerResult = new ApprovalManagerResult();
        approvalManagerResult.setEmployeeDao(employeeDao);
        approvalManagerResult.setApprovalTypeDao(approvalTypeDao);
        approvalManagerResult.setWorkAttendanceDao(workAttendanceDao);
        approvalManagerResult.setWorkShiftTypeDao(branWorkShiftTypeDao);
        approvalManagerResult.convert(approvalEntity);
        return approvalManagerResult;
    }

    @Override
    public void pass(BatchCommand batch, SessionInfo sessionInfo) {
        batch.getBatch().forEach(one -> {
            ApprovalEntity approvalEntity = approvalDao.findByIdNotDelete(one.getId());
            if (approvalEntity != null) {
                // 通过
                approvalEntity.setApprovalState(2);
                // 审批时间
                approvalEntity.setApprovalTime(new Date());
                // 审批计算
                approvalEngine.generate(approvalEntity.getId());
                approvalDao.update(approvalEntity);
            }
        });

    }

    @Override
    public void fail(BatchCommand batch, SessionInfo sessionInfo) {
        batch.getBatch().forEach(one -> {
            ApprovalEntity approvalEntity = approvalDao.findByIdNotDelete(one.getId());
            if (approvalEntity != null) {
                // 未通过
                approvalEntity.setApprovalState(1);
                // 审批时间
                approvalEntity.setApprovalTime(new Date());
                approvalDao.update(approvalEntity);
            }
        });
    }

    @Override
    public void export(ApprovalManagerQueryCommand approvalManagerQueryCommand, SessionInfo sessionInfo, HttpServletResponse httpServletResponse) {
        
        List<ApprovalEntity> list = approvalDao.findViewByCommand(approvalManagerQueryCommand);
        List<ApprovalManagerResult> listResult = ListUtils.mapTo(list,
                one -> {
                    ApprovalManagerResult approvalManagerResult = new ApprovalManagerResult();
                    approvalManagerResult.setEmployeeDao(employeeDao);
                    approvalManagerResult.setApprovalTypeDao(approvalTypeDao);
                    approvalManagerResult.setWorkAttendanceDao(workAttendanceDao);
                    approvalManagerResult.setWorkShiftTypeDao(branWorkShiftTypeDao);
                    approvalManagerResult.convert(one);
                    return approvalManagerResult;
                });
        Map<String, Object> params = new HashMap<>();
        params.put("list", listResult);
        excelExportHelper.export(
                branAdminConfigService.getExcelTemplateLocation() + BranAdminConfigService.WORK_ATTENDANCE_APPROVAL_TEMPLATE,
                "审批列表",
                params,
                httpServletResponse
        );
    }
}
