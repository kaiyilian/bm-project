package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by CuiMengxin on 2015/10/22.
 */
@ApiModel
public class DistrictSoinTypesResult extends ArrayList<DistrictSoinTypesResult.DistrictSoinType> {
    public static class DistrictSoinType implements Serializable {

        @ApiModelProperty(value = "社保类型id")
        String id;

        @ApiModelProperty(value = "社保类型名称")
        String text;

        @Deprecated
        @ApiModelProperty
        @JsonProperty("is_disable")
        boolean isDisable;

        public DistrictSoinType() {
        }

        public DistrictSoinType(String id, String text) {
            this.id = id;
            this.text = text;
        }

        public boolean isDisable() {
            return isDisable;
        }

        public void setDisable(boolean disable) {
            isDisable = disable;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
