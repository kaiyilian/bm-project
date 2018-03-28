package com.bumu.arya.admin.misc.controller.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CuiMengxin
 * @date 2016/6/14
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateOrUpdateAdsCommand {
	String id;

	String hint;

	@JsonProperty("file_name")
	String fileName;

	Integer active;

	@JsonProperty("jump_url")
	String jumpUrl;

	@JsonProperty("jump_type")
	Integer jumpType;

	@JsonProperty("device_type")
	Integer deviceType;

	Integer direction;

	@JsonProperty("min_version")
	Integer minVersion;

	@JsonProperty("max_version")
	Integer maxVersion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}

	public Integer getJumpType() {
		return jumpType;
	}

	public void setJumpType(Integer jumpType) {
		this.jumpType = jumpType;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Integer getMinVersion() {
		return minVersion;
	}

	public void setMinVersion(Integer minVersion) {
		this.minVersion = minVersion;
	}

	public Integer getMaxVersion() {
		return maxVersion;
	}

	public void setMaxVersion(Integer maxVersion) {
		this.maxVersion = maxVersion;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public CreateOrUpdateAdsCommand() {

	}
}
