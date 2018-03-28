package com.bumu.bran.admin.msg_center.service;

import com.bumu.bran.msgcenter.result.MsgCenterResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author majun
 * @date 2016/7/03
 */
@Transactional
public interface MsgCenterService {
    Pager<MsgCenterResult> getPagerList(Integer page, Integer pageSize, SessionInfo sessionInfo);

    void setState(BaseCommand.IDCommand IDCommand);
}
