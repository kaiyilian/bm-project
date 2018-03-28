package com.bumu.bran.admin.employee.helper;


import com.bumu.SysUtils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.admin.prospective.helper.ProspectiveHelper;
import com.bumu.bran.common.Constants;
import com.bumu.bran.common.service.CommonBranCorpService;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.ProspectiveEmployeeDao;
import com.bumu.bran.handler.ExcelImportHandler;
import com.bumu.bran.model.entity.DepartmentEntity;
import com.bumu.bran.model.entity.PositionEntity;
import com.bumu.bran.model.entity.WorkLineEntity;
import com.bumu.bran.model.entity.WorkShiftEntity;
import com.bumu.bran.setting.model.dao.DepartmentDao;
import com.bumu.bran.setting.model.dao.PositionDao;
import com.bumu.bran.setting.model.dao.WorkLineDao;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.prospective.command.SaveProspectiveEmployeeCommand;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserSaveHelper extends ExcelImportHandler<ProspectiveEmployeeEntity> {

	private Logger logger = LoggerFactory.getLogger(UserSaveHelper.class);


	@Resource
	private ProspectiveEmployeeDao prospectiveEmployeeDao;

	@Resource
	private EmployeeDao employeeDao;

	@Resource
	private PositionDao positionDao;

	@Resource
	private WorkShiftDao workShiftDao;

	@Resource
	private WorkLineDao workLineDao;

	@Resource
	private DepartmentDao departmentDao;

	@Autowired
	private CommonBranCorpService commonBranCorpService;

	@Autowired
	private ProspectiveHelper prospectiveHelper;

	@Override
	public List<ProspectiveEmployeeEntity> todoSheetList(Sheet sheet) {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		if (StringUtils.isBlank(branCorpId)) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
		}

		List<ProspectiveEmployeeEntity> prospectiveEmployeeEntities = new ArrayList<>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			ProspectiveEmployeeEntity entity = null;
			SaveProspectiveEmployeeCommand saveProspectiveEmployeeCommand = new SaveProspectiveEmployeeCommand();
			saveProspectiveEmployeeCommand.setBranCorpId(branCorpId);
			saveProspectiveEmployeeCommand.setCreateType(Constants.HR_CREATE);

			for (int j = 0; j < 7; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
				}

				//第7列是日期格式,所以先不要设置为string类型
				if (cell != null && j != 6) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}
				switch (j) {
					//姓名
					case 0: {

						logger.debug("判断姓名是否为空----------");

						String value = cell.getStringCellValue();

						if (StringUtils.isBlank(value)) {
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						saveProspectiveEmployeeCommand.setName(value);
					}
					break;
					//职位
					case 1: {

						String value = cell.getStringCellValue();
						value = value.trim();

						if (StringUtils.isBlank(value)) {
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						PositionEntity positionEntity = positionDao.findCorpPostionByNameAndBranCorpId(
								value, branCorpId);

						if (positionEntity == null) {
							throw new AryaServiceException("找不到对应的职业");
						}

						saveProspectiveEmployeeCommand.setPositionId(positionEntity.getId());
						saveProspectiveEmployeeCommand.setPositionName(positionEntity.getPositionName());
					}
					break;
					//班组
					case 2: {
						String value = cell.getStringCellValue();
						value = value.trim();

						if (StringUtils.isBlank(value)) {
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						WorkShiftEntity workShiftEntity = workShiftDao.findCorpWorkShiftByNameAndBranCorpId(value, branCorpId);

						if (workShiftEntity == null) {
							throw new AryaServiceException("找不到对应的班组");
						}

						saveProspectiveEmployeeCommand.setWorkShiftId(workShiftEntity.getId());
						saveProspectiveEmployeeCommand.setWorkShiftName(workShiftEntity.getShiftName());
					}
					break;
					//工段
					case 3: {

						String value = cell.getStringCellValue();
						value = value.trim();

						if (StringUtils.isBlank(value)) {
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						WorkLineEntity workLineEntity = workLineDao.findCorpWorkLineIdByName(value, branCorpId);

						if (workLineEntity == null) {
							throw new AryaServiceException("找不到对应的工段");
						}

						saveProspectiveEmployeeCommand.setWorkLineId(workLineEntity.getId());
						saveProspectiveEmployeeCommand.setWorkLineName(workLineEntity.getLineName());
					}
					break;
					//部门
					case 4: {

						String value = cell.getStringCellValue();
						value = value.trim();

						if (StringUtils.isBlank(value)) {
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						DepartmentEntity departmentEntity = departmentDao.findCorpDepartmentIdByName(value, branCorpId);
						if (departmentEntity == null) {
							throw new AryaServiceException("找不到对应的部门");
						}

						saveProspectiveEmployeeCommand.setDepartmentId(departmentEntity.getId());
						saveProspectiveEmployeeCommand.setDepartmentName(departmentEntity.getDepartmentName());
					}
					break;
					//手机号
					case 5: {

						String value = cell.getStringCellValue();
						value = value.trim();

						if (StringUtils.isBlank(value)) {
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						if (!SysUtils.checkPhoneNo(value)) {

							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						// 待入职
						entity =
								prospectiveEmployeeDao.findProspectiveEmployeeByPhoneNoAndBranCorpId(value, branCorpId);

						if (entity == null) {
							logger.debug("新增待入职");
							entity = new ProspectiveEmployeeEntity();
							entity.createBefore(saveProspectiveEmployeeCommand);
						} else {
							logger.debug("更新待入职");
							entity.updateBefore(saveProspectiveEmployeeCommand);
						}

						prospectiveHelper.setOtherPropOnSave(entity, saveProspectiveEmployeeCommand);
						// 正式员工
						EmployeeEntity employeeEntity =
								employeeDao.findEmployeeByPhoneAndBranCorpId(value, branCorpId);

						if (employeeEntity != null) {

							throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_PHONE_CONFLICT);
						}

						entity.setPhoneNo(value);
					}
					break;
					//入职时间
					case 6: {

						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							if (DateUtil.isCellDateFormatted(cell)) {
								double value = cell.getNumericCellValue();
								Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
								entity.setCheckinTime(date.getTime());
							}

						} else {
							throw new AryaServiceException(ErrorCode.CODE_CORP_CHECKIN_DATE_ERROR);
						}
					}
				}
			}
			prospectiveEmployeeEntities.add(entity);
		}
		return prospectiveEmployeeEntities;
	}
}
