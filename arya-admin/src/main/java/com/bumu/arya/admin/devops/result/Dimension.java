package com.bumu.arya.admin.devops.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 表示一个图表的维度
 * @author Allen 2017-11-24
 **/
@ApiModel
public class Dimension {

    @ApiModelProperty(value = "维度名称", example = "设备类型")
    String name;

    @ApiModelProperty(value = "维度名称值", example = "GOOGLE, HUAWEI, XIAOMI, OPPO, VIVO, ONEPLUS")
    List<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
