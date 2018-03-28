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
public class CustomerDetailUpdateCommand {

    @ApiModelProperty(value = "客户ID", name = "id")
    @NotBlank
    private String id;

    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
