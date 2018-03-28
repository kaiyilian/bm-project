package com.bumu.bran.admin.employee.result;

import com.bumu.SysUtils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.admin.employee_import.result.EmpImportColResult;
import com.bumu.bran.employee.command.UserDefinedCommand;
import com.bumu.bran.employee.model.UserDefinedTran;
import com.bumu.bran.employee.model.dao.UserDefinedColsDao;
import com.bumu.bran.employee.model.entity.UserDefinedColsEntity;
import com.bumu.bran.validated.constraint.CheckUserDefinedType;
import com.bumu.common.util.ListUtils;
import com.bumu.engine.excelimport.model.ICModel;
import com.bumu.exception.AryaServiceException;
import com.bumu.function.VoConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 导入正式员工
 *
 * @author majun
 */
@ApiModel
public class ImportEmployeeResult implements VoConverter<List<ICModel>> {

    public static List<String> requireTitle = new ArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(ImportEmployeeResult.class);

    static {

        requireTitle.add("realName");
        requireTitle.add("idCardNo");
        requireTitle.add("registerAccount");
        requireTitle.add("prefixName");
        requireTitle.add("workSn");
        requireTitle.add("departmentName");
        requireTitle.add("positionName");
        requireTitle.add("workShiftName");
        requireTitle.add("workLineName");
        requireTitle.add("checkinTime");
    }

    public List<String> optionalTitle = new ArrayList<>();
    public List<String> allTitle = new ArrayList<>();
    @JsonIgnore
    private UserDefinedColsDao userDefinedColsDao;
    @JsonIgnore
    private CheckUserDefinedType checkUserDefinedType;
    @ApiModelProperty
    private String file_id;
    @ApiModelProperty
    private List<Employee> employees;
    @ApiModelProperty
    private Integer total_count;
    @ApiModelProperty
    private boolean hasError;
    @ApiModelProperty
    private String fileTypeStr;
    @ApiModelProperty
    @JsonProperty(value = "problems_count")
    private int problemsCount;
    @JsonIgnore
    private String branCorpId;

