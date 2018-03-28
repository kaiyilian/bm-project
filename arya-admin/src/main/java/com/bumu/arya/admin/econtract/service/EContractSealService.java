package com.bumu.arya.admin.econtract.service;

import com.bumu.arya.admin.econtract.controller.command.EContractSealCommand;
import com.bumu.econtract.result.EContractSealResult;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.BaseResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 电子合同印章
 * @author majun
 * @date 2017/6/1
 */
@Transactional
public interface EContractSealService {
    List<BaseResult.IDResult> batchAdd(EContractSealCommand.EContractSealAdd command) throws Exception;

    void batchDelete(BaseCommand.BatchIds command) throws Exception;

    Pager<EContractSealResult> getPageList(String aryaCorpId, Integer page, Integer pageSize);

}
