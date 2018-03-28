package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bumu-zhz on 2015/11/12.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoinImportResult implements Serializable{

    @JsonProperty("file_id")
    String fileId;

    private List<SoinOutputBean> data;

    @JsonProperty("err_msgs")
    private List<SoinErrorMsgBean> errMsgs;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<SoinOutputBean> getData() {
        return data;
    }

    public void setData(List<SoinOutputBean> data) {
        this.data = data;
    }

    public List<SoinErrorMsgBean> getErrMsgs() {
        return errMsgs;
    }

    public void setErrMsgs(List<SoinErrorMsgBean> errMsgs) {
        this.errMsgs = errMsgs;
    }

    /**
     * 验证信息
     */
    public static class SoinErrorMsgBean{
        /**
         * 行号
         */
        private String columnNo;

        /**
         * 错误msg
         */
        private String msg;

        public String getColumnNo() {
            return columnNo;
        }

        public void setColumnNo(String columnNo) {
            this.columnNo = columnNo;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    /**
     * 社保导入inputbean
     * Created by bumu-zhz on 2015/11/9.
     */
    public static class SoinOutputBean {

        /**
         * 姓名
         */
        private String realName;

        /**
         * 身份证
         */
        private String cardId;

        /**
         * 手机号码
         */
        private String phone;

        /**
         * 月份
         */
        private String month;

        /**
         * 社保缴纳基数
         */
        private String soinBase;

        /**
         * 公积金缴纳基数
         */
        private String houseFundBase;

        /**
         * 个人-养老
         */
        private String personalPension;

        /**
         * 个人-失业
         */
        private String personalUnemployment;

        /**
         * 个人-医疗
         */
        private String personalMedical;

        /**
         * 个人-工伤
         */
        private String personalInjury;

        /**
         * 个人-生育
         */
        private String personalPregnancy;

        /**
         * 个人-住房
         */
        private String personalHouseFund;

        /**
         * 企业-养老
         */
        private String companyPension;

        /**
         * 企业-失业
         */
        private String companyUnemployment;

        /**
         * 企业-医疗
         */
        private String companyMedical;

        /**
         * 企业-工伤
         */
        private String companyInjury;

        /**
         * 企业-生育
         */
        private String companyPregnancy;

        /**
         * 企业-住房
         */
        private String companyHouseFund;

        /**
         * 残保
         */
        private String disability;

        /**
         * 管理费
         */
        private String fees;

        /**
         * 总金额
         */
        private String totalPayment;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getSoinBase() {
            return soinBase;
        }

        public void setSoinBase(String soinBase) {
            this.soinBase = soinBase;
        }

        public String getHouseFundBase() {
            return houseFundBase;
        }

        public void setHouseFundBase(String houseFundBase) {
            this.houseFundBase = houseFundBase;
        }

        public String getPersonalPregnancy() {
            return personalPregnancy;
        }

        public void setPersonalPregnancy(String personalPregnancy) {
            this.personalPregnancy = personalPregnancy;
        }

        public String getPersonalPension() {
            return personalPension;
        }

        public void setPersonalPension(String personalPension) {
            this.personalPension = personalPension;
        }

        public String getPersonalMedical() {
            return personalMedical;
        }

        public void setPersonalMedical(String personalMedical) {
            this.personalMedical = personalMedical;
        }

        public String getPersonalUnemployment() {
            return personalUnemployment;
        }

        public void setPersonalUnemployment(String personalUnemployment) {
            this.personalUnemployment = personalUnemployment;
        }

        public String getPersonalInjury() {
            return personalInjury;
        }

        public void setPersonalInjury(String personalInjury) {
            this.personalInjury = personalInjury;
        }

        public String getPersonalHouseFund() {
            return personalHouseFund;
        }

        public void setPersonalHouseFund(String personalHouseFund) {
            this.personalHouseFund = personalHouseFund;
        }

        public String getCompanyPregnancy() {
            return companyPregnancy;
        }

        public void setCompanyPregnancy(String companyPregnancy) {
            this.companyPregnancy = companyPregnancy;
        }

        public String getCompanyPension() {
            return companyPension;
        }

        public void setCompanyPension(String companyPension) {
            this.companyPension = companyPension;
        }

        public String getCompanyMedical() {
            return companyMedical;
        }

        public void setCompanyMedical(String companyMedical) {
            this.companyMedical = companyMedical;
        }

        public String getCompanyUnemployment() {
            return companyUnemployment;
        }

        public void setCompanyUnemployment(String companyUnemployment) {
            this.companyUnemployment = companyUnemployment;
        }

        public String getCompanyInjury() {
            return companyInjury;
        }

        public void setCompanyInjury(String companyInjury) {
            this.companyInjury = companyInjury;
        }

        public String getCompanyHouseFund() {
            return companyHouseFund;
        }

        public void setCompanyHouseFund(String companyHouseFund) {
            this.companyHouseFund = companyHouseFund;
        }

        public String getDisability() {
            return disability;
        }

        public void setDisability(String disability) {
            this.disability = disability;
        }

        public String getFees() {
            return fees;
        }

        public void setFees(String fees) {
            this.fees = fees;
        }

        public String getTotalPayment() {
            return totalPayment;
        }

        public void setTotalPayment(String totalPayment) {
            this.totalPayment = totalPayment;
        }
    }
}
