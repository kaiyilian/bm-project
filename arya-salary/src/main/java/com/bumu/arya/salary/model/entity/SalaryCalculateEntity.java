package com.bumu.arya.salary.model.entity;


import javax.persistence.*;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
@Entity
@Table(name = "SALARY_CALCULATE")
public class SalaryCalculateEntity extends BaseSalaryEntity {

    @Id()
    @Column(name = "ID", columnDefinition = "char(32) COMMENT 'ID'")
    private String id;

    @Column(name = "CUSTOMER_ID", columnDefinition = "char(32) COMMENT 'ID'")
    private String customerId;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "MONTH")
    private Integer month;

    @Column(name = "WEEK")
    private Long week;

    @Column(name = "SETTLEMENT_INTERVAL")
    private Integer settlementInterval;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "IS_DEDUCT")
    private Integer isDeduct;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Integer getSettlementInterval() {
        return settlementInterval;
    }

    public Integer getIsDeduct() {
        return isDeduct;
    }

    public void setIsDeduct(Integer isDeduct) {
        this.isDeduct = isDeduct;
    }

    public void setSettlementInterval(Integer settlementInterval) {
        this.settlementInterval = settlementInterval;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getWeek() {
        return week;
    }

    public void setWeek(Long week) {
        this.week = week;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
