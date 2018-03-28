package com.bumu.arya.admin.soin.result;

import com.bumu.arya.soin.model.entity.AryaSoinEntity;

import java.math.BigDecimal;

/**
 * @author majun
 * @date 2017/2/23
 */
public class OrderDetailResult {

	private static OrderDetailResult o ;

	public static OrderDetailResult create(AryaSoinEntity e) {
		if (e == null) {
			return o;
		}
		o = new OrderDetailResult();
		o.setServerIllnessTotal(e.getServerIllnessTotal());
		o.setInjuryAdditionTotal(e.getInjuryAdditionTotal());
		o.setHouseFundAdditionTotal(e.getHouseFundAdditionTotal());
		o.setHeatingTotal(e.getHeatingTotal());
		o.setDisabilityTotal(e.getDisabilityTotal());
		return o;
	}

	private OrderDetailResult() {
	}

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
