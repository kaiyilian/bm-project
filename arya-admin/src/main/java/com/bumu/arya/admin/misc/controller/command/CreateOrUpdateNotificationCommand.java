package com.bumu.arya.admin.misc.controller.command;

import com.bumu.arya.common.model.FilterTag;
import com.bumu.common.model.PushFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by CuiMengxin on 2016/9/30.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel
public class CreateOrUpdateNotificationCommand {

	String id;

	String title;

	String content;

	@JsonProperty("jump_type")
	int jumpType;

	@ApiModelProperty(value = "显示类型, 1表示状态栏通知，2表示页面显示通知，3表示两种方式都显示", example = "1")
	@JsonProperty("display_type")
	int displayType;

	@JsonProperty("jump_url")
	String jumpUrl;

	@JsonProperty("send_time")
	Long sendTime;

	@JsonProperty("filter_tags")
	List<FilterTag> filterTags;

	@ApiModelProperty(value = "tags")
	private List<Map<String, String>> tags;

	@ApiModelProperty(hidden = true)
	private PushFilter filter;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		if (StringUtils.isNotBlank(jumpUrl)) {
			if (!jumpUrl.toLowerCase().contains("http://")) {
				jumpUrl = "http://" + jumpUrl;
			}
		}
		this.jumpUrl = jumpUrl;
	}

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}

	public List<FilterTag> getFilterTags() {
		return filterTags;
	}

	public void setFilterTags(List<FilterTag> filterTags) {
		this.filterTags = filterTags;
	}

	public List<Map<String, String>> getTags() {
		return tags;
	}

	public void setTags(List<Map<String, String>> tags) {
		this.tags = tags;
	}

	public PushFilter getFilter() {
		return filter;
	}

	public void setFilter(PushFilter filter) {
		this.filter = filter;
	}


}
