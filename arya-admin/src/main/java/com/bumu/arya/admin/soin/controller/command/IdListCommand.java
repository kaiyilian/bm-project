package com.bumu.arya.admin.soin.controller.command;

import com.bumu.arya.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created by CuiMengxin on 16/8/7.
 * 通用删除命令
 */
@ApiModel
public class IdListCommand {

    List<IdCommand> ids;

    public List<IdCommand> getIds() {
        return ids;
    }

    public void setIds(List<IdCommand> ids) {
        this.ids = ids;
    }

    public IdListCommand() {

    }

    public static class IdCommand {

        @ApiModelProperty("id")
        String id;

        public IdCommand() {
        }

        @NotBlank(message = ErrorCode.CODE_VALIDATION_ID_BLANK)
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
