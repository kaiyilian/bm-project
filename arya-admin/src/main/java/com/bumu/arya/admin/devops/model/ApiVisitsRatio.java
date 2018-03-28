package com.bumu.arya.admin.devops.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 访问占比（PV和UV）
 * @author Allen 2017-11-30
 **/
public class ApiVisitsRatio extends ApiVisits {

    @JsonProperty("pv_ratio")
    Float pvRatio;

    @JsonProperty("uv_ratio")
    Float uvRatio;

    public ApiVisitsRatio(String tag) {
        super(tag);
    }

    public Float getPvRatio() {
        return pvRatio;
    }

    public void setPvRatio(Float pvRatio) {
        this.pvRatio = pvRatio;
    }

    public Float getUvRatio() {
        return uvRatio;
    }

    public void setUvRatio(Float uvRatio) {
        this.uvRatio = uvRatio;
    }
}
