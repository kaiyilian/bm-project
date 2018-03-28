package com.bumu.bran.admin.corporation.service;

import com.bumu.bran.admin.corporation.controller.command.UpdateCheckinMessageCommand;
import com.bumu.bran.admin.corporation.controller.command.UpdateDepartmentCommand;
import com.bumu.bran.admin.corporation.result.*;
import com.bumu.bran.admin.notification.controller.command.PostNewNotificationCommand;
import com.bumu.bran.admin.notification.result.NotificationListResult;
import com.bumu.bran.common.command.CorpOpLogQueryCommand;
import com.bumu.common.Node;
import com.bumu.common.result.TxVersionResult;
import com.bumu.exception.AryaServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Transactional
public interface BranCorpService {

    /**
     * 根据企业用户ID查询所属企业ID
     *
     * @param corpUserId
     * @return
     */
    String getBranCorpIdByCorpUserId(String corpUserId) throws AryaServiceException;

    /**
     * 查询企业所有部门或者指定部门下的所有部门
     *
     * @param branCorpId
     * @param parentDepartmentId
     * @return
     * @throws AryaServiceException
     */
    DepartmentListResult getCorpDepartments(String branCorpId, String parentDepartmentId) throws AryaServiceException;

    /**
     * 判断部门是否能被删除
     *
     * @param branCorpId
     * @param departmentId
     * @return
     * @throws AryaServiceException
     */
    Boolean isCorpDepartmentCanDelete(String branCorpId, String departmentId) throws AryaServiceException;

    /**
     * 查询企业所有工段
     *
     * @param branCorpId
     * @return
     * @throws AryaServiceException
     */
    WorkLineListResult getCorpAllWorkLines(String branCorpId) throws Exception;

    /**
     * 判断工段是否能被删除
     *
     * @param branCorpId
     * @param workLineId
     * @return
     * @throws AryaServiceException
     */
    Boolean isCorpWorkLineCanDelete(String branCorpId, String workLineId) throws AryaServiceException;

    /**
     * 查询企业所有班组
     *
     * @param branCorpId
     * @return
     * @throws AryaServiceException
     */
    WorkShiftListResult getCorpAllWorkShifts(String branCorpId) throws Exception;

    /**
     * 判断班组是否能被删除
     *
     * @param branCorpId
     * @param workShiftId
     * @return
     * @throws AryaServiceException
     */
    Boolean isCorpWorkShiftCanDelete(String branCorpId, String workShiftId) throws AryaServiceException;

    /**
     * 查询企业所有职位
     *
     * @param branCorpId
     * @return
     * @throws AryaServiceException
     */
    PositionListResult getCorpAllPositions(String branCorpId) throws Exception;

    /**
     * 判断职位是否能被删除
     *
     * @param branCorpId
     * @param positionId
     * @return
     * @throws AryaServiceException
     */
    Boolean isCorpPositionCanDelete(String branCorpId, String positionId) throws AryaServiceException;

    /**
     * 批量修改，删除或新增部门
     *
     * @param command
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    void departmentsCUD(UpdateDepartmentCommand command, String branCorpId, String operator) throws AryaServiceException;

    /**
     * 新增部门
     *
     * @param departmentName
     * @param branCorpId
     * @param operator
     * @return
     * @throws AryaServiceException
     */
    CreateDepartmentResult addCorpDepartment(String parentDepartmentId, String departmentName, String branCorpId,
                                             String operator) throws AryaServiceException;

    /**
     * 修改部门
     *
     * @param departmentId
     * @param departmentName
     * @param branCorpId
     * @param operator
     * @param txVersion
     * @throws AryaServiceException
     */
    TxVersionResult updateDepartment(String departmentId, String departmentName, String branCorpId, String operator,
                                     long txVersion) throws AryaServiceException;

    /**
     * 删除部门
     *
     * @param departmentId
     * @param branCorpId
     * @param operator
     * @param txVersion
     * @throws AryaServiceException
     */
    void deleteCorpDepartment(String departmentId, String branCorpId, String operator, long txVersion) throws AryaServiceException;

    /**
     * 新增工段
     *
     * @param workLineName
     * @param branCorpId
     * @param operator
     * @return
     * @throws AryaServiceException
     */
    CreateWorkLineResult addCorpWorkLine(String workLineName, String branCorpId, String operator) throws AryaServiceException;

    /**
     * 修改工段
     *
     * @param workLineId
     * @param workLineName
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    TxVersionResult updateWorkLine(String workLineId, String workLineName, String branCorpId, String operator, long txVersion) throws AryaServiceException;

    /**
     * 删除工段
     *
     * @param workLineId
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    void deleteCorpWorkLine(String workLineId, String branCorpId, String operator, long txVersion) throws AryaServiceException;

    /**
     * 新增班组
     *
     * @param workShift
     * @param branCorpId
     * @param operator
     * @return
     * @throws AryaServiceException
     */
    CreateWorkShiftResult addCorpWorkShift(String workShift, String branCorpId, String operator) throws AryaServiceException;

