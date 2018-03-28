package com.bumu.bran.admin.econtract.result;

import com.bumu.common.result.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2017/6/21
 */
@ApiModel
public class EContractSignerResult extends BaseResult.IDResult {

    @ApiModelProperty(value = "签署人手机号码")
    private String tel;
    @ApiModelProperty(value = "合同签署有效期")
    private Integer validDays;
    @ApiModelProperty(value = "合同签署人姓名")
    private String name;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
