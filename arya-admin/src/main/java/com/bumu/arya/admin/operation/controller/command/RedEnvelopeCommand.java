package com.bumu.arya.admin.operation.controller.command;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
public class RedEnvelopeCommand {

    private Long start;
    private Long end;
    private String redEnvelopeBalance;
    private String phone;

    public RedEnvelopeCommand() {
    }
    public RedEnvelopeCommand(Long start, Long end, String redEnvelopeBalance, String phone) {
        this.start = start;
        this.end = end;
        this.redEnvelopeBalance = redEnvelopeBalance;
        this.phone = phone;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public String getRedEnvelopeBalance() {
        return redEnvelopeBalance;
    }

    public void setRedEnvelopeBalance(String redEnvelopeBalance) {
        this.redEnvelopeBalance = redEnvelopeBalance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
