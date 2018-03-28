package com.bumu.bran.admin.econtract.service;

import com.bumu.bran.admin.econtract.result.EContractDetailResult;
import com.bumu.bran.admin.econtract.result.EContractInfoResult;
import com.bumu.bran.econtract.command.EContractCommand;
import com.bumu.bran.econtract.command.EContractSetStateCommand;
import com.bumu.bran.econtract.command.EContractUpdateCommand;
import com.bumu.bran.econtract.model.dao.impl.EContractMybatisQuery;
import com.bumu.bran.econtract.result.EContractResult2;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * @author majun
 * @date 2016/6/20
 */
@Transactional
public interface EContractService {
    void create(EContractCommand eContractCommand, SessionInfo sessionInfo) throws Exception;

    void update(EContractUpdateCommand eContractCommand, SessionInfo sessionInfo) throws Exception;

    Pager<EContractResult2> getPageList(EContractMybatisQuery eContractMybatisQuery, Integer page, Integer pageSize, SessionInfo sessionInfo);

    EContractDetailResult detail(String id);

    EContractInfoResult info(String id);

    void send(BaseCommand.BatchIds ids, SessionInfo sessionInfo) throws Exception;

    void del(BaseCommand.BatchIds ids, SessionInfo sessionInfo);

    void setState(EContractSetStateCommand eContractSetStateCommand, SessionInfo sessionInfo) throws Exception;

    String preview(String id) throws Exception;

    String download(String id, HttpServletResponse response) throws Exception;
}
