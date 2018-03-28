package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.SalaryCalculateEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * Created by liangjun on 2017/6/7
 */
@Transactional
public interface SalaryCalculateDao extends SalaryBaseDao<SalaryCalculateEntity> {

    SalaryCalculateEntity getOne(String customerId, Integer year, Integer month, Long week);

    SalaryCalculateEntity getMonthOne(String customerId, Integer year, Integer month);

    List<SalaryCalculateEntity> check(String customerId, Integer year, Integer month, Integer settlementInterval);

    List<SalaryCalculateEntity> getMonthList(String customerId, Integer year, Integer month, Long week);

    List<SalaryCalculateEntity> getListByWeeks(List<Long> weeks);

    void deleteByWeek(Long week);

    SalaryCalculateEntity getByWeek(String customerId, Integer year, Integer month, Long week);

    List<SalaryCalculateEntity> findDeductList();

}
