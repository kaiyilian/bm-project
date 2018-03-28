package com.bumu.arya.admin.econtract.result;

import com.bumu.arya.admin.econtract.model.EContractServiceInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2017/7/10
 * @email 351264830@qq.com
 */
@ApiModel
public class EContractServiceInfoResult {

    @ApiModelProperty(name = "info", value = "电子合同服务信息")
    private EContractServiceInfoVo info;
    @ApiModelProperty(value = "营业执照url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EContractServiceInfoVo getInfo() {
        return info;
    }

    public void setInfo(EContractServiceInfoVo info) {
        this.info = info;
    }
}
