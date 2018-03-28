package com.bumu.bran.admin.salary.helper;


import com.bumu.SysUtils;
import com.bumu.arya.IdcardValidator;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaSalaryDao;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.AryaSalaryEntity;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.admin.salary.result.ImportSalaryResult;
import com.bumu.bran.common.Constants;
import com.bumu.bran.handler.ExcelImportHandler;
import com.bumu.common.util.DateTimeUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class SalaryImportHelper extends ExcelImportHandler<ImportSalaryResult> {

	private Logger logger = LoggerFactory.getLogger(SalaryImportHelper.class);

	@Autowired
	private AryaUserDao aryaUserDao;

	@Autowired
	private AryaSalaryDao aryaSalaryDao;

	@Autowired
	private CorporationDao corporationDao;


	@Override
	public ImportSalaryResult todoSheet(Sheet sheet) {
		// 判断公司
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		if (StringUtils.isBlank(branCorpId)) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
		}

		// 判断总数
		if (sheet.getLastRowNum() > 200) {
			throw new AryaServiceException(ErrorCode.CODE_EXCEL_MAX_ERROR);
		}

		CorporationEntity corporationEntity = corporationDao.findByBranId(branCorpId);


		ImportSalaryResult importSalaryResult = new ImportSalaryResult();
		List<ImportSalaryResult.SalaryField> fields = new ArrayList<>();
		// 处理
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			ImportSalaryResult.SalaryField salaryField = importSalaryResult.new SalaryField();
			AryaUserEntity aryaUser = null;
			String card = null;
			String tel = null;
			String name = null;

			for (int j = 0; j < 26; j++) {
				Cell cell = row.getCell(j);
				if (cell != null) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}


				switch (j) {
					// 姓名
					case 0: {
						logger.debug("判断姓名是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "姓名为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("姓名为空或者NULL----------");
							salaryField.setName(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "姓名为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("姓名为空或者NULL----------");
							salaryField.setName(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setName(params);
						name = value;
						break;
					}

					case 1: {
						logger.debug("判断身份证是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "身份证为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("身份证为空或者NULL----------");
							salaryField.setIdCardNo(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "身份证为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("身份证为空或者NULL----------");
							salaryField.setIdCardNo(params);
							break;
						}

						// 验证身份证
						if (!IdcardValidator.isValidatedAllIdcard(value)) {
							importSalaryResult.setHasError(true);
							logger.debug("身份证格式不正确----------");
							params.setFlag(Constants.TRUE);
							params.setErr("身份证格式不正确");
							salaryField.setIdCardNo(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setIdCardNo(params);
						card = value;
						break;
					}

					case 2: {
						logger.debug("判断手机号是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "手机号为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("手机号为空或者NULL----------");
							salaryField.setTel(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "手机号为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("手机号为空或者NULL----------");
							salaryField.setTel(params);
							break;
						}

						// 验证手机号
						if (!SysUtils.checkPhoneNo(value)) {
							importSalaryResult.setHasError(true);
							logger.debug("手机号格式不正确----------");
							params.setFlag(Constants.TRUE);
							params.setErr("手机号格式不正确");
							salaryField.setTel(params);
							break;
						}

						// 通过身份证+手机号+名字查询aryaUser
						tel = value;
						aryaUser = aryaUserDao.findUserByIdCardNoAndNameAndTel(card, name, tel);
						if (aryaUser == null) {
							importSalaryResult.setHasError(true);
							logger.debug("通过身份证号: " + card + ", 姓名: " + name + ", 手机号 " + tel + "没有找到用户,请先去注册");
							params.setFlag(Constants.TRUE);
							params.setErr("通过身份证号: " + card + ", 姓名: " + name + ", 手机号 " + tel + "没有找到用户,请先去注册");
							salaryField.setTel(params);
							break;
						}
						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setTel(params);
						break;
					}

					case 3: {
						logger.debug("判断月份是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "月份为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("月份为空或者NULL----------");
							salaryField.setMonth(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "月份为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("月份为空或者NULL----------");
							salaryField.setMonth(params);
							break;
						}

						// 验证该月份的薪资是否已存在
						if (aryaUser != null) {

							Date date = DateTimeUtils.checkMonth(value);
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);
							int year = calendar.get(Calendar.YEAR);//年份
							int month = calendar.get(Calendar.MONTH) + 1;//月份

							List<AryaSalaryEntity> noRelease = aryaSalaryDao.findSalarysByUserMonthNoRelease(corporationEntity.getId(),
									aryaUser.getId(), year, month);
							String msg = aryaUser.getRealName() + value + "的薪资信息在未发布列表中已存在";

							if (noRelease != null && !noRelease.isEmpty()) {
								logger.debug("查询未发布的薪资条: ");
								params.setFlag(Constants.TRUE);
								logger.info(msg);
								params.setErr(msg);
								importSalaryResult.setHasError(true);
								salaryField.setMonth(params);
								break;
							}

							// 查询已发布的数据, 如果存在给提示信息
							List<AryaSalaryEntity> release = aryaSalaryDao.findByRelease(corporationEntity.getId(), aryaUser.getId(),
									year, month);

							if (release != null && !release.isEmpty()) {
								logger.info("查询到已经发布的薪资条");
								salaryField.setRelease(1);
							}
						}
						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setMonth(params);
						break;
					}

					case 4: {
						logger.debug("判断基本工资是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "基本工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("基本工资为空或者NULL----------");
							salaryField.setWage(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "基本工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("基本工资为空或者NULL----------");
							salaryField.setWage(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setWage(params);
						break;
					}

					case 5: {
						logger.debug("判断工作日出勤时间(小时)是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "工作日出勤时间(小时)为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("工作日出勤时间(小时)为空或者NULL----------");
							salaryField.setTotalWorkdayHours(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "工作日出勤时间(小时)为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("工作日出勤时间(小时)为空或者NULL----------");
							salaryField.setTotalWorkdayHours(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setTotalWorkdayHours(params);
						break;
					}

					case 6: {
						logger.debug("判断平时加班工资是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "平时加班工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("平时加班工资为空或者NULL----------");
							salaryField.setWorkdayOvertimeSalary(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "平时加班工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("平时加班工资为空或者NULL----------");
							salaryField.setWorkdayOvertimeSalary(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setWorkdayOvertimeSalary(params);
						break;
					}

					case 7: {
						logger.debug("判断平时加班工作时间(小时)是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "平时加班工作时间(小时)为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("平时加班工作时间(小时)为空或者NULL----------");
							salaryField.setWorkdayOvertimeHours(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "平时加班工作时间(小时)为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("平时加班工作时间(小时)为空或者NULL----------");
							salaryField.setWorkdayOvertimeHours(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setWorkdayOvertimeHours(params);
						break;
					}

					case 8: {
						logger.debug("判断休息日加班工资是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "休息日加班工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("休息日加班工资为空或者NULL----------");
							salaryField.setRestdayOvertimeSalary(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "休息日加班工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("休息日加班工资为空或者NULL----------");
							salaryField.setRestdayOvertimeSalary(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setRestdayOvertimeSalary(params);
						break;
					}

					case 9: {
						logger.debug("判断休息日工作时间(小时)是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "休息日工作时间(小时)为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("休息日工作时间(小时)为空或者NULL----------");
							salaryField.setRestDayOvertimeHours(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "休息日工作时间(小时)为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("休息日工作时间(小时)为空或者NULL----------");
							salaryField.setRestDayOvertimeHours(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setRestDayOvertimeHours(params);
						break;
					}

					case 10: {
						logger.debug("判断国假加班工资是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "国假加班工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("国假加班工资为空或者NULL----------");
							salaryField.setLegalHolidayOvertimeSalary(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "国假加班工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("国假加班工资为空或者NULL----------");
							salaryField.setLegalHolidayOvertimeSalary(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setLegalHolidayOvertimeSalary(params);
						break;
					}

					case 11: {
						logger.debug("判断国假加班时间(小时)是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "国假加班时间(小时)为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("国假加班时间(小时)为空或者NULL----------");
							salaryField.setLegalHolidayOvertimeHours(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "国假加班时间(小时)为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("国假加班时间(小时)为空或者NULL----------");
							salaryField.setLegalHolidayOvertimeHours(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setLegalHolidayOvertimeHours(params);
						break;
					}

					case 12: {
						logger.debug("判断绩效奖金是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "绩效奖金为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("绩效奖金为空或者NULL----------");
							salaryField.setPerformanceBonus(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "绩效奖金为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("绩效奖金为空或者NULL----------");
							salaryField.setPerformanceBonus(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setPerformanceBonus(params);
						break;
					}

					case 13: {
						logger.debug("判断津贴（餐补/车补/住房）是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "津贴（餐补/车补/住房）为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("津贴（餐补/车补/住房）为空或者NULL----------");
							salaryField.setSubsidy(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "津贴（餐补/车补/住房）为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("津贴（餐补/车补/住房）为空或者NULL----------");
							salaryField.setSubsidy(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setSubsidy(params);
						break;
					}

					case 14: {
						logger.debug("判断事假扣款是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "事假扣款为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("事假扣款为空或者NULL----------");
							salaryField.setCasualLeaveCut(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "事假扣款为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("事假扣款为空或者NULL----------");
							salaryField.setCasualLeaveCut(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setCasualLeaveCut(params);
						break;
					}

					case 15: {
						logger.debug("判断事假天数是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "事假天数为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("事假天数为空或者NULL----------");
							salaryField.setCasualLeaveDays(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "事假天数为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("事假天数为空或者NULL----------");
							salaryField.setCasualLeaveDays(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setCasualLeaveDays(params);
						break;
					}

					case 16: {
						logger.debug("判断病假扣款是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "病假扣款为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("病假扣款为空或者NULL----------");
							salaryField.setSickLeaveCut(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "病假扣款为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("病假扣款为空或者NULL----------");
							salaryField.setSickLeaveCut(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setSickLeaveCut(params);
						break;
					}

					case 17: {
						logger.debug("判断病假天数是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "病假天数为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("病假天数为空或者NULL----------");
							salaryField.setSickLeaveDays(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "病假天数为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("病假天数为空或者NULL----------");
							salaryField.setSickLeaveDays(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setSickLeaveDays(params);
						break;
					}

					case 18: {
						logger.debug("判断其他扣款是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "其他扣款为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("其他扣款为空或者NULL----------");
							salaryField.setOtherCut(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "其他扣款为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("其他扣款为空或者NULL----------");
							salaryField.setOtherCut(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setOtherCut(params);
						break;
					}

					case 19: {
						logger.debug("判断补款是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "补款为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("补款为空或者NULL----------");
							salaryField.setRepayment(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "补款为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("补款为空或者NULL----------");
							salaryField.setRepayment(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setRepayment(params);
						break;
					}

					case 20: {
						logger.debug("判断社保（个人）是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "社保（个人）为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("社保（个人）为空或者NULL----------");
							salaryField.setSoinPersonal(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "社保（个人）为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("社保（个人）为空或者NULL----------");
							salaryField.setSoinPersonal(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setSoinPersonal(params);
						break;
					}

					case 21: {
						logger.debug("判断公积金（个人）是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "公积金（个人）为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("公积金（个人）为空或者NULL----------");
							salaryField.setHouseFundPersonal(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "公积金（个人）为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("公积金（个人）为空或者NULL----------");
							salaryField.setHouseFundPersonal(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setHouseFundPersonal(params);
						break;
					}

					case 22: {
						logger.debug("判断应税工资是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "应税工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("应税工资为空或者NULL----------");
							salaryField.setTaxableSalary(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "应税工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("应税工资为空或者NULL----------");
							salaryField.setTaxableSalary(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setTaxableSalary(params);
						break;
					}

					case 23: {
						logger.debug("判断个税（个人）是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "个税（个人）为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("个税（个人）为空或者NULL----------");
							salaryField.setPersonalTax(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "个税（个人）为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("个税（个人）为空或者NULL----------");
							salaryField.setPersonalTax(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setPersonalTax(params);
						break;
					}

					case 24: {
						logger.debug("判断应发工资是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "应发工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("应发工资为空或者NULL----------");
							salaryField.setGrossSalary(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "应发工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("应发工资为空或者NULL----------");
							salaryField.setGrossSalary(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setGrossSalary(params);
						break;
					}

					case 25: {
						logger.debug("判断实发工资是否为空----------");
						ImportSalaryResult.Params params = importSalaryResult.new Params();

						if (!check(cell, params, "实发工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("实发工资为空或者NULL----------");
							salaryField.setNetSalary(params);
							break;
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value, params, "实发工资为空或者NULL")) {
							importSalaryResult.setHasError(true);
							logger.debug("实发工资为空或者NULL----------");
							salaryField.setNetSalary(params);
							break;
						}

						params.setFlag(Constants.FALSE);
						params.setValue(value);
						salaryField.setNetSalary(params);
						break;
					}
				}
			}

			fields.add(salaryField);
		}

		importSalaryResult.setFile_id(Utils.makeUUID());
		importSalaryResult.setSalaries(fields);
		importSalaryResult.setTotal_count(sheet.getLastRowNum());
		return importSalaryResult;
	}

	private boolean check(Cell value, ImportSalaryResult.Params params, String msg) {
		if (value == null) {
			params.setFlag(Constants.TRUE);
			params.setErr(msg);
			return false;
		}
		return true;
	}

	private boolean check(String value, ImportSalaryResult.Params params, String msg) {
		if (StringUtils.isBlank(value)) {
			params.setFlag(Constants.TRUE);
			params.setErr(msg);
			return false;
		}
		return true;
	}
}
