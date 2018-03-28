package com.bumu.arya.admin.misc.result;

import com.bumu.arya.response.SimpleIdNameResult;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by CuiMengxin on 2016/9/30.
 */
@ApiModel
public class NotificationJumpTypeList {

    List<SimpleIdNameResult> types;

    public List<SimpleIdNameResult> getTypes() {
        return types;
    }

    public void setTypes(List<SimpleIdNameResult> types) {
        this.types = types;
    }
}
