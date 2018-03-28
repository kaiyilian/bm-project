package com.bumu.bran.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author CuiMengxin
 * @date 2016/5/25
 */
@ApiModel
public class CorpUserInfoResult {

	String id;

    @ApiModelProperty("用户姓名")
	String name;

	@ApiModelProperty("公司名称")
	@JsonProperty("corp_name")
	String corpName;

	@JsonProperty("last_login_time")
	Long lastLoginTime;

	@JsonProperty("last_login_ip")
	String lastLoginIp;

	public CorpUserInfoResult() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}
