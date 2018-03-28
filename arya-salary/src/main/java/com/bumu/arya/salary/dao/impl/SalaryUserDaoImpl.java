package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.common.Constants;
import com.bumu.arya.salary.dao.SalaryUserDao;
import com.bumu.arya.salary.model.entity.SalaryUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Repository
public class SalaryUserDaoImpl extends SalaryBaseDaoImpl<SalaryUserEntity> implements SalaryUserDao {

    @Override
    public List<SalaryUserEntity> getListByIdCard(List<String> idCardNos, String customerId) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.in("idCardNo", idCardNos));
        if (StringUtils.isNotBlank(customerId)) {
            criteria.add(Restrictions.eq("customerId", customerId));
        }
        return criteriaToList(criteria);
    }

    @Override
    public List<SalaryUserEntity> getListByCondition(String condition, String customerId) throws Exception {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = findReturnCriteria(queryMap);
        criteria.add(Restrictions.eq("customerId", customerId));
        if (StringUtils.isNotBlank(condition)) {
            //condition = new String(condition.getBytes("iso8859-1"),"UTF-8");
            criteria.add(
                    Restrictions.or(
                            Restrictions.like("name", condition, MatchMode.ANYWHERE),
                            Restrictions.like("idCardNo", condition, MatchMode.ANYWHERE)));
        }
        return criteriaToList(criteria);
    }
}
