package com.bumu.bran.admin.employee_import.engine;


import com.bumu.bran.admin.employee_import.engine.import_check.EmpImportCheck;
import com.bumu.engine.excelimport.model.ICCollection;
import com.bumu.engine.excelimport.model.ImportReg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业管理平台员工导入引擎
 *
 * @author majun
 * @date 2017/11/10
 * @email 351264830@qq.com
 */
@Component
public class BranCorpEmployeeImportCollection extends ICCollection {

    private String realName;
    private String idCardNo;
    private String registerAccount;
    private String prefixName;
    private String workSn;
    private String departmentName;
    private String positionName;
    private String workShiftName;
    private String workLineName;
    private String checkinTime;
    private String interviewDate;
    private String startTime;
    private String endTime;
    private String provinceName;
    private String cityName;
    private String countyName;
    private String address;
    private String marriage;
    private String sex;
    private String telephone;
    private String urgentContact;
    private String urgentContactPhone;
    private String bankAccount;
    private String bankNum;
    private String sourceOfSupply;
    private String employeeNature;
    private String busAddress;
    private String branCorpId;
    private String bornDate;
    private String politicalStatus;
    private String socialSecurityType;
    private String degreeOfEducation;
    private String graduatedSchool;
    private String professionalCategory;
    private String graduationTime;
    private String expireStartTime;
    private String expireEndTime;

    @Autowired
    private EmpImportCheck empImportCheck;

    private EmpImportCheck.RealNameCheck realNameCheck = null;
    private EmpImportCheck.NoRepeatIdCardCheck noRepeatIdCardCheck = null;
    private EmpImportCheck.NoRepeatTelCheck noRepeatTelCheck = null;
    private EmpImportCheck.PrefixNameCheck prefixNameCheck = null;
    private EmpImportCheck.WorkSnCheck workSnCheck = null;
    private EmpImportCheck.DepartmentCheck departmentCheck = null;
    private EmpImportCheck.PositionCheck positionCheck = null;
    private EmpImportCheck.WorkLineCheck workLineCheck = null;
    private EmpImportCheck.WorkShiftCheck workShiftCheck = null;
    private EmpImportCheck.CheckInTimeCheck checkInTimeCheck = null;
    private EmpImportCheck.InterviewDateCheck interviewDateCheck = null;
    private EmpImportCheck.StartTimeCheck startTimeCheck = null;
    private EmpImportCheck.EndTimeCheck endTimeCheck = null;
    private EmpImportCheck.ProvinceNameCheck provinceNameCheck = null;
    private EmpImportCheck.CityNameCheck cityNameCheck = null;
    private EmpImportCheck.CountyNameCheck countyNameCheck = null;
    private EmpImportCheck.AddressCheck addressCheck = null;
    private EmpImportCheck.MarriageCheck marriageCheck = null;
    private EmpImportCheck.SexCheck sexCheck = null;
    private EmpImportCheck.TelephoneCheck telephoneCheck = null;
    private EmpImportCheck.UrgentContactCheck urgentContactCheck = null;
    private EmpImportCheck.UrgentContactPhoneCheck urgentContactPhoneCheck = null;
    private EmpImportCheck.BankAccountCheck bankAccountCheck = null;
    private EmpImportCheck.BankNumCheck bankNumCheck = null;
    private EmpImportCheck.SourceOfSupplyCheck sourceOfSupplyCheck = null;
    private EmpImportCheck.EmployeeNatureCheck employeeNatureCheck = null;
    private EmpImportCheck.BusAddressCheck busAddressCheck = null;
    private EmpImportCheck.BornDateCheck bornDateCheck = null;
    private EmpImportCheck.PoliticalStatusCheck politicalStatusCheck = null;
    private EmpImportCheck.SocialSecurityTypeCheck socialSecurityTypeCheck = null;
    private EmpImportCheck.DegreeOfEducationCheck degreeOfEducationCheck = null;
    private EmpImportCheck.GraduatedSchoolCheck graduatedSchoolCheck = null;
    private EmpImportCheck.ProfessionalCategory professionalCategoryCheck = null;
    private EmpImportCheck.GraduationTimeCheck graduationTimeCheck = null;
    private EmpImportCheck.ExpireStartTimeCheck expireStartTimeCheck = null;
    private EmpImportCheck.ExpireEndTimeCheck expireEndTimeCheck = null;


    /**
     * 验证规则
     */
    private List<ImportReg> empImportTitleCheckRule = new ArrayList<ImportReg>();

    public BranCorpEmployeeImportCollection() {


    }

