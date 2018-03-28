package com.bumu.bran.admin.salary.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaSalaryDao;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.SysLogDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.salary.helper.SalarySaveHelper;
import com.bumu.bran.admin.salary.result.ImportSalaryConfirmResult;
import com.bumu.bran.admin.salary.result.SalaryResult;
import com.bumu.bran.admin.salary.service.SalaryService;
import com.bumu.bran.admin.salary.vo.BranCorpSalaryCollection;
import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.admin.system.command.IdVersion;
import com.bumu.bran.common.Constants;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.bran.esalary.command.BranSalaryQuery;
import com.bumu.bran.esalary.command.SalaryCommand;
import com.bumu.bran.esalary.model.dao.NewSalaryDao;
import com.bumu.bran.esalary.model.dao.SalaryNotImportDao;
import com.bumu.bran.esalary.model.entity.SalaryDetailEntity;
import com.bumu.bran.esalary.model.entity.SalaryEntity;
import com.bumu.bran.esalary.model.entity.SalaryNotImportEntity;
import com.bumu.bran.esalary.result.AppWeixinSalaryDetailImportResult;
import com.bumu.bran.esalary.result.NewSalaryResult;
import com.bumu.bran.esalary.result.VerifySalaryResult;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.entity.BranCorporationEntity;
import com.bumu.bran.model.entity.BranOpLogEntity;
import com.bumu.common.result.Pager;
import com.bumu.common.service.BaseFileService;
import com.bumu.common.service.ConfigService;
import com.bumu.common.util.ListUtils;
import com.bumu.common.util.TxVersionUtil;
import com.bumu.common.util.ValidateUtils;
import com.bumu.engine.excelimport.ImportService;
import com.bumu.engine.excelimport.model.ICConfigModel;
import com.bumu.engine.excelimport.model.ICModel;
import com.bumu.engine.excelimport.model.ICResult;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.ExceptionModel;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * majun
 */
@Service
public class SalaryServiceImpl implements SalaryService {

    private Logger logger = LoggerFactory.getLogger(SalaryServiceImpl.class);

    @Autowired
    private BranAdminConfigService branAdminConfigService;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Autowired
    private AryaSalaryDao salaryDao;

    @Autowired
    private CorporationDao corporationDao;

    @Autowired
    private AryaUserDao aryaUserDao;

    @Autowired
    private BranOpLogDao branOpLogDao;

    @Autowired
    private NewSalaryDao newSalaryDao;

//    @Autowired
//    private SalaryConfigDao salaryConfigDao;

    @Autowired
    private ImportService importService;

    @Autowired
    private BaseFileService fileService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private SalaryNotImportDao salaryNotImportDao;

    @Override
    public void download(HttpServletResponse response) {
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        CorporationEntity aryaCorp = corporationDao.findByBranId(branCorpId);
        // 如果没有找到公司,抛异常
        Assert.notNull(aryaCorp, ErrorCode.CODE_CORPORATION_NOT_EXIST);
        // 根据配置文件,锁定哪个模板
        String templateName = null;
        File in = null;
        logger.info("aryaCorp.getId()" + aryaCorp.getId());
        templateName = "薪资默认模板.xls";
        String fileName = "bran_admin_salary_default.xls";
        in = new File(branAdminConfigService.getExcelTemplateLocation() + File.separator +
                fileName);

        if (!in.exists()) {
            logger.error("in... :" + in.getPath());
            throw new AryaServiceException(ErrorCode.CODE_ORDER_TEMPLATE_IS_NOT_EXIST);
        }
        SysUtils.writeResponseStream(in, response, Constants.HPPT_TYPE_EXCEL,
                "Content-Disposition",
                "attachment; filename=" + SysUtils.parseEncoding(templateName, CharEncoding.UTF_8)
        );
    }

    @Override
    public Map<String, Object> verify(MultipartFile file, String branCorpId, String token) throws Exception {
        // 判断文件
        if (file == null) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        // 验证公司是否存在
        BranCorporationEntity corp = branCorporationDao.findCorpById(branCorpId);
        if (corp == null) {
            throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
        }
        // 查询aryaCorp
        CorporationEntity aryaCorp = corporationDao.findByBranId(corp.getId());
        if (aryaCorp == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_NOT_EXIST);
        }
        Map<String, Object> map = new HashMap<>();

//		ImportHandler<List<VerifySalaryResult>> handler = (DefaultVerifyProcessor) SysUtils.getBean(DefaultVerifyProcessor.class);
        // 根据公司获得对应的processor

