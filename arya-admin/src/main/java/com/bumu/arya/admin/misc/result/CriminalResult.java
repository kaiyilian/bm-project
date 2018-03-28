package com.bumu.arya.admin.misc.result;

import com.bumu.arya.admin.misc.model.entity.CriminalEntity;
import com.bumu.common.result.ModelResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author majun
 * @date 2017/3/9
 */
@ApiModel(value = "单个犯罪记录响应对象", parent = ModelResult.class)
public class CriminalResult extends ModelResult {
	@ApiModelProperty(value = "身份证")
	private String idCardNo;
	@ApiModelProperty(value = "犯罪详情")
	private String criminalDetail;
	@ApiModelProperty(value = "查询状态 0:成功 1:失败")
	private int queryStatus;
	private String queryStatusName;

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

	public String getQueryStatusName() {
		return queryStatusName;
	}

	public void setQueryStatusName(String queryStatusName) {
		this.queryStatusName = queryStatusName;
	}

	public static CriminalResult createByEntity(CriminalEntity entity) throws InvocationTargetException, IllegalAccessException {
		CriminalResult criminalResult = new CriminalResult();
		if (entity != null) {
			BeanUtils.copyProperties(criminalResult, entity);
		}
		return criminalResult;
	}
}
