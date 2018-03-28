package com.bumu.arya.admin.devops.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 表示一个维度的所有值，例如PV或者UV
 * @author Allen 2017-11-24
 * @param <T> 数据中的数值类型
 */
@ApiModel
public class DimensionData<T extends Number> {

    @ApiModelProperty(value = "值名称", example = "PV")
    String name;

    @ApiModelProperty(value = "值类型", example = "integer")
    String type;

    @ApiModelProperty("维度值列表")
    List<T> values;

    public DimensionData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}
