package com.bumu.arya.admin.soin.service.impl;

import com.bumu.arya.Utils;
import com.bumu.common.result.JQDatatablesPaginationResult;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.soin.controller.command.*;
import com.bumu.arya.admin.soin.model.entity.SoinBaseInfoExportModel;
import com.bumu.arya.admin.soin.model.SoinTypeMinMaxBaseModel;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.service.*;
import com.bumu.arya.admin.soin.result.*;
import com.bumu.arya.admin.soin.service.ResponseService;
import com.bumu.arya.admin.soin.service.SoinBaseInfoService;
import com.bumu.arya.admin.soin.service.SoinOrderBillService;
import com.bumu.arya.admin.system.service.SystemRoleService;
import com.bumu.arya.common.Constants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.soin.constant.SoinOrderBillConstants;
import com.bumu.arya.soin.model.dao.AryaSoinDistrictDao;
import com.bumu.arya.soin.model.dao.AryaSoinTypeDao;
import com.bumu.arya.soin.model.dao.SoinRuleDao;
import com.bumu.arya.soin.model.dao.SoinTypeVersionDao;
import com.bumu.arya.soin.model.entity.AryaSoinDistrictEntity;
import com.bumu.arya.soin.model.entity.SoinRuleEntity;
import com.bumu.arya.soin.model.entity.SoinTypeEntity;
import com.bumu.arya.soin.model.entity.SoinTypeVersionEntity;
import com.bumu.arya.soin.model.SoinCalculateParams;
import com.bumu.arya.soin.service.SoinDistrictCommonService;
import com.bumu.arya.soin.service.SoinRuleService;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.common.util.ValidateUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.soin.model.SoinRuleCalculateModel;
import com.bumu.arya.soin.model.SoinTypeCalculateModel;
import com.bumu.common.service.impl.BaseBumuService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

import static com.bumu.arya.admin.soin.result.SoinTypeVersionsResult.SoinTypeVersion;
import static com.bumu.arya.admin.soin.util.SoinUtil.*;
import static com.bumu.arya.common.Constants.*;
import static com.bumu.arya.common.OperateConstants.*;
import static com.bumu.arya.exception.ErrorCode.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;

/**
 * @author CuiMengxin
 * @date 2015/12/29
 */
@Service
public class SoinBaseInfoServiceImpl extends BaseBumuService implements SoinBaseInfoService {

	Logger log = LoggerFactory.getLogger(SoinBaseInfoServiceImpl.class);

	@Autowired
	AryaSoinTypeDao soinTypeDao;

	@Autowired
	SoinTypeVersionDao versionDao;

	@Autowired
	SoinRuleDao ruleDao;

	@Autowired
	SoinDistrictCommonService soinDistrictCommonService;

	@Autowired
    OpLogService opLogService;

	@Autowired
	SysUserService sysUserService;

	@Autowired
    SystemRoleService systemRoleService;

	@Autowired
    ResponseService responseService;

	@Autowired
	private ExcelExportHelper excelExportHelper;

	@Autowired
    AryaAdminConfigService aryaAdminConfigService;

	@Autowired
	SoinRuleService soinRuleService;

	@Autowired
    SoinOrderBillService soinOrderBillService;

	@Autowired
	AryaSoinDistrictDao aryaSoinDistrictDao;


	@Override

	public JQDatatablesPaginationResult getDistrictAllSoinType(String districtId) {
		JQDatatablesPaginationResult tableResult = new JQDatatablesPaginationResult();
		SoinTypeListResult listResult = new SoinTypeListResult();
		Collection<String> districtIds = soinDistrictCommonService.getAllChildSoinDistrictList(districtId);//从已开通社保地区中查出该地区下的所有子地区
		List<SoinTypeEntity> soinTypeEntities;
		if (Constants.CHN_ID.equals(districtId)) {
			soinTypeEntities = soinTypeDao.findAllNotDeleteSoinTypeOrderByDistrictASC();//查出所有社保类型
		} else {
			soinTypeEntities = soinTypeDao.findNotDeleteSoinTypeByDistricts(districtIds);//查出所有社保类型
		}
		for (SoinTypeEntity typeEntity : soinTypeEntities) {//组装返回对象
			SoinTypeListResult.SoinTypeResult result = new SoinTypeListResult.SoinTypeResult();
			result.setTypeId(typeEntity.getId());
			result.setTypeName(typeEntity.getTypeName());
			result.setTypeDesc(typeEntity.getTypeDesc());
			listResult.add(result);
		}

		tableResult.setRecordsTotal(listResult.size());
		tableResult.setRecordsFiltered(listResult.size());
		tableResult.setData(listResult);

		return tableResult;
	}

	@Override
	public SoinTypeDetailResult getSoinTypeDetail(String typeId) {
		SoinTypeDetailResult result = new SoinTypeDetailResult();
		SoinTypeEntity typeEntity = soinTypeDao.find(typeId);
		if (typeEntity != null) {
			result.setId(typeEntity.getId());
			result.setFees(typeEntity.getFees());
			result.setForwardMonth(typeEntity.getForwardMonth());
			result.setHouseFundMust(typeEntity.getHouseFundMust());
			result.setLastDay(typeEntity.getLastDay());
			result.setLeastMonth(typeEntity.getLeastMonth());
			result.setMostMonth(typeEntity.getMostMonth());
			result.setTypeDesc(typeEntity.getTypeDesc());
			result.setTypeHint(typeEntity.getTypeHint());
			result.setTypeName(typeEntity.getTypeName());
			if (typeEntity.getDisable() == null || typeEntity.getDisable() == false) {
				result.setActive(1);
			} else {
				result.setActive(0);
			}
		}
		return result;
	}

