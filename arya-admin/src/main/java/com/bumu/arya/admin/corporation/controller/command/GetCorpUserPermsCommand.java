package com.bumu.arya.admin.corporation.controller.command;

import com.bumu.arya.command.PagerCommand;

/**
 * Created by CuiMengxin on 2016/10/11.
 */
public class GetCorpUserPermsCommand extends PagerCommand {

    String corp_user_id;

    public String getCorp_user_id() {
        return corp_user_id;
    }

    public void setCorp_user_id(String corp_user_id) {
        this.corp_user_id = corp_user_id;
    }
}
