package com.bumu.arya.admin.misc.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 用户信息-入职信息
 *
 * @author majun
 * @date 2018/1/19
 * @email 351264830@qq.com
 */
@ApiModel
public class UserEmpInfo {

    @ApiModelProperty
    private App app;
    @ApiModelProperty
    private List<Prospective> prospectives;
    @ApiModelProperty
    private List<Emp> emps;

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public List<Prospective> getProspectives() {
        return prospectives;
    }

    public void setProspectives(List<Prospective> prospectives) {
        this.prospectives = prospectives;
    }

    public List<Emp> getEmps() {
        return emps;
    }

    public void setEmps(List<Emp> emps) {
        this.emps = emps;
    }

    @ApiModel(value = "APP入职信息")
    public static class App {
        @ApiModelProperty(value = "姓名")
        private String realName;
        @ApiModelProperty(value = "身份证号")
        private String idCardNo;
        @ApiModelProperty(value = "性别 1:男 2:女")
        private Integer sex;
        @ApiModelProperty(value = "民族")
        private String nation;
        @ApiModelProperty(value = "有效期")
        private String expireTime;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }
    }

    @ApiModel(value = "待入职")
    public static class Prospective {
        @ApiModelProperty(value = "企业名称")
        private String corpName;
        @ApiModelProperty(value = "企业码")
        private String checkinCode;
        @ApiModelProperty(value = "姓名")
        private String realName;
        @ApiModelProperty(value = "手机号码")
        private String phoneNo;
        @ApiModelProperty(value = "入职时间")
        private Long checkinTime;
        @ApiModelProperty(value = "资料进度")
        private String profileProgress;
        @ApiModelProperty(value = "备注")
        private String memo;
        @ApiModelProperty(value = "同意入职")
        private Integer acceptToEmp = 0;
        @ApiModelProperty(value = "扫描确认")
        private Integer acceptOffer;
        @ApiModelProperty(value = "提交审核")
        private Integer checkinComplete;

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public String getCheckinCode() {
            return checkinCode;
        }

        public void setCheckinCode(String checkinCode) {
            this.checkinCode = checkinCode;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public Long getCheckinTime() {
            return checkinTime;
        }

        public void setCheckinTime(Long checkinTime) {
            this.checkinTime = checkinTime;
        }

        public String getProfileProgress() {
            return profileProgress;
        }

        public void setProfileProgress(String profileProgress) {
            this.profileProgress = profileProgress;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public Integer getAcceptToEmp() {
            return acceptToEmp;
        }

        public void setAcceptToEmp(Integer acceptToEmp) {
            this.acceptToEmp = acceptToEmp;
        }

        public Integer getAcceptOffer() {
            return acceptOffer;
        }

        public void setAcceptOffer(Integer acceptOffer) {
            this.acceptOffer = acceptOffer;
        }

        public Integer getCheckinComplete() {
            if (checkinComplete == null) {
                return 0;
            }
            return checkinComplete;
        }

        public void setCheckinComplete(Integer checkinComplete) {
            this.checkinComplete = checkinComplete;
        }

    }

    @ApiModel(value = "花名册列表")
    public static class Emp {
        @ApiModelProperty(value = "企业名称")
        private String corpName;
        @ApiModelProperty(value = "企业码")
        private String checkinCode;
        @ApiModelProperty(value = "姓名")
        private String realName;
        @ApiModelProperty(value = " 注册账号:手机账号 花名册导入用")
        private String phoneNo;
        @ApiModelProperty(value = "打卡号")
        private Long workAttendanceNo;
        @ApiModelProperty(value = "打卡状态 0:录入等待 1:录入失败 2:未录入 3:已录入")
        private Integer workAttendanceAddState;
        @ApiModelProperty(value = "打卡状态 0:录入等待 1:录入失败 2:未录入 3:已录入")
        private Integer isBinding;

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public String getCheckinCode() {
            return checkinCode;
        }

        public void setCheckinCode(String checkinCode) {
            this.checkinCode = checkinCode;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public Long getWorkAttendanceNo() {
            return workAttendanceNo;
        }

        public void setWorkAttendanceNo(Long workAttendanceNo) {
            this.workAttendanceNo = workAttendanceNo;
        }

        public Integer getWorkAttendanceAddState() {
            return workAttendanceAddState;
        }

        public void setWorkAttendanceAddState(Integer workAttendanceAddState) {
            this.workAttendanceAddState = workAttendanceAddState;
        }

        public Integer getIsBinding() {
            if (isBinding == null) {
                return 0;
            }
            return isBinding;
        }

        public void setIsBinding(Integer isBinding) {
            this.isBinding = isBinding;
        }
    }

}
