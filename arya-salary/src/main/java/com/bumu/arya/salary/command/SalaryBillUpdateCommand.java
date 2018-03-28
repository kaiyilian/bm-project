package com.bumu.arya.salary.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class SalaryBillUpdateCommand {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "ID必填")
    private String id;

    /**
     * 开票日期
     */
    @ApiModelProperty(value = "开票日期")
    private Long billDate;

    /**
     * 邮寄日期
     */
    @ApiModelProperty(value = "邮寄日期")
    private Long mailDate;

    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人")
    private String receiver;

    /**
     * 接受日期
     */
    @ApiModelProperty(value = "接受日期")
    private Long receiveDate;

    /**
     * 签收日期
     */
    @ApiModelProperty(value = "接收信息")
    private String receiveInfo;

    /**
     * 汇款情况
     */
    @ApiModelProperty(value = "回款情况")
    private String backInfo;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBillDate() {
        return billDate;
    }

    public void setBillDate(Long billDate) {
        this.billDate = billDate;
    }

    public Long getMailDate() {
        return mailDate;
    }

    public void setMailDate(Long mailDate) {
        this.mailDate = mailDate;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Long receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveInfo() {
        return receiveInfo;
    }

    public void setReceiveInfo(String receiveInfo) {
        this.receiveInfo = receiveInfo;
    }

    public String getBackInfo() {
        return backInfo;
    }

    public void setBackInfo(String backInfo) {
        this.backInfo = backInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