    {
        optionalTitle.add("interviewDate");
        optionalTitle.add("startTime");
        optionalTitle.add("endTime");
        optionalTitle.add("provinceName");
        optionalTitle.add("cityName");
        optionalTitle.add("countyName");
        optionalTitle.add("address");
        optionalTitle.add("marriage");
        optionalTitle.add("sex");

        optionalTitle.add("bornDate");

        optionalTitle.add("telephone");
        optionalTitle.add("urgentContact");
        optionalTitle.add("urgentContactPhone");
        optionalTitle.add("bankAccount");
        optionalTitle.add("bankNum");
        optionalTitle.add("sourceOfSupply");
        optionalTitle.add("employeeNature");
        optionalTitle.add("busAddress");

        optionalTitle.add("politicalStatus");
        optionalTitle.add("socialSecurityType");
        optionalTitle.add("degreeOfEducation");
        optionalTitle.add("graduatedSchool");
        optionalTitle.add("professionalCategory");
        optionalTitle.add("graduationTime");
        optionalTitle.add("expireStartTime");
        optionalTitle.add("expireEndTime");


        allTitle.addAll(requireTitle);

    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getFileTypeStr() {
        return fileTypeStr;
    }

    public void setFileTypeStr(String fileTypeStr) {
        this.fileTypeStr = fileTypeStr;
    }

    public int getProblemsCount() {
        return problemsCount;
    }

    public String getBranCorpId() {
        return branCorpId;
    }

    public void setBranCorpId(String branCorpId) {
        this.branCorpId = branCorpId;
    }

    public UserDefinedColsDao getUserDefinedColsDao() {
        return userDefinedColsDao;
    }

    public void setUserDefinedColsDao(UserDefinedColsDao userDefinedColsDao) {
        this.userDefinedColsDao = userDefinedColsDao;
    }

    public CheckUserDefinedType getCheckUserDefinedType() {
        return checkUserDefinedType;
    }

    public void setCheckUserDefinedType(CheckUserDefinedType checkUserDefinedType) {
        this.checkUserDefinedType = checkUserDefinedType;
    }

    public void calculateProblemsCount() {
        if (employees == null || employees.isEmpty()) {
            this.problemsCount = 0;
            return;
        }
        for (Employee emp : employees) {
            if (emp.isHasError()) {
                problemsCount++;
            }
        }
    }

    @Override
    public void convert(List<ICModel> list) {
        if (!ListUtils.checkNullOrEmpty(list)) {
            ImportEmployeeResult.Employee employee = new Employee();
            for (ICModel icModel : list) {
                employee.convert(icModel);
            }
        }
    }

    @ApiModel
    public class Employee implements VoConverter<ICModel>, Comparable<Employee> {
        /**
         * 花名册自定义字段
         */
        List<Params> userDefined = new ArrayList<>();
        /**
         * 页面显示排序用
         */
        @JsonIgnore
        private Integer index;
        @ApiModelProperty(value = "姓名")
        private Params realName;
        @ApiModelProperty(value = "身份证号")
        private Params idCardNo;
        @ApiModelProperty(value = "注册账号")
        private Params registerAccount;
        @ApiModelProperty(value = "工号前缀")
        private Params prefixName;
        @ApiModelProperty(value = "工号")
        private Params workSn;
        @ApiModelProperty(value = "部门")
        private Params departmentName;
        @ApiModelProperty(value = "职位")
        private Params positionName;
        @ApiModelProperty(value = "班组")
        private Params workShiftName;
        @ApiModelProperty(value = "工段")
        private Params workLineName;
        @ApiModelProperty(value = "入职日期")
        private Params checkinTime;
        @JsonIgnore
        private Params interviewDate;
        @JsonIgnore
        private Params startTime;
        @JsonIgnore
        private Params endTime;
        @JsonIgnore
        private Params provinceName;
        @JsonIgnore
        private Params cityName;
        @JsonIgnore
        private Params countyName;
        @JsonIgnore
        private Params address;
        @JsonIgnore
        private Params marriage;
        @JsonIgnore
        private Params sex;
        @JsonIgnore
        private Params bornDate;
        @JsonIgnore
        private Params telephone;
        @JsonIgnore
        private Params urgentContact;
        @JsonIgnore
        private Params urgentContactPhone;
        @JsonIgnore
        private Params bankAccount;
        @JsonIgnore
        private Params bankNum;
        @JsonIgnore
        private Params sourceOfSupply;
        @JsonIgnore
        private Params employeeNature;
        @JsonIgnore
        private Params busAddress;
        @JsonIgnore
        private Params politicalStatus;
        @JsonIgnore
        private Params socialSecurityType;
        @JsonIgnore
        private Params degreeOfEducation;
        @JsonIgnore
        private Params graduatedSchool;
        @JsonIgnore
        private Params professionalCategory;
        @JsonIgnore
        private Params graduationTime;
        @JsonIgnore
        private Params expireStartTime;
        @JsonIgnore
        private Params expireEndTime;
        @ApiModelProperty(value = "其他错误")
        private Params other;
        private boolean hasError;

        public Params getExpireStartTime() {
            return expireStartTime;
        }

        public void setExpireStartTime(Params expireStartTime) {
            this.expireStartTime = expireStartTime;
        }

        public Params getExpireEndTime() {
            return expireEndTime;
        }

        public void setExpireEndTime(Params expireEndTime) {
            this.expireEndTime = expireEndTime;
        }

        public boolean isHasError() {
            return hasError;
        }

        public void setHasError(boolean hasError) {
            this.hasError = hasError;
        }

        public Params getRealName() {
            return realName;
        }

        public void setRealName(Params realName) {
            this.realName = realName;
        }

        public Params getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(Params idCardNo) {
            this.idCardNo = idCardNo;
        }

        public Params getRegisterAccount() {
            return registerAccount;
        }

        public void setRegisterAccount(Params registerAccount) {
            this.registerAccount = registerAccount;
        }

        public Params getPrefixName() {
            return prefixName;
        }

        public void setPrefixName(Params prefixName) {
            this.prefixName = prefixName;
        }

        public Params getWorkSn() {
            return workSn;
        }

        public void setWorkSn(Params workSn) {
            this.workSn = workSn;
        }

        public Params getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(Params departmentName) {
            this.departmentName = departmentName;
        }

        public Params getPositionName() {
            return positionName;
        }

        public void setPositionName(Params positionName) {
            this.positionName = positionName;
        }

        public Params getWorkShiftName() {
            return workShiftName;
        }

        public void setWorkShiftName(Params workShiftName) {
            this.workShiftName = workShiftName;
        }

        public Params getWorkLineName() {
            return workLineName;
        }

        public void setWorkLineName(Params workLineName) {
            this.workLineName = workLineName;
        }

        public Params getCheckinTime() {
            return checkinTime;
        }

        public void setCheckinTime(Params checkinTime) {
            this.checkinTime = checkinTime;
        }

        public Params getOther() {
            return other;
        }

        public void setOther(Params other) {
            this.other = other;
        }

        public Params getInterviewDate() {
            return interviewDate;
        }

        public void setInterviewDate(Params interviewDate) {
            this.interviewDate = interviewDate;
        }

        public Params getStartTime() {
            return startTime;
        }

        public void setStartTime(Params startTime) {
            this.startTime = startTime;
        }

        public Params getEndTime() {
            return endTime;
        }

        public void setEndTime(Params endTime) {
            this.endTime = endTime;
        }

        public Params getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(Params provinceName) {
            this.provinceName = provinceName;
        }

        public Params getCityName() {
            return cityName;
        }

        public void setCityName(Params cityName) {
            this.cityName = cityName;
        }

        public Params getCountyName() {
            return countyName;
        }

        public void setCountyName(Params countyName) {
            this.countyName = countyName;
        }

        public Params getMarriage() {
            return marriage;
        }

        public void setMarriage(Params marriage) {
            this.marriage = marriage;
        }

        public Params getSex() {
            return sex;
        }

        public void setSex(Params sex) {
            this.sex = sex;
        }

        public Params getTelephone() {
            return telephone;
        }

        public void setTelephone(Params telephone) {
            this.telephone = telephone;
        }

        public Params getUrgentContact() {
            return urgentContact;
        }

        public void setUrgentContact(Params urgentContact) {
            this.urgentContact = urgentContact;
        }

        public Params getUrgentContactPhone() {
            return urgentContactPhone;
        }

        public void setUrgentContactPhone(Params urgentContactPhone) {
            this.urgentContactPhone = urgentContactPhone;
        }

        public Params getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(Params bankAccount) {
            this.bankAccount = bankAccount;
        }

        public Params getBankNum() {
            return bankNum;
        }

        public void setBankNum(Params bankNum) {
            this.bankNum = bankNum;
        }

        public Params getSourceOfSupply() {
            return sourceOfSupply;
        }

        public void setSourceOfSupply(Params sourceOfSupply) {
            this.sourceOfSupply = sourceOfSupply;
        }

        public Params getEmployeeNature() {
            return employeeNature;
        }

        public void setEmployeeNature(Params employeeNature) {
            this.employeeNature = employeeNature;
        }

        public Params getBusAddress() {
            return busAddress;
        }

        public void setBusAddress(Params busAddress) {
            this.busAddress = busAddress;
        }

        public Params getAddress() {
            return address;
        }

        public void setAddress(Params address) {
            this.address = address;
        }

        public Params getBornDate() {
            return bornDate;
        }

        public void setBornDate(Params bornDate) {
            this.bornDate = bornDate;
        }

        public Params getPoliticalStatus() {
            return politicalStatus;
        }

        public void setPoliticalStatus(Params politicalStatus) {
            this.politicalStatus = politicalStatus;
        }

        public Params getSocialSecurityType() {
            return socialSecurityType;
        }

        public void setSocialSecurityType(Params socialSecurityType) {
            this.socialSecurityType = socialSecurityType;
        }

        public Params getDegreeOfEducation() {
            return degreeOfEducation;
        }

        public void setDegreeOfEducation(Params degreeOfEducation) {
            this.degreeOfEducation = degreeOfEducation;
        }

        public Params getProfessionalCategory() {
            return professionalCategory;
        }

        public void setProfessionalCategory(Params professionalCategory) {
            this.professionalCategory = professionalCategory;
        }

        public Params getGraduationTime() {
            return graduationTime;
        }

        public void setGraduationTime(Params graduationTime) {
            this.graduationTime = graduationTime;
        }

        public Params getGraduatedSchool() {
            return graduatedSchool;
        }

        public void setGraduatedSchool(Params graduatedSchool) {
            this.graduatedSchool = graduatedSchool;
        }

        public List<Params> getUserDefined() {
            return userDefined;
        }

        public void setUserDefined(List<Params> userDefined) {
            this.userDefined = userDefined;
        }

        public Integer getIndex() {
            if (index == null) {
                return 0;
            }
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        @Override
        public void convert(ICModel icModel) {
            try {
                // 解析出错的列
                List<ICModel.ErrorMessageModel> errorMessageModels = icModel.getErrorMessageModels();
                Map<String, ICModel.ErrorMessageModel> errorMsg = new HashedMap();
                if (!ListUtils.checkNullOrEmpty(errorMessageModels)) {
                    hasError = true;
                }
                for (ICModel.ErrorMessageModel errorMessageModel : errorMessageModels) {
                    errorMsg.put(errorMessageModel.getKey(), errorMessageModel);
                }

                ICModel.ImportData extraJsonValue = icModel.getData().get("extra");
                List<EmpImportColResult> extraList = new ArrayList<>();
                if (extraJsonValue != null && StringUtils.isNotBlank(extraJsonValue.getValue())) {
                    logger.info("extraJsonValue: " + extraJsonValue.getValue());
                    extraList = SysUtils.jsonTo(extraJsonValue.getValue(), new TypeReference<List<EmpImportColResult>>() {
                    });
                }

                Map<String, EmpImportColResult> extraMap = new HashedMap();
                if (!ListUtils.checkNullOrEmpty(extraList)) {
                    extraList.forEach(e -> {
                        extraMap.put(e.getTitle(), e);
                        Params params = new Params();
                        params.setName(e.getTitle());
                        params.setValue(e.getValue());
                        userDefined.add(params);
                    });


                }

                if (getOther() == null) {
                    for (String title : optionalTitle) {
                        ICModel.ErrorMessageModel errorMessageModel = errorMsg.get(title);
                        if (errorMessageModel != null) {
                            Params params = new Params();
                            params.setValue(errorMessageModel.getValue());
                            params.setFlag(1);
                            params.setErr(errorMessageModel.getMessage());
                            setOther(params);
                            break;
                        }
                        if (extraMap.containsKey(title)) {
                            EmpImportColResult extra = extraMap.get(title);
                            if (StringUtils.isBlank(extra.getValue())) {
                                continue;
                            }
                            UserDefinedCommand userDefinedCommand = new UserDefinedCommand();
                            userDefinedCommand.setBranCorpId(getBranCorpId());
                            userDefinedCommand.setColName(title);
                            UserDefinedColsEntity userDefinedColsEntity = (UserDefinedColsEntity) userDefinedColsDao.findByColNameAndCorpId(userDefinedCommand).uniqueResult();
                            if (userDefinedColsEntity == null) {
                                continue;
                            }

                            UserDefinedTran userDefinedTran = new UserDefinedTran();
                            userDefinedTran.setColValue(extra.getValue());
                            userDefinedTran.setType(userDefinedColsEntity.getType());
                            // 开启字符串日期验证
                            checkUserDefinedType.setDateType(1);
                            if (!checkUserDefinedType.check(userDefinedTran)) {
                                Params params = new Params();
                                params.setValue(extra.getValue());
                                params.setFlag(1);
                                params.setErr("花名册自定义字段" + userDefinedColsEntity.getColName() + ": 类型错误");
                                setOther(params);
                                break;
                            }
                        }
                    }
                }


                for (String title : allTitle) {
                    ICModel.ImportData importData = icModel.getData().get(title);
                    ImportEmployeeResult.Params params = new Params();
                    if (importData == null) {
                        continue;
                    }
                    logger.debug("importData: " + importData.toString());
                    // 设置错误信息
                    if (errorMsg.containsKey(title)) {
                        params.setErr(errorMsg.get(title).getMessage());
                        params.setFlag(1);
                    }
                    params.setValue(importData.getValue());

                    PropertyUtils.setProperty(this, title, params);
                }
                Employee employee = new Employee();
                SysUtils.copyProperties(employee, this);
                employee.setIndex(icModel.getRow());
                employees.add(employee);

            } catch (Exception e) {
                e.printStackTrace();
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "解析出错");
            }

        }

        @Override
        public int compareTo(Employee o) {
            if (o == null) {
                return 0;
            }
            return getIndex() - o.getIndex();
        }
    }


    @ApiModel
    public class Params implements VoConverter<ICModel.ImportData> {
        @ApiModelProperty
        private String value;
        private int flag;
        @ApiModelProperty
        private String err;
        @ApiModelProperty
        private String tipMsg;
        private String name;


        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getErr() {
            return err;
        }

        public void setErr(String err) {
            this.err = err;
        }

        public String getTipMsg() {
            return tipMsg;
        }

        public void setTipMsg(String tipMsg) {
            this.tipMsg = tipMsg;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void convert(ICModel.ImportData importData) {
            if (hasError) {
                setFlag(1);
                setErr(importData.getValue());
                setValue(importData.getTitle());
                return;
            }

            setFlag(0);
            setValue(importData.getValue());
        }
    }


}
