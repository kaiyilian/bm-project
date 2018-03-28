package com.bumu.arya.admin.salary.controller.command;

public class QueryCalculateSalary2ModelsCommand {
	private final String groupId;
	private final String departmentId;
	private final int year;
	private final int month;
	private final Integer week;
	private final String keyWord;

	/**
	 * @param groupId
	 * @param year
	 * @param month
	 * @param week
	 */
	public QueryCalculateSalary2ModelsCommand(String groupId, String departmentId, int year, int month, Integer week, String keyWord) {
		this.groupId = groupId;
		this.departmentId = departmentId;
		this.year = year;
		this.month = month;
		this.week = week;
		this.keyWord = keyWord;
	}

	public QueryCalculateSalary2ModelsCommand(String groupId, String departmentId, int year, int month, Integer week) {
		this.groupId = groupId;
		this.departmentId = departmentId;
		this.year = year;
		this.month = month;
		this.week = week;
		this.keyWord = "export";
	}

	public String getGroupId() {
		return groupId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public Integer getWeek() {
		return week;
	}

	public String getKeyWord(){ return keyWord;}
}
