package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.salary.dao.CustomerAccountDao;
import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.bran.common.Constants;
import com.bumu.common.result.Pager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Repository
public class CustomerAccountDaoImpl extends SalaryBaseDaoImpl<CustomerAccountEntity> implements CustomerAccountDao{

    @Override
    public List<CustomerAccountEntity> getList(String customerId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        queryMap.put("customerId", customerId);
        return super.criteriaToList(super.findReturnCriteria(queryMap));
    }

    @Override
    public Pager<CustomerAccountEntity> getPager(String customerId, String yearMonth, Integer page, Integer pageSize) throws Exception {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        queryMap.put("customerId", customerId);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(yearMonth)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.parseDate(yearMonth, "yyyy-MM"));
            Date startDate = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date endDate = calendar.getTime();
            criteria.add(Restrictions.ge("dealDate", startDate.getTime()));
            criteria.add(Restrictions.le("dealDate", endDate.getTime()));
        }
        criteria.addOrder(Order.desc("createTime"));
        if (page > 0) {
            page -= 1;
        }
        return super.getPagerByCriteria(criteria, page, pageSize);
    }

    public List<CustomerAccountEntity> getList(String customerId, String yearMonth) throws Exception{
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        queryMap.put("customerId", customerId);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(yearMonth)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.parseDate(yearMonth, "yyyy-MM"));
            Date startDate = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date endDate = calendar.getTime();
            criteria.add(Restrictions.ge("dealDate", startDate.getTime()));
            criteria.add(Restrictions.le("dealDate", endDate.getTime()));
        }
        criteria.addOrder(Order.desc("createTime"));
        return criteria.list();
    }

    @Override
    public List<CustomerAccountEntity> salaryAccountList(String customerId, Long week) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        queryMap.put("customerId", customerId);
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("week", week));
        criteria.addOrder(Order.desc("createTime"));
        return criteria.list();
    }

}
