package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.common.Constants;
import com.bumu.arya.salary.dao.SalaryCalculateDao;
import com.bumu.arya.salary.model.entity.SalaryCalculateEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.analysis.function.Add;
import org.apache.ibatis.annotations.Param;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author majun
 * @date 2016/12/19
 */
@Repository
public class SalaryCalculateDaoImpl extends SalaryBaseDaoImpl<SalaryCalculateEntity> implements SalaryCalculateDao{

    @Override
    public SalaryCalculateEntity getOne(String customerId, Integer year, Integer month, Long week) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId))
                .add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month));
        if (null != week && week > 0) {
           criteria.add(Restrictions.eq("week", week));
        }
        return super.getOne(criteria);
    }

    @Override
    public SalaryCalculateEntity getMonthOne(String customerId, Integer year, Integer month) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId))
                .add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .add(Restrictions.eq("settlementInterval", 1));
        return super.getOne(criteria);
    }

    @Override
    public List<SalaryCalculateEntity> check(String customerId, Integer year, Integer month, Integer settlementInterval) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId))
                .add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .add(Restrictions.eq("settlementInterval", settlementInterval));
        return criteria.list();
    }

    @Override
    public List<SalaryCalculateEntity> getMonthList(String customerId, Integer year, Integer month, Long week) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId))
                .add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .addOrder(Order.asc("week"));
        if (null != week && week > 0) {
            criteria.add(Restrictions.eq("week", week));
        }
        return criteria.list();
    }

    @Override
    public List<SalaryCalculateEntity> getListByWeeks(List<Long> weeks) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.in("week", weeks));
        return criteria.list();
    }

    @Override
    public void deleteByWeek(Long week) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("week", week));
        SalaryCalculateEntity salaryCalculateEntity = (SalaryCalculateEntity) criteria.uniqueResult();
        if (null != salaryCalculateEntity) {
            salaryCalculateEntity.setIsDelete(Constants.TRUE);
            update(salaryCalculateEntity);
        }
    }

    @Override
    public SalaryCalculateEntity getByWeek(String customerId, Integer year, Integer month, Long week) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId))
                .add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .add(Restrictions.eq("week", week))
                .addOrder(Order.asc("week"));
        return super.criteriaToUnique(criteria);
    }

    @Override
    public List<SalaryCalculateEntity> findDeductList() {
        queryMap.clear();
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("isDeduct", Constants.TRUE));
        return criteria.list();
    }
}
