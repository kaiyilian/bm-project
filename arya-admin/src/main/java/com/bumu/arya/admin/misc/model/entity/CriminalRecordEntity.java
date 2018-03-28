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
@Table(name = "ARYA_CRIMINAL_RECORD")
public class CriminalRecordEntity extends CrimialAbstractEntity {

	public static CriminalRecordEntity createByCriminal(CriminalEntity criminalEntity, String curUser) throws Exception {
		CriminalRecordEntity criminalRecord = new CriminalRecordEntity();
		BeanUtils.copyProperties(criminalRecord, criminalEntity);
		criminalRecord.setId(Utils.makeUUID());
		criminalRecord.setCreateTime(System.currentTimeMillis());
		criminalRecord.setCreateUser(curUser);
		criminalRecord.setTxVersion(0L);
		return criminalRecord;
	}
}
