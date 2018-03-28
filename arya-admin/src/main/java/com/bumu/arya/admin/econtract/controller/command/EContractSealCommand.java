package com.bumu.arya.admin.econtract.controller.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 电子合同印章
 *
 * @author majun
 * @date 2017/5/31
 */

public interface EContractSealCommand {

    @ApiModel
    class EContractSealBase {

        @NotBlank(message = "arya公司id必填")
        @ApiModelProperty(value = "arya公司id", required = true)
        private String aryaCorpId;

        public String getAryaCorpId() {
            return aryaCorpId;
        }

        public void setAryaCorpId(String aryaCorpId) {
            this.aryaCorpId = aryaCorpId;
        }

        @Override
        public String toString() {
            return "EContractSealBase{" +
                    "aryaCorpId='" + aryaCorpId + '\'' +
                    '}';
        }
    }

    @ApiModel
    class EContractSealAdd extends EContractSealBase {

        @ApiModelProperty(value = "印章文件/图片名称, 可以上传多个", required = true)
        @Size(max = 10, message = "最多添加10个印章")
        @NotEmpty(message = "至少上传一个印章文件名称")
        private List<String> sealFileNames;

        public List<String> getSealFileNames() {
            return sealFileNames;
        }

        public void setSealFileNames(List<String> sealFileNames) {
            this.sealFileNames = sealFileNames;
        }

        @Override
        public String toString() {
            return "EContractSealAdd{" +
                    "sealFileNames=" + sealFileNames +
                    "} " + super.toString();
        }
    }
}