    public void init(String realName,
                     String idCardNo,
                     String registerAccount,
                     String prefixName,
                     String workSn,
                     String departmentName,
                     String positionName,
                     String workShiftName,
                     String workLineName,
                     String checkinTime,
                     String interviewDate,
                     String startTime,
                     String endTime,
                     String provinceName,
                     String cityName,
                     String countyName,
                     String address,
                     String marriage,
                     String sex,
                     String bornDate,
                     String telephone,
                     String urgentContact,
                     String urgentContactPhone,
                     String politicalStatus,
                     String socialSecurityType,
                     String degreeOfEducation,
                     String graduatedSchool,
                     String professionalCategory,
                     String graduationTime,
                     String bankAccount,
                     String bankNum,
                     String sourceOfSupply,
                     String employeeNature,
                     String busAddress,
                     String expireStartTime,
                     String expireEndTime,
                     String branCorpId) {

        realNameCheck = empImportCheck.new RealNameCheck();
        noRepeatIdCardCheck = empImportCheck.new NoRepeatIdCardCheck();
        noRepeatTelCheck = empImportCheck.new NoRepeatTelCheck();
        prefixNameCheck = empImportCheck.new PrefixNameCheck();
        workSnCheck = empImportCheck.new WorkSnCheck();
        departmentCheck = empImportCheck.new DepartmentCheck();
        positionCheck = empImportCheck.new PositionCheck();
        workLineCheck = empImportCheck.new WorkLineCheck();
        workShiftCheck = empImportCheck.new WorkShiftCheck();
        checkInTimeCheck = empImportCheck.new CheckInTimeCheck();
        interviewDateCheck = empImportCheck.new InterviewDateCheck();
        startTimeCheck = empImportCheck.new StartTimeCheck();
        endTimeCheck = empImportCheck.new EndTimeCheck();
        provinceNameCheck = empImportCheck.new ProvinceNameCheck();
        cityNameCheck = empImportCheck.new CityNameCheck();
        countyNameCheck = empImportCheck.new CountyNameCheck();
        addressCheck = empImportCheck.new AddressCheck();
        marriageCheck = empImportCheck.new MarriageCheck();
        sexCheck = empImportCheck.new SexCheck();
        telephoneCheck = empImportCheck.new TelephoneCheck();
        urgentContactCheck = empImportCheck.new UrgentContactCheck();
        urgentContactPhoneCheck = empImportCheck.new UrgentContactPhoneCheck();
        bankAccountCheck = empImportCheck.new BankAccountCheck();
        bankNumCheck = empImportCheck.new BankNumCheck();
        sourceOfSupplyCheck = empImportCheck.new SourceOfSupplyCheck();
        employeeNatureCheck = empImportCheck.new EmployeeNatureCheck();
        busAddressCheck = empImportCheck.new BusAddressCheck();

        bornDateCheck = empImportCheck.new BornDateCheck();
        politicalStatusCheck = empImportCheck.new PoliticalStatusCheck();
        socialSecurityTypeCheck = empImportCheck.new SocialSecurityTypeCheck();
        degreeOfEducationCheck = empImportCheck.new DegreeOfEducationCheck();
        graduatedSchoolCheck = empImportCheck.new GraduatedSchoolCheck();
        professionalCategoryCheck = empImportCheck.new ProfessionalCategory();
        graduationTimeCheck = empImportCheck.new GraduationTimeCheck();

        expireStartTimeCheck = empImportCheck.new ExpireStartTimeCheck();
        expireEndTimeCheck = empImportCheck.new ExpireEndTimeCheck();

        this.realName = realName;
        this.idCardNo = idCardNo;
        this.registerAccount = registerAccount;
        this.prefixName = prefixName;
        this.workSn = workSn;
        this.departmentName = departmentName;
        this.positionName = positionName;
        this.workShiftName = workShiftName;
        this.workLineName = workLineName;
        this.checkinTime = checkinTime;
        this.interviewDate = interviewDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.provinceName = provinceName;
        this.cityName = cityName;
        this.countyName = countyName;
        this.address = address;
        this.marriage = marriage;
        this.sex = sex;
        this.bornDate = bornDate;
        this.telephone = telephone;
        this.urgentContact = urgentContact;
        this.urgentContactPhone = urgentContactPhone;

        this.politicalStatus = politicalStatus;
        this.socialSecurityType = socialSecurityType;
        this.degreeOfEducation = degreeOfEducation;
        this.graduatedSchool = graduatedSchool;
        this.professionalCategory = professionalCategory;
        this.graduationTime = graduationTime;

        this.bankAccount = bankAccount;
        this.bankNum = bankNum;
        this.sourceOfSupply = sourceOfSupply;
        this.employeeNature = employeeNature;
        this.busAddress = busAddress;
        this.branCorpId = branCorpId;

        this.expireStartTime = expireStartTime;
        this.expireEndTime = expireEndTime;

        empImportTitleCheckRule.clear();

        /**
         * 姓名
         *
         * 姓名不能为空
         * 姓名不能超过8个字
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "realName",
                        realName,
                        false,
                        ImportReg.TypeEnum.STRING,
                        realNameCheck
                )
        );

        /**
         * 身份证验证
         *
         * 18位数字，最后一位可为X，自动转化为大写
         * 身份证号不能为空/花名册中已存在该身份证号/身份证号格式不对
         */
        //
        ImportReg.RegEnum idcard = ImportReg.RegEnum.IDCARD;
        idcard.setErrMessage("身份证号格式不对");
        empImportCheck.setBranCorpId(branCorpId);

        empImportTitleCheckRule.add(
                new ImportReg(
                        "idCardNo",
                        idCardNo,
                        true,
                        ImportReg.TypeEnum.STRING,
                        noRepeatIdCardCheck)
        );

        /**
         * 手机号
         *
         * 必填
         * 手机账号格式不对
         * 待入职中已存在该手机
         * 花名册中已存在该手机
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "registerAccount",
                        registerAccount,
                        true,
                        ImportReg.TypeEnum.STRING,
                        noRepeatTelCheck
                ));

        /**
         * 工号前缀
         *
         * 必填
         * 没有此前缀
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "prefixName",
                        prefixName,
                        false,
                        ImportReg.TypeEnum.STRING,
                        prefixNameCheck
                ));

        /**
         * 工号
         *
         * 必填
         * 必须是数字
         * 长度8位
         * 工号重复
         * 该工号已存在
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "workSn",
                        workSn,
                        false,
                        ImportReg.TypeEnum.STRING,
                        workSnCheck
                ));

        /**
         * 部门
         *
         * 必填
         * 没有该部门
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "departmentName",
                        departmentName,
                        false,
                        ImportReg.TypeEnum.STRING,
                        departmentCheck
                ));

        /**
         * 职位
         *
         * 必填
         * 没有该职位
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "positionName",
                        positionName,
                        false,
                        ImportReg.TypeEnum.STRING,
                        positionCheck
                ));

        /**
         * 班组
         *
         * 必填
         * 没有该班组
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "workShiftName",
                        workShiftName,
                        false,
                        ImportReg.TypeEnum.STRING,
                        workShiftCheck
                ));

        /**
         * 工段
         *
         * 必填
         * 没有该工段
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "workLineName",
                        workLineName,
                        false,
                        ImportReg.TypeEnum.STRING,
                        workLineCheck
                ));

        /**
         * 入职时间
         *
         * 入职日期格式不对
         * 入职时间不能为空
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "checkinTime",
                        checkinTime,
                        false,
                        ImportReg.TypeEnum.STRING,
                        checkInTimeCheck
                ));

        /**
         * 面试时间
         *
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "interviewDate",
                        interviewDate,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        interviewDateCheck
                ));

        /**
         * 合同开始时间
         *
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "startTime",
                        startTime,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        startTimeCheck
                ));

        /**
         * 合同结束时间
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "endTime",
                        endTime,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        endTimeCheck
                ));

        /**
         * 现居地省份
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "provinceName",
                        provinceName,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        provinceNameCheck
                ));

        /**
         * 现居地城市
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "cityName",
                        cityName,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        cityNameCheck
                ));

        /**
         * 现居地行政区
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "countyName",
                        countyName,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        countyNameCheck
                ));

        /**
         * 现居地地址
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "address",
                        address,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        addressCheck
                ));

        /**
         * 婚姻状况
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "marriage",
                        marriage,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        marriageCheck
                ));

        /**
         * 性别
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "sex",
                        sex,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        sexCheck
                ));

        /**
         * 出生年月
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "bornDate",
                        bornDate,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        bornDateCheck
                ));


        /**
         * 联系方式
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "telephone",
                        telephone,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        telephoneCheck
                ));

        /**
         * 紧急联系人
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "urgentContact",
                        urgentContact,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        urgentContactCheck
                ));

        /**
         * 紧急联系人手机
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "urgentContactPhone",
                        urgentContactPhone,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        urgentContactPhoneCheck
                ));


        /**
         * 政治面貌
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "politicalStatus",
                        politicalStatus,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        politicalStatusCheck
                ));


        /**
         * 社保类型
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "socialSecurityType",
                        socialSecurityType,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        socialSecurityTypeCheck
                ));

        /**
         * 文化程度
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "degreeOfEducation",
                        degreeOfEducation,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        degreeOfEducationCheck
                ));

        /**
         * 毕业院校
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "graduatedSchool",
                        graduatedSchool,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        graduatedSchoolCheck
                ));

        /**
         * 专业类别
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "professionalCategory",
                        professionalCategory,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        professionalCategoryCheck
                ));

        /**
         * 毕业时间
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "graduationTime",
                        graduationTime,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        graduationTimeCheck
                ));


        /**
         * 开户行信息
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "bankAccount",
                        bankAccount,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        bankAccountCheck
                ));

        /**
         * 银行卡号
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "bankNum",
                        bankNum,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        bankNumCheck
                ));

        /**
         * 供应来源
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "sourceOfSupply",
                        sourceOfSupply,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        sourceOfSupplyCheck
                ));

        /**
         * 员工性质
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "employeeNature",
                        employeeNature,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        employeeNatureCheck
                ));

        /**
         * 班车点
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "busAddress",
                        busAddress,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        busAddressCheck
                ));

        /**
         * 身份证有效期开始时间
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "expireStartTime",
                        expireStartTime,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        expireStartTimeCheck
                ));

        /**
         * 身份证有效期结束时间
         */
        empImportTitleCheckRule.add(
                new ImportReg(
                        "expireEndTime",
                        expireEndTime,
                        false,
                        false,
                        ImportReg.TypeEnum.STRING,
                        expireEndTimeCheck
                ));
    }

    public List<ImportReg> getEmpImportTitleCheckRule() {
        return empImportTitleCheckRule;
    }
}