        // 把文件存入临时文件
        String fileTypeStr = SysUtils.getFileSuffixStr(file.getOriginalFilename());

        String fileId = fileService.tempSaveFile(file, 0, fileTypeStr);


        ICConfigModel icConfigModel = new ICConfigModel();
        icConfigModel.sourceFile = new File(fileService.getTempFileUrl(fileId, 0, fileTypeStr));
        icConfigModel.setColMergerData(true);
        icConfigModel.setNumberScales(-1);
        icConfigModel.setRoundUpType(BigDecimal.ROUND_UP);
        BranCorpSalaryCollection collection = new BranCorpSalaryCollection(
                configService.getConfigByKey("bran.admin.salary.parser.key.name").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.id_card_no").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.tel").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.year").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.month").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.net_salary").trim()
        );
        icConfigModel.setImportRegList(collection.geteSalaryTitleCheckRule());
        ICResult importResultModel = importService.importExcel(icConfigModel);


        // 月份 年份一定要相同
        Set<String> yearMonthUniqueSet = new HashSet<>();
        // 手机号 身份证号 不能重复
        Set<String> phoneAndIdCardNoUniqueSet = new HashSet<>();

        // 成功
        List<VerifySalaryResult> list = new ArrayList<>();
        int sucessNum = 0;
        int failNum = 0;

        if (importResultModel.getExceptionModel() != null) {
            if ("7303".equals(importResultModel.getExceptionModel().getErrCode())) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, importResultModel.getExceptionModel().getTitle() + "列重复,请检查模板重新导入");
            }
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, importResultModel.getExceptionModel().getExceptionMessage());

        }

        if (!ListUtils.checkNullOrEmpty(importResultModel.getSuccessDataList())) {
            for (int i = 0; i < importResultModel.getSuccessDataList().size(); i++) {
                ICModel icModel = importResultModel.getSuccessDataList().get(i);
                Map<String, ICModel.ImportData> dataMap = icModel.getData();
                VerifySalaryResult verifySalaryResult = new VerifySalaryResult();
                verifySalaryResult.setName(dataMap.get("name").getValue());
                verifySalaryResult.setIdCard(dataMap.get("idCardNo").getValue());
                verifySalaryResult.setTel(dataMap.get("tel").getValue());
                verifySalaryResult.setYear(dataMap.get("year").getValue());
                verifySalaryResult.setMonth(dataMap.get("month").getValue());
                verifySalaryResult.setNetSalary(dataMap.get("netSalary").getValue());

                BranSalaryQuery branSalaryQuery = new BranSalaryQuery();
                branSalaryQuery.setMonth(verifySalaryResult.getMonth());
                branSalaryQuery.setYear(verifySalaryResult.getYear());
                branSalaryQuery.setIdcardNo(verifySalaryResult.getIdCard());
                branSalaryQuery.setPhoneNo(verifySalaryResult.getTel());
                branSalaryQuery.setRealName(verifySalaryResult.getName());
                branSalaryQuery.setIsPublished(0);
                branSalaryQuery.setAryaCorpId(aryaCorp.getId());

                // 年份 月份一定要一样
                String yearAndMonth = branSalaryQuery.getYear() + branSalaryQuery.getMonth() + "";
                if (i == 0) {
                    yearMonthUniqueSet.add(yearAndMonth);
                } else {
                    if (!yearMonthUniqueSet.contains(yearAndMonth)) {
                        verifySalaryResult.setFlag(1);
                        verifySalaryResult.setError("同一张表格中,即同一次上传,月份、年份一定要一样");
                        list.add(verifySalaryResult);
                        failNum++;
                        continue;
                    }
                }

                // 查询是否已经发布过

                // 判断薪资在未发布列表中是否已经存在
                SalaryEntity noRelease = newSalaryDao.findByBranSalaryQuery(branSalaryQuery);
                if (noRelease != null) {
                    verifySalaryResult.setFlag(1);
                    verifySalaryResult.setError(
                            String.format(
                                    "年份: %s 月份: %s 姓名: %s 手机号: %s 身份证号: %s 薪资在未发布列表中已经存在",
                                    verifySalaryResult.getYear(),
                                    verifySalaryResult.getMonth(),
                                    verifySalaryResult.getName(),
                                    verifySalaryResult.getTel(),
                                    verifySalaryResult.getIdCard())
                    );
                    list.add(verifySalaryResult);
                    failNum++;
                    continue;
                }
                branSalaryQuery.setIsPublished(1);


                // 判断薪资在已发布列表中已存在
                SalaryEntity release = newSalaryDao.findByBranSalaryQuery(branSalaryQuery);
                if (release != null) {
                    verifySalaryResult.setRelease(1);
                    verifySalaryResult.setTip(
                            String.format(
                                    "年份: %s 月份: %s 姓名: %s 手机号: %s 身份证号: %s 薪资在已发布列表中已经存在",
                                    verifySalaryResult.getYear(),
                                    verifySalaryResult.getMonth(),
                                    verifySalaryResult.getName(),
                                    verifySalaryResult.getTel(),
                                    verifySalaryResult.getIdCard()
                            )
                    );
                }
                sucessNum++;
                list.add(verifySalaryResult);
            }
        }

        // 失败
        if (!ListUtils.checkNullOrEmpty(importResultModel.getErrDataList())) {
            for (ICModel icModel : importResultModel.getErrDataList()) {
                Map<String, ICModel.ImportData> dataMap = icModel.getData();
                VerifySalaryResult verifySalaryResult = new VerifySalaryResult();
                verifySalaryResult.setName(dataMap.get("name").getValue());
                verifySalaryResult.setIdCard(dataMap.get("idCardNo").getValue());
                verifySalaryResult.setTel(dataMap.get("tel").getValue());

                if (ValidateUtils.isNumber(dataMap.get("year").getValue())) {
                    verifySalaryResult.setYear(Double.valueOf(dataMap.get("year").getValue()).intValue());
                }

                if (ValidateUtils.isNumber(dataMap.get("month").getValue())) {
                    verifySalaryResult.setMonth(Double.valueOf(dataMap.get("month").getValue()).intValue());
                }
                verifySalaryResult.setNetSalary(dataMap.get("netSalary").getValue());
                verifySalaryResult.setFlag(1);
                verifySalaryResult.setError(icModel.getErrStringList().toString());
                list.add(verifySalaryResult);
                failNum++;
            }

        }

        map.put("fileId", fileId);
        map.put("info", list);
        map.put("token", token);
        map.put("fileTypeStr", fileTypeStr);
        map.put("successNum", sucessNum);
        map.put("failNum", failNum);
        return map;
    }

    @Override
    public ImportSalaryConfirmResult confirm(SalaryCommand salaryCommand, BindingResult bindingResult) throws Exception {
        ImportSalaryConfirmResult importSalaryConfirmResult = new ImportSalaryConfirmResult();
        com.bumu.exception.Assert.paramsNotError(bindingResult, new ExceptionModel());

        // 验证公司是否存在
        BranCorporationEntity corp = branCorporationDao.findCorpById(salaryCommand.getBranCorpId());
        Assert.notNull(corp, ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
        // 查询aryaCorp
        CorporationEntity aryaCorp = corporationDao.findByBranId(corp.getId());
        Assert.notNull(aryaCorp, ErrorCode.CODE_CORPORATION_NOT_EXIST);

//        File file = new File(branAdminConfigService.getEmpExcelImportPath(salaryCommand.getBranCorpId(),
//                salaryCommand.getFile_id(), salaryCommand.getFileTypeStr()));
//        ImportHandler<SalaryEntity> handler = (DefaultSaveProcessor) SysUtils.getBean(DefaultSaveProcessor.class);
//        List<SalaryEntity> salaries = handler.fileImport(file);
//        if (salaries != null || !salaries.isEmpty()) {
//            newSalaryDao.create(salaries);
//        }

        ICConfigModel icConfigModel = new ICConfigModel();
        icConfigModel.sourceFile = new File(fileService.getTempFileUrl(salaryCommand.getFile_id(), 0, salaryCommand.getFileTypeStr()));
        icConfigModel.setColMergerData(true);
        icConfigModel.setNumberScales(-1);
        icConfigModel.setRoundUpType(BigDecimal.ROUND_UP);
        BranCorpSalaryCollection collection = new BranCorpSalaryCollection(
                configService.getConfigByKey("bran.admin.salary.parser.key.name").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.id_card_no").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.tel").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.year").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.month").trim(),
                configService.getConfigByKey("bran.admin.salary.parser.key.net_salary").trim()
        );
        icConfigModel.setImportRegList(collection.geteSalaryTitleCheckRule());
        ICResult importResultModel = importService.importExcel(icConfigModel);


        // 月份 年份一定要相同
        Set<String> yearMonthUniqueSet = new HashSet<>();
        // 手机号 身份证号 不能重复
        Set<String> phoneAndIdCardNoUniqueSet = new HashSet<>();


        // 成功
        List<SalaryEntity> salaryEntities = new ArrayList<>();

        if (!ListUtils.checkNullOrEmpty(importResultModel.getSuccessDataList())) {
            for (int i = 0; i < importResultModel.getSuccessDataList().size(); i++) {
                ICModel icModel = importResultModel.getSuccessDataList().get(i);
                Map<String, ICModel.ImportData> dataMap = icModel.getData();

                BranSalaryQuery branSalaryQuery = new BranSalaryQuery();
                branSalaryQuery.setMonth(Double.valueOf(dataMap.get("month").getValue()).intValue());
                branSalaryQuery.setYear(Double.valueOf(dataMap.get("year").getValue()).intValue());
                branSalaryQuery.setIdcardNo(dataMap.get("idCardNo").getValue());
                branSalaryQuery.setPhoneNo(dataMap.get("tel").getValue());
                branSalaryQuery.setRealName(dataMap.get("name").getValue());
                branSalaryQuery.setIsPublished(0);
                branSalaryQuery.setAryaCorpId(aryaCorp.getId());

                // 年份 月份一定要一样
                String yearAndMonth = branSalaryQuery.getYear() + branSalaryQuery.getMonth() + "";
                if (i == 0) {
                    yearMonthUniqueSet.add(yearAndMonth);
                } else {
                    if (!yearMonthUniqueSet.contains(yearAndMonth)) {
                        SalaryNotImportEntity importEntity = new SalaryNotImportEntity();
                        importEntity.setId(Utils.makeUUID());
                        importEntity.setImportTime(System.currentTimeMillis());

                        if (ValidateUtils.isNumber(dataMap.get("month").getValue())) {
                            importEntity.setMonth(Double.valueOf(dataMap.get("month").getValue()).intValue());
                        }
                        if (ValidateUtils.isNumber(dataMap.get("year").getValue())) {
                            importEntity.setYear(Double.valueOf(dataMap.get("year").getValue()).intValue());
                        }
                        importEntity.setBranCorp(corp);
                        importEntity.setName(dataMap.get("name").getValue());
                        importEntity.setIdCardNo(dataMap.get("idCardNo").getValue());
                        importEntity.setPhoneNo(dataMap.get("tel").getValue());
                        importEntity.setCreateTime(System.currentTimeMillis());
                        importEntity.setCreateUser(salaryCommand.getUserId());
                        importEntity.setFailMsg("同一张表格中,即同一次上传,月份、年份一定要一样");
                        salaryNotImportDao.persist(importEntity);
                        continue;
                    }
                }


                // 身份证号+手机号不能重复
                String phoneAndIdCardNo = branSalaryQuery.getPhoneNo() + branSalaryQuery.getIdcardNo();
                if (i == 0) {
                    phoneAndIdCardNoUniqueSet.add(phoneAndIdCardNo);
                } else {
                    if (phoneAndIdCardNoUniqueSet.contains(phoneAndIdCardNo)) {
                        SalaryNotImportEntity importEntity = new SalaryNotImportEntity();
                        importEntity.setId(Utils.makeUUID());
                        importEntity.setImportTime(System.currentTimeMillis());

                        if (ValidateUtils.isNumber(dataMap.get("month").getValue())) {
                            importEntity.setMonth(Double.valueOf(dataMap.get("month").getValue()).intValue());
                        }
                        if (ValidateUtils.isNumber(dataMap.get("year").getValue())) {
                            importEntity.setYear(Double.valueOf(dataMap.get("year").getValue()).intValue());
                        }
                        importEntity.setBranCorp(corp);
                        importEntity.setName(dataMap.get("name").getValue());
                        importEntity.setIdCardNo(dataMap.get("idCardNo").getValue());
                        importEntity.setPhoneNo(dataMap.get("tel").getValue());
                        importEntity.setCreateTime(System.currentTimeMillis());
                        importEntity.setCreateUser(salaryCommand.getUserId());
                        importEntity.setFailMsg("同一张表格中,即同一次上传,身份证、手机号不能重复");
                        salaryNotImportDao.persist(importEntity);
                        continue;
                    }
                }


                // 判断薪资在未发布列表中是否已经存在

                SalaryEntity noRelease = newSalaryDao.findByBranSalaryQuery(branSalaryQuery);
                if (noRelease != null) {
                    SalaryNotImportEntity importEntity = new SalaryNotImportEntity();
                    importEntity.setId(Utils.makeUUID());
                    importEntity.setImportTime(System.currentTimeMillis());

                    if (ValidateUtils.isNumber(dataMap.get("month").getValue())) {
                        importEntity.setMonth(Double.valueOf(dataMap.get("month").getValue()).intValue());
                    }
                    if (ValidateUtils.isNumber(dataMap.get("year").getValue())) {
                        importEntity.setYear(Double.valueOf(dataMap.get("year").getValue()).intValue());
                    }
                    importEntity.setBranCorp(corp);
                    importEntity.setName(dataMap.get("name").getValue());
                    importEntity.setIdCardNo(dataMap.get("idCardNo").getValue());
                    importEntity.setPhoneNo(dataMap.get("tel").getValue());
                    importEntity.setCreateTime(System.currentTimeMillis());
                    importEntity.setCreateUser(salaryCommand.getUserId());
                    importEntity.setFailMsg(String.format(
                            "年份: %s 月份: %s 姓名: %s 手机号: %s 身份证号: %s 薪资在未发布列表中已经存在",
                            importEntity.getYear(),
                            importEntity.getMonth(),
                            importEntity.getName(),
                            importEntity.getPhoneNo(),
                            importEntity.getIdCardNo()));
                    salaryNotImportDao.persist(importEntity);
                    continue;
                }


                SalaryEntity salaryEntity = new SalaryEntity();
                salaryEntity.setId(Utils.makeUUID());
                salaryEntity.setYear(Double.valueOf(dataMap.get("year").getValue()).intValue());
                salaryEntity.setMonth(Double.valueOf(dataMap.get("month").getValue()).intValue());
                salaryEntity.setCreateUser(salaryCommand.getAryaCorpId());
                salaryEntity.setCreateTime(System.currentTimeMillis());
                salaryEntity.setRealName(dataMap.get("name").getValue());
                salaryEntity.setPhoneNo(dataMap.get("tel").getValue());
                salaryEntity.setIdcardNo(dataMap.get("idCardNo").getValue());
                salaryEntity.setAryaCorp(aryaCorp);
                salaryEntity.setIndex(icModel.getRow());
                salaryEntity.setNetSalary(new BigDecimal(dataMap.get("netSalary").getValue()));
                // 设置detail
//                resultList.add(salaryEntity);
                List<AppWeixinSalaryDetailImportResult> importDataList = SysUtils.jsonTo(dataMap.get("extra").getValue(), new TypeReference<List<AppWeixinSalaryDetailImportResult>>() {
                });
                Set<SalaryDetailEntity> detailEntities = new HashSet<>();

                if (!ListUtils.checkNullOrEmpty(importDataList)) {
                    for (AppWeixinSalaryDetailImportResult detail : importDataList) {
                        SalaryDetailEntity salaryDetailEntity = new SalaryDetailEntity();
                        salaryDetailEntity.setId(Utils.makeUUID());
                        salaryDetailEntity.setSalaryId(salaryEntity.getId());
                        if (detail.getTitle().length() > 32) {
                            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, detail.getTitle() + "标题超过32位");
                        }
                        if (detail.getValue().length() > 32) {
                            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, detail.getValue() + "值过长超过32位");
                        }
                        salaryDetailEntity.setName(detail.getTitle());
                        salaryDetailEntity.setValue(detail.getValue());
                        salaryDetailEntity.setDetailIndex(Integer.valueOf(detail.getKey()));
                        detailEntities.add(salaryDetailEntity);
                    }
                }
                salaryEntity.setSalaryDetails(detailEntities);
                newSalaryDao.persist(salaryEntity);
                salaryEntities.add(salaryEntity);
            }
        }

        // 失败
        if (!ListUtils.checkNullOrEmpty(importResultModel.getErrDataList())) {
            for (ICModel icModel : importResultModel.getErrDataList()) {
                Map<String, ICModel.ImportData> dataMap = icModel.getData();

                SalaryNotImportEntity importEntity = new SalaryNotImportEntity();
                importEntity.setId(Utils.makeUUID());
                importEntity.setImportTime(System.currentTimeMillis());
                importEntity.setFailMsg(icModel.getErrStringList().toString());
                if (ValidateUtils.isNumber(dataMap.get("month").getValue())) {
                    importEntity.setMonth(Double.valueOf(dataMap.get("month").getValue()).intValue());
                }
                if (ValidateUtils.isNumber(dataMap.get("year").getValue())) {
                    importEntity.setYear(Double.valueOf(dataMap.get("year").getValue()).intValue());
                }
                importEntity.setBranCorp(corp);
                importEntity.setName(dataMap.get("name").getValue());
                importEntity.setIdCardNo(dataMap.get("idCardNo").getValue());
                importEntity.setPhoneNo(dataMap.get("tel").getValue());
                importEntity.setCreateTime(System.currentTimeMillis());
                importEntity.setCreateUser(salaryCommand.getUserId());
                salaryNotImportDao.persist(importEntity);
//                list.add(verifySalaryResult);
            }

        }


