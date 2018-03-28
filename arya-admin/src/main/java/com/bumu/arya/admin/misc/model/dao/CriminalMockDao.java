package com.bumu.arya.admin.misc.model.dao;

import com.bumu.arya.admin.misc.controller.command.CriminalCommand;
import com.bumu.arya.admin.misc.model.entity.CriminalMockEntity;
import com.bumu.common.model.dao.HbQueryDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author majun
 * @date 2017/3/9
 */
@Transactional
public interface CriminalMockDao extends HbQueryDao<CriminalMockEntity> {

	CriminalMockEntity findByParam(CriminalCommand param);
}
