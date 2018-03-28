package com.bumu.arya.admin.system.service.impl;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.system.controller.command.OpLogListCommand;
import com.bumu.arya.admin.common.result.OperateTypeListResult;
import com.bumu.arya.admin.system.result.SysLogListResult;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.model.SysJournalRepository;
import com.bumu.arya.model.document.SysJournalDocument;
import com.bumu.arya.response.SimpleIdNameResult;
import com.bumu.common.result.Pager;
import com.bumu.common.util.MapUtils;
import com.bumu.exception.AryaServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bumu.arya.common.OperateConstants.operateMap;

/**
 * Created by CuiMengxin on 16/7/26.
 */
@Service
public class OpLogServiceImpl implements OpLogService {

    @Autowired
    SysUserService sysUserService;


    @Autowired
    SysJournalRepository sysJourRepo;

    @Override
    public void successLog(int opType, StringBuffer logMsg, Logger log) {
        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        logMsg.append("成功!操作人id:" + currentUser.getId());
        sysJourRepo.success(opType, currentUser.getId(), currentUser.getLoginName(), currentUser.getRealName(), logMsg.toString());
        log.info(logMsg.toString());
    }

    @Override
    public void failedLog(int opType, StringBuffer logMsg, Logger log) {
        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        logMsg.append("失败!操作人id:" + currentUser.getId());
        sysJourRepo.fail(opType, currentUser.getId(), currentUser.getLoginName(), currentUser.getRealName(), logMsg.toString());
        log.info(logMsg.toString());
    }

    @Override
    public SysLogListResult getLogList(OpLogListCommand cmd) throws AryaServiceException {
        SysLogListResult listResult = new SysLogListResult();
        List<SysLogListResult.SysLogResult> results = new ArrayList<>();
        listResult.setLogs(results);
        Pager<SysJournalDocument> logEntities = sysJourRepo.findPaginationByOperateTypeKeyword(
                cmd.getOperateType(), cmd.getOpLoginName(), cmd.getKeyword(), cmd.getPage(), cmd.getPage_size());
        for (SysJournalDocument sysJour : logEntities.getResult()) {
            SysLogListResult.SysLogResult logResult = new SysLogListResult.SysLogResult();
            logResult.setId(sysJour.getId());
            logResult.setContent(MapUtils.toString(sysJour.getExtInfo()));
            if (StringUtils.isNotBlank(sysJour.getOpRealName())) {
                logResult.setOperator(sysJour.getOpRealName());
            } else {
                logResult.setOperator(sysJour.getOpLoginName());
            }
            logResult.setLoginName(sysJour.getOpLoginName());
            logResult.setOperateTime(sysJour.getCreateTime().getTime());
            logResult.setOperateType(sysJour.getOpType());
            logResult.setOperateTypeName(operateMap.get(sysJour.getOpType()));
            logResult.setStatus(sysJour.getOpSuccess() == 1 ? "成功" : "失败");
            results.add(logResult);
        }
        listResult.setPages(Utils.calculatePages(logEntities.getRowCount(), cmd.getPage_size()));
        listResult.setTotalRows(logEntities.getRowCount());
        return listResult;
    }

    @Override
    public OperateTypeListResult getAllOperateTypeList() throws AryaServiceException {
        OperateTypeListResult listResult = new OperateTypeListResult();
        List<SimpleIdNameResult> types = new ArrayList<>();
        listResult.setTypes(types);
        for (Integer key : operateMap.keySet()) {
            SimpleIdNameResult simpleIdNameResult = new SimpleIdNameResult();
            simpleIdNameResult.setId(key.toString());
            simpleIdNameResult.setName(operateMap.get(key));
            types.add(simpleIdNameResult);
        }
        return listResult;
    }
}
