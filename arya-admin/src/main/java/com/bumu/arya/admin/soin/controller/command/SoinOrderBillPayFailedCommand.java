package com.bumu.arya.admin.soin.controller.command;

/**
 * Created by CuiMengxin on 16/8/18.
 */
public class SoinOrderBillPayFailedCommand extends IdListCommand {

    String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
