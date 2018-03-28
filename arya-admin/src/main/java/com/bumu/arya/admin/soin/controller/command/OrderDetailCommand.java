package com.bumu.arya.admin.soin.controller.command;

import com.bumu.common.command.ModelCommand;

import java.math.BigDecimal;

/**
 * @author majun
 * @date 2017/2/22
 */
public class OrderDetailCommand extends ModelCommand{

	private BigDecimal houseFundAdditionTotal;
	private BigDecimal disabilityTotal;
	private BigDecimal serverIllnessTotal;
	private BigDecimal injuryAdditionTotal;
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
}
