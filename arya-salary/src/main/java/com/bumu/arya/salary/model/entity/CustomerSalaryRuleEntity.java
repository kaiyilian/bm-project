package com.bumu.arya.salary.model.entity;

import com.bumu.arya.salary.common.SalaryEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author CuiMengxin
 * @date 2016/7/8
 */
@Entity
@Table(name = "SALARY_CUSTOMER_RULE")
public class CustomerSalaryRuleEntity extends BaseSalaryEntity {

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
	 * 客户ID
	 */
	public static final String COL_NAME_CUSTOMER_ID = "CUSTOMER_ID";

	/**
	 * 规则分类
	 */
	public static final String COL_NAME_RULE_TYPE = "RULE_TYPE";

    /**
     * 费用承担方
     */
    public static final String COL_NAME_COST_BEARING = "COST_BEARING";

	/**
	 * 主键ID
	 */
	@Id()
	@Column(name = COL_NAME_ID, columnDefinition = "char(32)")
	String id;

	@Column(name = COL_NAME_RULE_NAME , length = 128)
	String name;

	@Column(name = COL_NAME_RULE_TYPE )
	SalaryEnum.RuleType ruleType;

	@Column(name = COL_NAME_RULE_DEF, length = 2000)
	String ruleDef;

	@Column(name = COL_NAME_CUSTOMER_ID, columnDefinition = "char(32)")
	private String customerId;

    @Column(name = COL_NAME_COST_BEARING)
    private SalaryEnum.CostBearing costBearing;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

    public SalaryEnum.CostBearing getCostBearing() {
        return costBearing;
    }

    public void setCostBearing(SalaryEnum.CostBearing costBearing) {
        this.costBearing = costBearing;
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

	public SalaryEnum.RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(SalaryEnum.RuleType ruleType) {
		this.ruleType = ruleType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
