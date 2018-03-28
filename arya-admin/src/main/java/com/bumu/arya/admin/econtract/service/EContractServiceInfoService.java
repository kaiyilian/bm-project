package com.bumu.arya.admin.econtract.service;

import com.bumu.arya.admin.econtract.controller.command.EContractServiceInfoCommand;
import com.bumu.arya.admin.econtract.result.EContractServiceInfoResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author majun
 * @date 2017/7/10
 * @email 351264830@qq.com
 */
@Transactional
public interface EContractServiceInfoService {

    @Transactional(readOnly = true)
    EContractServiceInfoResult getDetail(String aryaCorpId) throws Exception;


    void submit(EContractServiceInfoCommand command) throws Exception;
}


