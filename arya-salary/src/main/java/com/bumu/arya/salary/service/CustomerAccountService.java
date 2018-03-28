package com.bumu.arya.salary.service;

import com.bumu.arya.salary.command.CustomerAccountUpdateCommand;
import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.arya.salary.result.CustomerAccountResult;
import com.bumu.arya.salary.result.CustomerAccountTotalQueryResult;
import com.bumu.arya.salary.result.CustomerAccountTotalResult;
import com.bumu.common.result.Pager;
import com.bumu.common.result.PagerResult;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/10
 */
@Transactional
public interface CustomerAccountService {

    void create(CustomerAccountEntity createCustomerAccountEntity, List<CustomerAccountEntity> deleteCustomerAccountEntity);

    PagerResult<CustomerAccountResult> pageAccount(String customerId, String yearMonth, Integer page, Integer pageSize) throws Exception;

    void updateAccount(CustomerAccountUpdateCommand customerAccountUpdateCommand);

    List<CustomerAccountEntity> salaryAccountList(String customerId, Long week);

    CustomerAccountTotalResult customerAccountTotalList(Long startTime, Long endTime, Integer page, Integer pageSize);
}
