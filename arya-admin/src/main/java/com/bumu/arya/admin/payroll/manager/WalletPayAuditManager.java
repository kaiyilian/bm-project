package com.bumu.arya.admin.payroll.manager;

import com.bumu.common.result.DownloadResult;
import com.bumu.paysalary.command.WalletPaySalaryApplyDetailCommand;

public interface WalletPayAuditManager {
    void approve(Long id, String account) throws Exception;
    void reject(Long id,String account);

    DownloadResult export(WalletPaySalaryApplyDetailCommand command);
}
