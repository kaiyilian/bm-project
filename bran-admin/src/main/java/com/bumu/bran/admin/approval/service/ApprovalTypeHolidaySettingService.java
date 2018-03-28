package com.bumu.bran.admin.approval.service;

import com.bumu.approval.result.ApprovalTypeSettingResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2017/10/25
 * @email 351264830@qq.com
 */
@Transactional
public interface ApprovalTypeHolidaySettingService {


    List<ApprovalTypeSettingResult> all();
}
