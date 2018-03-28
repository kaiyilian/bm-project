package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.common.Constants;
import com.bumu.arya.salary.dao.CustomerContractDao;
import com.bumu.arya.salary.model.entity.CustomerContractEntity;
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
public class CustomerContractDaoImpl extends SalaryBaseDaoImpl<CustomerContractEntity> implements CustomerContractDao{

    @Override
    public List<CustomerContractEntity> findListByCustomerId(String customerId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId));
        criteria.addOrder(Order.asc("createTime"));
        return criteria.list();
    }

    @Override
    public CustomerContractEntity findOne(String customerId, String id) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId));
        criteria.add(Restrictions.eq("id", id));
        return (CustomerContractEntity) criteria.uniqueResult();
    }
}
