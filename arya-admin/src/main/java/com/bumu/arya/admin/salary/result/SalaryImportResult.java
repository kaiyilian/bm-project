package com.bumu.arya.admin.salary.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 薪资导入返回Bean，包括excel导入数据 excel验证信息
 * Created by bumu-zhz on 2015/11/10.
 */
public class SalaryImportResult implements Serializable{

    @JsonProperty("file_id")
    String fileId;

    @JsonProperty("excel_datas")
    private List<SalaryOutputBean> excelDatas;

    @JsonProperty("err_msgs")
    private List<SalaryErrorMsgBean> msgs;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<SalaryOutputBean> getExcelDatas() {
        return excelDatas;
    }

    public void setExcelDatas(List<SalaryOutputBean> excelDatas) {
        this.excelDatas = excelDatas;
    }

    public List<SalaryErrorMsgBean> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<SalaryErrorMsgBean> msgs) {
        this.msgs = msgs;
    }

    /**
     * 验证信息
     */
    public static class SalaryErrorMsgBean{
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
     * 薪资导入inputbean
     * Created by bumu-zhz on 2015/11/9.
     */
    public static class SalaryOutputBean {

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
         * 基本工资
         */
        private String beanSalary;

        /**
         * 工作日出勤时间(小时)
         */
        private String workdayHours;

        /**
         * 平时加班工资
         */
        private String workdayOvertimeSalary;

        /**
         * 平时加班工作时间(小时)
         */
        private String overtimeHours;

        /**
         * 休息日加班工资
         */
        private String restDayOvertimeSalary;

        /**
         * 休息日工作时间(小时)
         */
        private String restDayOvertimeHours;

        /**
         * 国假加班工资
         */
        private String legalHolidayOvertimeSalary;

        /**
         * 国假加班时间(小时)
         */
        private String legalHolidayOvertimeHours;

        /**
         * 绩效奖金
         */
        private String performanceBonus;

        /**
         * 津贴（餐补/车补/住房）
         */
        private String subsidy;

        /**
         * 事假扣款
         */
        private String casualLeaveCut;

        /**
         * 事假天数
         */
        private String casualLeaveDays;

        /**
         * 病假扣款
         */
        private String sickLeaveCut;

        /**
         * 病假天数
         */
        private String sickLeaveDays;

        /**
         * 其他扣款
         */
        private String otherCut;

        /**
         * 补款
         */
        private String repayment;

        /**
         * 社保（个人）
         */
        private String soinPersonal;

        /**
         * 公积金（个人）
         */
        private String fundPersonal;

        /**
         * 应税工资
         */
        private String taxableSalary;

        /**
         * 个税（个人）
         */
        private String personalTax;

        /**
         * 应发工资
         */
        private String grossSalary;

        /**
         * 实发工资
         */
        private String netSalary;

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

        public String getBeanSalary() {
            return beanSalary;
        }

        public void setBeanSalary(String beanSalary) {
            this.beanSalary = beanSalary;
        }

        public String getWorkdayHours() {
            return workdayHours;
        }

        public void setWorkdayHours(String workdayHours) {
            this.workdayHours = workdayHours;
        }

        public String getWorkdayOvertimeSalary() {
            return workdayOvertimeSalary;
        }

        public void setWorkdayOvertimeSalary(String workdayOvertimeSalary) {
            this.workdayOvertimeSalary = workdayOvertimeSalary;
        }

        public String getOvertimeHours() {
            return overtimeHours;
        }

        public void setOvertimeHours(String overtimeHours) {
            this.overtimeHours = overtimeHours;
        }

        public String getRestDayOvertimeSalary() {
            return restDayOvertimeSalary;
        }

        public void setRestDayOvertimeSalary(String restDayOvertimeSalary) {
            this.restDayOvertimeSalary = restDayOvertimeSalary;
        }

        public String getRestDayOvertimeHours() {
            return restDayOvertimeHours;
        }

        public void setRestDayOvertimeHours(String restDayOvertimeHours) {
            this.restDayOvertimeHours = restDayOvertimeHours;
        }

        public String getLegalHolidayOvertimeSalary() {
            return legalHolidayOvertimeSalary;
        }

        public void setLegalHolidayOvertimeSalary(String legalHolidayOvertimeSalary) {
            this.legalHolidayOvertimeSalary = legalHolidayOvertimeSalary;
        }

        public String getLegalHolidayOvertimeHours() {
            return legalHolidayOvertimeHours;
        }

        public void setLegalHolidayOvertimeHours(String legalHolidayOvertimeHours) {
            this.legalHolidayOvertimeHours = legalHolidayOvertimeHours;
        }

        public String getPerformanceBonus() {
            return performanceBonus;
        }

        public void setPerformanceBonus(String performanceBonus) {
            this.performanceBonus = performanceBonus;
        }

        public String getSubsidy() {
            return subsidy;
        }

        public void setSubsidy(String subsidy) {
            this.subsidy = subsidy;
        }

        public String getCasualLeaveCut() {
            return casualLeaveCut;
        }

        public void setCasualLeaveCut(String casualLeaveCut) {
            this.casualLeaveCut = casualLeaveCut;
        }

        public String getCasualLeaveDays() {
            return casualLeaveDays;
        }

        public void setCasualLeaveDays(String casualLeaveDays) {
            this.casualLeaveDays = casualLeaveDays;
        }

        public String getSickLeaveCut() {
            return sickLeaveCut;
        }

        public void setSickLeaveCut(String sickLeaveCut) {
            this.sickLeaveCut = sickLeaveCut;
        }

        public String getSickLeaveDays() {
            return sickLeaveDays;
        }

        public void setSickLeaveDays(String sickLeaveDays) {
            this.sickLeaveDays = sickLeaveDays;
        }

        public String getOtherCut() {
            return otherCut;
        }

        public void setOtherCut(String otherCut) {
            this.otherCut = otherCut;
        }

        public String getRepayment() {
            return repayment;
        }

        public void setRepayment(String repayment) {
            this.repayment = repayment;
        }

        public String getSoinPersonal() {
            return soinPersonal;
        }

        public void setSoinPersonal(String soinPersonal) {
            this.soinPersonal = soinPersonal;
        }

        public String getFundPersonal() {
            return fundPersonal;
        }

        public void setFundPersonal(String fundPersonal) {
            this.fundPersonal = fundPersonal;
        }

        public String getTaxableSalary() {
            return taxableSalary;
        }

        public void setTaxableSalary(String taxableSalary) {
            this.taxableSalary = taxableSalary;
        }

        public String getPersonalTax() {
            return personalTax;
        }

        public void setPersonalTax(String personalTax) {
            this.personalTax = personalTax;
        }

        public String getGrossSalary() {
            return grossSalary;
        }

        public void setGrossSalary(String grossSalary) {
            this.grossSalary = grossSalary;
        }

        public String getNetSalary() {
            return netSalary;
        }

        public void setNetSalary(String netSalary) {
            this.netSalary = netSalary;
        }
    }
}
