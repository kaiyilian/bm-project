package com.bumu.arya.salary.service.impl;

import com.bumu.arya.admin.model.SysUserModel;
import com.bumu.arya.admin.service.SysUserService;
import com.bumu.arya.model.SysJournalRepository;
import com.bumu.arya.model.SysLogDao;
import com.bumu.arya.model.document.SysJournalDocument;
import com.bumu.arya.model.entity.SysLogEntity;
import com.bumu.arya.salary.service.SalaryLogService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CuiMengxin on 16/7/26.
 */
@Service
public class SalaryLogServiceImpl implements SalaryLogService {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysLogDao sysLogDao;

    @Autowired
    SysJournalRepository sysJournalRepository;

    @Override
    public void successLog(int opType, StringBuffer logMsg, Logger log) {
        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        logMsg.append("成功!操作人id:" + currentUser.getId());
        log.info(logMsg.toString());
        sysJournalRepository.success(opType, currentUser.getId(), currentUser.getLoginName(), currentUser.getRealName(), logMsg.toString());
    }

    @Override
    public void failedLog(int opType, StringBuffer logMsg, Logger log) {
        SysUserModel currentUser = sysUserService.getCurrentSysUser();
        logMsg.append("失败!操作人id:" + currentUser.getId());
        log.info(logMsg.toString());
        sysJournalRepository.fail(opType, currentUser.getId(), currentUser.getLoginName(), currentUser.getRealName(), logMsg.toString());
    }

    @Override
    public List<SysJournalDocument> findLogs(int opType) {
        return sysJournalRepository.findListByOperateTypeKeyword(opType, null, null);
    }
}
