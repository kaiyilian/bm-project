package com.bumu.bran.admin.employee.service;

import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.bran.admin.corporation.result.CreateProspectiveEmployeeResult;
import com.bumu.bran.admin.employee.controller.command.RosterCommand;
import com.bumu.bran.admin.employee.controller.command.SelectModelResult;
import com.bumu.bran.admin.employee.result.*;
import com.bumu.bran.admin.prospective.controller.command.AcceptProspectiveEmployeeCommand;
import com.bumu.bran.admin.prospective.result.ProspectiveCheckResult;
import com.bumu.bran.admin.push.vo.CheckinMessageSendTimeModel;
import com.bumu.bran.admin.system.command.IdVersionsCommand;
import com.bumu.bran.employee.command.EmployeePagedListCommand;
import com.bumu.bran.employee.result.EmployeeResult;
import com.bumu.bran.employee.result.QueryEmpResult;
import com.bumu.bran.employee.result.QueryLeaveEmpResult;
import com.bumu.bran.employee.result.RosterResult;
import com.bumu.bran.model.entity.BranUserEntity;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.ModelResult;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.exception.AryaServiceException;
import com.bumu.leave_emp.model.entity.LeaveEmployeeEntity;
import com.bumu.prospective.command.SaveProspectiveEmployeeCommand;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Transactional
public interface EmployeeService {

	/**
	 * 新增待入职员工
	 *
	 * @param command       传递的参数
	 * @param bindingResult 异常的参数
	 * @return
	 */
	CreateProspectiveEmployeeResult addProspectiveEmployee(SaveProspectiveEmployeeCommand command,
                                                           BindingResult bindingResult) throws Exception;

	/**
	 * 修改待入职员工
	 *
	 * @param command
	 * @param branCorpId
	 * @param operateUserId
	 */
	UpdateProspectiveEmployeeResult updateProspectiveEmployee(SaveProspectiveEmployeeCommand command, String branCorpId,
															  String operateUserId) throws Exception;

	/**
	 * 批量删除待入职员工
	 *
	 * @param employeeIds
	 * @param operateUser
	 * @param branCorpId
	 * @throws AryaServiceException
	 */
	void deleteProspectiveEmployees(Map<String, Long> employeeIds, String operateUser, String branCorpId) throws Exception;

	/**
	 * 判断在员工是否已经存在
	 *
	 * @param phoneNo
	 * @param branCorpId
	 * @return
	 */
	String isEmployeeExisted(String phoneNo, String branCorpId);

	/**
	 * 获取资料完成项数
	 *
	 * @param aryaUserEntity
	 * @param branUserEntity
	 * @return
	 */
	int getProfileCompleteCount(AryaUserEntity aryaUserEntity, BranUserEntity branUserEntity);

	int getProfileCompleteCount(EmployeeEntity employeeEntity) throws InvocationTargetException, IllegalAccessException;

	int getProfileCompleteCount(LeaveEmployeeEntity leaveEmployeeEntity) throws Exception;

	/**
	 * 获取资料完成进度
	 *
	 * @param aryaUserEntity
	 * @param branUserEntity
	 * @return
	 */
	String getProfileProgress(AryaUserEntity aryaUserEntity, BranUserEntity branUserEntity);

	/**
	 * 分页查询待入职员工名单
	 *
	 * @param command
	 * @return
	 */
	EmployeeListPaginationResult getProspectiveEmployeePagedList(EmployeePagedListCommand command) throws Exception;

	/**
	 * 分页查询在职员工名单
	 *
	 * @param command
	 * @return
	 */
	EmployeeListPaginationResult getEmployeePagedList(EmployeePagedListCommand command);

	/**
	 * 分页查询在职员工名单
	 *
	 * @param command
	 * @return
	 */
	EmployeeListPaginationResult getEmployeePagedSortList(EmployeePagedListCommand command) throws Exception;

	/**
	 * 分页查询离职员工名单
	 *
	 * @param command
	 * @return
	 */
	EmployeeListPaginationResult getLeaveEmployeePagedList(EmployeePagedListCommand command);

	EmployeeListPaginationResult getLeaveEmployeePagedSortListNew(EmployeePagedListCommand command) throws Exception;

	void acceptProspectiveEmployees(AcceptProspectiveEmployeeCommand command) throws Exception;

	/**
	 * 获取在职员工详情
	 *
	 * @param employeeId
	 * @param branCorpId
	 * @return
	 * @throws AryaServiceException
	 */
	EmployeeDetailResult getEmployeeDetail(String employeeId, String branCorpId) throws Exception;


	/**
	 * 获取在职员工详情
	 *
	 * @param employeeId
	 * @param branCorpId
	 * @param employeeStatus
	 * @return
	 * @throws AryaServiceException
	 */
	EmployeeDetailResult getEmployeeDetailForEmp(String employeeId, String branCorpId, int employeeStatus) throws Exception;

	/**
	 * 获取在职员工详情
	 *
	 * @param currentUserId
	 * @param employeeId
	 * @param branCorpId
	 * @param employeeStatus
	 * @return
	 * @throws AryaServiceException
	 */
	EmployeeDetailResult getEmployeeDetailForLeave(String currentUserId, String employeeId, String branCorpId, int employeeStatus) throws Exception;


