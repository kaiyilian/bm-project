package com.bumu.arya.admin.misc.model.dao.impl;

import com.bumu.arya.admin.misc.controller.command.CriminalCommand;
import com.bumu.arya.admin.misc.model.dao.CriminalDao;
import com.bumu.arya.admin.misc.model.entity.CrimialAbstractEntity;
import com.bumu.common.model.dao.impl.HbQueryImpl;

/**
 * @author majun
 * @date 2017/3/13
 */
public abstract class CriminalAbstractDaoImpl<E extends CrimialAbstractEntity> extends HbQueryImpl<E> implements CriminalDao<E> {

	public E findByParam(CriminalCommand param) {
		queryMap.clear();
		queryMap.put("name", param.getName());
		queryMap.put("idCardNo", param.getIdCardNo());
		queryMap.put("isDelete", 0);
		return findUseCriteriaUnique(queryMap);
	}
}
