package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/7/14
 */
@ApiModel
public class OrganizationTreeResult {

	@ApiModelProperty("组织树 包括集团、公司、和部门")
	List<OrganizationResult> tree;

	public OrganizationTreeResult() {
	}

	public List<OrganizationResult> getTree() {
		return tree;
	}

	public void setTree(List<OrganizationResult> tree) {
		this.tree = tree;
	}

	public static class OrganizationResult {

		@ApiModelProperty("组织id (集团或公司或部门的组织id)")
		String id;

		@ApiModelProperty("上级组织id (某部门的上级Id 即部门的集团或公司的组织id)")
		@JsonProperty("parent_id")
		String parentId;

		@ApiModelProperty("组织名称")
		String name;

		@ApiModelProperty("组织类型 1.集团 2.子公司 3.通用部门 4.一级公司")
		int type;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public OrganizationResult() {

		}
	}
}
