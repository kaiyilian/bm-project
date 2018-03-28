package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.salary.dao.ProjectApplyDao;
import com.bumu.arya.salary.model.entity.ProjectApplyEntity;
import com.bumu.bran.common.Constants;
import com.bumu.common.result.Pager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author majun
 * @date 2016/12/19
 */
@Repository
public class ProjectApplyDaoImpl extends SalaryBaseDaoImpl<ProjectApplyEntity> implements ProjectApplyDao{

    @Override
    public Pager<ProjectApplyEntity> getPage(String condition, Integer page, Integer pageSize) throws Exception {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        queryMap.put("isCustomer", Constants.FALSE);
        Criteria criteria = super.findReturnCriteria(queryMap);
        if (StringUtils.isNotBlank(condition)) {
            //condition = new String(condition.getBytes("iso8859-1"),"UTF-8");
            criteria.add(
                    Restrictions.or(
                            Restrictions.like("salesMan", condition.trim(), MatchMode.ANYWHERE),
                            Restrictions.like("salesDepartment", condition.trim(), MatchMode.ANYWHERE)));
        }
        if (page > 0) {
            page -= 1;
        }
        return getPagerByCriteria(criteria, page, pageSize);
    }
}
