package com.bumu.bran.admin.corporation.result;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/6/21
 */
public class OpModuleListResult {

	List<OpModuleResult> modules;

	public OpModuleListResult() {
	}

	public List<OpModuleResult> getModules() {

		return modules;
	}

	public void setModules(List<OpModuleResult> modules) {
		this.modules = modules;
	}

	public static class OpModuleResult {
		String id;

		String name;

		public OpModuleResult() {

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
