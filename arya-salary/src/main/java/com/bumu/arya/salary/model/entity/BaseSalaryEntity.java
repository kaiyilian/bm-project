package com.bumu.arya.salary.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.swiftdao.entity.KeyedPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 *
 */
@MappedSuperclass
public abstract class BaseSalaryEntity implements KeyedPersistable<String> {

	public static final String TABLE_NAME_BASE_BRAN_ENTITY = "BASE_BRAN_ENTITY";

	/**
	 * 是否删除
	 */
	public static final String COL_NAME_IS_DELETE = "IS_DELETE";

	/**
	 * 创建人
	 */
	public static final String COL_NAME_CREATE_USER = "CREATE_USER";

	/**
	 * 创建时间
	 */
	public static final String COL_NAME_CREATE_TIME = "CREATE_TIME";

	/**
	 * 修改人
	 */
	public static final String COL_NAME_UPDATE_USER = "UPDATE_USER";

	/**
	 * 修改时间
	 */
	public static final String COL_NAME_UPDATE_TIME = "UPDATE_TIME";

	/**
	 * 事务版本字段
	 */
	public static final String COL_NAME_VERSION = "VERSION";

	/**
	 * 事务锁版本
	 */
	@Version
	@Column(name = COL_NAME_VERSION)
	@JsonProperty(value = "version")
	private Long txVersion = 0L;

	/**
	 * 是否删除
	 */
	@Column(name = COL_NAME_IS_DELETE, nullable = false)
	private int isDelete;

	/**
	 * 创建人
	 */
	@Column(name = COL_NAME_CREATE_USER, length = 32, columnDefinition = "char(32)")
	private String createUser;

	/**
	 * 创建时间
	 */
	@Column(name = COL_NAME_CREATE_TIME, nullable = false)
	private long createTime;

	/**
	 * 修改人
	 */
	@Column(name = COL_NAME_UPDATE_USER, length = 32, columnDefinition = "char(32)")
	private String updateUser;

	/**
	 * 修改时间
	 */
	@Column(name = COL_NAME_UPDATE_TIME)
	private long updateTime;

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getTxVersion() {
		return txVersion;
	}

	public void setTxVersion(Long version) {
		this.txVersion = version;
	}

	protected BaseSalaryEntity() {
		this.createTime = System.currentTimeMillis();
	}
}
