package com.bumu.bran.admin.salary.result;

import com.bumu.bran.esalary.result.NewSalaryResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * majun
 */
public class SalaryResult {

    private List<NewSalaryResult> rows;
    @JsonProperty(value = "total_page")
    private int totalPage;
    @JsonProperty(value = "total_rows")
    private int totalRows;

    public List<NewSalaryResult> getRows() {
        return rows;
    }

    public void setRows(List<NewSalaryResult> rows) {
        this.rows = rows;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
