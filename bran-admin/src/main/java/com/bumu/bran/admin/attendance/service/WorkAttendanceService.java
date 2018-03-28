package com.bumu.bran.admin.attendance.service;

import com.bumu.bran.attendance.result.*;
import com.bumu.bran.attendance.command.NewWorkAttendanceCleanCommand;
import com.bumu.bran.attendance.command.NewWorkAttendanceUpdateCommand;
import com.bumu.bran.attendance.command.WorkAttendanceCommand;
import com.bumu.bran.attendance.command.WorkAttendanceQueryCommand;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.FileUploadFileResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author majun
 * @date 2017/3/21
 */
@Transactional
public interface WorkAttendanceService {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    WorkAttendanceStateResult getState();

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    WorkAttendanceOneMonthResult getOneDayOrMonthList(WorkAttendanceQueryCommand command, SessionInfo sessionInfo);

    WorkAttendanceEmpMonthResult getEmpMonthDataList(Long yearMonth, String settingId, String empId, Integer isOnJob, SessionInfo sessionInfo) throws Exception;

    void updateOne(WorkAttendanceCommand command, SessionInfo sessionInfo);

    /**
     * 修改考勤
     * @param command
     * @param sessionInfo
     */
    void update(NewWorkAttendanceUpdateCommand command, SessionInfo sessionInfo) throws Exception;

    /**
     * 打开修改考勤，获取详情
     * @param workAttendId
     * @return
     */
    NewWorkAttendanceViewResult view(String workAttendId) throws Exception ;

    void clean(NewWorkAttendanceCleanCommand command, SessionInfo sessionInfo);

    List<NewWorkAttendanceModifyResult> modifyList(String workAttendanceId);

    FileUploadFileResult getExporUrl(String empId, Long yearMonth, String settingId, Integer isObJob) throws Exception;

    void exportList(String empId, Long yearMonth, String settingId, Integer isOnJob, SessionInfo sessionInfo, HttpServletResponse response) throws Exception;
}
