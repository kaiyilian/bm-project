package com.bumu.bran.admin.notification.result;

import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/5/22
 */
public class NotificationListResult extends PaginationResult {

	List<NotificationListResult.NotificationResult> notifications;

	public NotificationListResult() {

	}

	public List<NotificationResult> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<NotificationResult> notifications) {
		this.notifications = notifications;
	}

	public static class NotificationResult {
		@JsonProperty("notification_id")
		String notificationId;

		@JsonProperty("poster_name")
		String posterName;

		@JsonProperty("notification_title")
		String notificationTitle;

		@JsonProperty("notification_content")
		String notificationContent;

		@JsonProperty("post_time")
		long postTime;

		@JsonProperty("department_id")
		private String departmentId;

		@JsonProperty("department_name")
		private String departmentName;


		public NotificationResult() {

		}

		public String getDepartmentId() {
			return departmentId;
		}

		public void setDepartmentId(String departmentId) {
			this.departmentId = departmentId;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		public String getNotificationId() {
			return notificationId;
		}

		public void setNotificationId(String notificationId) {
			this.notificationId = notificationId;
		}

		public String getPosterName() {
			return posterName;
		}

		public void setPosterName(String posterName) {
			this.posterName = posterName;
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

		public long getPostTime() {
			return postTime;
		}

		public void setPostTime(long postTime) {
			this.postTime = postTime;
		}
	}
}
