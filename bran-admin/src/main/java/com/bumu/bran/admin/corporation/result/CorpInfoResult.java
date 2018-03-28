package com.bumu.bran.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * @author CuiMengxin
 * @date 2016/5/23
 */
@ApiModel
public class CorpInfoResult {

	String name;

	String type;

	String address;

	String telephone;

	String fax;

	String email;

	@JsonProperty("checkin_code")
	String checkinCode;

	String[] images;

	@JsonProperty("qrcode_url")
	String qrCodeUrl;

	public CorpInfoResult() {
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCheckinCode() {
		return checkinCode;
	}

	public void setCheckinCode(String checkinCode) {
		this.checkinCode = checkinCode;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
}
