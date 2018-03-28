package com.bumu.arya.salary.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 跟踪信息
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/3
 */
@Entity
@Table(name = "SALARY_PROJECT_FOLLOW")
public class CustomerFollowEntity extends BaseSalaryEntity {

    @Id()
    @Column(name = "ID", columnDefinition = "char(32)")
    private String id;

    /**
     * 跟踪信息
     */
    @Column(name = "FOLLOW_INFO")
    private String followInfo;

    /**
     * 立项申请ID
     */
    @Column(name = "PROJECT_ID", columnDefinition = "char(32)")
    private String projectId;

    /**
     * 客户ID
     */
    @Column(name = "CUSTOMER_ID", columnDefinition = "char(32)")
    private String customerId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(String followInfo) {
        this.followInfo = followInfo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
