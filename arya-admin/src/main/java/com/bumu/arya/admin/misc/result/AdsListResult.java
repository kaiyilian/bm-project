package com.bumu.arya.admin.misc.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/6/13
 */
public class AdsListResult {

	List<AdsListResult.AdsResult> ads;

	int pages;

	public AdsListResult() {
	}

	public List<AdsResult> getAds() {
		return ads;
	}

	public void setAds(List<AdsResult> ads) {
		this.ads = ads;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public static class AdsResult {
		String id;

		@JsonProperty("pic_url")
		String picUrl;

		@JsonProperty("file_name")
		String fileName;

		String hint;

		Integer active;

		@JsonProperty("jump_url")
		String jumpUrl;

		@JsonProperty("jump_type")
		int jumpType;

		@JsonProperty("device_type")
		Integer deviceType;

		String distrcit;

		@JsonProperty("min_version")
		Integer minVersion;

		@JsonProperty("max_version")
		Integer maxVersion;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPicUrl() {
			return picUrl;
		}

		public void setPicUrl(String picUrl) {
			this.picUrl = picUrl;
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

		public int getJumpType() {
			return jumpType;
		}

		public void setJumpType(int jumpType) {
			this.jumpType = jumpType;
		}

		public Integer getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(Integer deviceType) {
			this.deviceType = deviceType;
		}

		public String getDistrcit() {
			return distrcit;
		}

		public void setDistrcit(String distrcit) {
			this.distrcit = distrcit;
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

		public AdsResult() {

		}
	}
}
