package com.bumu.arya.salary.command;

import com.bumu.arya.Utils;
import com.bumu.arya.common.Constants;
import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.arya.salary.model.entity.CustomerFollowEntity;
import com.bumu.common.SessionInfo;
import com.bumu.function.EntityConverter;
import com.bumu.function.VoConverterFunction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class CustomerRechargeCommand implements EntityConverter<CustomerAccountEntity>,VoConverterFunction.Add<CustomerAccountEntity, SessionInfo>{

    @ApiModelProperty(value = "客户的ID", name = "customerId")
    @NotBlank(message = "客户的ID必填")
    private String customerId;

    @ApiModelProperty(value = "充值金额", name = "money")
    @NotNull(message = "充值金额必填")
    private BigDecimal money;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public void begin(CustomerAccountEntity customerAccountEntity, SessionInfo info) {
        customerAccountEntity.setTransAccountAmount(money.toString());
        customerAccountEntity.setTransAccountDate(Long.parseLong(Utils.currentTimestamp()));
        customerAccountEntity.setCustomerId(customerId);
        customerAccountEntity.setId(Utils.makeUUID());
        customerAccountEntity.setCreateTime(System.currentTimeMillis());
        customerAccountEntity.setCreateUser(info.getUserId());
        customerAccountEntity.setIsDelete(Constants.FALSE);
    }
}
