package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.SalaryCalculateDetailEntity;
import com.bumu.arya.salary.model.entity.SalaryUserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 */
@Transactional
public interface SalaryCalculateDetailDao extends SalaryBaseDao<SalaryCalculateDetailEntity> {

    List<SalaryCalculateDetailEntity> getList(String userId, Integer year, Integer month, Long week, String customerId, Integer settlementInterval);

    List<SalaryCalculateDetailEntity> findListByWeek(Long week, String customerId);

    List<SalaryCalculateDetailEntity> weekDatas(Integer year, Integer month, String customerId, String userId);

    List<SalaryCalculateDetailEntity> findByIds(List<String> ids);

    List<SalaryCalculateDetailEntity> weeksDatas(List<SalaryUserEntity> salaryUserEntityList, String condition, List<Long> weeks, Integer year, Integer month, String customerId) throws Exception;

    Boolean checkWeekIsEmpty(Long week);
}
