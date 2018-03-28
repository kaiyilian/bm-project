package com.bumu.arya.salary.dao.impl;

import com.bumu.arya.salary.dao.SalaryBaseDao;
import com.bumu.arya.salary.model.entity.BaseSalaryEntity;
import com.bumu.bran.common.Constants;
import com.bumu.common.SessionInfo;
import com.bumu.common.model.dao.impl.HbQueryImpl;
import com.bumu.exception.Assert;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author majun
 * @date 2016/11/29
 */
public class SalaryBaseDaoImpl<E extends BaseSalaryEntity> extends HbQueryImpl<E> implements SalaryBaseDao<E> {

    @Override
    public List<E> findAllNotDelete() {
        queryMap.clear();
        queryMap.put("isDelete", Constants.FALSE);
        return findReturnCriteria(queryMap).list();
    }

    @Override
    public E findByIdNotDelete(String id) {
        return (E) findReturnCriteriaByIdNotDelete(id).uniqueResult();
    }

    @Override
    public Criteria findReturnCriteriaByIdNotDelete(String id) {
        Session currentSession = super.getSessionFactory().getCurrentSession();
        Criteria criteria = currentSession.createCriteria(entityClass);
        if (StringUtils.isNotBlank(id)) {
            criteria.add(Restrictions.eq("id", id));
        }
        criteria.add(Restrictions.eq("isDelete", Constants.FALSE));

        return criteria;
    }

    @Override
    public void mockDelete(String id, SessionInfo sessionInfo) {
        queryMap.clear();
        queryMap.put("id", id);
        queryMap.put("isDelete", Constants.FALSE);
        E e = findUseCriteriaUnique(queryMap);
        Assert.notNull(e, "ID错误,数据库没有找到相关对象");
        e.setUpdateUser(sessionInfo.getUserId());
        e.setUpdateTime(System.currentTimeMillis());
        e.setIsDelete(Constants.TRUE);
        update(e);
    }


}
