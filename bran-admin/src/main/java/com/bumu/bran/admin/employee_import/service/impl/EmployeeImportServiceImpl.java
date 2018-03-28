package com.bumu.bran.admin.employee_import.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.DistrictDao;
import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.employee.result.ImportEmployeeResult;
import com.bumu.bran.admin.employee_import.engine.BranCorpEmployeeImportCollection;
import com.bumu.bran.admin.employee_import.service.EmployeeImportService;
import com.bumu.bran.employee.command.UserDefinedCommand;
import com.bumu.bran.employee.command.WorkSnPrefixQueryCommand;
import com.bumu.bran.employee.helper.EmpCommonHelper;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.ProspectiveEmployeeDao;
import com.bumu.bran.employee.model.dao.UserDefinedColsDao;
import com.bumu.bran.employee.model.dao.UserDefinedDetailsDao;
import com.bumu.bran.employee.model.dao.mybatis.EmployeeMybatisDao;
import com.bumu.bran.employee.model.entity.UserDefinedColsEntity;
import com.bumu.bran.employee.model.entity.UserDefinedDetailsEntity;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.*;
import com.bumu.bran.prospective.command.ProspectiveQueryCommand;
import com.bumu.bran.setting.model.dao.DepartmentDao;
import com.bumu.bran.setting.model.dao.PositionDao;
import com.bumu.bran.setting.model.dao.WorkLineDao;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.bran.validated.constraint.CheckUserDefinedType;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.FileStrCommand;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.service.BaseFileService;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.service.RedisService;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.model.entity.EmployeeDetailEntity;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.employee.util.EmpImportUtils;
import com.bumu.engine.excelimport.ImportService;
import com.bumu.engine.excelimport.model.ICConfigModel;
import com.bumu.engine.excelimport.model.ICResult;
import com.bumu.engine.excelimport.model.TitleModel;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import com.bumu.prospective.validated.ValidationMessages;
import com.bumu.worksn_prefix.command.WorkSnFormatCommand;
import com.bumu.worksn_prefix.model.dao.WorkSnPrefixDao;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author majun
 * @date 2017/11/9
 * @email 351264830@qq.com
 */
@Service
public class EmployeeImportServiceImpl implements EmployeeImportService {

    private static Logger logger = LoggerFactory.getLogger(EmployeeImportServiceImpl.class);

    @Autowired
    private BaseFileService fileService;

    @Autowired
    private BranCorpEmployeeImportCollection branCorpEmployeeImportCollection;

    @Autowired
    private ImportService importService;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private BranAdminConfigService branAdminConfigService;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private WorkLineDao workLineDao;

    @Autowired
    private WorkShiftDao workShiftDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private ProspectiveEmployeeDao prospectiveEmployeeDao;

    @Autowired
    private BranUserDao branUserDao;

    @Autowired
    private EmployeeMybatisDao employeeMybatisDao;

    @Autowired
    private ExcelExportHelper excelExportHelper;

    @Autowired
    private UserDefinedColsDao userDefinedColsDao;

    @Autowired
    private UserDefinedDetailsDao userDefinedDetailsDao;

    @Autowired
    private BranCorporationDao branCorporationDao;

    @Autowired
    private CheckUserDefinedType checkUserDefinedType;

    @Autowired
    private WorkSnPrefixDao workSnPrefixDao;

    @Autowired
    private EmpCommonHelper empCommonHelper;

    @Autowired
    private RedisService redisService;

    @Override
    public FileUploadFileResult download(SessionInfo sessionInfo, HttpServletResponse response) throws Exception {
        String templateName = "花名册导入模板";
        File in = new File(branAdminConfigService.getFilePath("bran.dir.excel.template", 0) + File.separator +
                templateName + ".xls");

        if (!in.exists()) {
            logger.error("in... :" + in.getPath());
            throw new AryaServiceException(ErrorCode.CODE_ORDER_TEMPLATE_IS_NOT_EXIST);
        }

        UserDefinedCommand userDefinedCommand = new UserDefinedCommand();
        userDefinedCommand.setBranCorpId(sessionInfo.getCorpId());
        List<UserDefinedColsEntity> list = userDefinedColsDao.findByCorpId(userDefinedCommand).list();

        Map<String, Object> params = new HashedMap();
        params.put("list", list);

        BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(sessionInfo.getCorpId());
        Assert.notNull(branCorporationEntity, "没有查询到企业");

        // 花名册导入模板临时文件
        String fileName = branCorporationEntity.getCorpName() + "_花名册导入模板";
        File temp = new File(branAdminConfigService.getFilePath("temp", 0) + fileName + "." + "xls");
        OutputStream outputStream = new FileOutputStream(temp);

        // excel引擎生成花名册自定义动态字段
        excelExportHelper.export(
                in.getPath(),
                in.getName(),
                params,
                outputStream
        );

        // 生成下载模板的url路径
        String url = fileUploadService.generateDownLoadFileUrl(
                branAdminConfigService.getConfigByKey("bran.admin.resource.server"),
                com.bumu.bran.common.Constants.HPPT_TYPE_EXCEL,
                fileName,
                0,
                "xls",
                "bran.dir.excel.template"
        );

        logger.info("url: " + url);

        return new FileUploadFileResult(null, url);
    }

