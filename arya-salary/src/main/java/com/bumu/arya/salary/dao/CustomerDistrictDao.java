package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.CustomerDistrictEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface CustomerDistrictDao extends SalaryBaseDao<CustomerDistrictEntity> {

    List<CustomerDistrictEntity> getListByCustomerId(String customerId);
}
