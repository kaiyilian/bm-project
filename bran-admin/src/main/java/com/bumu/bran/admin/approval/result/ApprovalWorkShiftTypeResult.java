package com.bumu.bran.admin.approval.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2017/10/25
 * @email 351264830@qq.com
 */
@ApiModel
public class ApprovalWorkShiftTypeResult {

    @ApiModelProperty
    private String id;
    @ApiModelProperty(value = "班次名字")
    private String name;
    @ApiModelProperty(value = "上班时间 08:00 ")
    private String startTime;
    @ApiModelProperty(value = "下班时间 17:00 ")
    private String endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
