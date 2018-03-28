package com.bumu.arya.salary.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class CustomerAccountDeleteCommand {

    @ApiModelProperty(value = "客户ID", name = "customerId")
    @NotBlank(message = "客户ID必填")
    private String customerId;

    @ApiModelProperty(value = "合同ID", name = "contractId")
    private String contractId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
