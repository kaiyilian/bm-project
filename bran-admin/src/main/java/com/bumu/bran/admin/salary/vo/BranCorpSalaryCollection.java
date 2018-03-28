package com.bumu.bran.admin.salary.vo;


import com.bumu.engine.excelimport.model.ICCollection;
import com.bumu.engine.excelimport.model.ImportReg;

import java.util.ArrayList;
import java.util.List;

/**
 * 企业管理平台薪资导入验证规则
 *
 * @author majun
 * @date 2017/8/21
 * @email 351264830@qq.com
 */
public class BranCorpSalaryCollection extends ICCollection {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCardNo;
    /**
     * 手机号码
     */
    private String tel;
    /**
     * 年
     */
    private String year;
    /**
     * 月
     */
    private String month;
    /**
     * 实发薪资
     */
    private String netSalary;
    /**
     * 验证规则
     */
    private List<ImportReg> eSalaryTitleCheckRule = new ArrayList<ImportReg>();

    public BranCorpSalaryCollection(String name, String idCardNo, String tel, String year, String month, String netSalary) {
        this.name = name;
        this.idCardNo = idCardNo;
        this.tel = tel;
        this.year = year;
        this.month = month;
        this.netSalary = netSalary;

        eSalaryTitleCheckRule.add(new ImportReg("name", name, false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
        eSalaryTitleCheckRule.add(new ImportReg("idCardNo", idCardNo, true, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK(), new CheckReg(ImportReg.RegEnum.IDCARD)));
        eSalaryTitleCheckRule.add(new ImportReg("tel", tel, true, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK(), new CheckReg(ImportReg.RegEnum.MOBLIE)));
        eSalaryTitleCheckRule.add(new ImportReg("year", year, false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
        eSalaryTitleCheckRule.add(new ImportReg("month", month, false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));
        eSalaryTitleCheckRule.add(new ImportReg("netSalary", netSalary, false, ImportReg.TypeEnum.STRING, new NOT_NULL_CHECK()));

    }

    public List<ImportReg> geteSalaryTitleCheckRule() {
        return eSalaryTitleCheckRule;
    }
}
