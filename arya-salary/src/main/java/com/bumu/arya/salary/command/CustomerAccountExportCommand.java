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
public class CustomerAccountExportCommand {

    @ApiModelProperty(value = "客户ID", name = "customerId")
    @NotBlank(message = "客户ID必填")
    private String customerId;

    @ApiModelProperty(value = "查询条件：年-月", name = "yearMonth")
    private String yearMonth;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }
}
