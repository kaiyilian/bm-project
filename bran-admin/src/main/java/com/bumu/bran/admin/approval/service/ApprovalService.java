package com.bumu.bran.admin.approval.service;

import com.bumu.approval.model.entity.ApprovalEntity;
import com.bumu.approval.command.ApprovalManagerQueryCommand;
import com.bumu.arya.command.PagerCommand;
import com.bumu.bran.admin.approval.result.ApprovalManagerResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BatchCommand;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author majun
 * @date 2017/10/14
 * @email 351264830@qq.com
 */
@Transactional
public interface ApprovalService {
    Pager<ApprovalManagerResult> get(ApprovalManagerQueryCommand approvalManagerQueryCommand, PagerCommand pagerCommand);

    ApprovalManagerResult detail(String approvalId, SessionInfo sessionInfo);

    void pass(BatchCommand batch, SessionInfo sessionInfo);

    void fail(BatchCommand batch, SessionInfo sessionInfo);

    void export(ApprovalManagerQueryCommand approvalManagerQueryCommand, SessionInfo sessionInfo, HttpServletResponse httpServletResponse);

}
