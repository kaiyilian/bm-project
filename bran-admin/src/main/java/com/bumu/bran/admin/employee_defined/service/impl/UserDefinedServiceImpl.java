package com.bumu.bran.admin.employee_defined.service.impl;

import com.bumu.bran.admin.employee_defined.helper.UserDefinedCheckHelper;
import com.bumu.bran.admin.employee_defined.service.UserDefinedService;
import com.bumu.bran.common.Constants;
import com.bumu.bran.employee.command.UserDefinedCommand;
import com.bumu.bran.employee.model.dao.UserDefinedColsDao;
import com.bumu.bran.employee.model.entity.UserDefinedColsEntity;
import com.bumu.bran.employee.result.UserDefinedResult;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.bran.model.entity.BranOpLogEntity;
import com.bumu.common.util.TxVersionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author majun
 * @date 2016/11/28
 */
@Service
public class UserDefinedServiceImpl implements UserDefinedService {

	private Logger logger = LoggerFactory.getLogger(UserDefinedServiceImpl.class);

	@Autowired
	private UserDefinedColsDao userDefinedColsDao;
	@Autowired
	private BranCorporationDao branCorporationDao;
	@Autowired
	private UserDefinedCheckHelper checkHelper;

	@Override
	public List<UserDefinedResult> all(UserDefinedCommand userDefinedCommand) throws Exception {
		List<UserDefinedColsEntity> entities = userDefinedColsDao.findByCorpId(userDefinedCommand).list();
		List<UserDefinedResult> results = new ArrayList<>();
		if (entities == null || entities.isEmpty()) {
			return results;
		}
		for (UserDefinedColsEntity entity : entities) {
			UserDefinedResult userDefinedResult = new UserDefinedResult();
			userDefinedResult.setId(entity.getId());
			userDefinedResult.setColName(entity.getColName());
			userDefinedResult.setVersion(entity.getTxVersion());
			userDefinedResult.setType(entity.getType());
			// 分页参数是不需要返回的
			userDefinedResult.setPage(null);
			userDefinedResult.setPageSize(null);
			results.add(userDefinedResult);
		}
		return results;
	}

	@Override
	public void add(UserDefinedCommand userDefinedCommand) throws Exception {
		// 检查名字是否被使用过
		checkHelper.checkColNameIsUsed(userDefinedCommand);
		// 检查max
		checkHelper.checkColsMaxRange(userDefinedCommand);
		// 设置entity
		BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpByIdThrow(userDefinedCommand.getBranCorpId());
		UserDefinedColsEntity userDefinedColsEntity = new UserDefinedColsEntity();
		userDefinedCommand.setBranCorporationEntity(branCorporationEntity);
		userDefinedColsEntity.createBefore(userDefinedCommand);
		// 新增
		logger.debug("add id: " + userDefinedColsEntity.getId());
		userDefinedColsDao.create(userDefinedColsEntity);

		// 在aop中记录日志用
		userDefinedCommand.setId(userDefinedColsEntity.getId());
		userDefinedCommand.setName(userDefinedColsEntity.getColName());
	}

	@Override
	public Map<String, Object> update(UserDefinedCommand userDefinedCommand) throws Exception {
		// 检查根据id有没有该entity
		UserDefinedColsEntity entity = userDefinedColsDao.findByIdNotDelete(userDefinedCommand.getId());
		if (entity == null) {
			return null;
		}
		// 判断version
		TxVersionUtil.compireVersion(entity.getTxVersion(), userDefinedCommand.getVersion());
		// 检查名字是否被使用过
		userDefinedCommand.setActionType(BranOpLogEntity.OP_TYPE_UPDATE);
		checkHelper.checkColNameIsUsed(userDefinedCommand);
		// 检查是否在花名册、离职员工中
		checkHelper.checkColCanDelete(userDefinedCommand);
		// 设置更新属性
		entity.updateBefore(userDefinedCommand);
		userDefinedColsDao.update(entity);
		// 记录日志用
		userDefinedCommand.setId(entity.getId());
		userDefinedCommand.setName(entity.getColName());

		Map<String, Object> map = new HashMap<>();
		map.put("version", entity.getTxVersion() + 1);
		return map;
	}

	@Override
	public void delete(UserDefinedCommand userDefinedCommand) {
		// 检查根据id有没有该entity
		UserDefinedColsEntity entity = userDefinedColsDao.findByIdNotDelete(userDefinedCommand.getId());
		if (entity == null) {
			return;
		}
		// 判断version
		TxVersionUtil.compireVersion(entity.getTxVersion(), userDefinedCommand.getVersion());
		// 删除之前判断改自定义名是否在花名册 离职员工列表被使用过
		checkHelper.checkColCanDelete(userDefinedCommand);
		// 设置删除属性
		entity.setIsDelete(Constants.TRUE);
		entity.setUpdateTime(System.currentTimeMillis());
		entity.setUpdateUser(userDefinedCommand.getUserId());
		userDefinedColsDao.update(entity);
		// 记录日志用
		userDefinedCommand.setId(entity.getId());
		userDefinedCommand.setName(entity.getColName());
	}
}
