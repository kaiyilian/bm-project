package com.bumu.arya.admin.welfare.result;

import com.bumu.arya.response.SimpleIdNameResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * Created by CuiMengxin on 16/9/13.
 */
@ApiModel
public class WelfareCategoriesAllSpecsListResult {

    @ApiModelProperty("{id:'规格id',name:'规格名称'},...")
    List<SimpleIdNameResult> specs;

    public List<SimpleIdNameResult> getSpecs() {
        return specs;
    }

    public void setSpecs(List<SimpleIdNameResult> specs) {
        this.specs = specs;
    }
}
