package com.bumu.arya.salary.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.common.Constants;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.salary.command.ProjectApplyCommand;
import com.bumu.arya.salary.command.ProjectApplyCustomerCommand;
import com.bumu.arya.salary.command.ProjectApplyUpdateCommand;
import com.bumu.arya.salary.command.ProjectFollowCommand;
import com.bumu.arya.salary.dao.CustomerDao;
import com.bumu.arya.salary.dao.CustomerFollowDao;
import com.bumu.arya.salary.dao.ProjectApplyDao;
import com.bumu.arya.salary.model.entity.CustomerEntity;
import com.bumu.arya.salary.model.entity.CustomerFollowEntity;
import com.bumu.arya.salary.model.entity.ProjectApplyEntity;
import com.bumu.arya.salary.result.CustomerFollowResult;
import com.bumu.arya.salary.result.ProjectApplyResult;
import com.bumu.arya.salary.service.ProjectApplyService;
import com.bumu.arya.salary.service.SalaryLogService;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.Pager;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.common.util.ListUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/6
 */
@Service
public class ProjectApplyServiceImpl implements ProjectApplyService {
    
    private static Logger logger = getLogger(ProjectApplyServiceImpl.class);

    @Autowired
    private ProjectApplyDao projectApplyDao;

    @Autowired
    private CustomerFollowDao customerFollowDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private SalaryLogService salaryLogService;

    @Autowired
    private SysUserService sysUserService;


    @Override
    public Pager<ProjectApplyResult> getPage(String condition, Integer page, Integer pageSize) throws Exception {
        Pager<ProjectApplyEntity> pager = projectApplyDao.getPage(condition, page, pageSize);
        Pager<ProjectApplyResult> result = new Pager<>();
        if (ListUtils.checkNullOrEmpty(pager.getResult())) {
            return result;
        }
        result.setPageSize(pageSize);
        result.setRowCount(pager.getRowCount());
        result.setResult(pager.getResult().stream().map(
                entity -> {
                    ProjectApplyResult projectApplyResult = new ProjectApplyResult();
                    projectApplyResult.convert(entity);
                    return projectApplyResult;
                }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public ProjectApplyResult view (String id) {
        ProjectApplyEntity projectApplyEntity = projectApplyDao.findByIdNotDelete(id);
        if (null != projectApplyEntity) {
            ProjectApplyResult projectApplyResult = new ProjectApplyResult();
            projectApplyResult.convert(projectApplyEntity);
            return projectApplyResult;
        }
        return null;
    }

    @Override
    public List<CustomerFollowResult> followList(String projectId) {
        List<CustomerFollowEntity> list = customerFollowDao.getListByProjectId(projectId);
        List<CustomerFollowResult> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            result.addAll(list.stream().map(
                    entity -> {
                        CustomerFollowResult customerFollowReulst = new CustomerFollowResult();
                        customerFollowReulst.setCreateTime(entity.getCreateTime());
                        customerFollowReulst.setFollowInfo(entity.getFollowInfo());
                        return customerFollowReulst;
                    }).collect(Collectors.toList())
            );
        }
        return result;
    }

    @Override
    public void addFollow(ProjectFollowCommand projectFollowCommand) {
        projectFollowCommand.convert(
                new CustomerFollowEntity(),
                entity -> {
                    projectFollowCommand.begin(entity, new SessionInfo());
                    logger.info("entity: " + entity.toString());
                },
                customerFollowDao::persist
        );
    }

    @Override
    public void create(ProjectApplyCommand projectApplyCommand) {
        ProjectApplyEntity projectApplyEntity = new ProjectApplyEntity();
        projectApplyCommand.convert(
                projectApplyEntity,
                entity -> {
                    projectApplyCommand.begin(entity, new SessionInfo());
                    logger.info("entity:" + entity.toString());
                },
                projectApplyDao::persist
        );

        if (StringUtils.isNotBlank(projectApplyCommand.getFollowInfo())) {
            CustomerFollowEntity customerFollowEntity = new CustomerFollowEntity();
            customerFollowEntity.setProjectId(projectApplyEntity.getId());
            customerFollowEntity.setFollowInfo(projectApplyCommand.getFollowInfo());
            customerFollowEntity.setId(Utils.makeUUID());
            customerFollowEntity.setCreateTime(System.currentTimeMillis());
            customerFollowEntity.setCreateUser(sysUserService.getCurrentSysUser().getId());
            customerFollowDao.create(customerFollowEntity);
        }
    }

    @Override
    public void update(ProjectApplyUpdateCommand projectApplyUpdateCommand) {
        ProjectApplyEntity projectApplyEntity = projectApplyDao.findByIdNotDelete(projectApplyUpdateCommand.getId());

        projectApplyUpdateCommand.convert(
                projectApplyEntity,
                entity -> {
                    projectApplyUpdateCommand.begin(entity, new SessionInfo());
                },
                projectApplyDao::update
        );
    }

    @Override
    public void toCustomer(ProjectApplyCustomerCommand projectApplyCustomerCommand) {
        if (projectApplyCustomerCommand.getContractDateEnd() <= projectApplyCustomerCommand.getContractDateStart()) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CUSTOMER_CONTRACT_DATE_ERROR, "合同结束日期不能小于开始日期");
        }
        ProjectApplyEntity projectApplyEntity = projectApplyDao.findByIdNotDelete(projectApplyCustomerCommand.getProjectId());
        if (null == projectApplyEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_PROJECT_APPLY_NULL, "选择的立项申请不能为空");
        }
        StringBuffer logInfo = new StringBuffer();
        try {
            logger.info("立项申请Entity --> :" + projectApplyEntity.toString());
            CustomerEntity customerEntity = new CustomerEntity();
            SysUtils.copyProperties(customerEntity, projectApplyEntity);

            projectApplyCustomerCommand.begin(customerEntity, new SessionInfo());
            logger.info("客户Entity --> :" + customerEntity.toString());
            customerDao.persist(customerEntity);

            logger.info("处理客户跟踪记录，增加转成正式客户后的ID标记");
            List<CustomerFollowEntity> followList = customerFollowDao.getListByProjectId(projectApplyEntity.getId());
            followList.stream().forEach(entity -> {
                entity.setCustomerId(customerEntity.getId());
            });
            customerFollowDao.update(followList);
            projectApplyEntity.setIsCustomer(Constants.TRUE);
            customerDao.update(customerEntity);
            logInfo.append("客户：" + customerEntity.getCustomerName() + "转为正式客户，合同期限为"
                    + DateTimeUtils.format(new DateTime(projectApplyCustomerCommand.getContractDateStart()), DateTimeUtils.YYYYMMDDPattern) + " 至 "
                    + DateTimeUtils.format(new DateTime(projectApplyCustomerCommand.getContractDateEnd()), DateTimeUtils.YYYYMMDDPattern));
            salaryLogService.successLog(OperateConstants.SALARY_PROJECT_APPLY_TO_CUSTOMER, logInfo, logger);
        } catch (Exception e) {
            logInfo.append("立项申请Id：" + projectApplyCustomerCommand.getProjectId() + "转正式客户失败");
            salaryLogService.successLog(OperateConstants.SALARY_PROJECT_APPLY_TO_CUSTOMER, logInfo, logger);
        }
    }
}
