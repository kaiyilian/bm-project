package com.bumu.arya.admin.soin.result;

import com.bumu.arya.admin.misc.result.SimpleResult;
import com.bumu.common.result.PaginationResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 16/8/8.
 */
public class SoinSupplierListResult extends PaginationResult {

    List<SoinSupplierResult> suppliers;

    public SoinSupplierListResult() {
        this.suppliers = new ArrayList<>();
    }

    public List<SoinSupplierResult> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SoinSupplierResult> suppliers) {
        this.suppliers = suppliers;
    }

    public static class SoinSupplierResult extends SimpleResult {

        BigDecimal fee;

        @JsonProperty("district_count")
        int districtCount;

        List<DistrictResult> districts;

        @JsonProperty("is_default")
        Integer isDefault;//是否是首选供应商

        public Integer getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(Integer isDefault) {
            this.isDefault = isDefault;
        }

        public SoinSupplierResult() {
            this.districts = new ArrayList<>();
        }

        public BigDecimal getFee() {
            return fee;
        }

        public void setFee(BigDecimal fee) {
            this.fee = fee;
        }

        public int getDistrictCount() {
            return districtCount;
        }

        public void setDistrictCount(int districtCount) {
            this.districtCount = districtCount;
        }

        public List<DistrictResult> getDistricts() {
            return districts;
        }

        public void setDistricts(List<DistrictResult> districts) {
            this.districts = districts;
        }
    }

    public static class DistrictResult extends SimpleResult {

    }
}
