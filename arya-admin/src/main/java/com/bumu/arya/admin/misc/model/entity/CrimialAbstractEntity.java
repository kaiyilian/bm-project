package com.bumu.arya.admin.misc.model.entity;

import com.bumu.bran.model.entity.BaseBranEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author majun
 * @date 2017/3/13
 */
@MappedSuperclass
public abstract class CrimialAbstractEntity extends BaseBranEntity {

	@Id()
	@Column(name = "ID", columnDefinition = "char(32)")
	private String id;
	@Column(name = "ID_CARD_NO", length = 20)
	private String idCardNo;
	@Column(name = "NAME", length = 20)
	private String name;
	@Column(name = "CRIMINAL_DETAIL")
	private String criminalDetail;
	@Column(name = "QUERY_STATUS", columnDefinition = "int default 0 COMMENT '查询状态' ")
	private int queryStatus;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getCriminalDetail() {
		return criminalDetail;
	}

	public void setCriminalDetail(String criminalDetail) {
		this.criminalDetail = criminalDetail;
	}

	public int getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(int queryStatus) {
		this.queryStatus = queryStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
