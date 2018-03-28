package com.bumu.arya.salary.dao;

import com.bumu.arya.salary.model.entity.BaseSalaryEntity;
import com.bumu.common.SessionInfo;
import com.bumu.common.model.dao.HbQueryDao;
import org.hibernate.Criteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2016/11/29
 */
@Transactional
public interface SalaryBaseDao<E extends BaseSalaryEntity> extends HbQueryDao<E> {

	List<E> findAllNotDelete();

	E findByIdNotDelete(String id);

	Criteria findReturnCriteriaByIdNotDelete(String id);

	void mockDelete(String id, SessionInfo sessionInfo);
}
