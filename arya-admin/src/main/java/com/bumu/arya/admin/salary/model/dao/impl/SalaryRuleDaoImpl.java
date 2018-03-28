package com.bumu.arya.admin.salary.model.dao.impl;

import com.bumu.arya.admin.salary.model.dao.SalaryRuleDao;
import com.bumu.arya.admin.salary.model.entity.SalaryRuleEntity;
import com.bumu.bran.common.Constants;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.swiftdao.impl.HibernateKeyedCrudDaoImpl;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
@Repository
public class SalaryRuleDaoImpl extends HibernateKeyedCrudDaoImpl<SalaryRuleEntity> implements SalaryRuleDao {
	@Override
	public SalaryRuleEntity findRuleById(String ruleId) {
		Session currentSession = super.getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(SalaryRuleEntity.class)
				.add(Restrictions.eq("id", ruleId))
				.add(Restrictions.eq("isDelete", Constants.FALSE));
		List<SalaryRuleEntity> ruleEntities = criteria.list();
		if (ruleEntities.size() > 0) {
			return ruleEntities.get(0);
		}
		return null;
	}
}
