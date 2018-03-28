package com.bumu.bran.admin.work_shift_rule.service;

import com.bumu.bran.workshift.command.NewWorkShiftRuleCommand;
import com.bumu.bran.workshift.result.WorkShiftRuleDetailResult;
import com.bumu.bran.workshift.result.WorkShiftTypeResult;
import com.bumu.bran.service.BranCommonScheduleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author majun
 * @date 2016/12/19
 */
@Transactional
public interface BranNewScheduleService extends BranCommonScheduleService {

    /**
     * 查询排版规律
     * @param id
     * @return
     */
    List<WorkShiftRuleDetailResult> getNewRuleDetails(String id);
    /**
     * 查询排版规律对应的全部班次
     * @return
     */
    List<WorkShiftTypeResult> getNewRuleType(NewWorkShiftRuleCommand newWorkShiftRuleCommand);

    /**
     * 社否启用排版规律
     * @param id
     * @param isUser
     * @return
     */
    Boolean setUseRule(String id, int isUser);

    /**
     * 关闭排版规律
     * @param id
     * @return
     */
    Boolean closeRule(String id);

}
