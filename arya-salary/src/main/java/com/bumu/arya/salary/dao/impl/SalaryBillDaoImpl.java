package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.common.Constants;
import com.bumu.arya.salary.dao.SalaryBillDao;
import com.bumu.arya.salary.model.entity.SalaryBillEntity;
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
public class SalaryBillDaoImpl extends SalaryBaseDaoImpl<SalaryBillEntity> implements SalaryBillDao{


    @Override
    public List<SalaryBillEntity> getList(String condition) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(condition)) {
            criteria.add(Restrictions.or(
                    Restrictions.like("customerName", condition.trim(), MatchMode.ANYWHERE),
                    Restrictions.like("totalMoney", condition.trim(), MatchMode.ANYWHERE)
            ));
        }
        return criteria.list();
    }

    @Override
    public Pager<SalaryBillEntity> getPager(String condition, Integer page, Integer pageSize) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(condition)) {
            criteria.add(Restrictions.or(
                    Restrictions.like("customerName", condition.trim(), MatchMode.ANYWHERE),
                    Restrictions.like("totalMoney", condition.trim(), MatchMode.ANYWHERE)
            ));
        }
        criteria.addOrder(Order.desc("createTime"));
        if (page > 0) {
            page -= 1;
        }
        return getPagerByCriteria(criteria, page, pageSize);
    }

    @Override
    public List<SalaryBillEntity> findList(List<String> ids) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.in("id", ids));
        return criteria.list();
    }
}