    /**
     * 修改班组
     *
     * @param workShiftId
     * @param workShiftName
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    TxVersionResult updateWorkShift(String workShiftId, String workShiftName, String branCorpId, String operator,
                                    long txVersion) throws AryaServiceException;

    /**
     * 删除班组
     *
     * @param workShiftId
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    void deleteCorpWorkShift(String workShiftId, String branCorpId, String operator, long txVersion) throws AryaServiceException;

    /**
     * 新增职位
     *
     * @param positionName
     * @param branCorpId
     * @param operator
     * @return
     * @throws AryaServiceException
     */
    CreatePositionResult addCorpPosition(String positionName, String branCorpId, String operator) throws AryaServiceException;

    /**
     * 修改职位
     *
     * @param positionId
     * @param positionName
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    TxVersionResult updatePosition(String positionId, String positionName, String branCorpId, String operator, long txVersion) throws AryaServiceException;

    /**
     * 删除职位
     *
     * @param positionId
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    void deleteCorpPosition(String positionId, String branCorpId, String operator, long txVersion) throws AryaServiceException;

    /**
     * 查询企业所有离职原因
     *
     * @param branCorpId
     * @return
     * @throws AryaServiceException
     */
    LeaveReasonListResult getCorpAllLeaveReason(String branCorpId) throws AryaServiceException;

    /**
     * 新增离职原因
     *
     * @param leaveReasonName
     * @param branCorpId
     * @param operator
     * @return
     * @throws AryaServiceException
     */
    CreateLeaveReasonResult addCorpLeaveReason(String leaveReasonName, int isNotGood, String branCorpId, String operator) throws AryaServiceException;

    /**
     * 修改离职原因
     *
     * @param leaveReasonId
     * @param leaveReasonName
     * @param isBadReason
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    TxVersionResult updateLeaveReason(String leaveReasonId, String leaveReasonName, Integer isBadReason, String branCorpId, String operator, long txVersion) throws AryaServiceException;

    /**
     * 删除离职原因
     *
     * @param leaveReasonId
     * @param branCorpId
     * @param operator
     * @throws AryaServiceException
     */
    void deleteCorpLeaveReason(String leaveReasonId, String branCorpId, String operator, long txVersion) throws AryaServiceException;

    /**
     * 判断离职原因是否能被删除
     *
     * @param branCorpId
     * @param leaveReasonId
     * @return
     * @throws AryaServiceException
     */
    Boolean isCorpLeaveReasonCanDelete(String branCorpId, String leaveReasonId) throws AryaServiceException;

    /**
     * 获取分页企业历史推送消息
     *
     * @param page
     * @param pageSize
     * @param branCorpId
     * @return
     * @throws AryaServiceException
     */
    NotificationListResult getCorpNotificationList(int page, int pageSize, String branCorpId) throws AryaServiceException;

    /**
     * 删除企业历史推送消息
     *
     * @param ids
     * @throws AryaServiceException
     */
    void deleteCorpNotifications(String[] ids, String operator) throws AryaServiceException;

    /**
     * 推送新消息
     *
     * @param command 参数
     * @throws AryaServiceException
     */
    void postNewNotification(PostNewNotificationCommand command) throws AryaServiceException;

    /**
     * 获取企业详情
     *
     * @param branCorpId
     * @return
     * @throws AryaServiceException
     */
    CorpInfoResult getCorpInfoDetail(String branCorpId) throws Exception;

    /**
     * 获取企业入职提醒消息设置
     *
     * @param branCorpId
     * @return
     * @throws AryaServiceException
     */
    CheckinMessageResult getCorpCheckinMessage(String branCorpId) throws AryaServiceException;

    /**
     * 修改企业入职消息提醒配置
     *
     * @param command
     * @param operator
     * @param branCorpId
     * @throws AryaServiceException
     */
    TxVersionResult updateCorpCheckinMessage(UpdateCheckinMessageCommand command, String operator, String branCorpId) throws AryaServiceException;

    /**
     * 分页过滤获取企业操作日志
     *
     * @param command
     * @return
     */
    CorpOperationListResult getCorpOperateLog(CorpOpLogQueryCommand command) throws Exception;

    /**
     * 获取企业操作的模块列表
     *
     * @param branCorpId
     * @return
     */
    OpModuleListResult getCorpOpModuleList(String branCorpId) throws Exception;

    /**
     * 获取操作类型列表
     *
     * @param moduleId
     * @param branCorpId
     * @return
     */
    OpTypeListResult getCorpOpTypeList(String moduleId, String branCorpId) throws Exception;

    /**
     * 导出公司的组织架构
     * @param corpId 公司ID
     * @return
     */
    List<Node> export(String corpId) throws Exception;

    /**
     * 获取所有的部门,并且统计人数
     *
     * @param branCorpId
     * @param
     * @return
     * @throws AryaServiceException
     */
    List<DepartmentListResult.DepartmentResult> getCorpDepartments(String branCorpId) throws Exception;
}
