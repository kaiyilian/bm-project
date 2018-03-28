package com.bumu.arya.salary.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Entity
@Table(name = "SALARY_USER")
public class SalaryUserEntity extends BaseSalaryEntity {

    public static final String COL_NAME_ID = "ID";

    public static final String COL_NAME_NAME = "NAME";

    public static final String COL_NAME_ID_CARD_NO = "ID_CARD_NO";

    public static final String COL_NAME_PHONE_NO = "PHONE_NO";

    public static final String COL_NAME_BANK_ACCOUNT = "BANK_ACCOUNT";

    public static final String COL_NAME_CUSTOMER_ID = "CUSTOMER_ID";

    public static final String COL_NAME_DISTRICT_ID = "DISTRICT_ID";

    public static final String COL_NAME_DISTRICT_NAME = "DISTRICT_NAME";

    public static final String COL_NAME_IS_UPDATE = "IS_UPDATE";

    /**
     * 主键ID
     */
    @Id()
    @Column(name = COL_NAME_ID, columnDefinition = "char(32)")
    private String id;

    /**
     * 姓名
     */
    @Column(name = COL_NAME_NAME, length = 32)
    private String name;

    /**
     * 身份证
     */
    @Column(name = COL_NAME_ID_CARD_NO, length = 32)
    private String idCardNo;

    /**
     * 手机号
     */
    @Column(name = COL_NAME_PHONE_NO, length = 32)
    private String phoneNo;

    /**
     * 银行卡号
     */
    @Column(name = COL_NAME_BANK_ACCOUNT, length = 64)
    private String bankAccount;


    @Column(name = "BANK_NAME")
    private String bankName;

    /**
     * 用户ID
     */
    @Column(name = COL_NAME_CUSTOMER_ID, columnDefinition = "char(32)")
    private String customerId;

    @Column(name = "COL_NAME_DISTRICT_NAME")
    private String districtName;

    /**
     * 地区ID
     */
    @Column(name = COL_NAME_DISTRICT_ID, columnDefinition = "char(32)")
    private String districtId;

    /**
     * 是否更新
     */
    @Column(name = COL_NAME_IS_UPDATE, length = 32)
    private Integer isUpdate;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    public static String getColNameId() {
        return COL_NAME_ID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public boolean equals(Object obj) {
        if (null != obj && obj instanceof SalaryUserEntity) {
            SalaryUserEntity salaryUserEntity = (SalaryUserEntity) obj;
            if (salaryUserEntity.getIdCardNo().equals(idCardNo)) {
                return true;
            }
        }
        return false;
    }
}
