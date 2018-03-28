package com.bumu.arya.salary.service.impl;

import com.bumu.arya.IdcardValidator;
import com.bumu.arya.Utils;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.common.Constants;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.document.SysJournalDocument;
import com.bumu.arya.salary.calculate.SalaryCalculateEngine;
import com.bumu.arya.salary.calculate.Value;
import com.bumu.arya.salary.calculate.context.BumuSalaryConfig;
import com.bumu.arya.salary.calculate.context.GlobalConfig;
import com.bumu.arya.salary.calculate.context.HumanpoolSalaryConfig;
import com.bumu.arya.salary.calculate.context.OrdinarySalaryConfig;
import com.bumu.arya.salary.calculate.factor.bumu.BumuSalaryAfterFactor;
import com.bumu.arya.salary.calculate.factor.bumu.BumuServiceFactor;
import com.bumu.arya.salary.calculate.factor.bumu.BumuTaxFactor;
import com.bumu.arya.salary.calculate.factor.general.TaxFactor;
import com.bumu.arya.salary.calculate.factor.humanpool.*;
import com.bumu.arya.salary.calculate.factor.ordinary.*;
import com.bumu.arya.salary.calculate.suite.BumuCalculateSuite;
import com.bumu.arya.salary.calculate.suite.HumanpoolCalculateSuite;
import com.bumu.arya.salary.calculate.suite.OrdinaryCalculateSuite;
import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.dao.*;
import com.bumu.arya.salary.model.*;
import com.bumu.arya.salary.model.entity.*;
import com.bumu.arya.salary.result.ErrLogResult;
import com.bumu.arya.salary.result.SalaryCalculateCountResult;
import com.bumu.arya.salary.result.SalaryCalculateListResult;
import com.bumu.arya.salary.result.SalaryUserResult;
import com.bumu.arya.salary.service.*;
import com.bumu.arya.soin.service.DistrictCommonService;
import com.bumu.common.result.Pager;
import com.bumu.common.util.ListUtils;
import com.bumu.engine.excelimport.ImportService;
import com.bumu.engine.excelimport.model.*;
import com.bumu.exception.AryaServiceException;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.bumu.function.VoConverter.logger;


/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/12
 */
@Service
public class SalaryCalculateServiceImpl implements SalaryCalculateService {

    Logger log = LoggerFactory.getLogger(ProjectApplyServiceImpl.class);

    /**
     * 导入时文件的阅读数据量
     */
    private final Integer readDataRows = 2001;

    @Autowired
    private ImportService importService;

    @Autowired
    private DistrictCommonService districtCommonService;

    @Autowired
    private CustomerDistrictDao customerDistrictDao;

    @Autowired
    private CustomerDistrictService customerDistrictService;

    @Autowired
    private SalaryUserService salaryUserService;

    @Autowired
    private SalaryUserDao salaryUserDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SalaryCalculateDao salaryCalculateDao;

    @Autowired
    private SalaryCalculateDetailDao salaryCalculateDetailDao;

    @Autowired
    private SalaryErrLogDao salaryErrLogDao;

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private SalaryLogService salaryLogService;

    @Autowired
    private SysUserService sysUserService;

    SalaryCalculateEngine calculateEngine = SalaryCalculateEngine.getInstance();

