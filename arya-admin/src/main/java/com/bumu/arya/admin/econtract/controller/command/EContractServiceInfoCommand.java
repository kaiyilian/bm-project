package com.bumu.arya.admin.econtract.controller.command;

import com.bumu.arya.admin.econtract.model.EContractServiceInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author majun
 * @date 2017/7/10
 * @email 351264830@qq.com
 */
@ApiModel
public class EContractServiceInfoCommand {

    @ApiModelProperty
    @NotBlank(message = "arya公司id必填")
    private String aryaCorpId;

    @ApiModelProperty(name = "info")
    @NotNull(message = "更新项必填")
    private EContractServiceInfoVo info;

    public String getAryaCorpId() {
        return aryaCorpId;
    }

    public void setAryaCorpId(String aryaCorpId) {
        this.aryaCorpId = aryaCorpId;
    }

    public EContractServiceInfoVo getInfo() {
        return info;
    }

    public void setInfo(EContractServiceInfoVo info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "EContractServiceInfoCommand{" +
                "aryaCorpId='" + aryaCorpId + '\'' +
                ", info=" + info +
                '}';
    }
}
