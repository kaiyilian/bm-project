package com.bumu.arya.admin.corporation.result;

import com.bumu.arya.model.entity.CorporationEntity;

import java.util.List;

/**
 * 公司查询返回Result
 * Created by bumu-zhz on 2015/11/11.
 */
public class CorporationRetrieveResult {

    private List<CorporationEntity> corporations;

    public List<CorporationEntity> getCorporations() {
        return corporations;
    }

    public void setCorporations(List<CorporationEntity> corporations) {
        this.corporations = corporations;
    }
}
