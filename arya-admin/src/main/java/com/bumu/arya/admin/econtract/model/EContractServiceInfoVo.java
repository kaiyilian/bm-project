package com.bumu.arya.admin.econtract.model;

import com.bumu.common.validator.annotation.PhoneNo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author majun
 * @date 2017/7/10
 * @email 351264830@qq.com
 */
@ApiModel
public class EContractServiceInfoVo {

    /**
     * 电子合同服务-企业全称
     */
    @ApiModelProperty(name = "eCorpName", value = "电子合同服务-企业全称, 最长100位")
    @Length(max = 100, message = "企业全称,长度超过100")
    @NotBlank(message = "企业全称必填")
    private String eCorpName;
    /**
     * 电子合同服务-企业法人
     */
    @ApiModelProperty(name = "eCorpUserName", value = "电子合同服务-企业法人, 最长20位")
    @Length(max = 20, message = "企业法人,长度超过20")
    @NotBlank(message = "企业法人必填")
    private String eCorpUserName;

    /**
     * 电子合同服务-企业法人手机号
     */
    @ApiModelProperty(name = "eCorpUserPhone", value = "电子合同服务-企业法人手机号, 最长11位, 需要验证手机号格式")
    @PhoneNo
    @Length(max = 11, message = "企业法人手机号,长度超过11")
    @NotBlank(message = "企业法人手机号必填")
    private String eCorpUserPhone;

    /**
     * 电子合同服务-企业法人身份证
     */
    @ApiModelProperty(name = "eCorpUserCardNo", value = "电子合同服务-企业法人身份证, 最长18位, 需要验证身份证格式")
    @Length(max = 18, message = "企业法人身份证, 长度超过18")
    @NotBlank(message = "企业法人身份证必填")
    private String eCorpUserCardNo;

    /**
     * 电子合同服务-营业执照
     */
    @ApiModelProperty(name = "eCorpLicenseCode", value = "电子合同服务-营业执照, 最长50位")
    @Length(max = 50, message = "营业执照, 长度超过50")
    @NotBlank(message = "营业执照必填")
    private String eCorpLicenseCode;

    /**
     * 电子合同服务-营业执照文件
     */
    @ApiModelProperty(name = "eCorpLicenseFileName", value = "电子合同服务-营业执照文件")
    @NotBlank(message = "营业执照文件必填")
    private String eCorpLicenseFileName;

    public String geteCorpName() {
        return eCorpName;
    }

    public void seteCorpName(String eCorpName) {
        this.eCorpName = eCorpName;
    }

    public String geteCorpUserName() {
        return eCorpUserName;
    }

    public void seteCorpUserName(String eCorpUserName) {
        this.eCorpUserName = eCorpUserName;
    }

    public String geteCorpUserPhone() {
        return eCorpUserPhone;
    }

    public void seteCorpUserPhone(String eCorpUserPhone) {
        this.eCorpUserPhone = eCorpUserPhone;
    }

    public String geteCorpUserCardNo() {
        return eCorpUserCardNo;
    }

    public void seteCorpUserCardNo(String eCorpUserCardNo) {
        this.eCorpUserCardNo = eCorpUserCardNo;
    }

    public String geteCorpLicenseCode() {
        return eCorpLicenseCode;
    }

    public void seteCorpLicenseCode(String eCorpLicenseCode) {
        this.eCorpLicenseCode = eCorpLicenseCode;
    }

    public String geteCorpLicenseFileName() {
        return eCorpLicenseFileName;
    }

    public void seteCorpLicenseFileName(String eCorpLicenseFileName) {
        this.eCorpLicenseFileName = eCorpLicenseFileName;
    }

    @Override
    public String toString() {
        return "EContractServiceInfoVo{" +
                "eCorpName='" + eCorpName + '\'' +
                ", eCorpUserName='" + eCorpUserName + '\'' +
                ", eCorpUserPhone='" + eCorpUserPhone + '\'' +
                ", eCorpUserCardNo='" + eCorpUserCardNo + '\'' +
                ", eCorpLicenseCode='" + eCorpLicenseCode + '\'' +
                ", eCorpLicenseFileName='" + eCorpLicenseFileName + '\'' +
                '}';
    }
}
