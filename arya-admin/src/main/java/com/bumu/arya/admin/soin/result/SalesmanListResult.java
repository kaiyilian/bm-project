package com.bumu.arya.admin.soin.result;

import java.util.List;

/**
 * Created by CuiMengxin on 16/8/7.
 */
public class SalesmanListResult {

    List<SalesmanResult> salesman;

    public SalesmanListResult() {
    }

    public List<SalesmanResult> getSalesman() {
        return salesman;
    }

    public void setSalesman(List<SalesmanResult> salesman) {
        this.salesman = salesman;
    }

    public static class SalesmanResult {
        String id;

        String name;

        public SalesmanResult() {
        }

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
    }
}