	@Override
	public void updateSoinTypeDetail(UpdateSoinTypeDetailCommand command) throws AryaServiceException {
		SoinTypeEntity typeEntity = soinTypeDao.findNotDeleteSoinTypeById(command.getId());

		if (command.getActive() == 1) {
			typeEntity.setDisable(false);
		} else {
			typeEntity.setDisable(true);
		}
		if (command.getFees() == null) {
			command.setFees(BigDecimal.ZERO);
		}

		if (command.getFees().compareTo(BigDecimal.ZERO) < 0) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_FEE_ILLEGAL);
		}
		if (command.getLastDay() < 0 || command.getLastDay() > 31) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_LAST_DAY_ILLEGAL);
		}
		if (command.getForwardMonth() < 0 || command.getForwardMonth() > 11) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_FORWARD_MONTH_ILLEGAL);
		}
		if (command.getLeastMonth() < 0) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_LEAST_MONTH_ILLEGAL);
		}
		if (command.getMostMonth() < 0) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_MOST_MONTH_ILLEGAL);
		}
		if (command.getMostMonth() < command.getLeastMonth()) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_MOST_MONTH_MUST_GE_LEAST_MONTH);
		}
		typeEntity.setLastDay(command.getLastDay());
		typeEntity.setForwardMonth(command.getForwardMonth());
		typeEntity.setHouseFundMust(command.getHouseFundMust());
		typeEntity.setLeastMonth(command.getLeastMonth());
		typeEntity.setTypeDesc(command.getTypeDesc());
		typeEntity.setMostMonth(command.getMostMonth());
		typeEntity.setTypeHint(command.getTypeHint());
		typeEntity.setTypeName(command.getTypeName());
		typeEntity.setFees(command.getFees());
		try {
			soinTypeDao.update(typeEntity);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SOIN_UPDATE_SOIN_TYPE_FAILED);
		}
	}

	@Override
	public SoinTypeVersion createNewSoinTypeVersion(CreateSoinTypeVersionCommand command) throws AryaServiceException {
		SoinTypeVersion result = new SoinTypeVersion();
		SoinTypeVersionEntity versionEntity = new SoinTypeVersionEntity();
		versionEntity.setId(Utils.makeUUID());
		versionEntity.setCreateTime(System.currentTimeMillis());
		versionEntity.setBaseAccordant(command.getBaseAccordant());
		if (command.getEffectYear() == null || command.getEffectYear() < 2000 || command.getEffectYear() > 9999) {
			throw new AryaServiceException(CODE_SOIN_TYPE_VERSION_YEAR_ILLEGAL);
		}
		versionEntity.setEffectYear(command.getEffectYear());
		if (command.getEffectMonth() == null || command.getEffectMonth() < 1 || command.getEffectMonth() > 12) {
			throw new AryaServiceException(CODE_SOIN_TYPE_VERSION_MONTH_ILLEGAL);
		}
		checkSoinTypeVersionConflict(command.getTypeId(), command.getEffectYear(), command.getEffectMonth(), command.getVersionType());
		versionEntity.setEffectMonth(command.getEffectMonth());
		versionEntity.setSoinTypeId(command.getTypeId());
		versionEntity.setVersionType(command.getVersionType());
		versionEntity.setDisable(true);//默认禁用
		SoinTypeVersionEntity copyVersionEntity = versionDao.findLatestEffectVersion(command.getTypeId(), false, command.getVersionType());
		if (copyVersionEntity == null && command.getVersionType() == SOIN_VERSION_TYPE_BACK) {
			copyVersionEntity = versionDao.findLatestEffectVersion(command.getTypeId(), false, SOIN_VERSION_TYPE_NORMAL);
		}
		if (copyVersionEntity != null) {
			try {
				versionEntity.setRuleMedical(copySoinRuleEntity(copyVersionEntity.getRuleMedical()));
				versionEntity.setRulePension(copySoinRuleEntity(copyVersionEntity.getRulePension()));
				versionEntity.setRulePregnancy(copySoinRuleEntity(copyVersionEntity.getRulePregnancy()));
				versionEntity.setRuleUnemployment(copySoinRuleEntity(copyVersionEntity.getRuleUnemployment()));
				versionEntity.setRuleInjury(copySoinRuleEntity(copyVersionEntity.getRuleInjury()));

				versionEntity.setRuleDisability(copySoinRuleEntity(copyVersionEntity.getRuleDisability()));
				versionEntity.setRuleSevereIllness(copySoinRuleEntity(copyVersionEntity.getRuleSevereIllness()));
				versionEntity.setRuleInjuryAddition(copySoinRuleEntity(copyVersionEntity.getRuleInjuryAddition()));

				versionEntity.setRuleHouseFund(copySoinRuleEntity(copyVersionEntity.getRuleHouseFund()));
				versionEntity.setRuleHouseFundAddition(copySoinRuleEntity(copyVersionEntity.getRuleHouseFundAddition()));
				versionEntity.setRuleHeating(copySoinRuleEntity(copyVersionEntity.getRuleHeating()));
			} catch (Exception e) {
				throw new AryaServiceException(ErrorCode.CODE_SOIN_RULE_COPY_ERROR);
			}
		}


		try {
			versionDao.create(versionEntity);
			result.setId(versionEntity.getId());
			result.setEffectYear(versionEntity.getEffectYear());
			result.setEffectMonth(versionEntity.getEffectMonth());
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public SoinRuleEntity copySoinRuleEntity(SoinRuleEntity ruleEntity) {
		if (ruleEntity == null) {
			return null;
		}
		SoinRuleEntity newRuleEntity = null;
		try {
			newRuleEntity = (SoinRuleEntity) BeanUtils.cloneBean(ruleEntity);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
		}
		newRuleEntity.setId(Utils.makeUUID());
		newRuleEntity.setCreateTime(System.currentTimeMillis());
		return newRuleEntity;
	}

	@Override
	public SoinTypeVersionsResult getSoinTypeVersion(String typeId, Integer versionType) {
		SoinTypeVersionsResult result = new SoinTypeVersionsResult();
		List<SoinTypeVersionEntity> versionEntities = versionDao.findAllVersionsBySoinTypeId(typeId, versionType, null);
		for (SoinTypeVersionEntity versionEntity : versionEntities) {
			SoinTypeVersion typeVersion = new SoinTypeVersion();
			typeVersion.setId(versionEntity.getId());
			typeVersion.setEffectYear(versionEntity.getEffectYear());
			typeVersion.setEffectMonth(versionEntity.getEffectMonth());
			result.add(typeVersion);
		}
		return result;
	}

	@Override
	public SoinTypeVersionDetailResut getSoinTypeVersionDetail(String versionId) {
		SoinTypeVersionDetailResut result = new SoinTypeVersionDetailResut();
		SoinTypeVersionEntity versionEntity = versionDao.find(versionId);
		if (versionEntity != null) {
			result.setId(versionEntity.getId());
			result.setSoinTypeId(versionEntity.getSoinTypeId());
			result.setEffectYear(versionEntity.getEffectYear());
			result.setEffectMonth(versionEntity.getEffectMonth());
			result.setBaseAccordant(versionEntity.getBaseAccordant());
			result.setAtLeast(versionEntity.getAtLeast());
			result.setAtMost(versionEntity.getAtMost());
			result.setLateFee(versionEntity.getLateFee());
			result.setCrossYear(versionEntity.getCrossYear());
			if (versionEntity.getDisable() == null || versionEntity.getDisable() == false) {
				result.setActive(1);
			} else {
				result.setActive(0);
			}

			if (versionEntity.getRuleInjury() != null) {
				result.setRuleInjury(getVersionDetailRule(versionEntity.getRuleInjury()));
			}

			if (versionEntity.getRuleInjuryAddition() != null) {
				result.setRuleInjuryAddition(getVersionDetailRule(versionEntity.getRuleInjuryAddition()));
			}

			if (versionEntity.getRuleMedical() != null) {
				result.setRuleMedical(getVersionDetailRule(versionEntity.getRuleMedical()));
			}

			if (versionEntity.getRulePension() != null) {
				result.setRulePension(getVersionDetailRule(versionEntity.getRulePension()));
			}

			if (versionEntity.getRulePregnancy() != null) {
				result.setRulePregnancy(getVersionDetailRule(versionEntity.getRulePregnancy()));
			}

			if (versionEntity.getRuleUnemployment() != null) {
				result.setRuleUnemployment(getVersionDetailRule(versionEntity.getRuleUnemployment()));
			}

			if (versionEntity.getRuleDisability() != null) {
				result.setRuleDisability(getVersionDetailRule(versionEntity.getRuleDisability()));
			}

			if (versionEntity.getRuleSevereIllness() != null) {
				result.setRuleSevereIllness(getVersionDetailRule(versionEntity.getRuleSevereIllness()));
			}

			if (versionEntity.getRuleHouseFund() != null) {
				result.setRuleHouseFund(getVersionDetailRule(versionEntity.getRuleHouseFund()));
			}

			if (versionEntity.getRuleHouseFundAddition() != null) {
				result.setRuleHouseFundAddition(getVersionDetailRule(versionEntity.getRuleHouseFundAddition()));
			}

			if (versionEntity.getRuleHeating() != null) {
				result.setRuleHeating(getVersionDetailRule(versionEntity.getRuleHeating()));
			}

		}
		return result;
	}

	@Override
	public void updateSoinTypeVersionDetail(UpdateSoinTypeVersionDetailCommand command) {
		SoinTypeVersionEntity versionEntity = versionDao.find(command.getId());
		try {
			if (command.getActive() == Constants.TRUE) {
				versionEntity.setDisable(false);
			} else {
				versionEntity.setDisable(true);
			}

			//补缴特殊处理
			if (versionEntity.getVersionType() != null && versionEntity.getVersionType() == SOIN_VERSION_TYPE_BACK) {
				//判断至多至少补缴月数是否合法
				if (command.getAtLeast() == null) {
					command.setAtLeast(0);
				}
				if (command.getAtMost() == null) {
					command.setAtMost(0);
				}
				if (command.getAtLeast() < 0) {
					throw new AryaServiceException(ErrorCode.CODE_SOIN_BACK_AT_LEAST_ILLEGAL);
				}

				if (command.getAtMost() < 0) {
					throw new AryaServiceException(ErrorCode.CODE_SOIN_BACK_AT_MOST_ILLEGAL);
				}

				if (command.getAtMost() < command.getAtLeast()) {
					throw new AryaServiceException(ErrorCode.CODE_SOIN_BACK_AT_MOST_MUST_GE_AT_LEAST);
				}

				versionEntity.setAtLeast(command.getAtLeast());
				versionEntity.setAtMost(command.getAtMost());

				if (command.getLateFee() == null) {
					command.setLateFee(BigDecimal.ZERO);
				}

				//验证滞纳金是否合法
				if (command.getLateFee().compareTo(BigDecimal.ZERO) < 0) {
					throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_VERSION_LATE_FEE_ILLEGAL);
				}
				versionEntity.setLateFee(command.getLateFee());
				versionEntity.setCrossYear(command.getCrossYear());
			}

			if (command.getEffectYear() == null || command.getEffectYear() < 2000) {
				throw new AryaServiceException(CODE_SOIN_TYPE_VERSION_YEAR_ILLEGAL);
			}
			if (command.getEffectMonth() == null || command.getEffectMonth() < 1 || command.getEffectMonth() > 12) {
				throw new AryaServiceException(CODE_SOIN_TYPE_VERSION_MONTH_ILLEGAL);
			}

			versionEntity.setEffectYear(command.getEffectYear());
			versionEntity.setEffectMonth(command.getEffectMonth());
			versionEntity.setBaseAccordant(command.getBaseAccordant());
			versionEntity.setRulePension(updateOrCreateSoinRule(versionEntity.getRulePension(), command.getRulePension(), "养老"));
			versionEntity.setRuleMedical(updateOrCreateSoinRule(versionEntity.getRuleMedical(), command.getRuleMedical(), "医疗"));
			versionEntity.setRuleInjury(updateOrCreateSoinRule(versionEntity.getRuleInjury(), command.getRuleInjury(), "工伤"));
			versionEntity.setRulePregnancy(updateOrCreateSoinRule(versionEntity.getRulePregnancy(), command.getRulePregnancy(), "生育"));
			versionEntity.setRuleUnemployment(updateOrCreateSoinRule(versionEntity.getRuleUnemployment(), command.getRuleUnemployment(), "失业"));
			versionEntity.setRuleHouseFund(updateOrCreateSoinRule(versionEntity.getRuleHouseFund(), command.getRuleHouseFund(), "公积金"));
			versionEntity.setRuleHouseFundAddition(updateOrCreateSoinRule(versionEntity.getRuleHouseFundAddition(), command.getRuleHouseFundAddition(), "补充公积金"));
			versionEntity.setRuleSevereIllness(updateOrCreateSoinRule(versionEntity.getRuleSevereIllness(), command.getRuleSevereIllness(), "大病医疗"));
			versionEntity.setRuleDisability(updateOrCreateSoinRule(versionEntity.getRuleDisability(), command.getRuleDisability(), "残保"));
			versionEntity.setRuleInjuryAddition(updateOrCreateSoinRule(versionEntity.getRuleInjuryAddition(), command.getRuleInjuryAddition(), "工伤补充"));
			versionEntity.setRuleHeating(updateOrCreateSoinRule(versionEntity.getRuleHeating(), command.getRuleHeating(), "采暖费"));
		} catch (AryaServiceException e) {
			throw e;
		}

		try {
			versionDao.update(versionEntity);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(CODE_SOIN_SAVE_SOIN_TYPE_VERSION_FAILED);
		}
	}

	@Override
	public void upSoinDistrict(CreateSoinDistrictCommand command) {
		AryaSoinDistrictEntity soinDistrictEntity = aryaSoinDistrictDao.findDistrictById(command.getDistrictId());
		if (soinDistrictEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_QUERY_NO_DISTRICT);
		}
		if (soinDistrictEntity.getId().endsWith("00")) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_DISTRICT_ONLY_COUNTY_CAN_UP_SUPER);
		}
		soinDistrictEntity.setUpSuper(Constants.TRUE);
		aryaSoinDistrictDao.update(soinDistrictEntity);
	}

	@Override
	public void cancelUpSoinDistrict(CreateSoinDistrictCommand command) {
		AryaSoinDistrictEntity soinDistrictEntity = aryaSoinDistrictDao.findDistrictById(command.getDistrictId());
		if (soinDistrictEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_QUERY_NO_DISTRICT);
		}
		if (soinDistrictEntity.getId().endsWith("00")) {
			throw new AryaServiceException(ErrorCode.CODE_SOIN_DISTRICT_ONLY_COUNTY_CAN_UP_SUPER);
		}
		soinDistrictEntity.setUpSuper(Constants.FALSE);
		aryaSoinDistrictDao.update(soinDistrictEntity);
	}

	@Override
	public void disablesSoinType(String typeId) throws AryaServiceException {
		SoinTypeEntity typeEntity = soinTypeDao.findNotDeleteSoinTypeById(typeId);
		typeEntity.setDisable(true);
		StringBuffer logMsg = new StringBuffer("【社保基础数据】禁用社保类型id:" + typeId);
		try {
			soinTypeDao.update(typeEntity);
			opLogService.successLog(SOIN_TYPE_DISABLE, logMsg, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SOIN_TYPE_DISABLE, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_DISABLE_FIALED);
		}
	}

	@Override
	public void enableSoinType(String typeId) throws AryaServiceException {
		SoinTypeEntity typeEntity = soinTypeDao.findNotDeleteSoinTypeById(typeId);
		typeEntity.setDisable(false);
		StringBuffer logMsg = new StringBuffer("【社保基础数据】启用社保类型id:" + typeId);
		try {
			soinTypeDao.update(typeEntity);
			opLogService.successLog(SOIN_TYPE_ENABLE, logMsg, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SOIN_TYPE_ENABLE, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_ENABLE_FIALED);
		}
	}

	@Override
	public void disableSoinTypeVersion(String versionId) throws AryaServiceException {
		SoinTypeVersionEntity typeVersion = versionDao.findVersionByIdThrow(versionId, null);
		StringBuffer logMsg = new StringBuffer(String.format("【社保基础数据】禁用社保类型id:%s的社保类型版本。版本id:%s。", typeVersion.getSoinTypeId(), typeVersion.getSoinTypeId()));
		try {
			typeVersion.setDisable(true);
			versionDao.update(typeVersion);
			opLogService.successLog(SOIN_TYPE_VERSION_DISABLE, logMsg, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SOIN_TYPE_VERSION_DISABLE, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_VERSION_DISABLE_FIALED);
		}
	}

	@Override
	public void enbableSoinTypeVersion(String versionId) throws AryaServiceException {
		SoinTypeVersionEntity typeVersion = versionDao.findVersionByIdThrow(versionId, null);
		StringBuffer logMsg = new StringBuffer(String.format("【社保基础数据】启用社保类型id:%s的社保类型版本。版本id:%s。", typeVersion.getSoinTypeId(), typeVersion.getSoinTypeId()));
		try {
			typeVersion.setDisable(false);
			versionDao.update(typeVersion);
			opLogService.successLog(SOIN_TYPE_VERSION_ENABLE, logMsg, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SOIN_TYPE_VERSION_ENABLE, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_VERSION_ENABLE_FIALED);
		}
	}

	@Override
	public SoinBaseInfoChangeAuthResult checkUserHasChangeAuth() throws AryaServiceException {
		SoinBaseInfoChangeAuthResult authResult = new SoinBaseInfoChangeAuthResult();
		SysUserModel currentUser = sysUserService.getCurrentSysUser();
		currentUser.getId();
		if (systemRoleService.isSalesmanRole(currentUser.getId())) {
			//销售没有修改权限
			authResult.setHasAuth(com.bumu.bran.common.Constants.FALSE);
		} else {
			//其他角色有
			authResult.setHasAuth(com.bumu.bran.common.Constants.TRUE);
		}
		return authResult;
	}

	@Override
	public void exportDistrictSoinBaseDetail(IdStrListCommand command, HttpServletResponse response) {
		if (command.getIds() == null || command.getIds().isEmpty()) {
			responseService.writeErrorCodeToResponse(response, ErrorCode.CODE_SOIN_BASE_EXPORT_DISTRICT_ID_EMPTY);
		}
		Calendar cal = Calendar.getInstance();//使用日历类
		int year = cal.get(Calendar.YEAR);//得到年
		int month = cal.get(Calendar.MONTH) + 1;
		//查出所有启用的社保类型
		List<SoinTypeEntity> soinTypeEntities = soinTypeDao.findEnableSoinTypeByDistricts(command.getIds());
		if (soinTypeEntities == null || soinTypeEntities.isEmpty()) {
			responseService.writeErrorCodeToResponse(response, ErrorCode.CODE_SOIN_BASE_EXPORT_SOIN_TYPE_FIND_NOTHING);
		}
		//查出所有启用的并且当前年月有效的社保类型版本
		List<SoinTypeVersionEntity> soinTypeVersionEntities = versionDao.findAllEffectedVersionsBySoinTypes(soinTypeEntities, year, month, false);
		if (soinTypeVersionEntities == null || soinTypeVersionEntities.isEmpty()) {
			responseService.writeErrorCodeToResponse(response, ErrorCode.CODE_SOIN_BASE_EXPORT_SOIN_TYPE_VERSION_FIND_NOTHING);
		}
		List<SoinBaseInfoExportModel> soinBaseInfoExportModels = new ArrayList<>();
		//组织返回结果
		for (SoinTypeEntity soinTypeEntity : soinTypeEntities) {
			//这一层外层循环是为了按地区归类
			for (int i = 0; i < soinTypeVersionEntities.size(); i++) {
				SoinTypeVersionEntity soinTypeVersionEntity = soinTypeVersionEntities.get(i);
				if (!soinTypeVersionEntity.getSoinTypeId().equals(soinTypeEntity.getId())) {
					continue;
				}
				SoinBaseInfoExportModel soinBaseInfoExportModel = new SoinBaseInfoExportModel();
				try {
					soinBaseInfoExportModel.setName(soinTypeEntity.getTypeName());
					List<String> districts = soinDistrictCommonService.getFullDistrictNameBySubDistrictId(soinTypeEntity.getDistrictId());
					if (districts != null && !districts.isEmpty()) {
						soinBaseInfoExportModel.setSheng(districts.get(0));
						if (districts.size() > 1) {
							soinBaseInfoExportModel.setShi(districts.get(1));
						} else {
							soinBaseInfoExportModel.setShi("-");
						}
						if (districts.size() > 2) {
							soinBaseInfoExportModel.setQu(districts.get(2));
						} else {
							soinBaseInfoExportModel.setQu("-");
						}
					}
					soinBaseInfoExportModel.setEffectTime(soinTypeVersionEntity.getEffectYear() + "年" + soinTypeVersionEntity.getEffectMonth() + "月起生效");
					soinBaseInfoExportModel.setHouseFoundMust(soinTypeEntity.getHouseFundMust() ? "是" : "否");
					soinBaseInfoExportModel.setForwardMonth(soinTypeEntity.getForwardMonth() > 0 ? ("必须提前" + soinTypeEntity.getForwardMonth() + "个月缴纳") : null);
					soinBaseInfoExportModel.setEndDay(soinTypeEntity.getLastDay() > 0 ? ("每月截止" + soinTypeEntity.getLastDay() + "号") : null);
					soinBaseInfoExportModel.setLeastBuyCount(soinTypeEntity.getLeastMonth() > 0 ? ("至少连续购买" + soinTypeEntity.getLeastMonth() + "个月") : null);
					soinBaseInfoExportModel.setSoinLinkWithfoundFound(soinTypeVersionEntity.getBaseAccordant() > 0 ? "是" : "否");
					//取基数
					SoinTypeMinMaxBaseModel minMaxBaseModel = soinOrderBillService.getMinMaxBase(soinTypeVersionEntity);
					BigDecimal houseFundBase = BigDecimal.ZERO;
					if (soinTypeVersionEntity.getRuleHouseFund() != null) {
						houseFundBase = soinTypeVersionEntity.getRuleHouseFund().getMinBase();
					}
					//计算
					SoinTypeCalculateModel soinTypeCalculateModel = soinRuleService.soinCalculate(new SoinCalculateParams(soinTypeEntity.getHouseFundMust(), soinTypeVersionEntity, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, minMaxBaseModel.getMinBase(), houseFundBase, null, null, year, month, 2015, 1, new BigDecimal("1"), null, null, null, SoinOrderBillConstants.SOIN_NORMAL_CALCULATE));
					{
						//八险两金
						soinBaseInfoExportModel.setPension(generateRuleDetail2Export(soinTypeVersionEntity.getRulePension(), soinTypeCalculateModel.getPension()));
						soinBaseInfoExportModel.setUnemployment(generateRuleDetail2Export(soinTypeVersionEntity.getRuleUnemployment(), soinTypeCalculateModel.getUnemployment()));
						soinBaseInfoExportModel.setMedical(generateRuleDetail2Export(soinTypeVersionEntity.getRuleMedical(), soinTypeCalculateModel.getMedical()));
						soinBaseInfoExportModel.setInjury(generateRuleDetail2Export(soinTypeVersionEntity.getRuleInjury(), soinTypeCalculateModel.getInjury()));
						soinBaseInfoExportModel.setPregnancy(generateRuleDetail2Export(soinTypeVersionEntity.getRulePregnancy(), soinTypeCalculateModel.getPregnancy()));
						soinBaseInfoExportModel.setHouseFund(generateRuleDetail2Export(soinTypeVersionEntity.getRuleHouseFund(), soinTypeCalculateModel.getHousefund()));
						soinBaseInfoExportModel.setSevereIllness(generateRuleDetail2Export(soinTypeVersionEntity.getRuleSevereIllness(), soinTypeCalculateModel.getServerIllness()));
						soinBaseInfoExportModel.setDisability(generateRuleDetail2Export(soinTypeVersionEntity.getRuleDisability(), soinTypeCalculateModel.getDisability()));
						soinBaseInfoExportModel.setInjuryAddition(generateRuleDetail2Export(soinTypeVersionEntity.getRuleInjuryAddition(), soinTypeCalculateModel.getInjuryAddition()));
						soinBaseInfoExportModel.setHouseFundAddition(generateRuleDetail2Export(soinTypeVersionEntity.getRuleHouseFundAddition(), soinTypeCalculateModel.getHousefundAddition()));

						soinBaseInfoExportModel.setHouseFundTotal(soinTypeCalculateModel.getHouseFundOnceTotalPayment(2));
						soinBaseInfoExportModel.setTotal(soinTypeCalculateModel.getTotalPayment());

						soinBaseInfoExportModel.setCorpSoinTotal(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP));
						soinBaseInfoExportModel.setPersonSoinTotal(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_UP));
						countSoinTotal(soinBaseInfoExportModel.getPension(), soinBaseInfoExportModel);
						countSoinTotal(soinBaseInfoExportModel.getMedical(), soinBaseInfoExportModel);
						countSoinTotal(soinBaseInfoExportModel.getUnemployment(), soinBaseInfoExportModel);
						countSoinTotal(soinBaseInfoExportModel.getPregnancy(), soinBaseInfoExportModel);
						countSoinTotal(soinBaseInfoExportModel.getInjury(), soinBaseInfoExportModel);
						countSoinTotal(soinBaseInfoExportModel.getInjuryAddition(), soinBaseInfoExportModel);
						countSoinTotal(soinBaseInfoExportModel.getDisability(), soinBaseInfoExportModel);
						countSoinTotal(soinBaseInfoExportModel.getSevereIllness(), soinBaseInfoExportModel);
						countSoinTotal(soinBaseInfoExportModel.getHouseFundAddition(), soinBaseInfoExportModel);

						soinBaseInfoExportModel.setSoinTotal(soinBaseInfoExportModel.getCorpSoinTotal().add(soinBaseInfoExportModel.getPersonSoinTotal()));
					}
					//使用过后移除掉版本
					soinTypeVersionEntities.remove(soinTypeVersionEntity);
					i--;
				} catch (Exception e) {
					elog.error(e.getMessage(), e);
					//如果有错则继续
					continue;
				}
				soinBaseInfoExportModels.add(soinBaseInfoExportModel);
			}
		}

		excelExportHelper.export(
				aryaAdminConfigService.getExportTemplatePath() + AryaAdminConfigService.SOIN_BASE_EXPORT,
				"地区社保规则详情",
				new HashedMap() {{
					put("list", soinBaseInfoExportModels);
				}},
				response
		);
	}

	public void checkSoinTypeVersionConflict(String soinTypeId, int effectYear, int effectMonth, int versionType) {
		SoinTypeVersionEntity soinTypeVersionEntity = versionDao.findDefiniteSoinTypeVersionByYearMonth(soinTypeId, effectYear, effectMonth, versionType);
		if (soinTypeVersionEntity != null) {
			if (versionType == SOIN_VERSION_TYPE_NORMAL) {
				throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_NORMAL_VERSION_EXIST);
			} else {
				throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_BACK_VERSION_EXIST);
			}
		}
	}


	/**
	 * 获取地区的社保类型，如苏州只获取苏州上的社保类型不包括工业园区
	 *
	 * @param districtId
	 * @return
	 */
	@Override
	public DistrictSoinTypesResult soinDistrictType(String districtId) throws AryaServiceException {
		DistrictSoinTypesResult result = new DistrictSoinTypesResult();
		List<SoinTypeEntity> typeEntities = soinTypeDao.findNotDeleteSoinTypeByDistrict(districtId);
		for (SoinTypeEntity typeEntity : typeEntities) {
			DistrictSoinTypesResult.DistrictSoinType type = new DistrictSoinTypesResult.DistrictSoinType();
			type.setId(typeEntity.getId());
			type.setText(typeEntity.getTypeName());
			if (typeEntity.getDisable() == null) {
				type.setDisable(false);
			} else {
				type.setDisable(typeEntity.getDisable());
			}
			result.add(type);
		}
		return result;
	}

	/**
	 * 生成险种导出数据
	 *
	 * @param ruleEntity
	 */
	SoinBaseInfoExportModel.RuleDetail generateRuleDetail2Export(SoinRuleEntity ruleEntity, SoinRuleCalculateModel soinRuleCalculateModel) {
		SoinBaseInfoExportModel.RuleDetail ruleDetail = new SoinBaseInfoExportModel.RuleDetail();
		if (ruleEntity == null) {
			return ruleDetail;
		}
		ruleDetail.setName(ruleEntity.getRuleName());
		if (StringUtils.isNotBlank(ruleEntity.getPercentageCorp())) {
			ruleDetail.setCorpPer(ruleEntity.getPercentageCorp() + "%");
		}
		if (ruleEntity.getExtraCorp() != null && ruleEntity.getExtraCorp().compareTo(BigDecimal.ZERO) > 0) {
			if (StringUtils.isNotBlank(ruleDetail.getCorpPer())) {
				ruleDetail.setCorpPer(ruleDetail.getCorpPer() + "+" + ruleEntity.getExtraCorp() + "元");
			} else {
				ruleDetail.setCorpPer(ruleEntity.getExtraCorp() + "元");
			}
		}

		if (StringUtils.isNotBlank(ruleEntity.getPercentagePerson())) {
			ruleDetail.setPersonPer(ruleEntity.getPercentagePerson() + "%");
		}
		if (ruleEntity.getExtraPerson() != null && ruleEntity.getExtraPerson().compareTo(BigDecimal.ZERO) > 0) {
			if (StringUtils.isNotBlank(ruleDetail.getPersonPer())) {
				ruleDetail.setPersonPer(ruleDetail.getPersonPer() + "+" + ruleEntity.getExtraPerson() + "元");
			} else {
				ruleDetail.setPersonPer(ruleEntity.getExtraPerson() + "元");
			}
		}

		ruleDetail.setBase(ruleEntity.getMinBase());
		ruleDetail.setCorpPay(soinRuleCalculateModel.getTotalCorpPayment());
		ruleDetail.setPersonPay(soinRuleCalculateModel.getTotalPersonPayment());

//		if (ruleEntity.getPayMonth() != null && ruleEntity.getPayMonth() > 0) {
//			ruleDetail.setMemo("一年一缴,从每年" + ruleEntity.getPayMonth() + "月开始。");
//		} else {
//			ruleDetail.setMemo("按月缴纳");
//		}
		return ruleDetail;
	}

	/**
	 * 统计社保公司和个人金额总计
	 *
	 * @param ruleDetail
	 * @param soinBaseInfoExportModel
	 */
	void countSoinTotal(SoinBaseInfoExportModel.RuleDetail ruleDetail, SoinBaseInfoExportModel soinBaseInfoExportModel) {
		if (ruleDetail != null && ruleDetail.getCorpPay() != null) {
			soinBaseInfoExportModel.setCorpSoinTotal(soinBaseInfoExportModel.getCorpSoinTotal().add(ruleDetail.getCorpPay()));
		}

		if (ruleDetail != null && ruleDetail.getPersonPay() != null) {
			soinBaseInfoExportModel.setPersonSoinTotal(soinBaseInfoExportModel.getPersonSoinTotal().add(ruleDetail.getPersonPay()));
		}
	}

	/**
	 * 修改或创建新的险种
	 *
	 * @param ruleEntity
	 * @param ruleModel
	 * @return
	 */
	private SoinRuleEntity updateOrCreateSoinRule(SoinRuleEntity ruleEntity, SoinTypeVersionRuleResult ruleModel, String ruleName) {
		if (ruleModel != null) {
			if (ruleModel.getPayMonth() < 0 || ruleModel.getPayMonth() > 12) {
				throw new AryaServiceException(ErrorCode.CODE_SOIN_RULE_PAY_TIME_ILLEGAL);
			}
			if (ruleModel.getMinBase() == null || ruleModel.getMaxBase() == null) {
				throw new AryaServiceException(ErrorCode.CODE_SOIN_TYPE_VERSION_BASE_ILLEGAL);
			}
			if (StringUtils.isNotBlank(ruleModel.getPercentagePerson()) && ValidateUtils.isNumber(ruleModel.getPercentagePerson())) {
				validateRulePercent(ruleName, ruleModel.getPercentagePerson(), false);
			}
			if (StringUtils.isNotBlank(ruleModel.getPercentageCorp()) && ValidateUtils.isNumber(ruleModel.getPercentageCorp())) {
				validateRulePercent(ruleName, ruleModel.getPercentageCorp(), true);
			}
			ruleModel.setExtraPerson(validateRuleExtra(ruleName, ruleModel.getExtraPerson(), false));
			ruleModel.setExtraCorp(validateRuleExtra(ruleName, ruleModel.getExtraCorp(), true));
			ruleModel.setMinBase(validateRuleBae(ruleName, ruleModel.getMinBase()));
			ruleModel.setMaxBase(validateRuleBae(ruleName, ruleModel.getMaxBase()));
		}

		//如果规则不存在并且客户端传来规则，则新建
		if (ruleEntity == null && ruleModel != null) {
			SoinRuleEntity newRuleEntity = new SoinRuleEntity();
			newRuleEntity.setId(Utils.makeUUID());
			newRuleEntity.setCreateTime(System.currentTimeMillis());
			newRuleEntity.setLimitMonth(0);
			if (!StringUtils.isAnyBlank(ruleName)) {
				newRuleEntity.setRuleName(ruleName);
			}
			if (!StringUtils.isAnyBlank(ruleModel.getDesc())) {
				newRuleEntity.setRuleDesc(ruleModel.getDesc());
			}
			newRuleEntity.setPayMonth(ruleModel.getPayMonth());

			if (StringUtils.isNotBlank(ruleModel.getPercentagePerson()) && ValidateUtils.isNumber(ruleModel.getPercentagePerson())) {
				newRuleEntity.setPercentagePerson(ruleModel.getPercentagePerson());
			}
			if (StringUtils.isNotBlank(ruleModel.getPercentageCorp()) && ValidateUtils.isNumber(ruleModel.getPercentageCorp())) {
				newRuleEntity.setPercentageCorp(ruleModel.getPercentageCorp());
			}
			newRuleEntity.setExtraPerson(ruleModel.getExtraPerson());
			newRuleEntity.setExtraCorp(ruleModel.getExtraCorp());
			newRuleEntity.setMinBase(ruleModel.getMinBase());
			newRuleEntity.setMaxBase(ruleModel.getMaxBase());
			ruleDao.create(newRuleEntity);
			return newRuleEntity;
		}
		//如果规则有修改
		if (ruleEntity != null && ruleModel != null) {
			ruleEntity.setRuleName(ruleName);
			ruleEntity.setRuleDesc(ruleModel.getDesc());
			ruleEntity.setPayMonth(ruleModel.getPayMonth());

			if (StringUtils.isNotBlank(ruleModel.getPercentagePerson()) && ValidateUtils.isNumber(ruleModel.getPercentagePerson())) {
				ruleEntity.setPercentagePerson(ruleModel.getPercentagePerson());
			} else {
				ruleEntity.setPercentagePerson(null);
			}

			if (StringUtils.isNotBlank(ruleModel.getPercentageCorp()) && ValidateUtils.isNumber(ruleModel.getPercentageCorp())) {
				ruleEntity.setPercentageCorp(ruleModel.getPercentageCorp());
			} else {
				ruleEntity.setPercentageCorp(null);
			}
			ruleEntity.setExtraPerson(ruleModel.getExtraPerson());
			ruleEntity.setExtraCorp(ruleModel.getExtraCorp());
			ruleEntity.setMinBase(ruleModel.getMinBase());
			ruleEntity.setMaxBase(ruleModel.getMaxBase());
			ruleDao.update(ruleEntity);
			return ruleEntity;
		}
		//如果无规则继续保持无规则
		return null;
	}

	/**
	 * 生成险种的返回对象
	 *
	 * @param ruleEntity
	 * @return
	 */
	private SoinTypeVersionRuleResult getVersionDetailRule(SoinRuleEntity ruleEntity) {
		SoinTypeVersionRuleResult ruleResult = new SoinTypeVersionRuleResult();
		ruleResult.setId(ruleEntity.getId());
		ruleResult.setDesc(ruleEntity.getRuleDesc());
		ruleResult.setName(ruleEntity.getRuleName());
		ruleResult.setLimitMonth(ruleEntity.getLimitMonth());
		ruleResult.setPayMonth(ruleEntity.getPayMonth());
		ruleResult.setMinBase(ruleEntity.getMinBase());
		ruleResult.setMaxBase(ruleEntity.getMaxBase());
		ruleResult.setPercentagePerson(ruleEntity.getPercentagePerson());
		ruleResult.setPercentageCorp(ruleEntity.getPercentageCorp());
		ruleResult.setExtraPerson(ruleEntity.getExtraPerson());
		ruleResult.setExtraCorp(ruleEntity.getExtraCorp());
		return ruleResult;
	}

	@Override
	public DistrictSoinTypesResult createDistrictType(CreateSoinTypeCommand command) throws AryaServiceException {
		if (isNotBlank(command.getDistrictId()) && command.getDistrictId().endsWith("0000")) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "省级地区不能添加社保类型。");
		}
		List<SoinTypeEntity> soinTypeEntities  =  soinTypeDao.findNotDeleteSoinTypeByDistrict(command.getDistrictId());
		for (SoinTypeEntity soinTypeEntity : soinTypeEntities){
			if (soinTypeEntity.getTypeName().equals(trim(command.getName()))){
				throw new AryaServiceException(ErrorCode.CODE_SOIN_BASE_TYPE_REPEAT);
			}
		}
		DistrictSoinTypesResult result = new DistrictSoinTypesResult();
		SoinTypeEntity typeEntity = new SoinTypeEntity();
		typeEntity.setId(Utils.makeUUID());
		typeEntity.setTypeName(command.getName());
		typeEntity.setDistrictId(command.getDistrictId());
		typeEntity.setDisable(true);
		typeEntity.setLeastMonth(1);
		typeEntity.setMostMonth(12);
		typeEntity.setCreateTime(System.currentTimeMillis());
		typeEntity.setDelete(false);
		if (isNotBlank(command.getCopySoinTypeId())) {
			//根据ID查询社保类型
			SoinTypeEntity soinTypeEntity = soinTypeDao.findNotDeleteSoinTypeById(command.getCopySoinTypeId());
			typeEntity.setFees(soinTypeEntity.getFees());
			typeEntity.setForwardMonth(soinTypeEntity.getForwardMonth());
			typeEntity.setLastDay(soinTypeEntity.getLastDay());
			typeEntity.setTypeDesc(soinTypeEntity.getTypeDesc());
			typeEntity.setTypeHint(soinTypeEntity.getTypeHint());
			typeEntity.setHouseFundMust(soinTypeEntity.getHouseFundMust());
			//复制最近社保类型下的社保版本（包括补缴和正常版本）
			CopySoinTypeVersion(soinTypeEntity.getId(), typeEntity.getId());
		}

		DistrictSoinTypesResult.DistrictSoinType type = new DistrictSoinTypesResult.DistrictSoinType();
		type.setId(typeEntity.getId());
		type.setText(typeEntity.getTypeName());
		result.add(type);
		try {
			soinTypeDao.create(typeEntity);
		} catch (Exception e) {
			//异常处理略过，下面会返回异常信息
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 复制社保类型包含的社保版本
	 *
	 * @param copySoinTypeId
	 * @param newSoinTypeId
	 */
	private void CopySoinTypeVersion(String copySoinTypeId, String newSoinTypeId) {
		//查询最近的正常版本
		SoinTypeVersionEntity copyNormalTypeVersionEntity = versionDao.findLatestEffectVersion(copySoinTypeId, null, SOIN_VERSION_TYPE_NORMAL);
		if (copyNormalTypeVersionEntity != null) {
			CreateNewSoinTypeVersion(copyNormalTypeVersionEntity, newSoinTypeId);
		}
		//查询最近的补缴版本
		SoinTypeVersionEntity copyBackTypeVersionEntity = versionDao.findLatestEffectVersion(copySoinTypeId, null, SOIN_VERSION_TYPE_BACK);
		if (copyBackTypeVersionEntity != null) {
			CreateNewSoinTypeVersion(copyBackTypeVersionEntity, newSoinTypeId);
		}
	}

	/**
	 * 创建一个新的社保类型版本
	 *
	 * @param copyVersionEntity
	 * @param newSoinTypeId
	 */
	private void CreateNewSoinTypeVersion(SoinTypeVersionEntity copyVersionEntity, String newSoinTypeId) {
		SoinTypeVersionEntity versionEntity = new SoinTypeVersionEntity();
		versionEntity.setId(Utils.makeUUID());
		versionEntity.setCreateTime(System.currentTimeMillis());
		versionEntity.setBaseAccordant(copyVersionEntity.getBaseAccordant());
		versionEntity.setEffectYear(copyVersionEntity.getEffectYear());
		versionEntity.setEffectMonth(copyVersionEntity.getEffectMonth());
		versionEntity.setSoinTypeId(newSoinTypeId);
		versionEntity.setVersionType(copyVersionEntity.getVersionType());
		versionEntity.setDisable(true);//默认禁用
		versionEntity.setAtLeast(copyVersionEntity.getAtLeast());
		versionEntity.setAtMost(copyVersionEntity.getAtMost());
		versionEntity.setCrossYear(copyVersionEntity.getCrossYear());
		versionEntity.setLateFee(copyVersionEntity.getLateFee());

		versionEntity.setRuleMedical(copySoinRuleEntity(copyVersionEntity.getRuleMedical()));
		versionEntity.setRulePension(copySoinRuleEntity(copyVersionEntity.getRulePension()));
		versionEntity.setRulePregnancy(copySoinRuleEntity(copyVersionEntity.getRulePregnancy()));
		versionEntity.setRuleUnemployment(copySoinRuleEntity(copyVersionEntity.getRuleUnemployment()));
		versionEntity.setRuleInjury(copySoinRuleEntity(copyVersionEntity.getRuleInjury()));

		versionEntity.setRuleDisability(copySoinRuleEntity(copyVersionEntity.getRuleDisability()));
		versionEntity.setRuleSevereIllness(copySoinRuleEntity(copyVersionEntity.getRuleSevereIllness()));
		versionEntity.setRuleInjuryAddition(copySoinRuleEntity(copyVersionEntity.getRuleInjuryAddition()));

		versionEntity.setRuleHouseFund(copySoinRuleEntity(copyVersionEntity.getRuleHouseFund()));
		versionEntity.setRuleHouseFundAddition(copySoinRuleEntity(copyVersionEntity.getRuleHouseFundAddition()));
		versionEntity.setRuleHeating(copySoinRuleEntity(copyVersionEntity.getRuleHeating()));

		versionDao.create(versionEntity);

	}

	@Override
	public void deleteDistrictType(String typeId) throws AryaServiceException {
		SoinTypeEntity entity = soinTypeDao.findNotDeleteSoinTypeById(typeId);
		List<SoinTypeVersionEntity> typeVersionEntities = versionDao.findAllVersionsBySoinTypeId(typeId,0,null);
		if (entity != null) {
			entity.setDelete(true);
			entity.setDisable(true);
		}
		if (typeVersionEntities != null){
			for (SoinTypeVersionEntity versionEntity : typeVersionEntities){
				versionEntity.setDelete(true);
				versionEntity.setDisable(true);
			}
		}
		try {
			soinTypeDao.update(entity);
			versionDao.update(typeVersionEntities);
		} catch (Exception e){
			throw new AryaServiceException(ErrorCode.CODE_SOIN_BASE_TYPE_DELETE_FAILED);
		}
	}

	@Override
	public void deleteSoinTypeVersion(String typeVersionId) throws AryaServiceException{
		SoinTypeVersionEntity typeVersionEntity = versionDao.findVersionByIdThrow(typeVersionId,null);
		if (typeVersionEntity != null){
			typeVersionEntity.setDelete(true);
			typeVersionEntity.setDisable(true);
		}
		try{
			versionDao.update(typeVersionEntity);
		}catch (Exception e){
			throw new AryaServiceException(ErrorCode.CODE_BRAN_IDCARD_UPLOAD_ERROR);
		}
	}
}
