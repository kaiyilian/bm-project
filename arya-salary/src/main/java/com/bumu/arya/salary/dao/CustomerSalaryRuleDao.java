package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.CustomerSalaryRuleEntity;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface CustomerSalaryRuleDao extends SalaryBaseDao<CustomerSalaryRuleEntity> {

    CustomerSalaryRuleEntity getOneByCustomerId(String customerId);

}
