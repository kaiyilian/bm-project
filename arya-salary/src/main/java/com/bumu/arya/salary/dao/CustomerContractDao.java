package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.CustomerContractEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface CustomerContractDao extends SalaryBaseDao<CustomerContractEntity> {

    List<CustomerContractEntity> findListByCustomerId(String customerId);

    CustomerContractEntity findOne(String customerId, String id);

}
