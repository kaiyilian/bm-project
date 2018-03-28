package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.legacy.Record;
import com.bumu.common.command.BatchCommand;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

/**
 * @author majun
 * @date 2017/2/22
 */

public class OrderBatchUpdateCommand extends BatchCommand implements Record {

    private OrderDetailCommand orderDetailCommand = new OrderDetailCommand();

    @Range(min = 0, max = 10000, message = "{arya.admin.order.batch.houseFundAdditionTotal.range}")
    private BigDecimal houseFundAdditionTotal;

    @Range(min = 0, max = 10000, message = "{arya.admin.order.batch.disabilityTotal}")
    private BigDecimal disabilityTotal;

    @Range(min = 0, max = 10000, message = "{arya.admin.order.batch.serverIllnessTotal}")
    private BigDecimal serverIllnessTotal;

    @Range(min = 0, max = 10000, message = "{arya.admin.order.batch.injuryAdditionTotal}")
    private BigDecimal injuryAdditionTotal;

    @Range(min = 0, max = 10000, message = "{arya.admin.order.batch.heatingTotal}")
    private BigDecimal heatingTotal;

    public BigDecimal getHouseFundAdditionTotal() {
        return houseFundAdditionTotal;
    }

    public void setHouseFundAdditionTotal(BigDecimal houseFundAdditionTotal) {
        this.houseFundAdditionTotal = houseFundAdditionTotal;
    }

    public BigDecimal getDisabilityTotal() {
        return disabilityTotal;
    }

    public void setDisabilityTotal(BigDecimal disabilityTotal) {
        this.disabilityTotal = disabilityTotal;
    }

    public BigDecimal getServerIllnessTotal() {
        return serverIllnessTotal;
    }

    public void setServerIllnessTotal(BigDecimal serverIllnessTotal) {
        this.serverIllnessTotal = serverIllnessTotal;
    }

    public BigDecimal getInjuryAdditionTotal() {
        return injuryAdditionTotal;
    }

    public void setInjuryAdditionTotal(BigDecimal injuryAdditionTotal) {
        this.injuryAdditionTotal = injuryAdditionTotal;
    }

    public BigDecimal getHeatingTotal() {
        return heatingTotal;
    }

    public void setHeatingTotal(BigDecimal heatingTotal) {
        this.heatingTotal = heatingTotal;
    }

    public OrderDetailCommand getModel() {
        orderDetailCommand.setDisabilityTotal(getDisabilityTotal());
        orderDetailCommand.setHeatingTotal(getHeatingTotal());
        orderDetailCommand.setHouseFundAdditionTotal(getHouseFundAdditionTotal());
        orderDetailCommand.setInjuryAdditionTotal(getInjuryAdditionTotal());
        orderDetailCommand.setServerIllnessTotal(getServerIllnessTotal());
        return orderDetailCommand;
    }

    @Override
    public String logMsg() {
        // 批量更新n条, 更新的内容分别为：
        StringBuilder sb = new StringBuilder();
        sb.append("批量更新为")
                .append(this.getBatch().size())
                .append("条").append(", ")
                .append("更新内容为: \n")
                .append("残保金额 ¥ : ")
                .append(this.getDisabilityTotal())
                .append("\n")
                .append("工伤补充 ¥ : ")
                .append(this.getInjuryAdditionTotal())
                .append("\n")
                .append("采暖费 ¥ :")
                .append(this.getHeatingTotal())
                .append("\n")
                .append("补充公积金 ¥ : ")
                .append(this.getHouseFundAdditionTotal())
                .append("\n")
                .append("大病医疗 ¥ : ")
                .append(this.getServerIllnessTotal());

        return sb.toString();
    }
}
