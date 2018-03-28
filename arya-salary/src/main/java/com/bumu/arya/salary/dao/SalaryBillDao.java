package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.SalaryBillEntity;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface SalaryBillDao extends SalaryBaseDao<SalaryBillEntity> {

    List<SalaryBillEntity> getList(String condition);

    Pager<SalaryBillEntity> getPager(String condition, Integer page, Integer pageSize);

    List<SalaryBillEntity> findList(List<String> ids);

}
