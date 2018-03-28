package com.bumu.bran.admin.main.controller.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2018/1/17
 * @email 351264830@qq.com
 */
@ApiModel
public class EmpIdCardNoExpireTimeSetCommand {

    @ApiModelProperty(value = "员工id")
    private String id;
    @ApiModelProperty
    private Long start;
    @ApiModelProperty
    private Long end;

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
