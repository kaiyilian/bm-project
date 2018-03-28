package com.bumu.arya.admin.common.result;

import com.bumu.arya.response.SimpleIdNameResult;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/10/12.
 */
public class OperateTypeListResult {

    List<SimpleIdNameResult> types;

    public List<SimpleIdNameResult> getTypes() {
        return types;
    }

    public void setTypes(List<SimpleIdNameResult> types) {
        this.types = types;
    }
}
