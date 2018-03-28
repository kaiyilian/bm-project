package com.bumu.bran.admin.econtract.result;

import com.bumu.common.result.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author majun
 * @date 2017/6/19
 */
@ApiModel
public class EContractDetailResult extends BaseResult.IDVersionResult {

    @ApiModelProperty(value = "合同编号")
    private String contractNo;
    @ApiModelProperty(value = "合同类型 0:劳动合同 1:社保合同")
    private Integer contractType;
    @ApiModelProperty(value = "创建时间")
    private Long createTime;
    @ApiModelProperty(value = "合同状态 0:未发送 1:已发送 2:待审核 3:已生效 4:已过期 5:已作废")
    private Integer contractState;
    @ApiModelProperty(value = "loops的keyValue")
    private List<EContractLoopsKeyValueResult> loopsKeyValues;
    @ApiModelProperty(value = "合同有效期")
    private Integer validDays;

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getContractState() {
        return contractState;
    }

    public void setContractState(Integer contractState) {
        this.contractState = contractState;
    }

    public List<EContractLoopsKeyValueResult> getLoopsKeyValues() {
        return loopsKeyValues;
    }

    public void setLoopsKeyValues(List<EContractLoopsKeyValueResult> loopsKeyValues) {
        this.loopsKeyValues = loopsKeyValues;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }
}
