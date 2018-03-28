package com.bumu.bran.admin.approval.service;

import com.bumu.approval.command.ApprovalTypeSettingCommand;
import com.bumu.approval.result.ApprovalTypeSettingResult;
import com.bumu.bran.admin.system.result.KeyValueResult;
import com.bumu.common.SessionInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2017/10/14
 * @email 351264830@qq.com
 */
@Transactional
public interface ApprovalTypeSettingService {

    int holidayTypeCount = 10;
    int overTimeTypeCount = 3;
    int overTimeTypeFirstIndex = 10;

    void commit(Integer approvalType,List<ApprovalTypeSettingCommand> approvalTypeSettingCommands, SessionInfo sessionInfo);

    List<ApprovalTypeSettingResult> get(Integer approvalType, SessionInfo sessionInfo);

    List<KeyValueResult> allType();

    List<KeyValueResult> allDetailType();
}
