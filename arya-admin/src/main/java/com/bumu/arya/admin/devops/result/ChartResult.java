package com.bumu.arya.admin.devops.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 统计图表接口返回结果。
 *
 * @author Allen 2017-11-24
 **/
@ApiModel
public class ChartResult {

    @JsonProperty("chart_name")
    @ApiModelProperty(value = "图表名称", example = "设备类型访问统计")
    String chartName;

    @ApiModelProperty("图表中维度的定义")
    Dimension dimension;

    @ApiModelProperty("图表数据")
    List<DimensionData> data;

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public List<DimensionData> getData() {
        return data;
    }

    public void setData(List<DimensionData> data) {
        this.data = data;
    }
}
