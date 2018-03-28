package com.bumu.bran.admin.econtract.service;

import com.bumu.bran.admin.econtract.result.EContractLoopsKeyValueResult;
import com.bumu.common.SessionInfo;
import com.bumu.econtract.result.EContractTemplateResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2017/6/20
 */
@Transactional
public interface EContractTemplateBranAdminService {
    List<EContractTemplateResult> getList(SessionInfo sessionInfo);

    List<EContractLoopsKeyValueResult> getLoops(String id, SessionInfo sessionInfo);
}
