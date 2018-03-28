package com.bumu.arya.salary.result;

import com.bumu.arya.salary.model.entity.CustomerAccountEntity;
import com.bumu.common.result.Pager;
import com.bumu.common.result.PaginationResult;
import com.bumu.function.ResultConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/4
 */
@ApiModel
public class CustomerAccountTotalResult extends PaginationResult {

    /**
     * 台账汇总list
     */
    @ApiModelProperty(value = "台账汇总list", name = "resultList")
    List<CustomerAccountTotalQueryResult> resultList;

    /**
     * 台账汇总分页pager
     */
    @ApiModelProperty(value = "台账汇总分页pager", name = "resultPager")
    List<CustomerAccountTotalQueryResult> resultPager;

    /**
     * 台账汇总合计
     */
    @ApiModelProperty(value = "台账汇总合计", name = "resultCount")
    CustomerAccountTotalQueryResult resultCount;

    public List<CustomerAccountTotalQueryResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<CustomerAccountTotalQueryResult> resultList) {
        this.resultList = resultList;
    }

    public List<CustomerAccountTotalQueryResult> getResultPager() {
        return resultPager;
    }

    public void setResultPager(List<CustomerAccountTotalQueryResult> resultPager) {
        this.resultPager = resultPager;
    }

    public CustomerAccountTotalQueryResult getResultCount() {
        return resultCount;
    }

    public void setResultCount(CustomerAccountTotalQueryResult resultCount) {
        this.resultCount = resultCount;
    }
}
