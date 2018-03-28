package com.bumu.arya.admin.econtract.controller.command;

import com.bumu.arya.Utils;
import com.bumu.common.SessionInfo;
import com.bumu.common.validator.annotation.UpdateValidatedGroup;
import com.bumu.econtract.constant.EContractEnum;
import com.bumu.econtract.model.EContractTemplateLoopsAdd;
import com.bumu.econtract.model.entity.EContractTemplateEntity;
import com.bumu.function.EntityConverter;
import com.bumu.function.VoConverterFunction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 电子合同模板
 *
 * @author majun
 * @date 2017/5/31
 */

public interface EContractTemplateCommand {

    @ApiModel
    class EContractTemplateBase {
        @NotNull(message = "合同类型必填")
        @ApiModelProperty(value = "合同类型 0: 劳动合同 1: 社保合同", required = true)
        private Integer contractType;

        @NotNull(message = "公司id必填")
        @ApiModelProperty(value = "arya公司id", required = true)
        private String aryaCorpId;

        @NotNull(message = "云签模板ID必填")
        @ApiModelProperty(value = "云签模板ID", required = true)
        private String yunSignTemplateId;

        @NotNull(message = "合同模板文件必填")
        @ApiModelProperty(value = "合同模板文件ID", required = true)
        private String uploadFileId;

        public Integer getContractType() {
            return contractType;
        }

        public void setContractType(Integer contractType) {
            this.contractType = contractType;
        }

        public String getAryaCorpId() {
            return aryaCorpId;
        }

        public void setAryaCorpId(String aryaCorpId) {
            this.aryaCorpId = aryaCorpId;
        }

        public String getYunSignTemplateId() {
            return yunSignTemplateId;
        }

        public void setYunSignTemplateId(String yunSignTemplateId) {
            this.yunSignTemplateId = yunSignTemplateId;
        }

        public String getUploadFileId() {
            return uploadFileId;
        }

        public void setUploadFileId(String uploadFileId) {
            this.uploadFileId = uploadFileId;
        }
    }

    @ApiModel
    class EContractTemplateAdd extends EContractTemplateBase implements EntityConverter<EContractTemplateEntity>,
            VoConverterFunction.Add<EContractTemplateEntity, SessionInfo> {

        @NotEmpty(message = "至少添加一个填写项")
        @ApiModelProperty(value = "合同模板填写项, 数组可以是多个", required = true)
        private List<EContractTemplateLoopsAdd> loops;

        public List<EContractTemplateLoopsAdd> getLoops() {
            return loops;
        }

        public void setLoops(List<EContractTemplateLoopsAdd> loops) {
            this.loops = loops;
        }

        @Override
        public void begin(EContractTemplateEntity entity, SessionInfo info) {
            entity.setId(Utils.makeUUID());
            entity.setCreateTime(System.currentTimeMillis());
            entity.setCreateUser(info.getUserId());
        }

        @Override
        public void convert(EContractTemplateEntity eContractTemplateEntity) {
            eContractTemplateEntity.setYunSignTemplateId(getYunSignTemplateId());
            eContractTemplateEntity.setAryaCorpId(getAryaCorpId());
            eContractTemplateEntity.setFileName(getUploadFileId());
            eContractTemplateEntity.setContractType(EContractEnum.ContractType.values()[getContractType()]);
        }
    }

    @ApiModel
    class EContractTemplateUpdate extends EContractTemplateBase implements EntityConverter<EContractTemplateEntity>,
            VoConverterFunction.Add<EContractTemplateEntity, SessionInfo> {

        @ApiModelProperty(value = "id", required = true)
        @NotBlank(message = "{bran.admin.id.require}", groups = {UpdateValidatedGroup.class})
        private String id;

        @NotEmpty(message = "至少添加一个填写项")
        @ApiModelProperty(value = "合同模板填写项, 数组可以是多个", required = true)
        private List<EContractTemplateLoopsAdd> loops;

        public List<EContractTemplateLoopsAdd> getLoops() {
            return loops;
        }

        public void setLoops(List<EContractTemplateLoopsAdd> loops) {
            this.loops = loops;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public void convert(EContractTemplateEntity eContractTemplateEntity) {

            eContractTemplateEntity.setAryaCorpId(getAryaCorpId());
            eContractTemplateEntity.setContractType(EContractEnum.ContractType.values()[getContractType()]);
            eContractTemplateEntity.setYunSignTemplateId(getYunSignTemplateId());
            eContractTemplateEntity.setFileName(getUploadFileId());
        }

        @Override
        public void begin(EContractTemplateEntity eContractTemplateEntity, SessionInfo info) {
            eContractTemplateEntity.setUpdateTime(System.currentTimeMillis());
            eContractTemplateEntity.setUpdateUser(info.getUserId());
        }
    }
}
