package com.bumu.bran.admin.econtract.result;

import com.bumu.common.result.BaseResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Comparator;

/**
 * @author majun
 * @date 2017/6/19
 */
@ApiModel
public class EContractLoopsKeyValueResult extends BaseResult.IDResult implements Comparator<EContractLoopsKeyValueResult> {

    @ApiModelProperty(hidden = true)
    private String keyParam;
    @ApiModelProperty(value = "填写项的key")
    private String key;
    @ApiModelProperty(value = "填写项的value")
    private String value;
    @ApiModelProperty(value = "填写方 0: 甲 1: 乙")
    private Integer writerType;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getWriterType() {
        return writerType;
    }

    public void setWriterType(Integer writerType) {
        this.writerType = writerType;
    }

    public String getKeyParam() {
        return keyParam;
    }

    public void setKeyParam(String keyParam) {
        this.keyParam = keyParam;
    }

    @Override
    public int compare(EContractLoopsKeyValueResult one, EContractLoopsKeyValueResult other) {
        int indexOne = one.getKeyParam().indexOf("loops") + 5;
        int indexOther = other.getKeyParam().indexOf("loops") + 5;
        return Integer.valueOf(one.getKeyParam().substring(indexOne)) - Integer.valueOf(other.getKeyParam().substring(indexOther));
    }
}
