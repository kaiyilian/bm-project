package com.bumu.arya.salary.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.common.Constants;
import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.arya.salary.model.entity.CustomerDistrictEntity;
import com.bumu.arya.salary.service.CustomerDistrictService;
import org.springframework.stereotype.Service;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Service
public class CustomerDistrictServiceImpl implements CustomerDistrictService {

    @Override
    public CustomerDistrictEntity newCustomerDistrictEntity(String customerId, String customerDistrictName, DistrictEntity districtEntity) {
        CustomerDistrictEntity customerDistrictEntity = new CustomerDistrictEntity();
        customerDistrictEntity.setId(Utils.makeUUID());
        customerDistrictEntity.setDistrictId(districtEntity.getId());
        customerDistrictEntity.setDistrictName(districtEntity.getDistrictName());
        customerDistrictEntity.setCreateTime(System.currentTimeMillis());
        customerDistrictEntity.setIsDelete(Constants.FALSE);
        customerDistrictEntity.setCustomerId(customerId);
        customerDistrictEntity.setCustomerDistrictName(customerDistrictName);
        return customerDistrictEntity;
    }
}
