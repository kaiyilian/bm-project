package com.bumu.arya.salary.model.entity;

import com.bumu.arya.Utils;
import com.bumu.arya.common.Constants;
import com.bumu.common.SessionInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/5
 */
@Entity
@Table(name = "SALARY_ERROR_LOG")
public class SalaryErrLogEntity extends BaseSalaryEntity {

    @Id()
    @Column(name = "ID", columnDefinition = "char(32)")
    private String id;

    @Column(name = "CUSTOMER_ID", columnDefinition = "char(32)")
    private String customerId;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @Column(name = "LOG_INFO", length = 512)
    private String logInfo;

    @Column(name = "REMARK")
    private String remark;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void init(){
        setId(Utils.makeUUID());
        setCreateTime(System.currentTimeMillis());
        setIsDelete(Constants.FALSE);
    }
}
