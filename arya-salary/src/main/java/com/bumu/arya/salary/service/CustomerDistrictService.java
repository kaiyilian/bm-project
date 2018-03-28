package com.bumu.arya.salary.service;

import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.arya.salary.model.entity.CustomerDistrictEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Transactional
public interface CustomerDistrictService {

    CustomerDistrictEntity newCustomerDistrictEntity(String customerId, String customerDistrictName, DistrictEntity districtEntity);
}
