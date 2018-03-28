package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.CustomerEntity;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface CustomerDao extends SalaryBaseDao<CustomerEntity> {

    List<CustomerEntity> getList(String condition);

    Pager<CustomerEntity> getPage(String condition, Integer page, Integer pageSize);

}
