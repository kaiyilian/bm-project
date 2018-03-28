package com.bumu.arya.salary.service;

import com.bumu.arya.model.document.SysJournalDocument;
import com.bumu.arya.model.entity.SysLogEntity;
import org.slf4j.Logger;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/7/14
 */
@Transactional
public interface SalaryLogService {
    void successLog(int opType, StringBuffer logMsg, Logger log);

    void failedLog(int opType, StringBuffer logMsg, Logger log);

    List<SysJournalDocument> findLogs(int opType);
}
