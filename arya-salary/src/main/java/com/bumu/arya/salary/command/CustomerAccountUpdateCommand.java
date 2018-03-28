package com.bumu.arya.salary.command;

import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.common.SessionInfo;
import com.bumu.function.EntityConverter;
import com.bumu.function.VoConverterFunction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class CustomerAccountUpdateCommand implements EntityConverter<CustomerAccountEntity>,VoConverterFunction.Add<CustomerAccountEntity, SessionInfo>{

    @ApiModelProperty(value = "台账记录ID", name = "id")
    @NotBlank(message = "台账记录D必填")
    private String id;

    @ApiModelProperty(value = "开票金额", name = "billAmount")
    @NotBlank(message = "开票金额必填")
    private String billAmount;

    @ApiModelProperty(value = "备注", name = "remark")
    @NotBlank(message = "备注")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public void begin(CustomerAccountEntity customerAccountEntity, SessionInfo info) {
        customerAccountEntity.setBillAmount(billAmount);
        customerAccountEntity.setRemark(remark);
        customerAccountEntity.setUpdateTime(System.currentTimeMillis());
        customerAccountEntity.setUpdateUser(info.getUserId());
    }
}
