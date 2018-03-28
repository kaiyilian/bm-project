package com.bumu.arya.admin.welfare.result;

import com.bumu.arya.admin.misc.result.SimpleResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by CuiMengxin on 16/9/8.
 * 福库产品名称获取
 */
@ApiModel
public class WelfareGoodNameList {

    @ApiModelProperty("产品名称列表")
    List<SimpleResult> goods;

    public List<SimpleResult> getGoods() {
        return goods;
    }

    public void setGoods(List<SimpleResult> goods) {
        this.goods = goods;
    }
}
