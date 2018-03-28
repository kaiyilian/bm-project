package com.bumu.arya.admin.devops.result;

import com.bumu.arya.admin.devops.model.ApiVisitsRatio;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * API 访问统计结果
 * @author Allen 2017-11-30
 **/
public class ApiVisitsResult {

    @JsonProperty("chart_name")
    String chartName;

    List<ApiVisitsRatio> data;

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public List<ApiVisitsRatio> getData() {
        return data;
    }

    public void setData(List<ApiVisitsRatio> data) {
        this.data = data;
    }
}
