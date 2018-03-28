package com.bumu.arya.admin.corporation.controller.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by CuiMengxin on 16/7/26.
 */
@ApiModel
public class DeleteCorpCommand {

    @ApiModelProperty("集团或公司id")
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeleteCorpCommand() {

    }
}
