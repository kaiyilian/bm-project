package com.bumu.bran.admin.main.service;

import com.bumu.arya.command.PagerCommand;
import com.bumu.bran.admin.main.controller.command.EmpIdCardNoExpireTimeSetCommand;
import com.bumu.bran.employee.command.ModelCommand;
import com.bumu.bran.home.result.EmpBirthdayWarningResult;
import com.bumu.bran.home.result.EmpProspectiveWarningResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BatchCommand;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

/**
 * majun
 */
@Transactional
public interface IndexService {
	Map<String, Object> getScheduleViews(ModelCommand modelCommand);

    Pager<EmpBirthdayWarningResult> getEmpBirthdayWarning(PagerCommand pagerCommand, SessionInfo sessionInfo);

    void disposeEmpBirthdayWarning(Set<com.bumu.common.command.ModelCommand> ids, SessionInfo sessionInfo);

    Pager<EmpBirthdayWarningResult> getEmpIdCardNoWarning(PagerCommand pagerCommand, SessionInfo sessionInfo);

    void setEmpIdCardNoExpireTime(EmpIdCardNoExpireTimeSetCommand empIdCardNoExpireTimeSetCommand, SessionInfo sessionInfo);

    Pager<EmpProspectiveWarningResult> getEmpProspectiveWarning(PagerCommand pagerCommand, SessionInfo sessionInfo);

    void disposeEmpProspectiveWarning(BatchCommand batch, SessionInfo sessionInfo);

    FileUploadFileResult exportEmpProspectiveWarning(SessionInfo sessionInfo) throws Exception;

}
