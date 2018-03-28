package com.bumu.bran.admin.user_permission.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.SysLogDao;
import com.bumu.bran.admin.model.CorpPermissionDao;
import com.bumu.bran.admin.model.entity.CorpPermissionEntity;
import com.bumu.bran.admin.model.entity.CorpRoleEntity;
import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.admin.system.command.IdVersion;
import com.bumu.bran.admin.user_permission.result.CorpModelResult;
import com.bumu.bran.admin.user_permission.service.UserPermissionService;
import com.bumu.bran.common.Constants;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.admin.model.dao.BranCorpUserDao;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BaseBranEntity;
import com.bumu.admin.model.entity.BranCorpUserEntity;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.bran.model.entity.BranOpLogEntity;
import com.bumu.common.util.TxVersionUtil;
import com.bumu.common.util.ValidateUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * majun
 */
@Service
public class UserPermissionServiceImp implements UserPermissionService {

	private static Logger logger = LoggerFactory.getLogger(UserPermissionServiceImp.class);

	@Resource
	private BranCorpUserDao branCorpUserDao;

	@Resource
	private BranOpLogDao branOpLogDao;

	@Resource
	private BranCorporationDao branCorporationDao;

	@Resource
	private CorpPermissionDao corpPermissionDao;

	@Override
	public Map<String, Long> addPermissionUser(CorpModel model) throws Exception {

		// 查询当前用户
		BranCorpUserEntity curUser = branCorpUserDao.findCorpUserById(model.getOperateUserId());

		if (curUser == null) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_USER_NOT_FOUND);
		}

		// 判断公司ID
		if (StringUtils.isBlank(model.getBranCorpId())) {
			throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
		}

		// 判断用户名
		if (StringUtils.isBlank(model.getName())) {
			throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
		}
		// 判断用户名是否存在中文
		if (ValidateUtils.vefityIsChinese(model.getName())) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_WORK_SN_PERFIX_CHINESE);
		}

		// 判断密码
		if (StringUtils.isBlank(model.getPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
		}

		// 判断密码复杂度
		if (!ValidateUtils.vefityPassword(model.getPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_VALID_LOGIN_PWD_FORMAT);
		}

		// 判断确认密码
		if (StringUtils.isBlank(model.getConfirmPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
		}

		// 判断两次密码是否一致
		if (!model.getPassword().equals(model.getConfirmPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_PASSWORD_CONFIRM);
		}

		// 查询公司是否存在
		BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(model.getBranCorpId());

		if (branCorporationEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
		}

		// 查询用户是否存在
		if (branCorpUserDao.isCorpUserExist(model.getName())) {
			throw new AryaServiceException(ErrorCode.CODE_CORPORATION_USER_EXIST);
		}

		// 查询权限
		List<CorpPermissionEntity> corpPermissionEntityList = corpPermissionDao.findAllCorpPermsByDefault();

//		if (corpPermissionEntityList == null) {
//			throw new AryaServiceException(ErrorCode.CORE_CORP_IS_NOT_ANY_PERMISSION);
//		}

		// 新建角色
		CorpRoleEntity corpUserRole = new CorpRoleEntity();
		corpUserRole.setId(Utils.makeUUID());
		corpUserRole.setRoleName(Constants.DEFAULT_ROLE_NAME);
		corpUserRole.setRoleDesc(Constants.DEFAULT_ROLE_DESC);
        if (corpPermissionEntityList != null) {
            corpUserRole.setCorpPermissions(new HashSet<>(corpPermissionEntityList));
        }
		Set<CorpRoleEntity> roles = new HashSet<>();
		roles.add(corpUserRole);

		// 新建用户
		BranCorpUserEntity corpUserEntity = new BranCorpUserEntity();
		corpUserEntity.setId(Utils.makeUUID());
		corpUserEntity.setBranCorpId(branCorporationEntity.getId());
		corpUserEntity.setLoginName(model.getName());
		corpUserEntity.setCreateTime(System.currentTimeMillis());
		corpUserEntity.setCreateUser(model.getOperateUserId());
		corpUserEntity.setIsDelete(0);
		corpUserEntity.setLoginPwd(SysUtils.encryptPassword(model.getPassword()));
		corpUserEntity.setCorpRoles(roles);
		corpUserEntity.setIsAdmin(0);
		branCorpUserDao.create(corpUserEntity);

		// 写日志
		StringBuilder sb = new StringBuilder();
		sb.append("添加了子账号:" + model.getName());

		SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
		sysLogExtInfo.setMsg(sb.toString());
		branOpLogDao.success(BranOpLogEntity.OP_MODULE_SUB_USER_PERMISSION,
				BranOpLogEntity.OP_TYPE_ADD, model.getOperateUserId(), sysLogExtInfo);

		Map<String, Long> map = new HashMap<>();
		map.put("version", corpUserEntity.getTxVersion());
		return map;
	}

	@Override
	public Map<String, Long> updatePermissionUser(CorpModel model) throws Exception {
		// 查询当前用户
		BranCorpUserEntity curUser = branCorpUserDao.findCorpUserById(model.getOperateUserId());

		if (curUser == null) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_USER_NOT_FOUND);
		}

		// 判断公司ID
		if (StringUtils.isBlank(model.getBranCorpId())) {
			throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
		}

		// 判断密码
		if (StringUtils.isBlank(model.getPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
		}

		// 判断密码复杂度
		if (!ValidateUtils.vefityPassword(model.getPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_VALID_LOGIN_PWD_FORMAT);
		}

		// 判断确认密码
		if (StringUtils.isBlank(model.getConfirmPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
		}

		// 判断两次密码是否一致
		if (!model.getPassword().equals(model.getConfirmPassword())) {
			throw new AryaServiceException(ErrorCode.CODE_PASSWORD_CONFIRM);
		}

		// 查询公司是否存在
		BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(model.getBranCorpId());

		if (branCorporationEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
		}

		// 判断ID
		if (StringUtils.isBlank(model.getId())) {
			throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
		}

		// 查询需要操作的用户
		BranCorpUserEntity branCorpUserEntity = branCorpUserDao.findCorpUserById(model.getId());

		if (branCorporationEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_CORPORATION_USER_NOT_FIND);
		}

		// 判断version
		TxVersionUtil.compireVersion(branCorpUserEntity.getTxVersion(), model.getVersion());

		// 更新用户
		branCorpUserEntity.setLoginPwd(SysUtils.encryptPassword(model.getPassword()));
		branCorpUserEntity.setUpdateTime(System.currentTimeMillis());
		branCorpUserEntity.setUpdateUser(model.getOperateUserId());
		branCorporationEntity.setTxVersion(branCorpUserEntity.getTxVersion() + 1);
		branCorpUserDao.update(branCorpUserEntity);

		// 写日志
		StringBuilder sb = new StringBuilder();
		sb.append("修改了:" + branCorpUserEntity.getLoginName());
		sb.append("的密码");

		SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
		sysLogExtInfo.setMsg(sb.toString());
		branOpLogDao.success(BranOpLogEntity.OP_MODULE_SUB_USER_PERMISSION,
				BranOpLogEntity.OP_TYPE_UPDATE, model.getOperateUserId(), sysLogExtInfo);

		Map<String, Long> map = new HashMap<>();
		map.put("version", branCorpUserEntity.getTxVersion());
		return map;

	}


	@Override
	public void deletePermissionUser(CorpModel model) throws Exception {

		// 查询当前用户
		BranCorpUserEntity curUser = branCorpUserDao.findCorpUserById(model.getOperateUserId());

		if (curUser == null) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_USER_NOT_FOUND);
		}

		// 判断公司ID
		if (StringUtils.isBlank(model.getBranCorpId())) {
			throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
		}

		List<IdVersion> idVersions = Arrays.asList(model.getIds());

		// 判断 ids
		if (idVersions == null || idVersions.isEmpty()) {
			throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
		}

		Map<String, Long> params = new HashMap<>();

		for (IdVersion idVersion : idVersions) {
			params.put(idVersion.getId(), idVersion.getVersion());
		}

		// 删除用户
		List<BranCorpUserEntity> branCorpUserEntities = branCorpUserDao.findBranCorpUsersByIds(params.keySet());

		if (branCorpUserEntities == null || branCorpUserEntities.isEmpty()) {
			throw new AryaServiceException(ErrorCode.CODE_CORP_USER_NOT_EXIST);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("删除了用户");

		for (BranCorpUserEntity entity : branCorpUserEntities) {

			// 判断version
			Long textVersion = params.get(entity.getId());
			if (textVersion == null) {
				throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
			}

			sb.append(" 用户: " + entity.getLoginName());
			logger.info("删除的 textVersion: " + textVersion + " userName: " + entity.getLoginName());
			TxVersionUtil.compireVersion(entity.getTxVersion(), textVersion);
			entity.setIsDelete(1);
			branCorpUserDao.update(branCorpUserEntities);
		}

		SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
		sysLogExtInfo.setMsg(sb.toString());
		branOpLogDao.success(BranOpLogEntity.OP_MODULE_USER_PERMISSION,
				BranOpLogEntity.OP_TYPE_DELETE, model.getOperateUserId(), sysLogExtInfo);

	}

	@Override
	public CorpModelResult getPermissionUser(CorpModel command) {
		CorpModelResult corpModelResult = new CorpModelResult();
		List<CorpModelResult.ModelResult> modelResults = new ArrayList<CorpModelResult.ModelResult>();

		Map<String, Object> params = new HashMap<>();
		params.put(BaseBranEntity.COL_NAME_IS_DELETE, 0);
		params.put(BranCorpUserEntity.COL_NAME_BRAN_CORP_ID, command.getBranCorpId());
		params.put(BranCorpUserEntity.COL_NAME_IS_ADMIN, 0);

		List<BranCorpUserEntity> list = branCorpUserDao.findByParamsPagination(
				params,
				command.getPage_size(),
				command.getPage()
		);

		List<BranCorpUserEntity> listCount = branCorpUserDao.findByParams(params);

		if (list == null || list.isEmpty()) {
			corpModelResult.setTotal_rows(0);
			return corpModelResult;
		}

		for (BranCorpUserEntity entity : list) {
			CorpModelResult.ModelResult modelResult = corpModelResult.new ModelResult();
			modelResult.setCreateTime(entity.getCreateTime());
			modelResult.setLastLoginTime(entity.getLastLoginTime());
			modelResult.setUserId(entity.getId());
			modelResult.setName(entity.getLoginName());
			modelResult.setVersion(entity.getTxVersion());
			modelResults.add(modelResult);
		}
		corpModelResult.setResult(modelResults);
		corpModelResult.setTotal_rows(listCount.size());
		corpModelResult.setTotal_page(Utils.calculatePages(listCount.size(), command.getPage_size()));
		return corpModelResult;
	}
}
