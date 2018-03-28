package com.bumu.arya.salary.command;

import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.common.SessionInfo;
import com.bumu.function.EntityConverter;
import com.bumu.function.VoConverterFunction;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

import static org.apache.commons.jexl2.parser.ParserConstants.not;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@ApiModel
public class SalaryBillDeleteCommand {

    @ApiModelProperty(value = "需要删除的开票记录", name = "ids")
    @NotEmpty(message = "需要选中删除记录")
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
