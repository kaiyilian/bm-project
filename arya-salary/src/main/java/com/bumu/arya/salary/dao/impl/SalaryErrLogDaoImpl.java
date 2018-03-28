package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.common.Constants;
import com.bumu.arya.salary.dao.SalaryErrLogDao;
import com.bumu.arya.salary.model.entity.SalaryErrLogEntity;
import com.bumu.common.result.Pager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Repository
public class SalaryErrLogDaoImpl extends SalaryBaseDaoImpl<SalaryErrLogEntity> implements SalaryErrLogDao {

    @Override
    public Pager<SalaryErrLogEntity> getPager(String condition, Integer pageSize, Integer page) throws Exception{
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(condition)) {
            //condition = new String(condition.getBytes("iso8859-1"),"UTF-8");
            criteria.add(
                    Restrictions.or(
                            Restrictions.like("customerName", condition, MatchMode.ANYWHERE),
                            Restrictions.like("districtName", condition, MatchMode.ANYWHERE)));
        }
        if (page > 0) {
            page -= 1;
        }
        return super.getPagerByCriteria(criteria, page, pageSize);
    }

    @Override
    public List<SalaryErrLogEntity> getList(List<String> idList) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        criteria.add(Restrictions.in("id", idList));
        return criteria.list();
    }

    @Override
    public List<SalaryErrLogEntity> getList(String condition) {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(condition)) {
            criteria.add(Restrictions.like("customerName", condition, MatchMode.ANYWHERE));
            criteria.add(Restrictions.like("districtName", condition, MatchMode.ANYWHERE));
        }
        return criteria.list();
    }
}
