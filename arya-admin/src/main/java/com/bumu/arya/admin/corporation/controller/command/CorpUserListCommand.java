package com.bumu.arya.admin.corporation.controller.command;

import com.bumu.arya.command.PagerCommand;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by CuiMengxin on 2016/10/9.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CorpUserListCommand extends PagerCommand {
    String corp_id;

    public String getCorp_id() {
        return corp_id;
    }

    public void setCorp_id(String corp_id) {
        this.corp_id = corp_id;
    }
}
