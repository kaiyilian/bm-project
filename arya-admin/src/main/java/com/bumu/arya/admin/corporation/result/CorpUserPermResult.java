package com.bumu.arya.admin.corporation.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2018/1/3
 */
@ApiModel
public class CorpUserPermResult {

    @ApiModelProperty
    private String id;
    @ApiModelProperty
    private String name;
    @ApiModelProperty
    private	String permissionCode;
    @ApiModelProperty
    private	String permDesc;
    @ApiModelProperty
    private	String parentId;
    @ApiModelProperty
    private	Integer selected;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private	String reg;
    public CorpUserPermResult() {
    }
    public CorpUserPermResult(String id, String permissionCode) {
        this.id = id;
        this.permissionCode = permissionCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermDesc() {
        return permDesc;
    }

    public void setPermDesc(String permDesc) {
        this.permDesc = permDesc;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }
}
