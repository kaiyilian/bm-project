package com.bumu.bran.admin.employee.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 导入带入职员工
 *
 * @author majun
 */
@ApiModel
public class ImportProspectiveResult {

    private String file_id;
    private List<Prospective> employees;
    private Integer total_count;
    private boolean hasError;
    private String fileTypeStr;
    @JsonProperty(value = "problems_count")
    private int problemsCount;


    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public List<Prospective> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Prospective> employees) {
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

    public void calculateProblemsCount() {
        if (employees == null || employees.isEmpty()) {
            this.problemsCount = 0;
            return;
        }
        for (Prospective emp : employees) {
            if (emp.isHasError()) {
                problemsCount++;
            }
        }
    }

    public class Prospective {
        private Params employee_name;
        private Params position_name;
        private Params work_shift_name;
        private Params work_line_name;
        private Params department_name;
        private Params phone_no;
        private Params check_in_time;

        private boolean hasError;

        public Params getEmployee_name() {
            return employee_name;
        }

        public void setEmployee_name(Params employee_name) {
            this.employee_name = employee_name;
        }

        public Params getPosition_name() {
            return position_name;
        }

        public void setPosition_name(Params position_name) {
            this.position_name = position_name;
        }

        public Params getWork_shift_name() {
            return work_shift_name;
        }

        public void setWork_shift_name(Params work_shift_name) {
            this.work_shift_name = work_shift_name;
        }

        public Params getWork_line_name() {
            return work_line_name;
        }

        public void setWork_line_name(Params work_line_name) {
            this.work_line_name = work_line_name;
        }

        public Params getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(Params department_name) {
            this.department_name = department_name;
        }

        public Params getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(Params phone_no) {
            this.phone_no = phone_no;
        }

        public Params getCheck_in_time() {
            return check_in_time;
        }

        public void setCheck_in_time(Params check_in_time) {
            this.check_in_time = check_in_time;
        }

        public boolean isHasError() {
            return hasError;
        }

        public void setHasError(boolean hasError) {
            this.hasError = hasError;
        }
    }

    @ApiModel
    public class Employee {
        @ApiModelProperty(name = "姓名")
        private Params realName;
        @ApiModelProperty(name = "身份证号")
        private Params idCardNo;
        @ApiModelProperty(name = "注册账号")
        private Params registerAccount;
        @ApiModelProperty(name = "工号前缀")
        private Params prefixName;
        @ApiModelProperty(name = "工号")
        private Params workSn;
        @ApiModelProperty(name = "部门")
        private Params departmentName;
        @ApiModelProperty(name = "职位")
        private Params positionName;
        @ApiModelProperty(name = "班组")
        private Params workShiftName;
        @ApiModelProperty(name = "工段")
        private Params workLineName;
        @ApiModelProperty(name = "入职日期")
        private Params checkinTime;
        @ApiModelProperty(name = "面试日期")
        private Params interviewDate;
        @ApiModelProperty(name = "合同开始时间")
        private Params startTime;
        @ApiModelProperty(name = "合同结束时间")
        private Params endTime;
        @ApiModelProperty(name = "现居地省份")
        private Params addProvinceName;
        @ApiModelProperty(name = "现居地城市")
        private Params addCityName;
        @ApiModelProperty(name = "现居地行政区")
        private Params addCountyName;
        @ApiModelProperty(name = "现居地地址")
        private Params address;
        @ApiModelProperty(name = "婚姻状况")
        private Params marriage;
        @ApiModelProperty(name = "性别")
        private Params sex;
        @ApiModelProperty(name = "联系方式")
        private Params telephone;
        @ApiModelProperty(name = "紧急联系人")
        private Params urgentContact;
        @ApiModelProperty(name = "紧急联系人手机")
        private Params urgentContactPhone;
        @ApiModelProperty(name = "开户行信息")
        private Params bankAccount;
        @ApiModelProperty(name = "银行卡号")
        private Params bankNum;
        @ApiModelProperty(name = "供应来源")
        private Params sourceOfSupply;
        @ApiModelProperty(name = "员工性质")
        private Params employeeNature;
        @ApiModelProperty(name = "班车点")
        private Params busAddress;

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

        public Params getAddProvinceName() {
            return addProvinceName;
        }

        public void setAddProvinceName(Params addProvinceName) {
            this.addProvinceName = addProvinceName;
        }

        public Params getAddCityName() {
            return addCityName;
        }

        public void setAddCityName(Params addCityName) {
            this.addCityName = addCityName;
        }

        public Params getAddCountyName() {
            return addCountyName;
        }

        public void setAddCountyName(Params addCountyName) {
            this.addCountyName = addCountyName;
        }

        public Params getAddress() {
            return address;
        }

        public void setAddress(Params address) {
            this.address = address;
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
    }


    public class Params {

        private String value;
        private int flag;
        private String err;
        private String tipMsg;


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
    }


}
