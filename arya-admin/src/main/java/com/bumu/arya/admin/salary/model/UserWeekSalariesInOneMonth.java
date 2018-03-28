package com.bumu.arya.admin.salary.model;

import com.bumu.arya.model.entity.AryaSalaryWeekEntity;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/4/7
 */

/**
 * 用户的某月部分周薪薪资
 */
public class UserWeekSalariesInOneMonth{
	String userId;

	List<AryaSalaryWeekEntity> weekEntities;

	public UserWeekSalariesInOneMonth() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<AryaSalaryWeekEntity> getWeekEntities() {
		return weekEntities;
	}

	public void setWeekEntities(List<AryaSalaryWeekEntity> weekEntities) {
		this.weekEntities = weekEntities;
	}
}
