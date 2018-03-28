package com.bumu.arya.salary.command;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
public class SalaryServiceCommand {

    @ApiModelProperty(value = "年份", name = "year")
    @NotNull(message = "年份必填")
    private Integer year;

    @ApiModelProperty(value = "月份", name = "month")
    @NotNull(message = "月份必填")
    private Integer month;

    @ApiModelProperty(value = "客户ID", name = "custoemrId")
    @NotBlank(message = "客户ID必填")
    private String customerId;

    @ApiModelProperty(value = "批次", name = "week")
    private Long week;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }
}
