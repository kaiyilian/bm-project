package com.bumu.arya.admin.soin.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author CuiMengxin
 * @date 2016/1/13
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SoinOrderDetailResult {
    @JsonProperty("order_id")
    String orderId;

    @JsonProperty("order_no")
    String orderNo;

    Long version;

    @JsonProperty("person_name")
    String personName;

    @JsonProperty("status_code")
    Integer statusCode;

    @JsonProperty("person_idcard_no")
    String personIdcardNo;

    @JsonProperty("person_phone_no")
    String personPhoneNo;

    @JsonProperty("person_hukou")
    String personHukou;

    @JsonProperty("person_hukou_type")
    String personHukouType;

    String payment;

    /**
     * 订单是否欠费
     */
    Integer arrearage;

    @JsonProperty("creator_phone_no")
    String creatorPhoneNo;

    String medical;

    @JsonProperty("medical_total")
    String medicalTotal;

    String pregnancy;

    @JsonProperty("pregnancy_total")
    String pregnancyTotal;

    String injury;

    @JsonProperty("injury_total")
    String injuryTotal;

    String unemployment;

    @JsonProperty("unemployment_total")
    String unemploymentTotal;

    String pension;

    @JsonProperty("pension_total")
    String pensionTotal;

    String disability;

    @JsonProperty("disability_total")
    String disabilityTotal;

    @JsonProperty("severe_illness")
    String severeIllness;

    @JsonProperty("severe_illness_total")
    String severeIllnessTotal;

    @JsonProperty("injury_addition")
    String injuryAddition;

    @JsonProperty("injury_addition_total")
    String injuryAdditionTotal;

    @JsonProperty("house_fund")
    String houseFund;

    @JsonProperty("house_fund_total")
    String houseFundTotal;

    @JsonProperty("house_fund_addition")
    String houseFundAddition;

    @JsonProperty("house_fund_addition_total")
    String houseFundAdditionTotal;

    String fees;

    @JsonProperty("fees_total")
    String feesTotal;

    @JsonProperty("actual_payment")
    String actualPayment;

    String refund;

    @JsonProperty("pension_base")
    String pensionBase;

    @JsonProperty("medical_base")
    String medicalBase;

    @JsonProperty("unemployment_base")
    String unemploymentBase;

    @JsonProperty("injury_base")
    String injuryBase;

    @JsonProperty("pregnancy_base")
    String pregnancyBase;

    @JsonProperty("disbility_base")
    String disabilityBase;

    @JsonProperty("severe_illness_base")
    String severeIllnessBase;

    @JsonProperty("injury_addition_base")
    String injuryAdditionBase;

    @JsonProperty("house_fund_base")
    String houseFundBase;

    @JsonProperty("house_fund_addition_base")
    String houseFundAdditionBase;

    @JsonProperty("paymonth_details")
    ArrayList<PaymonthDetail> paymonthDetails;

    @JsonProperty("month_payment")
    String monthPayment;

    @JsonProperty("month_payment_total")
    String monthPaymentTotal;

    @JsonProperty("house_fund_percentage")
    RulePercentage houseFundPercentage;

    @JsonProperty("house_fund_addition_percentage")
    RulePercentage houseFundAdditionPercentage;

    @JsonProperty("injury_percentage")
    RulePercentage injuryPercentage;

    @JsonProperty("medical_percentage")
    RulePercentage medicalPercentage;

    @JsonProperty("pregnancy_percentage")
    RulePercentage pregnancyPercentage;

    @JsonProperty("pension_percentage")
    RulePercentage pensionPercentage;

    @JsonProperty("unemployment_percentage")
    RulePercentage unemploymentPercentage;

    @JsonProperty("disability_percentage")
    RulePercentage disabilityPercentage;

    @JsonProperty("severe_illness_percentage")
    RulePercentage severeIllnessPercentage;

    @JsonProperty("injury_addition_percentage")
    RulePercentage injuryAdditionPercentage;

    @JsonProperty("soin_district_code")
    String soinDistrictCode;

    String salesman;

    String supplier;

    @JsonProperty("service_year_month")
    String serviceYearMonth;

    @JsonProperty("other_payment")
    BigDecimal otherPayment;

    public BigDecimal getOtherPayment() {
        return otherPayment;
    }

    public void setOtherPayment(BigDecimal otherPayment) {
        this.otherPayment = otherPayment;
    }

    public String getServiceYearMonth() {
        return serviceYearMonth;
    }

    public void setServiceYearMonth(String serviceYearMonth) {
        this.serviceYearMonth = serviceYearMonth;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSoinDistrictCode() {
        return soinDistrictCode;
    }

    public void setSoinDistrictCode(String soinDistrictCode) {
        this.soinDistrictCode = soinDistrictCode;
    }

    public RulePercentage getHouseFundPercentage() {
        return houseFundPercentage;
    }

    public void setHouseFundPercentage(RulePercentage houseFundPercentage) {
        this.houseFundPercentage = houseFundPercentage;
    }

    public RulePercentage getHouseFundAdditionPercentage() {
        return houseFundAdditionPercentage;
    }

    public void setHouseFundAdditionPercentage(RulePercentage houseFundAdditionPercentage) {
        this.houseFundAdditionPercentage = houseFundAdditionPercentage;
    }

    public RulePercentage getInjuryPercentage() {
        return injuryPercentage;
    }

    public void setInjuryPercentage(RulePercentage injuryPercentage) {
        this.injuryPercentage = injuryPercentage;
    }

    public RulePercentage getMedicalPercentage() {
        return medicalPercentage;
    }

    public void setMedicalPercentage(RulePercentage medicalPercentage) {
        this.medicalPercentage = medicalPercentage;
    }

    public RulePercentage getPregnancyPercentage() {
        return pregnancyPercentage;
    }

    public void setPregnancyPercentage(RulePercentage pregnancyPercentage) {
        this.pregnancyPercentage = pregnancyPercentage;
    }

    public RulePercentage getPensionPercentage() {
        return pensionPercentage;
    }

    public void setPensionPercentage(RulePercentage pensionPercentage) {
        this.pensionPercentage = pensionPercentage;
    }

    public RulePercentage getUnemploymentPercentage() {
        return unemploymentPercentage;
    }

    public void setUnemploymentPercentage(RulePercentage unemploymentPercentage) {
        this.unemploymentPercentage = unemploymentPercentage;
    }

    public RulePercentage getDisabilityPercentage() {
        return disabilityPercentage;
    }

    public void setDisabilityPercentage(RulePercentage disabilityPercentage) {
        this.disabilityPercentage = disabilityPercentage;
    }

    public RulePercentage getSevereIllnessPercentage() {
        return severeIllnessPercentage;
    }

    public void setSevereIllnessPercentage(RulePercentage severeIllnessPercentage) {
        this.severeIllnessPercentage = severeIllnessPercentage;
    }

    public RulePercentage getInjuryAdditionPercentage() {
        return injuryAdditionPercentage;
    }

    public void setInjuryAdditionPercentage(RulePercentage injuryAdditionPercentage) {
        this.injuryAdditionPercentage = injuryAdditionPercentage;
    }

    //缴纳比例
    public static class RulePercentage {
        @JsonProperty("percentage_person")
        String percentagePerson;

        @JsonProperty("percentage_corp")
        String percentageCorp;

        @JsonProperty("percentage_total")
        String percentageTotal;

        @JsonProperty("extra_person")
        String extraPerson;

        @JsonProperty("extra_corp")
        String extraCorp;

        @JsonProperty("extra_total")
        String extraTotal;

        public RulePercentage() {
        }

        public String getPercentagePerson() {
            return percentagePerson;
        }

        public void setPercentagePerson(String percentagePerson) {
            this.percentagePerson = percentagePerson;
        }

        public String getPercentageCorp() {
            return percentageCorp;
        }

        public void setPercentageCorp(String percentageCorp) {
            this.percentageCorp = percentageCorp;
        }

        public String getPercentageTotal() {
            return percentageTotal;
        }

        public void setPercentageTotal(String percentageTotal) {
            this.percentageTotal = percentageTotal;
        }

        public String getExtraPerson() {
            return extraPerson;
        }

        public void setExtraPerson(String extraPerson) {
            this.extraPerson = extraPerson;
        }

        public String getExtraCorp() {
            return extraCorp;
        }

        public void setExtraCorp(String extraCorp) {
            this.extraCorp = extraCorp;
        }

        public String getExtraTotal() {
            return extraTotal;
        }

        public void setExtraTotal(String extraTotal) {
            this.extraTotal = extraTotal;
        }
    }

    public String getMonthPayment() {
        return monthPayment;
    }

    public void setMonthPayment(String monthPayment) {
        this.monthPayment = monthPayment;
    }

    public String getMonthPaymentTotal() {
        return monthPaymentTotal;
    }

    public void setMonthPaymentTotal(String monthPaymentTotal) {
        this.monthPaymentTotal = monthPaymentTotal;
    }

    public ArrayList<PaymonthDetail> getPaymonthDetails() {
        return paymonthDetails;
    }

    public void setPaymonthDetails(ArrayList<PaymonthDetail> paymonthDetails) {
        this.paymonthDetails = paymonthDetails;
    }

    public SoinOrderDetailResult() {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getPersonIdcardNo() {
        return personIdcardNo;
    }

    public void setPersonIdcardNo(String personIdcardNo) {
        this.personIdcardNo = personIdcardNo;
    }

    public String getPersonPhoneNo() {
        return personPhoneNo;
    }

    public void setPersonPhoneNo(String personPhoneNo) {
        this.personPhoneNo = personPhoneNo;
    }

    public String getPersonHukou() {
        return personHukou;
    }

    public void setPersonHukou(String personHukou) {
        this.personHukou = personHukou;
    }

    public String getPersonHukouType() {
        return personHukouType;
    }

    public void setPersonHukouType(String personHukouType) {
        this.personHukouType = personHukouType;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getCreatorPhoneNo() {
        return creatorPhoneNo;
    }

    public void setCreatorPhoneNo(String creatorPhoneNo) {
        this.creatorPhoneNo = creatorPhoneNo;
    }

    public String getMedical() {
        return medical;
    }

    public void setMedical(String medical) {
        this.medical = medical;
    }

    public String getMedicalTotal() {
        return medicalTotal;
    }

    public void setMedicalTotal(String medicalTotal) {
        this.medicalTotal = medicalTotal;
    }

    public String getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(String pregnancy) {
        this.pregnancy = pregnancy;
    }

    public String getPregnancyTotal() {
        return pregnancyTotal;
    }

    public void setPregnancyTotal(String pregnancyTotal) {
        this.pregnancyTotal = pregnancyTotal;
    }

    public String getInjury() {
        return injury;
    }

    public void setInjury(String injury) {
        this.injury = injury;
    }

    public String getInjuryTotal() {
        return injuryTotal;
    }

    public void setInjuryTotal(String injuryTotal) {
        this.injuryTotal = injuryTotal;
    }

    public String getUnemployment() {
        return unemployment;
    }

    public void setUnemployment(String unemployment) {
        this.unemployment = unemployment;
    }

    public String getUnemploymentTotal() {
        return unemploymentTotal;
    }

    public void setUnemploymentTotal(String unemploymentTotal) {
        this.unemploymentTotal = unemploymentTotal;
    }

    public String getPension() {
        return pension;
    }

    public void setPension(String pension) {
        this.pension = pension;
    }

    public String getPensionTotal() {
        return pensionTotal;
    }

    public void setPensionTotal(String pensionTotal) {
        this.pensionTotal = pensionTotal;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getDisabilityTotal() {
        return disabilityTotal;
    }

    public void setDisabilityTotal(String disabilityTotal) {
        this.disabilityTotal = disabilityTotal;
    }

    public String getSevereIllness() {
        return severeIllness;
    }

    public void setSevereIllness(String severeIllness) {
        this.severeIllness = severeIllness;
    }

    public String getSevereIllnessTotal() {
        return severeIllnessTotal;
    }

    public void setSevereIllnessTotal(String severeIllnessTotal) {
        this.severeIllnessTotal = severeIllnessTotal;
    }

    public String getInjuryAddition() {
        return injuryAddition;
    }

    public void setInjuryAddition(String injuryAddition) {
        this.injuryAddition = injuryAddition;
    }

    public String getInjuryAdditionTotal() {
        return injuryAdditionTotal;
    }

    public void setInjuryAdditionTotal(String injuryAdditionTotal) {
        this.injuryAdditionTotal = injuryAdditionTotal;
    }

    public String getHouseFund() {
        return houseFund;
    }

    public void setHouseFund(String houseFund) {
        this.houseFund = houseFund;
    }

    public String getHouseFundTotal() {
        return houseFundTotal;
    }

    public void setHouseFundTotal(String houseFundTotal) {
        this.houseFundTotal = houseFundTotal;
    }

    public String getHouseFundAddition() {
        return houseFundAddition;
    }

    public void setHouseFundAddition(String houseFundAddition) {
        this.houseFundAddition = houseFundAddition;
    }

    public String getHouseFundAdditionTotal() {
        return houseFundAdditionTotal;
    }

    public void setHouseFundAdditionTotal(String houseFundAdditionTotal) {
        this.houseFundAdditionTotal = houseFundAdditionTotal;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getFeesTotal() {
        return feesTotal;
    }

    public void setFeesTotal(String feesTotal) {
        this.feesTotal = feesTotal;
    }

    public String getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(String actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getPensionBase() {
        return pensionBase;
    }

    public void setPensionBase(String pensionBase) {
        this.pensionBase = pensionBase;
    }

    public String getMedicalBase() {
        return medicalBase;
    }

    public void setMedicalBase(String medicalBase) {
        this.medicalBase = medicalBase;
    }

    public String getUnemploymentBase() {
        return unemploymentBase;
    }

    public void setUnemploymentBase(String unemploymentBase) {
        this.unemploymentBase = unemploymentBase;
    }

    public String getInjuryBase() {
        return injuryBase;
    }

    public void setInjuryBase(String injuryBase) {
        this.injuryBase = injuryBase;
    }

    public String getPregnancyBase() {
        return pregnancyBase;
    }

    public void setPregnancyBase(String pregnancyBase) {
        this.pregnancyBase = pregnancyBase;
    }

    public String getDisabilityBase() {
        return disabilityBase;
    }

    public void setDisabilityBase(String disabilityBase) {
        this.disabilityBase = disabilityBase;
    }

    public String getSevereIllnessBase() {
        return severeIllnessBase;
    }

    public void setSevereIllnessBase(String severeIllnessBase) {
        this.severeIllnessBase = severeIllnessBase;
    }

    public String getInjuryAdditionBase() {
        return injuryAdditionBase;
    }

    public void setInjuryAdditionBase(String injuryAdditionBase) {
        this.injuryAdditionBase = injuryAdditionBase;
    }

    public String getHouseFundBase() {
        return houseFundBase;
    }

    public void setHouseFundBase(String houseFundBase) {
        this.houseFundBase = houseFundBase;
    }

    public String getHouseFundAdditionBase() {
        return houseFundAdditionBase;
    }

    public void setHouseFundAdditionBase(String houseFundAdditionBase) {
        this.houseFundAdditionBase = houseFundAdditionBase;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getArrearage() {
        return arrearage;
    }

    public void setArrearage(Integer arrearage) {
        this.arrearage = arrearage;
    }

    public static class PaymonthDetail {

        /**
         * 编号
         */
        @JsonProperty("soin_id")
        String soinId;

        /**
         * 年月
         */
        String paymonth;

        /**
         * 是否缴纳了
         */
        @JsonProperty("is_payed")
        int isPayed;

        /**
         * 是否是当前需要缴纳的月份
         */
        @JsonProperty("is_current_paymonth")
        int isCurrentPaymonth;

        public String getSoinId() {
            return soinId;
        }

        public void setSoinId(String soinId) {
            this.soinId = soinId;
        }

        public String getPaymonth() {
            return paymonth;
        }

        public void setPaymonth(String paymonth) {
            this.paymonth = paymonth;
        }

        public int getIsPayed() {
            return isPayed;
        }

        public void setIsPayed(int isPayed) {
            this.isPayed = isPayed;
        }

        public int getIsCurrentPaymonth() {
            return isCurrentPaymonth;
        }

        public void setIsCurrentPaymonth(int isCurrentPaymonth) {
            this.isCurrentPaymonth = isCurrentPaymonth;
        }

        public PaymonthDetail() {

        }
    }
}
