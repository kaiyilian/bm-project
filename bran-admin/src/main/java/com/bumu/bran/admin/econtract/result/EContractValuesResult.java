package com.bumu.bran.admin.econtract.result;

import com.bumu.common.result.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2017/6/21
 */
@ApiModel
public class EContractValuesResult extends BaseResult.IDVersionResult {

    @ApiModelProperty(value = "电子合同模板填写项ID", name = "eContractTemplateLoopsId")
    private String eContractTemplateLoopsId;

    @ApiModelProperty(value = "电子合同填写项值")
    private String value;

    public String geteContractTemplateLoopsId() {
        return eContractTemplateLoopsId;
    }

    public void seteContractTemplateLoopsId(String eContractTemplateLoopsId) {
        this.eContractTemplateLoopsId = eContractTemplateLoopsId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
