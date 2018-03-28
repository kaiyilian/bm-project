package com.bumu.arya.admin.salary.service.impl;

import com.bumu.arya.admin.salary.model.entity.SalaryRuleEntity;
import com.bumu.common.util.ListUtils;
import com.bumu.SysUtils;
import com.bumu.arya.AppShare;
import com.bumu.arya.IdcardValidator;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.admin.salary.controller.command.CreateSalaryCalculateRuleCommand;
import com.bumu.arya.admin.salary.controller.command.CreateSalaryCalculateRuleCommand.SalaryCalculateRuleTaxGear;
import com.bumu.arya.admin.salary.controller.command.QueryCalculateSalary2ModelsCommand;
import com.bumu.arya.admin.salary.controller.command.UpdateSalaryCalculateRuleCommand;
import com.bumu.arya.admin.salary.controller.command.UpdateSalaryUserInfoCommand;
import com.bumu.arya.admin.salary.model.*;
import com.bumu.arya.admin.salary.model.dao.SalaryRuleDao;
import com.bumu.arya.admin.salary.result.*;
import com.bumu.arya.admin.salary.service.PhoneService;
import com.bumu.arya.admin.salary.service.SalaryCalculateService;
import com.bumu.arya.admin.salary.service.SalaryFileService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.*;
import com.bumu.arya.model.entity.*;
import com.bumu.bran.common.Constants;
import com.bumu.common.util.ValidateUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.arya.admin.salary.model.SalaryCalculateModel;
import com.bumu.arya.admin.salary.model.SalaryModel;
import com.bumu.common.service.impl.BaseBumuService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.bumu.arya.admin.salary.result.SalaryCalculateRuleResult.SalaryCalculateRuleTaxGearResult;
import static com.bumu.arya.common.OperateConstants.*;

/**
 * @author CuiMengxin
 * @date 2016/6/2
 */
@Service
public class SalaryCalculateServiceImpl extends BaseBumuService implements SalaryCalculateService {
	Logger log = LoggerFactory.getLogger(SalaryCalculateServiceImpl.class);

	/**
	 * 薪资Dao
	 */
	@Autowired
	private AryaSalaryDao aryaSalaryDao;

	@Autowired
	AryaSalaryWeekDao salaryWeekDao;

	@Autowired
	DistrictDao districtDao;

	/**
	 * 公司Dao
	 */
	@Autowired
	private CorporationDao corporationDao;

	@Autowired
    SalaryFileService fileService;

	@Autowired
    CorporationService corporationService;

	@Autowired
    PhoneService phoneService;

	/**
	 * 用户Dao
	 */
	@Autowired
	private AryaUserDao aryaUserDao;

	@Autowired
    SalaryRuleDao salaryRuleDao;

	@Autowired
	AryaDepartmentDao aryaDepartmentDao;

	@Autowired
    OpLogService opLogService;


	@Override
	public SalaryCalculateModel calculateSalaryToModel(BigDecimal taxableSalary, SalaryCalculateRuleModel ruleModel) {
		SalaryCalculateModel monthModel = new SalaryCalculateModel();
		monthModel.setTaxableSalary(taxableSalary);
		//计算个税
		BigDecimal tax = new BigDecimal("0").setScale(2, BigDecimal.ROUND_UP);
		if (ruleModel.getThresholdTax() == null) {
			for (SalaryCalculateRuleGearModel taxGear : getDescTaxGears(ruleModel.getTaxGears())) {
				if (taxableSalary.compareTo(taxGear.getGear()) >= 0) {
					tax = taxableSalary.multiply(taxGear.getTaxRate()).setScale(2, BigDecimal.ROUND_UP);
					break;
				}
			}
		} else {
			if (taxableSalary.subtract(ruleModel.getThresholdTax()).compareTo(BigDecimal.valueOf(80000)) > 0) {
				tax = (taxableSalary.subtract(ruleModel.getThresholdTax())).multiply(BigDecimal.valueOf(0.45)).setScale(2, BigDecimal.ROUND_UP).subtract(BigDecimal.valueOf(13505));
			} else if (taxableSalary.subtract(ruleModel.getThresholdTax()).compareTo(BigDecimal.valueOf(55000)) > 0) {
				tax = (taxableSalary.subtract(ruleModel.getThresholdTax())).multiply(BigDecimal.valueOf(0.35)).setScale(2, BigDecimal.ROUND_UP).subtract(BigDecimal.valueOf(5505));
			} else if (taxableSalary.subtract(ruleModel.getThresholdTax()).compareTo(BigDecimal.valueOf(35000)) > 0) {
				tax = (taxableSalary.subtract(ruleModel.getThresholdTax())).multiply(BigDecimal.valueOf(0.3)).setScale(2, BigDecimal.ROUND_UP).subtract(BigDecimal.valueOf(2755));
			} else if (taxableSalary.subtract(ruleModel.getThresholdTax()).compareTo(BigDecimal.valueOf(9000)) > 0) {
				tax = (taxableSalary.subtract(ruleModel.getThresholdTax())).multiply(BigDecimal.valueOf(0.25)).setScale(2, BigDecimal.ROUND_UP).subtract(BigDecimal.valueOf(1005));
			} else if (taxableSalary.subtract(ruleModel.getThresholdTax()).compareTo(BigDecimal.valueOf(4500)) > 0) {
				tax = (taxableSalary.subtract(ruleModel.getThresholdTax())).multiply(BigDecimal.valueOf(0.2)).setScale(2, BigDecimal.ROUND_UP).subtract(BigDecimal.valueOf(555));
			} else if (taxableSalary.subtract(ruleModel.getThresholdTax()).compareTo(BigDecimal.valueOf(1500)) > 0) {
				tax = (taxableSalary.subtract(ruleModel.getThresholdTax())).multiply(BigDecimal.valueOf(0.1)).setScale(2, BigDecimal.ROUND_UP).subtract(BigDecimal.valueOf(105));
			} else if (taxableSalary.subtract(ruleModel.getThresholdTax()).compareTo(BigDecimal.valueOf(0)) > 0) {
				tax = (taxableSalary.subtract(ruleModel.getThresholdTax())).multiply(BigDecimal.valueOf(0.03)).setScale(2, BigDecimal.ROUND_UP);
			} else {
				tax = BigDecimal.valueOf(0);
			}
		}
		monthModel.setPersonalTax(tax);
		//计算个税服务费
		BigDecimal serviceCharge = tax.multiply(ruleModel.getServiceChargeTaxRate()).setScale(2, BigDecimal.ROUND_UP);
		monthModel.setServiceCharge(serviceCharge);
		if (ruleModel.getThresholdTax() == null) {
			//计算税后薪资
			monthModel.setNetSalary(taxableSalary.subtract(tax).subtract(serviceCharge).setScale(2, BigDecimal.ROUND_UP));
			//计算薪资服务费
			monthModel.setBrokerage(taxableSalary.multiply(ruleModel.getBrokerageRate()).setScale(2, BigDecimal.ROUND_UP));
		} else {
			//计算税后薪资
			monthModel.setNetSalary(taxableSalary.subtract(tax));
			//计算薪资服务费
			monthModel.setBrokerage(ruleModel.getBrokerage());
		}
		return monthModel;
	}


