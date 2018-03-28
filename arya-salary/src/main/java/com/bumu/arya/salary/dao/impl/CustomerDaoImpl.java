package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.salary.dao.CustomerDao;
import com.bumu.arya.salary.model.entity.CustomerEntity;
import com.bumu.bran.common.Constants;
import com.bumu.common.result.Pager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Repository
public class CustomerDaoImpl extends SalaryBaseDaoImpl<CustomerEntity> implements CustomerDao {

    @Override
    public List<CustomerEntity> getList(String condition) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(condition)) {
            criteria.add(
                    Restrictions.or(
                            Restrictions.like("customerName", condition.trim(), MatchMode.ANYWHERE),
                            Restrictions.like("shortName", condition.trim(), MatchMode.ANYWHERE)));

        }
        criteria.addOrder(Order.asc("customerName"));
        return criteriaToList(criteria);
    }

    @Override
    public Pager<CustomerEntity> getPage(String condition, Integer page, Integer pageSize) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(condition)) {
            criteria.add(Restrictions.like("customerName", condition, MatchMode.ANYWHERE));
        }
        if (page > 0) {
            page -= 1;
        }
        criteria.addOrder(Order.desc("createTime"));
        return getPagerByCriteria(criteria, page, pageSize);
    }
}
