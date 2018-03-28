package com.bumu.arya.salary.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class SalaryDeleteCommand {

    @ApiModelProperty(value = "客户的ID", name = "customerId")
    @NotBlank(message = "客户的ID必填")
    private String customerId;

    @ApiModelProperty(value = "年", name = "year")
    @NotNull(message = "年必填")
    private Integer year;

    @ApiModelProperty(value = "月", name = "month")
    @NotNull(message = "月必填")
    private Integer month;

    @ApiModelProperty
    private Long week;

    @ApiModelProperty(value = "选中薪资ID", name = "salaryIds")
    @NotEmpty(message = "选中薪资ID不能为空")
    private List<String> salaryIds;

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

    public List<String> getSalaryIds() {
        return salaryIds;
    }

    public void setSalaryIds(List<String> salaryIds) {
        this.salaryIds = salaryIds;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }
}
