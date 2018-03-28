package com.bumu.arya.admin.corporation.controller.command;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author majun
 * @date 2018/1/3
 * @email 351264830@qq.com
 */
public class CorpUserPermCommand {

    @ApiModelProperty
    private String id;
    @ApiModelProperty
    private String name;
    @ApiModelProperty
    private	String permissionCode;
    @ApiModelProperty
    private	String permDesc;
    @ApiModelProperty
    private	Integer selected;
    @ApiModelProperty
    private	String parentId;

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

    public Integer getSelected() {
        if(selected == null){
            return 0;
        }
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPermDesc() {
        return permDesc;
    }

    public void setPermDesc(String permDesc) {
        this.permDesc = permDesc;
    }
}
