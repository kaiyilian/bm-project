package com.bumu.bran.admin.corporation.result;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/6/21
 */
public class OpTypeListResult {

	List<OpTypeResult> types;

	public OpTypeListResult() {

	}

	public List<OpTypeResult> getTypes() {
		return types;
	}

	public void setTypes(List<OpTypeResult> types) {
		this.types = types;
	}

	public static class OpTypeResult {
		String id;

		String name;

		public OpTypeResult() {

		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
