package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.salary.dao.CustomerFollowDao;
import com.bumu.arya.salary.model.entity.CustomerFollowEntity;
import com.bumu.bran.common.Constants;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Repository
public class CustomerFollowDaoImpl extends SalaryBaseDaoImpl<CustomerFollowEntity> implements CustomerFollowDao{

    @Override
    public List<CustomerFollowEntity> getListByProjectId(String projectApplyId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("projectId", projectApplyId));
        criteria.addOrder(Order.asc("createTime"));
        return criteriaToList(criteria);
    }

    @Override
    public List<CustomerFollowEntity> getListByCustomerId(String customerId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId));
        criteria.addOrder(Order.asc("createTime"));
        return criteriaToList(criteria);
    }
}