    @Override
    public ImportEmployeeResult verify(MultipartFile file, SessionInfo sessionInfo) throws Exception {

        Assert.notNull(file, "上传的文件必填");

        // 把文件存入临时文件
        String fileTypeStr = SysUtils.getFileSuffixStr(file.getOriginalFilename());

        if (!SysUtils.checkExcelFormat(fileTypeStr)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, ValidationMessages.ERROR_EXCEL_FORMAT);
        }

        String fileId = fileService.tempSaveFile(file, 0, fileTypeStr);
        ImportEmployeeResult importEmployeeResult = process(fileId, fileTypeStr, sessionInfo);
        return importEmployeeResult;
    }

    @Override
    public void confirm(FileStrCommand fileStrCommand, SessionInfo sessionInfo) throws Exception {
        fileService.getTempFileUrl(fileStrCommand.getFileId(), 0, fileStrCommand.getFileTypeStr());
        ImportEmployeeResult importEmployeeResult = process(fileStrCommand.getFileId(), fileStrCommand.getFileTypeStr(), sessionInfo);
        if (importEmployeeResult.isHasError()) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "解析出错");
        }
        int i = 1;
        List<ImportEmployeeResult.Employee> list = importEmployeeResult.getEmployees();
        for (ImportEmployeeResult.Employee employee : list) {
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setId(Utils.makeUUID());
            employeeEntity.setTxVersion(0L);
            employeeEntity.setBranCorpId(sessionInfo.getCorpId());
            employeeEntity.setBranContractId(null);
            employeeEntity.setCheckinTime(employee.getCheckinTime().getValue());
            // 合同开始时间
            if (employee.getStartTime() != null && StringUtils.isNotBlank(employee.getStartTime().getValue())) {
                employeeEntity.setStartTime(employee.getStartTime().getValue());
            }

            // 合同结束时间
            if (employee.getEndTime() != null && StringUtils.isNotBlank(employee.getEndTime().getValue()) && "无限期".equals(employee.getEndTime().getValue())) {
                employeeEntity.setEndTime(253402185600000L);
            } else {
                employeeEntity.setEndTime(employee.getEndTime().getValue());
            }

            // 现居地省份
            if (employee.getProvinceName() != null && StringUtils.isNotBlank(employee.getProvinceName().getValue())) {
                DistrictEntity province = districtDao.findOneDistrictByName(employee.getProvinceName().getValue());
                Assert.notNull(province, "没有查询到省");
                employeeEntity.setAddProvinceCode(province.getId());
                employeeEntity.setAddProvinceName(province.getDistrictName());
            }
            // 现居地城市
            if (employee.getCityName() != null && StringUtils.isNotBlank(employee.getCityName().getValue())) {
                String cityStr = employee.getCityName().getValue();

                if (!"市".equals(cityStr.substring(cityStr.length() - 1, cityStr.length()))) {
                    cityStr += "市";
                }
                logger.debug("市: " + cityStr);
                DistrictEntity city = districtDao.findOneDistrictByName(cityStr);
                Assert.notNull(city, "没有查询到市");
                employeeEntity.setAddCityCode(city.getId());
                employeeEntity.setAddCityName(city.getDistrictName());
            }
            // 现居地行政区
            if (employee.getCountyName() != null && StringUtils.isNotBlank(employee.getCountyName().getValue())) {
                DistrictEntity county = districtDao.findOneDistrictByName(employee.getCountyName().getValue());
                Assert.notNull(county, "没有查询到区");
                employeeEntity.setAddCountyCode(county.getId());
                employeeEntity.setAddCountyName(county.getDistrictName());
            }
            // 现居地地址
            if (employee.getBusAddress() != null) {
                employeeEntity.setAddress(employee.getBusAddress().getValue());
            }
            // 婚姻状况
            if (employee.getMarriage() != null) {
                employeeEntity.setStrMarriage(employee.getMarriage().getValue());
            } else {
                employeeEntity.setMarriage(null);
            }
            // 性别
            if (employee.getSex() != null) {
                employeeEntity.setStrSex(employee.getSex().getValue());
            } else {
                employeeEntity.setSex(null);
            }
            // 联系方式
            if (employee.getTelephone() != null) {
                employeeEntity.setTelephone(employee.getTelephone().getValue());
            }
            // 紧急联系人
            if (employee.getUrgentContact() != null) {
                employeeEntity.setUrgentContact(employee.getUrgentContact().getValue());
            }
            // 紧急联系人手机
            if (employee.getUrgentContactPhone() != null) {
                employeeEntity.setUrgentContactPhone(employee.getUrgentContactPhone().getValue());
            }
            String workSn = "";
            if (!"无".equals(employee.getPrefixName().getValue())) {
                // 设置工号前缀
                WorkSnPrefixEntity workSnPrefixEntity = workSnPrefixDao.findByNameOnCorp(employee.getPrefixName().getValue(), sessionInfo.getCorpId());
                WorkSnFormatCommand workSnFormatCommand = new WorkSnFormatCommand();
                workSnFormatCommand.setWorkSnPrefixId(workSnPrefixEntity.getId());
                workSnFormatCommand.setWorkSnPrefixName(workSnPrefixEntity.getPrefixName());
                workSnFormatCommand.setWorkSn(employee.getWorkSn().getValue());
                workSnFormatCommand.setId(null);
                employeeEntity.setWorkSnPrefixId(workSnPrefixEntity.getId());
                employeeEntity.setWorkSnPrefixName(workSnPrefixEntity.getPrefixName());
                employeeEntity.setWorkSnSuffixName(Long.valueOf(employee.getWorkSn().getValue()));
                employeeEntity.setWorkSn(employee.getPrefixName().getValue() + empCommonHelper.getFormatWorkSn(workSnFormatCommand));

            } else {

                WorkSnFormatCommand workSnFormatCommand = new WorkSnFormatCommand();
                workSnFormatCommand.setWorkSn(employee.getWorkSn().getValue());
                workSnFormatCommand.setId(null);
                employeeEntity.setWorkSn(empCommonHelper.getFormatWorkSn(workSnFormatCommand));
                employeeEntity.setWorkSnPrefixName("empty");
                employeeEntity.setWorkSnSuffixName(Long.valueOf(employee.getWorkSn().getValue()));

            }
            // 设置工号后缀
            workSn = employeeEntity.getWorkSn();
            logger.debug("save setWorkSnPrefixName:" + employeeEntity.getWorkSnPrefixName());
            logger.debug("save workSn:" + workSn);


            DepartmentEntity departmentEntity = departmentDao.findCorpDepartmentIdByName(
                    employee.getDepartmentName().getValue(), sessionInfo.getCorpId());
            Assert.notNull(departmentEntity, "没有查询到部门");
            employeeEntity.setDepartmentId(departmentEntity.getId());
            employeeEntity.setDepartmentName(departmentEntity.getDepartmentName());

            WorkLineEntity workLineEntity = workLineDao.findCorpWorkLineIdByName(
                    employee.getWorkLineName().getValue(), sessionInfo.getCorpId());
            Assert.notNull(workLineEntity, "没有查询到工段");
            employeeEntity.setWorkLineId(workLineEntity.getId());
            employeeEntity.setWorkLineName(workLineEntity.getLineName());

            WorkShiftEntity workShiftEntity = workShiftDao.findCorpWorkShiftByNameAndBranCorpId(
                    employee.getWorkShiftName().getValue(), sessionInfo.getCorpId());
            Assert.notNull(workShiftEntity, "没有查询到班组");
            employeeEntity.setWorkShiftId(workShiftEntity.getId());
            employeeEntity.setWorkShiftName(workShiftEntity.getShiftName());

            PositionEntity positionEntity = positionDao.findCorpPostionByNameAndBranCorpId(
                    employee.getPositionName().getValue(), sessionInfo.getCorpId());
            Assert.notNull(positionEntity, "没有查询到职位");
            employeeEntity.setPositionId(positionEntity.getId());
            employeeEntity.setPositionName(positionEntity.getPositionName());

            employeeEntity.setEmail(null);

            employeeEntity.setSalary(null);
            employeeEntity.setFaceFileName(null);
            employeeEntity.setIdcardBackFileName(null);
            employeeEntity.setIdcardFaceFileName(null);
            employeeEntity.setIdcardAddress(null);
            employeeEntity.setOffice(null);
            employeeEntity.setExpireTime(null);
            employeeEntity.setLeaveCertFileName(null);


            employeeEntity.setUrgentContactCorp(null);

            employeeEntity.setIsImported(0);
            employeeEntity.setProbationhandled(0);
            employeeEntity.setRealName(employee.getRealName().getValue());
            employeeEntity.setBirthday(null);
            employeeEntity.setNation(null);


            employeeEntity.setIdCardNo(employee.getIdCardNo().getValue());
            employeeEntity.setAge(0);
            employeeEntity.setExamId(null);
            EmployeeDetailEntity detailEntity = new EmployeeDetailEntity();
            // 员工性质
            if (employee.getEmployeeNature() != null) {
                detailEntity.setEmployeeNature(employee.getEmployeeNature().getValue());
            }

            // 面试日期
            if (employee.getInterviewDate() == null || StringUtils.isBlank(employee.getInterviewDate().getValue())) {
                // 如果面试日期没有填 则跟入职日期相同
                detailEntity.setInterviewDate(employee.getCheckinTime().getValue());
            } else {
                detailEntity.setInterviewDate(employee.getInterviewDate().getValue());
            }
            // 供应来源
            if (employee.getSourceOfSupply() != null) {
                detailEntity.setSourceOfSupply(employee.getSourceOfSupply().getValue());
            }
            employeeEntity.setDetail(detailEntity);
            BankCardEntity bankCardEntity = new BankCardEntity();
            // 开户信息
            if (employee.getBankAccount() != null) {
                bankCardEntity.setBankAccount(employee.getBankAccount().getValue());
            }
            bankCardEntity.setBankCardUrl(null);
            // 银行卡号
            if (employee.getBankNum() != null) {
                bankCardEntity.setBankNum(employee.getBankNum().getValue());
            }

            employeeEntity.setBankCardEntity(bankCardEntity);
            // 班车点
            if (employee.getBusAddress() != null) {
                employeeEntity.setBusAddress(employee.getBusAddress().getValue());
            }

            /*// 生成打卡号
            long workAttendanceNo = employeeMybatisDao.findMaxWorkAttendanceNo(sessionInfo.getCorpId()) + i;
            i++;*/
            String key = "WORKATTENDANCENO_"+sessionInfo.getCorpId();
            if(!redisService.exists(key)){
                redisService.set(key,"100010000");
            }
            long workAttendanceNo = redisService.incrby(key);

            employeeEntity.setWorkAttendanceNo(workAttendanceNo);

            employeeEntity.setWorkAttendanceAddState(EmployeeEntity.WorkAttendanceAddState.initial);
            employeeEntity.setWorkingState(EmployeeEntity.WorkingState.in);
            employeeEntity.setRegisterAccount(employee.getRegisterAccount().getValue());
            employeeEntity.setIsBinding(0);
            // 查询待入职 员工自己添加
            ProspectiveQueryCommand prospectiveQueryCommand = new ProspectiveQueryCommand();
            prospectiveQueryCommand.setTel(employeeEntity.getRegisterAccount());
            prospectiveQueryCommand.setCreateType(com.bumu.bran.common.Constants.APP_CREATE);
            prospectiveQueryCommand.setBranCorpId(sessionInfo.getCorpId());
            List<ProspectiveEmployeeEntity> prospectiveEmployeeEntities = prospectiveEmployeeDao.findByQueryCommand(prospectiveQueryCommand);
            if (!ListUtils.checkNullOrEmpty(prospectiveEmployeeEntities)) {
                logger.info("查询到待入职员工");
                // 删除待入职
                prospectiveEmployeeDao.delete(prospectiveEmployeeEntities);
            }

            logger.info("公司id: " + sessionInfo.getCorpId());
            // 如果员工扫了这家公司, 那么直接绑定
            // 查询 branUserId
            BranUserEntity branUserEntity = branUserDao.findBranUserByPhoneNoAndCorpId(employeeEntity.getRegisterAccount(), employeeEntity.getBranCorpId());
            if (branUserEntity != null) {
                logger.info("查询到branUserEntity");
                employeeEntity.setIsBinding(1);
                employeeEntity.setBranUserId(branUserEntity.getId());
            }

            if (employee.getBornDate() != null) {
                employeeEntity.setBornDate(EmpImportUtils.dateStringToDate(employee.getBornDate().getValue()));
            }

            if (employee.getPoliticalStatus() != null) {
                employeeEntity.setPoliticalStatus(EmpImportUtils.StringToPoliticalStatus(employee.getPoliticalStatus().getValue()));
            }

            if (employee.getSocialSecurityType() != null) {
                employeeEntity.setSocialSecurityType(employee.getSocialSecurityType().getValue());
            }

            if (employee.getDegreeOfEducation() != null) {
                employeeEntity.setDegreeOfEducation(EmpImportUtils.StringToDegreeOfEducation(employee.getDegreeOfEducation().getValue()));
            }

            if (employee.getGraduatedSchool() != null) {
                employeeEntity.setGraduatedSchool(employee.getGraduatedSchool().getValue());
            }

            if (employee.getProfessionalCategory() != null) {
                employeeEntity.setProfessionalCategory(employee.getProfessionalCategory().getValue());
            }


            if (employee.getGraduationTime() != null) {
                employeeEntity.setGraduationTime(EmpImportUtils.dateStringToDate(employee.getGraduationTime().getValue()));
            }

            String expireTime = "";

            if (employee.getExpireStartTime() != null && StringUtils.isNotBlank(employee.getExpireStartTime().getValue())) {
                employeeEntity.setExpireStartTime(EmpImportUtils.dateStringToDate(employee.getExpireStartTime().getValue()));
                expireTime += SysUtils.getDateStringFormTimestamp(employeeEntity.getExpireStartTime().getTime(), "yyyy.MM.dd");
            }

            if (employee.getExpireEndTime() != null && StringUtils.isNotBlank(employee.getExpireEndTime().getValue())) {
                if (employee.getExpireEndTime().getValue().equals("长期")) {
                    employeeEntity.setIsLongTerm(1);
                    expireTime += "-长期";
                } else {
                    employeeEntity.setExpireEndTime(EmpImportUtils.dateStringToDate(employee.getExpireEndTime().getValue()));
                    expireTime += "-" + SysUtils.getDateStringFormTimestamp(employeeEntity.getExpireEndTime().getTime(), "yyyy.MM.dd");
                }
            }

            if (StringUtils.isNotBlank(expireTime)) {
                employeeEntity.setExpireTime(expireTime);
            }

            List<ImportEmployeeResult.Params> paramsList = employee.getUserDefined();
            Map<String, ImportEmployeeResult.Params> paramMap = new HashedMap();
            paramsList.forEach(p -> {
                paramMap.put(p.getName(), p);
            });


            // 设置花名册自定义
            UserDefinedCommand command = new UserDefinedCommand();
            command.setBranCorpId(sessionInfo.getCorpId());
            List<UserDefinedColsEntity> userDefinedCols = userDefinedColsDao.findByCorpId(command).list();
            for (UserDefinedColsEntity userDefinedColsEntity : userDefinedCols) {
                UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                userDefinedDetailsEntity.setId(Utils.makeUUID());
                userDefinedDetailsEntity.setCreateUser(sessionInfo.getUserId());
                userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                userDefinedDetailsEntity.setEmployeeEntity(employeeEntity);
                userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                if (paramMap.containsKey(userDefinedColsEntity.getColName())) {
                    ImportEmployeeResult.Params params = paramMap.get(userDefinedColsEntity.getColName());
                    if (StringUtils.isNotBlank(paramMap.get(userDefinedColsEntity.getColName()).getValue())) {
                        if (userDefinedColsEntity.getType() == 2) {
                            userDefinedDetailsEntity.setColValue(EmpImportUtils.dateStringToLong(params.getValue()) + "");
                        } else {
                            userDefinedDetailsEntity.setColValue(params.getValue());
                        }

                    }
                }
                userDefinedDetailsDao.create(userDefinedDetailsEntity);
            }


            employeeDao.persist(employeeEntity);
        }
    }

    private ImportEmployeeResult process(String fileId, String fileTypeStr, SessionInfo sessionInfo) throws Exception {

        branCorpEmployeeImportCollection.init(
                "姓名",
                "身份证号",
                "注册账号",
                "工号前缀",
                "工号",
                "部门",
                "职位",
                "班组",
                "工段",
                "入职日期",
                "面试日期",
                "合同开始时间",
                "合同结束时间",
                "现居地省份",
                "现居地城市",
                "现居地行政区",
                "现居地地址",
                "婚姻状况",
                "性别",
                "出生年月",
                "联系方式",
                "紧急联系人",
                "紧急联系人手机",
                "政治面貌",
                "社保类型",
                "文化程度",
                "毕业院校",
                "专业类别",
                "毕业时间",
                "开户行信息",
                "银行卡号",
                "供应来源",
                "员工性质",
                "班车点",
                "身份证有效期开始时间",
                "身份证有效期结束时间",
                sessionInfo.getCorpId()
        );

        ICConfigModel icConfigModel = new ICConfigModel();
        icConfigModel.sourceFile = new File(fileService.getTempFileUrl(fileId, 0, fileTypeStr));
        icConfigModel.setImportRegList(branCorpEmployeeImportCollection.getEmpImportTitleCheckRule());
        ICResult icResult = importService.importExcel(icConfigModel);

        if (icResult.getExceptionModel() != null) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, icResult.getExceptionModel().getExceptionMessage());
        }

        ImportEmployeeResult importEmployeeResult = new ImportEmployeeResult();
        importEmployeeResult.setBranCorpId(sessionInfo.getCorpId());
        importEmployeeResult.setUserDefinedColsDao(userDefinedColsDao);
        importEmployeeResult.setCheckUserDefinedType(checkUserDefinedType);

        UserDefinedCommand modelCommand = new UserDefinedCommand();
        modelCommand.setBranCorpId(sessionInfo.getCorpId());
        // 查询本公司所有的自定义列,添加至optionalTitle,用于判断excel是否有多余的列
        List<UserDefinedColsEntity> userDefinedCols = userDefinedColsDao.findByCorpId(modelCommand).list();
        if (!ListUtils.checkNullOrEmpty(userDefinedCols)) {
            for (UserDefinedColsEntity col : userDefinedCols) {
                importEmployeeResult.optionalTitle.add(col.getColName());
            }
        }

        importEmployeeResult.allTitle.addAll(importEmployeeResult.optionalTitle);

        for (TitleModel titleModel : icResult.getTitleModelList()) {
            if ("isNull".equals(titleModel.getTitle())) {
                continue;
            }
            if (!importEmployeeResult.allTitle.contains(titleModel.getKey())) {
                logger.warn("titleModel: " + titleModel.toString());
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "多余的列" + titleModel.getKey());
            }
        }


        importEmployeeResult.setEmployees(new ArrayList<>());
        importEmployeeResult.convert(icResult.getErrDataList());
        importEmployeeResult.convert(icResult.getSuccessDataList());

        importEmployeeResult.setTotal_count(icResult.getTotalImportNumber());
        importEmployeeResult.setFileTypeStr(fileTypeStr);
        importEmployeeResult.setFile_id(fileId);

        Set<String> workSnSet = new HashSet<>();


        WorkSnPrefixQueryCommand workSnPrefixQueryCommand = new WorkSnPrefixQueryCommand();

        // 组合验证
        logger.info("开始组合验证");
        for (ImportEmployeeResult.Employee employee : importEmployeeResult.getEmployees()) {
            logger.info("开始验证工号");
            if (employee.getPrefixName() != null && employee.getWorkSn() != null) {
                if (employee.getPrefixName().getFlag() == 0 && employee.getWorkSn().getFlag() == 0) {
                    String workSn = "";
                    logger.info("设置工号前缀");
                    if (StringUtils.isNotBlank(employee.getPrefixName().getValue()) && !"无".equals(employee.getPrefixName().getValue())) {

                        logger.info("有前缀: " + employee.getPrefixName().getValue());
                        WorkSnPrefixEntity workSnPrefixEntity = workSnPrefixDao.findByNameOnCorp(employee.getPrefixName().getValue(), sessionInfo.getCorpId());
                        if (workSnPrefixEntity.getDigit() != null) {
                            logger.info("验证长度");
                            if (employee.getWorkSn().getValue().length() > workSnPrefixEntity.getDigit()) {
                                employee.getWorkSn().setFlag(1);
                                employee.getWorkSn().setErr("此工号不超过" + workSnPrefixEntity.getDigit() + "位数字");
                                employee.setHasError(true);
                            }
                        }

                        if (employee.getWorkSn().getFlag() == 0) {
                            WorkSnFormatCommand workSnFormatCommand = new WorkSnFormatCommand();
                            workSnFormatCommand.setWorkSnPrefixId(workSnPrefixEntity.getId());
                            workSnFormatCommand.setWorkSnPrefixName(workSnPrefixEntity.getPrefixName());
                            workSnFormatCommand.setWorkSn(employee.getWorkSn().getValue());
                            workSnFormatCommand.setWorkSnSuffixName(Long.valueOf(employee.getWorkSn().getValue()));
                            workSnFormatCommand.setId(null);
                            String w = empCommonHelper.getFormatWorkSn(workSnFormatCommand);
                            if (w.indexOf(workSnPrefixEntity.getPrefixName()) != -1) {
                                employee.getWorkSn().setValue(w.substring(workSnPrefixEntity.getPrefixName().length()));
                            }

                            workSn += (workSnPrefixEntity.getPrefixName() + employee.getWorkSn().getValue());

                            workSnPrefixQueryCommand.setWorkSnPrefixName(workSnPrefixEntity.getPrefixName());
                        }


                    } else {
                        logger.info("没有前缀: " + employee.getPrefixName().getValue());
                        logger.info("验证长度");
                        if (employee.getWorkSn().getValue().length() > 8) {
                            employee.getWorkSn().setFlag(1);
                            employee.getWorkSn().setErr("此工号不超过8位数字");
                            employee.setHasError(true);
                        }

                        WorkSnFormatCommand workSnFormatCommand = new WorkSnFormatCommand();
                        workSnFormatCommand.setWorkSn(employee.getWorkSn().getValue());
                        workSnFormatCommand.setId(null);
                        employee.getWorkSn().setValue(empCommonHelper.getFormatWorkSn(workSnFormatCommand));
                        workSn += employee.getWorkSn().getValue();

                        workSnPrefixQueryCommand.setWorkSnPrefixName("empty");
                    }

                    logger.info("workSn: " + workSn);
                    if (workSnSet.contains(workSn)) {
                        employee.getWorkSn().setFlag(1);
                        employee.getWorkSn().setErr("工号与表中其他员工重复");
                        employee.setHasError(true);
                    }
                    if (workSnSet.isEmpty()) {
                        workSnSet.add(workSn);
                    }
                    if (employee.getWorkSn().getFlag() == 0) {
                        workSnPrefixQueryCommand.setBranCorpId(sessionInfo.getCorpId());
                        workSnPrefixQueryCommand.setWorkSn(workSn);

                        if (employeeDao.isWorkSnsBeenUsed(workSnPrefixQueryCommand)) {
                            employee.getWorkSn().setFlag(1);
                            employee.getWorkSn().setErr("工号与公司人员重复");
                            employee.setHasError(true);
                        }
                    }

                }
            }

            // 面试日期要小于入职日期
            if (employee.getCheckinTime() != null && employee.getInterviewDate() != null) {
                if (employee.getCheckinTime().getFlag() == 0 && employee.getInterviewDate().getFlag() == 0) {
                    // 如果面试日期没有填 则跟入职日期相同
                    if (StringUtils.isBlank(employee.getInterviewDate().getValue())) {
                        employee.getInterviewDate().setValue(employee.getCheckinTime().getValue());
                    }
                    if (EmpImportUtils.dateStringToLong(employee.getInterviewDate().getValue()) >
                            EmpImportUtils.dateStringToLong(employee.getCheckinTime().getValue())

                            ) {
                        employee.getInterviewDate().setFlag(1);
                        employee.getInterviewDate().setErr("面试日期要小于入职日期");
                        employee.setHasError(true);
                        if (employee.getOther() == null) {
                            employee.setOther(employee.getInterviewDate());
                        }
                    }
                }
            }

            // 合同结束时间 > 合同开始时间
            if (employee.getStartTime() != null
                    && employee.getEndTime() != null
                    && StringUtils.isNotBlank(employee.getStartTime().getValue())
                    && StringUtils.isNotBlank(employee.getEndTime().getValue())) {
                if (!employee.getEndTime().getValue().equals("无期限") && employee.getStartTime().getFlag() == 0 && employee.getEndTime().getFlag() == 0) {
                    if (EmpImportUtils.dateStringToLong(employee.getStartTime().getValue()) >=
                            EmpImportUtils.dateStringToLong(employee.getEndTime().getValue())

                            ) {
                        employee.getEndTime().setFlag(1);
                        employee.getEndTime().setErr("合同结束时间小于等于合同开始时间");
                        employee.setHasError(true);
                        if (employee.getOther() == null) {
                            employee.setOther(employee.getEndTime());
                        }
                    }
                }
            }

            // 如果身份证有效期开始时间不填, 如果身份证有效期结束时间填写
            if (employee.getExpireStartTime() == null || StringUtils.isBlank(employee.getExpireStartTime().getValue())) {

                if (employee.getExpireEndTime() != null && StringUtils.isNotBlank(employee.getExpireEndTime().getValue())) {
                    employee.getExpireStartTime().setFlag(1);
                    employee.getExpireStartTime().setErr("身份证有效期开始时间必填");
                    employee.setOther(employee.getExpireStartTime());
                    employee.setHasError(true);
                }
            }

            // 如果身份证有效期开始时间不填, 如果身份证有效期结束时间填写
            if (employee.getExpireEndTime() == null || StringUtils.isBlank(employee.getExpireEndTime().getValue())) {

                if (employee.getExpireStartTime() != null && StringUtils.isNotBlank(employee.getExpireStartTime().getValue())) {
                    employee.getExpireEndTime().setFlag(1);
                    employee.getExpireEndTime().setErr("身份证有效期结束时间必填");
                    employee.setOther(employee.getExpireEndTime());
                    employee.setHasError(true);
                }
            }


            // 身份证有效期结束时间 > 身份证有效期开始时间
            if (employee.getExpireStartTime() != null
                    && employee.getExpireEndTime() != null
                    && StringUtils.isNotBlank(employee.getExpireStartTime().getValue())
                    && StringUtils.isNotBlank(employee.getExpireEndTime().getValue())) {
                if (!employee.getExpireEndTime().getValue().equals("长期") && employee.getExpireStartTime().getFlag() == 0 && employee.getExpireEndTime().getFlag() == 0) {
                    if (EmpImportUtils.dateStringToLong(employee.getExpireStartTime().getValue()) >=
                            EmpImportUtils.dateStringToLong(employee.getExpireEndTime().getValue())

                            ) {
                        employee.getExpireEndTime().setFlag(1);
                        employee.getExpireEndTime().setErr("身份证有效期开始时间大于身份证有效期结束时间");
                        employee.setHasError(true);
                        if (employee.getOther() == null) {
                            employee.setOther(employee.getExpireEndTime());
                        }
                    }
                }
            }

            logger.info("开始验证省市区");
            if (employee.getProvinceName() != null && employee.getProvinceName().getFlag() == 0) {
                logger.info("验证省");
                DistrictEntity province = districtDao.findOneDistrictByName(employee.getProvinceName().getValue());
                logger.info("验证市");
                if (employee.getCityName() != null && employee.getCityName().getFlag() == 0 && StringUtils.isNotBlank(employee.getCityName().getValue())) {
                    DistrictEntity cityParent = districtDao.findDistrictByNameAndParentId(employee.getCityName().getValue(), province.getId());
                    if (cityParent == null) {
                        employee.getCityName().setFlag(1);
                        employee.getCityName().setErr(province.getDistrictName() + "没有该市");
                        employee.setOther(employee.getCityName());
                        employee.setHasError(true);
                        continue;
                    }
                    logger.info("验证区");
                    DistrictEntity city = districtDao.findOneDistrictByName(employee.getCityName().getValue());
                    if (employee.getCountyName() != null && employee.getCountyName().getFlag() == 0 && StringUtils.isNotBlank(employee.getCountyName().getValue())) {
                        DistrictEntity countyParent = districtDao.findDistrictByNameAndParentId(employee.getCountyName().getValue(), city.getId());
                        if (countyParent == null) {
                            employee.getCountyName().setFlag(1);
                            employee.getCountyName().setErr(city.getDistrictName() + "没有该区");
                            employee.setOther(employee.getCountyName());
                            employee.setHasError(true);
                        }
                    }
                }
            }
        }

        importEmployeeResult.setHasError(icResult.getFailImportNumber() > 0);
        importEmployeeResult.calculateProblemsCount();
        Collections.sort(importEmployeeResult.getEmployees());
        return importEmployeeResult;
    }

}
