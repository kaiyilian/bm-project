package com.bumu.arya.salary.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.common.Constants;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.dao.CustomerContractDao;
import com.bumu.arya.salary.dao.CustomerDao;
import com.bumu.arya.salary.dao.CustomerFollowDao;
import com.bumu.arya.salary.dao.CustomerSalaryRuleDao;
import com.bumu.arya.salary.model.SalaryCalculateRuleModel;
import com.bumu.arya.salary.model.entity.*;
import com.bumu.arya.salary.result.ContractUploadResult;
import com.bumu.arya.salary.result.CustomerFollowResult;
import com.bumu.arya.salary.result.CustomerResult;
import com.bumu.arya.salary.result.CustomerSalaryRuleResult;
import com.bumu.arya.salary.service.CustomerAccountService;
import com.bumu.arya.salary.service.CustomerService;
import com.bumu.arya.salary.service.SalaryCalculateService;
import com.bumu.arya.salary.service.SalaryLogService;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.BaseResult;
import com.bumu.common.result.Pager;
import com.bumu.common.util.ListUtils;
import com.bumu.exception.AryaServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.bumu.function.VoConverter.logger;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/7
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerFollowDao customerFollowDao;

    @Autowired
    private CustomerContractDao customerContractDao;

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private CustomerSalaryRuleDao customerSalaryRuleDao;

    @Autowired
    private SalaryCalculateService salaryCalculateService;

    @Autowired
    private SalaryLogService salaryLogService;

    @Override
    public List<CustomerResult> getList(String condition) {
        List<CustomerEntity> list = customerDao.getList(condition);
        List<CustomerResult> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            result.addAll(list.stream().map(
                    entity -> {
                        CustomerResult customerResult = new CustomerResult();
                        SysUtils.copyProperties(customerResult, entity);
                        return customerResult;
                    }).collect(Collectors.toList())
            );
        }
        return result;
    }

    @Override
    public CustomerResult view(String id) {
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(id);
        CustomerResult customerResult = new CustomerResult();
        if (null != customerEntity) {
            SysUtils.copyProperties(customerResult, customerEntity);
        }
        if (StringUtils.isNotBlank(customerEntity.getContractId())) {
            CustomerContractEntity customerContractEntity = customerContractDao.findByIdNotDelete(customerEntity.getContractId());
            if (null != customerContractEntity) {
                customerResult.setContractDir(customerContractEntity.getContractDir());
            }
        }
        return customerResult;
    }

    @Override
    public BaseResult.IDResult update(CustomerUpdateCommand customerUpdateCommand) {
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerUpdateCommand.getId());
        SysUtils.copyProperties(customerEntity, customerUpdateCommand);
        customerDao.update(customerEntity);
        BaseResult.IDResult idResult = new BaseResult.IDResult();
        idResult.setId(customerEntity.getId());
        return idResult;
    }

    @Override
    public List<CustomerFollowResult> followList(String customerId) {
        List<CustomerFollowEntity> list = customerFollowDao.getListByCustomerId(customerId);
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
    public List<ContractUploadResult> contractList(String customerId) {
        List<ContractUploadResult> result = new ArrayList<>();
        List<CustomerContractEntity> customerContractEntityList = customerContractDao.findListByCustomerId(customerId);
        for (CustomerContractEntity entity : customerContractEntityList) {
            ContractUploadResult contractUploadResult = new ContractUploadResult();
            contractUploadResult.setId(entity.getId());
            contractUploadResult.setDir(entity.getContractDir());
            result.add(contractUploadResult);
        }
        return result;
    }

    @Override
    public void addFollow(CustomerFollowCommand customerFollowCommand) {
        customerFollowCommand.convert(
                new CustomerFollowEntity(),
                entity -> {
                    customerFollowCommand.begin(entity, new SessionInfo());
                    logger.info("entity: " + entity.toString());
                },
                customerFollowDao::persist
        );
    }

    @Override
    public void uploadContract(String customerId, ContractUploadResult contractUploadResult) {
        if (null == contractUploadResult || StringUtils.isAnyBlank(contractUploadResult.getId())
                || StringUtils.isAnyBlank(contractUploadResult.getUrl())) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_UPLOAD_CONTRACT_ERROR);
        }
        logger.info("合同上传-->：" + contractUploadResult.toString());

        logger.info("保存合同记录");
        CustomerContractEntity customerContractEntity = new CustomerContractEntity();
        customerContractEntity.setId(contractUploadResult.getId());
        customerContractEntity.setIsDelete(Constants.FALSE);
        customerContractEntity.setCreateUser(new SessionInfo().getUserId());
        customerContractEntity.setCreateTime(System.currentTimeMillis());
        customerContractEntity.setFileUrl(contractUploadResult.getUrl());
        customerContractEntity.setContractDir(contractUploadResult.getDir());
        customerContractDao.persist(customerContractEntity);

        logger.info("更新客户的合同地址");
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);
        customerEntity.setContractId(contractUploadResult.getId());
        //customerEntity.setContractUrl(contractUploadResult.getUrl());
        customerDao.update(customerEntity);
    }

    @Override
    public void uploadContracts(String customerId, List<ContractUploadResult> contractUploadResults) {
        if (CollectionUtils.isEmpty(contractUploadResults)) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_UPLOAD_CONTRACT_ERROR, "合同上传失败");
        }
        for (ContractUploadResult contractUploadResult : contractUploadResults) {
            logger.info("合同上传-->：" + contractUploadResult.toString());

            logger.info("保存合同记录");
            CustomerContractEntity customerContractEntity = new CustomerContractEntity();
            customerContractEntity.setId(contractUploadResult.getId());
            customerContractEntity.setCustomerId(customerId);
            customerContractEntity.setIsDelete(Constants.FALSE);
            customerContractEntity.setCreateUser(new SessionInfo().getUserId());
            customerContractEntity.setCreateTime(System.currentTimeMillis());
            customerContractEntity.setFileUrl(contractUploadResult.getUrl());
            customerContractEntity.setFileName(contractUploadResult.getName());
            customerContractEntity.setContractDir(contractUploadResult.getDir());
            customerContractDao.create(customerContractEntity);
        }
        //logger.info("更新客户的合同地址");
        //CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);
        //customerEntity.setContractId(contractUploadResult.getId());
        //customerEntity.setContractUrl(contractUploadResult.getUrl());
        //customerDao.update(customerEntity);
    }

    @Override
    public void deleteContract(String customerId, String contractId) {
        CustomerContractEntity customerContractEntity = customerContractDao.findOne(customerId, contractId);
        if (null == customerContractEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "客户不存在该合同");
        }
        customerContractEntity.setIsDelete(Constants.TRUE);
        customerContractDao.update(customerContractEntity);
    }

    @Override
    public void recharge(CustomerRechargeCommand customerRechargeCommand) {
        logger.info("增加充值台账记录");
        StringBuffer logInfo = new StringBuffer();
        try {
            CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity();
            CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerRechargeCommand.getCustomerId());
            customerRechargeCommand.begin(customerAccountEntity, new SessionInfo());
            customerAccountService.create(customerAccountEntity, null);
            logInfo.append("客户:" + customerEntity.getCustomerName() +
                    ", 充值金额:" + customerRechargeCommand.getMoney() + "。");
            salaryLogService.successLog(OperateConstants.SALARY_CUSTOMER_RECHARGE, logInfo, log);
        } catch (Exception e) {
            logInfo.append("客户Id" + customerRechargeCommand.getCustomerId() +
                    "充值金额:" + customerRechargeCommand.getMoney() + "。充值失败");
            salaryLogService.failedLog(OperateConstants.SALARY_CUSTOMER_RECHARGE, logInfo, log);
        }
    }

    @Override
    public BaseResult.IDResult addRule(CustomerSalaryRuleCommand customerSalaryRuleCommand) {
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerSalaryRuleCommand.getCustomerId());
        if (null == customerEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CUSTOMER_IS_NULL, "客户不存在");
        }
        //判断当月是否有缴纳薪资记录
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        List<Long> salaryCalculateEntityList = salaryCalculateService.getWeeks(customerSalaryRuleCommand.getCustomerId(),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        if (CollectionUtils.isNotEmpty(salaryCalculateEntityList)) {
            throw  new AryaServiceException(ErrorCode.CODE_SALARY_RULE_TYPE_ERROR, "本月已存在薪资记录，不可修改");
        }


        if (StringUtils.isNotBlank(customerEntity.getRuleId())) {
            CustomerSalaryRuleEntity old = customerSalaryRuleDao.findByIdNotDelete(customerEntity.getRuleId());
            if (null != old && old.getRuleType() != customerSalaryRuleCommand.getRuleType()) {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_RULE_TYPE_ERROR, "不能修改规则类型");
            }
        }

        if (customerSalaryRuleCommand.getRuleType() == SalaryEnum.RuleType.defined) {
            //自定义计算规则--计税档不能为空
            if (CollectionUtils.isEmpty(customerSalaryRuleCommand.getTaxGears())) {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_TAX_GEAR_EMPTY);//计税档不能为空
            }
        }
        StringBuffer logInfo = new StringBuffer();
        BaseResult.IDResult result = new BaseResult.IDResult();
        try {
            //command转化成entity
            CustomerSalaryRuleEntity customerSalaryRuleEntity = new CustomerSalaryRuleEntity();
            //持久化
            customerSalaryRuleCommand.begin(customerSalaryRuleEntity, new SessionInfo());

            //更新用户的薪资规则属性
            customerSalaryRuleDao.create(customerSalaryRuleEntity);
            customerEntity.setRuleId(customerSalaryRuleEntity.getId());
            customerEntity.setRuleDef(customerSalaryRuleEntity.getRuleDef());
            customerDao.update(customerEntity);
            result.setId(customerSalaryRuleEntity.getId());

            logInfo.append("客户名称：" + customerEntity.getCustomerName() + "保存了" + customerSalaryRuleCommand.getRuleName() + "薪资规则");
            salaryLogService.successLog(OperateConstants.SALARY_CALCULATE_CREATE_RULE, logInfo, log);
        } catch (Exception e) {
            logInfo.append("客户名称：" + customerEntity.getCustomerName() + "保存薪资规则" + customerSalaryRuleCommand.getRuleName() + "失败");
            salaryLogService.successLog(OperateConstants.SALARY_CALCULATE_CREATE_RULE, logInfo, log);
        }

        return result;
    }

    @Override
    public CustomerSalaryRuleResult getRule(String customerId) {
        //查询客户实体
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);
        if (null == customerEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CUSTOMER_IS_NULL);
        }
        //客户实体获取规则对象
        if (StringUtils.isAnyBlank(customerEntity.getRuleId())) {
            return null;
        }
        CustomerSalaryRuleEntity customerSalaryRuleEntity = customerSalaryRuleDao.findByIdNotDelete(customerEntity.getRuleId());
        if (null == customerSalaryRuleEntity) {
            return null;
        }

        CustomerSalaryRuleResult customerSalaryRuleResult = new CustomerSalaryRuleResult();
        customerSalaryRuleResult.setId(customerSalaryRuleEntity.getId());
        customerSalaryRuleResult.setRuleName(customerSalaryRuleEntity.getName());
        customerSalaryRuleResult.setRuleType(customerSalaryRuleEntity.getRuleType());
        SalaryCalculateRuleModel ruleModel;
        ObjectMapper mapper = new ObjectMapper();
        try {
            ruleModel = mapper.readValue(customerSalaryRuleEntity.getRuleDef(), SalaryCalculateRuleModel.class);
        } catch (IOException e) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_JSON_READ_FAILED);
        }
        customerSalaryRuleResult.setBrokerageRate(ruleModel.getBrokerageRate());
        customerSalaryRuleResult.setThresholdTax(ruleModel.getThresholdTax());
        customerSalaryRuleResult.setBrokerage(ruleModel.getBrokerage());
        customerSalaryRuleResult.setAbsenceSubRatio(ruleModel.getAbsenceSubRatio());
        customerSalaryRuleResult.setAffairSubRatio(ruleModel.getAffairSubRatio());
        customerSalaryRuleResult.setIllSubRatio(ruleModel.getIllSubRatio());
        customerSalaryRuleResult.setNewLeaveAbsenceSubRatio(ruleModel.getNewLeaveAbsenceSubRatio());
        customerSalaryRuleResult.setThresholdTaxOrdinary(ruleModel.getThresholdTaxOrdinary());
        customerSalaryRuleResult.setIllSubRatioOrdinary(ruleModel.getIllSubRatioOrdinary());
        customerSalaryRuleResult.setAffairSubRatioOrdinary(ruleModel.getAffairSubRatioOrdinary());
        customerSalaryRuleResult.setBrokerageOrdinary(ruleModel.getBrokerageOrdinary());
        if (customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.defined) {
            customerSalaryRuleResult.setCostBearing(ruleModel.getCostBearing());
            if (ruleModel.getTaxGears() == null) {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_EXIST, "薪资计税档不能为空");
            }
            customerSalaryRuleResult.setTaxGears(ruleModel.getTaxGears().stream().map(entity -> {
                CustomerSalaryRuleResult.SalaryCalculateRuleTaxGearResult gearResult = new CustomerSalaryRuleResult.SalaryCalculateRuleTaxGearResult();
                gearResult.setTaxRate(entity.getTaxRate());
                gearResult.setGear(entity.getGear());
                return gearResult;
            }).collect(Collectors.toList()));
        }
        if (customerSalaryRuleEntity.getRuleType() == SalaryEnum.RuleType.humanPool) {
            if (CollectionUtils.isEmpty(ruleModel.getFulltimeBonuList())) {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_EXIST, "全勤奖金不能为空");
            }
            customerSalaryRuleResult.setFulltimeBonuList(ruleModel.getFulltimeBonuList().stream().map(entity -> {
                CustomerSalaryRuleResult.FulltimeBonu fulltimeBonu = new CustomerSalaryRuleResult.FulltimeBonu();
                fulltimeBonu.setBonu(entity.getBonu());
                fulltimeBonu.setLeval(entity.getLeval());
                return fulltimeBonu;
            }).collect(Collectors.toList()));
        }
        return customerSalaryRuleResult;
    }

    @Override
    public SalaryCalculateRuleModel getRuleModel(String customerId) {
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerId);
        if (null == customerEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CUSTOMER_IS_NULL, "客户不存在");
        }
        if (StringUtils.isAnyBlank(customerEntity.getRuleId())) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_DB_FAILED, "该客户没有设置规则");
        }
        CustomerSalaryRuleEntity customerSalaryRuleEntity = customerSalaryRuleDao.findByIdNotDelete(customerEntity.getRuleId());
        if (null == customerSalaryRuleEntity) {
            return null;
        }

        SalaryCalculateRuleModel ruleModel;
        ObjectMapper mapper = new ObjectMapper();
        try {
            ruleModel = mapper.readValue(customerSalaryRuleEntity.getRuleDef(), SalaryCalculateRuleModel.class);
        } catch (IOException e) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_JSON_READ_FAILED);
        }
        return ruleModel;
    }

    @Override
    public CustomerEntity getOne(String id) {
        return customerDao.findByIdNotDelete(id);
    }

    @Override
    public Pager<CustomerResult> customerPager(String condition, Integer page, Integer pageSize) {
        Pager<CustomerEntity> pager = customerDao.getPage(condition, page, pageSize);
        Pager<CustomerResult> result = new Pager<>();
        if (ListUtils.checkNullOrEmpty(pager.getResult())) {
            return result;
        }
        result.setPageSize(pageSize);
        result.setRowCount(pager.getRowCount());
        result.setResult(pager.getResult().stream().map(
                entity -> {
                    CustomerResult customerResult = new CustomerResult();
                    SysUtils.copyProperties(customerResult, entity);
                    return customerResult;
                }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void updateDetail(CustomerDetailUpdateCommand customerDetailUpdateCommand) {
        CustomerEntity customerEntity = customerDao.findByIdNotDelete(customerDetailUpdateCommand.getId());
        if (null == customerEntity) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CUSTOMER_IS_NULL, "客户不存在");
        }
        customerEntity.setRemark(customerDetailUpdateCommand.getRemark());
        customerDao.update(customerEntity);
    }
}
