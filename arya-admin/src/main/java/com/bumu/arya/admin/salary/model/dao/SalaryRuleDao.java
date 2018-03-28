package com.bumu.arya.admin.salary.model.dao;

import com.bumu.arya.admin.salary.model.entity.SalaryRuleEntity;
import org.springframework.transaction.annotation.Transactional;
import org.swiftdao.KeyedCrudDao;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
@Transactional
public interface SalaryRuleDao extends KeyedCrudDao<SalaryRuleEntity> {

	/**
	 * 根据规则Id查询计算规则
	 * @param ruleId
	 * @return
	 */
	SalaryRuleEntity findRuleById(String ruleId);
}
