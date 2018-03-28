package com.bumu.arya.admin.payroll.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by liangjun on 17-7-26.
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PayrollManagerResult {

    @ApiModelProperty(value = "全部的用户")
    @JsonProperty("geteSalaryCorpUsers")
    private List<PayrollUserResult> eSalaryCorpUsers;

    @ApiModelProperty(value = "总页数")
    private int pages;

    @JsonProperty("total_rows")
    @ApiModelProperty(value = "总条数")
    private int rowCount;

    public List<PayrollUserResult> geteSalaryCorpUsers() {

        return eSalaryCorpUsers;
    }

    public void seteSalaryCorpUsers(List<PayrollUserResult> eSalaryCorpUsers) {
        this.eSalaryCorpUsers = eSalaryCorpUsers;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
