package com.bumu.arya.admin.soin.controller;

import com.bumu.arya.admin.common.controller.BaseController;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.soin.service.ResponseService;
import com.bumu.arya.admin.soin.controller.command.*;
import com.bumu.arya.admin.soin.result.*;
import com.bumu.arya.admin.soin.service.SoinBaseInfoService;
import com.bumu.arya.admin.soin.service.SoinDistrictTreeService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.soin.model.dao.AryaSoinDistrictDao;
import com.bumu.arya.soin.model.dao.AryaSoinTypeDao;
import com.bumu.arya.model.DistrictDao;
import com.bumu.arya.soin.model.entity.AryaSoinDistrictEntity;
import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.arya.soin.model.entity.SoinTypeEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.bumu.SysUtils.turnStringToBigDecimal;
import static com.bumu.arya.common.Constants.SOIN_VERSION_TYPE_BACK;
import static com.bumu.arya.common.Constants.SOIN_VERSION_TYPE_NORMAL;

/**
 * 社保相关接口
 */
@Controller
@Api(value = "SoinBaseInfo",tags = "社保基础数据SoinBaseInfo")
public class SoinBaseInfoController extends BaseController {

	@Autowired
	AryaSoinTypeDao aryaSoinTypeDao;

	@Autowired
	AryaSoinDistrictDao soinDistrictDao;

	@Autowired
	DistrictDao districtDao;

	@Autowired
	AryaSoinDistrictDao aryaSoinDistrictDao;

	@Autowired
	SoinDistrictTreeService buildDistrictTreeService;

	@Autowired
	SoinBaseInfoService baseInfoService;

	@Autowired
	ResponseService responseService;

	/**
	 * 获取社保基础数据维护页面
	 */
	@RequestMapping(value = "admin/soin/base_info/index", method = RequestMethod.GET)
	public String soinBaseInfoManagePage() {
		return "soin/soin_base_info_manage";
	}

	/**
	 * 获取社保基础数据维护页面
	 */
	@RequestMapping(value = "admin/soin/base_info/index/new", method = RequestMethod.GET)
	public String soinBaseInfoManageNewPage() {
		return "soin/soin_base_info_manage";
	}

	/**
	 * 获取个人社保订单处理页面
	 */
	@RequestMapping(value = "admin/soin/soin_personal_order", method = RequestMethod.GET)
	public String soinPersonalOrderPage() {
		return "soin/soin_order_manager";
	}

