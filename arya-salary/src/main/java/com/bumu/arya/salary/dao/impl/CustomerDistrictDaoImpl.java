package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.salary.dao.CustomerDistrictDao;
import com.bumu.arya.salary.model.entity.CustomerDistrictEntity;
import com.bumu.bran.common.Constants;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/13
 */
@Repository
public class CustomerDistrictDaoImpl extends SalaryBaseDaoImpl<CustomerDistrictEntity> implements CustomerDistrictDao {

    @Override
    public List<CustomerDistrictEntity> getListByCustomerId(String customerId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId));
        return criteriaToList(criteria);
    }
}
