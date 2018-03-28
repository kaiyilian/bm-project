package com.bumu.bran.admin.employee_import.engine.import_check;

import com.bumu.SysUtils;
import com.bumu.arya.model.DistrictDao;
import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.bran.employee.command.EmpQueryCommand;
import com.bumu.bran.employee.model.dao.EmployeeDao;
import com.bumu.bran.employee.model.dao.ProspectiveEmployeeDao;
import com.bumu.bran.model.entity.*;
import com.bumu.bran.prospective.command.ProspectiveQueryCommand;
import com.bumu.bran.setting.model.dao.DepartmentDao;
import com.bumu.bran.setting.model.dao.PositionDao;
import com.bumu.bran.setting.model.dao.WorkLineDao;
import com.bumu.bran.setting.model.dao.WorkShiftDao;
import com.bumu.common.util.ListUtils;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.employee.util.EmpImportUtils;
import com.bumu.engine.excelimport.ImportCheck;
import com.bumu.engine.excelimport.model.ICCollection;
import com.bumu.engine.excelimport.model.ICModel;
import com.bumu.engine.excelimport.model.ImportReg;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import com.bumu.worksn_prefix.model.dao.WorkSnPrefixDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author majun
 * @date 2017/11/10
 * @email 351264830@qq.com
 */
@Component
public class EmpImportCheck {

    private static Logger logger = LoggerFactory.getLogger(EmpImportCheck.class);

    private static ImportCheck notNullCheck = new ICCollection.NOT_NULL_CHECK();
    private static ImportCheck IdCardCheck = new ICCollection.CheckReg(ImportReg.RegEnum.IDCARD);
    private static ImportCheck telCheck = new ICCollection.CheckReg(ImportReg.RegEnum.MOBLIE);
    private String branCorpId;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private ProspectiveEmployeeDao prospectiveEmployeeDao;
    @Autowired
    private WorkSnPrefixDao workSnPrefixDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private PositionDao positionDao;
    @Autowired
    private WorkShiftDao workShiftDao;
    @Autowired
    private WorkLineDao workLineDao;
    @Autowired
    private DistrictDao districtDao;
    private ICModel icModel = new ICModel();

    public String getBranCorpId() {
        return branCorpId;
    }

    public void setBranCorpId(String branCorpId) {
        this.branCorpId = branCorpId;
    }

