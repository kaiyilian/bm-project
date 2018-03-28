package com.bumu.arya.salary.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/4
 */
@ApiModel
public class CustomerFollowResult {

    @ApiModelProperty(value = "跟踪记录信息")
    private String followInfo;

    @ApiModelProperty(value = "创建日期--跟踪日期")
    private Long createTime;

    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
