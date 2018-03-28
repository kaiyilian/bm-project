package com.bumu.bran.admin.work_shift_rule.service;

import com.bumu.arya.command.PagerCommand;
import com.bumu.bran.admin.work_shift_rule.controller.command.BranOneDayWorkShiftRuleCommand;
import com.bumu.bran.admin.work_shift_rule.controller.command.BranWorkShiftRuleCommand;
import com.bumu.bran.admin.work_shift_rule.result.AvailableWorkShiftEmpResult;
import com.bumu.bran.admin.work_shift_rule.result.AvailableWorkShiftResult;
import com.bumu.bran.admin.work_shift_rule.result.BranRuleResult;
import com.bumu.bran.service.BranCommonScheduleService;
import com.bumu.bran.workshift.command.WorkShiftRuleCommand;
import com.bumu.bran.workshift.command.WorkShiftRuleQueryCommand;
import com.bumu.bran.workshift.result.ScheduleViewResult;
import com.bumu.bran.workshift.result.WorkShiftRuleDetailResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.ModelResult;
import com.bumu.common.result.Pager;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author majun
 * @date 2016/12/19
 */
@Transactional
public interface BranScheduleService extends BranCommonScheduleService{

	Map<String, Object> getScheduleColors() throws Exception;

	void setRules(BranWorkShiftRuleCommand workShiftRuleCommand, SessionInfo sessionInfo) throws Exception;

	Pager<ScheduleViewResult> getRules(WorkShiftRuleQueryCommand workShiftRuleQueryCommand, PagerCommand pagerCommand, SessionInfo sessionInfo) throws Exception;

	void publishRules(WorkShiftRuleCommand workShiftRuleCommand) throws Exception;

	void export(WorkShiftRuleQueryCommand workShiftRuleCommand, HttpServletResponse httpServletResponse) throws Exception;

	List<BranRuleResult> getRuleList(WorkShiftRuleCommand workShiftRuleCommand);

	WorkShiftRuleDetailResult getRuleDetails(String id, SessionInfo sessionInfo) throws Exception;

	List<AvailableWorkShiftResult> getAvailableWorkShifts(String workShiftRuleId, Long executeTime, Long executeEndTime, Boolean isLoopAround, SessionInfo sessionInfo);

	String setOneDayRule(BranOneDayWorkShiftRuleCommand branOneDayWorkShiftRuleCommand, SessionInfo sessionInfo) throws Exception;

	String setRuleRevert(BranOneDayWorkShiftRuleCommand branOneDayWorkShiftRuleCommand, SessionInfo sessionInfo) throws Exception;

    List<ModelResult> getRuleNames(SessionInfo sessionInfo);

	List<AvailableWorkShiftEmpResult> getAvailableWorkShiftEmpList(SessionInfo sessionInfo, String workShitId);
}
