package com.bumu.arya.admin.welfare.result;

import com.bumu.arya.response.SimpleIdNameResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 16/9/13.
 * 福库商品所有分类
 */
@ApiModel
public class WelfareAllCategoriesListResult {

    @ApiModelProperty("{id:'分类id',name:'分类名称'}")
    List<SimpleIdNameResult> categories;

    public List<SimpleIdNameResult> getCategories() {
        return categories;
    }

    public void setCategories(List<SimpleIdNameResult> categories) {
        this.categories = categories;
    }
}
