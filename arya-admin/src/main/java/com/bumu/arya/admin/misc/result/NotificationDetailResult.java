package com.bumu.arya.admin.misc.result;

import com.bumu.arya.common.model.FilterTag;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/9/30.
 */
@ApiModel
public class NotificationDetailResult {

	String id;

	String title;

	String content;

    @ApiModelProperty(value = "显示类型, 1表示状态栏通知，2表示页面显示通知，3表示两种方式都显示", example = "1")
    @JsonProperty("display_type")
    int displayType;

	@JsonProperty("jump_type")
	int jumpType;

	@JsonProperty("jump_url")
	String jumpUrl;

	/**
	 * 是否是定时推送
	 */
	@JsonProperty("is_timing")
	int isTiming;

	/**
	 * 设置的发送时间
	 */
	@JsonProperty("set_send_time")
	Long setSendTime;

	@JsonProperty("filter_tags")
	List<FilterTag> filterTags;

	int status;

	/**
	 * 是否可编辑
	 */
	@JsonProperty("can_edit")
	int canEdit;

	@JsonProperty("push_count")
	long pushCount;

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getJumpType() {
		return jumpType;
	}

	public void setJumpType(int jumpType) {
		this.jumpType = jumpType;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public int getIsTiming() {
		return isTiming;
	}

	public void setIsTiming(int isTiming) {
		this.isTiming = isTiming;
	}

	public Long getSetSendTime() {
		return setSendTime;
	}

	public void setSetSendTime(Long setSendTime) {
		this.setSendTime = setSendTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<FilterTag> getFilterTags() {
		return filterTags;
	}

	public void setFilterTags(List<FilterTag> filterTags) {
		this.filterTags = filterTags;
	}

	public int getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(int canEdit) {
		this.canEdit = canEdit;
	}

	public long getPushCount() {
		return pushCount;
	}

	public void setPushCount(long pushCount) {
		this.pushCount = pushCount;
	}
}
