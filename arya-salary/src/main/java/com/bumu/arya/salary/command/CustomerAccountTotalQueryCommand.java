package com.bumu.arya.salary.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class CustomerAccountTotalQueryCommand {

    @ApiModelProperty(value = "开始时间", name = "startTime")
    @NotNull(message = "开始时间必填")
    private Long startTime;

    @ApiModelProperty(value = "结束时间", name = "endTime")
    @NotNull(message = "台账记录D必填")
    private Long endTime;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
