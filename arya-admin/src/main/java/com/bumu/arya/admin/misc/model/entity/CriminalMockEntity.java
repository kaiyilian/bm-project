package com.bumu.arya.admin.misc.model.entity;

import com.bumu.arya.Utils;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author majun
 * @date 2017/3/9
 */
@Entity
@Table(name = "ARYA_MOCK_CRIMINAL")
public class CriminalMockEntity extends CrimialAbstractEntity {

	public void toCreateEntity(CriminalEntity entity, String userId) throws Exception {
		BeanUtils.copyProperties(entity, this);
		entity.setId(Utils.makeUUID());
		entity.setCreateUser(userId);
		entity.setCreateTime(System.currentTimeMillis());
	}

	public void toUpdateEntity(CriminalEntity entity, String userId) {
		entity.setUpdateUser(userId);
		entity.setUpdateTime(System.currentTimeMillis());
		entity.setQueryStatus(getQueryStatus());
		entity.setCriminalDetail(getCriminalDetail());
	}

}
