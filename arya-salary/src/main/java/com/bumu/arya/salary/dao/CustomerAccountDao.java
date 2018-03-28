package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface CustomerAccountDao extends SalaryBaseDao<CustomerAccountEntity> {

    List<CustomerAccountEntity> getList(String customerId);

    Pager<CustomerAccountEntity> getPager(String customerId, String yearMonth, Integer page, Integer pageSize) throws Exception;

    List<CustomerAccountEntity> salaryAccountList(String customerId, Long week);

    List<CustomerAccountEntity> getList(String customerId, String yearMonth) throws Exception;
}
