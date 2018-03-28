package com.bumu.arya.admin.misc.controller.command;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.admin.misc.controller.validate.IdCardNo;
import com.bumu.arya.admin.misc.model.entity.CriminalEntity;
import com.bumu.common.validator.annotation.CriminalValidationGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author majun
 * @date 2017/3/9
 */
@ApiModel
public class CriminalCommand {

	private SysUserService sysUserService;
	@ApiModelProperty(value = "身份证", notes = "长度为15-18位", required = true)
	@IdCardNo(groups = CriminalValidationGroup.class)
	private String idCardNo;
	private String criminalDetail;
	private int queryStatus;
	@ApiModelProperty(value = "姓名", notes = "长度为1-8位", required = true)
	@NotBlank(message = "{arya.admin.criminal.name.notBlank}", groups = CriminalValidationGroup.class)
	@Length(min = 1, max = 8, message = "{arya.admin.criminal.name}", groups = CriminalValidationGroup.class)
	private String name;
	public CriminalCommand() {
	}
	public CriminalCommand(String idCardNo, String criminalDetail, int queryStatus) {
		this.idCardNo = idCardNo;
		this.criminalDetail = criminalDetail;
		this.queryStatus = queryStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getCriminalDetail() {
		return criminalDetail;
	}

	public void setCriminalDetail(String criminalDetail) {
		this.criminalDetail = criminalDetail;
	}

	public int getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(int queryStatus) {
		this.queryStatus = queryStatus;
	}

	public RealShowCommand toRealShowParams() {
		return new RealShowCommand(getName(), getIdCardNo());
	}


	public void toCreateEntity(CriminalEntity entity) {
		entity.setId(Utils.makeUUID());
		entity.setName(getName());
		entity.setIdCardNo(getIdCardNo());
		entity.setCriminalDetail(getCriminalDetail());
		entity.setQueryStatus(getQueryStatus());
		if (sysUserService == null) {
			sysUserService = (SysUserService) SysUtils.getBean(SysUserService.class);
		}
		entity.setCreateUser(sysUserService.getCurrentSysUser().getId());
	}

	public void toUpdateEntity(CriminalEntity entity) {
		entity.setCriminalDetail(getCriminalDetail());
		entity.setQueryStatus(getQueryStatus());
		entity.setUpdateTime(System.currentTimeMillis());
		entity.setName(getName());
		if (sysUserService == null) {
			sysUserService = (SysUserService) SysUtils.getBean(SysUserService.class);
		}
		entity.setUpdateUser(sysUserService.getCurrentSysUser().getId());
	}

}
