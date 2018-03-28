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
public class ErrLogDeleteCommand {

    @ApiModelProperty(value = "选中错误日志ID", name = "salaryIds")
    @NotEmpty(message = "选中错误日志ID不能为空")
    private List<String> salaryIds;

    public List<String> getSalaryIds() {
        return salaryIds;
    }

    public void setSalaryIds(List<String> salaryIds) {
        this.salaryIds = salaryIds;
    }
}
