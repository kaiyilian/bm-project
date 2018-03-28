package com.bumu.arya.admin.salary.model.entity;

import com.bumu.arya.model.entity.BaseTxVersionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
@Entity
@Table(name = "ARYA_SALARY_RULE")
public class SalaryRuleEntity extends BaseTxVersionEntity {
	public static final String TABLE_NAME_ARYA_SALARY_RULE = "ARYA_SALARY_RULE";

	/**
	 * 主键ID
	 */
	public static final String COL_NAME_ID = "ID";

	/**
	 * 规则名称
	 */
	public static final String COL_NAME_RULE_NAME = "NAME";

	/**
	 * 规则详情
	 */
	public static final String COL_NAME_RULE_DEF = "RULE_DEF";

	/**
	 * 是否删除
	 */
	public static final String COL_NAME_IS_DELETE = "IS_DELETE";

	/**
	 * 主键ID
	 */
	@Id()
	@Column(name = COL_NAME_ID, columnDefinition = "char(32)")
	private String id;

	@Column(name = COL_NAME_RULE_NAME , length = 128)
	private String name;

	@Column(name = COL_NAME_RULE_DEF, length = 512)
	private String ruleDef;

	@Column(name = COL_NAME_IS_DELETE)
	private int isDelete;

	public static String getTableNameAryaSalaryRule() {
		return TABLE_NAME_ARYA_SALARY_RULE;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRuleDef() {
		return ruleDef;
	}

	public void setRuleDef(String ruleDef) {
		this.ruleDef = ruleDef;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
}
