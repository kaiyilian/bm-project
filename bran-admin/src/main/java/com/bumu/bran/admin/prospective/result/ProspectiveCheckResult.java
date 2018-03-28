package com.bumu.bran.admin.prospective.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2018/1/23
 * @email 351264830@qq.com
 */
@ApiModel
public class ProspectiveCheckResult {

    @ApiModelProperty(value = "待入职员工id")
    private String id;

    @ApiModelProperty(value = "待入职员工姓名")
    private String name;

    @ApiModelProperty(value = "0:成功 1:失败")
    private Integer successOrFail;

    @ApiModelProperty(value = "失败原因, 存在多个失败因素")
    private String reason;

    public Integer getSuccessOrFail() {
        return successOrFail;
    }

    public void setSuccessOrFail(Integer successOrFail) {
        this.successOrFail = successOrFail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
