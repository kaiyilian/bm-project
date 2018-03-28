package com.bumu.arya.salary.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 客户合同管理
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
@Entity
@Table(name = "SALARY_CUSTOMER_CONTRACT")
public class CustomerContractEntity extends BaseSalaryEntity{

    @Id()
    @Column(name = "ID", columnDefinition = "char(32)")
    private String id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_URL")
    private String fileUrl;

    @Column(name = "CUSTOMER_ID", columnDefinition = "char(32)")
    private String customerId;

    private String contractDir;

    public String getContractDir() {
        return contractDir;
    }

    public void setContractDir(String contractDir) {
        this.contractDir = contractDir;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
