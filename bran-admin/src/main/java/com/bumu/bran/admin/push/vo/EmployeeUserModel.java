package com.bumu.bran.admin.push.vo;

/**
 * @author CuiMengxin
 * @date 2016/5/27
 */
public class EmployeeUserModel {

	String aryaUserId;

	String name;

	String corpName;

	String workSn;

	int lastClientType;

	public EmployeeUserModel() {

	}

	public String getAryaUserId() {
		return aryaUserId;
	}

	public void setAryaUserId(String aryaUserId) {
		this.aryaUserId = aryaUserId;
	}

	public int getLastClientType() {
		return lastClientType;
	}

	public void setLastClientType(int lastClientType) {
		this.lastClientType = lastClientType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getWorkSn() {
		return workSn;
	}

	public void setWorkSn(String workSn) {
		this.workSn = workSn;
	}
}
