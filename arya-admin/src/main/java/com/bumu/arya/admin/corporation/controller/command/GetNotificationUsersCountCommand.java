package com.bumu.arya.admin.corporation.controller.command;

import com.bumu.arya.common.model.FilterTag;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/11/2.
 * 统计推送人数
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetNotificationUsersCountCommand {

    @JsonProperty("filter_tags")
    List<FilterTag> filterTags;

    public List<FilterTag> getFilterTags() {
        return filterTags;
    }

    public void setFilterTags(List<FilterTag> filterTags) {
        this.filterTags = filterTags;
    }
}
