package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.SalaryUserEntity;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Transactional
public interface SalaryUserDao extends SalaryBaseDao<SalaryUserEntity>  {

    List<SalaryUserEntity> getListByIdCard(List<String> idCardNos, String customerId);

    List<SalaryUserEntity> getListByCondition(String condition, String customerId) throws Exception;
}
