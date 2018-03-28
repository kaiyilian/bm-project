package com.bumu.arya.admin.soin.controller.command;/* @author CuiMengxin
 * @date 2015/11/24
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ImportSoinVerifyCommand implements Serializable {
	@JsonProperty("district_id")
	String districtId;

	@JsonProperty("soin_type_id")
	String soinTypeId;

	@JsonProperty("corporation_id")
	String  corporationId;

	public ImportSoinVerifyCommand() {
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getSoinTypeId() {
		return soinTypeId;
	}

	public void setSoinTypeId(String soinTypeId) {
		this.soinTypeId = soinTypeId;
	}

	public String getCorporationId() {
		return corporationId;
	}

	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

}
