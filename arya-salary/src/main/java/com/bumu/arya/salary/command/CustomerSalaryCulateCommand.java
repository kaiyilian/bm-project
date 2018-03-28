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
public class CustomerSalaryCulateCommand {

    @ApiModelProperty(value = "客户的ID", name = "customerId")
    @NotBlank(message = "客户的ID必填")
    private String customerId;

    @ApiModelProperty(value = "结算周期(1：月  2：周期)", name = "settlementInterval")
    @NotNull
    private Integer settlementInterval;

    @ApiModelProperty(value = "年", name = "year")
    @NotNull(message = "年必填")
    private Integer year;

    @ApiModelProperty(value = "月", name = "month")
    @NotNull(message = "月必填")
    private Integer month;

    @ApiModelProperty(value = "批次", name = "week")
    private Long week;

    public void init(String customerId, Integer settlementInterval, Integer year, Integer month, Long week) {
        this.customerId = customerId;
        this.settlementInterval = settlementInterval;
        this.year = year;
        this.month = month;
        this.week = week;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getSettlementInterval() {
        return settlementInterval;
    }

    public void setSettlementInterval(Integer settlementInterval) {
        this.settlementInterval = settlementInterval;
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
