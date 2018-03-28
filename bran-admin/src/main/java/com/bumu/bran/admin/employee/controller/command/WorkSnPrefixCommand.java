package com.bumu.bran.admin.employee.controller.command;

import com.bumu.bran.employee.command.ModelCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * majun
 */
@ApiModel
public class WorkSnPrefixCommand extends ModelCommand {

    private String beginWorkSn;
    private int count;
    private long num;
    private String empId;
    @ApiModelProperty(value = "工号位数, 默认1位 最高8位")
    @Range(message = "工号位数范围在1-8位", min = 1, max = 8)
    @NotNull(message = "工号位数必填")
    private Integer digit;

    public String getBeginWorkSn() {
        return beginWorkSn;
    }

    public void setBeginWorkSn(String beginWorkSn) {
        this.beginWorkSn = beginWorkSn;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Integer getDigit() {
        if (digit == null) {
            return 1;
        }
        return digit;
    }

    public void setDigit(Integer digit) {
        this.digit = digit;
    }
}
