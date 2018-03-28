package com.bumu.bran.admin.work_shift_type.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.entity.WorkShiftTypeEntity;
import com.bumu.bran.admin.work_shift_type.service.BranWorkShiftTypeService;
import com.bumu.bran.workshift.command.WorkShiftTypeCommand;
import com.bumu.bran.workshift.model.dao.BranWorkShiftTypeDao;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.workshift.result.BranWorkShiftTypeResult;
import com.bumu.bran.workshift.util.WorkShiftTypeUtils;
import com.bumu.common.result.ModelResult;
import com.bumu.common.util.TxVersionUtil;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author majun
 * @date 2016/12/19
 */
@Service
public class BranWorkShiftTypeServiceImpl implements BranWorkShiftTypeService {

	@Autowired
	private BranWorkShiftTypeDao branWorkShiftTypeDao;

	@Autowired
	private BranCorporationDao branCorporationDao;

	@Autowired
	private WorkShiftTypeUtils workShiftTypeUtils;


	@Override
	public void add(WorkShiftTypeCommand workShiftTypeCommand) throws Exception {
		//判断时间
		workShiftTypeUtils.verifyTime(workShiftTypeCommand.getWorkStartTime(), workShiftTypeCommand.getWorkEndTime(),
				workShiftTypeCommand.getIsNextDay());
		// 最大限制
		workShiftTypeUtils.verifyMaxCount(workShiftTypeCommand.getBranCorpId());
		// 验证名字重复
		workShiftTypeUtils.verifyNameUsed(workShiftTypeCommand.getName(), workShiftTypeCommand.getShortName(),
				workShiftTypeCommand.getBranCorpId());

		WorkShiftTypeEntity workShiftTypeEntity = new WorkShiftTypeEntity();
		BeanUtils.copyProperties(workShiftTypeEntity, workShiftTypeCommand);
		workShiftTypeEntity.setId(Utils.makeUUID());
		workShiftTypeEntity.setCreateUser(workShiftTypeCommand.getUserId());
		workShiftTypeEntity.setBranCorp(branCorporationDao.findCorpById(workShiftTypeCommand.getBranCorpId()));
		branWorkShiftTypeDao.persist(workShiftTypeEntity);

		// 记录日志用
		workShiftTypeCommand.setId(workShiftTypeEntity.getId());
		workShiftTypeCommand.setName(workShiftTypeEntity.getName());
	}

	@Override
	public void delete(WorkShiftTypeCommand workShiftTypeCommand) {
		WorkShiftTypeEntity workShiftTypeEntity = branWorkShiftTypeDao.findByIdNotDelete(workShiftTypeCommand.getId());
		// id
		if (workShiftTypeEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "ID错误");
		}
		// version
		TxVersionUtil.compireVersion(workShiftTypeCommand.getVersion(), workShiftTypeEntity.getTxVersion());
		// 是否被使用
		workShiftTypeUtils.verifyIsUsed(workShiftTypeEntity.getId());

		workShiftTypeEntity.setIsDelete(1);
		workShiftTypeEntity.setUpdateTime(System.currentTimeMillis());
		workShiftTypeEntity.setUpdateUser(workShiftTypeCommand.getUserId());
		branWorkShiftTypeDao.update(workShiftTypeEntity);

		// 记录日志用
		workShiftTypeCommand.setId(workShiftTypeEntity.getId());
		workShiftTypeCommand.setName(workShiftTypeEntity.getName());
	}

	@Override
	public ModelResult update(WorkShiftTypeCommand workShiftTypeCommand) {
		//判断时间
		workShiftTypeUtils.verifyTime(workShiftTypeCommand.getWorkStartTime(), workShiftTypeCommand.getWorkEndTime(),
				workShiftTypeCommand.getIsNextDay());
		WorkShiftTypeEntity workShiftTypeEntity = branWorkShiftTypeDao.findByIdNotDelete(workShiftTypeCommand.getId());
		// id
		if (workShiftTypeEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "ID错误");
		}
		// version
		TxVersionUtil.compireVersion(workShiftTypeCommand.getVersion(), workShiftTypeEntity.getTxVersion());
		// 验证名字重复
		workShiftTypeUtils.verifyNameUsedForUpdate(workShiftTypeCommand.getName(), workShiftTypeCommand.getShortName(),
				workShiftTypeCommand.getBranCorpId(), workShiftTypeCommand.getId());

		workShiftTypeEntity.setName(workShiftTypeCommand.getName());
		workShiftTypeEntity.setShortName(workShiftTypeCommand.getShortName());
		workShiftTypeEntity.setColor(workShiftTypeCommand.getColor());
		workShiftTypeEntity.setWorkStartTime(workShiftTypeCommand.getWorkStartTime());
		workShiftTypeEntity.setWorkEndTime(workShiftTypeCommand.getWorkEndTime());
		workShiftTypeEntity.setIsNextDay(workShiftTypeCommand.getIsNextDay());
		workShiftTypeEntity.setUpdateTime(System.currentTimeMillis());
		workShiftTypeEntity.setUpdateUser(workShiftTypeCommand.getUserId());
		branWorkShiftTypeDao.update(workShiftTypeEntity);

		// 记录日志用
		workShiftTypeCommand.setId(workShiftTypeEntity.getId());
		workShiftTypeCommand.setName(workShiftTypeEntity.getName());

		return new ModelResult(workShiftTypeCommand.getId(), workShiftTypeCommand.getVersion() + 1);
	}

	@Override
	public Map<String, Object> get(WorkShiftTypeCommand workShiftTypeCommand) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		List<BranWorkShiftTypeResult> resultList = new ArrayList<>();
		resultMap.put("models", resultList);
		List<WorkShiftTypeEntity> entities = branWorkShiftTypeDao.findByCorpId(workShiftTypeCommand.getBranCorpId());
		if (entities == null || entities.isEmpty()) {
			return resultMap;
		}
		for (WorkShiftTypeEntity entity : entities) {
			BranWorkShiftTypeResult result = new BranWorkShiftTypeResult();
			BeanUtils.copyProperties(result, entity);
			result.setVersion(entity.getTxVersion());
			resultList.add(result);
		}
		return resultMap;
	}

	@Override
	public BranWorkShiftTypeResult getOneById(WorkShiftTypeCommand workShiftTypeCommand) throws Exception {
		WorkShiftTypeEntity entity = branWorkShiftTypeDao.findByIdNotDelete(workShiftTypeCommand.getId());
		if (entity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "id错误,获取详情失败");
		}
		BranWorkShiftTypeResult result = new BranWorkShiftTypeResult();
		BeanUtils.copyProperties(result, entity);
		result.setVersion(entity.getTxVersion());
		return result;
	}

	@Override
	public Map<String, Object> getDefault(WorkShiftTypeCommand workShiftTypeCommand) throws Exception {
		Map<String, Object> resultMap = get(workShiftTypeCommand);
		BranWorkShiftTypeResult result = new BranWorkShiftTypeResult();
		BeanUtils.copyProperties(result, branWorkShiftTypeDao.findDefault());
		List<BranWorkShiftTypeResult> resultList = (List<BranWorkShiftTypeResult>) resultMap.get("models");
		if (resultList == null) {
			resultList = new ArrayList<>();
		}
		resultList.add(result);
		resultMap.put("models", resultList);
		return resultMap;
	}
}
