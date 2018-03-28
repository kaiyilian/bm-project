package com.bumu.arya.admin.misc.result;

import com.bumu.arya.response.SimpleIdNameResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/11/1.
 */
@ApiModel
public class TagCategoryResult {

	@JsonProperty("category_id")
	String categoryId;

	@JsonProperty("category_name")
	String categoryName;

	@JsonProperty("is_multi_select")
	int isMultiSelect;

	List<SimpleIdNameResult> tags;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getIsMultiSelect() {
		return isMultiSelect;
	}

	public void setIsMultiSelect(int isMultiSelect) {
		this.isMultiSelect = isMultiSelect;
	}

	public List<SimpleIdNameResult> getTags() {
		return tags;
	}

	public void setTags(List<SimpleIdNameResult> tags) {
		this.tags = tags;
	}
}
