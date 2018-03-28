package com.bumu.arya.admin.devops.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户活跃度统计结果
 *
 * @author Allen 2018-02-27
 **/
@ApiModel
public class UserActivityResult {

    List<UserActivity> userActivities = new ArrayList<>();

    public List<UserActivity> getUserActivities() {
        return userActivities;
    }

    public void setUserActivities(List<UserActivity> userActivities) {
        this.userActivities = userActivities;
    }

    @ApiModel
    public static class UserActivity {
        String userId;

        String realName;

        String sex;

        String idcardNo;

        String phoneNo;

        @ApiModelProperty("最后发薪项目")
        String lastPayrollCorpName;

        @ApiModelProperty("最后工资单上传时间")
        String lastPayrollTime;

        long lastPayrollTimeMillis; // 最后工资单时间临时变量，用于匹配

        String corpName;

        @ApiModelProperty("App用户注册时间")
        String regTime;

        @ApiModelProperty("最后活跃时间")
        String lastVisitTime;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdcardNo() {
            return idcardNo;
        }

        public void setIdcardNo(String idcardNo) {
            this.idcardNo = idcardNo;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getLastPayrollCorpName() {
            return lastPayrollCorpName;
        }

        public void setLastPayrollCorpName(String lastPayrollCorpName) {
            this.lastPayrollCorpName = lastPayrollCorpName;
        }

        public String getLastPayrollTime() {
            return lastPayrollTime;
        }

        public void setLastPayrollTime(String lastPayrollTime) {
            this.lastPayrollTime = lastPayrollTime;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public String getLastVisitTime() {
            return lastVisitTime;
        }

        public void setLastVisitTime(String lastVisitTime) {
            this.lastVisitTime = lastVisitTime;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public long getLastPayrollTimeMillis() {
            return lastPayrollTimeMillis;
        }

        public void setLastPayrollTimeMillis(long lastPayrollTimeMillis) {
            this.lastPayrollTimeMillis = lastPayrollTimeMillis;
        }

        @Override
        public String toString() {
            return "UserActivity{" +
                    "userId='" + userId + '\'' +
                    ", realName='" + realName + '\'' +
                    ", sex='" + sex + '\'' +
                    ", idcardNo='" + idcardNo + '\'' +
                    ", phoneNo='" + phoneNo + '\'' +
                    ", lastPayrollCorpName='" + lastPayrollCorpName + '\'' +
                    ", lastPayrollTime='" + lastPayrollTime + '\'' +
                    ", corpName='" + corpName + '\'' +
                    ", lastVisitTime='" + lastVisitTime + '\'' +
                    '}';
        }
    }
}
