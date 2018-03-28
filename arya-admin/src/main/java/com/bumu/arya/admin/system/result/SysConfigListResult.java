package com.bumu.arya.admin.system.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 2017/2/14.
 */
public class SysConfigListResult {

	List<SysConfig> configs;

	public List<SysConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<SysConfig> configs) {
		this.configs = configs;
	}

	public static class SysConfig {
		String id;

		String key;

		String value;

		@JsonProperty("value_abbreviate")
		String valueAbbreviate;

		String memo;

		//为了方便小米所以将Integer类型改为String，直接传yes或no
		@JsonProperty("is_deprecated")
		String isDeprecated;

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

        public String getValueAbbreviate() {
            return valueAbbreviate;
        }

        public void setValueAbbreviate(String valueAbbreviate) {
            this.valueAbbreviate = valueAbbreviate;
        }

        public String getIsDeprecated() {
			return isDeprecated;
		}

		public void setIsDeprecated(String isDeprecated) {
			this.isDeprecated = isDeprecated;
		}
	}
}
