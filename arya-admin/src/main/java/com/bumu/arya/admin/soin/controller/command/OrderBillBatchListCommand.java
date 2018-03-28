package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.command.PagerCommand;

/**
 * Created by CuiMengxin on 16/8/17.
 */
public class OrderBillBatchListCommand extends PagerCommand {

    String batch_id;

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }
}
