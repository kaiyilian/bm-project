package com.bumu.arya.admin.misc.result;

import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/9/29.
 */
@ApiModel
public class NotificationListResult extends PaginationResult {

	List<NotificationResult> notifications;

	public List<NotificationResult> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<NotificationResult> notifications) {
		this.notifications = notifications;
	}

	@ApiModel
	public static class NotificationResult {

		String id;

		String title;

		String content;

		int status;

        @ApiModelProperty(value = "显示类型, 1表示状态栏通知，2表示页面显示通知，3表示两种方式都显示", example = "1")
        @JsonProperty("display_type")
        int displayType;

		@JsonProperty("status_str")
		String statusStr;

		@JsonProperty("send_time")
		Long sendTime;

		@JsonProperty("can_edit")
		int canEdit;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public int getCanEdit() {
			return canEdit;
		}

		public void setCanEdit(int canEdit) {
			this.canEdit = canEdit;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public Long getSendTime() {
			return sendTime;
		}

		public void setSendTime(Long sendTime) {
			this.sendTime = sendTime;
		}

		public String getStatusStr() {
			return statusStr;
		}

		public void setStatusStr(String statusStr) {
			this.statusStr = statusStr;
		}

        public int getDisplayType() {
            return displayType;
        }

        public void setDisplayType(int displayType) {
            this.displayType = displayType;
        }
    }
}
