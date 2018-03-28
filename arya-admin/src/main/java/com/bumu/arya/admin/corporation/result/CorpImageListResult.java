package com.bumu.arya.admin.corporation.result;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/5/28
 */
public class CorpImageListResult extends ArrayList<CorpImageListResult.CorpImageResult> {

	public static class CorpImageResult {
		String id;

		String url;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public CorpImageResult() {

		}
	}
}
