package com.bumu.arya.admin.soin.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiMengxin on 16/8/7.
 */
public class SoinOrderImportBatchListResult {

    List<SoinOrderImportBatchResult> batchs;

    public List<SoinOrderImportBatchResult> getBatchs() {
        return batchs;
    }

    public void setBatchs(List<SoinOrderImportBatchResult> batchs) {
        this.batchs = batchs;
    }

    public SoinOrderImportBatchListResult() {
        this.batchs = new ArrayList<>();
    }

    public static class SoinOrderImportBatchResult {
        String id;

        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public SoinOrderImportBatchResult() {

        }
    }
}
