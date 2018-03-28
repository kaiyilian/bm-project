package com.bumu.arya.admin.devops.result;

import java.util.List;

/**
 * 封装PV和UV两个维度的数据
 * @author Allen 2017-11-28
 **/
public class PvUvDimensionData {

    // 维度名称列表
    List<String> dimensions;
    DimensionData<Long> pvDimensionData;
    DimensionData<Long> uvDimensionData;

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public DimensionData<Long> getPvDimensionData() {
        return pvDimensionData;
    }

    public void setPvDimensionData(DimensionData<Long> pvDimensionData) {
        this.pvDimensionData = pvDimensionData;
    }

    public DimensionData<Long> getUvDimensionData() {
        return uvDimensionData;
    }

    public void setUvDimensionData(DimensionData<Long> uvDimensionData) {
        this.uvDimensionData = uvDimensionData;
    }
}
