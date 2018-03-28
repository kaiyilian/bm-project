package com.bumu.arya.salary.common;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
public interface SalaryEnum {

    String SALARY_CALCULATE_EXPORT_PATH = "salary_calculate_export/";//薪资计算导出文件目录

    String SALARY_CALCULATE_BILL_APPLY_PATH = "salary_bill_apply_export/";//开票申请导出文件目录

    String SALARY_CALCULATE_BILL_BACK_PATH = "salary_bill_back_export/";//开票回执

    String CUSTOMER_ACCOUNT_EXPORT_PATH = "customer_account_export/";//客户台账导出文件目录

    String SALARY_ERRORLOG_EXPORT_PATH = "salary_errlog_export/";//薪资导入错误日志导出文件目录

    String CUSTOMER_EXPORT_PATH = "customer_export/";

    String SALARY_BILL_EXPORT_PATH = "salary_bill_export/";

    String ACCOUNT_TOTAL_EXPORT_PATH = "account_total_export/";

    /**
     * 发票类型
     */
    enum BillType {

        /**
         * 全额专票
         */
        fullFare,
        /**
         * 差额专票
         */
        balanceFare,

        /**
         * 全额普票
         */
        fullSheet,

        /**
         * 差额专票
         */
        balanceSheet

    }

    enum BillProject {
        /**
         * 工资
         */
        salary(1, "工资"),
        /**
         * 劳务费
         */
        work(2, "劳务费"),
        /**
         * 管理费
         */
        manager(3, "管理费"),
        /**
         * 服务费
         */
        service(4, "服务费"),
        /**
         * 个税
         */
        personTax(5, "个税"),
        /**
         * 其他
         */
        other(6, "其他"),

        /**
         *
         */
        isNull(0, "无");

        BillProject(Integer key, String name) {
            this.key = key;
            this.name = name;
        }

        private Integer key;
        private String name;

        /**
         * 通过KEY找BillProject
         * @param key
         * @return
         */
        public static BillProject getByKey(Integer key) {
            BillProject[] list = BillProject.values();
            for (BillProject billProject : list) {
                if (billProject.getKey() == key) {
                    return billProject;
                }
            }
            return null;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum RuleType {
        /**
         * 自定义
         */
        defined,
        /**
         * 标准
         */
        standard,
        /**
         * 蓝领
         */
        humanPool,
        /**
         * 普通
         */
        ordinary
    }

    enum CostBearing {
        company,personal
    }

    enum LogType{
        salaryRule
    }

    enum exportType{
        salaryList(1, "导出", SALARY_CALCULATE_EXPORT_PATH),
        count(2, "导出", SALARY_CALCULATE_EXPORT_PATH),
        billApply(3, "开票申请", SALARY_CALCULATE_BILL_APPLY_PATH),
        billBack(4, "发票回执", SALARY_CALCULATE_BILL_BACK_PATH),
        errLog(5, "错误日志", SALARY_ERRORLOG_EXPORT_PATH),
        customerAccount(6, "客户台账", CUSTOMER_ACCOUNT_EXPORT_PATH),
        customer(7, "客户资料", CUSTOMER_EXPORT_PATH),
        salaryBill(8, "开票记录", SALARY_BILL_EXPORT_PATH),
        accountTotal(9, "台账汇总", ACCOUNT_TOTAL_EXPORT_PATH);
        
        exportType(Integer value, String name, String path) {
            this.value = value;
            this.name = name;
            this.path = path;
        }
        
        private Integer value;

        private String name;

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }}

