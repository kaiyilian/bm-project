package com.bumu.bran.admin.user_permission.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * majun
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CorpModelResult {

	private Integer total_page;
	private Integer total_rows;
	private List<ModelResult> result;

	public Integer getTotal_rows() {
		return total_rows;
	}

	public void setTotal_rows(Integer total_rows) {
		this.total_rows = total_rows;
	}

	public List<ModelResult> getResult() {
		return result;
	}

	public void setResult(List<ModelResult> result) {
		this.result = result;
	}

	public Integer getTotal_page() {
		return total_page;
	}

	public void setTotal_page(Integer total_page) {
		this.total_page = total_page;
	}

	public class ModelResult {

		private Long version;
		private String userId;
		private String name;
		private Long createTime;
		private Long lastLoginTime;

		public Long getVersion() {
			return version;
		}

		public void setVersion(Long version) {
			this.version = version;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}

		public Long getLastLoginTime() {
			return lastLoginTime;
		}

		public void setLastLoginTime(Long lastLoginTime) {
			this.lastLoginTime = lastLoginTime;
		}
	}
}
