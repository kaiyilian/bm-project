package com.bumu.bran.admin.econtract.service;

import com.bumu.common.SessionInfo;
import com.bumu.econtract.result.EContractSealResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2017/6/20
 */
@Transactional
public interface EContractSealBranAdminService {
    List<EContractSealResult> getList(SessionInfo sessionInfo);

}
