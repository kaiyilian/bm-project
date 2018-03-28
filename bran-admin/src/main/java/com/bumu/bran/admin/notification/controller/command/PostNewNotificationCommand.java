package com.bumu.bran.admin.notification.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/22
 */
public class PostNewNotificationCommand {

	@JsonProperty("notification_title")
	String notificationTitle;

	@JsonProperty("notification_content")
	String notificationContent;

	@JsonProperty("notification_poster")
	String poster;

	@JsonProperty("department_id")
	private String departmentId;

	private String currentUserId;

	private String branCorpId;

	public PostNewNotificationCommand() {

	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getBranCorpId() {
		return branCorpId;
	}

	public void setBranCorpId(String branCorpId) {
		this.branCorpId = branCorpId;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public String getNotificationContent() {
		return notificationContent;
	}

	public void setNotificationContent(String notificationContent) {
		this.notificationContent = notificationContent;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
