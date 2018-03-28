package com.bumu.bran.admin.salary.helper;


import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaSalaryDao;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.AryaSalaryEntity;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.handler.ExcelImportHandler;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
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

import java.util.*;

import static com.bumu.SysUtils.turnStringToBigDecimal;

@Component
public class SalarySaveHelper extends ExcelImportHandler<AryaSalaryEntity> {

	private Logger logger = LoggerFactory.getLogger(SalarySaveHelper.class);

	@Autowired
	private AryaUserDao aryaUserDao;

	@Autowired
	private AryaSalaryDao aryaSalaryDao;

	@Autowired
	private BranCorporationDao branCorporationDao;
	@Autowired
	private CorporationDao corporationDao;

	public static Map<String, Integer> getYearMonthByTime(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int year = calendar.get(Calendar.YEAR);//年份
		int month = calendar.get(Calendar.MONTH) + 1;//月份
		Map<String, Integer> result = new HashMap<>();
		result.put("year", year);
		result.put("month", month);
		return result;
	}

	public static String getMonthStr(int year, int month) {
		return year + "" + month;
	}

	@Override
	public List<AryaSalaryEntity> todoSheetList(Sheet sheet) {
		// 判断公司
		Session session = SecurityUtils.getSubject().getSession();
		String branCorpId = session.getAttribute("bran_corp_id").toString();

		if (StringUtils.isBlank(branCorpId)) {
			throw new AryaServiceException(ErrorCode.CODE_CORPORATION_ID_NOT_OFFER);
		}

		// 判断总数
		if (sheet.getLastRowNum() > 200) {
			throw new AryaServiceException(ErrorCode.CODE_EXCEL_MAX_ERROR);
		}

		// 查询公司是否存在
		BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(branCorpId);

		if (branCorporationEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
		}

		CorporationEntity corporationEntity = corporationDao.findByBranId(branCorpId);

		//更新公司信息  是否不能删除，0为否，1为是
		if (corporationEntity.getMandatory() != 1) {
			corporationEntity.setMandatory(1);
			corporationDao.update(corporationEntity);
		}

		List<AryaSalaryEntity> list = new ArrayList<>();
		// 处理
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			AryaUserEntity aryaUser = null;
			AryaSalaryEntity aryaSalaryEntity = new AryaSalaryEntity();
			String idCard = null;
			String phone = null;
			String name = null;

			for (int j = 0; j < 26; j++) {
				Cell cell = row.getCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);

				switch (j) {
					// 姓名
					case 0: {
						logger.debug("判断姓名是否为空----------");

						if (!check(cell)) {
							logger.debug("姓名为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}
						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value)) {
							logger.debug("姓名为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						name = value;

						break;
					}

					case 1: {
						logger.debug("判断身份证是否为空----------");

						if (!check(cell)) {
							logger.debug("判断身份证是否为空----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value)) {
							logger.debug("判断身份证是否为空----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}
						idCard = value;
						break;
					}

					case 2: {
						logger.debug("判断手机号是否为空----------");

						if (!check(cell)) {
							logger.debug("判断手机号是否为空----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value)) {
							logger.debug("判断手机号是否为空----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						phone = value;
						// 通过身份证查询用户
						aryaUser = aryaUserDao.findUserByIdCardNoAndNameAndTel(idCard, name, phone);
						if (aryaUser == null) {
							throw new AryaServiceException(ErrorCode.CODE_ARYA_USER_FOUND_NONE);
						}
						logger.debug("找到用户: " + aryaUser.getRealName());
						break;
					}

					case 3: {
						logger.debug("判断月份是否为空----------");

						if (!check(cell)) {
							logger.debug("月份为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(value)) {
							logger.debug("月份为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						// 验证该月份的薪资是否已存在
						Date date = DateTimeUtils.checkMonth(value);
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						int year = calendar.get(Calendar.YEAR);//年份
						int month = calendar.get(Calendar.MONTH) + 1;//月份

						List<AryaSalaryEntity> noRelease = aryaSalaryDao.findSalarysByUserMonthNoRelease(corporationEntity.getId(),
								aryaUser.getId(), year, month);
						String msg = aryaUser.getRealName() + value + "的薪资信息在未发布列表中已存在";

						if (noRelease != null && !noRelease.isEmpty()) {
							logger.debug(msg);
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}
						aryaSalaryEntity.setYear(year);
						aryaSalaryEntity.setMonth(month);

						break;
					}
					case 4: {
						logger.debug("判断基本工资是否为空----------");

						if (!check(cell)) {
							logger.debug("基本工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("基本工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setWage(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 5: {
						logger.debug("判断工作日出勤时间(小时)是否为空----------");

						if (!check(cell)) {
							logger.debug("工作日出勤时间(小时)为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("工作日出勤时间(小时)为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setTotalWorkdayHours(
								value == null ? 0 : Integer.parseInt(value)
						);
						break;
					}

					case 6: {
						logger.debug("判断平时加班工资是否为空----------");

						if (!check(cell)) {
							logger.debug("平时加班工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("平时加班工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setWorkdayOvertimeSalary(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 7: {
						logger.debug("判断平时加班工作时间(小时)是否为空----------");

						if (!check(cell)) {
							logger.debug("平时加班工作时间(小时)为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("平时加班工作时间(小时)为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setTotalOvertimeHours(
								value == null ? 0 : Integer.parseInt(value)
						);
						break;
					}

					case 8: {
						logger.debug("判断休息日加班工资是否为空----------");

						if (!check(cell)) {
							logger.debug("休息日加班工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("休息日加班工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setRestdayOvertimeSalary(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 9: {
						logger.debug("判断休息日工作时间(小时)是否为空----------");

						if (!check(cell)) {
							logger.debug("休息日工作时间(小时)为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("休息日工作时间(小时)为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setRestDayOvertimeHours(
								value == null ? 0 : Integer.parseInt(value)
						);
						break;
					}

					case 10: {
						logger.debug("判断国假加班工资是否为空----------");

						if (!check(cell)) {
							logger.debug("国假加班工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("国假加班工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setLegalHolidayOvertimeSalary(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 11: {
						logger.debug("判断国假加班时间(小时)是否为空----------");

						if (!check(cell)) {
							logger.debug("国假加班时间(小时)为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("国假加班时间(小时)为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setLegalHolidayOvertimeHours(
								value == null ? 0 : Integer.parseInt(value)
						);
						break;
					}

					case 12: {
						logger.debug("判断绩效奖金是否为空----------");

						if (!check(cell)) {
							logger.debug("绩效奖金为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("绩效奖金为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setPerformanceBonus(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 13: {
						logger.debug("判断津贴（餐补/车补/住房）是否为空----------");

						if (!check(cell)) {
							logger.debug("津贴（餐补/车补/住房）为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("津贴（餐补/车补/住房）为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setSubsidy(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 14: {
						logger.debug("判断事假扣款是否为空----------");

						if (!check(cell)) {
							logger.debug("事假扣款为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("事假扣款为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setCasualLeaveCut(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 15: {
						logger.debug("判断事假天数是否为空----------");

						if (!check(cell)) {
							logger.debug("事假天数为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("事假天数为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setCasualLeaveDays(
								value == null ? 0 : Integer.parseInt(value)
						);
						break;
					}

					case 16: {
						logger.debug("判断病假扣款是否为空----------");

						if (!check(cell)) {
							logger.debug("病假扣款为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("病假扣款为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setSickLeaveCut(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 17: {
						logger.debug("判断病假天数是否为空----------");

						if (!check(cell)) {
							logger.debug("病假天数为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("病假天数为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setSickLeaveDays(
								value == null ? 0 : Integer.parseInt(value)
						);
						break;
					}

					case 18: {
						logger.debug("判断其他扣款是否为空----------");

						if (!check(cell)) {
							logger.debug("其他扣款为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("其他扣款为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setOtherCut(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 19: {
						logger.debug("判断补款是否为空----------");

						if (!check(cell)) {
							logger.debug("补款为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("补款为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setRepayment(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 20: {
						logger.debug("判断社保（个人）是否为空----------");

						if (!check(cell)) {
							logger.debug("社保（个人）为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("社保（个人）为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setSoinPersonal(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 21: {
						logger.debug("判断公积金（个人）是否为空----------");

						if (!check(cell)) {
							logger.debug("公积金（个人）为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("公积金（个人）为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setHouseFundPersonal(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 22: {
						logger.debug("判断应税工资是否为空----------");

						if (!check(cell)) {
							logger.debug("应税工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("应税工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setTaxableSalary(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 23: {
						logger.debug("判断个税（个人）是否为空----------");

						if (!check(cell)) {
							logger.debug("个税（个人）为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("个税（个人）为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setPersonalTax(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 24: {
						logger.debug("判断应发工资是否为空----------");

						if (!check(cell)) {
							logger.debug("应发工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("应发工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setGrossSalary(
								turnStringToBigDecimal(value)
						);
						break;
					}

					case 25: {
						logger.debug("判断实发工资是否为空----------");

						if (!check(cell)) {
							logger.debug("实发工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}

						String value = cell.getStringCellValue();
						value = value.trim();

						if (!check(cell)) {
							logger.debug("实发工资为空或者NULL----------");
							throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
						}


						aryaSalaryEntity.setNetSalary(
								turnStringToBigDecimal(value)
						);
						break;
					}
				}

			}
			aryaSalaryEntity.setId(Utils.makeUUID());
			aryaSalaryEntity.setUserId(aryaUser.getId());
			aryaSalaryEntity.setCorpId(corporationEntity.getId());
			aryaSalaryEntity.setCreateTime(System.currentTimeMillis());
			aryaSalaryEntity.setDelete(false);
			aryaSalaryEntity.setPublished(false);
			logger.info("aryaSalaryEntity id: " + aryaSalaryEntity.getId());
			list.add(aryaSalaryEntity);
		}

		return list;
	}

	private boolean check(Cell value) {
		return value != null;

	}

	private boolean check(String value) {
		return !StringUtils.isBlank(value);
	}
}
