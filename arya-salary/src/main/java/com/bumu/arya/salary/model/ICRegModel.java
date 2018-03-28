package com.bumu.arya.salary.model;

import com.bumu.engine.excelimport.model.ICCollection;
import com.bumu.engine.excelimport.model.ImportReg;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/25
 */
public class ICRegModel extends ICCollection {

    /**
     * 薪资导入字段验证
     *
     */
    public List<ImportReg> BUMU_SALARY_CHECK_LIST = new ArrayList<ImportReg>() {
        {
            //key 是Excel中的表头，    value是验证规则
            add(new ImportReg("name", false,  ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("district", false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("corpName", false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("name", false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("idCardNo", true, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK(), new CheckReg(ImportReg.RegEnum.IDCARD)));
            add(new ImportReg("phoneNo", true, ImportReg.TypeEnum.STRING, new CheckReg(ImportReg.RegEnum.MOBLIE)));
            add(new ImportReg("taxableSalary", false, ImportReg.TypeEnum.BIGDECIMAL, new NOT_NULL_CHECK()));
            add(new ImportReg("bankAccount", true, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("bankName", false, ImportReg.TypeEnum.STRING));
        }
    };

    public List<ImportReg> HUMANPOOL_SALARY_CHECK_LIST = new ArrayList<ImportReg>() {
        {
            add(new ImportReg("name", false,  ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("idCardNo", true, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK(), new CheckReg(ImportReg.RegEnum.IDCARD)));
            add(new ImportReg("bankAccount", true, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("bankName", false, ImportReg.TypeEnum.STRING));
            add(new ImportReg("staffStatus", false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("salaryBase", false, ImportReg.TypeEnum.BIGDECIMAL, new NOT_NULL_CHECK()));
            add(new ImportReg("salaryBaseOvertime", false, ImportReg.TypeEnum.BIGDECIMAL, new NOT_NULL_CHECK()));
            add(new ImportReg("workDays", false, ImportReg.TypeEnum.FLOAT));
            add(new ImportReg("illDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("affairDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("absenseDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("annualDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("precreateDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("marryDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("newLeaveDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("overtimeWorkDay", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("overtimeWeekend", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("overtimeNational", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("newLeaveHours", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("workHours", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("receiveSalary", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
        }
    };

    public List<ImportReg> ORDINARY_SALARY_CHECK_LIST = new ArrayList<ImportReg>() {
        {
            //key 是Excel中的表头，    value是验证规则
            add(new ImportReg("sn", false, ImportReg.TypeEnum.STRING));
            add(new ImportReg("name", false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("idCardNo", true, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK(), new CheckReg(ImportReg.RegEnum.IDCARD)));
            add(new ImportReg("bankAccount", true, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
            add(new ImportReg("bankName", false, ImportReg.TypeEnum.STRING));
            add(new ImportReg("workDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("affairDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("illDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("annualDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("marryDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("funeralDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("precreateDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("absenseDays", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
            add(new ImportReg("salaryBase", false, ImportReg.TypeEnum.FLOAT, new NOT_NULL_CHECK()));
        }
    };
}