    public class RealNameCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                Pattern pattern = Pattern.compile(".{1,8}");
                if (!pattern.matcher(param).matches()) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("姓名不能超过8个字");
                    return icModel;
                }
            }
            return icModel;
        }
    }

    public class NoRepeatIdCardCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel = IdCardCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            EmpQueryCommand empQueryCommand = new EmpQueryCommand();
            empQueryCommand.setBranCorpId(branCorpId);
            for (String param : params) {
                param = param.trim();
                empQueryCommand.setIdCardNo(param);
                List<EmployeeEntity> employeeEntities = employeeDao.findByQueryCommand(empQueryCommand);
                if (!ListUtils.checkNullOrEmpty(employeeEntities)) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("花名册中已存在该身份证号");
                    return icModel;
                }
            }
            return icModel;
        }
    }

    public class NoRepeatTelCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel = telCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);

            EmpQueryCommand empQueryCommand = new EmpQueryCommand();
            empQueryCommand.setBranCorpId(branCorpId);

            ProspectiveQueryCommand prospectiveQueryCommand = new ProspectiveQueryCommand();
            prospectiveQueryCommand.setBranCorpId(branCorpId);

            for (String param : params) {
                param = param.trim();
                prospectiveQueryCommand.setTel(param);
                // HR添加
                prospectiveQueryCommand.setCreateType(2);
                List<ProspectiveEmployeeEntity> prospectiveEmployeeEntities = prospectiveEmployeeDao.findByQueryCommand(prospectiveQueryCommand);
                if (!ListUtils.checkNullOrEmpty(prospectiveEmployeeEntities)) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("待入职中已存在该手机");
                    return icModel;
                }
                empQueryCommand.setRegisterAccount(param);
                List<EmployeeEntity> employeeEntities = employeeDao.findByQueryCommand(empQueryCommand);
                if (!ListUtils.checkNullOrEmpty(employeeEntities)) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("花名册中已存在该手机");
                    return icModel;
                }
            }
            return icModel;
        }
    }

    public class PrefixNameCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (param.trim().equals("无")) {
                    continue;
                }
                if (!param.matches("^[a-zA-Z0-9]{1,8}$")) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("工号前缀只能是1-8位长度字母");
                    return icModel;
                }
                WorkSnPrefixEntity workSnPrefixEntity = workSnPrefixDao.findByNameOnCorp(param, branCorpId);
                if (workSnPrefixEntity == null) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("没有此前缀");
                    return icModel;
                }
            }
            return icModel;
        }
    }

    public class WorkSnCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            // 必填
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            return icModel;
        }
    }

    public class DepartmentCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            // 必填
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                // 是否存在
                DepartmentEntity departmentEntity = departmentDao.findCorpDepartmentIdByName(param, branCorpId);
                if (departmentEntity == null) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("没有该部门");
                    return icModel;
                }

            }
            return icModel;
        }
    }

    public class PositionCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            // 必填
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                // 是否存在
                PositionEntity positionEntity = positionDao.findCorpPostionByNameAndBranCorpId(param, branCorpId);
                if (positionEntity == null) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("没有该职位");
                    return icModel;
                }

            }
            return icModel;
        }
    }

    public class WorkShiftCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            // 必填
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                // 是否存在
                WorkShiftEntity workShiftEntity = workShiftDao.findCorpWorkShiftByNameAndBranCorpId(param, branCorpId);
                if (workShiftEntity == null) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("没有该班组");
                    return icModel;
                }

            }
            return icModel;
        }
    }

    public class WorkLineCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            // 必填
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                // 是否存在
                WorkLineEntity workShiftEntity = workLineDao.findCorpWorkLineIdByName(param, branCorpId);
                if (workShiftEntity == null) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("没有该工段");
                    return icModel;
                }

            }
            return icModel;
        }
    }

    public class CheckInTimeCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            // 必填
            icModel = notNullCheck.checkMethod(titleName, params);
            if (!icModel.getSuccess()) {
                return icModel;
            }
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (!EmpImportUtils.dateFormatCheck(param)) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add("入职日期格式不对");
                    return icModel;
                }
            }
            return icModel;
        }
    }

    /**
     * 面试日期
     * <p>
     * 面试日期格式不对
     */
    public class InterviewDateCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                if (StringUtils.isNotBlank(param)) {
                    param = param.trim();
                    if (!EmpImportUtils.dateFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("面试日期格式不对");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    /**
     * 合同开始时间
     * <p>
     * 合同开始时间格式不对
     */
    public class StartTimeCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                if (StringUtils.isNotBlank(param)) {
                    param = param.trim();
                    if (!EmpImportUtils.dateFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("合同开始时间格式不对");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    /**
     * 合同结束时间
     * <p>
     * 合同结束时间格式不对或为无限期
     * 合同开始时间必须小于结束时间
     * 合同结束时间不填,默认无限期
     */
    public class EndTimeCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                if (StringUtils.isNotBlank(param)) {
                    param = param.trim();
                    if (!EmpImportUtils.dateFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("合同结束时间格式不对");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    /**
     *
     */
    public class ProvinceNameCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    List<DistrictEntity> districtEntities = districtDao.findDistrictByName(param);
                    if (ListUtils.checkNullOrEmpty(districtEntities)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(param + "现居地省份不存在");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class CityNameCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!"市".equals(param.substring(param.length() - 1, param.length()))) {
                        param += "市";
                    }
                    logger.debug("市: " + param);
                    List<DistrictEntity> districtEntities = districtDao.findDistrictByName(param);
                    if (ListUtils.checkNullOrEmpty(districtEntities)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(param + "现居地城市不存在");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class CountyNameCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    List<DistrictEntity> districtEntities = districtDao.findDistrictByName(param);
                    if (ListUtils.checkNullOrEmpty(districtEntities)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(param + "现居地行政区不存在");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class AddressCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!Pattern.compile(".{1,50}").matcher(param).matches()) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("现居地地址不能超过50个字");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class MarriageCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!SysUtils.patternCheck(EmpImportUtils.marriageFormatPattern, param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("婚姻状态只能是未婚、已婚、已婚已育");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class SexCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!SysUtils.patternCheck(EmpImportUtils.sexFormatPattern, param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("性别只能是男、女");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class BornDateCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!EmpImportUtils.dateFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("出生年月日期格式不对");
                        return icModel;
                    }
                }

            }
            return icModel;
        }
    }

    public class TelephoneCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!EmpImportUtils.phoneFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "手机格式不正确");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class UrgentContactCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!SysUtils.patternCheck(".{1,8}", param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "必须在1-8个字");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class UrgentContactPhoneCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!EmpImportUtils.phoneFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "格式不正确");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    /**
     * 政治面貌:党员、团员、群众
     */
    public class PoliticalStatusCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!("党员".equals(param) || "团员".equals(param) || "群众".equals(param))) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "必须是党员、团员、群众");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }


    public class SocialSecurityTypeCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param) && param.length() > 255) {
                    icModel.setSuccess(false);
                    icModel.getErrStringList().add(titleName + "长度过长(最大255)");
                    return icModel;
                }
            }
            return icModel;
        }
    }

    /**
     * 文化程度 0:小学 1:初中 2:高中 3: 中专 4:大专 5:本科 6:大专 7:本科 8:硕士 9:博士
     */
    public class DegreeOfEducationCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!("小学".equals(param) || "初中".equals(param) || "高中".equals(param) ||
                            "中专".equals(param) || "大专".equals(param) || "本科".equals(param) ||
                            "硕士".equals(param) || "博士".equals(param))) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "必须是小学、初中、高中、中专、大专、本科、硕士、博士");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }


    public class GraduatedSchoolCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (StringUtils.isNotBlank(param) && param.length() > 255) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "长度过长(最大255)");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }


    public class ProfessionalCategory implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (StringUtils.isNotBlank(param) && param.length() > 255) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "长度过长(最大255)");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class GraduationTimeCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!EmpImportUtils.dateFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("毕业时间格式不正确");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }


    public class BankAccountCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!SysUtils.patternCheck(".{1,20}", param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "必须在1-20个字");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class BankNumCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!SysUtils.patternCheck("\\d{1,21}", param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "必须在1-21个全数字");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class SourceOfSupplyCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!SysUtils.patternCheck(".{1,20}", param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "必须在1-20个字");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class EmployeeNatureCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!SysUtils.patternCheck(".{1,20}", param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "必须在1-20个字");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class BusAddressCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                param = param.trim();
                if (StringUtils.isNotBlank(param)) {
                    if (!SysUtils.patternCheck(".{1,40}", param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add(titleName + "必须在1-40个字");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class ExpireStartTimeCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                if (StringUtils.isNotBlank(param)) {
                    param = param.trim();
                    if (!EmpImportUtils.dateFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("身份证有效期开始时间格式不对");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }

    public class ExpireEndTimeCheck implements ImportCheck {

        @Override
        public ICModel checkMethod(String titleName, String... params) {
            ICModel icModel = new ICModel();
            icModel.setSuccess(true);
            for (String param : params) {
                if (StringUtils.isNotBlank(param)) {
                    param = param.trim();
                    if ("长期".equals(param)) {
                        return icModel;
                    }
                    if (!EmpImportUtils.dateFormatCheck(param)) {
                        icModel.setSuccess(false);
                        icModel.getErrStringList().add("身份证有效期结束时间格式不对");
                        return icModel;
                    }
                }
            }
            return icModel;
        }
    }
}
