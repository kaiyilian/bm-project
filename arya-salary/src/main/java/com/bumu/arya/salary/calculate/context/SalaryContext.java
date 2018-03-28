package com.bumu.arya.salary.calculate.context;

import com.bumu.arya.salary.calculate.model.InfoModel;

/**
 * 薪资计算引擎的上下文，在整个引擎内部传递
 * Created by allen on 2017/7/6.
 */
public class SalaryContext {

    SalaryConfig salaryConfig;

    /**
     * 保存计算过程中的任何信息
     */
    InfoModel infoModel = new InfoModel();

    /**
     * 计算当前所处的行数（临时方案，需要修改，避免处理过程中被修改）
     */
    int currentRow = 0;

    public SalaryConfig getSalaryConfig() {
        return salaryConfig;
    }

    public void setSalaryConfig(SalaryConfig salaryConfig) {
        this.salaryConfig = salaryConfig;
    }

    public InfoModel getInfoModel() {
        return infoModel;
    }

    public void setInfoModel(InfoModel infoModel) {
        this.infoModel = infoModel;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
}
