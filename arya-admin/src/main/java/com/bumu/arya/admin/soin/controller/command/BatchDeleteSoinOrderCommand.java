package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 16/8/7.
 */
public class BatchDeleteSoinOrderCommand {

    @JsonProperty("batch_id")
    String batchId;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public BatchDeleteSoinOrderCommand() {

    }
}
