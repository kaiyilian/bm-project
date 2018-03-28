package com.bumu.bran.admin.prospective.service;

import com.bumu.bran.employee.command.ProspectiveConfigCommand;
import com.bumu.bran.employee.result.ProspectiveConfigResult;
import com.bumu.common.SessionInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 待入职配置service
 * @author majun
 * @date 2017/8/15
 * @email 351264830@qq.com
 */
@Transactional
public interface ProspectiveConfigService {

    void add(ProspectiveConfigCommand prospectiveConfigCommand, SessionInfo sessionInfo);

    void update(ProspectiveConfigCommand prospectiveConfigCommand, SessionInfo sessionInfo);

    ProspectiveConfigResult get(SessionInfo sessionInfo);
}
