package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2015/12/29
 */
public class SoinTypeListResult extends ArrayList<SoinTypeListResult.SoinTypeResult> {

	public static class SoinTypeResult {

		@JsonProperty("type_id")
		String typeId;

		@JsonProperty("type_name")
		String typeName;

		@JsonProperty("type_desc")
		String typeDesc;

		public String getTypeId() {
			return typeId;
		}

		public void setTypeId(String typeId) {
			this.typeId = typeId;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getTypeDesc() {
			return typeDesc;
		}

		public void setTypeDesc(String typeDesc) {
			this.typeDesc = typeDesc;
		}

		public SoinTypeResult() {

		}
	}
}
