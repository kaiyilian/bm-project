package com.bumu.arya.salary.calculate;

/**
 * 薪资计算过程中产生的异常
 * TODO
 * Created by allen on 2017/7/10.
 */
public class SalaryException {

    /**
     * 行数
     */
    int row;

    /**
     * 列数
     */
    int col;

    /**
     * 行号
     */
    String rowSn;

    /**
     * 列关键字
     */
    String colKey;

    /**
     * 列名称
     */
    String colName;

    /**
     * 提示消息
     */
    String msg;

    public SalaryException(int row, String msg) {
        this.row = row;
        this.msg = msg;
    }

    public SalaryException(int row, int col, String msg) {
        this.row = row;
        this.col = col;
        this.msg = msg;
    }

    public SalaryException(String rowSn, String colKey, String colName, String msg) {
        this.rowSn = rowSn;
        this.colKey = colKey;
        this.colName = colName;
        this.msg = msg;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getRowSn() {
        return rowSn;
    }

    public void setRowSn(String rowSn) {
        this.rowSn = rowSn;
    }

    public String getColKey() {
        return colKey;
    }

    public void setColKey(String colKey) {
        this.colKey = colKey;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
