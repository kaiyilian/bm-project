package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by CuiMengxin on 16/7/26.
 */
@ApiModel
public class CreateUpdateCorpResult {
    @ApiModelProperty("集团或公司Id")
    @JsonProperty("corp_id")
    String corpId;

    @ApiModelProperty("名称")
    String name;

    @ApiModelProperty("新增或修改类型 1.集团 2.子公司 3.部门 4.一级公司")
    int type;

    @ApiModelProperty("是否是新增 1.是 0.否")
    @JsonProperty("is_new")
    int isNew;

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    @ApiModelProperty("上级公司id")
    @JsonProperty("parent_id")
    String parentId;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public CreateUpdateCorpResult() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
