package com.bumu.arya.admin.system.controller.command;

import com.bumu.arya.command.PagerCommand;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * Created by CuiMengxin on 2016/10/11.
 */
public class OpLogListCommand extends PagerCommand {

    @JsonProperty("op_login_name")
    String opLoginName;

    @JsonProperty("op_real_name")
    String opRealName;

    String keyword;

    @JsonProperty("operate_type")
    Integer operateType;

    public String getOpLoginName() {
        return opLoginName;
    }

    public void setOpLoginName(String opLoginName) {
        this.opLoginName = opLoginName;
    }

    public String getOpRealName() {
        return opRealName;
    }

    public void setOpRealName(String opRealName) {
        this.opRealName = opRealName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
}
