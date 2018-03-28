package com.bumu.arya.admin.soin.result;

import com.bumu.arya.response.SimpleIdNameResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 2016/11/15.
 */
public class DistrictTreeV2Result {

	List<DistrictTreeV2> tree;

	public DistrictTreeV2Result() {
		this.tree = new ArrayList<>();
	}

	public List<DistrictTreeV2> getTree() {
		return tree;
	}

	public void setTree(List<DistrictTreeV2> tree) {
		this.tree = tree;
	}

	public static class DistrictTreeV2 extends SimpleIdNameResult {

		@JsonProperty("parent_id")
		String parentId;

		/**
		 * 与上级并列情况
		 */
		@JsonProperty("up_super")
		int upSuper;

		/**
		 * 能否与上级并列
		 */
		@JsonProperty("can_up_super")
		int canUpSuper;

		public int getUpSuper() {
			return upSuper;
		}

		public void setUpSuper(int upSuper) {
			this.upSuper = upSuper;
		}

		public int getCanUpSuper() {
			return canUpSuper;
		}

		public void setCanUpSuper(int canUpSuper) {
			this.canUpSuper = canUpSuper;
		}

		public String getParentId() {
			return parentId;
		}

		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
	}
}
