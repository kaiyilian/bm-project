package com.bumu.arya.admin.soin.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by CuiMengxin on 16/8/5.
 */
public class SoinOrderBillImportConfirmCommand {

    @JsonProperty("file_name")
    String fileName;

    String batch;

    @JsonProperty("batch_id")
    String batchId;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public SoinOrderBillImportConfirmCommand() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