	/**
	 * 获取已开通社保地区，地区最多分三级：省-市-区
	 */
	@RequestMapping(value = "/admin/soin/district/tree", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse districtTree() {
		DistrictTreeV2Result tree;
		List<AryaSoinDistrictEntity> districtEntities = soinDistrictDao.findAll();
		//把地区放入tree中
		tree = buildDistrictTreeService.buildDistrictTreeV2(districtEntities);
		return new HttpResponse(tree);
	}

	/**
	 * 获取未开通社保的地区字典
	 */
	@RequestMapping(value = "/admin/soin/district/na", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse subDistricts(@RequestParam("district_id") String districtId) {
		DistrictTreeV2Result tree = new DistrictTreeV2Result();
		if (null != districtId) {
			List<DistrictEntity> districtEntities = districtDao.findByParam(DistrictEntity.COL_NAME_PARENT_ID, districtId);
			List<AryaSoinDistrictEntity> soinDistrictEntities = aryaSoinDistrictDao.findByParam(AryaSoinDistrictEntity.COL_NAME_PARENT_ID, districtId);
			//收集已开通社保的地区
			Collection<String> soinDistrictIds = new ArrayList();
			for (AryaSoinDistrictEntity soinDistrictEntity : soinDistrictEntities) {
				if (!soinDistrictIds.contains(soinDistrictEntity.getId())) {
					soinDistrictIds.add(soinDistrictEntity.getId());
				}
			}

			//把已开通地区去掉
			for (DistrictEntity districtEntity : districtEntities) {
				if (!soinDistrictIds.contains(districtEntity.getId())) {
					DistrictTreeV2Result.DistrictTreeV2 treeV2 = new DistrictTreeV2Result.DistrictTreeV2();
					treeV2.setId(districtEntity.getId());
					treeV2.setName(districtEntity.getDistrictName());
					treeV2.setParentId(districtEntity.getParentId());
					tree.getTree().add(treeV2);
				}
			}
		}
		return new HttpResponse(tree);
	}

	/**
	 * 添加社保地区
	 */
	@RequestMapping(value = "/admin/soin/district/create", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<AddSoinDistrictResult> createSoinDistrict(@RequestBody CreateSoinDistrictCommand command) {
		AddSoinDistrictResult result = new AddSoinDistrictResult();
		DistrictEntity targetDistrict = districtDao.find(command.getDistrictId());
		if (null != aryaSoinDistrictDao.find(command.getDistrictId())) {
			result.setIsExist(true);
			result.setIsSuccess(false);
			return new HttpResponse<>(result);
		}
		AryaSoinDistrictEntity newSoinDistrict = new AryaSoinDistrictEntity();
		newSoinDistrict.setId(targetDistrict.getId());
		newSoinDistrict.setCreateTime(System.currentTimeMillis());
		newSoinDistrict.setDistrictName(targetDistrict.getDistrictName());
		newSoinDistrict.setParentId(targetDistrict.getParentId());
		aryaSoinDistrictDao.create(newSoinDistrict);
		result.setIsSuccess(true);
		result.setIsExist(false);

		return new HttpResponse<>(result);
	}


	/**
	 * 将参保地区与父级地区并列
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/up", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse upSoinDistrict(@RequestBody CreateSoinDistrictCommand command) {
		try {
			baseInfoService.upSoinDistrict(command);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 取消参保地区与父级地区并列
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/up/cancel", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse cancelUpSoinDistrict(@RequestBody CreateSoinDistrictCommand command) {
		try {
			baseInfoService.cancelUpSoinDistrict(command);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 删除社保地区
	 *
	 * @param deleteSoinDistrictCommand
	 */
	@RequestMapping(value = "/admin/soin/district/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse deleteSoinDistrict(@RequestBody DeleteSoinDistrictCommand deleteSoinDistrictCommand) {

		if (aryaSoinTypeDao.findDistricAllSoinTypeCount(deleteSoinDistrictCommand.getDistrictId()) > 0) {
			HttpResponse cantDeleteReponse = new HttpResponse();
			cantDeleteReponse.setCode(ErrorCode.CODE_INSURANCE_DISTRICT_CANT_DELETE_HAVE_TYPE);
			return cantDeleteReponse;
		}

		if (soinDistrictDao.findChildrenDistrictCount(deleteSoinDistrictCommand.getDistrictId()) > 0) {
			HttpResponse cantDeleteReponse = new HttpResponse();
			cantDeleteReponse.setCode(ErrorCode.CODE_INSURANCE_DISTRICT_CANT_DELETE_HAVE_CHILDREN);
			return cantDeleteReponse;
		}

		try {
			aryaSoinDistrictDao.delete(deleteSoinDistrictCommand.getDistrictId());
			HttpResponse success = new HttpResponse();
			success.setMsg("删除成功");
			return success;
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpResponse deleteError = new HttpResponse();
		deleteError.setCode(ErrorCode.CODE_INSURANCE_DISTRICT_DELETE_FAILD);
		return deleteError;
	}

	/**
	 * 获取地区的社保类型，如苏州只获取苏州上的社保类型不包括工业园区
	 */
	@ApiOperation(httpMethod = "GET", notes = "不包含删除的社保类型", value = "获取地区的社保类型")
	@ApiResponse(code = 200, message = "查询成功", response = DistrictSoinTypesResult.class, responseContainer = "list")
	@RequestMapping(value = "/admin/soin/district/type", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<DistrictSoinTypesResult> soinDistrictType(@RequestParam("district_id") String districtId) throws Exception {
		return new HttpResponse(baseInfoService.soinDistrictType(districtId));
	}


	/**
	 * 禁用社保类型
	 *
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type/disable", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse disableSoinType(@RequestParam("type_id") String typeId) {
		try {
			baseInfoService.disablesSoinType(typeId);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			throw new AryaServiceException(e.getErrorCode());
		}
	}

	/**
	 * 启用社保类型
	 *
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type/enable", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse enableSoinType(@RequestParam("type_id") String typeId) {
		try {
			baseInfoService.enableSoinType(typeId);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			throw new AryaServiceException(e.getErrorCode());
		}
	}

	/**
	 * 获取地区的所有的社保类型,如苏州则获取苏州下的所有社保类型包括工业园区。
	 *
	 * @param districtId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type_all", method = RequestMethod.GET)
	public
	@ResponseBody
    JQDatatablesPaginationResult getDistrictAllSoinType(@RequestParam("district_id") String districtId) {
		return baseInfoService.getDistrictAllSoinType(districtId);
	}

	/**
	 * 获取社保类型详情
	 *
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type/detail", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<SoinTypeDetailResult> soinTypeDetailResult(@RequestParam("type_id") String typeId) {
		return new HttpResponse<>(baseInfoService.getSoinTypeDetail(typeId));
	}

	/**
	 * 修改社保类型详情
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type/detail/update", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse updateSoinTypeDetailResult(@RequestBody UpdateSoinTypeDetailCommand command) {
		try {
			baseInfoService.updateSoinTypeDetail(command);
			return new HttpResponse<>();
		} catch (AryaServiceException e) {
			return new HttpResponse<>(e.getErrorCode());
		}
	}

	/**
	 * 新增社保类型版本
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/type/version/create", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<SoinTypeVersionsResult.SoinTypeVersion> createSoinTypeVersion(@RequestBody CreateSoinTypeVersionCommand command) {
		try {
			command.setVersionType(SOIN_VERSION_TYPE_NORMAL);
			SoinTypeVersionsResult.SoinTypeVersion result = baseInfoService.createNewSoinTypeVersion(command);
			return new HttpResponse<>(result);
		} catch (AryaServiceException e) {
			return new HttpResponse<>(e.getErrorCode());
		}
	}

	/**
	 * 获取社保类型的所有版本
	 *
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type/versions", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<SoinTypeVersionsResult> soinTypeVersionResult(@RequestParam("type_id") String typeId) {
		return new HttpResponse<>(baseInfoService.getSoinTypeVersion(typeId, SOIN_VERSION_TYPE_NORMAL));
	}

	/**
	 * 禁用社保类型版本
	 *
	 * @param versionId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type/version/disable", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse disableSoinTypeVersion(@RequestParam("version_id") String versionId) {
		try {
			baseInfoService.disableSoinTypeVersion(versionId);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			throw new AryaServiceException(e.getErrorCode());
		}
	}

	/**
	 * 启用社保类型版本
	 *
	 * @param versionId
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type/version/enable", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse enableSoinTypeVersion(@RequestParam("version_id") String versionId) {
		try {
			baseInfoService.enbableSoinTypeVersion(versionId);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			throw new AryaServiceException(e.getErrorCode());
		}
	}

	/**
	 * 获取某社保类型版本的详情
	 */
	@RequestMapping(value = "/admin/soin/district/type/version/detail", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse<SoinTypeVersionDetailResut> getSoinTypeVersionDetail(@RequestParam("version_id") String versionId) {
		try {
			SoinTypeVersionDetailResut resut = baseInfoService.getSoinTypeVersionDetail(versionId);
			return new HttpResponse<>(resut);
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpResponse<>(ErrorCode.CODE_SYS_ERR);
		}
	}

	/**
	 * 修改社保类型版本详情
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/type/version/detail/update", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse updateSoinTypeVersionDetail(@RequestBody UpdateSoinTypeVersionDetailCommand command) {
		try {
			baseInfoService.updateSoinTypeVersionDetail(command);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode(), e.getMessage());
		}
	}

	/**
	 * 获取某社保类型的地区名称（省-市-区+类型名称）
	 *
	 * @deprecated
	 */
	@RequestMapping(value = "/admin/soin/district_soin_info", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse getDistrictNameAndSoinType(@RequestParam("type_id") String typeId) {
		try {
			SoinTypeEntity typeEntity = aryaSoinTypeDao.find(typeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HttpResponse();
	}

	/**
	 * 给某地区添加社保类型
	 */
	@ApiOperation(httpMethod = "POST", notes = "本地区已经存在社保类型则复制最近的一个类型", value = "给某地区添加社保类型")
	@ApiResponse(code = 200, message = "OK")
	@RequestMapping(value = "/admin/soin/district/type/create", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<DistrictSoinTypesResult> createSoinTypeResult(@ApiParam("创建社保类型") @RequestBody CreateSoinTypeCommand command) throws Exception {
		return new HttpResponse(baseInfoService.createDistrictType(command));
	}

	/**
	 * 给某地区删除社保类型
	 *
	 * @param deleteSoinTypeCommand
	 */
	@ApiOperation(httpMethod = "POST",notes = "删除某地区的社保类型并且删除此类型下的社保版本", value = "删除社保类型")
	@ApiResponse(code = 200,message = "OK")
	@RequestMapping(value = "/admin/soin/district/type/delete", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> deleteSoinType(@ApiParam("需要删除的社保类型id") @RequestBody DeleteSoinTypeCommand deleteSoinTypeCommand){
		try {
			baseInfoService.deleteDistrictType(deleteSoinTypeCommand.getTypeId());
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 删除社保类型版本
	 *
	 */
	@ApiOperation(httpMethod = "POST",notes = "根据选中的版本，删除社保类型版本",value = "删除社保类型版本")
	@ApiResponse(code = 200,message = "OK")
	@RequestMapping(value = "/admin/soin/type/version/delete",method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<Object> deleteSoinTypeVersion(@ApiParam("删除的社保类型版本id") @RequestBody DeleteSoinTypeVersionCommand command) throws Exception{
		baseInfoService.deleteSoinTypeVersion(command.getTypeVersionId());
		return new HttpResponse();
	}

	/**
	 * 更改社保类型
	 */
	@RequestMapping(value = "/admin/soin/district/type/update", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse<UpdateSoinTypeResult> updatetSoinTypeResult(@RequestBody UpdateSoinTypeCommand command) {
		if (null != command.getTypeId()) {
			try {
				SoinTypeEntity typeEntity = aryaSoinTypeDao.find(command.getTypeId());
				if (null != command.getFee()) {
					typeEntity.setFees(turnStringToBigDecimal(command.getFee()));
				}

				if (null != command.getLastDay()) {
					try {
						typeEntity.setLastDay(Integer.parseInt(command.getLastDay()));
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				if (null != command.getName()) {
					typeEntity.setTypeName(command.getName());
				}

				if (null != command.getTypeDesc()) {
					typeEntity.setTypeDesc(command.getTypeDesc());
				}
				aryaSoinTypeDao.update(typeEntity);
				UpdateSoinTypeResult result = new UpdateSoinTypeResult();
				return new HttpResponse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpResponse error = new HttpResponse();
		error.setCode(ErrorCode.CODE_SYS_ERR);
		return error;
	}

	/**
	 * 导出社保规则详情
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/detail/export", method = RequestMethod.POST)
	public
	@ResponseBody
	void soinBaseInfoDetailExport(@RequestParam String ids, HttpServletResponse response) {
		try {
			ids = StringUtils.replace(ids, "\"", "");
			ids = StringUtils.replace(ids, "[", "");
			ids = StringUtils.replace(ids, "]", "");
			IdStrListCommand command = new IdStrListCommand();
			List<String> idList = java.util.Arrays.asList(ids.split(","));
			command.setIds(idList);
			baseInfoService.exportDistrictSoinBaseDetail(command, response);
		} catch (Exception e) {
			responseService.writeErrorCodeToResponse(response, ErrorCode.CODE_SOIN_BASE_EXPORT_FAILED);
		}
	}

	/**
	 * 判断用户是否有修改社保规则的权限
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/base_info/change/auth", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse checkUserHasChangeBaseInfoAuth() {
		try {
			return new HttpResponse(baseInfoService.checkUserHasChangeAuth());
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 补缴业务
	 */

	/**
	 * 5.1.5获取社保类型补缴版本列表
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/district/type/back/versions", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse getbackVersionList(@RequestParam("type_id") String typeId) {
		try {
			return new HttpResponse<>(baseInfoService.getSoinTypeVersion(typeId, SOIN_VERSION_TYPE_BACK));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 新增社保类型补缴版本
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/type/back/version/create", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse createSoinTypeBackVersion(@RequestBody CreateSoinTypeVersionCommand command) {
		try {
			command.setVersionType(SOIN_VERSION_TYPE_BACK);
			return new HttpResponse<>(baseInfoService.createNewSoinTypeVersion(command));
		} catch (AryaServiceException e) {
			return new HttpResponse<>(e.getErrorCode());
		}
	}

	/**
	 * 获取某社保类型补缴版本的详情
	 */
	@RequestMapping(value = "/admin/soin/district/type/back/version/detail", method = RequestMethod.GET)
	public
	@ResponseBody
	HttpResponse getSoinTypeBackVersionDetail(@RequestParam("version_id") String versionId) {
		try {
			return new HttpResponse(baseInfoService.getSoinTypeVersionDetail(versionId));
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}

	/**
	 * 修改社保类型补缴版本详情
	 *
	 * @param command
	 * @return
	 */
	@RequestMapping(value = "/admin/soin/type/back/version/detail/update", method = RequestMethod.POST)
	public
	@ResponseBody
	HttpResponse updateSoinTypeBackVersionDetail(@RequestBody UpdateSoinTypeVersionDetailCommand command) {
		try {
			baseInfoService.updateSoinTypeVersionDetail(command);
			return new HttpResponse();
		} catch (AryaServiceException e) {
			return new HttpResponse(e.getErrorCode());
		}
	}


}
