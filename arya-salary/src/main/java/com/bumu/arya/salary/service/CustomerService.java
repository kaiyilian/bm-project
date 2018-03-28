package com.bumu.arya.salary.service;

import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.model.SalaryCalculateRuleModel;
import com.bumu.arya.salary.model.entity.CustomerEntity;
import com.bumu.arya.salary.result.ContractUploadResult;
import com.bumu.arya.salary.result.CustomerFollowResult;
import com.bumu.arya.salary.result.CustomerResult;
import com.bumu.arya.salary.result.CustomerSalaryRuleResult;
import com.bumu.common.result.BaseResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/7
 */
@Transactional
public interface CustomerService {

    List<CustomerResult> getList(String condition);

    CustomerResult view(String id);

    BaseResult.IDResult update(CustomerUpdateCommand customerUpdateCommand);

    List<CustomerFollowResult> followList(String customerId);

    void addFollow(CustomerFollowCommand customerFollowCommand);

    List<ContractUploadResult> contractList(String customerId);

    void uploadContract(String customerId, ContractUploadResult contractUploadResult);

    void uploadContracts(String customerId, List<ContractUploadResult> contractUploadResults);

    /**
     * 删除合同
     * @param contractId
     */
    void deleteContract(String customerId, String contractId);

    void recharge(CustomerRechargeCommand customerRechargeCommand);

    BaseResult.IDResult addRule(CustomerSalaryRuleCommand customerSalaryRuleCommand);

    CustomerSalaryRuleResult getRule(String customerId);

    CustomerEntity getOne(String id);

    SalaryCalculateRuleModel getRuleModel(String customerId);

    Pager<CustomerResult> customerPager(String condition, Integer page, Integer pageSize);

    void updateDetail(CustomerDetailUpdateCommand customerDetailUpdateCommand);
}