    @Override
    public void checkImportWeek(CustomerSalaryCulateCommand customerSalaryCulateCommand) {
        // 1.不能是按月结算周期导入 或者预览
        if (customerSalaryCulateCommand.getSettlementInterval() == 1) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_SETTLEMENT_INTERVAL_ERROR, "不能按月导入或预览薪资");
        }
        customerSalaryCulateCommand.setWeek(null == customerSalaryCulateCommand.getWeek() ? generateSalaryBatchNo() : customerSalaryCulateCommand.getWeek());
    }

    @Override
    public SalaryCalculatePoolModel importBumuExcel(File salaryFile, CustomerSalaryCulateCommand customerSalaryCulateCommand) throws Exception {
        SalaryCalculatePoolModel salaryCalculatePoolModel = new SalaryCalculatePoolModel();
        salaryCalculatePoolModel.customerId = customerSalaryCulateCommand.getCustomerId();
        // 1.读取EXCEL
        // 2.检查 地区、公司 收集用户
        logger.info("获取计算规则model");
        salaryCalculatePoolModel.salaryCalculateRuleModel = customerService.getRuleModel(customerSalaryCulateCommand.getCustomerId());
        if (null == salaryCalculatePoolModel.salaryCalculateRuleModel) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_RULE_SAVE_DB_FAILED, "该客户没有设置规则");
        }
        logger.info("导入Excel--》开始");
        List<ImportReg> paramCheckList = new ArrayList<>();
        // 根据客户的薪资计算规则 确定他的导入配置
        if (salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.defined
                || salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.standard) {
            paramCheckList = new ICRegModel().BUMU_SALARY_CHECK_LIST;
        }
        if (salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.humanPool) {
            paramCheckList = new ICRegModel().HUMANPOOL_SALARY_CHECK_LIST;
        }
        if (salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.ordinary) {
            paramCheckList = new ICRegModel().ORDINARY_SALARY_CHECK_LIST;
        }

        ICConfigModel icConfigModel = new ICConfigModel(salaryFile, paramCheckList, ImportService.FileTemplateSource.Bumu);
        icConfigModel.setReadDataRows(readDataRows);

        salaryCalculatePoolModel.importResultModel = importService.importExcel(icConfigModel);
        logger.info("导入Excel--》结束,共" + salaryCalculatePoolModel.importResultModel.getTotalImportNumber()
                + "条数据,其中成功" + salaryCalculatePoolModel.importResultModel.getSuccessImportNumber() + "条");
        salaryCalculatePoolModel.dataModelList = salaryCalculatePoolModel.importResultModel.getSuccessDataList();
        // 验证模板信息
        if (!checkImportModel(paramCheckList, salaryCalculatePoolModel.importResultModel)) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_IMPORT_TEMPALTE_ERR, "导入的模板不符合");
        }
        // 所有地区的map key->地区中文名  value->地区对象
        logger.info("获取系统中所有的地区");
        salaryCalculatePoolModel.districtEntityMap = districtCommonService.allDistrictNameMap();
        // 该用户所有地区和地区公司
        logger.info("获取该客户的地区公司关系");
        salaryCalculatePoolModel.customerDistrictEntityList = customerDistrictDao.getListByCustomerId(customerSalaryCulateCommand.getCustomerId());
        salaryCalculatePoolModel.customerDistrictEntityMap = salaryCalculatePoolModel.customerDistrictEntityListToMap(salaryCalculatePoolModel.customerDistrictEntityList);

        logger.info("开始收集并检验数据");
        for (ICModel importCheckResult : salaryCalculatePoolModel.importResultModel.getSuccessDataList()) {
            SalaryExcelMode salaryExcelMode = new SalaryExcelMode();
            try {
                logger.info("Excel一行的数据 ——》 业务处理model");
                salaryExcelMode = importCheckResult.toBean(salaryExcelMode);
            } catch (Exception e){
                logger.info("Excel转化业务Model失败，请检查模板和model");
                salaryCalculatePoolModel.importResultToFail(importCheckResult, "用户信息异常，请检查");
                continue;
            }

            //判断地区名称是否需要加上“市“ 顺带
            logger.info("处理地区");
            if (StringUtils.isNotBlank(salaryExcelMode.getDistrict()) && salaryExcelMode.getDistrict().length() > 0) {
                String lastWord = salaryExcelMode.getDistrict().substring(salaryExcelMode.getDistrict().length() - 1);
                if (!Objects.equals(lastWord, "省") && !Objects.equals(lastWord, "市") && !Objects.equals(lastWord, "区") && !Objects.equals(lastWord, "县")) {
                    salaryExcelMode.setDistrict(salaryExcelMode.getDistrict() + "市");
                }
                if (null == salaryCalculatePoolModel.districtEntityMap.get(salaryExcelMode.getDistrict())) {
                    salaryExcelMode.setDistrict("");
                    salaryCalculatePoolModel.importResultToFail(importCheckResult, "地区" + salaryExcelMode.getDistrict() + "不存在");
                } else {
                    salaryExcelMode.setDistrictId(salaryCalculatePoolModel.districtEntityMap.get(salaryExcelMode.getDistrict()).getId());
                }
            }

            logger.info("客户地区下的公司验证");
            //客户-地区公司验证
            if (StringUtils.isNotBlank(salaryExcelMode.getDistrict()) && StringUtils.isNotBlank(salaryExcelMode.getCorpName())) {
                List<String> customerDistrictNameList = salaryCalculatePoolModel.customerDistrictEntityMap.get(salaryExcelMode.getDistrict());
                if (null == customerDistrictNameList || !customerDistrictNameList.contains(salaryExcelMode.getCorpName())) {
                    if (null == customerDistrictNameList) {
                        customerDistrictNameList = new ArrayList<>();
                    }
                    customerDistrictNameList.add(salaryExcelMode.getCorpName());
                    CustomerDistrictEntity customerDistrictEntity = customerDistrictService.newCustomerDistrictEntity(
                            customerSalaryCulateCommand.getCustomerId(),
                            salaryExcelMode.getCorpName(),
                            salaryCalculatePoolModel.districtEntityMap.get(salaryExcelMode.getDistrict()));
                    salaryCalculatePoolModel.newCustomerDistrictEntityList.add(customerDistrictEntity);
                    salaryCalculatePoolModel.customerDistrictEntityMap.put(salaryExcelMode.getDistrict(), customerDistrictNameList);
                }
            }

            /*if (StringUtils.isNotBlank(salaryExcelMode.getDistrict()) && null != salaryCalculatePoolModel.customerDistrictEntityMap.get(salaryExcelMode.getDistrict())) {
                // 如果地区下的公司分公司名称不同，视为错误数据。
                if (!salaryCalculatePoolModel.customerDistrictEntityMap.get(salaryExcelMode.getDistrict()).getCustomerDistrictName().equals(salaryExcelMode.getCorpName())) {
                    salaryCalculatePoolModel.importResultToFail(importCheckResult, "地区" + salaryExcelMode.getDistrict() + "的公司" + salaryExcelMode.getCorpName() + "与"
                            + salaryCalculatePoolModel.customerDistrictEntityMap.get(salaryExcelMode.getDistrict()).getCustomerDistrictName() + "冲突");
                }
            } else if (StringUtils.isNotBlank(salaryExcelMode.getDistrict()) && null != salaryCalculatePoolModel.districtEntityMap.get(salaryExcelMode.getDistrict())){
                // 新增客户该地区下的分公司
                CustomerDistrictEntity customerDistrictEntity = customerDistrictService.newCustomerDistrictEntity(
                        customerSalaryCulateCommand.getCustomerId(),
                        salaryExcelMode.getCorpName(),
                        salaryCalculatePoolModel.districtEntityMap.get(salaryExcelMode.getDistrict()));
                salaryCalculatePoolModel.newCustomerDistrictEntityList.add(customerDistrictEntity);
                salaryCalculatePoolModel.customerDistrictEntityMap.put(salaryExcelMode.getDistrict(), customerDistrictEntity);
            }*/

            //增加身份证验证合法性
            if (!IdcardValidator.isValidatedAllIdcard(salaryExcelMode.getIdCardNo())) {
                salaryCalculatePoolModel.importResultToFail(importCheckResult, "身份证" + salaryExcelMode.getIdCardNo() + "不合法");
            }

            //用户
            logger.info("收集用户信息");
            salaryCalculatePoolModel.idCardList.add(salaryExcelMode.getIdCardNo());
            salaryCalculatePoolModel.idCardRowMap.put(salaryExcelMode.getIdCardNo(), importCheckResult);
            salaryCalculatePoolModel.idCardExcelMap.put(salaryExcelMode.getIdCardNo(), salaryExcelMode);

        }
        // 3.检查用户
        logger.info("过滤用户");
        checkUser(salaryCalculatePoolModel);

        // 4.过滤错误数据
        for (ICModel importCheckResult : salaryCalculatePoolModel.errImportCheckResult) {
            importCheckResult.setSuccess(false);
            if (null != salaryCalculatePoolModel.importResultModel.getSuccessDataList().remove(salaryCalculatePoolModel.importResultModel.getSuccessDataList().indexOf(importCheckResult))) {
                salaryCalculatePoolModel.importResultModel.setSuccessImportNumber(salaryCalculatePoolModel.importResultModel.getSuccessImportNumber() - 1);
            }
            salaryCalculatePoolModel.importResultModel.getErrDataList().add(importCheckResult);
            salaryCalculatePoolModel.importResultModel.setFailImportNumber(salaryCalculatePoolModel.importResultModel.getFailImportNumber() + 1);
        }
        return salaryCalculatePoolModel;
    }

    public Long generateSalaryBatchNo() throws AryaServiceException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");//日时分秒 长度7-8位
        Long no = Long.parseLong(dateFormat.format(new Date()));
        return no;
    }

    private void checkUser(SalaryCalculatePoolModel salaryCalculatePoolModel) {
        //1.查询导入模板中用户与数据库中对应的数据，根据身份证查询
        Map<String, SalaryUserEntity> salaryUserMap = salaryUserService.userListToMap(salaryUserService.getUserList(salaryCalculatePoolModel.idCardList, salaryCalculatePoolModel.customerId));
        //2.遍历检查用户
        Iterator<String> iterator = salaryCalculatePoolModel.idCardRowMap.keySet().iterator();
        while (iterator.hasNext()) {
            String idCardNo = iterator.next();

            ICModel importCheckResult = salaryCalculatePoolModel.idCardRowMap.get(idCardNo);
            SalaryExcelMode salaryExcelMode = salaryCalculatePoolModel.idCardExcelMap.get(idCardNo);

            if (null == salaryUserMap.get(idCardNo)) {
                // 保存新用户
                SalaryUserEntity salaryUserEntity = new SalaryUserEntity();
                salaryUserEntity.setName(salaryExcelMode.getName().trim());
                salaryUserEntity.setIdCardNo(salaryExcelMode.getIdCardNo().trim());
                salaryUserEntity.setDistrictId(salaryExcelMode.getDistrictId());
                salaryUserEntity.setDistrictName(salaryExcelMode.getDistrict());
                salaryUserEntity.setCreateTime(System.currentTimeMillis());
                salaryUserEntity.setIsDelete(Constants.FALSE);
                salaryUserEntity.setPhoneNo(salaryExcelMode.getPhoneNo());
                salaryUserEntity.setBankAccount(salaryExcelMode.getBankAccount());
                salaryUserEntity.setIsUpdate(Constants.TRUE);
                salaryUserEntity.setCustomerId(salaryCalculatePoolModel.customerId);
                salaryUserEntity.setBankName(salaryExcelMode.getBankName());
                salaryUserMap.put(salaryUserEntity.getIdCardNo(), salaryUserEntity);
            } else {
                // 刷新数据库中的用户信息
                List<String> errInfos = checkUserMore(salaryUserMap.get(idCardNo), salaryCalculatePoolModel);
                if (CollectionUtils.isNotEmpty(errInfos)) {
                    for (String errInfo : errInfos) {
                        salaryCalculatePoolModel.importResultToFail(importCheckResult, errInfo);
                    }
                }
            }
        }
        salaryCalculatePoolModel.userEntityMap = salaryUserMap;
    }

    private List<String> checkUserMore(SalaryUserEntity salaryUserEntity,
                                 SalaryCalculatePoolModel salaryCalculatePoolModel) {
        SalaryExcelMode salaryExcelMode = salaryCalculatePoolModel.idCardExcelMap.get(salaryUserEntity.getIdCardNo());
        List<String> errInfos = new ArrayList<>();
        if (!salaryExcelMode.getName().trim().equals(salaryUserEntity.getName().trim())) {
            String errInfo = "身份证" + salaryUserEntity.getIdCardNo() + "姓名冲突";
            errInfos.add(errInfo);
            logger.info(errInfo);
        }
        if (StringUtils.isNotBlank(salaryExcelMode.getBankAccount()) && (StringUtils.isAnyBlank(salaryUserEntity.getBankAccount()) || !salaryUserEntity.getBankAccount().equals(salaryExcelMode.getBankAccount()))) {
            salaryUserEntity.setBankAccount(salaryExcelMode.getBankAccount());
            salaryUserEntity.setIsUpdate(Constants.TRUE);
        }
        if (StringUtils.isNotBlank(salaryExcelMode.getPhoneNo()) &&(StringUtils.isAnyBlank(salaryUserEntity.getPhoneNo()) || !salaryUserEntity.getPhoneNo().equals(salaryExcelMode.getPhoneNo()))) {
            salaryUserEntity.setPhoneNo(salaryExcelMode.getPhoneNo());
            salaryUserEntity.setIsUpdate(Constants.TRUE);
        }
        if (StringUtils.isNotBlank(salaryExcelMode.getCustomerId()) && !salaryUserEntity.getCustomerId().equals(salaryExcelMode.getCustomerId())) {
            salaryUserEntity.setCustomerId(salaryExcelMode.getCustomerId());
            salaryUserEntity.setIsUpdate(Constants.TRUE);
        }
        if (StringUtils.isNotBlank(salaryExcelMode.getBankName()) && (StringUtils.isAnyBlank(salaryUserEntity.getBankName()) || !salaryUserEntity.getBankName().equals(salaryExcelMode.getBankName()))) {
            salaryUserEntity.setBankName(salaryExcelMode.getBankName());
            salaryUserEntity.setIsUpdate(Constants.TRUE);
        }
        if (null != salaryExcelMode.getDistrictId() && (StringUtils.isAnyBlank(salaryUserEntity.getDistrictId()) || !salaryExcelMode.getDistrictId().equals(salaryUserEntity.getDistrictId()))) {
            salaryUserEntity.setDistrictId(salaryExcelMode.getDistrictId());
            salaryUserEntity.setDistrictName(salaryExcelMode.getDistrict());
            salaryUserEntity.setIsUpdate(Constants.TRUE);
        }
        return errInfos;
    }

    private Boolean checkImportModel (List<ImportReg> paramCheckList, ICResult importResultModel){
        CirOne:
        for (ImportReg importReg : paramCheckList) {
            for (TitleModel titleModel : importResultModel.getTitleModelList()) {
                if (importReg.getKey().equals(titleModel.getKey())) {
                    continue CirOne;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 计算每一条导入数据的薪资
     * @param customerSalaryCulateCommand
     * @param salaryCalculatePoolModel
     * @return 结果
     */
    @Override
    public SalaryCalculateListResult calculate(CustomerSalaryCulateCommand customerSalaryCulateCommand, SalaryCalculatePoolModel salaryCalculatePoolModel, Integer pageSize, Integer page) {
        logger.info("开始计算");
        SalaryCalculateListResult result = new SalaryCalculateListResult();
        // 遍历成功导入的数据
        for (ICModel importCheckResult : salaryCalculatePoolModel.importResultModel.getSuccessDataList()) {
            SalaryExcelMode salaryExcelMode = salaryCalculatePoolModel.idCardExcelMap.get(importCheckResult.getData().get("idCardNo").getValue());
            result.getCalculateResults().add(calculateModel(salaryExcelMode, customerSalaryCulateCommand, salaryCalculatePoolModel));
        }
        // 取分页数据
        if (null != page && null != pageSize) {
            logger.info("将计算结果分页---page:" + page + ",pageSize:" + pageSize);
            int total = result.getCalculateResults().size();
            result.setPages(Utils.calculatePages(total, pageSize));
            result.setCalculateResultPager(result.getCalculateResults().subList(pageSize * (page - 1), ((pageSize * page) > total ? total : (pageSize * page))));
        }
        result.setIcResult(salaryCalculatePoolModel.importResultModel);
        result.setWeek(customerSalaryCulateCommand.getWeek());
        return result;
    }


    @Override
    public SalaryCalculateListResult
    query(String condition, String customerId, Integer settlementInterval, Integer year, Integer month, Long week, Integer pageSize, Integer page) throws Exception{
        CustomerSalaryCulateCommand customerSalaryCulateCommand = new CustomerSalaryCulateCommand();
        customerSalaryCulateCommand.init(customerId, settlementInterval, year, month, week);
        SalaryCalculateListResult result = new SalaryCalculateListResult();
        List<SalaryCalculateEntity> salaryCalculateEntityList = salaryCalculateDao.getMonthList(customerSalaryCulateCommand.getCustomerId(), customerSalaryCulateCommand.getYear()
                , customerSalaryCulateCommand.getMonth(), customerSalaryCulateCommand.getWeek());
        List<Long> weeks = new ArrayList<>();
        for (SalaryCalculateEntity salaryCalculateEntity : salaryCalculateEntityList) {
            weeks.add(salaryCalculateEntity.getWeek());
        }
        if (CollectionUtils.isEmpty(weeks)) {
            return result;
        }
        //查出这些批次中的薪资数据
        //查询条件下的用户
        List<SalaryUserEntity> userEntityList = new ArrayList<>();
        if (StringUtils.isNotBlank(condition)) {
            userEntityList.addAll(salaryUserDao.getListByCondition(condition.trim(), customerId));
        }
        List<SalaryCalculateDetailEntity> salaryCalculateDetailEntityList = salaryCalculateDetailDao.weeksDatas(userEntityList, condition, weeks, customerSalaryCulateCommand.getYear()
                , customerSalaryCulateCommand.getMonth(), customerSalaryCulateCommand.getCustomerId());

        //该客户下的所有用户
        Map<String, SalaryUserEntity> userEntityMap = salaryUserService.customerUserMap(customerSalaryCulateCommand.getCustomerId());
        //将同一身份证的用户薪资数据合并
        Map<String, SalaryCalculateListResult.SalaryCalculateResult> salaryCalculateDetailEntityMap = new LinkedMap();
        for(SalaryCalculateDetailEntity detailEntity : salaryCalculateDetailEntityList) {
            String key = detailEntity.getUserId() + "-" + detailEntity.getDistrictId() + "-" + detailEntity.getCorpName();
            SalaryUserEntity salaryUserEntity = userEntityMap.get(detailEntity.getUserId());
            if (null == salaryUserEntity) {
                continue;
            }
            if (null == salaryCalculateDetailEntityMap.get(key)) {
                salaryCalculateDetailEntityMap.put(key, SalaryCalculateListResult.convert(detailEntity, salaryUserEntity));
            } else {
                SalaryCalculateListResult.SalaryCalculateResult temp = salaryCalculateDetailEntityMap.get(key);
                temp.setId(null);
                temp.setTaxableSalary(new BigDecimal(null == temp.getTaxableSalary() ? "0" : temp.getTaxableSalary()).add(detailEntity.getTaxableSalary()).toString());
                temp.setPersonalTax(new BigDecimal(null == temp.getPersonalTax() ? "0" : temp.getPersonalTax()).add(detailEntity.getPersonalTax()).toString());
                temp.setBrokerage(new BigDecimal(null == temp.getBrokerage() ? "0" : temp.getBrokerage()).add(detailEntity.getBrokerage()).toString());
                temp.setNetSalary(new BigDecimal(null == temp.getNetSalary() ? "0" : temp.getNetSalary()).add(detailEntity.getNetSalary()).toString());
            }
        }

        result.getCalculateResults().addAll(salaryCalculateDetailEntityMap.values());
        //分页
        if (null != pageSize && null != page) {
            int total = result.getCalculateResults().size();
            result.setPages(Utils.calculatePages(total, pageSize));
            result.setCalculateResultPager(result.getCalculateResults().subList(pageSize * (page - 1), ((pageSize * page) > total ? total : (pageSize * page))));
        }
        //统计
        SalaryCalculatePoolModel salaryCalculatePoolModel = new SalaryCalculatePoolModel();
        salaryCalculatePoolModel.customerDistrictEntityList = customerDistrictDao.getListByCustomerId(customerSalaryCulateCommand.getCustomerId());
        salaryCalculatePoolModel.customerDistrictEntityMap = salaryCalculatePoolModel.customerDistrictEntityListToMap(salaryCalculatePoolModel.customerDistrictEntityList);
        countSalaryCalculat(result, salaryCalculatePoolModel);
        return result;
    }

    private SalaryCalculateListResult.SalaryCalculateResult calculateModel(SalaryExcelMode salaryExcelModel, CustomerSalaryCulateCommand customerSalaryCulateCommand, SalaryCalculatePoolModel salaryCalculatePoolModel) {
        SalaryCalculateListResult.SalaryCalculateResult salaryCalculateResult = new SalaryCalculateListResult.SalaryCalculateResult();
        SalaryCalculateModel salaryCalculateModel = null;
        if (salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.defined
                || salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.standard) {
            salaryCalculateModel = calculateBumuModel(salaryExcelModel, customerSalaryCulateCommand, salaryCalculatePoolModel);
        }
        if (salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.humanPool) {
            salaryCalculateModel = calculateHumanpoolModel(salaryExcelModel, customerSalaryCulateCommand, salaryCalculatePoolModel);
        }
        if (salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.ordinary) {
            salaryCalculateModel = calculateOrdinaryModel(salaryExcelModel, customerSalaryCulateCommand, salaryCalculatePoolModel);
        }


        logger.info("生成一条数据明细");
        salaryCalculateResult.setName(salaryExcelModel.getName());
        salaryCalculateResult.setIdcardNo(salaryExcelModel.getIdCardNo());
        salaryCalculateResult.setTaxableSalary(salaryCalculateModel.getTaxableSalary().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryCalculateResult.setTaxableWeekSalary(salaryCalculateModel.getTaxableWeekSalary().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryCalculateResult.setDistrictName(salaryExcelModel.getDistrict());
        salaryCalculateResult.setDistrictId(salaryExcelModel.getDistrictId());
        salaryCalculateResult.setCorpName(salaryExcelModel.getCorpName());
        salaryCalculateResult.setBankAccount(salaryExcelModel.getBankAccount());
        salaryCalculateResult.setBankName(salaryExcelModel.getBankName());
        salaryCalculateResult.setBrokerage(salaryCalculateModel.getBrokerage().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryCalculateResult.setNetSalary(salaryCalculateModel.getNetSalary().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryCalculateResult.setPhone(salaryExcelModel.getPhoneNo());
        salaryCalculateResult.setPersonalTax(salaryCalculateModel.getPersonalTax().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        salaryCalculateResult.setExcelInfo(salaryCalculateModel.getExtraValueMaps());
        return salaryCalculateResult;
    }

    /**
     * 不木计算
     * @param salaryExcelModel
     * @param customerSalaryCulateCommand
     * @param salaryCalculatePoolModel
     * @return
     */
    private SalaryCalculateModel calculateBumuModel(SalaryExcelMode salaryExcelModel, CustomerSalaryCulateCommand customerSalaryCulateCommand, SalaryCalculatePoolModel salaryCalculatePoolModel) {
        SalaryCalculateModel salaryCalculateModel = new SalaryCalculateModel();
        SalaryUserEntity salaryUserEntity = salaryCalculatePoolModel.userEntityMap.get(salaryExcelModel.getIdCardNo());
        if (salaryUserEntity == null || null == salaryUserEntity.getId()) {
            //用户为新用户，之前无薪资记录
            salaryCalculateModel = calculateBumuSalaryToModel(salaryExcelModel.getTaxableSalary(), salaryCalculatePoolModel.salaryCalculateRuleModel);
        } else {
            //统计本年-月-批次之前的薪资记录
            salaryCalculateModel = calculateWeekSalary(salaryUserEntity, salaryExcelModel.getTaxableSalary(), customerSalaryCulateCommand, salaryCalculatePoolModel.salaryCalculateRuleModel);
            if (salaryCalculateModel == null) {
                salaryCalculateModel = calculateBumuSalaryToModel(salaryExcelModel.getTaxableSalary(), salaryCalculatePoolModel.salaryCalculateRuleModel);
            } else {
                //计算个税---需要累计税前薪资计算个税
                SalaryCalculateModel salaryPersonal = calculateBumuSalaryToModel(salaryCalculateModel.getTaxableSalary(), salaryCalculatePoolModel.salaryCalculateRuleModel);
                //计算薪资服务费---修改为只根据当前导入批次收取薪资服务费
                SalaryCalculateModel salaryBrokerge = calculateBumuSalaryToModel(salaryExcelModel.getTaxableSalary(), salaryCalculatePoolModel.salaryCalculateRuleModel);
                salaryCalculateModel.setTaxableSalary(salaryExcelModel.getTaxableSalary());
                salaryCalculateModel.setPersonalTax(salaryPersonal.getPersonalTax().subtract(salaryCalculateModel.getPersonalTax()));
                salaryCalculateModel.setBrokerage(salaryBrokerge.getBrokerage());
                salaryCalculateModel.setNetSalary(salaryCalculateModel.getTaxableSalary().subtract(salaryCalculateModel.getPersonalTax()));
                if (salaryCalculatePoolModel.salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.defined
                        && salaryCalculatePoolModel.salaryCalculateRuleModel.getCostBearing() == SalaryEnum.CostBearing.personal) {
                    salaryCalculateModel.setNetSalary(salaryCalculateModel.getNetSalary().subtract(salaryCalculateModel.getBrokerage()));
                }
                salaryCalculateModel.setTaxableWeekSalary(salaryExcelModel.getTaxableSalary());
            }
        }
        return salaryCalculateModel;
    }

    /**
     * 蓝领计算
     * @param salaryExcelModel
     * @param customerSalaryCulateCommand
     * @param salaryCalculatePoolModel
     * @return
     */
    private SalaryCalculateModel calculateHumanpoolModel(SalaryExcelMode salaryExcelModel, CustomerSalaryCulateCommand customerSalaryCulateCommand, SalaryCalculatePoolModel salaryCalculatePoolModel) {
        SalaryCalculateModel salaryCalculateModel = new SalaryCalculateModel();
        SalaryUserEntity salaryUserEntity = salaryCalculatePoolModel.userEntityMap.get(salaryExcelModel.getIdCardNo());
        if (salaryUserEntity == null || null == salaryUserEntity.getId()) {
            //用户为新用户，之前无薪资记录
            salaryCalculateModel = calculateHumanPoolSalaryToModel(null, salaryExcelModel, salaryCalculatePoolModel.idCardRowMap.get(salaryExcelModel.getIdCardNo()), salaryCalculatePoolModel.salaryCalculateRuleModel);
        } else {
            //统计本年-月-批次之前的薪资记录
            salaryCalculateModel = calculateWeekSalary(salaryUserEntity, null == salaryExcelModel.getTaxableSalary() ? new BigDecimal(0) : salaryExcelModel.getTaxableSalary()
                    , customerSalaryCulateCommand, salaryCalculatePoolModel.salaryCalculateRuleModel);
            if (salaryCalculateModel == null) {
                salaryCalculateModel = calculateHumanPoolSalaryToModel(null, salaryExcelModel, salaryCalculatePoolModel.idCardRowMap.get(salaryExcelModel.getIdCardNo()), salaryCalculatePoolModel.salaryCalculateRuleModel);
            } else {
                SalaryCalculateModel salaryCalculateModelTemp = calculateHumanPoolSalaryToModel(salaryCalculateModel.getTaxableSalary(), salaryExcelModel, salaryCalculatePoolModel.idCardRowMap.get(salaryExcelModel.getIdCardNo()), salaryCalculatePoolModel.salaryCalculateRuleModel);
                salaryCalculateModel.setTaxableSalary(salaryCalculateModelTemp.getTaxableSalary());
                salaryCalculateModel.setPersonalTax(salaryCalculateModelTemp.getPersonalTax().subtract(salaryCalculateModel.getPersonalTax()));
                salaryCalculateModel.setBrokerage(salaryCalculateModelTemp.getBrokerage().subtract(salaryCalculateModel.getBrokerage()));
                salaryCalculateModel.setNetSalary(salaryCalculateModelTemp.getTaxableSalary().subtract(salaryCalculateModelTemp.getPersonalTax()));
                salaryCalculateModel.setTaxableWeekSalary(salaryCalculateModelTemp.getTaxableWeekSalary());
                salaryCalculateModel.setExtraValueMaps(salaryCalculateModelTemp.getExtraValueMaps());
            }
        }
        return salaryCalculateModel;
    }

    private SalaryCalculateModel calculateOrdinaryModel(SalaryExcelMode salaryExcelModel, CustomerSalaryCulateCommand customerSalaryCulateCommand, SalaryCalculatePoolModel salaryCalculatePoolModel) {
        SalaryCalculateModel salaryCalculateModel = new SalaryCalculateModel();
        SalaryUserEntity salaryUserEntity = salaryCalculatePoolModel.userEntityMap.get(salaryExcelModel.getIdCardNo());
        if (salaryUserEntity == null || null == salaryUserEntity.getId()) {
            //用户为新用户，之前无薪资记录
            salaryCalculateModel = calculateOrdinarySalaryToModel(null, salaryExcelModel, salaryCalculatePoolModel.idCardRowMap.get(salaryExcelModel.getIdCardNo()), salaryCalculatePoolModel.salaryCalculateRuleModel);
        } else {
            //统计本年-月-批次之前的薪资记录
            salaryCalculateModel = calculateWeekSalary(salaryUserEntity, null == salaryExcelModel.getTaxableSalary() ? new BigDecimal(0) : salaryExcelModel.getTaxableSalary()
                    , customerSalaryCulateCommand, salaryCalculatePoolModel.salaryCalculateRuleModel);
            if (salaryCalculateModel == null) {
                salaryCalculateModel = calculateOrdinarySalaryToModel(null, salaryExcelModel, salaryCalculatePoolModel.idCardRowMap.get(salaryExcelModel.getIdCardNo()), salaryCalculatePoolModel.salaryCalculateRuleModel);
            } else {
                SalaryCalculateModel salaryCalculateModelTemp = calculateOrdinarySalaryToModel(salaryCalculateModel.getTaxableSalary(), salaryExcelModel, salaryCalculatePoolModel.idCardRowMap.get(salaryExcelModel.getIdCardNo()), salaryCalculatePoolModel.salaryCalculateRuleModel);
                salaryCalculateModel.setTaxableSalary(salaryCalculateModelTemp.getTaxableSalary());
                salaryCalculateModel.setPersonalTax(salaryCalculateModelTemp.getPersonalTax().subtract(salaryCalculateModel.getPersonalTax()));
                salaryCalculateModel.setBrokerage(salaryCalculateModelTemp.getBrokerage());
                salaryCalculateModel.setNetSalary(salaryCalculateModelTemp.getTaxableSalary().subtract(salaryCalculateModelTemp.getPersonalTax()));
                salaryCalculateModel.setTaxableWeekSalary(salaryCalculateModelTemp.getTaxableWeekSalary());
                salaryCalculateModel.setExtraValueMaps(salaryCalculateModelTemp.getExtraValueMaps());
            }
        }
        return salaryCalculateModel;
    }

    private SalaryCalculateModel calculateBumuSalaryToModel(BigDecimal taxableSalary, SalaryCalculateRuleModel salaryCalculateRuleModel) {
        SalaryCalculateModel salaryCalculateModel = new SalaryCalculateModel();
        GlobalConfig globalConfig = new GlobalConfig();
        BumuCalculateSuite calculateSuite = new BumuCalculateSuite();
        calculateSuite.setBumuTaxFactor(new BumuTaxFactor());
        calculateSuite.setBumuServiceFactor(new BumuServiceFactor());
        calculateSuite.setBumuSalaryAfterFactor(new BumuSalaryAfterFactor());

        calculateSuite.init();

        BumuSalaryConfig bumuSalaryConfig = new BumuSalaryConfig(globalConfig);
        if (salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.defined) {
            bumuSalaryConfig.initTaxRatios(salaryCalculateRuleModel.getTaxGears());
            bumuSalaryConfig.setBrokerageRate(null == salaryCalculateRuleModel.getBrokerageRate() ? 0f : salaryCalculateRuleModel.getBrokerageRate().floatValue());
            bumuSalaryConfig.setPersonBeatServiceFee(null != salaryCalculateRuleModel.getCostBearing() && salaryCalculateRuleModel.getCostBearing() == SalaryEnum.CostBearing.personal ? true : false  );
        }
        if (salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.standard) {
            bumuSalaryConfig.setTaxThrottle(null == salaryCalculateRuleModel.getThresholdTax() ? 0f : salaryCalculateRuleModel.getThresholdTax().floatValue());
            bumuSalaryConfig.setBrokerageFee(null == salaryCalculateRuleModel.getBrokerage() ? 0f : salaryCalculateRuleModel.getBrokerage().floatValue());
        }

        bumuSalaryConfig.setRuleType(salaryCalculateRuleModel.getRuleType());

        List<com.bumu.arya.salary.calculate.model.SalaryModel> salaryModelList = new ArrayList<>();
        com.bumu.arya.salary.calculate.model.SalaryModel salaryModel = new com.bumu.arya.salary.calculate.model.SalaryModel();
        salaryModel.addValue("taxableSalary", new Value(taxableSalary.floatValue(), "税前工资"));
        salaryModel.addValue("tax_sub", new Value(0f, "上次发薪扣税"));
        salaryModelList.add(salaryModel);

        List<com.bumu.arya.salary.calculate.model.SalaryModel> result = calculateEngine.calculate(calculateSuite, bumuSalaryConfig, salaryModelList);
        if (CollectionUtils.isNotEmpty(result)) {
            com.bumu.arya.salary.calculate.model.SalaryModel resultModel = result.get(0);
            salaryCalculateModel.setBrokerage(new BigDecimal(String.valueOf(resultModel.getValue("brokerage").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setPersonalTax(new BigDecimal(String.valueOf(resultModel.getValue("tax").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setTaxableSalary(new BigDecimal(String.valueOf(resultModel.getValue("taxableSalary").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setNetSalary(new BigDecimal(String.valueOf(resultModel.getValue("netSalary").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setTaxableWeekSalary(taxableSalary);
        }
        return salaryCalculateModel;
    }

    private SalaryCalculateModel calculateOrdinarySalaryToModel(BigDecimal taxableSalaryTotal, SalaryExcelMode salaryExcelMode, ICModel icModel, SalaryCalculateRuleModel calculateRuleModel) {
        SalaryCalculateModel salaryCalculateModel = new SalaryCalculateModel();
        GlobalConfig globalConfig = new GlobalConfig();
        OrdinaryCalculateSuite ordinaryCalculateSuite = new OrdinaryCalculateSuite();
        ordinaryCalculateSuite.setDaySalaryFactor(new DaySalaryFactor());
        ordinaryCalculateSuite.setWorkAttendanceDaysFactor(new WorkAttendanceDaysFactor());
        ordinaryCalculateSuite.setAbsentSubstractFactor(new AbsentSubstractFactor());
        ordinaryCalculateSuite.setLeaveSubFactor(new LeaveSubFactor());
        //ordinaryCalculateSuite.setGrossSalaryFactor(new GrossSalaryFactor());
        ordinaryCalculateSuite.setOrdinarySalaryBeforeFactor(new com.bumu.arya.salary.calculate.factor.ordinary.OrdinarySalaryBeforeFactor());
        ordinaryCalculateSuite.setTaxFactor(new TaxFactor());
        ordinaryCalculateSuite.setOrdinarySalaryAfterFactor(new OrdinarySalaryAfterFactor());

        ordinaryCalculateSuite.init();

        OrdinarySalaryConfig ordinarySalaryConfig = new OrdinarySalaryConfig(globalConfig);
        ordinarySalaryConfig.setIllSubRatio(calculateRuleModel.getIllSubRatioOrdinary().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        ordinarySalaryConfig.setAffairSubRatio(calculateRuleModel.getAffairSubRatioOrdinary().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        ordinarySalaryConfig.setTaxThrottle(null == calculateRuleModel.getThresholdTaxOrdinary() ? 0f : calculateRuleModel.getThresholdTaxOrdinary().floatValue());
        ordinarySalaryConfig.setTaxableSalaryTotal(null == taxableSalaryTotal ? 0f : taxableSalaryTotal.floatValue());

        List<com.bumu.arya.salary.calculate.model.SalaryModel> salaryModelList = new ArrayList<>();
        com.bumu.arya.salary.calculate.model.SalaryModel salaryModel = new com.bumu.arya.salary.calculate.model.SalaryModel();
        salaryModel.addValue("BASE_SALARY", new Value<Float>(salaryExcelMode.getSalaryBase().floatValue(), ""));
        salaryModel.addValue("WORK_DAYS", new Value<Float>(salaryExcelMode.getWorkDays(), ""));
        salaryModel.addValue("AFFAIR_DAYS", new Value<Float>(salaryExcelMode.getAffairDays(), ""));
        salaryModel.addValue("ILL_DAYS", new Value<Float>(salaryExcelMode.getIllDays(), ""));
        salaryModel.addValue("ABSENSE_DAYS", new Value<Float>(salaryExcelMode.getAbsenseDays(), ""));
        salaryModel.addValue("ANNUAL_DAYS", new Value<Float>(salaryExcelMode.getAnnualDays(), ""));
        salaryModel.addValue("PRECREATE_DAYS", new Value<Float>(salaryExcelMode.getPrecreateDays(), ""));
        salaryModel.addValue("MARRY_DAYS", new Value<Float>(salaryExcelMode.getMarryDays(), ""));
        salaryModel.addValue("FUNERAL_DAYS", new Value<Float>(salaryExcelMode.getFuneralDays(), ""));
        salaryModel.addValue("OTHER_SUB", new Value<Float>(salaryExcelMode.getOtherSub(), ""));
        salaryModel.addValue("ORDINARY_BROKERAGE", new Value<Float>(calculateRuleModel.getBrokerageOrdinary().floatValue(), "薪资服务费"));
        // 多值 PLUS
        Set<Value<Float>> plusSet = new HashSet<>();
        // 多值 SUBSTRACT
        Set<Value<Float>> subSet = new HashSet<>();
        // 多值 atPlus
        Set<Value<Float>> atPlustSet = new HashSet<>();
        // 多值 atSubstract
        Set<Value<Float>> atSubstract = new HashSet<>();

        ICModel.ImportData extraData = icModel.getData().get("extra");
        if (null != extraData) {
            List<Map<String, String>> extraMapList = new Gson().fromJson(extraData.getValue(), ArrayList.class);
            for (Map<String, String> extraMap : extraMapList) {
                if (extraMap.get("key").equals("plus") && StringUtils.isNotBlank(extraMap.get("value"))) {
                    plusSet.add(new Value<Float>(Float.valueOf(extraMap.get("value")), extraMap.get("title")));
                }
                if (extraMap.get("key").equals("substract") && StringUtils.isNotBlank(extraMap.get("value"))) {
                    subSet.add(new Value<Float>(Float.valueOf(extraMap.get("value")), extraMap.get("title")));
                }
                if (extraMap.get("key").equals("atPlus") && StringUtils.isNotBlank(extraMap.get("value"))) {
                    subSet.add(new Value<Float>(Float.valueOf(extraMap.get("value")), extraMap.get("title")));
                }
                if (extraMap.get("key").equals("atSubstract") && StringUtils.isNotBlank(extraMap.get("value"))) {
                    subSet.add(new Value<Float>(Float.valueOf(extraMap.get("value")), extraMap.get("title")));
                }
            }
        }

        salaryModel.addMultiValue("PLUS", plusSet);
        salaryModel.addMultiValue("SUBSTRACT", subSet);
        salaryModel.addMultiValue("AT_PLUS", atPlustSet);
        salaryModel.addMultiValue("AT_SUBSTRACT", atSubstract);
        salaryModelList.add(salaryModel);

        List<com.bumu.arya.salary.calculate.model.SalaryModel> result = calculateEngine.calculate(ordinaryCalculateSuite, ordinarySalaryConfig, salaryModelList);
        if (CollectionUtils.isNotEmpty(result)) {
            com.bumu.arya.salary.calculate.model.SalaryModel resultModel = result.get(0);
            BigDecimal taxableSalary = new BigDecimal(String.valueOf(null == resultModel.getValue("TAXABLE_SALARY") ? 0 : resultModel.getValue("TAXABLE_SALARY").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP);
            salaryCalculateModel.setPersonalTax(new BigDecimal(String.valueOf(null == resultModel.getValue("TAX") ? 0 : resultModel.getValue("TAX").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setTaxableSalary(taxableSalary);
            salaryCalculateModel.setBrokerage(new BigDecimal(String.valueOf(null == resultModel.getValue("ORDINARY_BROKERAGE") ? 0 : resultModel.getValue("ORDINARY_BROKERAGE").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setNetSalary(new BigDecimal(String.valueOf(null == resultModel.getValue("NET_SALARY") ? 0 : resultModel.getValue("NET_SALARY").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setTaxableWeekSalary(taxableSalary.subtract(new BigDecimal(ordinarySalaryConfig.getTaxableSalaryTotal())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryExcelMode.setTaxableSalary(taxableSalary.subtract(new BigDecimal(ordinarySalaryConfig.getTaxableSalaryTotal())).setScale(2, BigDecimal.ROUND_HALF_UP));
            for (Value v : resultModel.getValues().values()) {
                Map<String, String> temp = new HashedMap();
                if (StringUtils.isAnyBlank(v.getTitle())) {
                    continue;
                }
                temp.put("title", v.getTitle());
                try {
                    temp.put("value", new BigDecimal(String.valueOf(v.getValue())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                } catch (Exception e) {
                    temp.put("value", String.valueOf(v.getValue()));
                }
                salaryCalculateModel.getExtraValueMaps().add(temp);
            }
        }
        return salaryCalculateModel;
    }

    private SalaryCalculateModel calculateHumanPoolSalaryToModel(BigDecimal taxableSalaryTotal, SalaryExcelMode salaryExcelMode, ICModel icModel, SalaryCalculateRuleModel salaryCalculateRuleModel) {
        SalaryCalculateModel salaryCalculateModel = new SalaryCalculateModel();
        GlobalConfig globalConfig = new GlobalConfig();
        HumanpoolCalculateSuite calculateSuite = new HumanpoolCalculateSuite();
        calculateSuite.setHourSalaryFactor(new HourSalaryFactor());
        calculateSuite.setWorkSalaryFactor(new WorkSalaryFactor());
        calculateSuite.setNewLeaveHoursFactor(new NewLeaveHoursFactor());
        calculateSuite.setWorkdayOvertimeSalaryFactor(new WorkdayOvertimeSalaryFactor());
        calculateSuite.setWeekendOvertimeSalaryFactor(new WeekendOvertimeSalaryFactor());
        calculateSuite.setNationalOvertimeSalaryFactor(new NationalOvertimeSalaryFactor());
        calculateSuite.setFulltimeBonusFactor(new FulltimeBonusFactor());
        calculateSuite.setAffairSubFactor(new AffairSubFactor());
        calculateSuite.setIllSubFactor(new IllSubFactor());
        calculateSuite.setAbsenseSubFactor(new AbsenseSubFactor());
        calculateSuite.setNewLeaveSubFactor(new NewLeaveSubFactor());
        calculateSuite.setSalaryBeforeFactor(new SalaryBeforeFactor());
        calculateSuite.setTaxFactor(new TaxFactor());
        calculateSuite.setHumanpoolSalaryAfterFactor(new HumanpoolSalaryAfterFactor());

        calculateSuite.init();

        HumanpoolSalaryConfig humanpoolSalaryConfig = new HumanpoolSalaryConfig(globalConfig);
        humanpoolSalaryConfig.initFullTimeBouns(salaryCalculateRuleModel.getFulltimeBonuList());
        humanpoolSalaryConfig.setAbsenceSubRatio(salaryCalculateRuleModel.getAbsenceSubRatio().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        humanpoolSalaryConfig.setAffairSubRatio(salaryCalculateRuleModel.getAffairSubRatio().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        humanpoolSalaryConfig.setIllSubRatio(salaryCalculateRuleModel.getIllSubRatio().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        humanpoolSalaryConfig.setNewLeaveAbsenceSubRatio(salaryCalculateRuleModel.getNewLeaveAbsenceSubRatio().divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        humanpoolSalaryConfig.setTaxableSalaryTotal(null == taxableSalaryTotal ? 0f : taxableSalaryTotal.floatValue());

        List<com.bumu.arya.salary.calculate.model.SalaryModel> salaryModelList = new ArrayList<>();
        com.bumu.arya.salary.calculate.model.SalaryModel salaryModel = new com.bumu.arya.salary.calculate.model.SalaryModel();
        salaryModel.addValue("BASE_SALARY", new Value<Float>(salaryExcelMode.getSalaryBase().floatValue(), ""));
        salaryModel.addValue("BASE_SALARY_OVERTIME", new Value<Float>(salaryExcelMode.getSalaryBaseOvertime().floatValue(), ""));
        salaryModel.addValue("STAFF_STATUS", new Value<String>(salaryExcelMode.getStaffStatus(), ""));
        salaryModel.addValue("SCHEDULE_DAYS", new Value<Float>(salaryExcelMode.getWorkDays(), ""));
        salaryModel.addValue("ILL_DAYS", new Value<Float>(salaryExcelMode.getIllDays(), ""));
        salaryModel.addValue("AFFAIR_DAYS", new Value<Float>(salaryExcelMode.getAffairDays(), ""));
        salaryModel.addValue("ABSENSE_DAYS", new Value<Float>(salaryExcelMode.getAbsenseDays(), ""));
        salaryModel.addValue("ANNUAL_DAYS", new Value<Float>(salaryExcelMode.getAnnualDays(), ""));
        salaryModel.addValue("PRECREATE_DAYS", new Value<Float>(salaryExcelMode.getPrecreateDays(), ""));
        salaryModel.addValue("MARRY_DAYS", new Value<Float>(salaryExcelMode.getMarryDays(), ""));
        salaryModel.addValue("NEW_LEAVE_DAYS", new Value<Float>(salaryExcelMode.getNewLeaveDays(), ""));
        salaryModel.addValue("WORK_HOURS", new Value<Float>(salaryExcelMode.getWorkHours().floatValue(), ""));
        salaryModel.addValue("WORKDAY_OVERTIME", new Value<Float>(salaryExcelMode.getOvertimeWorkDay(), ""));
        salaryModel.addValue("WEEKEND_OVERTIME", new Value<Float>(salaryExcelMode.getOvertimeWeekend(), ""));
        salaryModel.addValue("NATIONAL_OVERTIME", new Value<Float>(salaryExcelMode.getOvertimeNational(), ""));
        salaryModel.addValue("RECEIVE_SALARY", new Value<Float>(salaryExcelMode.getReceiveSalary(), ""));
        // 多值 PLUS
        Set<Value<Float>> plusSet = new HashSet<>();
        // 多值 SUBSTRACT
        Set<Value<Float>> subSet = new HashSet<>();
        // 多值 atPlus
        Set<Value<Float>> atPlustSet = new HashSet<>();
        // 多值 atSubstract
        Set<Value<Float>> atSubstract = new HashSet<>();

        ICModel.ImportData extraData = icModel.getData().get("extra");
        if (null != extraData) {
            List<Map<String, String>> extraMapList = new Gson().fromJson(extraData.getValue(), ArrayList.class);
            for (Map<String, String> extraMap : extraMapList) {
                if (extraMap.get("key").equals("plus") && StringUtils.isNotBlank(extraMap.get("value"))) {
                    plusSet.add(new Value<Float>(Float.valueOf(extraMap.get("value")), extraMap.get("title")));
                }
                if (extraMap.get("key").equals("substract") && StringUtils.isNotBlank(extraMap.get("value"))) {
                    subSet.add(new Value<Float>(Float.valueOf(extraMap.get("value")), extraMap.get("title")));
                }
                if (extraMap.get("key").equals("atPlus") && StringUtils.isNotBlank(extraMap.get("value"))) {
                    subSet.add(new Value<Float>(Float.valueOf(extraMap.get("value")), extraMap.get("title")));
                }
                if (extraMap.get("key").equals("atSubstract") && StringUtils.isNotBlank(extraMap.get("value"))) {
                    subSet.add(new Value<Float>(Float.valueOf(extraMap.get("value")), extraMap.get("title")));
                }
            }
        }

        salaryModel.addMultiValue("PLUS", plusSet);
        salaryModel.addMultiValue("SUBSTRACT", subSet);
        salaryModel.addMultiValue("AT_PLUS", atPlustSet);
        salaryModel.addMultiValue("AT_SUBSTRACT", atSubstract);
        salaryModelList.add(salaryModel);

        List<com.bumu.arya.salary.calculate.model.SalaryModel> result = calculateEngine.calculate(calculateSuite, humanpoolSalaryConfig, salaryModelList);
        if (CollectionUtils.isNotEmpty(result)) {
            com.bumu.arya.salary.calculate.model.SalaryModel resultModel = result.get(0);
            BigDecimal taxableSalary = new BigDecimal(String.valueOf(null == resultModel.getValue("TAXABLE_SALARY") ? 0 : resultModel.getValue("TAXABLE_SALARY").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP);
            salaryCalculateModel.setPersonalTax(new BigDecimal(String.valueOf(null == resultModel.getValue("TAX") ? 0 : resultModel.getValue("TAX").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setTaxableSalary(taxableSalary);
            salaryCalculateModel.setNetSalary(new BigDecimal(String.valueOf(null == resultModel.getValue("NET_SALARY") ? 0 : resultModel.getValue("NET_SALARY").getValue())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryCalculateModel.setTaxableWeekSalary(taxableSalary.subtract(new BigDecimal(humanpoolSalaryConfig.getTaxableSalaryTotal())).setScale(2, BigDecimal.ROUND_HALF_UP));
            salaryExcelMode.setTaxableSalary(taxableSalary.subtract(new BigDecimal(humanpoolSalaryConfig.getTaxableSalaryTotal())).setScale(2, BigDecimal.ROUND_HALF_UP));
            for (Value v : resultModel.getValues().values()) {
                Map<String, String> temp = new HashedMap();
                if (StringUtils.isAnyBlank(v.getTitle())) {
                    continue;
                }
                temp.put("title", v.getTitle());
                try {
                    temp.put("value", new BigDecimal(String.valueOf(v.getValue())).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                } catch (Exception e) {
                    temp.put("value", String.valueOf(v.getValue()));
                }
                salaryCalculateModel.getExtraValueMaps().add(temp);
            }
        }
        return salaryCalculateModel;
    }

    private SalaryCalculateModel calculateWeekSalary(SalaryUserEntity salaryUserEntity,  BigDecimal taxableSalary, CustomerSalaryCulateCommand customerSalaryCulateCommand, SalaryCalculateRuleModel salaryCalculateRuleModel){
        //本年-月 其他批次的收入
        List<SalaryCalculateDetailEntity> weekEntities = salaryCalculateDetailDao.getList(salaryUserEntity.getId(), customerSalaryCulateCommand.getYear()
                , customerSalaryCulateCommand.getMonth(), customerSalaryCulateCommand.getWeek(), customerSalaryCulateCommand.getCustomerId(), customerSalaryCulateCommand.getSettlementInterval());
        if (weekEntities.size() == 0) {
            return null;
        }
        //合计下
        return calculateWeekSalaryBaseOnForwardWeekSalaries(weekEntities, taxableSalary, salaryCalculateRuleModel);
    }

    private SalaryCalculateModel calculateWeekSalaryBaseOnForwardWeekSalaries(List<SalaryCalculateDetailEntity> forwardWeekSalaryEntities, BigDecimal taxableSalary, SalaryCalculateRuleModel ruleModel) {
        BigDecimal taxableSalaryCount = taxableSalary;//累计税前薪资
        BigDecimal personalTax = new BigDecimal("0");//累计个税
        BigDecimal brokerage = new BigDecimal("0");//累计薪资服务费
        BigDecimal netSalary = new BigDecimal("0");
        //累计
        for (SalaryCalculateDetailEntity salaryCalculateDetailEntity : forwardWeekSalaryEntities) {
            taxableSalaryCount = taxableSalaryCount.add(salaryCalculateDetailEntity.getTaxableSalary());
            personalTax = personalTax.add(salaryCalculateDetailEntity.getPersonalTax());
            brokerage = brokerage.add(salaryCalculateDetailEntity.getBrokerage());
            netSalary = netSalary.add(salaryCalculateDetailEntity.getNetSalary());
        }
        SalaryCalculateModel calculateModel = new SalaryCalculateModel();
        calculateModel.setTaxableSalary(taxableSalaryCount);
        calculateModel.setPersonalTax(personalTax);
        calculateModel.setBrokerage(brokerage);
        calculateModel.setNetSalary(netSalary);
        return calculateModel;
    }

    /**
     * 统计
     * @param salaryCalculateListResult
     * @param salaryCalculatePoolModel
     * @return
     */
    public void countSalaryCalculat(SalaryCalculateListResult salaryCalculateListResult, SalaryCalculatePoolModel salaryCalculatePoolModel) {
        logger.info("统计");
        List<SalaryCalculateCountResult> countResultList = new ArrayList<>();
        Map<String, SalaryCalculateCountResult> districtStatisticesMap = new HashMap<>();//key是地区
        int staffTotal = 0;
        BigDecimal taxableSalaryTotal = new BigDecimal("0");
        BigDecimal personalTaxTotal = new BigDecimal("0");
        BigDecimal brokerageTotal = new BigDecimal("0");
        BigDecimal netSalaryTotal = new BigDecimal("0");

        List<SalaryCalculateListResult.SalaryCalculateResult> calculateResults = salaryCalculateListResult.getCalculateResults();

        if (calculateResults == null || calculateResults.size() == 0) {
            return;
        }
        //遍历记录，分公司统计
        for (SalaryCalculateListResult.SalaryCalculateResult salaryCalculateResult : calculateResults) {
            String key = null == salaryCalculateResult.getDistrictName() ? "" : salaryCalculateResult.getDistrictName();
            key += "-" + (null == salaryCalculateResult.getCorpName() ? "" : salaryCalculateResult.getCorpName());
            SalaryCalculateCountResult countResult;
            if (districtStatisticesMap.containsKey(key)) {
                countResult = districtStatisticesMap.get(key);
            } else {
                countResult = new SalaryCalculateCountResult();
                countResult.setDistrictName(salaryCalculateResult.getDistrictName());
                countResult.setCorpName(null == salaryCalculateResult.getCorpName() ? "" : salaryCalculateResult.getCorpName());
                districtStatisticesMap.put(key, countResult);
            }
            countResult.setStaffCount(countResult.getStaffCount() + 1);
            countResult.setTaxableSalaryTotal(countResult.getTaxableSalaryTotal().add(new BigDecimal(salaryCalculateResult.getTaxableSalary())));
            countResult.setPersonalTaxTotal(countResult.getPersonalTaxTotal().add(new BigDecimal(salaryCalculateResult.getPersonalTax())));
            //countResult.setServiceChargeTotal(countResult.getServiceChargeTotal().add(salaryCalculateResult.getServiceCharge()));
            countResult.setBrokerageTotal(countResult.getBrokerageTotal().add(new BigDecimal(salaryCalculateResult.getBrokerage())));
            countResult.setNetSalaryTotal(countResult.getNetSalaryTotal().add(new BigDecimal(salaryCalculateResult.getNetSalary())));
        }
        if (districtStatisticesMap.size() == 0) {
            return;
        }
        countResultList.addAll(districtStatisticesMap.values());
        //统计总和
        logger.info("统计总和");
        if (countResultList.size() > 0) {
            for (SalaryCalculateCountResult countResult : districtStatisticesMap.values()) {
                staffTotal += countResult.getStaffCount();
                taxableSalaryTotal = taxableSalaryTotal.add(countResult.getTaxableSalaryTotal());
                personalTaxTotal = personalTaxTotal.add(countResult.getPersonalTaxTotal());
                brokerageTotal = brokerageTotal.add(countResult.getBrokerageTotal());
                netSalaryTotal = netSalaryTotal.add(countResult.getNetSalaryTotal());
            }
            SalaryCalculateCountResult totalCountResult = new SalaryCalculateCountResult();
            totalCountResult.setDistrictName("总计");
            totalCountResult.setCorpName("总计");
            totalCountResult.setDepartmentName("总计");
            totalCountResult.setStaffCount(staffTotal);
            totalCountResult.setTaxableSalaryTotal(taxableSalaryTotal);
            totalCountResult.setPersonalTaxTotal(personalTaxTotal);
            totalCountResult.setBrokerageTotal(brokerageTotal);
            totalCountResult.setNetSalaryTotal(netSalaryTotal);
            countResultList.add(totalCountResult);
        }
        salaryCalculateListResult.setSalaryCalculateCountResultList(countResultList);
    }

    @Override
    public void saveData(SalaryCalculateListResult salaryCalculateListResult, SalaryCalculatePoolModel salaryCalculatePoolModel
            , CustomerSalaryCulateCommand customerSalaryCulateCommand, File file) {
        StringBuffer logInfo = new StringBuffer();
        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        try {
            //1.保存地区-客户公司
            for (CustomerDistrictEntity customerDistrictEntity : salaryCalculatePoolModel.newCustomerDistrictEntityList) {
                customerDistrictDao.create(customerDistrictEntity);
            }
            //2.保存用户
            Collection<SalaryUserEntity> userEntities = salaryCalculatePoolModel.userEntityMap.values();
            for (SalaryUserEntity salaryUserEntity : userEntities) {
                if (null != salaryUserEntity.getIsUpdate() && salaryUserEntity.getIsUpdate() == Constants.TRUE) {
                    if (null == salaryUserEntity.getId()) {
                        salaryUserEntity.setId(Utils.makeUUID());
                        salaryUserEntity.setIsUpdate(Constants.FALSE);
                        salaryUserEntity.setCustomerId(customerSalaryCulateCommand.getCustomerId());
                        //salaryUserService.saveUser(salaryUserEntity);
                        salaryUserDao.create(salaryUserEntity);
                        salaryCalculatePoolModel.userEntityMap.put(salaryUserEntity.getIdCardNo(), salaryUserEntity);
                    } else {
                        salaryUserEntity.setIsUpdate(Constants.FALSE);
                        salaryUserEntity.setCustomerId(customerSalaryCulateCommand.getCustomerId());
                        salaryUserDao.update(salaryUserEntity);
                    }
                }
            }
            SalaryCalculateEntity salaryCalculateEntity = salaryCalculateDao.getOne(customerSalaryCulateCommand.getCustomerId(), customerSalaryCulateCommand.getYear()
                    , customerSalaryCulateCommand.getMonth(), customerSalaryCulateCommand.getWeek());
                //3.保存导入主数据
            if (null == salaryCalculateEntity) {
                salaryCalculateEntity = new SalaryCalculateEntity();
                salaryCalculateEntity.setId(Utils.makeUUID());
                salaryCalculateEntity.setSettlementInterval(customerSalaryCulateCommand.getSettlementInterval());
                salaryCalculateEntity.setIsDelete(Constants.FALSE);
                salaryCalculateEntity.setCreateTime(System.currentTimeMillis());
                salaryCalculateEntity.setCreateUser(currentUser.getId());
                salaryCalculateEntity.setCustomerId(customerSalaryCulateCommand.getCustomerId());
                salaryCalculateEntity.setFileName(file.getName());
                salaryCalculateEntity.setMonth(customerSalaryCulateCommand.getMonth());
                salaryCalculateEntity.setYear(customerSalaryCulateCommand.getYear());
                salaryCalculateEntity.setWeek(customerSalaryCulateCommand.getWeek());
                salaryCalculateEntity.setIsDeduct(Constants.FALSE);
                salaryCalculateDao.create(salaryCalculateEntity);
            } else {
                //重复导入需要标记 可以重新进行确认扣款
                salaryCalculateEntity.setIsDeduct(Constants.FALSE);
                salaryCalculateDao.update(salaryCalculateEntity);
            }
            //4.保存导入明细数据
            List<SalaryCalculateDetailEntity> repeatDetailList = new ArrayList<>();
            Map<String, SalaryCalculateDetailEntity> detailMap = new HashMap<>();
            // 同批次薪资数据
            List<SalaryCalculateDetailEntity> list = salaryCalculateDetailDao.findListByWeek(salaryCalculateEntity.getWeek(), salaryCalculateEntity.getCustomerId());
            for (SalaryCalculateDetailEntity salaryCalculateDetailEntity : list) {
                detailMap.put(salaryCalculateDetailEntity.getUserId(), salaryCalculateDetailEntity);
            }
            // 批次中 覆盖原来的数据
            for (SalaryCalculateListResult.SalaryCalculateResult salaryCalculateResult : salaryCalculateListResult.getCalculateResults()) {
                SalaryCalculateDetailEntity salaryCalculateDetailEntity = new SalaryCalculateDetailEntity();
                salaryCalculateDetailEntity.setId(Utils.makeUUID());
                salaryCalculateDetailEntity.setWeek(customerSalaryCulateCommand.getWeek());
                salaryCalculateDetailEntity.setMonth(customerSalaryCulateCommand.getMonth());
                salaryCalculateDetailEntity.setYear(customerSalaryCulateCommand.getYear());
                salaryCalculateDetailEntity.setCreateUser(currentUser.getId());
                salaryCalculateDetailEntity.setCreateTime(System.currentTimeMillis());
                salaryCalculateDetailEntity.setCustomerId(customerSalaryCulateCommand.getCustomerId());
                salaryCalculateDetailEntity.setDistrictId(salaryCalculateResult.getDistrictId());
                salaryCalculateDetailEntity.setBrokerage(new BigDecimal(salaryCalculateResult.getBrokerage()).setScale(2, BigDecimal.ROUND_HALF_UP));
                salaryCalculateDetailEntity.setNetSalary(new BigDecimal(salaryCalculateResult.getNetSalary()).setScale(2, BigDecimal.ROUND_HALF_UP));
                salaryCalculateDetailEntity.setPersonalTax(new BigDecimal(salaryCalculateResult.getPersonalTax()).setScale(2, BigDecimal.ROUND_HALF_UP));
                salaryCalculateDetailEntity.setTaxableSalary(new BigDecimal(salaryCalculateResult.getTaxableSalary()).setScale(2, BigDecimal.ROUND_HALF_UP));
                salaryCalculateDetailEntity.setUserId(salaryCalculatePoolModel.userEntityMap.get(salaryCalculateResult.getIdcardNo()).getId());
                salaryCalculateDetailEntity.setCorpName(salaryCalculateResult.getCorpName());
                salaryCalculateDetailEntity.setTaxableWeekSalary(new BigDecimal(salaryCalculateResult.getTaxableWeekSalary()));
                salaryCalculateDetailEntity.setSettlementInterval(customerSalaryCulateCommand.getSettlementInterval());
                salaryCalculateDetailEntity.setIsDeduct(Constants.FALSE);
                //将整行数据转换成JSON存储
                ICModel icModel = salaryCalculatePoolModel.idCardRowMap.get(salaryCalculateResult.getIdcardNo());
                String excelDataJson = null == icModel ? "" : icModel.dataToJson();
                //将计算结果存储
                if (CollectionUtils.isNotEmpty(salaryCalculateResult.getExcelInfo())) {
                    List<Map<String, String>> excelDataList = new Gson().fromJson(excelDataJson, ArrayList.class);
                    excelDataList.addAll(salaryCalculateResult.getExcelInfo());
                    excelDataJson = new Gson().toJson(excelDataList);
                }
                salaryCalculateDetailEntity.setExcelInfo(excelDataJson);
                //新增本次导入数据
                salaryCalculateDetailDao.create(salaryCalculateDetailEntity);
                //同批次同一人 应删除旧数据 并且将后面几个批次更新
                if (null != detailMap.get(salaryCalculateDetailEntity.getUserId())) {
                    SalaryCalculateDetailEntity deleteEntity = detailMap.get(salaryCalculateDetailEntity.getUserId());
                    deleteEntity.setIsDelete(Constants.TRUE);
                    repeatDetailList.add(deleteEntity);
                    //同年-月 人员 后面批次的数据需要更新
                }
                repeatSalaryData(customerSalaryCulateCommand.getYear(), customerSalaryCulateCommand.getMonth(), customerSalaryCulateCommand.getCustomerId()
                        , salaryCalculateDetailEntity.getUserId(), customerSalaryCulateCommand.getWeek(), salaryCalculatePoolModel.salaryCalculateRuleModel);
            }
            salaryCalculateDetailDao.update(repeatDetailList);

            CustomerEntity customerEntity = customerService.getOne(customerSalaryCulateCommand.getCustomerId());

            //5.保存错误信息
            for (ICModel importCheckResult : salaryCalculatePoolModel.importResultModel.getErrDataList()) {
                SalaryErrLogEntity salaryErrLogEntity = new SalaryErrLogEntity();
                salaryErrLogEntity.init();
                salaryErrLogEntity.setCreateUser(currentUser.getId());
                StringBuilder errInfoBuilder = new StringBuilder();
                for (String errInfo : importCheckResult.getErrStringList()) {
                    errInfoBuilder.append("," + errInfo);
                }
                salaryErrLogEntity.setLogInfo(errInfoBuilder.substring(StringUtils.isAnyBlank(errInfoBuilder.toString()) ? 0 : 1));
                salaryErrLogEntity.setDistrictName(null == importCheckResult.getData().get(SalaryExcelMode.KEY_DISTRICT) ? "" : importCheckResult.getData().get(SalaryExcelMode.KEY_DISTRICT).getValue());
                salaryErrLogEntity.setCustomerId(customerEntity.getId());
                salaryErrLogEntity.setCustomerName(customerEntity.getCustomerName());
                salaryErrLogDao.create(salaryErrLogEntity);
            }
            logInfo.append("客户：" + customerEntity.getCustomerName() + "导入了薪资，改薪资批次为" + salaryCalculateEntity.getWeek());
            salaryLogService.successLog(OperateConstants.SALARY_CALCULATE_IMPORT, logInfo, log);
        } catch (Exception e) {
            logInfo.append("客户Id：" + customerSalaryCulateCommand.getCustomerId() + "导入了薪资失败");
            salaryLogService.successLog(OperateConstants.SALARY_CALCULATE_IMPORT, logInfo, log);
        }
    }

    private SalaryCalculateModel repeatSalaryData(Integer year, Integer month, String customerId, String userId, Long week, SalaryCalculateRuleModel salaryCalculateRuleModel){
        //用户当月所有薪资数据 按批次顺序
        List<SalaryCalculateDetailEntity> monthUserDatas = salaryCalculateDetailDao.weekDatas(year, month, customerId, userId);
        //用户批次之前的所有薪资数据
        List<SalaryCalculateDetailEntity> beforeWeeks = new ArrayList<>();
        //整体影响的数据累计， 用于删除之后的台账处理
        SalaryCalculateModel totalModel = new SalaryCalculateModel();
        //BigDecimal lastTaxableSalary = new BigDecimal(0);
        for (SalaryCalculateDetailEntity tempEntity : monthUserDatas) {
            if (tempEntity.getWeek() > week) {
                SalaryCalculateModel salaryCalculateModel = calculateWeekSalaryBaseOnForwardWeekSalaries(beforeWeeks, tempEntity.getTaxableSalary(), salaryCalculateRuleModel);
                if (null != salaryCalculateModel) {
                    SalaryCalculateModel tempSalaryCalculateModel = calculateBumuSalaryToModel(salaryCalculateModel.getTaxableSalary(), salaryCalculateRuleModel);
                    totalModel.setPersonalTax(totalModel.getBrokerage().add(tempEntity.getPersonalTax().subtract(tempSalaryCalculateModel.getPersonalTax())));
                    totalModel.setNetSalary(totalModel.getTaxableSalary().subtract(totalModel.getPersonalTax()));
                    tempEntity.setTaxableSalary(tempEntity.getTaxableSalary());
                    tempEntity.setPersonalTax(tempSalaryCalculateModel.getPersonalTax().subtract(salaryCalculateModel.getPersonalTax()));
                    tempEntity.setNetSalary(tempEntity.getTaxableSalary().subtract(tempEntity.getPersonalTax()));
                    salaryCalculateDetailDao.update(tempEntity);
                }
            } else if (tempEntity.getWeek() ==  week){
                totalModel.setTaxableSalary(tempEntity.getTaxableSalary());
                totalModel.setPersonalTax(tempEntity.getPersonalTax());
                totalModel.setBrokerage(tempEntity.getBrokerage());
                //lastTaxableSalary = tempEntity.getTaxableSalary();
                //continue;
            }
            //lastTaxableSalary = tempEntity.getTaxableSalary();
            beforeWeeks.add(tempEntity);
        }
        return totalModel;
    }

    @Override
    public Pager<ErrLogResult> errLogPage(String condition, Integer pageSize, Integer page) throws Exception {
        Pager<SalaryErrLogEntity> pager = salaryErrLogDao.getPager(condition, pageSize, page);
        Pager<ErrLogResult> result = new Pager<>();
        if (ListUtils.checkNullOrEmpty(pager.getResult())) {
            return result;
        }
        result.setPageSize(pageSize);
        result.setRowCount(pager.getRowCount());
        result.setResult(pager.getResult().stream().map(
                entity -> {
                    ErrLogResult errLogResult = new ErrLogResult();
                    errLogResult.setId(entity.getId());
                    errLogResult.setLogInfo(entity.getLogInfo());
                    errLogResult.setDistrictName(entity.getDistrictName());
                    errLogResult.setCustomerName(entity.getCustomerName());
                    errLogResult.setRemark(entity.getRemark());
                    errLogResult.setCreateTime(entity.getCreateTime());
                    return errLogResult;
                }).collect(Collectors.toList()));
        return result;
    }

    @Override
    public void errLogDelete(ErrLogDeleteCommand errLogDeleteCommand) {
        List<SalaryErrLogEntity> salaryErrLogEntityList = salaryErrLogDao.getList(errLogDeleteCommand.getSalaryIds());
        if (CollectionUtils.isNotEmpty(salaryErrLogEntityList)) {
            for (SalaryErrLogEntity salaryErrLogEntity : salaryErrLogEntityList){
                salaryErrLogEntity.setIsDelete(Constants.TRUE);
            }
            salaryErrLogDao.update(salaryErrLogEntityList);
        }
    }

    @Override
    public void errLogUpdate(ErrLogUpdateCommand errLogUpdateCommand) {
        if (StringUtils.isNotBlank(errLogUpdateCommand.getId())) {
            SalaryErrLogEntity salaryErrLogEntity = salaryErrLogDao.findByIdNotDelete(errLogUpdateCommand.getId());
            if (null != salaryErrLogEntity) {
                salaryErrLogEntity.setRemark(errLogUpdateCommand.getRemark());
                salaryErrLogEntity.setUpdateTime(System.currentTimeMillis());
                salaryErrLogDao.update(salaryErrLogEntity);
            } else {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "无该错误日志对象");
            }
        }
    }

    @Override
    public SalaryUserResult userInfo(String id) {
        SalaryUserResult salaryUserResult = new SalaryUserResult();
        SalaryCalculateDetailEntity salaryCalculateDetailEntity = salaryCalculateDetailDao.findByIdNotDelete(id);
        if (null != salaryCalculateDetailEntity) {
            SalaryUserEntity salaryUserEntity = salaryUserDao.findByIdNotDelete(salaryCalculateDetailEntity.getUserId());
            if (null != salaryUserEntity) {
                salaryUserResult.setName(salaryUserEntity.getName());
                salaryUserResult.setPhoneNo(salaryUserEntity.getPhoneNo());
                salaryUserResult.setIdCardNo(salaryUserEntity.getIdCardNo());
                salaryUserResult.setBankAccount(salaryUserEntity.getBankAccount());
                salaryUserResult.setBankName(salaryUserEntity.getBankName());
                salaryUserResult.setId(salaryUserEntity.getId());
            }
        }
        return salaryUserResult;
    }

    @Override
    public void updateUser(UpdateSalaryUserInfoCommand command) {
        SalaryUserEntity salaryUserEntity = salaryUserDao.findByIdNotDelete(command.getUserId());
        if (null != salaryUserEntity) {
            salaryUserEntity.setName(StringUtils.isAnyBlank(command.getUserName()) ? salaryUserEntity.getName() : command.getUserName().trim());
            salaryUserEntity.setPhoneNo(StringUtils.isAnyBlank(command.getPhoneNo()) ? salaryUserEntity.getPhoneNo() : command.getPhoneNo().trim());
            salaryUserEntity.setIdCardNo(StringUtils.isAnyBlank(command.getIdCardNo()) ? salaryUserEntity.getIdCardNo() : command.getIdCardNo().trim());
            salaryUserEntity.setBankAccount(StringUtils.isAnyBlank(command.getBankAccount()) ? salaryUserEntity.getBankAccount() : command.getBankAccount().trim());
            salaryUserEntity.setBankName(StringUtils.isAnyBlank(command.getBankName()) ? salaryUserEntity.getBankName() : command.getBankName().trim());
        }
    }

    @Override
    public void
    deleteSalarys(SalaryDeleteCommand salaryDeleteCommand) {
        if (null == salaryDeleteCommand.getWeek()) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "删除前必须选择具体的批次");
        }
        StringBuffer logInfo = new StringBuffer();
        CustomerEntity customerEntity = customerService.getOne(salaryDeleteCommand.getCustomerId());
        try {
            List<SalaryCalculateDetailEntity> salaryCalculateDetailEntityList = salaryCalculateDetailDao.findByIds(salaryDeleteCommand.getSalaryIds());
            //List<Long> weeks = new ArrayList<>();
            //薪资计算规则
            SalaryCalculateRuleModel salaryCalculateRuleModel = customerService.getRuleModel(salaryDeleteCommand.getCustomerId());
            //Map<Long, SalaryCalculateModel> weekSalaryMap = new HashMap<>();
            SalaryCalculateModel deleteCalculateModel = new SalaryCalculateModel();
            for (SalaryCalculateDetailEntity salaryCalculateDetailEntity : salaryCalculateDetailEntityList) {
                SalaryCalculateModel salaryCalculateModel = repeatSalaryData(salaryDeleteCommand.getYear(), salaryDeleteCommand.getMonth(), salaryDeleteCommand.getCustomerId()
                        , salaryCalculateDetailEntity.getUserId(), salaryCalculateDetailEntity.getWeek(), salaryCalculateRuleModel);
               /* if (null == weekSalaryMap.get(salaryCalculateDetailEntity.getWeek())) {
                    weekSalaryMap.put(salaryCalculateDetailEntity.getWeek(), salaryCalculateModel);
                    weeks.add(salaryCalculateDetailEntity.getWeek());
                } else {
                    SalaryCalculateModel tmp = weekSalaryMap.get(salaryCalculateDetailEntity.getWeek());
                    tmp.setNetSalary(tmp.getNetSalary().add(salaryCalculateModel.getNetSalary()));
                    tmp.setTaxableSalary(tmp.getTaxableSalary().add(salaryCalculateModel.getTaxableSalary()));
                    tmp.setPersonalTax(tmp.getPersonalTax().add(salaryCalculateModel.getPersonalTax()));
                    tmp.setBrokerage(tmp.getBrokerage().add(salaryCalculateModel.getBrokerage()));
                    weekSalaryMap.put(salaryCalculateDetailEntity.getWeek(), salaryCalculateModel);
                }*/
                deleteCalculateModel.setNetSalary(deleteCalculateModel.getNetSalary().add(salaryCalculateModel.getNetSalary()));
                deleteCalculateModel.setTaxableSalary(deleteCalculateModel.getTaxableSalary().add(salaryCalculateModel.getTaxableSalary()));
                deleteCalculateModel.setPersonalTax(deleteCalculateModel.getPersonalTax().add(salaryCalculateModel.getPersonalTax()));
                deleteCalculateModel.setBrokerage(deleteCalculateModel.getBrokerage().add(salaryCalculateModel.getBrokerage()));
                salaryCalculateDetailEntity.setIsDelete(Constants.TRUE);
                salaryCalculateDetailEntity.setUpdateTime(new Date().getTime());
            }
            /*for (SalaryCalculateDetailEntity salaryCalculateDetailEntity : salaryCalculateDetailEntityList) {
                salaryCalculateDetailEntity.setIsDelete(Constants.TRUE);
            }*/
            salaryCalculateDetailDao.update(salaryCalculateDetailEntityList);

            SalaryCalculateEntity salaryCalculateEntity = salaryCalculateDao.getByWeek(salaryDeleteCommand.getCustomerId(),
                    salaryDeleteCommand.getYear(), salaryDeleteCommand.getMonth(), salaryDeleteCommand.getWeek());
            if (salaryCalculateDetailDao.checkWeekIsEmpty(salaryCalculateEntity.getWeek())) {
                salaryCalculateEntity.setIsDelete(Constants.TRUE);
                salaryCalculateDao.update(salaryCalculateEntity);
            }
           // 将本次所有删除的批次都收集 做台账处理
            /*Map<Long, SalaryCalculateEntity> salaryCalculateEntityMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(weeks)) {
                List<SalaryCalculateEntity> salaryCalculateEntityList = salaryCalculateDao.getListByWeeks(weeks);
                for (SalaryCalculateEntity salaryCalculateEntity : salaryCalculateEntityList) {
                    salaryCalculateEntityMap.put(salaryCalculateEntity.getWeek(), salaryCalculateEntity);
                    if (salaryCalculateDetailDao.checkWeekIsEmpty(salaryCalculateEntity.getWeek())) {
                        salaryCalculateEntity.setIsDelete(Constants.TRUE);
                        salaryCalculateDao.update(salaryCalculateEntity);
                    }
                }
            }*/
            logInfo.append("客户名称：" + customerEntity.getCustomerName()
                    + "删除" + salaryDeleteCommand.getYear() + "年" + salaryDeleteCommand.getMonth() + "月"
                    + salaryDeleteCommand.getWeek() + "批次的薪资记录ID：["
                    + StringUtils.join(salaryDeleteCommand.getSalaryIds(), ",") + "]");
            //生成台账
            logger.info("生成台账");
            //Iterator<Long> i = weekSalaryMap.keySet().iterator();
            BigDecimal transMoney = new BigDecimal(0);
            /*while (i.hasNext()) {
                Long week = i.next();
                SalaryCalculateEntity salaryCalculateEntity = salaryCalculateEntityMap.get(week);*/
                /* 如果是已经确认扣款后，则删除 */
            if (null != salaryCalculateEntity && salaryCalculateEntity.getIsDeduct() == Constants.TRUE) {
                //SalaryCalculateModel salaryCalculateModel = weekSalaryMap.get(week);
                CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity();
                customerAccountEntity.setId(Utils.makeUUID());
                customerAccountEntity.setTransAccountDate(System.currentTimeMillis());
                customerAccountEntity.setTransAccountAmount(deleteCalculateModel.getTaxableSalary().add(
                        null != salaryCalculateRuleModel.getCostBearing() && salaryCalculateRuleModel.getCostBearing() == SalaryEnum.CostBearing.company
                                ? deleteCalculateModel.getBrokerage() : new BigDecimal(0)).toString());
                customerAccountEntity.setWeek(salaryDeleteCommand.getWeek());
                customerAccountEntity.setCustomerId(salaryDeleteCommand.getCustomerId());
                customerAccountService.create(customerAccountEntity, null);
                transMoney = transMoney.add(new BigDecimal(customerAccountEntity.getTransAccountAmount()));
            }
            //}
            logInfo.append("，返还客户金额：" + transMoney.toString());
            salaryLogService.successLog(OperateConstants.SALARY_CALCULATE_DELETE, logInfo, log);
        } catch (Exception e) {
            logInfo.append("客户名称：" + customerEntity.getCustomerName()
                    + "删除" + salaryDeleteCommand.getYear() + "年" + salaryDeleteCommand.getMonth() + "月"
                    + salaryDeleteCommand.getWeek() + "的薪资记录ID：["
                    + StringUtils.join(salaryDeleteCommand.getSalaryIds(), ",") + "]失败");
            salaryLogService.failedLog(OperateConstants.SALARY_CALCULATE_DELETE, logInfo, log);
        }
    }

    @Override
    public void deduct(SalaryServiceCommand salaryServiceCommand) {
        SalaryCalculateEntity salaryCalculateEntity = new SalaryCalculateEntity();
        //找到该批次
        List<SalaryCalculateEntity> salaryCalculateEntityList = salaryCalculateDao.getMonthList(salaryServiceCommand.getCustomerId(), salaryServiceCommand.getYear()
                , salaryServiceCommand.getMonth(), salaryServiceCommand.getWeek());
        if (CollectionUtils.isEmpty(salaryCalculateEntityList)) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_SALARY_CALCULATE_NULL, "无可扣款批次");
        } else if (salaryCalculateEntityList.size() > 1) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_SALARY_CALCULATE_NULL, "请确认扣款批次");
        } else {
            salaryCalculateEntity = salaryCalculateEntityList.get(0);
            if (Constants.TRUE == salaryCalculateEntity.getIsDeduct()) {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_SALARY_HAS_DEDUCT, "该批次已扣款");
            }
        }

        StringBuffer logInfo = new StringBuffer();
        CustomerEntity customerEntity = customerService.getOne(salaryServiceCommand.getCustomerId());
        SalaryCalculateRuleModel salaryCalculateRuleModel = customerService.getRuleModel(salaryServiceCommand.getCustomerId());

        // 获取该批次下所有的薪资数据
        List<SalaryCalculateDetailEntity> salaryCalculateDetailEntityList = salaryCalculateDetailDao.findListByWeek(salaryCalculateEntity.getWeek(), salaryServiceCommand.getCustomerId());
        CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity();
        customerAccountEntity.setWeek(salaryCalculateEntity.getWeek());
        BigDecimal salaryBeforeTax = new BigDecimal(0);
        BigDecimal personalTaxFee = new BigDecimal(0);
        BigDecimal salaryFee = new BigDecimal(0);
        BigDecimal netSalary = new BigDecimal(0);

        for (SalaryCalculateDetailEntity salaryCalculateDetailEntity : salaryCalculateDetailEntityList) {
            salaryBeforeTax = salaryBeforeTax.add(null == salaryCalculateDetailEntity.getTaxableWeekSalary() ? new BigDecimal(0) : salaryCalculateDetailEntity.getTaxableWeekSalary());
            personalTaxFee = personalTaxFee.add(null == salaryCalculateDetailEntity.getPersonalTax() ? new BigDecimal(0) : salaryCalculateDetailEntity.getPersonalTax());
            salaryFee = salaryFee.add(null == salaryCalculateDetailEntity.getBrokerage() ? new BigDecimal(0) : salaryCalculateDetailEntity.getBrokerage());
            netSalary = netSalary.add(null == salaryCalculateDetailEntity.getNetSalary() ? new BigDecimal(0) : salaryCalculateDetailEntity.getNetSalary());
        }
        /*BigDecimal salaryAfterTax = salaryBeforeTax.subtract(personalTaxFee);
        if (salaryCalculateRuleModel.getCostBearing() == SalaryEnum.CostBearing.company) {
            salaryAfterTax = salaryAfterTax.subtract(salaryFee);
        }*/

        customerAccountEntity.setSalaryBeforeTax(salaryBeforeTax.toString());
        customerAccountEntity.setPersonalTaxFee(personalTaxFee.toString());
        customerAccountEntity.setSalaryAfterTax(netSalary.toString());
        customerAccountEntity.setSalaryFee(salaryFee.toString());
        customerAccountEntity.setDealDate(System.currentTimeMillis());
        customerAccountEntity.setCustomerId(salaryServiceCommand.getCustomerId());
        customerAccountEntity.setId(Utils.makeUUID());

        // 验证台账余额
        BigDecimal deductAmount = personalTaxFee.add(netSalary).add(salaryCalculateRuleModel.getCostBearing() == SalaryEnum.CostBearing.company
                && salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.defined ?
                salaryFee : new BigDecimal(0));

        if (deductAmount.compareTo(new BigDecimal(StringUtils.isAnyBlank(customerEntity.getRemainAccount()) ? "0" : customerEntity.getRemainAccount())) == 1) {
            throw new AryaServiceException(ErrorCode.CODE_CUSTOMER_REMAIN_ACCOUNT_NOT_ENOUGH, "客户余额不足，扣款失败");
        }

        try {
            // 新增新的台账
            List<CustomerAccountEntity> customerAccountEntityList = customerAccountService.salaryAccountList(salaryServiceCommand.getCustomerId(), salaryCalculateEntity.getWeek());
            customerAccountService.create(customerAccountEntity, customerAccountEntityList);

            salaryCalculateEntity.setIsDeduct(Constants.TRUE);
            salaryCalculateDao.update(salaryCalculateEntity);
            logInfo.append("客户名称：" + customerEntity.getCustomerName() + "确认扣款批次："
                    +  salaryServiceCommand.getWeek()
                    +  "，扣款金额合计为"
                    + new BigDecimal(customerAccountEntity.getSalaryBeforeTax())
                    .add(new BigDecimal(salaryCalculateRuleModel.getRuleType() == SalaryEnum.RuleType.defined
                            && salaryCalculateRuleModel.getCostBearing() == SalaryEnum.CostBearing.company
                            ? customerAccountEntity.getPersonalTaxFee() : "0")).toString());
            salaryLogService.successLog(OperateConstants.SALARY_CALCULATE_DEDUCT, logInfo, log);
            //将所有的薪资明细设置为已确认扣款
            for (SalaryCalculateDetailEntity salaryCalculateDetailEntity : salaryCalculateDetailEntityList) {
                salaryCalculateDetailEntity.setIsDeduct(Constants.TRUE);
            }
            salaryCalculateDetailDao.update(salaryCalculateDetailEntityList);
        } catch (Exception e) {
            logInfo.append("客户名称：" + customerEntity.getCustomerName()
                    + "确认扣款批次：" + salaryServiceCommand.getWeek()
                    + "，扣款失败");
            salaryLogService.failedLog(OperateConstants.SALARY_CALCULATE_DEDUCT, logInfo, log);
        }
    }

    @Override
    public List<Long> getWeeks(String customerId, Integer year, Integer month) {
        List<Long> weeks = new ArrayList<>();
        List<SalaryCalculateEntity> salaryCalculateEntityList = salaryCalculateDao.getMonthList(customerId, year, month, null);
        for (SalaryCalculateEntity salaryCalculateEntity : salaryCalculateEntityList) {
            weeks.add(salaryCalculateEntity.getWeek());
        }
        return weeks;
    }

    @Override
    public void refreshDetailData(HttpServletRequest request) {
        List<SalaryCalculateDetailEntity> detailEntityList = salaryCalculateDetailDao.findAll();
        //1.收集所有已经确认扣款的薪资批次
        logger.info("收集所有已经确认扣款的薪资批次");
        List<SalaryCalculateEntity> salaryCalculateEntityList = salaryCalculateDao.findDeductList();
        Set<Long> deductCalculateIdSet = new HashSet<>();
        salaryCalculateEntityList.stream().forEach(entity -> {
            deductCalculateIdSet.add(entity.getWeek());
        });
        //2.将所有的数据的是否确认扣款设置为0，将已经确认扣款的设置为1
        logger.info("将所有的数据的是否确认扣款设置为0，将已经确认扣款的设置为1");
        for (SalaryCalculateDetailEntity entity : detailEntityList) {
            entity.setIsDeduct(deductCalculateIdSet.contains(entity.getWeek()) ? Constants.TRUE : Constants.FALSE);
        }
        salaryCalculateDetailDao.update(detailEntityList);
        //3.更新所有日志里删除的数据
        List<SysJournalDocument> sysJournalDocuments = salaryLogService.findLogs(OperateConstants.SALARY_CALCULATE_DELETE);
        for (SysJournalDocument sysJournalDocument : sysJournalDocuments) {
            if (sysJournalDocument.getOpSuccess() == Constants.FALSE || null == sysJournalDocument.getExtInfo() ){
                continue;
            }
            String extInfo = String.valueOf(sysJournalDocument.getExtInfo().get("msg"));
            if (StringUtils.isAnyBlank(extInfo)) {
                continue;
            }
            logger.info("日志记录-》" + extInfo);
            if (extInfo.indexOf("[") != -1 && extInfo.indexOf("]") != -1) {
                String ids = extInfo.substring(extInfo.indexOf("[") + 1, extInfo.indexOf("]"));
                logger.info("需要功能新的ID为:" + ids);
                for (String id : ids.split(",")){
                    SalaryCalculateDetailEntity detailEntity = salaryCalculateDetailDao.findByUniqueParam("id", id);
                    if (null == detailEntity) {
                        logger.info("id为:" + id + "的薪资计算明细数据不存在");
                        continue;
                    }
                    logger.info("更新id为:" + id + ",的updateTime为" + String.valueOf(sysJournalDocument.getCreateTime()));
                    detailEntity.setUpdateTime(sysJournalDocument.getCreateTime().getTime());
                    salaryCalculateDetailDao.update(detailEntity);
                }
            }
        }
        salaryLogService.successLog(OperateConstants.SALARY_DETAIL_DATA_REFRESH, new StringBuffer("更新薪资明细"), log);
    }
}