	/**
	 * 生成薪资计算的数据结构
	 *
	 * @param groupId
	 * @param year
	 * @param month
	 * @param week
	 * @param fileName
	 * @return
	 * @throws AryaServiceException
	 */
	private SalaryCalculateStructure generateSalaryCalculateStructure(String groupId, String departmentId, int year, int month, Integer week, String fileName) throws AryaServiceException {
		//薪资计算数据结构
		SalaryCalculateStructure calculateStructure = new SalaryCalculateStructure();
		String ruleId;

		AryaDepartmentEntity aryaDepartmentEntity = new AryaDepartmentEntity();
		aryaDepartmentEntity.setName("-");
		//通过企业那集团和企业的薪资计算规则
		if (StringUtils.isNotBlank(departmentId)) {
			aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(departmentId);
			if (aryaDepartmentEntity == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_NOT_EXIST);
			}
			if (StringUtils.isAnyBlank(aryaDepartmentEntity.getSalaryRuleId())) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_ADD_DEPARTMENT_RULE_FIRST);
			}
			ruleId = aryaDepartmentEntity.getSalaryRuleId();
			groupId = aryaDepartmentEntity.getCorpId();//赋值集团id
		} else {
			//拿集团的薪资规则
			CorporationEntity groupEntity = corporationDao.findCorporationById(groupId);
			if (groupEntity == null) {
				throw new AryaServiceException(ErrorCode.CODE_CORPORATION_GROUP_NOT_FIND);
			}
			if (StringUtils.isAnyBlank(groupEntity.getSalaryRuleId())) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_ADD_GROUP_RULE_FIRST);
			}
			ruleId = groupEntity.getSalaryRuleId();
		}
		calculateStructure.groupId = groupId;

		//查询计算规则
		SalaryRuleEntity salaryRuleEntity = salaryRuleDao.findRuleById(ruleId);
		if (salaryRuleEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_FOUND);
		}
		// 计算规则详情
		calculateStructure.setRuleModel(getSalaryCalculateRuleModel(salaryRuleEntity));

		// 阅读Excel中的薪资信息
		SalaryCalculateExcelFileReadResult fileReadResult = fileService.readSalaryCalculateExcelFile(fileName);
		// 所有的薪资信息
		calculateStructure.salaryModels = fileReadResult.getModels();
		// 导入日志
		calculateStructure.log = fileReadResult.getLog();
		List<String> ignoredLog = new ArrayList<>();//有问题的记录的信息


		//收集身份证号，地区名称，公司名称，用于查询用户，地区和公司
		for (SalaryModel salaryModel : calculateStructure.salaryModels) {
			salaryModel.setDepartmentId(departmentId);
			salaryModel.setDepartmentName(aryaDepartmentEntity.getName());

			//判断税前薪资是否合法
			if (salaryModel.getTaxableSalary() == null) {
				salaryModel.setWrong(true);
				ignoredLog.add(salaryModel.getName() + "的税前薪资不存在，请解决。");
			}

			//收集身份证号码
			if (!StringUtils.isAnyBlank(salaryModel.getIdcardNo()) && IdcardValidator.isValidatedAllIdcard(salaryModel.getIdcardNo())) {
				if (!calculateStructure.userIdcardNos.contains(salaryModel.getIdcardNo())) {
					calculateStructure.userIdcardNos.add(salaryModel.getIdcardNo());
				} else {
					//有重复身份证号码
					calculateStructure.userDuplicateIdcardNos.add(salaryModel.getIdcardNo());
				}
			} else {
				salaryModel.setIdCardStatus(SalaryModel.IDCARD_WRONG);
				salaryModel.setWrong(true);
				ignoredLog.add(salaryModel.getName() + "的身份证号码" + salaryModel.getIdcardNo() + "不正确，请解决。");
			}

			//收集手机号码
			if (SysUtils.checkPhoneNo(salaryModel.getPhoneNo())) {
				if (!calculateStructure.userPhonesNos.contains(salaryModel.getPhoneNo())) {
					calculateStructure.userPhonesNos.add(salaryModel.getPhoneNo());
				} else {
					//有重复手机号码
					calculateStructure.userDuplicatePhonesNos.add(salaryModel.getPhoneNo());
				}
			} else if (StringUtils.isAnyBlank(salaryModel.getPhoneNo())) {
				//略过，下面会检查该用户是否存在，如果存在则沿用旧手机号码，不存在则使用系统生成的号码
			} else {
				salaryModel.setPhoneNoStatus(SalaryModel.PHONE_WRONG);
				salaryModel.setWrong(true);
				ignoredLog.add(salaryModel.getName() + "的手机号" + salaryModel.getPhoneNo() + "不正确，请解决。");
			}
			//判断地区名称是否需要加上“市“
			if (salaryModel.getDistrict() != null && salaryModel.getDistrict().length() > 0) {
				String lastWord = salaryModel.getDistrict().substring(salaryModel.getDistrict().length() - 1);
				if (!Objects.equals(lastWord, "省") && !Objects.equals(lastWord, "市") && !Objects.equals(lastWord, "区") && !Objects.equals(lastWord, "县")) {
					salaryModel.setDistrict(salaryModel.getDistrict() + "市");
				}
			}

			//收集地区名称
			if (!calculateStructure.districtNames.contains(salaryModel.getDistrict())) {
				calculateStructure.districtNames.add(salaryModel.getDistrict());
			}

			//收集公司名称
			if (!calculateStructure.corpNames.contains(salaryModel.getCorp())) {
				calculateStructure.corpNames.add(salaryModel.getCorp());
			}

			//检查银行账号长度是否过长
			if (salaryModel.getBankAccount() != null && salaryModel.getBankAccount().length() > 20) {
				salaryModel.setWrong(true);
				salaryModel.setBankAccountWrong(true);
				ignoredLog.add(salaryModel.getName() + "的银行账号" + salaryModel.getBankAccount() + "过长，请解决。");
			}
		}

		//查询地区
		if (calculateStructure.districtNames.size() > 0) {
			List<DistrictEntity> districtEntities = districtDao.findDistrictListByNames(calculateStructure.districtNames);
			for (DistrictEntity districtEntity : districtEntities) {
				calculateStructure.districtEntitiesMap.put(districtEntity.getDistrictName(), districtEntity);
			}
		}

		//查询公司
		if (calculateStructure.corpNames.size() > 0) {
			List<CorporationEntity> corporationEntities = corporationDao.findCorporationListByNames(calculateStructure.corpNames, groupId);
			for (CorporationEntity corporationEntity : corporationEntities) {
				calculateStructure.existCorporationEntitiesMap.put(corporationEntity.getName(), corporationEntity);
				calculateStructure.corporationName2IdMap.put(corporationEntity.getName(), corporationEntity.getId());
			}
		}

		//分别通过手机号码和身份证号码查询用户
		List<AryaUserEntity> userEntitiesByIdcard = aryaUserDao.findUserByIdcardNos(calculateStructure.userIdcardNos);
		List<AryaUserEntity> userEntitiesByPhone = aryaUserDao.findUserByPhoneNos(calculateStructure.userPhonesNos);

		// 把所有导入EXCEL中没有的用户身份证 加进来
		for (AryaUserEntity userEntity : userEntitiesByIdcard) {
			calculateStructure.existUserEntitiesFindByIdcardMap.put(userEntity.getIdcardNo().toUpperCase(), userEntity);
			//收集已存在的用户id
			if (!calculateStructure.existUserIds.contains(userEntity.getId())) {
				calculateStructure.existUserIds.add(userEntity.getId());
			}
		}

		//构建已存在用户id和已存在公司id的map，用于查询用户在公司或连同部门下本月的各种薪资
		for (SalaryModel salaryModel : calculateStructure.salaryModels) {
			if (!calculateStructure.existUserEntitiesFindByIdcardMap.containsKey(salaryModel.getIdcardNo()) || !calculateStructure.corporationName2IdMap.containsKey(salaryModel.getCorp())) {
				continue;
			}
			AryaUserEntity existUserEntity = calculateStructure.existUserEntitiesFindByIdcardMap.get(salaryModel.getIdcardNo());
			if (!calculateStructure.existUserId2existCorpIdMap.containsKey(existUserEntity.getId())) {
				calculateStructure.existUserId2existCorpIdMap.put(existUserEntity.getId(), calculateStructure.corporationName2IdMap.get(salaryModel.getCorp()));
			}
		}

		for (AryaUserEntity userEntity : userEntitiesByPhone) {
			calculateStructure.existUserEntitiesFindByPhoneMap.put(userEntity.getPhoneNo(), userEntity);
		}

		//检查模型记录中是否存在相同手机号码和相同身份证的情况，也检查手机号是否为空
		for (SalaryModel compareSalaryModel : calculateStructure.salaryModels) {
			if (StringUtils.isAnyBlank(compareSalaryModel.getPhoneNo())) {
				//如果手机号为空，判断用户是否存在，存在则沿用手机号，不存在则生成系统手机号
				AryaUserEntity existUser = calculateStructure.existUserEntitiesFindByIdcardMap.get(compareSalaryModel.getIdcardNo());
				if (existUser == null) {
					compareSalaryModel.setPhoneNoStatus(SalaryModel.PHONE_SYSTEM);
					String newPhoneNo = phoneService.getNewSystemPhoneNumber();
					calculateStructure.log.add("新用户，" + compareSalaryModel.getName() + "的手机号" + compareSalaryModel.getPhoneNo() + "为空，已替换成系统生成的手机号" + newPhoneNo + "。");
					compareSalaryModel.setPhoneNo(newPhoneNo);
				} else {
					calculateStructure.log.add(compareSalaryModel.getName() + "的手机号码" + compareSalaryModel.getPhoneNo() + "为空，沿用旧手机号码" + existUser.getPhoneNo() + "。");
					compareSalaryModel.setPhoneNo(existUser.getPhoneNo());
				}
			}
			if (calculateStructure.userDuplicateIdcardNos.contains(compareSalaryModel.getIdcardNo())) {
				//重复身份证号码
				ignoredLog.add(compareSalaryModel.getName() + "的身份证号码" + compareSalaryModel.getIdcardNo() + "在Excel文件中与他人重复，请解决。");
				compareSalaryModel.setWrong(true);//标记有问题
				compareSalaryModel.setIdCardStatus(SalaryModel.IDCARD_CONFLICT);//标记同文件内冲突
			}
			//重复手机号相同
			if (calculateStructure.userDuplicatePhonesNos.contains(compareSalaryModel.getPhoneNo())) {
				ignoredLog.add(compareSalaryModel.getName() + "的手机号码" + compareSalaryModel.getPhoneNo() + "在Excel文件中与他人重复，请解决。");
				compareSalaryModel.setWrong(true);//标记有问题
				compareSalaryModel.setPhoneNoStatus(SalaryModel.PHONE_CONFLICT);//标记冲突
			}
		}

		//校验模型的地区，公司，用户
		for (int i = 0; i < calculateStructure.salaryModels.size(); i++) {
			SalaryModel salaryModel = calculateStructure.salaryModels.get(i);
			if (salaryModel.getIdcardNo() == null) {
				salaryModel.setIdcardNo("N/A");
			}
			if (!calculateStructure.districtEntitiesMap.containsKey(salaryModel.getDistrict())) {
				ignoredLog.add(salaryModel.getName() + "的地区错误，请解决。");
				salaryModel.setDistrictWrong(true);//标记地区错误
				salaryModel.setWrong(true);//标记有问题
			} else {
				DistrictEntity districtEntity = calculateStructure.districtEntitiesMap.get(salaryModel.getDistrict());
				salaryModel.setDistrictId(districtEntity.getId());
			}

			//检查公司是否新增
			if (!calculateStructure.existCorporationEntitiesMap.containsKey(salaryModel.getCorp())) {
				salaryModel.setNewCorp(true);//标记新公司
			} else {
				CorporationEntity corporationEntity = calculateStructure.existCorporationEntitiesMap.get(salaryModel.getCorp());
				salaryModel.setCorpId(corporationEntity.getId());
			}

			salaryModel.setNewUser(true);
			//与db用户检查
			for (AryaUserEntity userEntityIdcard : userEntitiesByIdcard) {
				//检查相同身份证号码的用户手机号码是否需要更新
				if (ValidateUtils.isIdCardEquals(salaryModel.getIdcardNo(), userEntityIdcard.getIdcardNo().toUpperCase())) {
					//标记该模型记录检查过手机号。
					salaryModel.setPhoneNoChecked(true);
					salaryModel.setNewUser(false);
					//赋值用户id
					salaryModel.setUserId(userEntityIdcard.getId());
					//身份证相同的不同用户给予提示
					if (!salaryModel.getName().equals(userEntityIdcard.getRealName())) {
						ignoredLog.add(salaryModel.getName() + "的身份证" + salaryModel.getIdcardNo() + "与" + userEntityIdcard.getRealName() + "的相同，请检查确认后导入" + "。");
						salaryModel.setWrong(true);//标记有问题
						salaryModel.setIdCardStatus(SalaryModel.IDCARD_CONFLICT);//标记身份证冲突
					}
					//身份证号码相同，但是手机号码不同
					if (!salaryModel.getPhoneNo().equals(userEntityIdcard.getPhoneNo())) {
						if (!salaryModel.getIgnore()) {
							//初始化map
							if (calculateStructure.updateUserEntityMap == null) {
								calculateStructure.updateUserEntityMap = new HashMap<>();
							}
							if (calculateStructure.updateUserEntityMap.containsKey(salaryModel.getIdcardNo())) {
								calculateStructure.updateUserEntityMap.remove(salaryModel.getIdcardNo());
							}
							//再检查手机号码在DB有没有存在冲突
							if (calculateStructure.existUserEntitiesFindByPhoneMap.containsKey(salaryModel.getPhoneNo())) {
								AryaUserEntity samePhoneNoUser = calculateStructure.existUserEntitiesFindByPhoneMap.get(salaryModel.getPhoneNo());
								if (ValidateUtils.isIdCardEquals(samePhoneNoUser.getIdcardNo(), salaryModel.getIdcardNo())) {
									//如果是同一人则更新手机号
									userEntityIdcard.setPhoneNo(salaryModel.getPhoneNo());
									calculateStructure.updateUserEntityMap.put(salaryModel.getIdcardNo(), userEntityIdcard);
									salaryModel.setPhoneNoStatus(SalaryModel.PHONE_NEW);//标记新手机号码
								} else {
									//有冲突，禁止更新
									calculateStructure.log.add(salaryModel.getName() + "的手机号码" + salaryModel.getPhoneNo() + "被他人占用，沿用旧手机号码" + userEntityIdcard.getPhoneNo() + "。");
									salaryModel.setPhoneNo(userEntityIdcard.getPhoneNo());
								}
							}
						}
					}
					break;
				}
			}
			//如果模型记录没有校验过手机号码是否与db用户冲突
			if (!salaryModel.isPhoneNoChecked()) {
				//只存在新用户
				salaryModel.setPhoneNoChecked(true);
				if (calculateStructure.existUserEntitiesFindByPhoneMap.containsKey(salaryModel.getPhoneNo())) {
					AryaUserEntity userEntity = calculateStructure.existUserEntitiesFindByPhoneMap.get(salaryModel.getPhoneNo());
					if (!ValidateUtils.isIdCardEquals(salaryModel.getIdcardNo(), userEntity.getIdcardNo())) {
						//如果手机号相同，但是身份证号码不同，增存在冲突
						String newPhoneNo = phoneService.getNewSystemPhoneNumber();
						calculateStructure.log.add("新用户，" + salaryModel.getName() + "的手机号码" + salaryModel.getPhoneNo() + "被他人占用，替换成系统生成手机号" + newPhoneNo + "。");
						salaryModel.setPhoneNo(newPhoneNo);//赋值系统手机号
						salaryModel.setPhoneNoStatus(SalaryModel.PHONE_SYSTEM);//标记系统生成手机号码
					}
				}
			}
		}

		//检查DB薪资，标记覆盖
		//查询已存在相同年月周的薪资，用于标记记录是否存在覆盖情况
		//如果是周薪
		if (week != null) {
			if (calculateStructure.existUserId2existCorpIdMap.size() > 0) {
				if (calculateStructure.existWeekSalaryEntities == null) {
					calculateStructure.existWeekSalaryEntities = new ArrayList<>();
				}
				for (String existUserId : calculateStructure.existUserId2existCorpIdMap.keySet()) {
					AryaSalaryWeekEntity salaryWeekEntity = salaryWeekDao.findUserWeekExistSalary(existUserId, calculateStructure.existUserId2existCorpIdMap.get(existUserId), departmentId, year, month, week);
					if (salaryWeekEntity != null) {
						calculateStructure.existWeekSalaryEntities.add(salaryWeekEntity);
					}
				}

				if (calculateStructure.existOtherWeekSalaryEntities == null) {
					calculateStructure.existOtherWeekSalaryEntities = new ArrayList<>();
				}
				//查找用户已存在的其他周的薪资
				//当本周某用户税前薪资和其他周相同时，确认是否输入正确
				List<AryaSalaryWeekEntity> existOtherWeekSalaryEntities;
				for (String aryaUserId : calculateStructure.existUserId2existCorpIdMap.keySet()) {
					existOtherWeekSalaryEntities = salaryWeekDao.findUserThisMonthOtherWeekSalary(aryaUserId, calculateStructure.existUserId2existCorpIdMap.get(aryaUserId), departmentId, year, month, week);
					if (ListUtils.checkNullOrEmpty(existOtherWeekSalaryEntities)) {
						continue;
					}
					for (SalaryModel salaryModel : calculateStructure.salaryModels) {
						if (!aryaUserId.equals(salaryModel.getUserId())) {
							continue;
						}
						for (AryaSalaryWeekEntity weekSalaryEntity : existOtherWeekSalaryEntities) {
							if (weekSalaryEntity.getTaxableSalary().compareTo(salaryModel.getTaxableSalary()) == 0) {
								calculateStructure.log.add(salaryModel.getName() + "的税前薪资与本月其它周存在相同记录，请确认。");
								break;
							}
						}
					}
				}
			}

			//标记记录是否存在覆盖DB情况
			if (calculateStructure.existWeekSalaryEntities != null && calculateStructure.existWeekSalaryEntities.size() > 0) {
				for (SalaryModel salaryModel : calculateStructure.salaryModels) {
					for (AryaSalaryWeekEntity weekSalaryEntity : calculateStructure.existWeekSalaryEntities) {
						//如果DB不存在该记录
						if (!weekSalaryEntity.getUserId().equals(salaryModel.getUserId())) {
							continue;
						}
						//如果公司不同则跳过
						if (!weekSalaryEntity.getCorpId().equals(salaryModel.getCorpId())) {
							continue;
						}
						//如果DB存在该记录并且税前薪资相同，则标记忽略，不更新
						if (weekSalaryEntity.getTaxableSalary().compareTo(salaryModel.getTaxableSalary()) == 0) {
							salaryModel.setIgnore(true);
							calculateStructure.log.add(salaryModel.getName() + "的记录与数据库中重复，忽略该条。");
							break;
						}
						//如果DB存在该记录并且税前薪资不同，则标记覆盖DB记录
						if (weekSalaryEntity.getTaxableSalary().compareTo(salaryModel.getTaxableSalary()) != 0) {
							salaryModel.setOverrideStatus(SalaryModel.OVERRIDER_DB);//标记覆盖DB记录
							break;
						}
					}
				}
			}
		} else {
			//月薪
			if (calculateStructure.existUserId2existCorpIdMap.size() > 0) {
				if (calculateStructure.existMonthSalaryEntities == null) {
					calculateStructure.existMonthSalaryEntities = new ArrayList<>();
				}
				for (String existUserId : calculateStructure.existUserId2existCorpIdMap.keySet()) {
					AryaSalaryEntity existSalary = aryaSalaryDao.findUserExistMonthSalary(existUserId, calculateStructure.existUserId2existCorpIdMap.get(existUserId), departmentId, year, month);
					if (existSalary != null) {
						calculateStructure.existMonthSalaryEntities.add(existSalary);
					}
				}
			}

			//标记记录是否存在覆盖DB情况
			if (calculateStructure.existMonthSalaryEntities != null && calculateStructure.existMonthSalaryEntities.size() > 0) {
				for (SalaryModel salaryModel : calculateStructure.salaryModels) {
					for (AryaSalaryEntity monthSalaryEntity : calculateStructure.existMonthSalaryEntities) {
						//如果DB不存在该记录
						if (!monthSalaryEntity.getUserId().equals(salaryModel.getUserId())) {
							continue;
						}
						//如果DB存在该记录并且税前薪资相同，则标记忽略，不更新
						if (monthSalaryEntity.getTaxableSalary().compareTo(salaryModel.getTaxableSalary()) == 0) {
							salaryModel.setIgnore(true);
							calculateStructure.log.add(salaryModel.getName() + "的记录与数据库中重复，忽略该条。");
							break;
						}
						//如果DB存在该记录并且税前薪资不同，则标记覆盖DB记录
						if (monthSalaryEntity.getTaxableSalary().compareTo(salaryModel.getTaxableSalary()) != 0) {
							salaryModel.setOverrideStatus(SalaryModel.OVERRIDER_DB);//标记覆盖DB记录
							break;
						}
					}
				}
			}
		}

		//过滤掉文件中被忽略的记录
		for (int i = 0; i < calculateStructure.salaryModels.size(); i++) {
			SalaryModel salaryModel = calculateStructure.salaryModels.get(i);
			if (salaryModel.getIgnore()) {
				calculateStructure.ignoredSalaryModels.add(salaryModel);//移动到忽略的数组中去
				calculateStructure.salaryModels.remove(salaryModel);//移除
				i--;
			}
		}
		//过滤掉文件中有问题的记录
		for (int i = 0; i < calculateStructure.salaryModels.size(); i++) {
			SalaryModel salaryModel = calculateStructure.salaryModels.get(i);
			if (salaryModel.isWrong()) {
				calculateStructure.wrongSalaryModels.add(salaryModel);//移动到有问题的数组中去
				calculateStructure.salaryModels.remove(salaryModel);//移除
				i--;
			}
		}
		if (ignoredLog.size() > 0) {
			ignoredLog.add(0, "请解决以下问题才能导入-----------------------------------------------------------------");
			ignoredLog.add("请解决以上问题才能导入-----------------------------------------------------------------");
			calculateStructure.log.addAll(ignoredLog);
		}
		return calculateStructure;
	}

	@Override
	public SalaryCalculateListResult calculateSalary(String groupCorpId, String departmentId, int year, int month, Integer week, String fileName, Integer page, Integer pageSize) throws AryaServiceException {
		//如果week == 0 则为新增一批薪资
		if (week != null && week == 0) {
			week = generateSalaryBatchNo();
		}
		ArrayList<SalaryCalculateListResult.SalaryCalculateResult> listResult = new ArrayList<>();
		SalaryCalculateListResult calculateResult = new SalaryCalculateListResult();
		//生成数据结构
		SalaryCalculateStructure calculateStructure = generateSalaryCalculateStructure(groupCorpId, departmentId, year, month, week, fileName);
		List<SalaryModel> legalSalaryModels = new ArrayList<>();
		legalSalaryModels.addAll(calculateStructure.salaryModels);

		//开始计算
		try {
			//合并那些忽略的记录
			calculateStructure.salaryModels.addAll(calculateStructure.getIgnoredSalaryModels());
			//合并那些有问题的记录
			calculateStructure.salaryModels.addAll(calculateStructure.getWrongSalaryModels());
			for (SalaryModel model : calculateStructure.salaryModels) {
				//计算薪资
				SalaryCalculateModel calculateModel = calculateMonthOrWeekSalary(year, month, week, model, calculateStructure);
				if (calculateModel == null) {
					continue;
				}
				model.setPersonalTax(calculateModel.getPersonalTax());
				model.setServiceCharge(calculateModel.getServiceCharge());
				model.setBrokerage(calculateModel.getBrokerage());
				model.setNetSalary(calculateModel.getNetSalary());

				SalaryCalculateListResult.SalaryCalculateResult result = new SalaryCalculateListResult.SalaryCalculateResult();
				SalaryCalculateListResult.SalaryCalculateResult.UserName name = new SalaryCalculateListResult.SalaryCalculateResult.UserName();
				name.setName(model.getName());
				name.setNewUser(model.getNewUser());
				SalaryCalculateListResult.SalaryCalculateResult.UserIdcard idcard = new SalaryCalculateListResult.SalaryCalculateResult.UserIdcard();
				idcard.setIdcardStatus(model.getIdCardStatus());
				if (model.getNewUser() && model.getIdCardStatus() == SalaryModel.IDCARD_NORMAL) {
					idcard.setIdcardStatus(SalaryModel.IDCARD_NEW);
				}
				idcard.setIdcardNo(model.getIdcardNo());
				SalaryCalculateListResult.SalaryCalculateResult.City city = new SalaryCalculateListResult.SalaryCalculateResult.City();
				city.setDistrictWrong(model.getDistrictWrong());
				city.setCity(model.getDistrict());
				SalaryCalculateListResult.SalaryCalculateResult.Company corp = new SalaryCalculateListResult.SalaryCalculateResult.Company();
				corp.setCorp(model.getCorp());
				corp.setNewCorp(model.getNewCorp());
				SalaryCalculateListResult.SalaryCalculateResult.TaxableSalary taxableSalary = new SalaryCalculateListResult.SalaryCalculateResult.TaxableSalary();
				taxableSalary.setTaxableSalary(model.getTaxableSalary().toString());
				SalaryCalculateListResult.SalaryCalculateResult.PersonalTax personalTax = new SalaryCalculateListResult.SalaryCalculateResult.PersonalTax();
				personalTax.setPersonalTax(calculateModel.getPersonalTaxStr());
				SalaryCalculateListResult.SalaryCalculateResult.ServiceCharge serviceCharge = new SalaryCalculateListResult.SalaryCalculateResult.ServiceCharge();
				serviceCharge.setServiceCharge(calculateModel.getServiceChargeStr());
				SalaryCalculateListResult.SalaryCalculateResult.NetSalary netSalary = new SalaryCalculateListResult.SalaryCalculateResult.NetSalary();
				netSalary.setNetSalary(calculateModel.getNetSalaryStr());
				SalaryCalculateListResult.SalaryCalculateResult.Brokerage brokerage = new SalaryCalculateListResult.SalaryCalculateResult.Brokerage();
				brokerage.setBrokerage(calculateModel.getBrokerageStr());
				SalaryCalculateListResult.SalaryCalculateResult.BankAccount bankAccount = new SalaryCalculateListResult.SalaryCalculateResult.BankAccount();
				bankAccount.setBankAccountId(model.getBankAccount());
				bankAccount.setAccountWrong(model.isBankAccountWrong());
				SalaryCalculateListResult.SalaryCalculateResult.Phone phone = new SalaryCalculateListResult.SalaryCalculateResult.Phone();
				phone.setPhoneNo(model.getPhoneNo());
				phone.setPhoneStatus(model.getPhoneNoStatus());
				//如果是新增用户，并且手机号也是正常的
				if (model.getNewUser() && model.getPhoneNoStatus() == SalaryModel.PHONE_NORMAL) {
					model.setPhoneNoStatus(SalaryModel.PHONE_NEW);
					phone.setPhoneStatus(SalaryModel.PHONE_NEW);
				}

				result.setCity(city);
				result.setName(name);
				result.setCorp(corp);
				result.setDepartmentName(model.getDepartmentName());
				result.setIdcardNo(idcard);
				result.setTaxableSalary(taxableSalary);
				result.setPersonalTax(personalTax);
				result.setServiceCharge(serviceCharge);
				result.setBrokerage(brokerage);
				result.setNetSalary(netSalary);
				result.setBankAccount(bankAccount);
				result.setPhone(phone);
				result.setIgnore(model.getIgnore());
				listResult.add(result);
			}
			if (calculateStructure.getRuleModel().getThresholdTax() == null) {
				calculateResult.setRuleType(1);
			} else {
				calculateResult.setRuleType(2);
			}
			calculateResult.setFileName(fileName);
			//calculateResult.setCalculateResults(listResult);
			int total = listResult.size();
			calculateResult.setPages(Utils.calculatePages(total, pageSize));
			calculateResult.setCalculateResults(listResult.subList(pageSize * (page - 1), ((pageSize * page) > total ? total : (pageSize * page))));
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_FAILD);
		}
		//统计
		List<SalaryModel> mergeredSalaryModels = mergerSalaryModelsAndDBRecords(legalSalaryModels, groupCorpId, departmentId, year, month, week);
		if (mergeredSalaryModels != null && mergeredSalaryModels.size() > 0) {
			calculateResult.setStatistics(countCorpSalary(mergeredSalaryModels));
		}
		calculateStructure.log.add("总共计算" + calculateStructure.salaryModels.size() + "条，其中忽略" + calculateStructure.ignoredSalaryModels.size() + "条，有问题" + calculateStructure.getWrongSalaryModels().size() + "条。");
		calculateResult.setLog(StringUtils.join(calculateStructure.log, "\n"));
		log.info(calculateResult.getLog());
		if (calculateStructure.getWrongSalaryModels().size() > 0) {
			calculateResult.setCanImport(false);
		} else {
			calculateResult.setCanImport(true);
		}
		return calculateResult;
	}

	/**
	 * 计算月薪或者周薪统一入口
	 *
	 * @param year
	 * @param month
	 * @param week
	 * @param model
	 * @param calculateStructure
	 * @return
	 */
	private SalaryCalculateModel calculateMonthOrWeekSalary(Integer year, Integer month, Integer week, SalaryModel model, SalaryCalculateStructure calculateStructure) {
		SalaryCalculateModel calculateModel;
		//如果是月薪，或者第一周
		if (week == null || week == 1) {
			calculateModel = calculateSalaryToModel(model.getTaxableSalary(), calculateStructure.getRuleModel());
		} else {
			//需要用户id
			AryaUserEntity userEntity = calculateStructure.existUserEntitiesFindByIdcardMap.get(model.getIdcardNo());
			if (userEntity == null) {
				//增员，第一次计算周薪
				calculateModel = calculateSalaryToModel(model.getTaxableSalary(), calculateStructure.getRuleModel());
			} else {
				//计算周薪资
				calculateModel = calculateWeekSalary(model.getUserId(), model.getCorpId(), model.getDepartmentId(), year, month, week, model.getTaxableSalary(), calculateStructure.ruleModel);
				//用户没有过该月的该周之前周的薪资
				if (calculateModel == null) {
					calculateModel = calculateSalaryToModel(model.getTaxableSalary(), calculateStructure.getRuleModel());
				}
			}
		}
		return calculateModel;
	}

	/**
	 * 计算周薪资
	 *
	 * @param userId
	 * @param corpId
	 * @param departmentId
	 * @param year
	 * @param month
	 * @param week
	 * @param taxableSalary
	 * @return
	 */
	private SalaryCalculateModel calculateWeekSalary(String userId, String corpId, String departmentId, Integer year, Integer month, Integer week, BigDecimal taxableSalary, SalaryCalculateRuleModel ruleModel) {
		List<AryaSalaryWeekEntity> weekEntities = salaryWeekDao.findUserBeforeWeekSalary(userId, corpId, departmentId, year, month, week);
		if (weekEntities.size() == 0) {
			return null;
		}
		return calculateWeekSalaryBaseOnForwardWeekSalaries(weekEntities, taxableSalary, ruleModel);
	}

	@Override
	public SalaryCalculateImportResult importCalculateSalary(String userId, String groupId, String departmentId, int year, int month, Integer week, String fileName) throws AryaServiceException {
		//如果week == 0 则为新增一批薪资
		if (week != null && week == 0) {
			week = generateSalaryBatchNo();
		}

		SalaryCalculateImportResult importResult = new SalaryCalculateImportResult();
		//生成数据结构
		SalaryCalculateStructure calculateStructure = generateSalaryCalculateStructure(groupId, departmentId, year, month, week, fileName);
		//赋值记录总数
		importResult.setTotalCount((calculateStructure.salaryModels != null ? calculateStructure.salaryModels.size() : 0)
				+ (calculateStructure.ignoredSalaryModels != null ? calculateStructure.ignoredSalaryModels.size() : 0)
				+ (calculateStructure.wrongSalaryModels != null ? calculateStructure.wrongSalaryModels.size() : 0));
		if (calculateStructure.wrongSalaryModels != null && calculateStructure.wrongSalaryModels.size() > 0) {
			calculateStructure.log.add("导入失败，请先修改导入文件中存在的问题。");
			importResult.setLog(StringUtils.join(calculateStructure.log, "\n"));
			log.info(importResult.getLog());
			return importResult;
		}
		calculateStructure.log.add("开始导入");
		//初始化
		calculateStructure.newCorpEntities = new ArrayList<>();
		calculateStructure.newUserEntities = new ArrayList<>();

		//遍历合法的薪资模型，生成新公司
		for (SalaryModel salaryModel : calculateStructure.salaryModels) {
			//检查公司是否新增，如果是新公司并且新公司map中不包含该公司则新增
			if (salaryModel.getNewCorp() && !calculateStructure.newCorpNames.contains(salaryModel.getCorp())) {
				CorporationEntity corporationEntity = new CorporationEntity();
				corporationEntity.setId(Utils.makeUUID());
				corporationEntity.setCreateTime(System.currentTimeMillis());
				corporationEntity.setIsDelete(false);
				corporationEntity.setDistrictId(salaryModel.getDistrictId());
				corporationEntity.setParentId(calculateStructure.groupId);
				corporationEntity.setName(salaryModel.getCorp());
				corporationEntity.setIsGroup(0);
				calculateStructure.newCorpEntities.add(corporationEntity);
				calculateStructure.newCorpNames.add(salaryModel.getCorp());
				calculateStructure.corporationName2IdMap.put(corporationEntity.getName(), corporationEntity.getId());
				salaryModel.setCorpId(corporationEntity.getId());
			} else {
				salaryModel.setCorpId(calculateStructure.corporationName2IdMap.get(salaryModel.getCorp()));
			}
		}

		if (week != null) {
			//周薪
			//先查出用户的月薪，没有的话需要先创建月薪
			if (calculateStructure.existUserIds.size() > 0) {
				if (calculateStructure.existMonthSalaryEntities == null) {
					calculateStructure.existMonthSalaryEntities = new ArrayList<>();
				}
				AryaSalaryEntity aryaSalaryEntity;
				for (String aryaUserId : calculateStructure.existUserId2existCorpIdMap.keySet()) {
					aryaSalaryEntity = aryaSalaryDao.findMonthSalarysByUserIdAndCorpIdAndDepartmentId(aryaUserId, calculateStructure.existUserId2existCorpIdMap.get(aryaUserId), departmentId, year, month);
					if (aryaSalaryEntity != null) {
						calculateStructure.existMonthSalaryEntities.add(aryaSalaryEntity);
					}
				}
			}
		}

		//周薪月薪都需要构建已存在的月薪map
		if (calculateStructure.existMonthSalaryEntities != null) {
			for (AryaSalaryEntity monthEntity : calculateStructure.existMonthSalaryEntities) {
				if (!calculateStructure.existMonthSalaryEntitiesMap.containsKey(monthEntity.getUserId())) {
					calculateStructure.existMonthSalaryEntitiesMap.put(monthEntity.getUserId(), monthEntity);
				}
			}
		}
		//周薪月薪都需要初始化更新及新增的月薪数组
		calculateStructure.updateMonthSalaryEntities = new ArrayList<>();
		calculateStructure.newMonthSalaryEntities = new ArrayList<>();
		if (week != null) {
			//周薪，构建已存在的周薪记录map
			if (calculateStructure.existWeekSalaryEntities != null) {
				for (AryaSalaryWeekEntity weekEntity : calculateStructure.existWeekSalaryEntities) {
					if (!calculateStructure.existWeekSalaryEntitiesMap.containsKey(weekEntity.getUserId())) {
						calculateStructure.existWeekSalaryEntitiesMap.put(weekEntity.getUserId(), weekEntity);
					}
				}
			}
			//初始化存放需要更新及新增的数组
			calculateStructure.updateWeekSalaryEntities = new ArrayList<>();
			calculateStructure.newWeekSalaryEntities = new ArrayList<>();

			//查询出用户已存在的本周之前与之后的周薪，用于更新月薪，结果必须是正序排序好的，不然更新周薪薪资会出现计算错误。
			if (calculateStructure.existUserIds.size() > 0) {
				if (calculateStructure.existOtherWeekSalaryEntities == null) {
					calculateStructure.existOtherWeekSalaryEntities = new ArrayList<>();
				}
				List<AryaSalaryWeekEntity> existOtherWeekSalaryEntities;
				for (String aryaUserId : calculateStructure.existUserIds) {
					existOtherWeekSalaryEntities = salaryWeekDao.findUserThisMonthOtherWeekSalary(aryaUserId, calculateStructure.existUserId2existCorpIdMap.get(aryaUserId), departmentId, year, month, week);
					if (existOtherWeekSalaryEntities.size() > 0) {
						calculateStructure.existOtherWeekSalaryEntities.addAll(existOtherWeekSalaryEntities);
					}
				}
			}
			//遍历，构建用户已存在的本月其他周周薪map
			calculateStructure.existOtherWeekSalaryEntitiesInOneMonthMap = new HashMap<>();
			if (calculateStructure.existOtherWeekSalaryEntities != null && calculateStructure.existOtherWeekSalaryEntities.size() > 0) {
				for (AryaSalaryWeekEntity weekEntity : calculateStructure.existOtherWeekSalaryEntities) {
					UserWeekSalariesInOneMonth inOneMonth;
					if (!calculateStructure.existOtherWeekSalaryEntitiesInOneMonthMap.containsKey(weekEntity.getUserId())) {
						inOneMonth = new UserWeekSalariesInOneMonth();
						inOneMonth.setUserId(weekEntity.getUserId());
						inOneMonth.setWeekEntities(new ArrayList<AryaSalaryWeekEntity>());
						calculateStructure.existOtherWeekSalaryEntitiesInOneMonthMap.put(weekEntity.getUserId(), inOneMonth);
					} else {
						inOneMonth = calculateStructure.existOtherWeekSalaryEntitiesInOneMonthMap.get(weekEntity.getUserId());
					}
					inOneMonth.getWeekEntities().add(weekEntity);
				}
			}
		}
		//遍历合法的薪资模型，生成新用户，分拣需要更新和新增的薪资
		for (SalaryModel salaryModel : calculateStructure.salaryModels) {
			//检查用户是否新增
			if (salaryModel.getNewUser() && !calculateStructure.newUserIdcards.contains(salaryModel.getIdcardNo())) {
				AryaUserEntity userEntity = new AryaUserEntity();
				userEntity.setId(Utils.makeUUID());
				userEntity.setCreateTime(System.currentTimeMillis());
				userEntity.setPwd(SysUtils.encryptPassword("123456"));
				userEntity.setCorporationId(calculateStructure.corporationName2IdMap.get(salaryModel.getCorp()));
				userEntity.setRealName(salaryModel.getName().trim());
				userEntity.setLastClientType(0);
				userEntity.setCreateType(com.bumu.arya.common.Constants.CREATE_TYPE_IMPORT);
				userEntity.setNickName(salaryModel.getName());
				userEntity.setPhoneNo(salaryModel.getPhoneNo().trim());
				userEntity.setBalance(BigDecimal.valueOf(0));
				userEntity.setIdcardNo(salaryModel.getIdcardNo().trim());
				calculateStructure.newUserEntities.add(userEntity);
				calculateStructure.newUserIdcards.add(salaryModel.getIdcardNo());
				//给模型赋值ID，用于新增薪资时使用
				salaryModel.setUserId(userEntity.getId());
			}
			//计算薪资
			SalaryCalculateModel calculateModel = calculateMonthOrWeekSalary(year, month, week, salaryModel, calculateStructure);
			//如果计算为空
			if (calculateModel == null) {
				importResult.setFailedCount(importResult.getTotalCount());
				importResult.setSuccessCount(0);
				importResult.setIgnoredCount(importResult.getTotalCount());
				return importResult;
			}
			salaryModel.setPersonalTax(calculateModel.getPersonalTax());
			salaryModel.setServiceCharge(calculateModel.getServiceCharge());
			salaryModel.setBrokerage(calculateModel.getBrokerage());
			salaryModel.setNetSalary(calculateModel.getNetSalary());
			//赋值，并区分是更新还是新增
			if (week == null) {
				//赋值月薪
				AryaSalaryEntity salaryEntity;
				//如果是update的薪资记录
				if (salaryModel.getOverrideStatus() == SalaryModel.OVERRIDER_DB) {
					salaryEntity = calculateStructure.existMonthSalaryEntitiesMap.get(salaryModel.getUserId());
					calculateStructure.updateMonthSalaryEntities.add(salaryEntity);
				} else {
					salaryEntity = new AryaSalaryEntity();
					salaryEntity.setId(Utils.makeUUID());
					salaryEntity.setDepartmentId(departmentId);
					salaryEntity.setDepartmentId(salaryModel.getDepartmentName());
					calculateStructure.newMonthSalaryEntities.add(salaryEntity);
				}
				salaryEntity.setBankAccountId(salaryModel.getBankAccount());
				salaryEntity.setCorpId(salaryModel.getCorpId());
				salaryEntity.setCorpName(salaryModel.getCorp());
				salaryEntity.setCityId(salaryModel.getDistrictId());
				salaryEntity.setCityName(salaryModel.getDistrict());
				salaryEntity.setUserId(salaryModel.getUserId());
				salaryEntity.setYear(year);
				salaryEntity.setMonth(month);
				salaryEntity.setTaxableSalary(salaryModel.getTaxableSalary());
				salaryEntity.setPersonalTax(salaryModel.getPersonalTax());
				salaryEntity.setServiceCharge(salaryModel.getServiceCharge());
				salaryEntity.setBrokerage(salaryModel.getBrokerage());
				//实发和应发相同
				salaryEntity.setGrossSalary(salaryModel.getNetSalary());
				salaryEntity.setNetSalary(salaryModel.getNetSalary());

				salaryEntity.setDelete(false);
				salaryEntity.setCreateTime(System.currentTimeMillis());

				salaryModel.setId(salaryEntity.getId());
			} else {
				//赋值周薪
				AryaSalaryWeekEntity weekSalaryEntity;
				//如果是update的薪资记录
				if (salaryModel.getOverrideStatus() == SalaryModel.OVERRIDER_DB) {
					//取出待更新的周薪
					weekSalaryEntity = calculateStructure.existWeekSalaryEntitiesMap.get(salaryModel.getUserId());
					//判断是否需要更新，如果税前薪资相同则不用更新，跳过
					if (weekSalaryEntity.getTaxableSalary().compareTo(salaryModel.getTaxableSalary()) == 0) {
						continue;
					}
					calculateStructure.updateWeekSalaryEntities.add(weekSalaryEntity);
				} else {
					//新增周薪
					weekSalaryEntity = new AryaSalaryWeekEntity();
					weekSalaryEntity.setId(Utils.makeUUID());
					weekSalaryEntity.setDepartmentId(departmentId);
					weekSalaryEntity.setDepartmentName(salaryModel.getDepartmentName());
					calculateStructure.newWeekSalaryEntities.add(weekSalaryEntity);
				}
				weekSalaryEntity.setCorpId(salaryModel.getCorpId());
				weekSalaryEntity.setCorpName(salaryModel.getCorp());
				weekSalaryEntity.setCityId(salaryModel.getDistrictId());
				weekSalaryEntity.setCityName(salaryModel.getDistrict());
				weekSalaryEntity.setUserId(salaryModel.getUserId());
				weekSalaryEntity.setYear(year);
				weekSalaryEntity.setMonth(month);
				weekSalaryEntity.setWeek(week);
				weekSalaryEntity.setTaxableSalary(salaryModel.getTaxableSalary());
				weekSalaryEntity.setPersonalTax(salaryModel.getPersonalTax());
				weekSalaryEntity.setServiceCharge(salaryModel.getServiceCharge());
				weekSalaryEntity.setBrokerage(salaryModel.getBrokerage());
				//实发和应发相同
				weekSalaryEntity.setNetSalary(salaryModel.getNetSalary());
				weekSalaryEntity.setGrossSalary(salaryModel.getNetSalary());

				weekSalaryEntity.setDelete(false);
				weekSalaryEntity.setCreateTime(System.currentTimeMillis());
				salaryModel.setId(weekSalaryEntity.getId());
				//判断是否需要新增月薪记录。
				AryaSalaryEntity existMonthSalary = calculateStructure.existMonthSalaryEntitiesMap.get(salaryModel.getUserId());
				// 如果不存在月薪记录
				if (existMonthSalary == null) {
					AryaSalaryEntity newMonthSalaryEntity = new AryaSalaryEntity();
					newMonthSalaryEntity.setId(Utils.makeUUID());
					newMonthSalaryEntity.setBankAccountId(salaryModel.getBankAccount());
					newMonthSalaryEntity.setCorpId(salaryModel.getCorpId());
					newMonthSalaryEntity.setCorpName(salaryModel.getCorp());
					newMonthSalaryEntity.setDepartmentId(departmentId);
					newMonthSalaryEntity.setDepartmentName(salaryModel.getDepartmentName());
					newMonthSalaryEntity.setCityId(salaryModel.getDistrictId());
					newMonthSalaryEntity.setCityName(salaryModel.getDistrict());
					newMonthSalaryEntity.setUserId(salaryModel.getUserId());
					newMonthSalaryEntity.setYear(year);
					newMonthSalaryEntity.setMonth(month);
					newMonthSalaryEntity.setTaxableSalary(salaryModel.getTaxableSalary());
					newMonthSalaryEntity.setPersonalTax(salaryModel.getPersonalTax());
					newMonthSalaryEntity.setServiceCharge(salaryModel.getServiceCharge());
					newMonthSalaryEntity.setBrokerage(salaryModel.getBrokerage());
					//实发和应发相同
					newMonthSalaryEntity.setGrossSalary(salaryModel.getNetSalary());
					newMonthSalaryEntity.setNetSalary(salaryModel.getNetSalary());

					newMonthSalaryEntity.setDelete(false);
					newMonthSalaryEntity.setCreateTime(System.currentTimeMillis());
					//加入到新增数组中去
					calculateStructure.newMonthSalaryEntities.add(newMonthSalaryEntity);
					//为周薪赋上月薪的id
					weekSalaryEntity.setParentId(newMonthSalaryEntity.getId());
				} else {
					//为周薪赋上月薪的id
					weekSalaryEntity.setParentId(existMonthSalary.getId());
					BigDecimal monthTotalTaxableSalary = salaryModel.getTaxableSalary();
					//如果存在月薪记录，则update月薪，上面已做了周薪是否更新的判断，到这里说明周薪有变，则月薪也需要变
					UserWeekSalariesInOneMonth userWeekSalariesInOneMonth = calculateStructure.existOtherWeekSalaryEntitiesInOneMonthMap.get(salaryModel.getUserId());
					if (userWeekSalariesInOneMonth != null) {
						for (AryaSalaryWeekEntity weekEntity : userWeekSalariesInOneMonth.getWeekEntities()) {
							if (!weekEntity.getCorpId().equals(salaryModel.getCorpId())) {
								//如果不是同一公司的薪资则不累加
								continue;
							}

							monthTotalTaxableSalary = monthTotalTaxableSalary.add(weekEntity.getTaxableSalary());
							//如果该周大于本周，更新该周薪资
							if (weekEntity.getWeek() > week) {
								//累计税前薪资，先记下本周的税前薪资加上要更新的那周税前薪资
								//因为userWeekSalariesInOneMonth.getWeekEntities()这个数组里只包含本月除了本周外的周薪，所以要先记录下本周各种费用
								BigDecimal untilThisWeekTotalTaxableSalary = salaryModel.getTaxableSalary().add(weekEntity.getTaxableSalary());
								BigDecimal untilThisWeekPersonalTax = salaryModel.getPersonalTax();//累计个税
								BigDecimal untilThisWeekServiceCharge = salaryModel.getServiceCharge();//累计个税服务费
								BigDecimal untilThisWeekBrokerage = salaryModel.getBrokerage();//累计薪资服务费
								//遍历周薪，累加税前薪资
								for (AryaSalaryWeekEntity forwardWeekEntity : userWeekSalariesInOneMonth.getWeekEntities()) {
									//如果当前周薪是之前的周，累加税前薪资
									if (forwardWeekEntity.getWeek() < weekEntity.getWeek()) {
										untilThisWeekTotalTaxableSalary = untilThisWeekTotalTaxableSalary.add(forwardWeekEntity.getTaxableSalary());
										untilThisWeekPersonalTax = untilThisWeekPersonalTax.add(forwardWeekEntity.getPersonalTax());
										untilThisWeekServiceCharge = untilThisWeekServiceCharge.add(forwardWeekEntity.getServiceCharge());
										untilThisWeekBrokerage = untilThisWeekBrokerage.add(forwardWeekEntity.getBrokerage());
									}
								}
								SalaryCalculateModel untilThisWeekTotalCalculateModel = calculateSalaryToModel(untilThisWeekTotalTaxableSalary, calculateStructure.getRuleModel());
								weekEntity.setPersonalTax(untilThisWeekTotalCalculateModel.getPersonalTax().subtract(untilThisWeekPersonalTax));//减去之前周的个税
								weekEntity.setServiceCharge(untilThisWeekTotalCalculateModel.getServiceCharge().subtract(untilThisWeekServiceCharge));//减去之前周的个税服务费
								weekEntity.setBrokerage(untilThisWeekTotalCalculateModel.getBrokerage().subtract(untilThisWeekBrokerage));//减去之前周的薪资服务费
								//实发和应发相同
								weekEntity.setNetSalary(weekEntity.getTaxableSalary().subtract(weekEntity.getPersonalTax()).subtract(weekEntity.getServiceCharge()));
								weekEntity.setGrossSalary(weekEntity.getNetSalary());
								calculateStructure.updateWeekSalaryEntities.add(weekEntity);
							}
						}
					} else {
						userWeekSalariesInOneMonth = new UserWeekSalariesInOneMonth();
						userWeekSalariesInOneMonth.setWeekEntities(new ArrayList<>());
					}
					//取出月薪
					AryaSalaryEntity monthSalaryEntity = calculateStructure.existMonthSalaryEntitiesMap.get(salaryModel.getUserId());
					//加入本周周薪
					userWeekSalariesInOneMonth.getWeekEntities().add(weekSalaryEntity);
					//重新计算月薪
					log.info("重新计算" + salaryModel.getName() + "的月薪:" + monthTotalTaxableSalary);
					reCalculateMonthSalary(monthSalaryEntity, userWeekSalariesInOneMonth.getWeekEntities(), calculateStructure.getRuleModel());

					monthSalaryEntity.setBankAccountId(salaryModel.getBankAccount());
					monthSalaryEntity.setCorpId(salaryModel.getCorpId());
					calculateStructure.updateMonthSalaryEntities.add(monthSalaryEntity);
				}
			}
		}

		importResult.setIgnoredCount(calculateStructure.ignoredSalaryModels != null ? calculateStructure.ignoredSalaryModels.size() : 0);
		StringBuffer logMsg = new StringBuffer("【薪资计算导入】");
		logMsg.append("导入" + groupId + "公司的" + year + "-" + month + "-" + week + "薪资");
		try {
			//创建公司
			corporationDao.create(calculateStructure.newCorpEntities);
			calculateStructure.log.add("新增" + calculateStructure.newCorpEntities.size() + "个公司。");
			//更新用户
			List<AryaUserEntity> updateUsers = calculateStructure.getUpdateUserEntities();
			if (updateUsers != null) {
				aryaUserDao.update(updateUsers);
			}
			//创建用户
			aryaUserDao.create(calculateStructure.newUserEntities);
			calculateStructure.log.add("新增" + calculateStructure.newUserEntities.size() + "个用户。");
			if (week == null) {
				aryaSalaryDao.create(calculateStructure.newMonthSalaryEntities);
				aryaSalaryDao.update(calculateStructure.updateMonthSalaryEntities);
				importResult.setSuccessCount(calculateStructure.newMonthSalaryEntities.size() + calculateStructure.updateMonthSalaryEntities.size());
				calculateStructure.log.add("总共" + (calculateStructure.salaryModels.size() + calculateStructure.ignoredSalaryModels.size())
						+ "条，其中忽略" + calculateStructure.ignoredSalaryModels.size() + "条，有问题" + calculateStructure.getWrongSalaryModels().size() + "条。操作成功" + importResult.getSuccessCount() + "条薪资，其中新增" + calculateStructure.newMonthSalaryEntities.size() + "条月薪，更新" + calculateStructure.updateMonthSalaryEntities.size() + "条月薪。");
			} else {
				//如果是周薪
				salaryWeekDao.create(calculateStructure.newWeekSalaryEntities);
				salaryWeekDao.update(calculateStructure.updateWeekSalaryEntities);
				//判断是否需要创建月薪表
				if (calculateStructure.newMonthSalaryEntities != null && calculateStructure.newMonthSalaryEntities.size() > 0) {
					aryaSalaryDao.create(calculateStructure.newMonthSalaryEntities);
					calculateStructure.log.add("新增" + calculateStructure.newMonthSalaryEntities.size() + "条月薪。");
				}
				//判断是否需要更新的月薪表
				//如果周薪更新，也需要更新月薪表
				if (calculateStructure.updateMonthSalaryEntities != null && calculateStructure.updateMonthSalaryEntities.size() > 0) {
					aryaSalaryDao.update(calculateStructure.updateMonthSalaryEntities);
					calculateStructure.log.add("更新" + calculateStructure.updateMonthSalaryEntities.size() + "条月薪。");
				}
				importResult.setSuccessCount(calculateStructure.newWeekSalaryEntities.size() + calculateStructure.updateWeekSalaryEntities.size());
				calculateStructure.log.add("总共" + (calculateStructure.salaryModels.size() + calculateStructure.ignoredSalaryModels.size())
						+ "条，其中忽略" + calculateStructure.ignoredSalaryModels.size() + "条，有问题" + calculateStructure.getWrongSalaryModels().size() + "条。操作成功"
						+ importResult.getSuccessCount() + "条薪资，其中新增" + calculateStructure.newWeekSalaryEntities.size()
						+ "条周薪，更新" + calculateStructure.updateWeekSalaryEntities.size() + "条周薪。");
			}
			opLogService.successLog(OP_TYPE_SALARY_IMPORT_SUCCESS, logMsg, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(OP_TYPE_SALARY_IMPORT_FAILED, logMsg, log);
		}
		importResult.setLog(StringUtils.join(calculateStructure.log, "\n"));
		log.info(importResult.getLog());
		return importResult;
	}

	@Override
	public SalaryCalculateListResult queryCalculateSalary(String groupId, String departmentId, int year, int month, Integer week, String keyWord, Integer page, Integer pageSize) throws AryaServiceException {
		SalaryCalculateListResult calculateResult = new SalaryCalculateListResult();
		ArrayList<SalaryCalculateListResult.SalaryCalculateResult> listResult = new ArrayList<>();
		List<SalaryModel> models = queryCalculateSalary2Models(new QueryCalculateSalary2ModelsCommand(groupId, departmentId, year, month, week, keyWord));
		for (SalaryModel model : models) {
			SalaryCalculateListResult.SalaryCalculateResult result = new SalaryCalculateListResult.SalaryCalculateResult();
			SalaryCalculateListResult.SalaryCalculateResult.UserName name = new SalaryCalculateListResult.SalaryCalculateResult.UserName();
			name.setName(model.getName());
			SalaryCalculateListResult.SalaryCalculateResult.UserIdcard idcard = new SalaryCalculateListResult.SalaryCalculateResult.UserIdcard();
			idcard.setIdcardNo(model.getIdcardNo());
			SalaryCalculateListResult.SalaryCalculateResult.Phone phone = new SalaryCalculateListResult.SalaryCalculateResult.Phone();
			phone.setPhoneNo(model.getPhoneNo());
			SalaryCalculateListResult.SalaryCalculateResult.City city = new SalaryCalculateListResult.SalaryCalculateResult.City();
			city.setCity(model.getDistrict());
			SalaryCalculateListResult.SalaryCalculateResult.Company corp = new SalaryCalculateListResult.SalaryCalculateResult.Company();
			corp.setCorp(model.getCorp());
			SalaryCalculateListResult.SalaryCalculateResult.TaxableSalary taxableSalary = new SalaryCalculateListResult.SalaryCalculateResult.TaxableSalary();
			taxableSalary.setTaxableSalary(model.getTaxableSalary().toString());
			SalaryCalculateListResult.SalaryCalculateResult.PersonalTax personalTax = new SalaryCalculateListResult.SalaryCalculateResult.PersonalTax();
			personalTax.setPersonalTax(model.getPersonalTax().toString());
			SalaryCalculateListResult.SalaryCalculateResult.ServiceCharge serviceCharge = new SalaryCalculateListResult.SalaryCalculateResult.ServiceCharge();
			serviceCharge.setServiceCharge(model.getServiceCharge().toString());
			SalaryCalculateListResult.SalaryCalculateResult.NetSalary netSalary = new SalaryCalculateListResult.SalaryCalculateResult.NetSalary();
			netSalary.setNetSalary(model.getNetSalary().toString());
			SalaryCalculateListResult.SalaryCalculateResult.Brokerage brokerage = new SalaryCalculateListResult.SalaryCalculateResult.Brokerage();
			brokerage.setBrokerage(model.getBrokerage().toString());
			SalaryCalculateListResult.SalaryCalculateResult.BankAccount bankAccount = new SalaryCalculateListResult.SalaryCalculateResult.BankAccount();
			bankAccount.setBankAccountId(model.getBankAccount());
			calculateResult.setRuleType(model.getRuleType());
			result.setId(model.getId());
			result.setCity(city);
			result.setName(name);
			result.setCorp(corp);
			if (StringUtils.isAnyBlank(model.getDepartmentName())) {
				result.setDepartmentName("-");
			} else {
				result.setDepartmentName(model.getDepartmentName());
			}
			result.setIdcardNo(idcard);
			result.setTaxableSalary(taxableSalary);
			result.setPersonalTax(personalTax);
			result.setServiceCharge(serviceCharge);
			result.setBrokerage(brokerage);
			result.setNetSalary(netSalary);
			result.setBankAccount(bankAccount);
			result.setPhone(phone);

			listResult.add(result);
		}
		int total = listResult.size();
		calculateResult.setPages(Utils.calculatePages(total, pageSize));
		calculateResult.setCalculateResults(listResult.subList(pageSize * (page - 1), ((pageSize * page) > total ? total : (pageSize * page))));

		calculateResult.setStatistics(countCorpSalary(models));
		log.info("【薪资计算】查询完成。");
		return calculateResult;
	}

	@Override
	public List<SalaryModel> queryCalculateSalary2Models(QueryCalculateSalary2ModelsCommand Salary2ModelsCommand) throws AryaServiceException {
		String ruleId = null;
		SalaryCalculateRuleModel salaryCalculateRuleModel = new SalaryCalculateRuleModel();

		List<SalaryModel> models = new ArrayList<>();
		List<AryaSalaryEntity> monthSalaryEntities = new ArrayList<>();//月薪
		List<AryaSalaryWeekEntity> weekSalaryEntities = new ArrayList<>();//周薪
		Collection<String> userIds = new ArrayList<>();
		Map<String, AryaUserEntity> userId2EntityMap = new HashMap<>();//key是用户id，value是用户实体
		Map<String, AryaSalaryEntity> monthSalaryId2EntityMap = new HashMap<>();//key是用户id，value是月薪实体
		//如果是指定某部门查询的话，不需要查出所有公司，只需要用部门ID去查询薪资
		Map<String, CorporationEntity> corporationEntityMap = new HashMap<>();
		Map<String, String> departmentNameMap = new HashMap<>();//key是部门id，value是部门名称
		Collection<String> corpIds = new ArrayList<>();
		if (StringUtils.isNotBlank(Salary2ModelsCommand.getDepartmentId())) {
			//查询指定部门
			AryaDepartmentEntity aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(Salary2ModelsCommand.getDepartmentId());
			if (aryaDepartmentEntity == null) {
				log.error("【薪资计算】未查询到指定部门" + Salary2ModelsCommand.getDepartmentId() + "。");
				return models;
			}
			ruleId = aryaDepartmentEntity.getSalaryRuleId();
			departmentNameMap.put(Salary2ModelsCommand.getDepartmentId(), aryaDepartmentEntity.getName());
			monthSalaryEntities = aryaSalaryDao.findDepartmentMonthSalaries(Salary2ModelsCommand.getDepartmentId(), Salary2ModelsCommand.getYear(), Salary2ModelsCommand.getMonth(), Salary2ModelsCommand.getWeek(), Salary2ModelsCommand.getKeyWord());
		} else {
			//查出公司及所有子公司
			corporationEntityMap = corporationService.getGroupAndSubCorpMap(Salary2ModelsCommand.getGroupId());
			if (corporationEntityMap == null || corporationEntityMap.size() == 0) {
				log.error("【薪资计算】未查询到任何公司。");
				return models;
			}
			for (CorporationEntity corporationEntity : corporationEntityMap.values()) {
				corpIds.add(corporationEntity.getId());
				ruleId = corporationEntity.getSalaryRuleId();
			}

			//查月薪
			monthSalaryEntities = aryaSalaryDao.findCorpsOrDepartmentMonthSalaries(corpIds, Salary2ModelsCommand.getDepartmentId(), Salary2ModelsCommand.getYear(), Salary2ModelsCommand.getMonth(), Salary2ModelsCommand.getWeek(), Salary2ModelsCommand.getKeyWord());
			//查出集团下所有部门
			departmentNameMap = corporationService.getGroupDepartmentsMap(Salary2ModelsCommand.getGroupId());
		}

		if (monthSalaryEntities == null || monthSalaryEntities.size() == 0) {
			log.error("公司id:" + StringUtils.join(corpIds, ","));
			log.error("【薪资计算】月薪记录为空。");
			return models;
		}

		//构建用户id数组
		for (AryaSalaryEntity monthSalaryEntity : monthSalaryEntities) {
			if (!userIds.contains(monthSalaryEntity.getUserId())) {
				userIds.add(monthSalaryEntity.getUserId());
			}
		}
		if (Salary2ModelsCommand.getWeek() != null) {
			if (StringUtils.isNotBlank(Salary2ModelsCommand.getDepartmentId())) {
				weekSalaryEntities = salaryWeekDao.findDepartmentWeekSalaries(Salary2ModelsCommand.getDepartmentId(), Salary2ModelsCommand.getYear(), Salary2ModelsCommand.getMonth(), Salary2ModelsCommand.getWeek(), Salary2ModelsCommand.getKeyWord());
			} else {
				weekSalaryEntities = salaryWeekDao.findCorpsOrDepartmentWeekSalaries(corpIds, Salary2ModelsCommand.getDepartmentId(), Salary2ModelsCommand.getYear(), Salary2ModelsCommand.getMonth(), Salary2ModelsCommand.getWeek(), Salary2ModelsCommand.getKeyWord());
			}
			if (weekSalaryEntities == null || weekSalaryEntities.size() == 0) {
				log.error("【薪资计算】周薪记录为空。");
				return models;
			}
			//遍历月薪，构建月薪map
			for (AryaSalaryEntity monthSalaryEntity : monthSalaryEntities) {
				monthSalaryId2EntityMap.put(monthSalaryEntity.getUserId(), monthSalaryEntity);
			}
			//遍历周薪
			for (AryaSalaryWeekEntity weekSalaryEntity : weekSalaryEntities) {
				if (!userIds.contains(weekSalaryEntity.getUserId())) {
					userIds.add(weekSalaryEntity.getUserId());
				}
			}
		}

		{
			//查用户
			List<AryaUserEntity> userEntities = aryaUserDao.findUsersByIds(userIds);
			if (userEntities == null || userEntities.size() == 0) {
				log.error("【薪资计算】未查询到任何用户。");
				return models;
			}
			for (AryaUserEntity userEntity : userEntities) {
				userId2EntityMap.put(userEntity.getId(), userEntity);
			}
		}

		//查询计算规则
		if (ruleId != null) {
			SalaryRuleEntity salaryRuleEntity = salaryRuleDao.findRuleById(ruleId);
			if (salaryRuleEntity != null) {
				salaryCalculateRuleModel = getSalaryCalculateRuleModel(salaryRuleEntity);
			}
		}

		if (Salary2ModelsCommand.getWeek() == null) {
			for (AryaSalaryEntity monthSalaryEntity : monthSalaryEntities) {
				SalaryModel model = new SalaryModel();
				AryaUserEntity userEntity = userId2EntityMap.get(monthSalaryEntity.getUserId());
				if (userEntity == null) {
					continue;
				}
				model.setId(monthSalaryEntity.getId());
				model.setUserId(userEntity.getId());
				model.setName(userEntity.getRealName());
				model.setIdcardNo(userEntity.getIdcardNo());
				model.setPhoneNo(userEntity.getPhoneNo());
				model.setDistrict(monthSalaryEntity.getCityName());
				model.setCorp(monthSalaryEntity.getCorpName());
				model.setTaxableSalary(monthSalaryEntity.getTaxableSalary());
				model.setPersonalTax(monthSalaryEntity.getPersonalTax());
				model.setServiceCharge(monthSalaryEntity.getServiceCharge());
				model.setNetSalary(monthSalaryEntity.getNetSalary());
				model.setBrokerage(monthSalaryEntity.getBrokerage());
				model.setBankAccount(monthSalaryEntity.getBankAccountId());
				if (salaryCalculateRuleModel.getThresholdTax() != null) {
					model.setRuleType(2);
				} else {
					model.setRuleType(1);
				}
				if (StringUtils.isNotBlank(monthSalaryEntity.getDepartmentId())) {
					model.setDepartmentId(monthSalaryEntity.getDepartmentId());
					model.setDepartmentName(departmentNameMap.get(monthSalaryEntity.getDepartmentId()));
				} else {
					model.setDepartmentId("");
					model.setDepartmentName("-");
				}
				models.add(model);
			}
		} else {
			for (AryaSalaryWeekEntity weekSalaryEntity : weekSalaryEntities) {
				SalaryModel model = new SalaryModel();
				SalaryCalculateListResult.SalaryCalculateResult result = new SalaryCalculateListResult.SalaryCalculateResult();
				AryaUserEntity userEntity = userId2EntityMap.get(weekSalaryEntity.getUserId());
				if (userEntity == null) {
					continue;
				}
				AryaSalaryEntity monthSalaryEntity = monthSalaryId2EntityMap.get(weekSalaryEntity.getUserId());
				if (monthSalaryEntity == null) {
					continue;
				}
				model.setId(weekSalaryEntity.getId());
				model.setUserId(userEntity.getId());
				model.setName(userEntity.getRealName());
				model.setIdcardNo(userEntity.getIdcardNo());
				model.setPhoneNo(userEntity.getPhoneNo());
				model.setDistrict(weekSalaryEntity.getCityName());
				model.setCorp(weekSalaryEntity.getCorpName());
				model.setTaxableSalary(weekSalaryEntity.getTaxableSalary());
				model.setPersonalTax(weekSalaryEntity.getPersonalTax());
				model.setServiceCharge(weekSalaryEntity.getServiceCharge());
				model.setNetSalary(weekSalaryEntity.getNetSalary());
				model.setBrokerage(weekSalaryEntity.getBrokerage());
				model.setBankAccount(monthSalaryEntity.getBankAccountId());
				if (salaryCalculateRuleModel.getThresholdTax() != null) {
					model.setRuleType(2);
				} else {
					model.setRuleType(1);
				}
				if (StringUtils.isNotBlank(weekSalaryEntity.getDepartmentId())) {
					model.setDepartmentId(weekSalaryEntity.getDepartmentId());
					model.setDepartmentName(departmentNameMap.get(weekSalaryEntity.getDepartmentId()));
				} else {
					model.setDepartmentId("");
					model.setDepartmentName("-");
				}
				models.add(model);
			}
		}
		countCorpSalary(models);
		return models;
	}

	@Override
	public void exportSalary(String userId, String groupId, String departmentId, int year, int month, Integer week, HttpServletResponse response) {
		CorporationEntity corporationEntity;
		AryaDepartmentEntity departmentEntity = new AryaDepartmentEntity();
		if (StringUtils.isNotBlank(departmentId)) {
			departmentEntity = aryaDepartmentDao.findDepartmentById(departmentId);
			if (departmentEntity == null) {
				return;
			}
			corporationEntity = corporationDao.find(departmentEntity.getCorpId());
		} else if (StringUtils.isNotBlank(groupId)) {
			corporationEntity = corporationDao.find(groupId);
			if (corporationEntity == null) {
				return;
			}
		} else {
			return;
		}

		StringBuffer fileName = new StringBuffer("薪资：" + corporationEntity.getName());
		if (StringUtils.isNotBlank(departmentId)) {
			fileName.append(departmentEntity.getName());
		}
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String yearMonthWeekStr = "的" + year + "年" + month + "月";
		if (week != null) {
			yearMonthWeekStr += "第" + week + "批次";
		}
		fileName.append(yearMonthWeekStr);

		fileName.append("薪资" + dateFormater.format(date) + ".xls");
		List<SalaryModel> salaryModels = queryCalculateSalary2Models(new QueryCalculateSalary2ModelsCommand(groupId, departmentId, year, month, week));
		HSSFWorkbook workbook = fileService.generateSalaryCalculateExcelFile(salaryModels);
		response.setContentType("APPLICATION/OCTET-STREAM");
		String headStr = "attachment;filename=\"" + SysUtils.parseGBK(fileName.toString()) + "\"";
		response.setHeader("Content-Disposition", headStr);
		StringBuffer logMsg = new StringBuffer("【薪资计算】导出" + corporationEntity.getName() + groupId + "公司的" + year + "-" + month + "-" + week + "薪资");
		try {
			log.info("开始导出文件。" + fileName + "。");
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			logMsg.append(",文件名：" + fileName);
			opLogService.successLog(OP_TYPE_SALARY_EXPORT_SUCCESS, logMsg, log);
			log.info("【薪资计算】导出文件完成。" + fileName + "。");
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(OP_TYPE_SALARY_EXPORT_SUCCESS, logMsg, log);
			log.info("【薪资计算】导出文件失败。" + fileName + "。");
		}
		//转存文件
		log.info("【薪资计算】开始copy文件。" + fileName + "。");
		fileService.copySalaryCalculateExportExcelFile(workbook, fileName.toString());
		log.info("【薪资计算】copy文件完成。" + fileName + "。");
	}

	@Override
	public void exportStatistics(String userId, String groupId, String departmentId, int year, int month, Integer week, HttpServletResponse response) {
		List<SalaryModel> models = queryCalculateSalary2Models(new QueryCalculateSalary2ModelsCommand(groupId, departmentId, year, month, week));
		Collection<CorpSalaryStatisticsStructure> structures = generateCorpSalaryStatisticsStructures(models);
		CorporationEntity corporationEntity;
		AryaDepartmentEntity departmentEntity = new AryaDepartmentEntity();
		if (StringUtils.isNotBlank(departmentId)) {
			departmentEntity = aryaDepartmentDao.findDepartmentById(departmentId);
			if (departmentEntity == null) {
				return;
			}
			corporationEntity = corporationDao.find(departmentEntity.getCorpId());
		} else if (StringUtils.isNotBlank(groupId)) {
			corporationEntity = corporationDao.find(groupId);
			if (corporationEntity == null) {
				return;
			}
		} else {
			return;
		}
		StringBuffer fileName = new StringBuffer("统计：" + corporationEntity.getName());
		if (StringUtils.isNotBlank(departmentId)) {
			fileName.append(departmentEntity.getName());
		}
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String yearMonthWeekStr = "的" + year + "年" + month + "月";
		if (week != null) {
			yearMonthWeekStr += "第" + week + "批次";
		}
		fileName.append(yearMonthWeekStr + "统计结果" + dateFormater.format(date) + ".xls");
		HSSFWorkbook workbook = fileService.generateSalaryCalculateStatisticsExcelFile(structures);
		response.setContentType("APPLICATION/OCTET-STREAM");
		String headStr = "attachment;filename=\"" + SysUtils.parseGBK(fileName.toString()) + "\"";
		response.setHeader("Content-Disposition", headStr);
		StringBuffer logMsg = new StringBuffer("【薪资计算导出统计】导出" + corporationEntity.getName() + groupId + "公司的" + year + "-" + month + "-" + week + "薪资统计,文件名：" + fileName);
		try {
			log.info("【薪资计算】开始导出统计文件。" + fileName + "。");
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			opLogService.successLog(OP_TYPE_SALARY_EXPORT_SUCCESS, logMsg, log);
			log.info("【薪资计算】导出统计文件完成。" + fileName + "。");
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(OP_TYPE_SALARY_EXPORT_SUCCESS, logMsg, log);
			log.info("【薪资计算】导出统计文件失败。" + fileName + "。");
		}
		//转存文件
		log.info("【薪资计算】开始copy统计文件。" + fileName + "。");
		fileService.copySalaryCalculateExportExcelFile(workbook, fileName.toString());
		log.info("【薪资计算】copy统计文件完成。" + fileName + "。");
	}

	@Override
	public List<SalaryModel> mergerSalaryModelsAndDBRecords(List<SalaryModel> salaryModels, String groupId, String departmentId, int year, int month, Integer week) {
		//查出DB的记录
		List<SalaryModel> dbSalaryModels = queryCalculateSalary2Models(new QueryCalculateSalary2ModelsCommand(groupId, departmentId, year, month, week));
		if (salaryModels == null || salaryModels.size() == 0) {
			return dbSalaryModels;
		}

		//剔除掉db记录里被覆盖的
		for (int i = 0; i < dbSalaryModels.size(); i++) {
			SalaryModel dbSalaryModel = dbSalaryModels.get(i);
			for (SalaryModel salaryModel : salaryModels) {
				if (!dbSalaryModel.getUserId().equals(salaryModel.getUserId())) {
					continue;
				}
				if (salaryModel.getOverrideStatus() == SalaryModel.OVERRIDER_DB) {
					//把被覆盖的db记录剔除掉
					dbSalaryModels.remove(i);
					i--;
					break;
				}
			}
		}
		//合并db记录与传递的记录
		dbSalaryModels.addAll(salaryModels);
		return dbSalaryModels;
	}

	@Override
	public SalaryCalculateStatisticsResultList countCorpSalary(List<SalaryModel> salaryModels) {
		SalaryCalculateStatisticsResultList resultList = new SalaryCalculateStatisticsResultList();
		if (salaryModels == null || salaryModels.size() == 0) {
			return resultList;
		}
		Collection<CorpSalaryStatisticsStructure> structures =  generateCorpSalaryStatisticsStructures(salaryModels);
		for (CorpSalaryStatisticsStructure structure : structures) {
			SalaryCalculateStatisticsResultList.SalaryCalculateStatisticsResult result = new SalaryCalculateStatisticsResultList.SalaryCalculateStatisticsResult();
			result.setCorp(structure.getCorpName());
			result.setDepartmentName(structure.getDepartmentName());
			result.setDistrict(structure.getDistrictName());
			result.setStaffCount(structure.getStaffCount());
			result.setTaxableSalaryTotal(structure.getTaxableSalaryTotal().toString());
			result.setPersonalTaxTotal(structure.getPersonalTaxTotal().toString());
			result.setServiceChargeTotal(structure.getServiceChargeTotal().toString());
			result.setBrokerageTotal(structure.getBrokerageTotal().toString());
			result.setNetSalaryTotal(structure.getNetSalaryTotal().toString());
			resultList.add(result);
		}
		return resultList;
	}

	@Override
	public Collection<CorpSalaryStatisticsStructure> generateCorpSalaryStatisticsStructures(List<SalaryModel> salaryModels) {
		Map<String, CorpSalaryStatisticsStructure> corpStatisticesMap = new HashMap<>();//key是地区+公司名称+部门id，key是公司薪资统计数据结构
		int staffTotal = 0;
		BigDecimal taxableSalaryTotal = new BigDecimal("0");
		BigDecimal personalTaxTotal = new BigDecimal("0");
		BigDecimal serviceCharegeTotal = new BigDecimal("0");
		BigDecimal brokerageTotal = new BigDecimal("0");
		BigDecimal netSalaryTotal = new BigDecimal("0");
		if (salaryModels == null || salaryModels.size() == 0) {
			return new ArrayList<>();
		}
		//遍历记录，分公司统计
		for (SalaryModel salaryModal : salaryModels) {
			String key = salaryModal.getDistrict() + salaryModal.getCorp() + salaryModal.getDepartmentId();
			CorpSalaryStatisticsStructure structure;
			if (corpStatisticesMap.containsKey(key)) {
				structure = corpStatisticesMap.get(key);
			} else {
				structure = new CorpSalaryStatisticsStructure();
				structure.setDistrictName(salaryModal.getDistrict());
				structure.setCorpName(salaryModal.getCorp());
				structure.setDepartmentName(salaryModal.getDepartmentName());
				corpStatisticesMap.put(key, structure);
			}
			structure.setStaffCount(structure.getStaffCount() + 1);
			structure.setTaxableSalaryTotal(structure.getTaxableSalaryTotal().add(salaryModal.getTaxableSalary()));
			structure.setPersonalTaxTotal(structure.getPersonalTaxTotal().add(salaryModal.getPersonalTax()));
			structure.setServiceChargeTotal(structure.getServiceChargeTotal().add(salaryModal.getServiceCharge()));
			structure.setBrokerageTotal(structure.getBrokerageTotal().add(salaryModal.getBrokerage()));
			structure.setNetSalaryTotal(structure.getNetSalaryTotal().add(salaryModal.getNetSalary()));
		}
		ArrayList<CorpSalaryStatisticsStructure> structures = new ArrayList<>();
		if (corpStatisticesMap.size() == 0) {
			return structures;
		}
		structures.addAll(corpStatisticesMap.values());
		//统计总和
		if (corpStatisticesMap.size() > 0) {
			for (CorpSalaryStatisticsStructure structure : corpStatisticesMap.values()) {
				staffTotal += structure.getStaffCount();
				taxableSalaryTotal = taxableSalaryTotal.add(structure.getTaxableSalaryTotal());
				personalTaxTotal = personalTaxTotal.add(structure.getPersonalTaxTotal());
				serviceCharegeTotal = serviceCharegeTotal.add(structure.getServiceChargeTotal());
				brokerageTotal = brokerageTotal.add(structure.getBrokerageTotal());
				netSalaryTotal = netSalaryTotal.add(structure.getNetSalaryTotal());
			}
			CorpSalaryStatisticsStructure structure = new CorpSalaryStatisticsStructure();
			structure.setDistrictName("总计");
			structure.setCorpName("总计");
			structure.setDepartmentName("总计");
			structure.setStaffCount(staffTotal);
			structure.setTaxableSalaryTotal(taxableSalaryTotal);
			structure.setPersonalTaxTotal(personalTaxTotal);
			structure.setServiceChargeTotal(serviceCharegeTotal);
			structure.setBrokerageTotal(brokerageTotal);
			structure.setNetSalaryTotal(netSalaryTotal);
			structures.add(structure);
		}
		return structures;
	}

	@Override
	public SalaryCalculateRuleResult getSalaryCalculateRule(String groupId, String departmentId, int ruleType) throws AryaServiceException {
		SalaryCalculateRuleResult ruleResult = new SalaryCalculateRuleResult();
		String ruleId = "";
		if (StringUtils.isAnyBlank(groupId) && StringUtils.isAnyBlank(departmentId)) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_GROUP_OR_DEPARTMENT_NOT_FIND);
		}
		if (StringUtils.isNotBlank(departmentId)) {
			AryaDepartmentEntity aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(departmentId);
			if (aryaDepartmentEntity == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_NOT_EXIST);
			}
			if (StringUtils.isAnyBlank(aryaDepartmentEntity.getSalaryRuleId())) {
				return ruleResult;
			}
			ruleId = aryaDepartmentEntity.getSalaryRuleId();
		} else if (StringUtils.isNotBlank(groupId)) {
			CorporationEntity groupEntity = corporationDao.findCorporationByIdThrow(groupId);
			if (StringUtils.isAnyBlank(groupEntity.getSalaryRuleId())) {
				return ruleResult;
			}
			ruleId = groupEntity.getSalaryRuleId();
		}

		SalaryRuleEntity ruleEntity = salaryRuleDao.findRuleById(ruleId);
		if (ruleEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_FOUND);
		}

		ObjectMapper mapper = new ObjectMapper();
		SalaryCalculateRuleModel ruleModel;
		try {
			ruleModel = mapper.readValue(ruleEntity.getRuleDef(), SalaryCalculateRuleModel.class);
		} catch (IOException e) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_JSON_READ_FAILED);
		}
		ruleResult.setId(ruleEntity.getId());
		ruleResult.setRuleName(ruleEntity.getName());
		ruleResult.setServiceChargeTaxRate(ruleModel.getServiceChargeTaxRate());
		ruleResult.setBrokerageRate(ruleModel.getBrokerageRate());
		if (ruleType == 1) {
			if (ruleModel.getTaxGears() == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_EXIST);
			}
			ruleResult.setTaxGears(getDescTaxGearsResult(ruleModel.getTaxGears()));
		} else {
			if (ruleModel.getThresholdTax() == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_EXIST);
			}
			ruleResult.setThresholdTax(ruleModel.getThresholdTax());
			ruleResult.setBrokerage(ruleModel.getBrokerage());
		}
		return ruleResult;
	}

	@Override
	public SalaryCalculateRuleTypeResult getSalaryCalculateRuleType(String groupId, String departmentId) {
		SalaryCalculateRuleTypeResult ruleResult = new SalaryCalculateRuleTypeResult();
		String ruleId = "";
		if (StringUtils.isAnyBlank(groupId) && StringUtils.isAnyBlank(departmentId)) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_GROUP_OR_DEPARTMENT_NOT_FIND);
		}
		if (StringUtils.isNotBlank(departmentId)) {
			AryaDepartmentEntity aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(departmentId);
			if (aryaDepartmentEntity == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_NOT_EXIST);
			}
			if (StringUtils.isAnyBlank(aryaDepartmentEntity.getSalaryRuleId())) {
				return ruleResult;
			}
			ruleId = aryaDepartmentEntity.getSalaryRuleId();
		} else if (StringUtils.isNotBlank(groupId)) {
			CorporationEntity groupEntity = corporationDao.findCorporationByIdThrow(groupId);
			if (StringUtils.isAnyBlank(groupEntity.getSalaryRuleId())) {
				return ruleResult;
			}
			ruleId = groupEntity.getSalaryRuleId();
		}

		SalaryRuleEntity ruleEntity = salaryRuleDao.findRuleById(ruleId);
		if (ruleEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_FOUND);
		}

		ObjectMapper mapper = new ObjectMapper();
		SalaryCalculateRuleModel ruleModel;
		try {
			ruleModel = mapper.readValue(ruleEntity.getRuleDef(), SalaryCalculateRuleModel.class);
		} catch (IOException e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_JSON_READ_FAILED);
		}
		if (ruleModel.getThresholdTax() != null) {
			ruleResult.setRuleType(2);
		} else {
			ruleResult.setRuleType(1);
		}
		return ruleResult;
	}

	@Override
	public SalaryCalculateRuleModel getSalaryCalculateRuleModel(SalaryRuleEntity ruleEntity) throws AryaServiceException {
		SalaryCalculateRuleModel ruleModel;
		try {
			ObjectMapper mapper = new ObjectMapper();
			ruleModel = mapper.readValue(ruleEntity.getRuleDef(), SalaryCalculateRuleModel.class);
		} catch (IOException e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_JSON_READ_FAILED);
		}
		return ruleModel;
	}

	@Override
	public SalaryCalculateRuleResult addGroupSalaryCalculateRule(CreateSalaryCalculateRuleCommand command) {
		if (StringUtils.isAnyBlank(command.getGroupId())) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_CORPORATION_ID_EMPTY);
		}
		CorporationEntity corporationEntity = corporationDao.findCorporationById(command.getGroupId());
		if (corporationEntity.getIsGroup() == Constants.FALSE) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_GOURP_MUST);
		}
		if (corporationEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
		}

		if (StringUtils.isNotBlank(corporationEntity.getSalaryRuleId())) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_CORPORATION_HAS_RULE_ALREADY);//已存在规则
		}

		if (command.getRuleType() == 1) {
			if (command.getTaxGears() == null || command.getTaxGears().size() == 0) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_TAX_GEAR_EMPTY);//计税档不能为空
			}
			checkTaxGearsLegal(command.getTaxGears());
		} else {
			if (command.getThresholdTax() == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_ThRESHOLD_TAX_EMPTY);//个税起征点不能为空
			}
		}
		if (command.getBrokerageRate() == null) {
			command.setBrokerageRate(new BigDecimal("0"));
		}

		if (command.getServiceChargeTaxRate() == null) {
			command.setServiceChargeTaxRate(new BigDecimal("0"));
		}

		if (command.getBrokerage() == null) {
			command.setBrokerage(new BigDecimal("0"));
		}

		SalaryRuleEntity ruleEntity = new SalaryRuleEntity();
		ruleEntity.setId(Utils.makeUUID());
		ruleEntity.setName(command.getRuleName());
		ruleEntity.setCreateTime(System.currentTimeMillis());
		ruleEntity.setUpdateTime(ruleEntity.getCreateTime());
		ruleEntity.setIsDelete(Constants.FALSE);
		SalaryCalculateRuleModel ruleModel = new SalaryCalculateRuleModel();
		ruleModel.setServiceChargeTaxRate(command.getServiceChargeTaxRate());
		ruleModel.setBrokerageRate(command.getBrokerageRate());
		if (command.getRuleType() == 1) {
			ruleModel.setTaxGears(turnSalaryCalculateRuleGearCommandToModel(command.getTaxGears()));
		} else {
			ruleModel.setThresholdTax(command.getThresholdTax());
			ruleModel.setBrokerage(command.getBrokerage());
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			ruleEntity.setRuleDef(mapper.writeValueAsString(ruleModel));
		} catch (JsonProcessingException e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_JSON_FAILED);
		}
		corporationEntity.setSalaryRuleId(ruleEntity.getId());
		StringBuffer logStr = new StringBuffer("【薪资计算】新增集团计算规则,集团id:" + corporationEntity.getId() + ",规则id:" + ruleEntity.getId());
		try {
			salaryRuleDao.create(ruleEntity);
			corporationDao.update(corporationEntity);
			opLogService.successLog(SALARY_CALCULATE_CREATE_RULE, logStr, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SALARY_CALCULATE_CREATE_RULE, logStr, log);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_DB_FAILED);
		}
		SalaryCalculateRuleResult ruleResult = new SalaryCalculateRuleResult();
		ruleResult.setId(ruleEntity.getId());
		ruleResult.setRuleName(ruleEntity.getName());
		ruleResult.setServiceChargeTaxRate(ruleModel.getServiceChargeTaxRate());
		ruleResult.setBrokerageRate(ruleModel.getBrokerageRate());
		if (command.getRuleType() == 1) {
			ruleResult.setTaxGears(getDescTaxGearsResult(ruleModel.getTaxGears()));
		} else {
			ruleResult.setThresholdTax(ruleModel.getThresholdTax());
			ruleResult.setBrokerage(ruleModel.getBrokerage());
		}
		return ruleResult;
	}

	@Override
	public SalaryCalculateRuleResult addDepartmentSalaryCalculateRule(CreateSalaryCalculateRuleCommand command) {
		if (StringUtils.isAnyBlank(command.getDepartmentId())) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_ID_EMPTY);
		}
		AryaDepartmentEntity aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(command.getDepartmentId());
		if (aryaDepartmentEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_NOT_EXIST);
		}

		if (StringUtils.isNotBlank(aryaDepartmentEntity.getSalaryRuleId())) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_HAS_RULE_ALREADY);//已存在规则
		}
		if (command.getRuleType() == 1) {
			if (command.getTaxGears() == null || command.getTaxGears().size() == 0) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_TAX_GEAR_EMPTY);//计税档不能为空
			}
			checkTaxGearsLegal(command.getTaxGears());
		} else {
			if (command.getThresholdTax() == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_ThRESHOLD_TAX_EMPTY);//个税起征点不能为空
			}
		}

		if (command.getBrokerageRate() == null) {
			command.setBrokerageRate(new BigDecimal("0"));
		}

		if (command.getServiceChargeTaxRate() == null) {
			command.setServiceChargeTaxRate(new BigDecimal("0"));
		}

		SalaryRuleEntity ruleEntity = new SalaryRuleEntity();
		ruleEntity.setId(Utils.makeUUID());
		ruleEntity.setName(command.getRuleName());
		ruleEntity.setCreateTime(System.currentTimeMillis());
		ruleEntity.setUpdateTime(ruleEntity.getCreateTime());
		ruleEntity.setIsDelete(Constants.FALSE);
		SalaryCalculateRuleModel ruleModel = new SalaryCalculateRuleModel();
		ruleModel.setServiceChargeTaxRate(command.getServiceChargeTaxRate());
		ruleModel.setBrokerageRate(command.getBrokerageRate());
		if (command.getRuleType() == 1) {
			ruleModel.setTaxGears(turnSalaryCalculateRuleGearCommandToModel(command.getTaxGears()));
		} else {
			ruleModel.setThresholdTax(command.getThresholdTax());
			ruleModel.setBrokerage(command.getBrokerage());
		}
		ObjectMapper mapper = new ObjectMapper();
		StringBuffer logStr = new StringBuffer("【薪资计算】新增通用部门计算规则,部门id:" + aryaDepartmentEntity.getId() + ",规则id:" + ruleEntity.getId());
		try {
			ruleEntity.setRuleDef(mapper.writeValueAsString(ruleModel));
		} catch (JsonProcessingException e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_JSON_FAILED);
		}
		aryaDepartmentEntity.setSalaryRuleId(ruleEntity.getId());
		try {
			salaryRuleDao.create(ruleEntity);
			aryaDepartmentDao.update(aryaDepartmentEntity);
			opLogService.successLog(SALARY_CALCULATE_CREATE_RULE, logStr, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SALARY_CALCULATE_CREATE_RULE, logStr, log);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_DB_FAILED);
		}
		SalaryCalculateRuleResult ruleResult = new SalaryCalculateRuleResult();
		ruleResult.setId(ruleEntity.getId());
		ruleResult.setRuleName(ruleEntity.getName());
		ruleResult.setServiceChargeTaxRate(ruleModel.getServiceChargeTaxRate());
		ruleResult.setBrokerageRate(ruleModel.getBrokerageRate());
		if (command.getRuleType() == 1) {
			ruleResult.setTaxGears(getDescTaxGearsResult(ruleModel.getTaxGears()));
		} else {
			ruleResult.setThresholdTax(ruleModel.getThresholdTax());
			ruleResult.setBrokerage(ruleModel.getBrokerage());
		}
		return ruleResult;
	}

	@Override
	public SalaryCalculateRuleResult updateSalaryCalculateRule(UpdateSalaryCalculateRuleCommand command) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		//判断该规则当月是否被使用，使用过则当月不可以更新
		if (StringUtils.isNotBlank(command.getGroupId())) {
			List<String> subCorpIds = corporationDao.findAllSubCorpIds(command.getGroupId());
			if (subCorpIds == null || subCorpIds.size() == 0) {
				//新集团不用检查规则是否使用过
			} else if (aryaSalaryDao.isGroupImportedSalary(subCorpIds, year, month)) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_HAS_BEEN_USED);
			}
		} else if (StringUtils.isNotBlank(command.getDepartmentId())) {
			if (aryaSalaryDao.isDepartmentImportedSalary(command.getDepartmentId(), year, month)) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_HAS_BEEN_USED);
			}
		} else {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_CORP_AND_DEPARTMENT_EMPTY);
		}
		if (command.getRuleType() == 1) {
			if (command.getTaxGears() == null || command.getTaxGears().size() == 0) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_TAX_GEAR_EMPTY);//计税档不能为空
			}
			checkTaxGearsLegal(command.getTaxGears());
		} else {
			if (command.getThresholdTax() == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_ThRESHOLD_TAX_EMPTY);//个税起征点不能为空
			}
		}

		if (command.getBrokerageRate() == null) {
			command.setBrokerageRate(new BigDecimal("0"));
		}

		if (command.getServiceChargeTaxRate() == null) {
			command.setServiceChargeTaxRate(new BigDecimal("0"));
		}

		SalaryRuleEntity ruleEntity = salaryRuleDao.findRuleById(command.getId());
		if (ruleEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_FOUND);
		}
		ruleEntity.setName(command.getRuleName());
		ruleEntity.setUpdateTime(System.currentTimeMillis());
		SalaryCalculateRuleModel ruleModel = new SalaryCalculateRuleModel();
		ruleModel.setServiceChargeTaxRate(command.getServiceChargeTaxRate());
		ruleModel.setBrokerageRate(command.getBrokerageRate());
		if (command.getRuleType() == 1) {
			ruleModel.setTaxGears(turnSalaryCalculateRuleGearCommandToModel(command.getTaxGears()));
		} else {
			ruleModel.setThresholdTax(command.getThresholdTax());
			ruleModel.setBrokerage(command.getBrokerage());
		}
		ObjectMapper mapper = new ObjectMapper();
		StringBuffer logStr = new StringBuffer("【薪资计算】更新计算规则,id:" + ruleEntity.getId());
		try {
			ruleEntity.setRuleDef(mapper.writeValueAsString(ruleModel));
			logStr.append(",规则内容:" + mapper.writeValueAsString(ruleModel));
		} catch (JsonProcessingException e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_JSON_FAILED);
		}

		try {
			salaryRuleDao.update(ruleEntity);
			opLogService.successLog(SALARY_CALCULATE_UPDATE_RULE, logStr, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SALARY_CALCULATE_UPDATE_RULE, logStr, log);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_DB_FAILED);
		}
		SalaryCalculateRuleResult ruleResult = new SalaryCalculateRuleResult();
		ruleResult.setId(ruleEntity.getId());
		ruleResult.setRuleName(ruleEntity.getName());
		ruleResult.setServiceChargeTaxRate(ruleModel.getServiceChargeTaxRate());
		ruleResult.setBrokerageRate(ruleModel.getBrokerageRate());
		if (command.getRuleType() == 1) {
			ruleResult.setTaxGears(getDescTaxGearsResult(ruleModel.getTaxGears()));
		} else {
			ruleResult.setThresholdTax(ruleModel.getThresholdTax());
			ruleResult.setBrokerage(ruleModel.getBrokerage());
		}
		return ruleResult;
	}

	@Override
	public void deleteSalaryCalculateRule(UpdateSalaryCalculateRuleCommand command) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		//判断该规则当月是否被使用，使用过则当月不可以删除
		if (StringUtils.isNotBlank(command.getGroupId())) {
			List<String> subCorpIds = corporationDao.findAllSubCorpIds(command.getGroupId());
			if (subCorpIds == null || subCorpIds.size() == 0) {
				//新集团不用检查规则是否使用过
			} else if (aryaSalaryDao.isGroupImportedSalary(subCorpIds, year, month)) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_HAS_BEEN_USED);
			}
		} else if (StringUtils.isNotBlank(command.getDepartmentId())) {
			if (aryaSalaryDao.isDepartmentImportedSalary(command.getDepartmentId(), year, month)) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_HAS_BEEN_USED);
			}
		} else {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_CORP_AND_DEPARTMENT_EMPTY);
		}
		SalaryRuleEntity ruleEntity = salaryRuleDao.findRuleById(command.getId());
		if (ruleEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_FOUND);
		}
		ruleEntity.setIsDelete(Constants.TRUE);
		StringBuffer logStr = new StringBuffer("【薪资计算】删除计算规则id:" + ruleEntity.getId());
		try {
			//删除规则
			if (StringUtils.isNotBlank(command.getGroupId())) {
				CorporationEntity corporationEntity = corporationDao.findCorporationById(command.getGroupId());
				if (corporationEntity != null) {
					corporationEntity.setSalaryRuleId(null);
					corporationDao.update(corporationEntity);
					logStr.append(",公司名称:" + corporationEntity.getName() + ",id:" + corporationEntity.getId());
				}
			} else if (StringUtils.isNotBlank(command.getDepartmentId())) {
				AryaDepartmentEntity aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(command.getDepartmentId());
				if (aryaDepartmentEntity != null) {
					aryaDepartmentEntity.setSalaryRuleId(null);
					aryaDepartmentDao.update(aryaDepartmentEntity);
					logStr.append(",部门名称:" + aryaDepartmentEntity.getName() + ",id:" + aryaDepartmentEntity.getId());
				}
			}
			salaryRuleDao.update(ruleEntity);
			opLogService.successLog(SALARY_CALCULATE_DELETE_RULE, logStr, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SALARY_CALCULATE_DELETE_RULE, logStr, log);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_DB_FAILED);
		}
	}

	@Override
	public List<SalaryCalculateRuleGearModel> getDescTaxGears(List<SalaryCalculateRuleGearModel> taxGears) {
		if (taxGears.size() == 0) {
			return taxGears;
		}

		//倒叙排序
		for (int i = 0; i < taxGears.size() - 1; i++) {
			BigDecimal greaterTaxGear = taxGears.get(i).getGear();
			int greaterPosition = i;
			for (int j = i + 1; j < taxGears.size(); j++) {
				SalaryCalculateRuleGearModel tempTaxGear = taxGears.get(j);
				if (tempTaxGear.getGear().compareTo(greaterTaxGear) == 1) {

					greaterPosition = j;
					greaterTaxGear = tempTaxGear.getGear();
				}
			}
			//交换位置
			if (greaterPosition != i) {
				SalaryCalculateRuleGearModel tempTaxGear = taxGears.get(i);
				taxGears.set(i, taxGears.get(greaterPosition));
				taxGears.set(greaterPosition, tempTaxGear);
			}
		}

		return taxGears;
	}

	@Override
	public List<SalaryCalculateRuleResult.SalaryCalculateRuleTaxGearResult> getDescTaxGearsResult(List<SalaryCalculateRuleGearModel> taxGears) {
		List<SalaryCalculateRuleResult.SalaryCalculateRuleTaxGearResult> results = new ArrayList<>();
		List<SalaryCalculateRuleGearModel> models = getDescTaxGears(taxGears);
		for (SalaryCalculateRuleGearModel gear : models) {
			SalaryCalculateRuleResult.SalaryCalculateRuleTaxGearResult result = new SalaryCalculateRuleTaxGearResult();
			result.setGear(gear.getGear());
			result.setTaxRate(gear.getTaxRate());
			results.add(result);
		}
		return results;
	}

	@Override
	public void deleteCalculatedSalary(List<String> deleteIds, Integer ruleType) {
		if (deleteIds == null || deleteIds.size() == 0) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DELETE_CHOSE_EMPTY);
		}
		String firstId = deleteIds.get(0);
		AryaSalaryEntity salaryEntity = aryaSalaryDao.findById(firstId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		StringBuffer logStr = new StringBuffer("【薪资计算】");
		if (salaryEntity == null) {
			//删除周薪
			AryaSalaryWeekEntity salaryWeekEntity = salaryWeekDao.findById(firstId);
			if (salaryWeekEntity == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DELETE_NOT_FOUND);
			}
			if (!AppShare.isDevMode && (salaryWeekEntity.getYear() != year || salaryWeekEntity.getMonth() != month)) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DELETE_CURRENT_MONTH_ONLY);//只能删除本月的薪资
			}
			//删除周薪
			//查出计算规则
			String calculateRuleId;
			if (StringUtils.isNotBlank(salaryWeekEntity.getDepartmentId())) {
				//查出部门
				AryaDepartmentEntity aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(salaryWeekEntity.getDepartmentId());
				if (aryaDepartmentEntity == null) {
					throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_NOT_EXIST);
				}
				if (StringUtils.isAnyBlank(aryaDepartmentEntity.getSalaryRuleId())) {
					throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DEPARTMENT_RULE_HAS_BEEN_DELETE);
				}
				calculateRuleId = aryaDepartmentEntity.getSalaryRuleId();
			} else {
				//查出集团
				CorporationEntity subCorpEntity = corporationDao.findCorporationByIdThrow(salaryWeekEntity.getCorpId());
				CorporationEntity groupCorpEntity = corporationDao.findCorporationByIdThrow(subCorpEntity.getParentId());
				if (StringUtils.isAnyBlank(groupCorpEntity.getSalaryRuleId())) {
					throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_GROUP_RULE_HAS_BEEN_DELETE);
				}
				calculateRuleId = groupCorpEntity.getSalaryRuleId();
			}
			SalaryRuleEntity salaryRuleEntity = salaryRuleDao.findRuleById(calculateRuleId);
			if (salaryRuleEntity == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_NOT_FOUND);
			}
			try {
				salaryWeekDao.deleteWeekSalaries(deleteIds);
				logStr.append("删除周薪ids:" + StringUtils.join(deleteIds, ",") + "。同时更新所有涉及的月薪。");
				//更新该周之后的周薪，更新月薪
				updateAfterDeleteWeekSalariesAndMonthSalary(deleteIds, ruleType, salaryWeekEntity.getWeek(), getSalaryCalculateRuleModel(salaryRuleEntity));
				opLogService.successLog(SALARY_CALCULATE_DELETE_WEEK, logStr, log);
			} catch (Exception e) {
				opLogService.failedLog(SALARY_CALCULATE_DELETE_WEEK, logStr, log);
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DELETE_FAILED);
			}
		} else {
			//删除月薪
			if (!AppShare.isDevMode && (salaryEntity.getYear() != year || salaryEntity.getMonth() != month)) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DELETE_CURRENT_MONTH_ONLY);//只能删除本月的薪资
			}
			//删除月薪和周薪
			try {
				salaryWeekDao.deleteWeekSalariesByMonthSalaryIds(deleteIds);
				aryaSalaryDao.deleteMonthSalaries(deleteIds);
				logStr.append("删除月薪ids:" + StringUtils.join(deleteIds, ",") + "。同时删除所有涉及的周薪。");
				opLogService.successLog(SALARY_CALCULATE_DELETE_MONTH, logStr, log);
			} catch (Exception e) {
				opLogService.failedLog(SALARY_CALCULATE_DELETE_MONTH, logStr, log);
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DELETE_FAILED);
			}
		}
	}

	@Override
	public void updateAfterDeleteWeekSalariesAndMonthSalary(List<String> deleteIds, Integer ruleType, int afterWeek, SalaryCalculateRuleModel salaryCalculateRuleModel) {
		if (deleteIds == null || deleteIds.size() == 0) {
			return;
		}
		List<String> monthSalaryIds = salaryWeekDao.findDeleteWeekSalariesMonthSalaries(deleteIds);
		if (monthSalaryIds.size() == 0) {
			return;
		}

		//查出所有月薪
		List<AryaSalaryEntity> salaryEntities = aryaSalaryDao.findSalariesByIds(monthSalaryIds);
		if (salaryEntities.size() == 0) {
			return;
		}
		//查出所有周薪
		Map<String, List<AryaSalaryWeekEntity>> weekSalariesMap = new HashMap<>();
		List<AryaSalaryWeekEntity> salaryWeekEntities = salaryWeekDao.findByMonthSalaryIds(monthSalaryIds);
		//构建月薪与周薪的Map
		for (AryaSalaryWeekEntity salaryWeekEntity : salaryWeekEntities) {
			if (!weekSalariesMap.containsKey(salaryWeekEntity.getParentId())) {
				List<AryaSalaryWeekEntity> salaryWeekEntities1 = new ArrayList<>();
				salaryWeekEntities1.add(salaryWeekEntity);
				weekSalariesMap.put(salaryWeekEntity.getParentId(), salaryWeekEntities1);
			} else {
				weekSalariesMap.get(salaryWeekEntity.getParentId()).add(salaryWeekEntity);
			}
		}

		List<String> needDeleteMonthSalaryIds = new ArrayList<>();
		List<AryaSalaryWeekEntity> needUpdateWeekSalaryList = new ArrayList<>();
		for (AryaSalaryEntity monthSalaryEntity : salaryEntities) {
			//如果没有其他周薪，删除月薪
			if (!weekSalariesMap.containsKey(monthSalaryEntity.getId())) {
				needDeleteMonthSalaryIds.add(monthSalaryEntity.getId());
				weekSalariesMap.remove(monthSalaryEntity.getId());
			} else {
				//重新计算周薪
				reCalculateWeekSalariesAfterWeek(ruleType, weekSalariesMap.get(monthSalaryEntity.getId()), afterWeek, salaryCalculateRuleModel);

				//更新月薪
				reCalculateMonthSalary(monthSalaryEntity, weekSalariesMap.get(monthSalaryEntity.getId()), salaryCalculateRuleModel);

				//收集需要更新的周薪
				needUpdateWeekSalaryList.addAll(weekSalariesMap.get(monthSalaryEntity.getId()));
			}
		}

		try {
			if (needDeleteMonthSalaryIds.size() > 0) {
				aryaSalaryDao.deleteMonthSalaries(needDeleteMonthSalaryIds);//删除需要删除的月薪
			}
			if (needUpdateWeekSalaryList.size() > 0) {
				salaryWeekDao.update(needUpdateWeekSalaryList);
			}
			if (salaryEntities.size() > 0) {
				aryaSalaryDao.update(salaryEntities);
			}
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_DELETE_FAILED);
		}

	}

	@Override
	public void reCalculateWeekSalariesAfterWeek(Integer ruleType, List<AryaSalaryWeekEntity> salaryWeekEntities, int afterWeek, SalaryCalculateRuleModel calculateRuleModel) {
		//先按照周正序排序
		//正序排序
		for (int i = 0; i < salaryWeekEntities.size() - 1; i++) {
			int currentWeek = salaryWeekEntities.get(i).getWeek();
			int ltPosition = i;
			for (int j = i + 1; j < salaryWeekEntities.size(); j++) {
				int compareWeek = salaryWeekEntities.get(j).getWeek();
				if (compareWeek < currentWeek) {
					ltPosition = j;
					currentWeek = compareWeek;
				}
			}
			//交换位置
			if (ltPosition != i) {
				AryaSalaryWeekEntity tempWeekSalaryEntity = salaryWeekEntities.get(i);
				salaryWeekEntities.set(i, salaryWeekEntities.get(ltPosition));
				salaryWeekEntities.set(ltPosition, tempWeekSalaryEntity);
			}
		}

		//开始计算和更新
		for (AryaSalaryWeekEntity weekSalaryEntity : salaryWeekEntities) {
			if (weekSalaryEntity.getWeek() > afterWeek) {
				List<AryaSalaryWeekEntity> forwardWeekSalaries = new ArrayList<>();
				for (AryaSalaryWeekEntity forwardSalaryEntity : salaryWeekEntities) {
					if (forwardSalaryEntity.getWeek() < weekSalaryEntity.getWeek()) {
						forwardWeekSalaries.add(forwardSalaryEntity);
					}
				}
				if (forwardWeekSalaries.size() > 0) {
					SalaryCalculateModel calculateModel = calculateWeekSalaryBaseOnForwardWeekSalaries(forwardWeekSalaries, weekSalaryEntity.getTaxableSalary(), calculateRuleModel);
					weekSalaryEntity.setPersonalTax(calculateModel.getPersonalTax());
					weekSalaryEntity.setServiceCharge(calculateModel.getServiceCharge());
					weekSalaryEntity.setBrokerage(calculateModel.getBrokerage());
					weekSalaryEntity.setNetSalary(calculateModel.getNetSalary());
				} else if (forwardWeekSalaries.size() == 0) {
					//如果该周之前没有周新则直接更新该周的薪资
					SalaryCalculateModel calculateModel = calculateSalaryToModel(weekSalaryEntity.getTaxableSalary(), calculateRuleModel);
					weekSalaryEntity.setPersonalTax(calculateModel.getPersonalTax());
					weekSalaryEntity.setServiceCharge(calculateModel.getServiceCharge());
					weekSalaryEntity.setBrokerage(calculateModel.getBrokerage());
					weekSalaryEntity.setNetSalary(calculateModel.getNetSalary());
				}
			}
		}
	}

	@Override
	public void reCalculateMonthSalary(AryaSalaryEntity salaryEntity, List<AryaSalaryWeekEntity> salaryWeekEntities, SalaryCalculateRuleModel calculateRuleModel) {
		SalaryCalculateModel calculateModel = new SalaryCalculateModel();
		calculateModel.setTaxableSalary(new BigDecimal("0"));
		calculateModel.setPersonalTax(new BigDecimal("0"));
		calculateModel.setServiceCharge(new BigDecimal("0"));
		calculateModel.setBrokerage(new BigDecimal("0"));
		calculateModel.setNetSalary(new BigDecimal("0"));
		for (AryaSalaryWeekEntity weekEntity : salaryWeekEntities) {
			calculateModel.setTaxableSalary(calculateModel.getTaxableSalary().add(weekEntity.getTaxableSalary()));
			calculateModel.setPersonalTax(calculateModel.getPersonalTax().add(weekEntity.getPersonalTax()));
			calculateModel.setServiceCharge(calculateModel.getServiceCharge().add(weekEntity.getServiceCharge()));
			calculateModel.setBrokerage(calculateModel.getBrokerage().add(weekEntity.getBrokerage()));
			calculateModel.setNetSalary(calculateModel.getNetSalary().add(weekEntity.getNetSalary()));
		}
		salaryEntity.setTaxableSalary(calculateModel.getTaxableSalary());
		salaryEntity.setPersonalTax(calculateModel.getPersonalTax());
		salaryEntity.setServiceCharge(calculateModel.getServiceCharge());
		salaryEntity.setBrokerage(calculateModel.getBrokerage());
		salaryEntity.setNetSalary(calculateModel.getNetSalary());
		//实发和应发相同
		salaryEntity.setGrossSalary(calculateModel.getNetSalary());
		salaryEntity.setUpdateTime(System.currentTimeMillis());
	}

	@Override
	public SalaryCalculateModel calculateWeekSalaryBaseOnForwardWeekSalaries(List<AryaSalaryWeekEntity> forwardWeekSalaryEntities, BigDecimal taxableSalary, SalaryCalculateRuleModel ruleModel) {
		BigDecimal taxableSalaryCount = taxableSalary;//累计税前薪资
		BigDecimal personalTax = new BigDecimal("0");//累计个税
		BigDecimal serviceCharge = new BigDecimal("0");//累计个税服务费
		BigDecimal brokerage = new BigDecimal("0");//累计薪资服务费
		//累计
		for (AryaSalaryWeekEntity weekEntity : forwardWeekSalaryEntities) {
			taxableSalaryCount = taxableSalaryCount.add(weekEntity.getTaxableSalary());
			personalTax = personalTax.add(weekEntity.getPersonalTax());
			serviceCharge = serviceCharge.add(weekEntity.getServiceCharge());
			brokerage = brokerage.add(weekEntity.getBrokerage());
		}
		SalaryCalculateModel calculateModel = calculateSalaryToModel(taxableSalaryCount, ruleModel);
		calculateModel.setTaxableSalary(taxableSalary);
		calculateModel.setPersonalTax(calculateModel.getPersonalTax().subtract(personalTax));
		calculateModel.setServiceCharge(calculateModel.getServiceCharge().subtract(serviceCharge));
		calculateModel.setBrokerage(calculateModel.getBrokerage().subtract(brokerage));
		calculateModel.setNetSalary(taxableSalary.subtract(calculateModel.getPersonalTax()).subtract(calculateModel.getServiceCharge()));
		return calculateModel;
	}

	@Override
	public List<SalaryCalculateRuleGearModel> turnSalaryCalculateRuleGearCommandToModel(List<SalaryCalculateRuleTaxGear> commandGears) throws AryaServiceException {
		List<SalaryCalculateRuleGearModel> models = new ArrayList<>();
		for (SalaryCalculateRuleTaxGear commandGear : commandGears) {
			SalaryCalculateRuleGearModel model = new SalaryCalculateRuleGearModel();
			model.setGear(commandGear.getGear());
			model.setTaxRate(commandGear.getTaxRate());
			models.add(model);
		}
		return models;
	}

	@Override
	public void checkTaxGearsLegal(List<SalaryCalculateRuleTaxGear> taxGears) throws AryaServiceException {
		for (SalaryCalculateRuleTaxGear gear : taxGears) {
			if (gear.getGear() == null || gear.getTaxRate() == null) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_GEAR_OR_RATE_EMPTY);
			}
		}
	}

	@Override
	public void updateSalaryUserInfo(UpdateSalaryUserInfoCommand command) throws AryaServiceException {
		AryaSalaryEntity salaryEntity = aryaSalaryDao.findById(command.getId());
		AryaSalaryWeekEntity weekSalaryEntity = salaryWeekDao.findById(command.getId());
		if (salaryEntity == null && weekSalaryEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_NOT_FOUND);
		}
		if (weekSalaryEntity != null) {
			salaryEntity = aryaSalaryDao.findById(weekSalaryEntity.getParentId());
		}
		if (salaryEntity == null) {
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_MONTH_SALARY_NOT_FOUND);
		}
		AryaUserEntity userEntity = aryaUserDao.findUserByIdThrow(salaryEntity.getUserId());
		StringBuffer logMsg = new StringBuffer("【薪资计算】修改用户信息id:" + userEntity.getId() + "。");
		if (StringUtils.isNotBlank(command.getBankAccount()) && !command.getBankAccount().equals(salaryEntity.getBankAccountId())) {
			if (command.getBankAccount().length() > 20) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_BANK_ACCOUNT_TOO_LONG);
			}
			salaryEntity.setBankAccountId(command.getBankAccount());
			logMsg.append(String.format("修改银行账号%s为%s。", salaryEntity.getBankAccountId(), command.getBankAccount()));
		}

		if (StringUtils.isNotBlank(command.getName()) && !command.getName().equals(userEntity.getRealName())) {
			if (command.getName().length() > 32) {
				throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_NAME_TOO_LONG);
			}
			userEntity.setRealName(command.getName());
			userEntity.setNickName(command.getName());
			logMsg.append(String.format("修改姓名%s为%s。", userEntity.getRealName(), command.getName()));
		}

		if (StringUtils.isNotBlank(command.getIdcardNo()) && !command.getIdcardNo().equals(userEntity.getIdcardNo())) {
			if (IdcardValidator.isValidatedAllIdcard(command.getIdcardNo())) {
				userEntity.setIdcardNo(command.getIdcardNo());
				logMsg.append(String.format("修改身份证号码%s为%s。", userEntity.getIdcardNo(), command.getIdcardNo()));
			} else {
				throw new AryaServiceException(ErrorCode.CODE_VALID_IDCARDNO_WRONG);
			}
		}

		try {
			aryaSalaryDao.update(salaryEntity);
			aryaUserDao.update(userEntity);
			opLogService.successLog(SALARY_CALCULATE_UPDATE_USER_INFO, logMsg, log);
		} catch (Exception e) {
			elog.error(e.getMessage(), e);
			opLogService.failedLog(SALARY_CALCULATE_UPDATE_USER_INFO, logMsg, log);
			throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_UPDATE_USER_INFO_FAILED);
		}
	}

	@Override
	public Integer generateSalaryBatchNo() throws AryaServiceException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddHHmmss");//日时分秒 长度7-8位
		Integer no = Integer.parseInt(dateFormat.format(new Date(System.currentTimeMillis())));
		return no;
	}

	@Override
	public List getSalaryBatchNoList(String groupOrDepartmentId, Integer year, Integer month) throws AryaServiceException {
		AryaDepartmentEntity departmentEntity = aryaDepartmentDao.findDepartmentById(groupOrDepartmentId);
		List<Integer> batches = new ArrayList();
		if (departmentEntity == null) {
			//查集团
			List<CorporationEntity> corporationEntities = corporationDao.findCorpsFromGroup(groupOrDepartmentId);
			if (corporationEntities.size() == 0) {
				return batches;
			}

			List<String> subCorpIds = new ArrayList<>();
			for (CorporationEntity subCorp : corporationEntities) {
				subCorpIds.add(subCorp.getId());
			}
			batches = salaryWeekDao.findCorpSalaryBatches(subCorpIds, year, month);
		} else {
			//查部门
			batches = salaryWeekDao.findDepartmentSalaryBatches(groupOrDepartmentId, year, month);
		}
//        List<String> batchesStrList = new ArrayList<>();
//        if (batches.size() > 0) {
//            for (Integer batchNo : batches) {
//                String batchNoStr = batchNo.toString();
//                if (batchNoStr.length() == 7) {
//                    //因为数据库里是int型,如果是个位数日期,需在前面加上0保持整齐
//                    batchNoStr = "0" + batchNoStr;
//                }
//                if (batchNoStr.length() > 2) {
//                    //批次号精确到秒,但是返回前端时去掉秒。
//                    batchNoStr = batchNoStr.substring(0, batchNoStr.length() - 2);
//                }
//                batchesStrList.add(batchNoStr);
//            }
//        }
		return batches;
	}
}
