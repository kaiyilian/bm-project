package com.bumu.bran.admin.work_shift_rule.controller.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 排班按天修改
 *
 * @author majun
 * @date 2017/9/7
 * @email 351264830@qq.com
 */
@ApiModel
public class BranOneDayWorkShiftRuleCommand {

    @ApiModelProperty(value = "正式员工id")
    @NotBlank(message = "正式员工id必填")
    private String empId;
    @ApiModelProperty(value = "修改的时间 具体的某一天 时间戳")
    @NotNull(message = "修改时间必填")
    private Long modifyDate;
    @ApiModelProperty(value = "班次id")
    private String workShiftTypeId;
    @ApiModelProperty(value = "排班id")
    @NotBlank(message = "排班id必填")
    private String workShiftRuleId;
    @ApiModelProperty(value = "当前页数,局部刷新的时候有用")
    private Integer page = 1;


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getWorkShiftTypeId() {
        return workShiftTypeId;
    }

    public void setWorkShiftTypeId(String workShiftTypeId) {
        this.workShiftTypeId = workShiftTypeId;
    }

    public String getWorkShiftRuleId() {
        return workShiftRuleId;
    }

    public void setWorkShiftRuleId(String workShiftRuleId) {
        this.workShiftRuleId = workShiftRuleId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "BranOneDayWorkShiftRuleCommand{" +
                "empId='" + empId + '\'' +
                ", modifyDate=" + modifyDate +
                ", workShiftTypeId='" + workShiftTypeId + '\'' +
                ", workShiftRuleId='" + workShiftRuleId + '\'' +
                '}';
    }
}
