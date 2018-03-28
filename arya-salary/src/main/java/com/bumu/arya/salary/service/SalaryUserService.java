package com.bumu.arya.salary.service;

import com.bumu.arya.salary.model.entity.SalaryUserEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Transactional
public interface SalaryUserService {

    List<SalaryUserEntity> getUserList(List<String> userIds, String customerId);

    Map<String, SalaryUserEntity> userListToMap(List<SalaryUserEntity> list);

    void saveUser(SalaryUserEntity salaryUserEntity);

    Map<String, SalaryUserEntity> customerUserMap(String customerId);
}