	/**
	 * 退工
	 *
	 * @param idMap
	 * @param leaveReasonId
	 * @param leaveTime
	 * @param remarks
	 * @param branCorpId
	 * @param operator
	 * @throws AryaServiceException
	 */
	void employeesLeave(Map<String, Long> idMap, String leaveReasonId, Long leaveTime, String remarks,
						String branCorpId, String operator) throws Exception;

	/**
	 * 删除考勤机中离职员工
	 * @param ids
	 * @param sessionInfo
	 */
	void AttendanceMachineEmployeesLeave(List<String> ids,SessionInfo sessionInfo) throws Exception;

	/**
	 * 续签
	 *
	 * @param ids
	 * @param contractStartTime
	 * @param contractEndTime
	 * @param branCorpId
	 * @param operator
	 * @throws AryaServiceException
	 */
	void contractExtension(Map<String, Long> ids, Long contractStartTime, Long contractEndTime, String branCorpId,
						   String operator) throws Exception;

	/**
	 * 导入带入职员工,并且把excel保存至文件夹
	 *
	 * @param file       需要导入的xls文件
	 * @param branCorpId 公司id
	 * @return
	 */
	ImportProspectiveResult importEmployees(MultipartFile file, String operator, String branCorpId) throws Exception;

	/**
	 * 确认导入待入职员工,新增至数据库
	 *
	 * @param file_id    已经保存在本地的excel文件
	 * @param branCorpId 公司id
	 * @return
	 */
	ImportEmpConfirmResult importEmployeesConfirm(String file_id,
												  String fileTypeStr,
												  String operator,
												  String branCorpId,
												  BindingResult bindingResult) throws Exception;

	/**
	 * 导出待入职员工
	 *
	 * @param prospectiveExportCommand
	 * @param httpServletResponse
	 */
	void prospectiveExport(EmployeePagedListCommand prospectiveExportCommand, String operator,
								   HttpServletResponse httpServletResponse) throws Exception;

	/**
	 * 导出正式员工
	 *
	 * @param prospectiveExportCommand
	 * @param response
	 */
	FileUploadFileResult employeeExport(EmployeePagedListCommand prospectiveExportCommand, String operator,
										HttpServletResponse response) throws Exception;

	/**
	 * 导出离职员工
	 *
	 * @param employeePagedListCommand
	 * @param response
	 */
	void leaveExport(EmployeePagedListCommand employeePagedListCommand, String operator, HttpServletResponse response)
			throws Exception;

	void attachmentDownload(String[] phones, String operator, HttpServletResponse response, Integer type,
							int employeeType, HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 计算企业的消息发送时间
	 *
	 * @param checkinTime
	 * @param branCorpId
	 * @return
	 */
	CheckinMessageSendTimeModel calculateCorpCheckinMessageSendTime(long checkinTime, String branCorpId) throws ParseException;

	/**
	 * 计算消息发送时间
	 *
	 * @param checkinTime
	 * @param beforeDay
	 * @param postHour
	 * @return
	 * @throws ParseException
	 */
	CheckinMessageSendTimeModel calculateCheckinMessageSendTime(long checkinTime, int beforeDay, int postHour) throws ParseException;

	/**
	 * 修改入职提醒手机号码
	 *
	 * @param fromPhone
	 * @param toPhone
	 * @param branCorpId
	 * @param checkinTime
	 */
	void changeCheckinMessageJobPhoneNo(String fromPhone, String toPhone, String branCorpId, long checkinTime) throws ParseException;

	/**
	 * 修改入职提醒时间
	 *
	 * @param phone
	 * @param branCorpId
	 * @param newCheckinTime
	 */
	void changeCheckinMessageJobSendTime(String phone, String branCorpId, long newCheckinTime) throws ParseException;

	/**
	 * 删除入职提醒
	 *
	 * @param phone
	 */
	void deleteCheckinMessageJob(String phone);

	void deleteLeaveEmployee(IdVersionsCommand command) throws Exception;

	DistractResult location(String id, int type) throws Exception;

	Map update(RosterCommand command) throws Exception;

	SelectModelResult<RosterResult> expiration(RosterCommand command) throws Exception;

	SelectModelResult<RosterResult> probation(RosterCommand command) throws Exception;

	SelectModelResult<ModelResult> probationProcess(IdVersionsCommand command) throws Exception;

	EmployeeResult getId(String id, SessionInfo sessionInfo) throws Exception;

	/**
	 * 首页显示已经完成入职流程的待入职员工
	 *
	 * @param command
	 * @return
	 */
	Map<String, Object> acceptOfferUsers(RosterCommand command) throws Exception;

    List<QueryEmpResult> getAllEmpListByBranCorpId(SessionInfo sessionInfo);

	List<QueryLeaveEmpResult> getAllLeaveEmpListByBranCorpId(SessionInfo sessionInfo);

    List<ProspectiveCheckResult> checkProspectiveEmployees(BaseCommand.BatchIds command, SessionInfo sessionInfo);
}
