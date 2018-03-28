package com.bumu.arya.salary.command;

import com.bumu.arya.Utils;
import com.bumu.arya.salary.model.entity.CustomerFollowEntity;
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
public class CustomerFollowCommand implements EntityConverter<CustomerFollowEntity>,VoConverterFunction.Add<CustomerFollowEntity, SessionInfo> {

    @ApiModelProperty(value = "客户的ID", name = "customerId")
    @NotBlank(message = "客户的ID必填")
    private String customerId;

    @ApiModelProperty(value = "追踪记录信息", name= "followInfo")
    @NotBlank(message = "追踪记录信息不能为空")
    private String followInfo;

    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void begin(CustomerFollowEntity customerFollowEntity, SessionInfo info) {
        customerFollowEntity.setId(Utils.makeUUID());
        customerFollowEntity.setCreateTime(System.currentTimeMillis());
        customerFollowEntity.setCreateUser(info.getUserId());
    }
}
