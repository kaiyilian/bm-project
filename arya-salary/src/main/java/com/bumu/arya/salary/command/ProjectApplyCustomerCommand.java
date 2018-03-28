package com.bumu.arya.salary.command;

import com.bumu.arya.Utils;
import com.bumu.arya.salary.model.entity.CustomerEntity;
import com.bumu.common.SessionInfo;
import com.bumu.function.VoConverterFunction;
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
public class ProjectApplyCustomerCommand implements VoConverterFunction.Add<CustomerEntity, SessionInfo>{

    @ApiModelProperty(value = "客户立项申请的ID", name = "projectId")
    @NotBlank(message = "立项申请的ID必填")
    private String projectId;

    @ApiModelProperty(value = "合同日期开始", name= "contractDateStart")
    @NotNull(message = "合同日期开始必填")
    private Long contractDateStart;

    @ApiModelProperty(value = "合同日期结束", name= "contractDateEnd")
    @NotNull(message = "合同日期结束必填")
    private Long contractDateEnd;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Long getContractDateStart() {
        return contractDateStart;
    }

    public void setContractDateStart(Long contractDateStart) {
        this.contractDateStart = contractDateStart;
    }

    public Long getContractDateEnd() {
        return contractDateEnd;
    }

    public void setContractDateEnd(Long contractDateEnd) {
        this.contractDateEnd = contractDateEnd;
    }

    @Override
    public void begin(CustomerEntity customerEntity, SessionInfo info) {
        customerEntity.setProjectApplyId(customerEntity.getId());
        customerEntity.setUpdateTime(System.currentTimeMillis());
        customerEntity.setUpdateUser(info.getUserId());
        customerEntity.setId(Utils.makeUUID());
        customerEntity.setCreateTime(System.currentTimeMillis());
        customerEntity.setCreateUser(info.getUserId());
        customerEntity.setContractDateStart(contractDateStart);
        customerEntity.setContractDateEnd(contractDateEnd);
    }
}
