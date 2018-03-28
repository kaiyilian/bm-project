package com.bumu.arya.admin.misc.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/11/1.
 * 所有标签结果
 */
@ApiModel
public class GetAllTagsResult {

	@JsonProperty("corp_tags")
    TagCategoryResult corpTags;

	@JsonProperty("filter_tags")
	List<TagCategoryResult> filterTags;

	public List<TagCategoryResult> getFilterTags() {
		return filterTags;
	}

	public void setFilterTags(List<TagCategoryResult> filterTags) {
		this.filterTags = filterTags;
	}

	public TagCategoryResult getCorpTags() {
		return corpTags;
	}

	public void setCorpTags(TagCategoryResult corpTags) {
		this.corpTags = corpTags;
	}
}
