package com.bumu.arya.admin.econtract.service;

import com.bumu.arya.admin.econtract.controller.command.EContractTemplateCommand;
import com.bumu.econtract.result.EContractTemplateDetailResult;
import com.bumu.econtract.result.EContractTemplateResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.BaseResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

/**
 * 电子合同模板
 * @author majun
 * @date 2017/6/1
 */
@Transactional
public interface EContractTemplateService {
    BaseResult.IDResult add(EContractTemplateCommand.EContractTemplateAdd command, SessionInfo sessionInfo);

    void update(EContractTemplateCommand.EContractTemplateUpdate command, SessionInfo sessionInfo);

    EContractTemplateDetailResult detail(String contractTemplateId);

    Pager<EContractTemplateResult> getPageList(String aryaCorpId, Integer page, Integer pageSize);

    void batchDelete(BaseCommand.BatchIds command);
}

