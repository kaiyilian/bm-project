package com.bumu.arya.admin.soin.service;

import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.soin.controller.command.*;
import com.bumu.arya.admin.soin.result.*;
import com.bumu.arya.soin.model.entity.SoinRuleEntity;
import com.bumu.exception.AryaServiceException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author CuiMengxin
 * @date 2015/12/29
 */
public interface SoinBaseInfoService {

	/**
	 * 获取地区下所有的社保类型
	 *
	 * @param districtId
	 * @return
	 */
	JQDatatablesPaginationResult getDistrictAllSoinType(String districtId);

	/**
	 * 获取社保类型详情
	 *
	 * @param typeId
	 * @return
	 */
	SoinTypeDetailResult getSoinTypeDetail(String typeId);

	/**
	 * 修改社保类型详情
	 *
	 * @param command
	 * @return
	 */
	void updateSoinTypeDetail(UpdateSoinTypeDetailCommand command) throws AryaServiceException;

	/**
	 * 新建社保类型版本
	 *
	 * @param command
	 * @return
	 */
	SoinTypeVersionsResult.SoinTypeVersion createNewSoinTypeVersion(CreateSoinTypeVersionCommand command) throws AryaServiceException;


	/**
	 * 复制险种
	 *
	 * @param ruleEntity
	 * @return
	 */
	SoinRuleEntity copySoinRuleEntity(SoinRuleEntity ruleEntity);

	/**
	 * 获取社保类型下的所有版本
	 *
	 * @param typeId
	 * @param versionType
	 * @return
	 */
	SoinTypeVersionsResult getSoinTypeVersion(String typeId, Integer versionType);


	/**
	 * 获取社保类型指定版本的详情
	 *
	 * @param versionId
	 * @return
	 */
	SoinTypeVersionDetailResut getSoinTypeVersionDetail(String versionId);

	/**
	 * 修改社保类型版本详情
	 *
	 * @param command
	 */
	void updateSoinTypeVersionDetail(UpdateSoinTypeVersionDetailCommand command);

	/**
	 * 将参保地区与上级地区并列
	 *
	 * @param command
	 */
	void upSoinDistrict(CreateSoinDistrictCommand command);

	/**
	 * 取消参保地区与上级地区并列
	 *
	 * @param command
	 */
	void cancelUpSoinDistrict(CreateSoinDistrictCommand command);

	/**
	 * 禁用社保类型
	 *
	 * @param typeId
	 * @throws AryaServiceException
	 */
	void disablesSoinType(String typeId) throws AryaServiceException;

	/**
	 * 启用社保类型
	 *
	 * @param typeId
	 * @throws AryaServiceException
	 */
	void enableSoinType(String typeId) throws AryaServiceException;

	/**
	 * 禁用社保类型版本
	 *
	 * @param versionId
	 * @throws AryaServiceException
	 */
	void disableSoinTypeVersion(String versionId) throws AryaServiceException;

	/**
	 * 启用社保类型版本
	 *
	 * @param versionId
	 * @throws AryaServiceException
	 */
	void enbableSoinTypeVersion(String versionId) throws AryaServiceException;


	/**
	 * 判断用户是否有修改基础数据的权限
	 *
	 * @return
	 * @throws AryaServiceException
	 */
	SoinBaseInfoChangeAuthResult checkUserHasChangeAuth() throws AryaServiceException;

	/**
	 * 导出指定地区的社保规则详情
	 *
	 * @param command
	 * @param response
	 */
	void exportDistrictSoinBaseDetail(IdStrListCommand command, HttpServletResponse response);

	/**
	 * 检查社保类型是否存在冲突的社保类型版本
	 *
	 * @param soinTypeId
	 * @param effectYear
	 * @param effectMonth
	 * @param versionType
	 */
	void checkSoinTypeVersionConflict(String soinTypeId, int effectYear, int effectMonth, int versionType);

	/**
	 * 获取地区的社保类型，如苏州只获取苏州上的社保类型不包括工业园区
	 *
	 * @param districtId
	 * @return
	 */
	DistrictSoinTypesResult soinDistrictType(String districtId) throws AryaServiceException;

	/**
	 * 创建地区社保类型
	 *
	 * @param command
	 * @return
	 */
	DistrictSoinTypesResult createDistrictType(CreateSoinTypeCommand command) throws AryaServiceException;

	/**
	 * 删除社保类型
	 *
	 * @param typeId
	 * @throws AryaServiceException
	 */
	void deleteDistrictType(String typeId) throws AryaServiceException;

	/**
	 * 删除社保类型版本
	 *
	 * @param typeVersionId
	 * @throws AryaServiceException
	 */
	void deleteSoinTypeVersion(String typeVersionId) throws AryaServiceException;
}