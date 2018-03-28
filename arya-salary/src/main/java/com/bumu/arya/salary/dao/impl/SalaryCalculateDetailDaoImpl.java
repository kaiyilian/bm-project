package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.common.Constants;
import com.bumu.arya.salary.dao.SalaryCalculateDetailDao;
import com.bumu.arya.salary.model.entity.SalaryCalculateDetailEntity;
import com.bumu.arya.salary.model.entity.SalaryUserEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Repository
public class SalaryCalculateDetailDaoImpl extends SalaryBaseDaoImpl<SalaryCalculateDetailEntity> implements SalaryCalculateDetailDao {
    @Override
    public List<SalaryCalculateDetailEntity> getList(String userId, Integer year, Integer month, Long week, String customerId, Integer settlementInterval) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("customerId", customerId))
                .add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .addOrder(Order.asc("week"));
        if (settlementInterval == 1) {
            //如果是月份导入，则过滤本月中的月份数据
            criteria.add(Restrictions.ne("settlementInterval", 1));
        } else {
            //如果是批次，则需要比本次之前的数据
            criteria.add(Restrictions.lt("week", week));
        }
        return criteria.list();
    }

    @Override
    public List<SalaryCalculateDetailEntity> findListByWeek(Long week, String customerId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("week", week))
                .add(Restrictions.eq("customerId", customerId));
        return criteria.list();
    }

    @Override
    public List<SalaryCalculateDetailEntity> weekDatas(Integer year, Integer month, String customerId, String userId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .add(Restrictions.eq("customerId", customerId))
                .add(Restrictions.eq("userId", userId))
                .addOrder(Order.asc("week"));
        return criteria.list();
    }

    @Override
    public List<SalaryCalculateDetailEntity> findByIds(List<String> ids) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.in("id", ids));
        return criteria.list();
    }

    @Override
    public List<SalaryCalculateDetailEntity> weeksDatas(List<SalaryUserEntity> salaryUserEntityList, String condition, List<Long> weeks, Integer year, Integer month, String customerId) throws Exception {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.in("week", weeks))
                .add(Restrictions.eq("year", year))
                .add(Restrictions.eq("month", month))
                .add(Restrictions.eq("customerId", customerId))
                .addOrder(Order.asc("week"));
        if (StringUtils.isNotBlank(condition)) {
            List<String> userIds = new ArrayList<>();
            for (SalaryUserEntity salaryUserEntity : salaryUserEntityList) {
                userIds.add(salaryUserEntity.getId());
            }
            if (CollectionUtils.isEmpty(userIds)) {
                userIds.add("-1");
            }
            criteria.add(Restrictions.in("userId", userIds));
        }
        return criteria.list();
    }

    @Override
    public Boolean checkWeekIsEmpty(Long week) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("week", week));
        List<SalaryCalculateDetailEntity> detailList = criteria.list();
        return CollectionUtils.isEmpty(detailList);
    }
}
