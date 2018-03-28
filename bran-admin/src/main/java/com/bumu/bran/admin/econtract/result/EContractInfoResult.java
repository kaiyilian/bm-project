package com.bumu.bran.admin.econtract.result;

import com.bumu.common.result.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author majun
 * @date 2017/6/21
 */
@ApiModel
public class EContractInfoResult extends BaseResult.IDVersionResult {

    @ApiModelProperty(value = "电子合同模板ID", name = "eContractTemplateId")
    private String eContractTemplateId;

    @ApiModelProperty(value = "合同签署人")
    private List<EContractSignerResult> signers;

    @ApiModelProperty(value = "电子合同填写项")
    private List<EContractLoopsKeyValueResult> values;

    @ApiModelProperty(value = "印章文件ID", name = "eContractSealId")
    private String eContractSealId;

    @ApiModelProperty(value = "印章文件url", name = "eContractSealUrl")
    private String eContractSealUrl;

    public String geteContractTemplateId() {
        return eContractTemplateId;
    }

    public void seteContractTemplateId(String eContractTemplateId) {
        this.eContractTemplateId = eContractTemplateId;
    }

    public List<EContractSignerResult> getSigners() {
        return signers;
    }

    public void setSigners(List<EContractSignerResult> signers) {
        this.signers = signers;
    }

    public String geteContractSealId() {
        return eContractSealId;
    }

    public void seteContractSealId(String eContractSealId) {
        this.eContractSealId = eContractSealId;
    }

    public String geteContractSealUrl() {
        return eContractSealUrl;
    }

    public void seteContractSealUrl(String eContractSealUrl) {
        this.eContractSealUrl = eContractSealUrl;
    }

    public List<EContractLoopsKeyValueResult> getValues() {
        return values;
    }

    public void setValues(List<EContractLoopsKeyValueResult> values) {
        this.values = values;
    }
}
