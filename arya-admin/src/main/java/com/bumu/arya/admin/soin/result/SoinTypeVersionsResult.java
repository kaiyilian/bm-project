package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/3/9
 */
public class SoinTypeVersionsResult extends ArrayList<SoinTypeVersionsResult.SoinTypeVersion> {
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class SoinTypeVersion implements Serializable {

		String id;

		@JsonProperty("effect_year")
		Integer effectYear;

		@JsonProperty("effect_month")
		Integer effectMonth;


		public SoinTypeVersion() {
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Integer getEffectYear() {
			return effectYear;
		}

		public void setEffectYear(Integer effectYear) {
			this.effectYear = effectYear;
		}

		public Integer getEffectMonth() {
			return effectMonth;
		}

		public void setEffectMonth(Integer effectMonth) {
			this.effectMonth = effectMonth;
		}

	}
}
