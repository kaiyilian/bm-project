package com.bumu.arya.admin.system.service;

import com.bumu.arya.admin.system.controller.command.OpLogListCommand;
import com.bumu.arya.admin.common.result.OperateTypeListResult;
import com.bumu.arya.admin.system.result.SysLogListResult;
import com.bumu.exception.AryaServiceException;
import org.slf4j.Logger;

/**
 * 系统操作日志
 * Created by CuiMengxin on 16/7/26.
 */
public interface OpLogService {

    /**
     * 输出日志,保存日志
     *
     * @param opType
     * @param logMsg
     */
    void successLog(int opType, StringBuffer logMsg, Logger log);

    void failedLog(int opType, StringBuffer logMsg, Logger log);

    /**
     * 分页获取系统日志
     *
     * @return
     * @throws AryaServiceException
     */
    SysLogListResult getLogList(OpLogListCommand command) throws AryaServiceException;

    /**
     * 获取所有操作类型
     *
     * @return
     * @throws AryaServiceException
     */
    OperateTypeListResult getAllOperateTypeList() throws AryaServiceException;
}
