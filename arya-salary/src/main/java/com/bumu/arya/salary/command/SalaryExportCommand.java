package com.bumu.arya.salary.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class SalaryExportCommand {

    @ApiModelProperty(value = "查询条件", name = "condition")
    private String condition;

    @ApiModelProperty(value = "客户的ID", name = "customerId")
    private String customerId;

    @ApiModelProperty(value = "年", name = "year")
    @NotNull(message = "年必填")
    private Integer year;

    @ApiModelProperty(value = "月", name = "month")
    @NotNull(message = "月必填")
    private Integer month;

    @ApiModelProperty(value = "批次", name = "week")
    private Long week;

    @ApiModelProperty(value = "导出分类（1.导出薪资数据 2.导出统计结果 3.导出开票申请 4.导出发票回执单）", name = "exportType")
    @NotNull(message = "导出分类必填")
    private Integer exportType;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }


}
