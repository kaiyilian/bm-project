package com.bumu.arya.salary.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/13
 */
@Entity
@Table(name = "SALARY_CUSTOMER_DISTRICT")
public class CustomerDistrictEntity extends BaseSalaryEntity {

    @Id()
    @Column(name = "ID", columnDefinition = "char(32)")
    private String id;

    /**
     * 客户ID
     */
    @Column(name = "CUSTOMER_ID", columnDefinition = "char(32)")
    private String customerId;

    /**
     * 客户地区名称
     */
    @Column(name = "CUSTOMER_DISTRICT_NAME")
    private String customerDistrictName;

    /**
     * 地区ID
     */
    @Column(name = "DISTRICT_ID", columnDefinition = "char(32)")
    private String districtId;

    /**
     * 地区名称
     */
    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerDistrictName() {
        return customerDistrictName;
    }

    public void setCustomerDistrictName(String customerDistrictName) {
        this.customerDistrictName = customerDistrictName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
