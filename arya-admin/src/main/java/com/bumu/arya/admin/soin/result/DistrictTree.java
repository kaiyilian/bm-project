package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 2015/10/21.
 */
public class DistrictTree extends ArrayList<DistrictTree.DistrictTreeItem> {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class DistrictTreeItem implements Serializable {

		String href;

		String text;

		int upSuper;

		List<DistrictTreeItem> nodes;

		public void addChildren(DistrictTreeItem children) {
			if (this.nodes == null) {
				this.nodes = new ArrayList();
			}
			this.nodes.add(children);
		}

		public DistrictTreeItem() {
		}

		public DistrictTreeItem(String href, String text) {
			this.href = href;
			this.text = text;
		}

		public DistrictTreeItem(String href, String text, List<DistrictTreeItem> children) {
			this.href = href;
			this.text = text;
			this.nodes = children;
		}

		public int getUpSuper() {
			return upSuper;
		}

		public void setUpSuper(int upSuper) {
			this.upSuper = upSuper;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public List<DistrictTreeItem> getNodes() {
			return nodes;
		}

		public void setNodes(List<DistrictTreeItem> nodes) {
			this.nodes = nodes;
		}
	}
}
