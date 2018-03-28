package com.bumu.bran.admin.employee.helper;


import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.employee.result.ImportProspectiveResult;
import com.bumu.bran.common.Constants;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.LeaveEmployeeDao;
import com.bumu.bran.employee.model.dao.ProspectiveEmployeeDao;
import com.bumu.bran.handler.ExcelImportHandler;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.*;
import com.bumu.bran.setting.model.dao.DepartmentDao;
import com.bumu.bran.setting.model.dao.PositionDao;
import com.bumu.bran.setting.model.dao.WorkLineDao;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.leave_emp.model.entity.LeaveEmployeeEntity;
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

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UserImportHelper extends ExcelImportHandler<ImportProspectiveResult> {

	private Logger logger = LoggerFactory.getLogger(UserImportHelper.class);

	@Autowired
	private ProspectiveEmployeeDao prospectiveEmployeeDao;

	@Autowired
	private PositionDao positionDao;

	@Autowired
	private WorkShiftDao workShiftDao;

	@Autowired
	private WorkLineDao workLineDao;

	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private LeaveEmployeeDao leaveEmployeeDao;

	@Autowired
	private AryaUserDao aryaUserDao;

	@Autowired
	private BranAdminConfigService configService;

	@Autowired
	private BranUserDao branUserDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public ImportProspectiveResult todoSheet(Sheet sheet) {
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		if (StringUtils.isBlank(branCorpId)) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
		}

		Map<String, String> phoneMaps = new HashMap<>();
		ImportProspectiveResult importProspectiveResult = new ImportProspectiveResult();
		List<ImportProspectiveResult.Prospective> prospectiveList = new ArrayList<>();
		// 空文件
		if (sheet.getLastRowNum() <= 0) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "上传文件中有错误，请修改后重新提交");
		}
		if (sheet.getLastRowNum() > 200) {
			throw new AryaServiceException(ErrorCode.CODE_EXCEL_MAX_ERROR);
		}
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			ImportProspectiveResult.Prospective employess = importProspectiveResult.new Prospective();

			for (int j = 0; j < 7; j++) {
				Cell cell = row.getCell(j);
				//第7列是日期格式,所以先不要设置为string类型
				if (cell != null && j != 6) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}
				switch (j) {
					//姓名
					case 0: {
						logger.debug("判断姓名是否为空----------");
						ImportProspectiveResult.Params employeeName = importProspectiveResult.new Params();

						if (!check(cell, employeeName, "姓名为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							logger.debug("姓名为空或者NULL----------");
							employess.setHasError(true);
							employess.setEmployee_name(employeeName);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, employeeName, "姓名为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							logger.debug("姓名为空或者NULL----------");
							employess.setHasError(true);
							employess.setEmployee_name(employeeName);
							break;
						}

						employeeName.setFlag(Constants.FALSE);
						employeeName.setValue(value);
						employess.setEmployee_name(employeeName);
					}
					break;
					//职位
					case 1: {
						logger.debug("判断职位是否为空----------");
						ImportProspectiveResult.Params employeeName = importProspectiveResult.new Params();

						if (!check(cell, employeeName, "职位为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							logger.debug("职位为空或者NULL----------");
							employess.setHasError(true);
							employess.setPosition_name(employeeName);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();
						if (!check(value, employeeName, "职位为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							employess.setHasError(true);
							employess.setPosition_name(employeeName);
							break;
						}

						PositionEntity p = positionDao.findCorpPostionByNameAndBranCorpId(value, branCorpId);

						if (p == null) {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employeeName.setErr("没有此职位: " + value);
							employess.setHasError(true);
							employess.setPosition_name(employeeName);
							break;
						}


						employeeName.setFlag(Constants.FALSE);
						employeeName.setValue(value);
						employess.setPosition_name(employeeName);
					}
					break;
					//班组
					case 2: {
						logger.debug("判断班组是否为空----------");
						ImportProspectiveResult.Params employeeName = importProspectiveResult.new Params();

						if (!check(cell, employeeName, "班组为空或者为NULL")) {
							importProspectiveResult.setHasError(true);
							employess.setHasError(true);
							logger.debug("班组为空或者为NULL----------");
							employess.setWork_shift_name(employeeName);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();
						if (!check(value, employeeName, "班组为空或者为NULL")) {
							importProspectiveResult.setHasError(true);
							employess.setHasError(true);
							employess.setWork_shift_name(employeeName);
							break;
						}

						WorkShiftEntity workShiftEntity = workShiftDao.findCorpWorkShiftByNameAndBranCorpId(value, branCorpId);

						if (workShiftEntity == null) {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employeeName.setErr("没有此班组: " + value);
							employess.setHasError(true);
							employess.setWork_shift_name(employeeName);
							break;
						}

						employeeName.setFlag(Constants.FALSE);
						employeeName.setValue(value);
						employess.setWork_shift_name(employeeName);
					}
					break;
					//工段
					case 3: {
						logger.debug("判断工段是否为空----------");
						ImportProspectiveResult.Params employeeName = importProspectiveResult.new Params();

						if (!check(cell, employeeName, "工段为空或者为NULL")) {
							importProspectiveResult.setHasError(true);
							logger.debug("工段为空或者为NULL----------");
							employess.setHasError(true);
							employess.setWork_line_name(employeeName);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();
						if (!check(value, employeeName, "工段为空或者为NULL")) {
							importProspectiveResult.setHasError(true);
							employess.setHasError(true);
							employess.setWork_line_name(employeeName);
							break;
						}

						WorkLineEntity workLineEntity = workLineDao.findCorpWorkLineIdByName(value, branCorpId);

						if (workLineEntity == null) {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employess.setHasError(true);
							employeeName.setErr("没有此工段: " + value);
							employess.setWork_line_name(employeeName);
							break;
						}

						employeeName.setFlag(Constants.FALSE);
						employeeName.setValue(value);
						employess.setWork_line_name(employeeName);
					}
					break;
					//部门
					case 4: {
						logger.debug("判断部门是否为空----------");
						ImportProspectiveResult.Params employeeName = importProspectiveResult.new Params();

						if (!check(cell, employeeName, "部门为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							logger.debug("部门为空或者NULL----------");
							employess.setDepartment_name(employeeName);
							employess.setHasError(true);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();
						if (!check(value, employeeName, "部门为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							employess.setDepartment_name(employeeName);
							employess.setHasError(true);
							break;
						}

						DepartmentEntity departmentEntity = departmentDao.findCorpDepartmentIdByName(value, branCorpId);

						if (departmentEntity == null) {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employeeName.setErr("没有此部门: " + value);
							employess.setDepartment_name(employeeName);
							employess.setHasError(true);
							break;
						}

						employeeName.setFlag(Constants.FALSE);
						employeeName.setValue(value);
						employess.setDepartment_name(employeeName);
					}
					break;
					//手机号
					case 5: {
						logger.debug("判断手机号是否为空----------");
						ImportProspectiveResult.Params employeeName = importProspectiveResult.new Params();

						if (!check(cell, employeeName, "手机号为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							logger.debug("手机号为空或者NULL----------");
							employess.setPhone_no(employeeName);
							employess.setHasError(true);
							break;
						}


						String value = cell.getStringCellValue();
						value = value.trim();
						if (!check(value, employeeName, "手机号为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							employess.setPhone_no(employeeName);
							employess.setHasError(true);
							break;
						}

						if (!SysUtils.checkPhoneNo(value)) {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employeeName.setErr("手机号码错误: " + value);
							employess.setPhone_no(employeeName);
							employess.setHasError(true);
							break;
						}

						// excel
						if (phoneMaps.containsKey(value)) {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employeeName.setErr("手机号码在导入的excel中已经存在: " + value);
							employess.setPhone_no(employeeName);
							employess.setHasError(true);
							break;
						}

						// 待入职
						ProspectiveEmployeeEntity prospectiveEmployeeEntity =
								prospectiveEmployeeDao.findProspectiveEmployeeByPhoneNoAndBranCorpId(value, branCorpId);

						if (prospectiveEmployeeEntity != null && prospectiveEmployeeEntity.getCreateType() != null
								&& prospectiveEmployeeEntity.getCreateType() == Constants.HR_CREATE) {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employeeName.setErr("手机号码在本公司待入职员工中已经存在: " + value);
							employess.setPhone_no(employeeName);
							employess.setHasError(true);
							break;
						}

						// 正式员工
						if (isEmployeeExisted(value, branCorpId) != null) {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employeeName.setErr("手机号码在本公司正式员工中已经存在: " + value);
							employess.setPhone_no(employeeName);
							employess.setHasError(true);
							break;
						}

						// 离职员工
						LeaveEmployeeEntity leaveEmployeeEntity = leaveEmployeeDao.
								findLeaveEmployeeByPhoneAndBranCorpId(value, branCorpId);

						if (leaveEmployeeEntity != null) {
							employeeName.setTipMsg(" 手机号码在本公司有过入职记录");
						}

						// 判断是否需要提醒不良记录
						// 根据手机号 找到aryaUser
						AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(value);
						if (aryaUserEntity != null) {
							//找到brUser
							BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId(), branCorpId);
							if (branUserEntity != null) {
								if (branUserEntity.getBadRecordCount() >= configService.getBadRecordCount()) {
									employeeName.setTipMsg(" 该员工有过多次不良记录");
								}
							}
						}

						employeeName.setFlag(Constants.FALSE);
						employeeName.setValue(cell.getStringCellValue());
						employess.setPhone_no(employeeName);
						phoneMaps.put(employeeName.getValue(), employeeName.getValue());

					}
					break;
					//入职时间
					case 6: {
						logger.debug("判断入职时间----------");
						ImportProspectiveResult.Params employeeName = importProspectiveResult.new Params();

						if (!check(cell, employeeName, "入职时间为空或者NULL")) {
							importProspectiveResult.setHasError(true);
							logger.debug("入职时间为空或者NULL----------");
							employess.setCheck_in_time(employeeName);
							employess.setHasError(true);
							break;
						}

						if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							SimpleDateFormat sdf = null;
//							if (format == 14 || format == 176) {
							if (DateUtil.isCellDateFormatted(cell)) {
								sdf = new SimpleDateFormat("yyyy/MM/dd");
								double value = cell.getNumericCellValue();
								Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
								employeeName.setValue(sdf.format(date));
							} else {
								importProspectiveResult.setHasError(true);
								employeeName.setFlag(Constants.TRUE);
								employeeName.setErr("日期格式错误: " + SysUtils.DATE_FORMAT);
								employess.setCheck_in_time(employeeName);
								employess.setHasError(true);
								break;
							}

						} else {
							importProspectiveResult.setHasError(true);
							employeeName.setFlag(Constants.TRUE);
							employeeName.setErr("日期格式错误: " + SysUtils.DATE_FORMAT);
							employess.setCheck_in_time(employeeName);
							employess.setHasError(true);
							break;
						}

						employess.setCheck_in_time(employeeName);
					}
				}
			}
			prospectiveList.add(employess);
		}

		importProspectiveResult.setFile_id(Utils.makeUUID());
		importProspectiveResult.setEmployees(prospectiveList);
		importProspectiveResult.setTotal_count(sheet.getLastRowNum());
		importProspectiveResult.calculateProblemsCount();


		return importProspectiveResult;
	}

	private boolean check(Cell value, ImportProspectiveResult.Params params, String msg) {
		if (value == null) {
			params.setFlag(Constants.TRUE);
			params.setErr(msg);
			return false;
		}
		return true;
	}

	private boolean check(String value, ImportProspectiveResult.Params params, String msg) {
		if (StringUtils.isBlank(value)) {
			params.setFlag(Constants.TRUE);
			params.setErr(msg);
			return false;
		}
		return true;
	}

	private String isEmployeeExisted(String phoneNo, String branCorpId) {
		// 先通过手机号找到arya用户
		AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(phoneNo);
		if (aryaUserEntity == null) {
			return null;
		}
		// 在通过arya用户找到bran用户
		BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
		if (branUserEntity == null) {
			return null;
		}

		EmployeeEntity employeeEntity = employeeDao.findEmployeeByBranUserIdAndBranCorpId(branUserEntity.getId(), branCorpId);
		if (employeeEntity != null) {
			return employeeEntity.getId();
		}
		return null;
	}
}
