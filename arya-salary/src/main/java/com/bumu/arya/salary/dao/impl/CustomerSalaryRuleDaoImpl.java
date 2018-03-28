package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.salary.dao.CustomerSalaryRuleDao;
import com.bumu.arya.salary.model.entity.CustomerSalaryRuleEntity;
import com.bumu.bran.common.Constants;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author majun
 * @date 2016/12/19
 */
@Repository
public class CustomerSalaryRuleDaoImpl extends SalaryBaseDaoImpl<CustomerSalaryRuleEntity> implements CustomerSalaryRuleDao{

    @Override
    public CustomerSalaryRuleEntity getOneByCustomerId(String customerId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria c = findReturnCriteria(queryMap);
        c.add(Restrictions.eq("customerId", customerId));
        return getOne(c);
    }
}
