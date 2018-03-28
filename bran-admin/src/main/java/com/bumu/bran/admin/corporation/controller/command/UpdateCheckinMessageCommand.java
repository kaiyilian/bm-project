package com.bumu.bran.admin.corporation.controller.command;

import com.bumu.bran.common.command.TxVersionCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/23
 */
public class UpdateCheckinMessageCommand extends TxVersionCommand {
	@JsonProperty("message_content")
	String messageContent;

	@JsonProperty("before_check_in_day")
	Integer beforeCheckinDay;

	@JsonProperty("post_hour")
	Integer postHour;

	@JsonProperty("is_working")
	Integer isWorking;

	public UpdateCheckinMessageCommand() {
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getBeforeCheckinDay() {
		return beforeCheckinDay;
	}

	public void setBeforeCheckinDay(Integer beforeCheckinDay) {
		this.beforeCheckinDay = beforeCheckinDay;
	}

	public Integer getPostHour() {
		return postHour;
	}

	public void setPostHour(Integer postHour) {
		this.postHour = postHour;
	}

	public Integer getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(Integer isWorking) {
		this.isWorking = isWorking;
	}
}
