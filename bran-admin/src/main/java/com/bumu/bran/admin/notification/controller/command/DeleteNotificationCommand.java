package com.bumu.bran.admin.notification.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/22
 */
public class DeleteNotificationCommand {

	@JsonProperty("notification_ids")
	String[] notificationIds;

	public DeleteNotificationCommand() {

	}

	public String[] getNotificationIds() {
		return notificationIds;
	}

	public void setNotificationIds(String[] notificationIds) {
		this.notificationIds = notificationIds;
	}
}
