package com.bumu.bran.admin.employee_defined.helper;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.common.Constants;
import com.bumu.bran.employee.command.UserDefinedCommand;
import com.bumu.bran.employee.model.dao.UserDefinedColsDao;
import com.bumu.bran.employee.model.dao.UserDefinedDetailsDao;
import com.bumu.bran.employee.model.entity.UserDefinedColsEntity;
import com.bumu.bran.model.entity.BranOpLogEntity;
import com.bumu.exception.AryaServiceException;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author majun
 * @date 2016/11/30
 */
@Component
public class UserDefinedCheckHelper {

	@Autowired
	private UserDefinedColsDao userDefinedColsDao;

	@Autowired
	private UserDefinedDetailsDao userDefinedDetailsDao;


	public void checkColNameIsUsed(UserDefinedCommand userDefinedCommand) {
		Criteria criteria = userDefinedColsDao.findByColNameAndCorpId(userDefinedCommand);
		UserDefinedColsEntity userDefinedColsEntity = (UserDefinedColsEntity) criteria.uniqueResult();
		if (userDefinedColsEntity == null) {
			return;
		}
		// 如果更新时,如果更新的名字与数据库当前的名字一样的话,没问题
		if (userDefinedCommand.getActionType() == BranOpLogEntity.OP_TYPE_UPDATE) {
			if (userDefinedColsEntity.getId().equals(userDefinedCommand.getId())) {
				return;
			}
		}
		throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "自定义列名在本公司已经被使用过了");
	}

	public void checkColCanDelete(UserDefinedCommand userDefinedCommand) {
		Criteria criteria = userDefinedDetailsDao.findByUserDefinedColsId(userDefinedCommand);
		if (criteria.list() == null || criteria.list().isEmpty()) {
			return;
		}
		throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "自定义列名在本公司花名册或者离职员工被使用过了");
	}

	public void checkColsMaxRange(UserDefinedCommand userDefinedCommand) {
		Criteria criteria = userDefinedColsDao.findByCorpId(userDefinedCommand);
		int rowCount = Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		if (rowCount > Constants.USER_DEFINED_COLS_MAX_SIZE) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "花名册自定义项最多定义30项");
		}
	}

}