//			本次成功导入0条，有1条错误，失败的可在"未导入薪资条"中查看！
        StringBuilder sb = new StringBuilder();
        sb.append("本次薪资成功导入");
        sb.append(salaryCommand.getSuccessNum());
        sb.append("条,有");
        sb.append(salaryCommand.getFailNum());
        sb.append("条错误.");

        logger.debug("导入薪资总数: " + salaryEntities.size());
        importSalaryConfirmResult.setTotal(salaryEntities.size());
        importSalaryConfirmResult.setFlag(0);

        SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
        sysLogExtInfo.setMsg(sb.toString());
        branOpLogDao.success(BranOpLogEntity.OP_MODULE_SALARY,
                BranOpLogEntity.OP_TYPE_IMPORT, salaryCommand.getUserId(), sysLogExtInfo);
        return importSalaryConfirmResult;

    }

    @Override
    public SalaryResult get(SalaryCommand salaryCommand) {
        List<NewSalaryResult> rows = new ArrayList<>();

        SalaryResult salaryResult = new SalaryResult();
        Session session = SecurityUtils.getSubject().getSession();
        String branCorpId = session.getAttribute("bran_corp_id").toString();
        CorporationEntity corporationEntity = corporationDao.findByBranId(branCorpId);
        if (corporationEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_CORP_NOT_FOUND);
        }
        logger.debug("bran_corp_id: " + branCorpId);
        salaryCommand.setAryaCorpId(corporationEntity.getId());

        // 判断企业
        if (StringUtils.isBlank(salaryCommand.getAryaCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        Integer year = null;
        Integer month = null;
        if (salaryCommand.getMonth() != null) {
            Map<String, Integer> yearMonth = SalarySaveHelper.getYearMonthByTime(salaryCommand.getMonth());
            year = yearMonth.get("year");
            month = yearMonth.get("month");
            salaryCommand.setQueryMonth(month);
            salaryCommand.setYear(year);
        }

        Pager<SalaryEntity> pager = newSalaryDao.findBySalaryCommandPagination(salaryCommand);

        for (SalaryEntity sal : pager.getResult()) {
            NewSalaryResult newSalaryResult = new NewSalaryResult();
            newSalaryResult.setId(sal.getId());
            newSalaryResult.setVersion(sal.getTxVersion());
//            if (sal.getAryaUser() != null) {
//                newSalaryResult.setName(sal.getAryaUser().getRealName());
//                newSalaryResult.setIdCard(sal.getAryaUser().getIdcardNo());
//                newSalaryResult.setTel(sal.getAryaUser().getPhoneForTruncateTimestamp());
//            }

            newSalaryResult.setName(sal.getRealName());
            newSalaryResult.setIdCard(sal.getIdcardNo());
            newSalaryResult.setTel(sal.getPhoneNo());
            newSalaryResult.setMonth(sal.getMonth());
            newSalaryResult.setYear(sal.getYear());
            newSalaryResult.setWage(sal.getWage());
            newSalaryResult.setTaxableSalary(sal.getTaxableSalary());
            newSalaryResult.setGrossSalary(sal.getGrossSalary());
            newSalaryResult.setNetSalary(sal.getNetSalary());
            newSalaryResult.setPersonalTax(sal.getPersonalTax());
            newSalaryResult.setRelease(sal.getIsPublished());

            // 判断在已发布列表中是否已经存在
//            if (sal.getAryaUser() != null) {
//                List<SalaryEntity> release = newSalaryDao.findRelease(corporationEntity.getId(),
//                        sal.getAryaUser().getId(), newSalaryResult.getYear(), newSalaryResult.getMonth());
//                if (release != null && !release.isEmpty()) {
//                    newSalaryResult.setRelease(1);
//                }
//            }


            rows.add(newSalaryResult);
        }

        salaryResult.setTotalPage(pager.getPage());
        salaryResult.setTotalRows(pager.getRowCount());
        salaryResult.setRows(rows);
        return salaryResult;
    }

    @Override
    public NewSalaryResult detail(String id) {
        NewSalaryResult newSalaryResult = new NewSalaryResult();

        if (StringUtils.isBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        SalaryEntity sal = newSalaryDao.findById(id);
        newSalaryResult.setId(sal.getId());
//        if (sal.getAryaUser() != null) {
//            newSalaryResult.setName(sal.getAryaUser().getRealName());
//            newSalaryResult.setIdCard(sal.getAryaUser().getIdcardNo());
//            newSalaryResult.setTel(sal.getAryaUser().getPhoneForTruncateTimestamp());
//        }
        newSalaryResult.setName(sal.getRealName());
        newSalaryResult.setTel(sal.getPhoneNo());
        newSalaryResult.setIdCard(sal.getIdcardNo());
        newSalaryResult.setMonth(sal.getMonth());
        newSalaryResult.setYear(sal.getYear());
        newSalaryResult.setWage(sal.getWage());
        newSalaryResult.setTaxableSalary(sal.getTaxableSalary());
        newSalaryResult.setGrossSalary(sal.getGrossSalary());
        newSalaryResult.setNetSalary(sal.getNetSalary());
        newSalaryResult.setPersonalTax(sal.getPersonalTax());
        newSalaryResult.setDetail(sal.getSalaryDetails());
        return newSalaryResult;
    }

    @Override
    public void delete(CorpModel command) {
        List<SalaryEntity> dels = new ArrayList<>();

        // 判断参数
        if (command.getIds() == null || command.getIds().length <= 0) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("删除了");

        for (IdVersion idVersion : command.getIds()) {
            // 根据id 查询
            SalaryEntity salaryEntity = newSalaryDao.findById(idVersion.getId());
            if (salaryEntity == null) {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_QUERY_ERROR);
            }
            // 判断version
            TxVersionUtil.compireVersion(idVersion.getVersion(), salaryEntity.getTxVersion());
//            AryaUserEntity aryaUserEntity = salaryEntity.getAryaUser();
            sb.append(" " + salaryEntity.getRealName() + "的" + salaryEntity.getYear() + "年" + salaryEntity.getMonth() + "月" + "的薪资 ");
            dels.add(salaryEntity);
            logger.debug("id: " + salaryEntity.getId());
        }

        newSalaryDao.delete(dels);

        SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
        sysLogExtInfo.setMsg(sb.toString());
        branOpLogDao.success(BranOpLogEntity.OP_MODULE_SALARY,
                BranOpLogEntity.OP_TYPE_DELETE, command.getOperateUserId(), sysLogExtInfo);

    }

    @Override
    public CorpModel release(CorpModel command) {
        CorpModel corpModel = new CorpModel();
        List<IdVersion> idVersions = new ArrayList<>();
        List<SalaryEntity> releases = new ArrayList<>();
        // 薪资发布之后用户推送的ids
        List<String> aryaUserIds = new ArrayList<>();
        // 判断参数
        if (command.getIds() == null || command.getIds().length <= 0) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        // 根据公司id 查询公司
        if (StringUtils.isBlank(command.getBranCorpId())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        CorporationEntity corp = corporationDao.findByBranId(command.getBranCorpId());

        StringBuilder sb = new StringBuilder();

        sb.append("发布了: ");

        for (IdVersion idVersion : command.getIds()) {
            // 根据id 查询
            logger.info("idVersion.getId: " + idVersion.getId());
            SalaryEntity salaryEntity = newSalaryDao.findById(idVersion.getId());
            if (salaryEntity == null) {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_QUERY_ERROR);
            }
            // 通过手机号查询用户(设置推送id)
            AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(salaryEntity.getPhoneNo());
            if (aryaUserEntity != null) {
                logger.info("aryaUserEntity id: " + aryaUserEntity.getId());
                aryaUserIds.add(aryaUserEntity.getId());
            }

//            AryaUserEntity aryaUserEntity = salaryEntity.getAryaUser();


            sb.append(" " + salaryEntity.getRealName() + "的" + salaryEntity.getYear() + "年" + salaryEntity.getMonth() + "月的薪资 ");

            // 判断version
            TxVersionUtil.compireVersion(idVersion.getVersion(), salaryEntity.getTxVersion());

            logger.info("当前id: " + salaryEntity.getId());
            // 判断该id是否存在已发布的薪资
            BranSalaryQuery branSalaryQuery = new BranSalaryQuery();
            branSalaryQuery.setPhoneNo(salaryEntity.getPhoneNo());
            branSalaryQuery.setIsPublished(1);
            branSalaryQuery.setYear(salaryEntity.getYear());
            branSalaryQuery.setMonth(salaryEntity.getMonth());
            branSalaryQuery.setAryaCorpId(corp.getId());
            logger.info("查询当前id 是否存在已发布的薪资");
            SalaryEntity del = newSalaryDao.findByBranSalaryQuery(branSalaryQuery);

            // 删除已发布的薪资
//            if (dels != null || !dels.isEmpty()) {
//                List<SalaryEntity> fifter = new ArrayList<>();
//                dels.forEach(del -> {
//                            if (!ids.containsKey(del.getId())) {
//                                logger.debug("del id:" + del.getId());
//                                fifter.add(del);
//                            }
//                        }
//                );
//                newSalaryDao.delete(fifter);
//            }
            if (del != null) {
                logger.info("删除已经发布的薪资: " + del.getId() + " , " + del.getRealName());
                newSalaryDao.delete(del);
            }

            // 把状态改成已发布
            salaryEntity.setIsPublished(1);
            salaryEntity.setTxVersion(salaryEntity.getTxVersion() + 1);
            newSalaryDao.merge(salaryEntity);
            idVersion.setId(salaryEntity.getId());
            idVersion.setVersion(salaryEntity.getTxVersion());
            idVersions.add(idVersion);
            releases.add(salaryEntity);
        }

        SysLogDao.SysLogExtInfo sysLogExtInfo = new SysLogDao.SysLogExtInfo();
        sysLogExtInfo.setMsg(sb.toString());
        branOpLogDao.success(BranOpLogEntity.OP_MODULE_SALARY,
                BranOpLogEntity.OP_TYPE_SEND, command.getOperateUserId(), sysLogExtInfo);

        corpModel.setIds(idVersions.toArray(new IdVersion[0]));
        // 设置推送的ids
        command.setAryaUserIds(aryaUserIds);
        return corpModel;
    }

    @Override
    public CorpModel releaseAll(CorpModel command) {
        // 查询出所有的未发布列表
        CorporationEntity corp = corporationDao.findByBranId(command.getBranCorpId());
        SalaryCommand salaryCommand = new SalaryCommand();
        salaryCommand.setRelease(0);
        salaryCommand.setAryaCorpId(corp.getId());
        salaryCommand.setKeyword(command.getKeyword());
        salaryCommand.setMonth(command.getMonth());
        List<SalaryEntity> list = newSalaryDao.criteriaToList(newSalaryDao.findBySalaryCommand(salaryCommand));
        List<IdVersion> ids = new ArrayList<>();
        for (SalaryEntity salaryEntity : list) {
            IdVersion idVersion = new IdVersion();
            idVersion.setId(salaryEntity.getId());
            idVersion.setVersion(salaryEntity.getTxVersion());
            ids.add(idVersion);
        }
        command.setIds(ids.toArray(new IdVersion[]{}));
        return this.release(command);
    }
}
