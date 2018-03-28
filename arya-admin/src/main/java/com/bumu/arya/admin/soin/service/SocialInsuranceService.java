package com.bumu.arya.admin.soin.service;

import com.bumu.arya.admin.soin.result.SoinImportResult;
import com.bumu.exception.AryaServiceException;

import java.util.List;

/**
 * 社保service
 * Created by bumu-zhz on 2015/11/5.
 */
public interface SocialInsuranceService {

    /**
     * 社保导入方法
     * @param companyId 公司ID
     * @param cityId 城市ID
     * @param soinTypeId 社保类型ID
     * @param list
     * @return
     */
    List<SoinImportResult.SoinErrorMsgBean> insert(String companyId, String cityId, String soinTypeId, List<SoinImportResult.SoinOutputBean> list) throws AryaServiceException;
}
