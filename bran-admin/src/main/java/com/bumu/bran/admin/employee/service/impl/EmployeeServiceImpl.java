package com.bumu.bran.admin.employee.service.impl;

import com.bumu.SysUtils;
import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.DistrictDao;
import com.bumu.arya.model.SysLogDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.arya.model.entity.DistrictEntity;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceUserRefuseDao;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceUserSynDao;
import com.bumu.attendance.model.dao.WorkAttendanceDeviceWorkShiftDao;
import com.bumu.attendance.model.entity.WorkAttendanceDeviceUserSynEntity;
import com.bumu.attendance.model.entity.WorkAttendanceDeviceWorkShiftEntity;
import com.bumu.bran.admin.config.service.BranAdminConfigService;
import com.bumu.bran.admin.corporation.result.CreateProspectiveEmployeeResult;
import com.bumu.bran.admin.corporation.service.FileService;
import com.bumu.bran.admin.employee.controller.command.RosterCommand;
import com.bumu.bran.admin.employee.controller.command.SelectModelResult;
import com.bumu.bran.admin.employee.helper.EmpHelper;
import com.bumu.bran.admin.employee.helper.UserImportHelper;
import com.bumu.bran.admin.employee.helper.UserSaveHelper;
import com.bumu.bran.admin.employee.result.*;
import com.bumu.bran.admin.employee.result.EmployeeDetailResult.EmployeeDetailAttachmentResult;
import com.bumu.bran.admin.employee.result.EmployeeDetailResult.EmployeeDetailCareerResult;
import com.bumu.bran.admin.employee.result.EmployeeDetailResult.EmployeeDetailEducationResult;
import com.bumu.bran.admin.employee.result.EmployeeDetailResult.EmployeeDetailProfileResult;
import com.bumu.bran.admin.employee.service.EmployeeService;
import com.bumu.bran.admin.employee.util.ZipCompressing;
import com.bumu.bran.admin.employee_defined.helper.UserDefinedDetailsHelper;
import com.bumu.bran.admin.prospective.controller.command.AcceptProspectiveEmployeeCommand;
import com.bumu.bran.admin.prospective.helper.ProspectiveEntryStatus;
import com.bumu.bran.admin.prospective.helper.ProspectiveHelper;
import com.bumu.bran.admin.prospective.helper.ProspectiveRuleBuilder;
import com.bumu.bran.admin.prospective.result.ProspectiveCheckResult;
import com.bumu.bran.admin.push.PushProspectiveEmployeeAcceptNotice;
import com.bumu.bran.admin.push.vo.CheckinMessageSendTimeModel;
import com.bumu.bran.admin.push.vo.EmployeeUserModel;
import com.bumu.bran.admin.system.command.IdVersionsCommand;
import com.bumu.bran.attendance.service.WorkAttendanceEmpService;
import com.bumu.bran.common.Constants;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import com.bumu.bran.common.service.CommonBranCorpService;
import com.bumu.bran.common.util.ExcelExportUtils;
import com.bumu.bran.corporation.model.dao.BranCorpCheckinMsgDao;
import com.bumu.bran.corporation.model.entity.BranCorpCheckinMessageEntity;
import com.bumu.bran.econtract.model.dao.ContractDao;
import com.bumu.bran.employee.command.*;
import com.bumu.bran.employee.helper.InductionHelper;
import com.bumu.bran.employee.model.dao.*;
import com.bumu.bran.employee.model.dao.mybatis.EmployeeMybatisDao;
import com.bumu.bran.employee.model.entity.*;
import com.bumu.bran.employee.result.*;
import com.bumu.bran.employee.service.impl.EmpExcelExportServiceImpl;
import com.bumu.bran.employee.service.impl.LeaveExcelServiceImpl;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.bran.model.dao.BranCorporationDao;
import com.bumu.bran.model.dao.BranUserDao;
import com.bumu.bran.model.entity.*;
import com.bumu.bran.prospective.command.ProspectiveQueryCommand;
import com.bumu.bran.service.ScheduleService;
import com.bumu.bran.setting.model.dao.*;
import com.bumu.career.model.dao.CareerDao;
import com.bumu.career.model.entity.CareerEntity;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.model.SendManySmsMessage;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.ModelResult;
import com.bumu.common.result.Pager;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.service.PushService;
import com.bumu.common.service.RedisService;
import com.bumu.common.util.*;
import com.bumu.education.model.dao.EducationDao;
import com.bumu.education.model.entity.EducationEntity;
import com.bumu.employee.constant.EmployeeConstants;
import com.bumu.employee.model.entity.EmployeeDetailEntity;
import com.bumu.employee.model.entity.EmployeeEntity;
import com.bumu.employee.util.EmpImportUtils;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import com.bumu.exception.ExceptionModel;
import com.bumu.leave_cert.model.dao.LeaveCertDao;
import com.bumu.leave_cert.model.entity.LeaveCertEntity;
import com.bumu.leave_emp.model.entity.LeaveEmployeeEntity;
import com.bumu.prospective.command.SaveProspectiveEmployeeCommand;
import com.bumu.prospective.helper.CheckUserTran;
import com.bumu.prospective.helper.InductionCommonHelper;
import com.bumu.prospective.model.entity.ProspectiveEmployeeEntity;
import com.bumu.worksn_prefix.command.WorkSnFormatCommand;
import com.bumu.worksn_prefix.model.dao.WorkSnPrefixDao;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

import static com.bumu.bran.handler.ServiceHandler.NO;
import static com.bumu.bran.handler.ServiceHandler.YES;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author CuiMengxin
 * @date 2016/5/11
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static Logger logger = getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CommonBranCorpService commonBranCorpService;

    @Autowired
    private ProspectiveEmployeeDao prospectiveEmployeeDao;

    @Autowired
    private BranUserDao branUserDao;

    @Autowired
    private AryaUserDao aryaUserDao;

    @Autowired
    private LeaveCertDao leaveCertDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private LeaveEmployeeDao leaveEmployeeDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CareerDao careerDao;

    @Autowired
    private EducationDao educationDao;

    @Autowired
    private LeaveReasonDao leaveReasonDao;

    @Autowired
    private BranAdminConfigService configService;

    @Autowired
    private FileService fileService;

    @Resource
    private UserImportHelper userImportHelper;

    @Resource
    private UserSaveHelper userSaveHelper;

    @Resource
    private ExcelExportHelper excelExportHelper;

    @Resource
    private BranAdminConfigService branAdminConfigService;

    @Resource
    private BranCorporationDao branCorporationDao;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private BranCorpCheckinMsgDao corpCheckinMessageDao;

    @Resource
    private PushService pushService;

    @Resource
    private BranOpLogDao branOpLogDao;

    @Resource
    private DistrictDao districtDao;

    @Resource
    private WorkSnPrefixDao workSnPrefixDao;

    @Resource
    private EmpHelper empHelper;

    @Resource
    private CareerEmpDao careerEmpDao;

    @Resource
    private EducationEmpDao educationEmpDao;

    @Resource
    private LeaveCertEmpDao leaveCertEmpDao;

    @Resource
    private ProspectiveHelper prospectiveHelper;

    @Resource
    private InductionHelper inductionHelper;

    @Autowired
    private UserDefinedDetailsHelper userDefinedDetailsHelper;

    @Autowired
    private UserDefinedDetailsDao userDefinedDetailsDao;

    @Autowired
    private UserDefinedColsDao userDefinedColsDao;

    @Autowired
    private EmpExcelExportServiceImpl empExcelHandler;

    @Autowired
    private LeaveExcelServiceImpl leaveExcelHandler;

    @Autowired
    private EmployeeMybatisDao employeeMybatisDao;

    @Autowired
    private WorkAttendanceEmpService workAttendanceEmpService;

    @Autowired
    private SendManySmsMessage sendManySmsMessage;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private WorkLineDao workLineDao;

    @Autowired
    private WorkShiftDao workShiftDao;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ProspectiveRuleBuilder prospectiveRuleBuilder;

    @Autowired
    private ProspectiveEntryStatus.ProspectiveEntryStatusProcess prospectiveEntryStatusProcess;

    @Autowired
    private WorkAttendanceDeviceUserRefuseDao workAttendanceDeviceUserRefuseDao;

    @Autowired
    private WorkAttendanceDeviceWorkShiftDao workAttendanceDeviceWorkShiftDao;

    @Autowired
    private WorkAttendanceDeviceUserSynDao workAttendanceDeviceUserSynDao;

    @Autowired
    private RedisService redisService;

    private Logger log = getLogger(EmployeeServiceImpl.class);

    @Override
    public CreateProspectiveEmployeeResult addProspectiveEmployee(SaveProspectiveEmployeeCommand command,
                                                                  BindingResult bindingResult) throws Exception {

        CreateProspectiveEmployeeResult result = new CreateProspectiveEmployeeResult();
        ProspectiveEmployeeEntity prospectiveEmployeeEntity = null;
        // 验证参数异常
        Assert.paramsNotError(bindingResult, new ExceptionModel());
        prospectiveEmployeeEntity = commonBranCorpService.
                getProspectiveEntityByParams(command.getBranCorpId(), command.getPhoneNo());

        command.setCreateType(Constants.HR_CREATE);
        if (prospectiveEmployeeEntity == null) {
            prospectiveEmployeeEntity = new ProspectiveEmployeeEntity();
            log.debug("addProspectiveEmployee 创建... ");
            prospectiveEmployeeEntity.createBefore(command);
        } else {
            log.debug("addProspectiveEmployee 更新... ");
            prospectiveEmployeeEntity.updateBefore(command);
//            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, command.getPhoneNo() + "在待入职里已经存在");
        }

        // 判断在花名册中是否已经存在
        EmployeeEntity employeeEntity = employeeDao.findEmployeeByPhoneAndBranCorpId(command.getBranCorpId(), command.getPhoneNo());
        if (employeeEntity != null) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, command.getPhoneNo() + "在花名册里已经存在");
        }

        // 判断该员工手机号是否曾经在该公司离过职
        prospectiveHelper.checkIsLeaving(command, result);
        // 判断是否需要提醒不良记录
        prospectiveHelper.checkHasManyBadRecords(command, result, prospectiveEmployeeEntity);
        log.debug("ProspectiveEmployeeEntity id: " + prospectiveEmployeeEntity.getId());
        prospectiveHelper.setOtherPropOnSave(prospectiveEmployeeEntity, command);
        // 添加待入职员工
        prospectiveEmployeeDao.persist(prospectiveEmployeeEntity);
        // 发送入职天数提醒短消息
        // %s提醒您，距离%s入职还有%d天。【不木科技】
        prospectiveHelper.sendProspectiveMsg(command);
        // 发送企业码短消息
        // %s提醒您，请在招才进宝手机app提交公司所需资料，企业码为%s。欢迎您的加入！【不木科技】
        prospectiveHelper.sendTipMsg(command, prospectiveEmployeeEntity);
        // 添加日志
        SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
        info.setMsg("新增待入职员工：" + prospectiveEmployeeEntity.getFullName() + "，手机号为：" + prospectiveEmployeeEntity.getPhoneNo());
        branOpLogDao.success(BranOpLogEntity.OP_MODULE_PROSPECTIVE_EMPLOYEE, BranOpLogEntity.OP_TYPE_UPDATE,
                command.getUserId(), info);

        // 返回响应信息
        result.setEmployeeId(prospectiveEmployeeEntity.getId());
        result.setVersion(prospectiveEmployeeEntity.getTxVersion());
        return result;
    }

    @Override
    public UpdateProspectiveEmployeeResult updateProspectiveEmployee(SaveProspectiveEmployeeCommand command,
                                                                     String branCorpId, String operateUserId)
            throws Exception {

        UpdateProspectiveEmployeeResult result = new UpdateProspectiveEmployeeResult();
        //先判断手机号是否合法
        if (!SysUtils.checkPhoneNo(command.getPhoneNo())) {
            throw new AryaServiceException(ErrorCode.CODE_VALID_PHONENO_WRONG);
        }
        ProspectiveEmployeeEntity employeeEntity = prospectiveEmployeeDao.findProspectiveEmployeeById(command.getEmployeeId());
        TxVersionUtil.compireVersion(employeeEntity.getTxVersion(), command.getVersion());
        //手机号是否被其他待入职员工使用
        ProspectiveEmployeeEntity exist = prospectiveEmployeeDao.findProspectiveEmployeeByPhoneNoAndBranCorpId(
                command.getPhoneNo(), branCorpId);
        // 不是本人
        if (exist != null && exist.getPhoneNo() != null && !exist.getPhoneNo().equals(employeeEntity.getPhoneNo())) {
            if (exist.getCreateType() != null && exist.getCreateType() == Constants.APP_CREATE) {
                exist.setWorkLineId(employeeEntity.getWorkLineId());
                exist.setWorkLineName(employeeEntity.getWorkLineName());
                exist.setWorkShiftId(employeeEntity.getWorkShiftId());
                exist.setWorkShiftName(employeeEntity.getWorkShiftName());
                exist.setDepartmentId(employeeEntity.getDepartmentId());
                exist.setDepartmentName(employeeEntity.getDepartmentName());
                exist.setPositionId(employeeEntity.getPositionId());
                exist.setPositionName(employeeEntity.getPositionName());
                exist.setCheckinTime(employeeEntity.getCheckinTime());
                exist.setCreateType(Constants.HR_CREATE);
                prospectiveEmployeeDao.update(exist);
                employeeEntity.setIsDelete(Constants.TRUE);
                prospectiveEmployeeDao.update(employeeEntity);
            } else {
                // 是否存在同一待入职员工手机号
                throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEE_PHONE_CONFLICT);
            }

        }
        //是否已存在同一在职员工
        if (isEmployeeExisted(command.getPhoneNo(), branCorpId) != null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEE_PHONE_BE_EMPLOYEE_USED);
        }

        //判断该员工手机号是否曾经在该公司离过职
        String leaveEmployeeId = leaveEmployeeDao.findLeaveEmployeeByPhoneNo(command.getPhoneNo(), branCorpId);
        if (StringUtils.isNotBlank(leaveEmployeeId)) {
            result.setMsg("注意：该员工 " + command.getName() + " 的手机号码 " + command.getPhoneNo() + " 曾在您公司有离职记录。");
        }

        List<String> msgs = new ArrayList<>();
        String tempFullName = employeeEntity.getFullName();
        if (!command.getPhoneNo().equals(employeeEntity.getPhoneNo())) {
            try {
                if (command.getCheckInTime() != null) {
                    changeCheckinMessageJobPhoneNo(employeeEntity.getPhoneNo(), command.getPhoneNo(), branCorpId, command.getCheckInTime());
                } else {
                    changeCheckinMessageJobPhoneNo(employeeEntity.getPhoneNo(), command.getPhoneNo(), branCorpId, employeeEntity.getCheckinTime());
                }
                log.info("修改入职提醒任务的手机号码" + employeeEntity.getPhoneNo() + "为" + command.getPhoneNo() + "。");
            } catch (Exception e) {
                log.error("修改入职提醒任务的手机号码" + employeeEntity.getPhoneNo() + "为" + command.getPhoneNo() + "失败。");
            }
            msgs.add(String.format("手机号%s为%s。", employeeEntity.getPhoneNo(), command.getPhoneNo()));
        }

        if (StringUtils.isNotBlank(command.getName())) {
            msgs.add(String.format("姓名为%s。", command.getName()));
            employeeEntity.setFullName(command.getName());
        }

        if (StringUtils.isNotBlank(command.getPositionId())) {
            String newPositionName = commonBranCorpService.getPositionNameById(command.getPositionId());
            msgs.add(String.format("职位%s为%s。", commonBranCorpService.getPositionNameById(command.getPositionId()), newPositionName));
            employeeEntity.setPositionId(command.getPositionId());
            employeeEntity.setPositionName(newPositionName);
        }

        if (StringUtils.isNotBlank(command.getWorkLineId())) {
            String newWorkLineName = commonBranCorpService.getWorkLineNameById(command.getWorkLineId());
            msgs.add(String.format("工段%s为%s。", commonBranCorpService.getWorkLineNameById(command.getWorkLineId()), newWorkLineName));
            employeeEntity.setWorkLineId(command.getWorkLineId());
            employeeEntity.setWorkLineName(newWorkLineName);
        }

        if (StringUtils.isNotBlank(command.getWorkShiftId())) {
            String newWorkShiftName = commonBranCorpService.getWorkShiftNameById(command.getWorkShiftId());
            msgs.add(String.format("班组%s为%s。", commonBranCorpService.getWorkShiftNameById(command.getWorkShiftId()), newWorkShiftName));
            employeeEntity.setWorkShiftId(command.getWorkShiftId());
            employeeEntity.setWorkShiftName(newWorkShiftName);
        }

        if (StringUtils.isNotBlank(command.getDepartmentId())) {
            String newDepartmentName = commonBranCorpService.getDepartmentNameById(command.getDepartmentId());
            msgs.add(String.format("部门%s为%s。", commonBranCorpService.getDepartmentNameById(command.getDepartmentId()), newDepartmentName));
            employeeEntity.setDepartmentId(command.getDepartmentId());
            employeeEntity.setDepartmentName(newDepartmentName);
        }

        if (command.getCheckInTime() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            msgs.add(String.format("入职时间%s为%s。", format.format(command.getCheckInTime()), format.format(command.getCheckInTime())));
            employeeEntity.setCheckinTime(command.getCheckInTime());
            //修改入职提醒时间
            try {
                if (StringUtils.isNotBlank(command.getPhoneNo())) {
                    changeCheckinMessageJobSendTime(command.getPhoneNo(), branCorpId, command.getCheckInTime());
                    log.info("修改" + command.getPhoneNo() + "入职提醒时间为" + command.getCheckInTime() + "。");
                } else {
                    changeCheckinMessageJobSendTime(employeeEntity.getPhoneNo(), branCorpId, command.getCheckInTime());
                    log.info("修改" + employeeEntity.getPhoneNo() + "入职提醒时间为" + command.getCheckInTime() + "。");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                log.error("修改入职提醒时间为" + command.getCheckInTime() + "失败。");
            }
        }
        try {
            employeeEntity.setTxVersion(employeeEntity.getTxVersion() + 1);
            if (msgs.size() > 0) {
                SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
                info.setMsg(String.format("修改待入职员工%s的%s", tempFullName, StringUtils.join(msgs, "，")));
                branOpLogDao.success(BranOpLogEntity.OP_MODULE_PROSPECTIVE_EMPLOYEE, BranOpLogEntity.OP_TYPE_UPDATE, operateUserId, info);
            }
            result.setVersion(employeeEntity.getTxVersion());

        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEE_UPDATE_FAILED);
        }

        // 判断修改的手机号 是否与原来的手机号相同
        if (!employeeEntity.getPhoneNo().equals(command.getPhoneNo())) {

            employeeEntity.setPhoneNo(command.getPhoneNo());

            // 如果修改的手机号不相同,流程要重新开始判断
            BranUserEntity branUserEntity = branUserDao.findBranUserByPhoneNoAndCorpId(command.getPhoneNo(), branCorpId);
            if (branUserEntity == null) {
                employeeEntity.setAcceptOffer(NO);
            } else {
                employeeEntity.setAcceptOffer(YES);
            }


            // 如果手机号不相同,并且今日修改次数不超过限制次数
            if (employeeEntity.getPhoneNoModifyCount() <= branAdminConfigService.getModifyCount()) {
                // 查询公司
                BranCorporationEntity branCorporationEntity = branCorporationDao.findCorpById(branCorpId);
                if (branCorporationEntity != null) {
                    String msg = String.format(branAdminConfigService.getProspectiveSMS_Msg(), branCorporationEntity.getCorpName(), branCorporationEntity.getCheckinCode());
                    log.info("msg: " + msg);
                    sendManySmsMessage.init(msg, new ArrayList<String>() {{
                        add(employeeEntity.getPhoneNo());
                    }});
                    sendManySmsMessage.run();
                }
                employeeEntity.setPhoneNoModifyCount(employeeEntity.getPhoneNoModifyCount() + 1);
            } else {
                log.info(employeeEntity.getPhoneNo() + "修改手机号的次数超过上限，不会发送短消息");
                if (StringUtils.isNotBlank(result.getMsg())) {
                    result.setMsg(result.getMsg() + "\n修改手机号的次数超过上限，不会发送短消息");
                } else {
                    result.setMsg("修改手机号的次数超过上限，不会发送短消息");
                }
            }
        }
        prospectiveEmployeeDao.update(employeeEntity);

        return result;
    }

    @Override
    public void deleteProspectiveEmployees(Map<String, Long> employeeIds, String operateUser, String branCorpId)
            throws Exception {
        if (employeeIds.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_PROSPECTIVE_EMPLOYEE_SELECT_NONE);// 没有选中待入职员工
        }
        List<String> ids = employeeIds.keySet().stream().collect(Collectors.toList());
        List<ProspectiveEmployeeEntity> employeeEntities = prospectiveEmployeeDao.findProspectiveEmployeesByIds(ids, branCorpId);
        if (employeeEntities.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEE_NOT_FOUND);// 没有找到待入职员工
        }
        SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
        StringBuffer msg = new StringBuffer("删除待入职员工：");
        List<String> employeeNames = new ArrayList<>();
        for (ProspectiveEmployeeEntity employeeEntity : employeeEntities) {
            TxVersionUtil.compireVersion(employeeEntity.getTxVersion(), employeeIds.get(employeeEntity.getId()));
            employeeNames.add(employeeEntity.getFullName());
            // 通过手机号 + 企业编号
            BranUserEntity branUserEntity = branUserDao.findBranUserByPhoneNoAndCorpId(employeeEntity.getPhoneNo(),
                    employeeEntity.getBranCorpId());
            if (branUserEntity == null) {
                log.debug("没有查询到branUserEntity: ");
            } else {
                log.debug("查询到branUserEntity: ");
                log.debug("清空branUserEntity的corpId ");
                branUserEntity.setBranCorpId(null);
                branUserDao.update(branUserEntity);
            }
            employeeEntity.setIsDelete(Constants.TRUE);
            employeeEntity.setUpdateTime(System.currentTimeMillis());
            employeeEntity.setUpdateUser(operateUser);
            //移除入职提醒任务
            deleteCheckinMessageJob(employeeEntity.getPhoneNo());
            log.info("删除" + employeeEntity.getPhoneNo() + "入职提醒任务。");
        }
        msg.append(StringUtils.join(employeeNames, "，") + "。");
        try {
            prospectiveEmployeeDao.update(employeeEntities);
            info.setMsg(msg.toString());
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_PROSPECTIVE_EMPLOYEE, BranOpLogEntity.OP_TYPE_DELETE, operateUser, info);
        } catch (Exception e) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_DELETE_FAILED);
        }
    }

    @Override
    public String isEmployeeExisted(String phoneNo, String branCorpId) {
        // 先通过手机号找到arya用户
        AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(phoneNo);
        if (aryaUserEntity == null) {
            return null;
        }
        // 在通过arya用户找到bran用户
        BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
        if (branUserEntity == null) {
            return null;
        }

        EmployeeEntity employeeEntity = employeeDao.findEmployeeByBranUserIdAndBranCorpId(branUserEntity.getId(), branCorpId);
        if (employeeEntity != null) {
            return employeeEntity.getId();
        }
        return null;
    }

    @Override
    public int getProfileCompleteCount(AryaUserEntity aryaUserEntity, BranUserEntity branUserEntity) {
        CheckUserTran checkUserTran = new CheckUserTran();
        checkUserTran.init(branUserEntity);
        checkUserTran.setIdcardNo(aryaUserEntity.getIdcardNo());
        return inductionHelper.calculationComplete(checkUserTran);
    }

    @Override
    public int getProfileCompleteCount(EmployeeEntity employeeEntity) throws InvocationTargetException, IllegalAccessException {
        CheckUserTran checkUserTran = new CheckUserTran();
        checkUserTran.init(employeeEntity);
        int completeCount = inductionHelper.calculationComplete(checkUserTran);

        return inductionHelper.calculationComplete(checkUserTran, completeCount);
    }

    @Override
    public int getProfileCompleteCount(LeaveEmployeeEntity employeeEntity) throws Exception {
        CheckUserTran checkUserTran = new CheckUserTran();
        checkUserTran.init(employeeEntity);
        int completeCount = inductionHelper.calculationComplete(checkUserTran);

        return inductionHelper.calculationComplete(checkUserTran, completeCount, 0);
    }


    @Override
    public String getProfileProgress(AryaUserEntity aryaUserEntity, BranUserEntity branUserEntity) {
        return InductionCommonHelper.getProgressStr(getProfileCompleteCount(aryaUserEntity, branUserEntity), configService.getProfileTotal());
    }

    @Override
    public EmployeeListPaginationResult getProspectiveEmployeePagedList(EmployeePagedListCommand command)
            throws Exception {
        log.debug("【查询待入职员工】 关键字：" + command.getKeyword() + " 入职时间：" + command.getCheckinStartTime() + "至" + command.getCheckinEndTime() + " 部门：" + command.getDepartmentId() + " 工段：" + command.getWorkLineId() + " 班组：" + command.getWorkShiftId() + " 职位：" + command.getPositionId());
        List<ProspectiveEmployeeEntity> prospectiveEmployeeEntities = prospectiveEmployeeDao.findPaginationProspectiveEmployees(command);
        log.debug("查询出 " + prospectiveEmployeeEntities.size() + " 个员工。");
        EmployeeListPaginationResult paginationResult = new EmployeeListPaginationResult();
        if (prospectiveEmployeeEntities.size() == 0) {
            return paginationResult;
        }
        //收集手机号，用于查出aryaUser
        Collection<String> phoneNos = new ArrayList<>();
        for (ProspectiveEmployeeEntity employeeEntity : prospectiveEmployeeEntities) {
            if (!phoneNos.contains(employeeEntity.getPhoneNo())) {
                phoneNos.add(employeeEntity.getPhoneNo());
            }
        }
        //查出aryaUser
        List<AryaUserEntity> aryaUserEntities = aryaUserDao.findUserByPhoneNos(phoneNos);
        Map<String, AryaUserEntity> phone2AryaUserMap = new HashMap();
        Collection<String> aryaUserIds = new ArrayList<>();
        for (AryaUserEntity aryaUserEntity : aryaUserEntities) {
            if (!phone2AryaUserMap.containsKey(aryaUserEntity.getPhoneNo())) {
                phone2AryaUserMap.put(aryaUserEntity.getPhoneNo(), aryaUserEntity);
            }
            if (!aryaUserIds.contains(aryaUserEntity.getId())) {
                aryaUserIds.add(aryaUserEntity.getId());
            }
        }

        //查出branUser
        List<BranUserEntity> branUserEntities = branUserDao.findBranUsersByAryaUserIds(aryaUserIds);
        Map<String, BranUserEntity> aryaUserId2BranUserMap = new HashMap<>();
        for (BranUserEntity branUserEntity : branUserEntities) {
            if (!aryaUserId2BranUserMap.containsKey(branUserEntity.getAryaUserId())) {
                aryaUserId2BranUserMap.put(branUserEntity.getAryaUserId(), branUserEntity);
            }
        }

        EmployeeListResult results = new EmployeeListResult();
        for (ProspectiveEmployeeEntity employeeEntity : prospectiveEmployeeEntities) {
            EmployeeListResult.EmployeeResult result = new EmployeeListResult.EmployeeResult();
            result.setEmployeeId(employeeEntity.getId());
            result.setName(employeeEntity.getFullName());
            result.setPositionId(employeeEntity.getPositionId());
            result.setPositionName(employeeEntity.getPositionName());
            result.setWorkShiftId(employeeEntity.getWorkShiftId());
            result.setWorkShiftName(employeeEntity.getWorkShiftName());
            result.setWorkLineId(employeeEntity.getWorkLineId());
            result.setWorkLineName(employeeEntity.getWorkLineName());
            result.setDepartmentId(employeeEntity.getDepartmentId());
            result.setDepartmentName(employeeEntity.getDepartmentName());
            result.setPhoneNo(employeeEntity.getPhoneNo());
            result.setCheckinTime(employeeEntity.getCheckinTime());
            result.setIsOfferAccept(employeeEntity.getAcceptOffer());
            result.setVersion(employeeEntity.getTxVersion());
            result.setRecordExceed(employeeEntity.getBadRecordExceed());
            // 通过手机号找到brUser
            log.info("通过手机号找到arUser");
            AryaUserEntity aryaUserEntity = phone2AryaUserMap.get(employeeEntity.getPhoneNo());
            if (aryaUserEntity != null) {
                BranUserEntity branUserEntity = aryaUserId2BranUserMap.get(aryaUserEntity.getId());
                if (branUserEntity != null) {
                    log.info("getFaceMatch: " + branUserEntity.getFaceMatch());
                    result.setFaceMatch(branUserEntity.getFaceMatch());
                    // 设置资料进度这里需要修改
                    int employeeInfoCompleteCount = prospectiveHelper.getProspectiveProfileCompleteCount(aryaUserEntity, branUserEntity);
                    result.setProfileProgress(InductionCommonHelper.getProgressStr(employeeInfoCompleteCount, configService.getRequireProfileTotal()));
                }

            } else {
                result.setProfileProgress("0%");
            }
            //用户是否提交资料，HR是否可以点击同意入职
            result.setIsProfileComplete(employeeEntity.getCheckinComplete());
            // 返回体检ID
            result.setExam_id(employeeEntity.getExamId());
            // 设置来源
            result.setCreateType(employeeEntity.getCreateType());

            result.setEntryStatus(prospectiveEntryStatusProcess.getEntryStatus(employeeEntity));
            log.info("entryStatus: " + result.getEntryStatus());
            results.add(result);
        }
        paginationResult.setEmployees(results);
        int filterRows = prospectiveEmployeeDao.findProspectiveEmployeesCount(command);
        paginationResult.setFilterRows(filterRows);
        paginationResult.setTotalRows(prospectiveEmployeeDao.findAllProspectiveEmployeesCount(command.getCreateUserId(), command.getBranCorpId()));
        paginationResult.setPages(Utils.calculatePages(filterRows, command.getPageSize()));
        return paginationResult;
    }

    @Override
    public EmployeeListPaginationResult getEmployeePagedList(EmployeePagedListCommand command) {
        EmployeeListPaginationResult paginationResult = new EmployeeListPaginationResult();
        List<EmployeeEntity> employeeEntities = employeeDao.findPaginationEmployees(command);
        Map<String, BranUserEntity> branUserId2BranUserEntityMap = new HashMap<>();
        Map<String, AryaUserEntity> aryaUserId2AryaUserEntityMap = new HashMap<>();
        if (employeeEntities.size() == 0) {
            return paginationResult;
        }
        Collection<String> branUserIds = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            if (!branUserIds.contains(employeeEntity.getBranUserId())) {
                branUserIds.add(employeeEntity.getBranUserId());
            }
        }
        List<BranUserEntity> branUserEntities = branUserDao.findBranUsersByIds(branUserIds);
        if (branUserEntities.size() == 0) {
            return paginationResult;
        }
        Collection<String> aryaUserIds = new ArrayList<>();
        for (BranUserEntity branUserEntity : branUserEntities) {
            if (!aryaUserIds.contains(branUserEntity.getAryaUserId())) {
                aryaUserIds.add(branUserEntity.getAryaUserId());
            }
            if (!branUserId2BranUserEntityMap.containsKey(branUserEntity.getId())) {
                branUserId2BranUserEntityMap.put(branUserEntity.getId(), branUserEntity);
            }
        }
        List<AryaUserEntity> aryaUserEntities = aryaUserDao.findUsersByIds(aryaUserIds);
        if (aryaUserEntities.size() == 0) {
            return paginationResult;
        }
        for (AryaUserEntity aryaUserEntity : aryaUserEntities) {
            if (!aryaUserId2AryaUserEntityMap.containsKey(aryaUserEntity.getId())) {
                aryaUserId2AryaUserEntityMap.put(aryaUserEntity.getId(), aryaUserEntity);
            }
        }
        EmployeeListResult results = new EmployeeListResult();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            EmployeeListResult.EmployeeResult result = new EmployeeListResult.EmployeeResult();
            result.setEmployeeId(employeeEntity.getId());


            result.setWorkSn(empHelper.getFormatWorkSn(employeeEntity.getWorkSnFormatCommand()));
            result.setPositionId(employeeEntity.getPositionId());
            result.setPositionName(employeeEntity.getPositionName());
            result.setWorkShiftId(employeeEntity.getWorkShiftId());
            result.setWorkShiftName(employeeEntity.getWorkShiftName());
            result.setWorkLineId(employeeEntity.getWorkLineId());
            result.setWorkLineName(employeeEntity.getWorkLineName());
            result.setDepartmentId(employeeEntity.getDepartmentId());
            result.setDepartmentName(employeeEntity.getDepartmentName());
            result.setCheckinTime(employeeEntity.getCheckinTime());
            result.setContractStartTime(employeeEntity.getStartTime());
            result.setContractEndTime(employeeEntity.getEndTime());
            BranUserEntity branUserEntity = branUserId2BranUserEntityMap.get(employeeEntity.getBranUserId());
            AryaUserEntity aryaUserEntity = aryaUserId2AryaUserEntityMap.get(branUserEntity.getAryaUserId());
            result.setName(aryaUserEntity.getRealName());
            result.setIdCardNo(aryaUserEntity.getIdcardNo());
            result.setPhoneNo(aryaUserEntity.getPhoneNo());
            result.setExam_id(employeeEntity.getExamId());
            results.add(result);
        }
        paginationResult.setEmployees(results);
//		int filterRows = employeeDao.findPaginationEmployeesCount(criteria);
        int filterRows = 0;
        paginationResult.setFilterRows(filterRows);
        paginationResult.setTotalRows(employeeDao.findAllEmployeesCount(command));
        paginationResult.setPages(Utils.calculatePages(filterRows, command.getPageSize()));
        return paginationResult;
    }

    @Override
    public EmployeeListPaginationResult getEmployeePagedSortList(EmployeePagedListCommand command) throws Exception {
        EmployeeListPaginationResult paginationResult = new EmployeeListPaginationResult();
//		List employees = employeeDao.findPaginationEmployees(command);
        Pager<EmployeeEntity> pager = employeeDao.findPaginationEmployeesNew(command);

        EmployeeListResult results = new EmployeeListResult();
        for (EmployeeEntity emp : pager.getResult()) {
            EmployeeListResult.EmployeeResult result = new EmployeeListResult.EmployeeResult();
            result.setEmployeeId(emp.getId());
            result.setCheckinTime(emp.getCheckinTime());
            result.setContractStartTime(emp.getStartTime());
            result.setContractEndTime(emp.getEndTime());
            result.setWorkSn(empHelper.getFormatWorkSn(emp.getWorkSnFormatCommand()));
            result.setDepartmentId(emp.getDepartmentId());
            result.setDepartmentName(emp.getDepartmentName());
            result.setWorkLineId(emp.getWorkLineId());
            result.setWorkLineName(emp.getWorkLineName());
            result.setWorkShiftId(emp.getWorkShiftId());
            result.setWorkShiftName(emp.getWorkShiftName());
            result.setPositionId(emp.getPositionId());
            result.setPositionName(emp.getPositionName());
            result.setName(emp.getRealName());
            result.setPhoneNo(emp.getTelephone());
            result.setVersion(emp.getTxVersion());
            // 体检报告
            result.setExam_id(emp.getExamId());
            // 打卡号
            result.setWorkAttendanceNo(emp.getWorkAttendanceNo());
            // 录入状态
            result.setWorkAttendanceAddState(emp.getWorkAttendanceAddState());
            // 注册账号
            result.setRegisterAccount(emp.getRegisterAccount());
            // 绑定状态
            result.setIsBinding(emp.getIsBinding());
            // 生日日期
            if (emp.getBornDate() != null) {
                result.setBornDate(emp.getBornDate().getTime());
            }

            result.setIdCardNo(emp.getIdCardNo());
            result.setSex(emp.getSex());
            result.setMarriage(emp.getMarriage());
            result.setNation(emp.getNation());

            result.setIdcardAddress(emp.getIdcardAddress());
            result.setPhoneNo(emp.getTelephone());
            result.setAddress(emp.getResidence());
            result.setUrgentContactPhone(emp.getUrgentContactPhone());
            result.setUrgentContact(emp.getUrgentContact());
            result.setPoliticalStatus(emp.getPoliticalStatus());
            result.setSocialSecurityType(emp.getSocialSecurityType());
            result.setDegreeOfEducation(emp.getDegreeOfEducation());
            result.setTelephone(emp.getTelephone());
            result.setGraduatedSchool(emp.getGraduatedSchool());
            if (emp.getGraduationTime() != null) {
                result.setGraduationTime(emp.getGraduationTime().getTime());
            }
            result.setProfessionalCategory(emp.getProfessionalCategory());
            result.setPoliticalStatus(emp.getPoliticalStatus());

            // 身份证有效期
            if (emp.getExpireStartTime() != null) {
                result.setExpireStartTime(emp.getExpireStartTime().getTime());
            }
            if (emp.getExpireEndTime() != null) {
                result.setExpireEndTime(emp.getExpireEndTime().getTime());
            }
            result.setIsLongTerm(emp.getIsLongTerm());

            results.add(result);
        }
        paginationResult.setEmployees(results);
        paginationResult.setTotalRows(pager.getRowCount());
        paginationResult.setPages(pager.getPage());
        return paginationResult;
    }

    @Override
    public EmployeeListPaginationResult getLeaveEmployeePagedList(EmployeePagedListCommand command) {
        EmployeeListPaginationResult paginationResult = new EmployeeListPaginationResult();
        Criteria criteria = leaveEmployeeDao.getLeaveEmployeeListCriteria(command);
        List<LeaveEmployeeEntity> employeeEntities = leaveEmployeeDao.findPaginationLeaveEmployees(criteria, command.getPage(), command.getPageSize());
        Map<String, BranUserEntity> branUserId2BranUserEntityMap = new HashMap<>();
        Map<String, AryaUserEntity> aryaUserId2AryaUserEntityMap = new HashMap<>();
        if (employeeEntities.size() == 0) {
            return paginationResult;
        }
        Collection<String> branUserIds = new ArrayList<>();
        for (LeaveEmployeeEntity employeeEntity : employeeEntities) {
            if (!branUserIds.contains(employeeEntity.getBranUserId())) {
                branUserIds.add(employeeEntity.getBranUserId());
            }
        }
        List<BranUserEntity> branUserEntities = branUserDao.findBranUsersByIds(branUserIds);
        if (branUserEntities.size() == 0) {
            return paginationResult;
        }
        Collection<String> aryaUserIds = new ArrayList<>();
        for (BranUserEntity branUserEntity : branUserEntities) {
            if (!aryaUserIds.contains(branUserEntity.getAryaUserId())) {
                aryaUserIds.add(branUserEntity.getAryaUserId());
            }
            if (!branUserId2BranUserEntityMap.containsKey(branUserEntity.getId())) {
                branUserId2BranUserEntityMap.put(branUserEntity.getId(), branUserEntity);
            }
        }
        List<AryaUserEntity> aryaUserEntities = aryaUserDao.findUsersByIds(aryaUserIds);
        if (aryaUserEntities.size() == 0) {
            return paginationResult;
        }
        for (AryaUserEntity aryaUserEntity : aryaUserEntities) {
            if (!aryaUserId2AryaUserEntityMap.containsKey(aryaUserEntity.getId())) {
                aryaUserId2AryaUserEntityMap.put(aryaUserEntity.getId(), aryaUserEntity);
            }
        }
        EmployeeListResult results = new EmployeeListResult();
        for (LeaveEmployeeEntity employeeEntity : employeeEntities) {
            EmployeeListResult.EmployeeResult result = new EmployeeListResult.EmployeeResult();
            result.setEmployeeId(employeeEntity.getId());
            result.setWorkSn(empHelper.getFormatWorkSn(employeeEntity.getWorkSnFormatCommand()));
            result.setPositionId(employeeEntity.getPositionId());
            result.setPositionName(employeeEntity.getPositionName());
            result.setWorkShiftId(employeeEntity.getWorkShiftId());
            result.setWorkShiftName(employeeEntity.getWorkShiftName());
            result.setWorkLineId(employeeEntity.getWorkLineId());
            result.setWorkLineName(employeeEntity.getWorkLineName());
            result.setDepartmentId(employeeEntity.getDepartmentId());
            result.setDepartmentName(employeeEntity.getDepartmentName());
            result.setCheckinTime(employeeEntity.getCheckinTime());
            result.setLeaveTime(employeeEntity.getLeaveTime());
            BranUserEntity branUserEntity = branUserId2BranUserEntityMap.get(employeeEntity.getBranUserId());
            AryaUserEntity aryaUserEntity = aryaUserId2AryaUserEntityMap.get(branUserEntity.getAryaUserId());
            result.setName(aryaUserEntity.getRealName());
            result.setIdCardNo(aryaUserEntity.getIdcardNo());
            result.setPhoneNo(aryaUserEntity.getPhoneNo());
            results.add(result);
        }
        paginationResult.setEmployees(results);
        int filterRows = leaveEmployeeDao.findPaginationLeaveEmployeesCount(criteria);
        paginationResult.setFilterRows(filterRows);
        paginationResult.setTotalRows(leaveEmployeeDao.findAllLeaveEmployeesCount(command));
        paginationResult.setPages(Utils.calculatePages(filterRows, command.getPageSize()));
        return paginationResult;
    }

    @Override
    public EmployeeListPaginationResult getLeaveEmployeePagedSortListNew(EmployeePagedListCommand command) throws Exception {
        EmployeeListPaginationResult paginationResult = new EmployeeListPaginationResult();
        Pager<LeaveEmployeeEntity> pager = leaveEmployeeDao.findPaginationSortLeaveEmployeesNew(command);
        EmployeeListResult results = new EmployeeListResult();
        for (LeaveEmployeeEntity leave : pager.getResult()) {
            EmployeeListResult.EmployeeResult result = new EmployeeListResult.EmployeeResult();
            result.setEmployeeId(leave.getId());
            result.setCheckinTime(leave.getCheckinTime());
            result.setLeaveTime(leave.getLeaveTime());
            result.setWorkSn(empHelper.getFormatWorkSn(leave.getWorkSnFormatCommand()));
            result.setDepartmentId(leave.getDepartmentId());
            result.setDepartmentName(leave.getDepartmentName());
            result.setWorkLineId(leave.getWorkLineId());
            result.setWorkLineName(leave.getWorkLineName());
            result.setWorkShiftId(leave.getWorkShiftId());
            result.setWorkShiftName(leave.getWorkShiftName());
            result.setPositionId(leave.getPositionId());
            result.setPositionName(leave.getPositionName());
            result.setName(leave.getRealName());
            result.setIdCardNo(leave.getIdCardNo());
            result.setPhoneNo(leave.getTelephone());
            result.setVersion(leave.getTxVersion());
            // 打卡号
            result.setWorkAttendanceNo(leave.getWorkAttendanceNo());
            result.setExam_id(leave.getExamId());
            results.add(result);
        }
        paginationResult.setEmployees(results);
        paginationResult.setTotalRows(pager.getRowCount());
        paginationResult.setPages(pager.getPage());
        return paginationResult;
    }


    @Override
    public void acceptProspectiveEmployees(AcceptProspectiveEmployeeCommand command) throws Exception {
        Map<String, BranUserEntity> branUserPhone2EntityMap = new HashMap<>();//存放bran用户，key是手机号，value是实体
        Map<String, AryaUserEntity> aryaUserId2EntityMap = new HashMap<>();//存放arya用户，key是id，value是实体
        Collection<String> phoneNos = new ArrayList<>();//收集员工的手机号
        Collection<String> aryaUserIds = new ArrayList<>();//收集Arya用户的id
        List<ProspectiveEmployeeEntity> needDeleteProspectiveEmployees = new ArrayList<>();
        Collection<String> prospectiveEmployeeIds = new ArrayList<>();
        List<BranUserEntity> needUpdateBranUsers = new ArrayList<>();
        List<ContractEntity> needCreateContracts = new ArrayList<>();
        List<EmployeeEntity> needCreateEmployees = new ArrayList<>();
        if (command.getIds().size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_PROSPECTIVE_EMPLOYEE_SELECT_NONE);//没有选中待入职员工
        }
        String corpName = branCorporationDao.findCorpNameByIdThrow(command.getBranCorpId());
        List<String> ids = command.getIds();
        Map<String, Long> map = command.getMap();
        //查出待入职员工
        List<ProspectiveEmployeeEntity> proEmployeeEntities = prospectiveEmployeeDao.findProspectiveEmployeesByIds(ids, command.getBranCorpId());
        if (proEmployeeEntities.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_PROSPECTIVE_EMPLOYEE_FOUND_NONE);//没有找到任何待入职员工
        }

        //判断是否所有待入职员工都完成了入职流程
        List<String> prospectiveEmployeeNames = new ArrayList<>();
        for (ProspectiveEmployeeEntity prospectiveEmployeeEntity : proEmployeeEntities) {
            if (prospectiveEmployeeEntity.getCheckinComplete() != 1) {
                prospectiveEmployeeNames.add(prospectiveEmployeeEntity.getFullName());
            }
        }
        //是否存在尚未完成入职流程的待入职员工
        if (prospectiveEmployeeNames.size() > 0) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEES_EXIST_NOT_CHECKIN_COMPLETE, StringUtils.join(prospectiveEmployeeNames, "，") + "待入职员工尚未在App完成入职流程。");//存在没有完成入职流程的待入职员工
        }

        for (ProspectiveEmployeeEntity proEmployeeEntity : proEmployeeEntities) {
            TxVersionUtil.compireVersion(proEmployeeEntity.getTxVersion(), map.get(proEmployeeEntity.getId()));
            if (!phoneNos.contains(proEmployeeEntity.getPhoneNo())) {
                phoneNos.add(proEmployeeEntity.getPhoneNo());
            }

            if (!prospectiveEmployeeIds.contains(proEmployeeEntity.getId())) {
                prospectiveEmployeeIds.add(proEmployeeEntity.getId());
            }
        }

        //查出所有arya用户
        List<AryaUserEntity> aryaUserEntities = aryaUserDao.findUserByPhoneNos(phoneNos);
        if (aryaUserEntities.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_ARYA_USER_FOUND_NONE);//没有找到任何Arya用户
        }
        //判断是否查询到所有待入职员工的Arya账号
        if (ids.size() != aryaUserEntities.size()) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEES_NOT_HAVE_ARYA_USER);
        }

        for (AryaUserEntity aryaUserEntity : aryaUserEntities) {
            //收集arya用户id
            if (!aryaUserIds.contains(aryaUserEntity.getId())) {
                aryaUserIds.add(aryaUserEntity.getId());
            }
            if (!aryaUserId2EntityMap.containsKey(aryaUserEntity.getId())) {
                aryaUserId2EntityMap.put(aryaUserEntity.getId(), aryaUserEntity);
            }
        }

        //查出所有bran用户，此时待入职员工都完成资料填写，并且肯定都有bran账号
        List<BranUserEntity> branUserEntities = branUserDao.findBranUsersByAryaUserIds(aryaUserIds);
        if (branUserEntities.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_BRAN_USER_FOUND_NONE);//没有找到任何Bran用户
        }
        List<String> notInThisCorpProspectiveEmployeeNames = new ArrayList<>();
        for (BranUserEntity branUserEntity : branUserEntities) {
            if (!command.getBranCorpId().equals(branUserEntity.getBranCorpId())) {
                notInThisCorpProspectiveEmployeeNames.add(branUserEntity.getRealName());
            }
            AryaUserEntity aryaUserEntity = aryaUserId2EntityMap.get(branUserEntity.getAryaUserId());
            if (!branUserPhone2EntityMap.containsKey(aryaUserEntity.getPhoneNo())) {
                branUserPhone2EntityMap.put(aryaUserEntity.getPhoneNo(), branUserEntity);
            }
        }
        //待入职员工branUser的corpId不是该公司
        if (notInThisCorpProspectiveEmployeeNames.size() > 0) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_NOT_IN_THIS_COMPANY, "待入职员工" + StringUtils.join(notInThisCorpProspectiveEmployeeNames, "，") + "尚未在App完成入职流程或人事关系在其他公司。");
        }

        List<EmployeeUserModel> employeeUserModels = new ArrayList<>();

        //生成工号
        List<Map<String, String>> workSnList = new ArrayList<>();
        List<String> workSns = new ArrayList<>();

        long num = Long.valueOf(command.getStartWorkSn());
        if (StringUtils.isBlank(command.getWorkSnPrefixName()) || "empty".equals(command.getWorkSnPrefixId())) {
            for (int i = 0; i < command.getCount(); i++) {
                Map<String, String> workSnMap = new HashedMap();
                workSnMap.put("prefixId", null);
                workSnMap.put("prefix", "empty");
                workSnMap.put("suffix", null);
                workSnMap.put("workSn", (num + i) + "");
                workSnList.add(workSnMap);
                workSns.add(workSnMap.get("workSn"));
            }
        } else {
            for (int i = 0; i < command.getCount(); i++) {
                Map<String, String> workSnMap = new HashedMap();
                workSnMap.put("prefixId", command.getWorkSnPrefixId());
                workSnMap.put("prefix", command.getWorkSnPrefixName());
                workSnMap.put("suffix", (num + i) + "");
                workSnMap.put("workSn", command.getWorkSnPrefixName() + (num + i));
                workSnList.add(workSnMap);
                workSns.add(workSnMap.get("workSn"));
            }
        }


        if (employeeDao.isWorkSnsBeenUsed(workSns, command.getBranCorpId(), command.getWorkSnPrefixName())) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_START_WORK_SN_HAS_BEEN_USED);
        }


        log.debug(workSnList.size() + "");


        if (workSnList.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_WORK_SN_NOT_ASSIGN);//没有分配工号
        }
        List<String> logMsgs = new ArrayList<>();
        List<LeaveCertEmployeeEntity> createLeaveEmps = new ArrayList<>();
        List<EducationEmployeeEntity> educationEmployeeEntities = new ArrayList<>();

        //long WorkAttendanceNo = employeeMybatisDao.findMaxWorkAttendanceNo(command.getBranCorpId()) + 1;

        for (int i = 0; i < proEmployeeEntities.size(); i++) {
            ProspectiveEmployeeEntity proEmployeeEntity = proEmployeeEntities.get(i);
            if (proEmployeeEntity.getCheckinComplete() == Constants.FALSE) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_INFO_NOT_COMPLETE);
            }

            // 因为待入职员工可以员工添加,要判断入职时间、职位、班组、工段、部门有没有填写

            // 入职时间
            Assert.notNull(proEmployeeEntity.getCheckinTime(), "请先填写入职时间才能同意入职");
            // 职位ID
            Assert.notNull(proEmployeeEntity.getPositionId(), "请先填写职位才能同意入职");
            // 职位NAME
            Assert.notNull(proEmployeeEntity.getPositionName(), "请先填写职位才能同意入职");
            // 班组ID
            Assert.notNull(proEmployeeEntity.getWorkShiftId(), "请先填写班组才能同意入职");
            // 班组NAME
            Assert.notNull(proEmployeeEntity.getWorkShiftName(), "请先填写班组才能同意入职");
            // 工段ID
            Assert.notNull(proEmployeeEntity.getWorkLineId(), "请先填写工段才能同意入职");
            // 工段NAME
            Assert.notNull(proEmployeeEntity.getWorkLineName(), "请先填写工段才能同意入职");
            // 部门ID
            Assert.notNull(proEmployeeEntity.getDepartmentId(), "请先填写部门才能同意入职");
            // 部门NAME
            Assert.notNull(proEmployeeEntity.getDepartmentName(), "请先填写部门才能同意入职");


            //删除待入职员工
            proEmployeeEntity.setIsDelete(Constants.TRUE);
            needDeleteProspectiveEmployees.add(proEmployeeEntity);
            EmployeeEntity employeeEntity = new EmployeeEntity();
            employeeEntity.setId(Utils.makeUUID());

            Map<String, String> workSnMap = workSnList.get(i);

            logger.info("workSnMap: ");
            logger.info("prefixId: " + workSnMap.get("prefixId"));
            logger.info("prefix: " + workSnMap.get("prefix"));
            logger.info("suffix: " + workSnMap.get("suffix"));
            logger.info("workSn: " + workSnMap.get("workSn"));

            if (workSnMap.get("suffix") != null) {
                // 设置工号前缀
                logger.info("有工号前缀");
                WorkSnPrefixEntity workSnPrefixEntity = workSnPrefixDao.findByNameOnCorp(workSnMap.get("prefix"), command.getBranCorpId());

                if (workSnPrefixEntity == null) {
                    logger.info("workSnPrefixEntity is null");
                }
                WorkSnFormatCommand workSnFormatCommand = new WorkSnFormatCommand();
                workSnFormatCommand.setWorkSnPrefixId(workSnPrefixEntity.getId());
                workSnFormatCommand.setWorkSnPrefixName(workSnPrefixEntity.getPrefixName());
                workSnFormatCommand.setWorkSn(workSnMap.get("suffix"));
                workSnFormatCommand.setWorkSnSuffixName(Long.valueOf(workSnMap.get("suffix")));
                workSnFormatCommand.setId(null);
                employeeEntity.setWorkSnPrefixId(workSnPrefixEntity.getId());
                employeeEntity.setWorkSnPrefixName(workSnPrefixEntity.getPrefixName());
                employeeEntity.setWorkSnSuffixName(Long.valueOf(workSnMap.get("suffix")));
                employeeEntity.setWorkSn(empHelper.getFormatWorkSn(workSnFormatCommand));
            } else {
                logger.info("没有工号前缀");
                WorkSnFormatCommand workSnFormatCommand = new WorkSnFormatCommand();
                workSnFormatCommand.setWorkSn(workSnMap.get("workSn"));
                workSnFormatCommand.setId(null);
                employeeEntity.setWorkSnPrefixName("empty");
                employeeEntity.setWorkSn(empHelper.getFormatWorkSn(workSnFormatCommand));
            }

            logger.info("workSn: " + employeeEntity.getWorkSn());
            employeeEntity.setIsDelete(Constants.FALSE);
            employeeEntity.setCreateUser(command.getUserId());
            employeeEntity.setCreateTime(System.currentTimeMillis());
            employeeEntity.setBranCorpId(command.getBranCorpId());
            employeeEntity.setPositionId(proEmployeeEntity.getPositionId());
            employeeEntity.setPositionName(proEmployeeEntity.getPositionName());
            employeeEntity.setWorkLineId(proEmployeeEntity.getWorkLineId());
            employeeEntity.setWorkLineName(proEmployeeEntity.getWorkLineName());
            employeeEntity.setWorkShiftId(proEmployeeEntity.getWorkShiftId());
            employeeEntity.setWorkShiftName(proEmployeeEntity.getWorkShiftName());
            employeeEntity.setDepartmentId(proEmployeeEntity.getDepartmentId());
            employeeEntity.setDepartmentName(proEmployeeEntity.getDepartmentName());
            employeeEntity.setCheckinTime(proEmployeeEntity.getCheckinTime());
            employeeEntity.setIsImported(0);
            if (!branUserPhone2EntityMap.containsKey(proEmployeeEntity.getPhoneNo())) {
                throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_USER_NOT_FOUND);
            }
            BranUserEntity branUserEntity = branUserPhone2EntityMap.get(proEmployeeEntity.getPhoneNo());
            branUserEntity.setUpdateTime(System.currentTimeMillis());
            branUserEntity.setUpdateUser(command.getUserId());
            employeeEntity.setBranUserId(branUserEntity.getId());
            //判断该员工是否完成资料
            AryaUserEntity aryaUserEntity = aryaUserId2EntityMap.get(branUserEntity.getAryaUserId());
            if (ProspectiveHelper.getRequireProfileCompleteCount(aryaUserEntity, branUserEntity) != configService.getRequireProfileTotal()) {
                throw new AryaServiceException(ErrorCode.CODE_BRAN_PROSPECTIVE_EMPLOYEE_PROFILE_NOT_COMPLETE);
            }

            // 判断身份证号与花名是否相同
            EmpQueryCommand empQueryCommand = new EmpQueryCommand();
            empQueryCommand.setRegisterAccount(null);
            empQueryCommand.setWorkSn(null);
            empQueryCommand.setIdCardNo(aryaUserEntity.getIdcardNo().trim());
            empQueryCommand.setBranCorpId(command.getBranCorpId());

            List<EmployeeEntity> registerAccounts = employeeDao.findByQueryCommand(empQueryCommand);
            if (!ListUtils.checkNullOrEmpty(registerAccounts)) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证不能与花名册员工的身份证相同");
            }


            //判断待入职员工和branUser真实姓名是否相同
            log.info("待入职员工姓名: " + proEmployeeEntity.getFullName());
            if (proEmployeeEntity.getFullName() != null) {
                log.info("待入职员工姓名长度: " + proEmployeeEntity.getFullName().length());
            }
            log.info("app姓名: " + branUserEntity.getRealName());
            if (branUserEntity.getRealName() != null) {
                log.info("app姓名长度: " + branUserEntity.getRealName().length());
            }
            if (proEmployeeEntity.getFullName() == null || branUserEntity.getRealName() == null
                    || !proEmployeeEntity.getFullName().trim().equals(branUserEntity.getRealName().trim())) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEE_NAME_NOT_RIGHT);
            }

            // 保存身份证正面到正式员工目录
            empHelper.saveImg(employeeEntity.getBranUserId(), employeeEntity.getId(), branUserEntity.getIdcardFrontFileName(),
                    branUserEntity.getBranCorpId(), "身份证正面照", 0);
            // 保存身份证反面照到正式员工目录
            empHelper.saveImg(employeeEntity.getBranUserId(), employeeEntity.getId(), branUserEntity.getIdcardBackFileName(),
                    branUserEntity.getBranCorpId(), "身份证反面照", 0);
            // 保存身份证头像照正式员工目录
            empHelper.saveImg(employeeEntity.getBranUserId(), employeeEntity.getId(), branUserEntity.getIdcardFaceFileName(),
                    branUserEntity.getBranCorpId(), "身份证头像照", 0);
            //保存学历证明到正式员工目录
            EducationEntity educationEntity = educationDao.findEducationByBranUserId(branUserEntity.getId());
            if (educationEntity != null) {
                empHelper.saveImg(employeeEntity.getBranUserId(), employeeEntity.getId(), educationEntity.getEducationCertFileName(),
                        branUserEntity.getBranCorpId(), "学历证明照", 1);
                // 保存到新的教育经历表
                EducationEmployeeEntity educationEmployeeEntity = new EducationEmployeeEntity();
                BeanUtils.copyProperties(educationEmployeeEntity, educationEntity);
                educationEmployeeEntity.setCreateTime(System.currentTimeMillis());
                educationEmployeeEntity.setCreateUser(command.getUserId());
                educationEmployeeEntity.setId(Utils.makeUUID());
                educationEmployeeEntity.setIsDelete(0);
                educationEmployeeEntity.setTxVersion(0L);
                educationEmployeeEntity.setEmployeeId(employeeEntity.getId());
                educationEmployeeEntity.setEducationCertFileName("学历证明照");
                // 文化程度
                if (educationEmployeeEntity.getEducation() > 0) {
                    employeeEntity.setDegreeOfEducation(educationEmployeeEntity.getEducation() - 1);
                }
                // 毕业院校
                employeeEntity.setGraduatedSchool(educationEmployeeEntity.getSchool());
                // 专业类别
                employeeEntity.setProfessionalCategory(educationEmployeeEntity.getMajor());
                // 毕业时间
                if (educationEmployeeEntity.getEndTime() > 0) {
                    employeeEntity.setGraduationTime(new Date(educationEmployeeEntity.getEndTime()));
                }
                educationEmployeeEntities.add(educationEmployeeEntity);

            }


            LeaveCertEntity leaveCertEntity = leaveCertDao.findBranUserLatestUnDeleteLeaveCert(branUserEntity.getId());
            if (leaveCertEntity != null) {
                //保存离职证明到正式员工目录
                empHelper.saveImg(employeeEntity.getBranUserId(), employeeEntity.getId(), leaveCertEntity.getLeaveCertFileName(),
                        branUserEntity.getBranCorpId(), "离职证明照", 2);

                LeaveCertEmployeeEntity leaveCertEmployeeEntity = new LeaveCertEmployeeEntity();
                BeanUtils.copyProperties(leaveCertEmployeeEntity, leaveCertEntity);

                // 保存新的离职证明表
                leaveCertEmployeeEntity.setId(Utils.makeUUID());
                leaveCertEmployeeEntity.setTxVersion(0L);
                leaveCertEmployeeEntity.setIsDelete(0);
                leaveCertEmployeeEntity.setLeaveCertFileName("离职证明照");
                leaveCertEmployeeEntity.setEmployeeId(employeeEntity.getId());
                leaveCertEmployeeEntity.setIsUsed(1);
                leaveCertEmployeeEntity.setCreateTime(System.currentTimeMillis());
                leaveCertEmployeeEntity.setCreateUser(command.getUserId());
                createLeaveEmps.add(leaveCertEmployeeEntity);

                employeeEntity.setLeaveCertFileName("离职证明照");
                leaveCertEntity.setIsUsed(Constants.TRUE);
                leaveCertDao.update(leaveCertEntity);
            }
            //保存人脸识别照到正式员工目录
            empHelper.saveImg(employeeEntity.getBranUserId(), employeeEntity.getId(), branUserEntity.getFaceFileName(),
                    branUserEntity.getBranCorpId(), "人脸识别照", 3);

            BankCardEntity bankCardEntity = branUserEntity.getBankCardEntity();
            if (bankCardEntity != null) {
                empHelper.saveImg(employeeEntity.getBranUserId(), employeeEntity.getId(), bankCardEntity.getBankCardUrl(),
                        branUserEntity.getBranCorpId(), "银行卡照片", 4);
            }

            // 保存新的工作经历表
            List<CareerEntity> careerEntities = careerDao.findCareersByBranUserId(branUserEntity.getId());
            if (careerEntities != null) {
                List<CareerEmployeeEntity> careerEmployeeEntities = new ArrayList<>();
//				BeanUtils.copyProperties(careerEmployeeEntities, careerEntities);
                for (CareerEntity careerEntity : careerEntities) {
                    CareerEmployeeEntity temp = new CareerEmployeeEntity();
                    BeanUtils.copyProperties(temp, careerEntity);
                    temp.setId(Utils.makeUUID());
                    temp.setTxVersion(0L);
                    temp.setCreateTime(System.currentTimeMillis());
                    temp.setIsDelete(0);
                    temp.setCreateUser(command.getUserId());
                    temp.setEmployeeId(employeeEntity.getId());
                    careerEmployeeEntities.add(temp);
                }

                careerEmpDao.create(careerEmployeeEntities);
            }


            employeeEntity.setIdcardFrontFileName("身份证正面照");
            employeeEntity.setIdcardBackFileName("身份证反面照");
            employeeEntity.setIdcardFaceFileName("身份证头像照");
            employeeEntity.setIdcardAddress(branUserEntity.getIdcardAddress());
            employeeEntity.setFaceFileName("人脸识别照");
            employeeEntity.setOffice(branUserEntity.getOffice());
            employeeEntity.setExpireTime(branUserEntity.getExpireTime());


            employeeEntity.setRealName(aryaUserEntity.getRealName());

            employeeEntity.setNation(branUserEntity.getNation());

            employeeEntity.setAddProvinceCode(branUserEntity.getAddProvinceCode());
            employeeEntity.setAddProvinceName(branUserEntity.getAddProvinceName());
            employeeEntity.setAddCityCode(branUserEntity.getAddCityCode());
            employeeEntity.setAddCityName(branUserEntity.getAddCityName());
            employeeEntity.setAddCountyCode(branUserEntity.getAddCountyCode());
            employeeEntity.setAddCountyName(branUserEntity.getAddCountyName());
            employeeEntity.setIdCardNo(aryaUserEntity.getIdcardNo());
            employeeEntity.setIdcardAddress(branUserEntity.getIdcardAddress());
            employeeEntity.setMarriage(branUserEntity.getMarriage());
            employeeEntity.setTelephone(aryaUserEntity.getPhoneNo());
            employeeEntity.setAddress(branUserEntity.getAddress());
            employeeEntity.setRegisterAccount(aryaUserEntity.getPhoneNo());
            employeeEntity.setIsBinding(1);

            employeeEntity.setUrgentContact(branUserEntity.getUrgentContact());
            employeeEntity.setUrgentContactCorp(branUserEntity.getUrgentContactCorp());
            employeeEntity.setUrgentContactPhone(branUserEntity.getUrgentContactPhone());
            employeeEntity.setUrgentContactRelation(branUserEntity.getUrgentContactRelation());
            // 设置试用期月份
            employeeEntity.setProbation(command.getProbation());

            employeeEntity.setStartTime(command.getContractStartTime());
            employeeEntity.setEndTime(command.getContractEndTime());
            // 体检单
            employeeEntity.setExamId(proEmployeeEntity.getExamId());
            // 设置银行卡信息
            if (bankCardEntity != null) {
                employeeEntity.setBankCardEntity(bankCardEntity);
                employeeEntity.getBankCardEntity().setBankCardUrl("银行卡照片");
            }

            // 班车
            employeeEntity.setBusAddress(branUserEntity.getBusAddress());

            // 设置detail
            EmployeeDetailEntity detail = new EmployeeDetailEntity();
            // 供应来源
            detail.setSourceOfSupply(command.getSourceOfSupply());
            // 面试日期
            detail.setInterviewDate(command.getInterviewDate());
            // 员工性质
            detail.setEmployeeNature(command.getEmployeeNature());
            employeeEntity.setDetail(detail);
            // 考勤打卡号

            String key = "WORKATTENDANCENO_"+command.getBranCorpId();
            if(!redisService.exists(key)){
                redisService.set(key,"100010000");
            }
            long workAttendanceNo = redisService.incrby(key);

            employeeEntity.setWorkAttendanceNo(workAttendanceNo++);
            log.info("添加考勤打卡号: " + employeeEntity.getWorkAttendanceNo());
            // 同步状态
            employeeEntity.setWorkAttendanceAddState(EmployeeEntity.WorkAttendanceAddState.initial);
            //生成合同
            ContractEntity contractEntity = new ContractEntity();
            contractEntity.setId(Utils.makeUUID());
            contractEntity.setBranUserId(branUserEntity.getId());
            contractEntity.setBranEmployeeId(employeeEntity.getId());
            contractEntity.setBranCorporationId(command.getBranCorpId());
            contractEntity.setContractName("默认劳动合同");
            contractEntity.setStartTime(command.getContractStartTime());
            contractEntity.setEndTime(command.getContractEndTime());
            contractEntity.setIsDelete(Constants.FALSE);
            contractEntity.setCreateUser(command.getUserId());
            contractEntity.setCreateTime(System.currentTimeMillis());
            // 生日  1989-04-09
            employeeEntity.setBirthday(branUserEntity.getBirthday());
            if (branUserEntity.getBirthday() != null) {
                employeeEntity.setBornDate(SysUtils.stringToDate(branUserEntity.getBirthday()));
            }
            // 性别 1 男 2 女 BranUserEntity
            if (branUserEntity.getSex() > 0) {
                employeeEntity.setSex(branUserEntity.getSex() - 1);
            }

            if (SysUtils.patternCheck("^[1-9][0-9]{3}.[0-9]{2}.[0-9]{2}-[1-9][0-9]{3}.[0-9]{2}.[0-9]{2}$",
                    employeeEntity.getExpireTime())) {
                logger.info("匹配到xx.xx.xx-xx.xx.xx");
                String[] sp = employeeEntity.getExpireTime().split("-");
                employeeEntity.setExpireStartTime(SysUtils.stringToDate(sp[0], "yyyy.MM.dd"));
                employeeEntity.setExpireEndTime(SysUtils.stringToDate(sp[1], "yyyy.MM.dd"));
            }

            if (SysUtils.patternCheck("^[1-9][0-9]{3}.[0-9]{2}.[0-9]{2}-长期$",
                    employeeEntity.getExpireTime())) {
                logger.info("匹配到xx.xx.xx-长期");
                String[] sp = employeeEntity.getExpireTime().split("-");
                employeeEntity.setExpireStartTime(SysUtils.stringToDate(sp[0], "yyyy.MM.dd"));
                employeeEntity.setExpireEndTime(null);
                employeeEntity.setIsLongTerm(1);
            }

            employeeEntity.setExpireTime(branUserEntity.getExpireTime());

            log.info("身份证有效期开始时间: " + employeeEntity.getExpireStartTime());
            log.info("身份证有效期结束时间: " + employeeEntity.getExpireEndTime());
            log.info("身份证有效期: " + employeeEntity.getExpireTime());

            needCreateContracts.add(contractEntity);
            needCreateEmployees.add(employeeEntity);
            needUpdateBranUsers.add(branUserEntity);

            //生成推送模型
            EmployeeUserModel employeeUserModel = new EmployeeUserModel();
            employeeUserModel.setName(aryaUserEntity.getRealName());
            employeeUserModel.setAryaUserId(aryaUserEntity.getId());
            employeeUserModel.setLastClientType(aryaUserEntity.getLastClientType());
            employeeUserModel.setCorpName(corpName);
            employeeUserModel.setWorkSn(empHelper.getFormatWorkSn(employeeEntity.getWorkSnFormatCommand()));
            employeeUserModels.add(employeeUserModel);

            logMsgs.add(proEmployeeEntity.getFullName());
        }
        try {
            prospectiveEmployeeDao.update(needDeleteProspectiveEmployees);
            employeeDao.create(needCreateEmployees);
            contractDao.create(needCreateContracts);
            branUserDao.update(needUpdateBranUsers);
            leaveCertEmpDao.create(createLeaveEmps);
            educationEmpDao.create(educationEmployeeEntities);

            //可用工号最大值+1
            WorkSnPrefixEntity workSnPrefixEntity = workSnPrefixDao.findById(command.getWorkSnPrefixId());
            if (workSnPrefixEntity != null) {
                long workNum = Long.valueOf(command.getEndWorkSn());
                if (workNum < workSnPrefixEntity.getLatestSn()) {
                    workNum = workSnPrefixEntity.getLatestSn();
                }
                workSnPrefixEntity.setLatestSn(workNum + 1);
                workSnPrefixDao.update(workSnPrefixEntity);
            }


            if (logMsgs.size() > 0) {
                SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
                info.setMsg("同意员工入职，名单：" + StringUtils.join(logMsgs, "，") + "，共" + logMsgs.size() + "人。");
                branOpLogDao.success(BranOpLogEntity.OP_MODULE_PROSPECTIVE_EMPLOYEE, BranOpLogEntity.OP_TYPE_ACCEPT, command.getUserId(), info);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEE_ACCEPT_FAILED);//同意入职失败
        }
        try {
            //入职推送
            PushProspectiveEmployeeAcceptNotice pushThread = new PushProspectiveEmployeeAcceptNotice(pushService, employeeUserModels, corpName);
            pushThread.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EmployeeDetailResult getEmployeeDetail(String employeeId, String branCorpId) throws Exception {
        EmployeeDetailResult result = new EmployeeDetailResult();
        String employeePhoneNo = "";
        int employeeInfoCompleteCount = 0;//资料完成数
        AryaUserEntity aryaUserEntity = null;
        BranUserEntity branUserEntity = null;
        //如果是待入职员工，需要判断员工是否接受Offer才能有权限查看其个人资料
        ProspectiveEmployeeEntity prospectiveEmployeeEntity = prospectiveEmployeeDao.findProspectiveEmployeeById(employeeId, branCorpId);
        if (prospectiveEmployeeEntity.getAcceptOffer() == Constants.FALSE) {
            //用户没有接受入职邀请
            throw new AryaServiceException(ErrorCode.CODE_CORP_PROSPECTIVE_EMPLOYEE_NOT_ACCEPT_OFFER_YET);
        }
        result.setVersion(prospectiveEmployeeEntity.getTxVersion());
        //用户是否提交资料，HR是否可以点击同意入职
        result.setIsProfileComplete(prospectiveEmployeeEntity.getCheckinComplete());
        employeePhoneNo = prospectiveEmployeeEntity.getPhoneNo();
        aryaUserEntity = aryaUserDao.findUserByPhoneNoThrow(employeePhoneNo);
        branUserEntity = branUserDao.findBranUsersByAryaUserIdThrow(aryaUserEntity.getId(), branCorpId);
        //资料进度
        employeeInfoCompleteCount = prospectiveHelper.getProspectiveProfileCompleteCount(aryaUserEntity, branUserEntity);
        result.setProfileProgress(InductionHelper.getProgressStr(employeeInfoCompleteCount, configService.getRequireProfileTotal()));
        log.debug("employeeInfoCompleteCount ... " + employeeInfoCompleteCount);

        //工作经历区
        List<CareerEntity> careerEntities = careerDao.findCareersByBranUserId(branUserEntity.getId());
        if (careerEntities.size() > 0) {
            result.setCareer(generateCareerResult(careerEntities));
        }

        //学历区
        EducationEntity educationEntity = educationDao.findEducationByBranUserId(branUserEntity.getId());
        String educationImageFileName = null;
        if (educationEntity != null) {
            result.setEducation(generateEducationResult(educationEntity));
            educationImageFileName = educationEntity.getEducationCertFileName();
        }

        // 个人资料区
        EmployeeDetailProfileResult details = generateProfileResult(aryaUserEntity, branUserEntity, null,
                branUserEntity.getMarriage(), branUserEntity.getUrgentContact(),
                branUserEntity.getUrgentContactPhone(),
                StringUtil.isImageServerUrl(branUserEntity.getFaceFileName())==true?branUserEntity.getFaceFileName():
                fileService.getEmployeeAllKindImageURL(FileService.EMPLOYEE_PROSPECTIVE_FACE_ROUTE, branUserEntity.getFaceFileName(),
                        branUserEntity.getId()));

        result.setProfile(details);
        // 附件区
        LeaveCertEntity latestUnuseLeaveCertEntity = leaveCertDao.findBranUserLatestUnuseLeaveCert(branUserEntity.getId());
        result.setAttachment(generateEmployeeAttachmentResult(branUserEntity.getId(),
                branUserEntity.getBranCorpId(),
                1, branUserEntity.getIdcardFrontFileName(),
                branUserEntity.getIdcardBackFileName(),
                educationImageFileName,
                latestUnuseLeaveCertEntity != null ? latestUnuseLeaveCertEntity.getLeaveCertFileName() : null,
                branUserEntity.getIdcardFaceFileName(),
                branUserEntity.getBankCardEntity() != null ? branUserEntity.getBankCardEntity().getBankCardUrl() : null
        ));
        return result;
    }

    @Override
    public EmployeeDetailResult getEmployeeDetailForEmp(String employeeId, String branCorpId, int employeeStatus) throws Exception {
        EmployeeDetailResult result = new EmployeeDetailResult();
        int employeeInfoCompleteCount = 0;//资料完成数
        EmployeeEntity employeeEntity = employeeDao.findEmployeeById(employeeId, branCorpId);
        employeeInfoCompleteCount = getProfileCompleteCount(employeeEntity);

        //资料进度
        result.setProfileProgress(InductionHelper.getProgressStr(employeeInfoCompleteCount, configService.getProfileTotal()));

        //工作经历区
        List<CareerEmployeeEntity> careerEntities = careerEmpDao.findCareersByEmpId(branCorpId, employeeId);
        if (careerEntities.size() > 0) {
            result.setCareer(generateCareerResult1(careerEntities));
        }

        //学历区
        EducationEmployeeEntity educationEntity = educationEmpDao.findEducationByEmpId(employeeId, branCorpId);
        String educationImageFileName = null;
        if (educationEntity != null) {
            result.setEducation(generateEducationResult1(educationEntity));
            educationImageFileName = educationEntity.getEducationCertFileName();
        }

        // 个人资料区
        result.setProfile(generateProfileResult(employeeEntity, employeeEntity.getWorkSn(), employeeEntity.getMarriage(),
                employeeEntity.getUrgentContact(), employeeEntity.getUrgentContactPhone(),
                StringUtil.isImageServerUrl(employeeEntity.getFaceFileName())==true?employeeEntity.getFaceFileName():
                fileService.getEmpFaceImg(employeeEntity.getFaceFileName(), employeeEntity.getId(), employeeEntity.getBranCorpId())));

        log.info("result.profile.orginAddress: " + result.getProfile().getOriginDistrict());

        // 附件区
        result.setAttachment(generateEmployeeAttachmentResult(employeeEntity, educationImageFileName));

        // 自定义列
        UserDefinedCommand userDefinedCommand = new UserDefinedCommand();
        userDefinedCommand.setId(employeeId);
        userDefinedCommand.setBranCorpId(branCorpId);
        ModelCommand modelCommand = new ModelCommand();
        modelCommand.setId(employeeEntity.getId());

        List<UserDefinedDetailsResult> userDefinedDetailsResultList = userDefinedDetailsHelper.getUserDefinedColsForEmp(modelCommand);
        if (userDefinedDetailsResultList == null || userDefinedDetailsResultList.isEmpty()) {
            userDefinedDetailsResultList = new ArrayList<>();
            // 如果没找到 就查询公司
            List<UserDefinedColsEntity> list = userDefinedColsDao.findByCorpId(userDefinedCommand).list();
            for (UserDefinedColsEntity userDefinedColsEntity : list) {
                UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                userDefinedDetailsEntity.setId(Utils.makeUUID());
                userDefinedDetailsEntity.setCreateUser(userDefinedCommand.getUserId());
                userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                userDefinedDetailsEntity.setEmployeeEntity(employeeEntity);
                userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                userDefinedDetailsDao.create(userDefinedDetailsEntity);
                UserDefinedDetailsResult userDefinedDetailsResult = new UserDefinedDetailsResult();
                userDefinedDetailsResult.setDetailsId(userDefinedDetailsEntity.getId());
                userDefinedDetailsResult.setColName(userDefinedColsEntity.getColName());
                userDefinedDetailsResult.setColValue(userDefinedDetailsEntity.getColValue());
                userDefinedDetailsResultList.add(userDefinedDetailsResult);
            }

        } else {
            userDefinedDetailsHelper.checkUpdate(userDefinedDetailsResultList, userDefinedCommand, employeeEntity, null, null);
        }
        result.setUserDefinedResults(userDefinedDetailsResultList);
        return result;
    }

    @Override
    public EmployeeDetailResult getEmployeeDetailForLeave(String currentUserId, String employeeId, String branCorpId, int employeeStatus) throws Exception {
        EmployeeDetailResult result = new EmployeeDetailResult();
        int employeeInfoCompleteCount = 0;//资料完成数
        LeaveEmployeeEntity leaveEmployeeEntity = leaveEmployeeDao.findLeaveEmployeeById(employeeId);
        employeeInfoCompleteCount = getProfileCompleteCount(leaveEmployeeEntity);

        //资料进度
        result.setProfileProgress(InductionHelper.getProgressStr(employeeInfoCompleteCount, configService.getProfileTotal()));

        //工作经历区
        List<CareerEmployeeEntity> careerEntities = careerEmpDao.findCareersByLeaveId(branCorpId, employeeId);
        if (careerEntities.size() > 0) {
            result.setCareer(generateCareerResult1(careerEntities));
        }

        //学历区
        EducationEmployeeEntity educationEntity = educationEmpDao.findEducationByLeaveId(employeeId, branCorpId);

        String educationImageFileName = null;
        if (educationEntity != null) {
            result.setEducation(generateEducationResult1(educationEntity));
            educationImageFileName = educationEntity.getEducationCertFileName();
        }

        // 个人资料区
        result.setProfile(generateProfileResult(leaveEmployeeEntity, leaveEmployeeEntity.getWorkSn(), leaveEmployeeEntity.getMarriage(),
                leaveEmployeeEntity.getUrgentContact(), leaveEmployeeEntity.getUrgentContactPhone(),
                fileService.getEmpFaceImg(leaveEmployeeEntity.getFaceFileName(), leaveEmployeeEntity.getId(), leaveEmployeeEntity.getBranCorpId())));

        // 附件区
        result.setAttachment(generateEmployeeAttachmentResult(leaveEmployeeEntity, educationImageFileName));
        EmployeeDetailResult.EmployeeDetailLeaveReasonResult reasonResult = new EmployeeDetailResult.EmployeeDetailLeaveReasonResult();
        // 查询离职原因
        if (StringUtils.isNotBlank(leaveEmployeeEntity.getBranLeaveReasonId())) {
            LeaveReasonEntity leaveReasonEntity = leaveReasonDao.findCorpLeaveReasonByIdThrow(
                    leaveEmployeeEntity.getBranLeaveReasonId(), leaveEmployeeEntity.getBranCorpId());
            reasonResult.setReason(leaveReasonEntity.getReasonName());
            reasonResult.setMemo(leaveEmployeeEntity.getLeaveMemo());
            reasonResult.setLeaveTime(leaveEmployeeEntity.getLeaveTime());
            result.setLeaveReason(reasonResult);
        }

        // 花名册自定义项
        ModelCommand modelCommand = new ModelCommand();
        modelCommand.setId(leaveEmployeeEntity.getId());
        UserDefinedCommand userDefinedCommand = new UserDefinedCommand();
        userDefinedCommand.setBranCorpId(branCorpId);
        userDefinedCommand.setUserId(currentUserId);

        List<UserDefinedDetailsResult> userDefinedDetailsResultList = userDefinedDetailsHelper.getUserDefinedColsForLeave(modelCommand);
        if (userDefinedDetailsResultList == null || userDefinedDetailsResultList.isEmpty()) {
            userDefinedDetailsResultList = new ArrayList<>();
            // 如果没找到 就查询公司
            List<UserDefinedColsEntity> list = userDefinedColsDao.findByCorpId(userDefinedCommand).list();
            for (UserDefinedColsEntity userDefinedColsEntity : list) {
                UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                userDefinedDetailsEntity.setId(Utils.makeUUID());
                userDefinedDetailsEntity.setCreateUser(currentUserId);
                userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                userDefinedDetailsEntity.setLeaveEmployeeEntity(leaveEmployeeEntity);
                userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                userDefinedDetailsDao.create(userDefinedDetailsEntity);
                UserDefinedDetailsResult userDefinedDetailsResult = new UserDefinedDetailsResult();
                userDefinedDetailsResult.setDetailsId(userDefinedDetailsEntity.getId());
                userDefinedDetailsResult.setColName(userDefinedColsEntity.getColName());
                userDefinedDetailsResult.setColValue(userDefinedDetailsEntity.getColValue());
                userDefinedDetailsResultList.add(userDefinedDetailsResult);
            }

        } else {
            userDefinedDetailsHelper.checkUpdate(userDefinedDetailsResultList, userDefinedCommand, leaveEmployeeEntity);
        }

        result.setUserDefinedResults(userDefinedDetailsResultList);
        return result;
    }

    @Override
    public void employeesLeave(Map<String, Long> idMap, String leaveReasonId, Long leaveTime, String remarks,
                               String branCorpId, String operator) throws Exception {

        if (idMap == null || idMap.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_TO_LEAVE_SELECT_NONE);
        }

        if (leaveTime == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_LEAVE_TIME_EMPTY);
        }

        if (leaveReasonId == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_LEAVE_REASON_EMPTY);
        }
        List<String> ids = idMap.keySet().stream().collect(Collectors.toList());
        List<EmployeeEntity> employeeEntities = employeeDao.findEmployeesByIds(ids, branCorpId);
        Collection<String> branUserIds = new ArrayList<>();
        Map<String, EmployeeEntity> employeeId2EntityMap = new HashMap<>();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            TxVersionUtil.compireVersion(employeeEntity.getTxVersion(), idMap.get(employeeEntity.getId()));
            if (!employeeId2EntityMap.containsKey(employeeEntity.getId())) {
                employeeId2EntityMap.put(employeeEntity.getId(), employeeEntity);
            }
            if (!branUserIds.contains(employeeEntity.getBranUserId())) {
                branUserIds.add(employeeEntity.getBranUserId());
            }
        }
        //判断员工是否全部找到
        for (String employeeId : ids) {
            if (employeeId2EntityMap.get(employeeId) == null) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_NOT_FOUND);
            }
        }

        //查询合同
        List<ContractEntity> contractEntities = contractDao.findContractsByEmployeeIds(ids);

        //解除与公司的关联
        List<BranUserEntity> branUserEntities = branUserDao.findBranUsersByIds(branUserIds);
//        if (branUserEntities.size() == 0) {
//            throw new AryaServiceException(ErrorCode.CODE_BRAN_USER_FOUND_NONE);
//        }
        List<String> logMsg = new ArrayList<>();
        // 查询离职原因 是否是不良记录
        LeaveReasonEntity leaveReasonEntity = leaveReasonDao.findCorpLeaveReasonByIdThrow(leaveReasonId, branCorpId);

        //公司id匹配的话至null
        if (!ListUtils.checkNullOrEmpty(branUserEntities)) {
            for (BranUserEntity branUserEntity : branUserEntities) {
                if (branCorpId.equals(branUserEntity.getBranCorpId())) {
                    branUserEntity.setBranCorpId(null);
                    // 如果离职原因是不良记录, 就在不良记录次数中+1
                    if (leaveReasonEntity.getIsNotGood() == 1) {
                        branUserEntity.setBadRecordCount(branUserEntity.getBadRecordCount() + 1);
                    }

                }
                logMsg.add(branUserEntity.getRealName());
            }
        }
        //生成离职员工
        List<LeaveEmployeeEntity> needCreateLeaveEmployees = new ArrayList<>();
        List<LeaveCertEmployeeEntity> leaveCertEmployeeEntities = new ArrayList<>();
        List<EducationEmployeeEntity> educationEmployeeEntities = new ArrayList<>();


        for (EmployeeEntity employeeEntity : employeeEntities) {
            LeaveEmployeeEntity leaveEmployeeEntity = new LeaveEmployeeEntity();
            BeanUtils.copyProperties(leaveEmployeeEntity, employeeEntity);
            leaveEmployeeEntity.setId(Utils.makeUUID());
            leaveEmployeeEntity.setIsDelete(Constants.FALSE);
            leaveEmployeeEntity.setCreateTime(System.currentTimeMillis());
            leaveEmployeeEntity.setCreateUser(operator);
            leaveEmployeeEntity.setUrgentContact(employeeEntity.getUrgentContact());
            leaveEmployeeEntity.setUrgentContactPhone(employeeEntity.getUrgentContactPhone());
            leaveEmployeeEntity.setUrgentContactRelation(employeeEntity.getUrgentContactRelation());
            leaveEmployeeEntity.setIdcardFrontFileName(employeeEntity.getIdcardFrontFileName());
            leaveEmployeeEntity.setIdcardBackFileName(employeeEntity.getIdcardBackFileName());
            leaveEmployeeEntity.setIdcardFaceFileName(employeeEntity.getIdcardFaceFileName());
            leaveEmployeeEntity.setIdcardAddress(employeeEntity.getIdcardAddress());
            leaveEmployeeEntity.setFaceFileName(employeeEntity.getFaceFileName());
            leaveEmployeeEntity.setAddress(employeeEntity.getAddress());
            leaveEmployeeEntity.setBranCorpId(branCorpId);
            leaveEmployeeEntity.setBranUserId(employeeEntity.getBranUserId());
            leaveEmployeeEntity.setBranContractId(employeeEntity.getBranContractId());
            leaveEmployeeEntity.setBranLeaveReasonId(leaveReasonId);
            leaveEmployeeEntity.setDepartmentName(employeeEntity.getDepartmentName());
            leaveEmployeeEntity.setDepartmentId(employeeEntity.getDepartmentId());
            leaveEmployeeEntity.setWorkLineName(employeeEntity.getWorkLineName());
            leaveEmployeeEntity.setWorkLineId(employeeEntity.getWorkLineId());
            leaveEmployeeEntity.setWorkShiftName(employeeEntity.getWorkShiftName());
            leaveEmployeeEntity.setWorkShiftId(employeeEntity.getWorkShiftId());
            leaveEmployeeEntity.setPositionName(employeeEntity.getPositionName());
            leaveEmployeeEntity.setPositionId(employeeEntity.getPositionId());
            leaveEmployeeEntity.setEmail(employeeEntity.getEmail());
            leaveEmployeeEntity.setStartTime(employeeEntity.getStartTime());
            leaveEmployeeEntity.setEndTime(employeeEntity.getEndTime());
            leaveEmployeeEntity.setExpireTime(employeeEntity.getExpireTime());
            leaveEmployeeEntity.setOffice(employeeEntity.getOffice());
            leaveEmployeeEntity.setMarriage(employeeEntity.getMarriage());
            leaveEmployeeEntity.setSalary(employeeEntity.getSalary());
            leaveEmployeeEntity.setLeaveCertFileName(employeeEntity.getLeaveCertFileName());
            leaveEmployeeEntity.setTelephone(employeeEntity.getTelephone());

            leaveEmployeeEntity.setWorkSnPrefixId(employeeEntity.getWorkSnPrefixId());
            leaveEmployeeEntity.setWorkSnPrefixName(employeeEntity.getWorkSnPrefixName());
            leaveEmployeeEntity.setWorkSnSuffixName(employeeEntity.getWorkSnSuffixName());
            leaveEmployeeEntity.setWorkSn(employeeEntity.getWorkSn());
            leaveEmployeeEntity.setCheckinTime(employeeEntity.getCheckinTime());
            leaveEmployeeEntity.setLeaveTime(leaveTime);
            leaveEmployeeEntity.setLeaveMemo(remarks);
            leaveEmployeeEntity.setRealName(employeeEntity.getRealName());
            leaveEmployeeEntity.setBirthday(employeeEntity.getBirthday());
            leaveEmployeeEntity.setNation(employeeEntity.getNation());
            leaveEmployeeEntity.setSex(employeeEntity.getSex());
            leaveEmployeeEntity.setAddProvinceCode(employeeEntity.getAddProvinceCode());
            leaveEmployeeEntity.setAddProvinceName(employeeEntity.getAddProvinceName());
            leaveEmployeeEntity.setAddCityCode(employeeEntity.getAddCityCode());
            leaveEmployeeEntity.setAddCityName(employeeEntity.getAddCityName());
            leaveEmployeeEntity.setAddCountyCode(employeeEntity.getAddCountyCode());
            leaveEmployeeEntity.setAddCountyName(employeeEntity.getAddCountyName());
            leaveEmployeeEntity.setIdCardNo(employeeEntity.getIdCardNo());
            leaveEmployeeEntity.setIdcardAddress(employeeEntity.getIdcardAddress());
            leaveEmployeeEntity.setMarriage(employeeEntity.getMarriage());
            leaveEmployeeEntity.setTelephone(employeeEntity.getTelephone());
            leaveEmployeeEntity.setBankCardEntity(employeeEntity.getBankCardEntity());
            leaveEmployeeEntity.setDetail(employeeEntity.getDetail());
            leaveEmployeeEntity.setBusAddress(employeeEntity.getBusAddress());
            leaveEmployeeEntity.setEmpId(employeeEntity.getId());
            // 添加体检单ID
            leaveEmployeeEntity.setExamId(employeeEntity.getExamId());
            BranUserEntity branUserEntity = branUserDao.findBranUsersById(employeeEntity.getBranUserId());

            // 保存图片
            // 保存身份证正面到正式员工目录
            empHelper.saveImgEmp(employeeEntity.getBranUserId(), employeeEntity.getId(), leaveEmployeeEntity.getId()
                    , "身份证正面照", employeeEntity.getBranCorpId());
            empHelper.saveImgEmp(employeeEntity.getBranUserId(), employeeEntity.getId(), leaveEmployeeEntity.getId()
                    , "身份证反面照", employeeEntity.getBranCorpId());
            empHelper.saveImgEmp(employeeEntity.getBranUserId(), employeeEntity.getId(), leaveEmployeeEntity.getId()
                    , "身份证头像照", employeeEntity.getBranCorpId());
            empHelper.saveImgEmp(employeeEntity.getBranUserId(), employeeEntity.getId(), leaveEmployeeEntity.getId()
                    , "离职证明照", employeeEntity.getBranCorpId());
            empHelper.saveImgEmp(employeeEntity.getBranUserId(), employeeEntity.getId(), leaveEmployeeEntity.getId()
                    , "学历证明照", employeeEntity.getBranCorpId());
            empHelper.saveImgEmp(employeeEntity.getBranUserId(), employeeEntity.getId(), leaveEmployeeEntity.getId()
                    , "人脸识别照", employeeEntity.getBranCorpId());
            empHelper.saveImgEmp(employeeEntity.getBranUserId(), employeeEntity.getId(), leaveEmployeeEntity.getId()
                    , "银行卡照片", employeeEntity.getBranCorpId());


            // 离职证明
            LeaveCertEmployeeEntity leaveCertEmployeeEntity = leaveCertEmpDao.findByEmpId(employeeEntity.getId(), employeeEntity.getBranUserId());
            if (leaveCertEmployeeEntity != null) {

                leaveCertEmployeeEntity.setLeaveEmpId(leaveEmployeeEntity.getId());
                leaveCertEmployeeEntities.add(leaveCertEmployeeEntity);

            }


            // 教育经历
            EducationEmployeeEntity educationEmployeeEntity = educationEmpDao.findEducationByEmpId(employeeEntity.getId(), employeeEntity.getBranCorpId());
            if (educationEmployeeEntity != null) {
                educationEmployeeEntity.setLeaveEmpId(leaveEmployeeEntity.getId());
                educationEmployeeEntities.add(educationEmployeeEntity);

            }
            log.info("退工查询工作经历: ");
            log.info("查询参数: ");
            log.info("branCorpId: " + branCorpId);
            log.info("employeeEntity.getId(): " + employeeEntity.getId());
            // 工作经历
            List<CareerEmployeeEntity> careerEmployeeEntities = careerEmpDao.findCareersByEmpId(branCorpId, employeeEntity.getId());
            log.info("careerEmployeeEntities " + careerEmployeeEntities.size());
            if (careerEmployeeEntities != null) {
                careerEmployeeEntities.forEach(c -> {
                    c.setLeaveEmpId(leaveEmployeeEntity.getId());
                });
            }

            careerEmpDao.update(careerEmployeeEntities);

            needCreateLeaveEmployees.add(leaveEmployeeEntity);
            //修改合同的员工Id为离职员工Id
            for (ContractEntity contractEntity : contractEntities) {
                if (employeeEntity.getId().equals(contractEntity.getBranEmployeeId())) {
                    contractEntity.setBranEmployeeId(leaveEmployeeEntity.getId());
                }
            }

            leaveEmployeeEntity.setBornDate(employeeEntity.getBornDate());
            leaveEmployeeEntity.setPoliticalStatus(employeeEntity.getPoliticalStatus());
            leaveEmployeeEntity.setSocialSecurityType(employeeEntity.getSocialSecurityType());
            leaveEmployeeEntity.setDegreeOfEducation(employeeEntity.getDegreeOfEducation());
            leaveEmployeeEntity.setGraduatedSchool(employeeEntity.getGraduatedSchool());
            leaveEmployeeEntity.setProfessionalCategory(employeeEntity.getProfessionalCategory());
            leaveEmployeeEntity.setGraduationTime(employeeEntity.getGraduationTime());

            // 更新退工自定义类
            log.debug("退工 更新自定义类");
            ModelCommand modelCommand = new ModelCommand();
            modelCommand.setId(employeeEntity.getId());
            modelCommand.setUserId(leaveEmployeeEntity.getId());
            modelCommand.setBranCorpId(branCorpId);
            leaveEmployeeDao.create(leaveEmployeeEntity);
            userDefinedDetailsHelper.updateUserDefinedDetailsForLeave(modelCommand, leaveEmployeeEntity);
            employeeEntity.setIsDelete(Constants.TRUE);//从正式员工中删除

        }

        // 调用远程退工接口
        try {
            workAttendanceEmpService.retiredEmpForWorkAttendanceDevice(employeeEntities, new SessionInfo(operator, branCorpId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "退工时调用考勤机服务器时错误,请检查考勤机服务器是否正常连接");
        }


        branUserDao.update(branUserEntities);
        employeeDao.update(employeeEntities);
        contractDao.update(contractEntities);
        educationEmpDao.update(educationEmployeeEntities);
        leaveCertEmpDao.update(leaveCertEmployeeEntities);

        if (logMsg.size() > 0) {
            SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
            info.setMsg("退工，名单：" + StringUtils.join(logMsg, "，") + "，共" + logMsg.size() + "人。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_EMPLOYEE, BranOpLogEntity.OP_TYPE_LEAVE, operator, info);
        }
    }

    @Override
    public void AttendanceMachineEmployeesLeave(List<String> ids, SessionInfo sessionInfo) throws Exception {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        if (!ListUtils.checkNullOrEmpty(ids)) {
            employeeEntities = employeeDao.findEmployeesByIds(ids, sessionInfo.getCorpId());
        }
        if (!ListUtils.checkNullOrEmpty(employeeEntities)) {
            // 调用远程退工接口
            workAttendanceEmpService.retiredEmpForWorkAttendanceDevice(employeeEntities, sessionInfo);
        }
    }

    @Override
    public void contractExtension(Map<String, Long> ids, Long contractStartTime, Long contractEndTime,
                                  String branCorpId, String operator) throws Exception {
        if (ids == null || ids.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_TO_CONTRACT_EXTENSION_SELECT_NONE);
        }
        if (contractStartTime == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CONTRACT_START_TIME_EMPTY);
        }
        if (contractEndTime == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_CONTRACT_END_TIME_EMPTY);
        }
        List<String> employeeIds = new ArrayList<>();
        for (String id : ids.keySet()) {
            employeeIds.add(id);
        }
        List<EmployeeEntity> employeeEntities = employeeDao.findEmployeesByIds(employeeIds, branCorpId);
        if (employeeEntities.size() == 0) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_FOUND_EMPTY);
        }

        Long lastContractEndTime = null;
        Map<String, EmployeeEntity> id2EmployeeMap = new HashMap<>();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            //判断员工合同结束日期是否是同一天
//            if (lastContractEndTime == null) {
//                lastContractEndTime = employeeEntity.getEndTime();
//            } else {
//                if (lastContractEndTime != employeeEntity.getEndTime()) {
//                    throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_CONTRACT_END_TIME_NOT_SAME);
//                }
//            }
            //判断续签起始时间与已有合同时间是否存在交叉
            if (employeeEntity.getEndTime() > contractStartTime) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_CONTRACT_EXTENSION_CROSS);
            }
            if (!id2EmployeeMap.containsKey(employeeEntity.getId())) {
                id2EmployeeMap.put(employeeEntity.getId(), employeeEntity);
            }
        }

        //判断员工是否都查到
        for (String employeeId : employeeIds) {
            if (id2EmployeeMap.get(employeeId) == null) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_EMPLOYEE_NOT_FOUND);
            }
        }

        //生成新合同
        List<ContractEntity> contractEntities = new ArrayList<>();
        Collection<String> branUserIds = new ArrayList<>();
        for (EmployeeEntity employeeEntity : employeeEntities) {
            TxVersionUtil.compireVersion(employeeEntity.getTxVersion(), ids.get(employeeEntity.getId()));
            ContractEntity contractEntity = new ContractEntity();
            contractEntity.setId(Utils.makeUUID());
            contractEntity.setCreateUser(operator);
            contractEntity.setCreateTime(System.currentTimeMillis());
            contractEntity.setUpdateUser(operator);
            contractEntity.setUpdateTime(System.currentTimeMillis());
            contractEntity.setBranUserId(employeeEntity.getBranUserId());
            contractEntity.setBranEmployeeId(employeeEntity.getId());
            contractEntity.setContractName("续签劳动合同");
            contractEntity.setBranCorporationId(branCorpId);
            contractEntity.setMemo("续签劳动合同");
            contractEntity.setStartTime(contractStartTime);
            contractEntity.setEndTime(contractEndTime);
            contractEntities.add(contractEntity);
            employeeEntity.setBranContractId(contractEntity.getId());
            employeeEntity.setStartTime(contractStartTime);
            employeeEntity.setEndTime(contractEndTime);
            branUserIds.add(employeeEntity.getBranUserId());
        }
        List<String> logMsg = new ArrayList<>();
        List<BranUserEntity> branUserEntities = branUserDao.findBranUsersByIds(branUserIds);
        for (BranUserEntity branUserEntity : branUserEntities) {
            logMsg.add(branUserEntity.getRealName());
        }
        employeeDao.update(employeeEntities);
        contractDao.create(contractEntities);
        if (logMsg.size() > 0) {
            SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
            info.setMsg("续签，名单：" + StringUtils.join(logMsg, "，") + "，共" + logMsg.size() + "人。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_EMPLOYEE, BranOpLogEntity.OP_TYPE_EXTENSION, operator, info);
        }
    }

    @Override
    public ImportProspectiveResult importEmployees(MultipartFile file, String operator, String branCorpId) throws Exception {
        if (StringUtils.isBlank(branCorpId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        ImportProspectiveResult importProspectiveResult = userImportHelper.fileImport(file);
        log.info("employees size: " + importProspectiveResult.getEmployees().size());
        log.info("isHasError: " + importProspectiveResult.isHasError());
        String fileTypeStr = SysUtils.getFileTypeStr(file.getOriginalFilename());
        importProspectiveResult.setFileTypeStr(fileTypeStr);
        if (!importProspectiveResult.isHasError()) {
            String fileId = importProspectiveResult.getFile_id();
            String filePath = configService.getEmpExcelImportPath(
                    branCorpId,
                    fileId,
                    fileTypeStr);
            log.debug("临时文件夹: " + filePath);
            file.transferTo(new File(filePath));
        }
        return importProspectiveResult;
    }

    @Override
    public ImportEmpConfirmResult importEmployeesConfirm(String file_id, String fileTypeStr, String operator,
                                                         String branCorpId,
                                                         BindingResult bindingResult) throws Exception {

        ImportEmpConfirmResult importEmpConfirmResult = new ImportEmpConfirmResult();

        Assert.paramsNotError(bindingResult, new ExceptionModel());
        File file = new File(configService.getEmpExcelImportPath(branCorpId,
                file_id,
                fileTypeStr
        ));

        // 查询公司
        BranCorporationEntity branCorporationEntity = branCorporationDao.findByIdNotDelete(branCorpId);
        Assert.notNull(branCorporationEntity, "没有查询到bran公司 id: " + branCorpId);

        List<ProspectiveEmployeeEntity> list = userSaveHelper.fileImport(file);
        List<String> logMsg = new ArrayList<>();

        if (!ListUtils.checkNullOrEmpty(list)) {
            for (ProspectiveEmployeeEntity prospectiveEmployeeEntity : list) {

                // 根据手机号 找到aryaUser
                AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(prospectiveEmployeeEntity.getPhoneNo());
                if (aryaUserEntity != null) {
                    //找到brUser
                    BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId(), branCorpId);
                    if (branUserEntity != null) {
                        if (branUserEntity.getBadRecordCount() >= branAdminConfigService.getBadRecordCount()) {
                            prospectiveEmployeeEntity.setBadRecordExceed(1);
                        }
                    }
                }
                log.debug("prospectiveEmployeeEntity id: " + prospectiveEmployeeEntity.getId());

                String msg = String.format(branAdminConfigService.getProspectiveSMS_Msg(), branCorporationEntity.getCorpName(),
                        branCorporationEntity.getCheckinCode());

                // 创建容器存放手机号,用来发送短消息
                List<String> phones = new ArrayList<>();
                phones.add(prospectiveEmployeeEntity.getPhoneNo());
                sendManySmsMessage.init(msg, phones);
                sendManySmsMessage.run();

                logMsg.add(prospectiveEmployeeEntity.getFullName());
//            phones.add(prospectiveEmployeeEntity.getPhoneNo());

            }
        }

        log.debug("导入带入职员工总数: " + list.size());
        if (!list.isEmpty()) {
            prospectiveEmployeeDao.createOrUpdate(list);
        }


        list.forEach(prospectiveEmployeeEntity -> {
            CheckinMessageSendTimeModel sendTimeModel = null;
            try {
                sendTimeModel = calculateCorpCheckinMessageSendTime(prospectiveEmployeeEntity.getCheckinTime(), branCorpId);
            } catch (ParseException e) {
                e.printStackTrace();
                log.info("增加入职提醒任务失败。");
                throw new AryaServiceException(e.getMessage());
            }
            if (sendTimeModel != null) {
                log.info("增加入职提醒");
                scheduleService.scheduleCheckinNotification(branCorpId, prospectiveEmployeeEntity.getPhoneNo(),
                        prospectiveEmployeeEntity.getCheckinTime(),
                        sendTimeModel.getYear(), sendTimeModel.getMonth(),
                        sendTimeModel.getDay(), sendTimeModel.getHour(),
                        sendTimeModel.getMinute());
            }
        });


//        sendManySmsMessage.init(branAdminConfigService.getProspectiveSMS_Msg(), phones);
//        sendManySmsMessage.run();

        importEmpConfirmResult.setFlag(EmployeeConstants.IMPORT_SUCESS);
        if (logMsg.size() > 0) {
            SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
            info.setMsg("导入一批待入职员工，名单：" + StringUtils.join(logMsg, "，") + "，共" + logMsg.size() + "人。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_PROSPECTIVE_EMPLOYEE, BranOpLogEntity.OP_TYPE_IMPORT, operator, info);
        }
        return importEmpConfirmResult;
    }

    @Override
    public void prospectiveExport(EmployeePagedListCommand prospectiveExportCommand, String operator,
                                  HttpServletResponse httpServletResponse) throws Exception {


        List<ProspectiveEmployeeEntity> list = null;
        List<ProspectiveExportResult> resultList = new ArrayList<>();
        Criteria criteria = prospectiveEmployeeDao.getProspectiveEmployeeListCriteria(prospectiveExportCommand);

        if (criteria != null) {
            list = criteria.list();
        }


        if (list == null) {
            list = new ArrayList<>();
        }

        log.debug("list size: " + list.size());
        List<String> logMsg = new ArrayList<>();
        list.forEach(p -> {
            ProspectiveExportResult prospectiveExportResult = new ProspectiveExportResult();
            // 通过待入职员工手机号查询到branUser
            String employeePhoneNo = p.getPhoneNo();
            AryaUserEntity aryaUserEntity = aryaUserDao.findUserByPhoneNo(employeePhoneNo);
            if (aryaUserEntity == null) {
                log.debug("没有找到aryaUserId: " + employeePhoneNo);
            }
            if (aryaUserEntity != null) {
                BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId(),
                        prospectiveExportCommand.getBranCorpId());

                if (branUserEntity != null) {
                    // 设置银行卡、开户账户、班车点信息
                    if (branUserEntity.getBankCardEntity() != null) {
                        prospectiveExportResult.setBankAccount(branUserEntity.getBankCardEntity().getBankAccount());
                        prospectiveExportResult.setBankNum(branUserEntity.getBankCardEntity().getBankNum());
                    }

                    prospectiveExportResult.setBusAddress(branUserEntity.getBusAddress());
                }


            }
            prospectiveExportResult.setFullName(p.getFullName());
            prospectiveExportResult.setCheckinTime(p.getCheckinTime());
            prospectiveExportResult.setDepartmentName(p.getDepartmentName());
            prospectiveExportResult.setPositionName(p.getPositionName());
            prospectiveExportResult.setWorkShitName(p.getWorkShiftName());
            prospectiveExportResult.setWorkLineName(p.getWorkLineName());
            prospectiveExportResult.setPhoneNo(p.getPhoneNo());


            resultList.add(prospectiveExportResult);
            logMsg.add(p.getFullName());
        });
        Map<String, Object> params = new HashMap<>();
        params.put("list", resultList);

        excelExportHelper.export(
                branAdminConfigService.getExcelTemplateLocation() + BranAdminConfigService.PROSPECTIVE_EMPLOYEE_EXCEL_TEMPLATE,
                "待入职员工列表",
                params,
                httpServletResponse
        );


        if (logMsg.size() > 0) {
            SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
            info.setMsg("导出" + logMsg.get(0) + (logMsg.size() > 1 ? "等一批共" + logMsg.size() + "人" : "") + "待入职员工。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_PROSPECTIVE_EMPLOYEE, BranOpLogEntity.OP_TYPE_EXPORT, operator, info);
        }
    }

    @Override
    public FileUploadFileResult employeeExport(EmployeePagedListCommand prospectiveExportCommand, String operator,
                                               HttpServletResponse response) throws Exception {
        log.info("导出花名册员工");
        List<EmployeeExportNewResult> resultList = new ArrayList<>();
        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        Pager<EmployeeEntity> pager = new Pager<>();
        prospectiveExportCommand.setPage(null);
        prospectiveExportCommand.setPageSize(null);

        pager = employeeDao.findPaginationEmployeesNew(prospectiveExportCommand);
        ModelCommand modelCommand = new ModelCommand();
        UserDefinedCommand userDefinedCommand = new UserDefinedCommand();

        List<String> logMsg = new ArrayList<>();
        LinkedHashMap<String, String> map = empExcelHandler.setHeadersParams();

        if (pager != null && pager.getResult() != null && !pager.getResult().isEmpty()) {
            for (EmployeeEntity emp : pager.getResult()) {
                EmployeeExportNewResult employeeExportResult = new EmployeeExportNewResult();
                logMsg.add(emp.getRealName());
                BeanUtils.copyProperties(employeeExportResult, emp);
                employeeExportResult.setAddress(emp.getResidence());
                employeeExportResult.setWorkSn(empHelper.getFormatWorkSn(emp.getWorkSnFormatCommand()));
                employeeExportResult.setName(emp.getRealName());
                employeeExportResult.setPhoneNo(emp.getTelephone());
                employeeExportResult.setWorkShitName(emp.getWorkShiftName());
                employeeExportResult.setCheckinTime(SysUtils.getDateStringFormTimestamp(emp.getCheckinTime()));
                employeeExportResult.setStartTime(SysUtils.getDateStringFormTimestamp(emp.getStartTime()));
                employeeExportResult.setEndTime(SysUtils.getDateStringFormTimestamp(emp.getEndTime()));
                if (StringUtils.isNotBlank(emp.getBirthday())) {
                    employeeExportResult.setAge(Utils.calculateAge(
                            SysUtils.getTimestampFormDateString(emp.getBirthday())));
                }
                employeeExportResult.setSex(EmployeeConstants.getSex(emp.getSex()));
                employeeExportResult.setMarriage(EmployeeConstants.getMarriage(emp.getMarriage()));

                BankCardEntity bankCardEntity = emp.getBankCardEntity();
                EmployeeDetailEntity employeeDetailEntity = emp.getDetail();

                if (bankCardEntity != null) {
                    employeeExportResult.setBankAccount(bankCardEntity.getBankAccount());
                    employeeExportResult.setBankNum(bankCardEntity.getBankNum());
                }

                if (employeeDetailEntity != null) {
                    employeeExportResult.setEmployeeNature(employeeDetailEntity.getEmployeeNature());
                    employeeExportResult.setInterviewDate(employeeDetailEntity.getInterviewDate());
                    employeeExportResult.setSourceOfSupply(employeeDetailEntity.getSourceOfSupply());
                }
                employeeExportResult.setBusAddress(emp.getBusAddress());

                // 查询花名册的教育经历
                EducationEmployeeEntity educationEmployeeEntity = educationEmpDao.findEducationByEmpId(emp.getId(),
                        emp.getBranCorpId());
                if (educationEmployeeEntity != null) {
                    employeeExportResult.setEducationName(EmployeeConstants.getEducationNameByCode(
                            educationEmployeeEntity.getEducation()));
                }

                // 东吴黄金新增字段
                if (emp.getBornDate() != null) {
                    employeeExportResult.setBornDate(SysUtils.getDateStringFormTimestamp(emp.getBornDate().getTime()));
                }

                employeeExportResult.setPoliticalStatus(EmpImportUtils.politicalStatusToString(emp.getPoliticalStatus()));
                employeeExportResult.setSocialSecurityType(emp.getSocialSecurityType());
                employeeExportResult.setGraduatedSchool(emp.getGraduatedSchool());
                employeeExportResult.setProfessionalCategory(emp.getProfessionalCategory());
                employeeExportResult.setDegreeOfEducation(EmpImportUtils.degreeOfEducationToString(emp.getDegreeOfEducation()));

                if (emp.getGraduationTime() != null) {
                    employeeExportResult.setGraduationTime(SysUtils.getDateStringFormTimestamp(emp.getGraduationTime().getTime()));
                }

                if (emp.getIsLongTerm() == 1) {
                    employeeExportResult.setExpireEndTime("长期");
                } else {

                    if (emp.getExpireEndTime() != null) {
                        employeeExportResult.setExpireEndTime(SysUtils.getDateStringFormTimestamp(emp.getExpireEndTime().getTime()));
                    }
                }

                // 查询花名册自定义
                modelCommand.setId(emp.getId());

                List<UserDefinedDetailsResult> userDefinedDetailsResultList = userDefinedDetailsHelper.getUserDefinedColsForEmp(modelCommand);
                if (userDefinedDetailsResultList == null || userDefinedDetailsResultList.isEmpty()) {
                    userDefinedDetailsResultList = new ArrayList<>();
                    // 如果没找到 就查询公司
                    userDefinedCommand.setBranCorpId(prospectiveExportCommand.getBranCorpId());
                    List<UserDefinedColsEntity> list = userDefinedColsDao.findByCorpId(userDefinedCommand).list();
                    // 添加动态列的头
                    empExcelHandler.setDynamicParams(map, list, 0);
                    for (UserDefinedColsEntity userDefinedColsEntity : list) {
                        UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                        userDefinedDetailsEntity.setId(Utils.makeUUID());
                        userDefinedDetailsEntity.setCreateUser(operator);
                        userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                        userDefinedDetailsEntity.setEmployeeEntity(emp);
                        userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                        userDefinedDetailsDao.create(userDefinedDetailsEntity);
                        UserDefinedDetailsResult userDefinedDetailsResult = new UserDefinedDetailsResult();
                        userDefinedDetailsResult.setDetailsId(userDefinedDetailsEntity.getId());
                        userDefinedDetailsResult.setColName(userDefinedColsEntity.getColName());
                        userDefinedDetailsResult.setColValue(userDefinedDetailsEntity.getColValue());
                        // 添加动态列的值
                        empExcelHandler.setDynamicData(userDefinedDetailsResultList, employeeExportResult, 0);
                        userDefinedDetailsResultList.add(userDefinedDetailsResult);
                    }

                } else {
                    userDefinedCommand.setBranCorpId(prospectiveExportCommand.getBranCorpId());
                    userDefinedCommand.setUserId(operator);
                    userDefinedDetailsHelper.checkUpdate(userDefinedDetailsResultList, userDefinedCommand, emp, map,
                            employeeExportResult);
                }

                resultList.add(employeeExportResult);
            }
        }


        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + Utils.makeUUID() + ".xls");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        empExcelHandler.createWorkbook(map, resultList).write(fileOutputStream);


        FileUploadFileResult fileUploadFileResult = fileUploadService.uploadFile(
                MultipartFileUtils.create(file),
                0,
                "xls",
                null, null);

        String url = fileUploadService.generateDownLoadFileUrl(
                configService.getConfigByKey("bran.admin.resource.server"),
                com.bumu.bran.common.Constants.HPPT_TYPE_PDF,
                fileUploadFileResult.getId(),
                0,
                "xls");

        logger.debug("url: " + url);

        if (logMsg.size() > 0) {
            SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
            info.setMsg("导出" + logMsg.get(0) + (logMsg.size() > 1 ? "等一批共" + logMsg.size() + "人" : "") + "在职员工。");
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_EMPLOYEE, BranOpLogEntity.OP_TYPE_EXPORT, operator, info);
        }

        return new FileUploadFileResult(null, url);
    }

    @Override
    public void leaveExport(EmployeePagedListCommand employeePagedListCommand, String operator,
                            HttpServletResponse response) throws Exception {
        List<LeaveEmployeeEntity> leaveEmployeeEntities = new ArrayList<>();
        List<EmployeeExportNewResult> resultList = new ArrayList<>();
        List<String> logMsg = new ArrayList<>();
        employeePagedListCommand.setPageSize(null);
        employeePagedListCommand.setPage(null);
        ModelCommand modelCommand = new ModelCommand();
        UserDefinedCommand userDefinedCommand = new UserDefinedCommand();
        LinkedHashMap<String, String> headersParams = leaveExcelHandler.setHeadersParams();

        Pager<LeaveEmployeeEntity> pager = leaveEmployeeDao.findPaginationSortLeaveEmployeesNew(employeePagedListCommand);


        if (pager != null && pager.getResult() != null && !pager.getResult().isEmpty()) {
            for (LeaveEmployeeEntity emp : pager.getResult()) {
                EmployeeExportNewResult employeeExportResult = new EmployeeExportNewResult();
                logMsg.add(emp.getRealName());
                BeanUtils.copyProperties(employeeExportResult, emp);
                employeeExportResult.setAddress(emp.getResidence());
                employeeExportResult.setName(emp.getRealName());
                employeeExportResult.setPhoneNo(emp.getTelephone());
                employeeExportResult.setWorkShitName(emp.getWorkShiftName());
                employeeExportResult.setCheckinTime(SysUtils.getDateStringFormTimestamp(emp.getCheckinTime()));
                employeeExportResult.setStartTime(SysUtils.getDateStringFormTimestamp(emp.getStartTime()));
                employeeExportResult.setEndTime(SysUtils.getDateStringFormTimestamp(emp.getEndTime()));
                employeeExportResult.setLeaveTime(SysUtils.getDateStringFormTimestamp(emp.getLeaveTime()));
                if (StringUtils.isNotBlank(emp.getBirthday())) {
                    employeeExportResult.setAge(Utils.calculateAge(
                            SysUtils.getTimestampFormDateString(emp.getBirthday())));
                }
                employeeExportResult.setSex(EmployeeConstants.getSex(emp.getSex()));
                employeeExportResult.setMarriage(EmployeeConstants.getMarriage(emp.getMarriage()));

                BankCardEntity bankCardEntity = emp.getBankCardEntity();
                EmployeeDetailEntity employeeDetailEntity = emp.getDetail();

                if (bankCardEntity != null) {
                    employeeExportResult.setBankAccount(bankCardEntity.getBankAccount());
                    employeeExportResult.setBankNum(bankCardEntity.getBankNum());
                }

                if (employeeDetailEntity != null) {
                    employeeExportResult.setEmployeeNature(employeeDetailEntity.getEmployeeNature());
                    if (employeeDetailEntity.getInterviewDate() != null) {

                    }
                    employeeExportResult.setInterviewDate(employeeDetailEntity.getInterviewDate());
                    employeeExportResult.setSourceOfSupply(employeeDetailEntity.getSourceOfSupply());
                }
                employeeExportResult.setBusAddress(emp.getBusAddress());

                // 查询离职员工的教育经历
                EducationEmployeeEntity educationEmployeeEntity = educationEmpDao.findEducationByLeaveId(emp.getId(),
                        emp.getBranCorpId());
                if (educationEmployeeEntity != null) {
                    employeeExportResult.setEducationName(EmployeeConstants.getEducationNameByCode(
                            educationEmployeeEntity.getEducation()));
                }

                // 查询花名册自定义
                modelCommand.setId(emp.getId());

                List<UserDefinedDetailsResult> userDefinedDetailsResultList = userDefinedDetailsHelper.
                        getUserDefinedColsForLeave(modelCommand);
                if (userDefinedDetailsResultList == null || userDefinedDetailsResultList.isEmpty()) {
                    userDefinedDetailsResultList = new ArrayList<>();
                    // 如果没找到 就查询公司
                    userDefinedCommand.setBranCorpId(employeePagedListCommand.getBranCorpId());
                    List<UserDefinedColsEntity> list = userDefinedColsDao.findByCorpId(userDefinedCommand).list();
                    // 添加动态列的头
                    leaveExcelHandler.setDynamicParams(headersParams, list, 0);
                    for (UserDefinedColsEntity userDefinedColsEntity : list) {
                        UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                        userDefinedDetailsEntity.setId(Utils.makeUUID());
                        userDefinedDetailsEntity.setCreateUser(operator);
                        userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                        userDefinedDetailsEntity.setLeaveEmployeeEntity(emp);
                        userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                        userDefinedDetailsDao.create(userDefinedDetailsEntity);
                        UserDefinedDetailsResult userDefinedDetailsResult = new UserDefinedDetailsResult();
                        userDefinedDetailsResult.setDetailsId(userDefinedDetailsEntity.getId());
                        userDefinedDetailsResult.setColName(userDefinedColsEntity.getColName());
                        userDefinedDetailsResult.setColValue(userDefinedDetailsEntity.getColValue());
                        // 添加动态列的值
                        leaveExcelHandler.setDynamicData(userDefinedDetailsResultList, employeeExportResult, 0);
                        userDefinedDetailsResultList.add(userDefinedDetailsResult);
                    }

                } else {
                    userDefinedCommand.setBranCorpId(employeePagedListCommand.getBranCorpId());
                    userDefinedCommand.setUserId(operator);
                    userDefinedDetailsHelper.checkUpdate(userDefinedDetailsResultList, userDefinedCommand, emp, headersParams,
                            employeeExportResult);
                }


                resultList.add(employeeExportResult);
            }
        }

        // 离职员工
        ExcelExportUtils.export(
                null,
                "离职员工列表",
                empExcelHandler.createWorkbook(headersParams, resultList),
                response

        );
        if (logMsg.size() > 0) {
            SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
            info.setMsg("导出" + logMsg.get(0) + (logMsg.size() > 1 ? "等一批离职员工，共" + logMsg.size() + "人。" : "离职员工。"));
            branOpLogDao.success(BranOpLogEntity.OP_MODULE_LEAVE_EMPLOYEE, BranOpLogEntity.OP_TYPE_EXPORT, operator, info);
        }
    }

    @Override
    public void attachmentDownload(String[] phones, String operator, HttpServletResponse response, Integer type,
                                   int employeeType, HttpServletRequest httpServletRequest) throws Exception {
        List<ZipObj> zipObjList = new ArrayList<>();
        List<AryaUserEntity> aryaUserEntityList = null;
        ZipOutputStream zos = null;
        List<String> logMsg = new ArrayList<>();
        String attachment = null;
        String sessionId = httpServletRequest.getRequestedSessionId();

        if (type == null) {
            throw new AryaServiceException("参数为空");
        }

        if (phones == null) {
            throw new AryaServiceException("参数为空");
        }

        if (phones.length > 10) {
            throw new AryaServiceException("最多上传10个");
        }

        if (type != 1 && type != 2) {
            throw new AryaServiceException("类型错误");
        }

        log.info("下载附件: 参数");

        if (type == 1) {
            attachment = "待入职员工附件列表";
            log.info("type: " + type);

            aryaUserEntityList = aryaUserDao.findUserByPhoneNos(Arrays.asList(phones));

            aryaUserEntityList.forEach(aryaUserEntity -> {
                logMsg.add(aryaUserEntity.getRealName());
                ZipObj zipObj = new ZipObj();
                List<File> fileList = new ArrayList<>();
                BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());

                if (branUserEntity != null) {
                    // 指定目录
                    String dir = aryaUserEntity.getRealName() + aryaUserEntity.getPhoneNo() + "/";
                    zipObj.setDir(dir);


                    log.info("getIdcardFrontFileName: " + branUserEntity.getIdcardFrontFileName());
                    if (StringUtils.isNotBlank(branUserEntity.getIdcardFrontFileName())) {
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                (branAdminConfigService.getInductionUploadPath(branUserEntity.getId()) +
                                        branUserEntity.getIdcardFrontFileName()) + ".jpg"), EmployeeConstants.IDCARD_FRONT, sessionId));
                    }

                    log.info("getIdcardBackFileName: " + branUserEntity.getIdcardBackFileName());
                    if (StringUtils.isNotBlank(branUserEntity.getIdcardBackFileName())) {
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                (branAdminConfigService.getInductionUploadPath(branUserEntity.getId()) +
                                        branUserEntity.getIdcardBackFileName()) + ".jpg"), EmployeeConstants.IDCARD_BACK, sessionId)
                        );
                    }


                    log.info("getFaceFileName: " + branUserEntity.getFaceFileName());
                    if (StringUtils.isNotBlank(branUserEntity.getFaceFileName())) {
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                (branAdminConfigService.getFaceFileUploadPath(branUserEntity.getId()) +
                                        branUserEntity.getFaceFileName()) + ".jpg"), EmployeeConstants.FACE, sessionId));
                    }

                    log.info("查询离职");
                    log.info("branUserEntity.getId(): " + branUserEntity.getId());
                    // 查询离职
                    LeaveCertEntity leaveCertEntity = leaveCertDao.findBranUserLatestUnuseLeaveCert(branUserEntity.getId());

                    if (leaveCertEntity != null) {
                        log.info("getLeaveCertFileName: " + leaveCertEntity.getLeaveCertFileName());
                    }

                    if (leaveCertEntity != null && StringUtils.isNotBlank(leaveCertEntity.getLeaveCertFileName())) {
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                (branAdminConfigService.getLeaveUploadPath(branUserEntity.getId()) +
                                        leaveCertEntity.getLeaveCertFileName()) + ".jpg"), EmployeeConstants.LEAVE_CERT, sessionId));
                    }


                    // 学历
                    EducationEntity educationEntity = educationDao.findEducationByBranUserId(
                            branUserEntity.getId());


                    if (educationEntity != null) {
                        log.info("getEducationCertFileName: " + educationEntity.getEducationCertFileName());
                    }

                    if (educationEntity != null && StringUtils.isNotBlank(
                            educationEntity.getEducationCertFileName())) {

                        fileList.add(SysUtils.getAttachmentName(createFile(
                                (branAdminConfigService.getCertUploadPath(branUserEntity.getId()) +
                                        educationEntity.getEducationCertFileName()) + ".jpg"), EmployeeConstants.EDUCATION_CERT, sessionId));
                    }

                    if (branUserEntity.getBankCardEntity() != null && StringUtils.isNotBlank(branUserEntity
                            .getBankCardEntity().getBankCardUrl())) {
                        log.debug("branUserEntity.getBankCardEntity().getBankCardUrl())... " +
                                branUserEntity.getBankCardEntity().getBankCardUrl());
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                (branAdminConfigService.getBankCardUploadPath(branUserEntity.getId()) +
                                        branUserEntity.getBankCardEntity().getBankCardUrl()) + ".jpg"),
                                Constants.BANK_CARD, sessionId));
                    }

                }

                zipObj.setFileList(fileList);
                zipObjList.add(zipObj);
            });


        }

        if (type == 2) {
            log.info("type: " + type);
            attachment = "正式员工附件列表";

            List<EmployeeEntity> employees = employeeDao.findEmployeesByIds(Arrays.asList(phones), null);

            employees.forEach(emp -> {
                ZipObj zipObj = new ZipObj();
                List<File> fileList = new ArrayList<>();

                if (emp != null) {

                    BranUserEntity branUserEntity = branUserDao.findBranUsersById(emp.getBranUserId());

                    // 指定目录
                    String dir = emp.getRealName() + emp.getTelephone() + File.separator;
                    zipObj.setDir(dir);
                    log.info("setDir: " + dir);

                    log.info("getIdcardFrontFileName: " + emp.getIdcardFrontFileName());
                    if (StringUtils.isNotBlank(emp.getIdcardFrontFileName())) {
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                branAdminConfigService.getCorpPhotoPath(branUserEntity.getBranCorpId(), emp.getId()) + File.separator + EmployeeConstants.IDCARD_FRONT + ".jpg"), EmployeeConstants.IDCARD_FRONT, sessionId)
                        );
                    }

                    log.info("getIdcardBackFileName: " + emp.getIdcardBackFileName());
                    if (StringUtils.isNotBlank(emp.getIdcardBackFileName())) {
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                branAdminConfigService.getCorpPhotoPath(branUserEntity.getBranCorpId(), emp.getId()) + File.separator + EmployeeConstants.IDCARD_BACK + ".jpg"), EmployeeConstants.IDCARD_BACK, sessionId)
                        );
                    }

                    log.info("getIdcardFaceFileName: " + emp.getIdcardFaceFileName());
                    if (StringUtils.isNotBlank(emp.getIdcardFaceFileName())) {
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                branAdminConfigService.getCorpPhotoPath(branUserEntity.getBranCorpId(), emp.getId()) + File.separator + EmployeeConstants.FACE + ".jpg"), EmployeeConstants.FACE, sessionId)
                        );
                    }

                    log.info("getLeaveCertFileName: " + emp.getLeaveCertFileName());
                    if (StringUtils.isNotBlank(emp.getLeaveCertFileName())) {
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                branAdminConfigService.getCorpPhotoPath(branUserEntity.getBranCorpId(), emp.getId()) + File.separator + EmployeeConstants.LEAVE_CERT + ".jpg"), EmployeeConstants.LEAVE_CERT, sessionId));
                    }

                    // 学历
                    EducationEmployeeEntity educationEntity = educationEmpDao.findEducationByEmpId(emp.getId(), emp.getBranUserId());


                    if (educationEntity != null) {
                        log.info("getEducationCertFileName: " + educationEntity.getEducationCertFileName());
                    }

                    if (educationEntity != null && StringUtils.isNotBlank(
                            educationEntity.getEducationCertFileName())) {

                        fileList.add(SysUtils.getAttachmentName(createFile(
                                branAdminConfigService.getCorpPhotoPath(branUserEntity.getBranCorpId(), emp.getId())
                                        + File.separator + EmployeeConstants.EDUCATION_CERT + ".jpg"), EmployeeConstants.EDUCATION_CERT, sessionId)
                        );

                    }

                    if (emp.getBankCardEntity() != null && StringUtils.isNotBlank(emp
                            .getBankCardEntity().getBankCardUrl())) {
                        log.debug("emp.getBankCardEntity().getBankCardUrl())... " +
                                emp.getBankCardEntity().getBankCardUrl());
                        fileList.add(SysUtils.getAttachmentName(createFile(
                                branAdminConfigService.getCorpPhotoPath(branUserEntity.getBranCorpId(), emp.getId())
                                        + File.separator + Constants.BANK_CARD_1 + ".jpg"), Constants.BANK_CARD, sessionId)
                        );
                    }
                }

                zipObj.setFileList(fileList);
                zipObjList.add(zipObj);

            });

        }
        if (logMsg.size() > 0) {
            SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
            if (employeeType == 1) {
                info.setMsg("下载" + logMsg.get(0) + (logMsg.size() > 1 ? "等一批共" + logMsg.size() + "人的" : "的") + "待入职员工附件。");
                //待入职员工
                branOpLogDao.success(BranOpLogEntity.OP_MODULE_PROSPECTIVE_EMPLOYEE, BranOpLogEntity.OP_TYPE_DOWNLOAD, operator, info);
            } else if (employeeType == 2) {
                info.setMsg("下载" + logMsg.get(0) + (logMsg.size() > 1 ? "等一批共" + logMsg.size() + "人的" : "的") + "在职员工附件。");
                //在职员工
                branOpLogDao.success(BranOpLogEntity.OP_MODULE_EMPLOYEE, BranOpLogEntity.OP_TYPE_DOWNLOAD, operator, info);
            }
        }
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + SysUtils.parseEncoding(attachment + ".zip", "UTF-8"));
        System.out.println("in BatchDownload................");
        zos = new ZipOutputStream(response.getOutputStream());
        ZipCompressing.zip(zos, zipObjList);
        zos.close();
    }

    @Override
    public CheckinMessageSendTimeModel calculateCorpCheckinMessageSendTime(long checkinTime, String branCorpId) throws ParseException {
        BranCorpCheckinMessageEntity corpCheckinMessageEntity = corpCheckinMessageDao.findCheckinMessageByBranCorpId(branCorpId);
        return calculateCheckinMessageSendTime(checkinTime, corpCheckinMessageEntity.getBeforeCheckinDay(), corpCheckinMessageEntity.getPostHour());
    }

    @Override
    public CheckinMessageSendTimeModel calculateCheckinMessageSendTime(long checkinTime, int beforeDay, int postHour) throws ParseException {
        long sendTime = SysUtils.getOneDayStartTime(checkinTime) - beforeDay * 24 * 60 * 60 * 1000 + postHour * 60 * 60 * 1000;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String[] date = dateFormat.format(new Date(sendTime)).split("-");
        CheckinMessageSendTimeModel model = new CheckinMessageSendTimeModel();
        model.setYear(Integer.parseInt(date[0]));
        model.setMonth(Integer.parseInt(date[1]));
        model.setDay(Integer.parseInt(date[2]));
        model.setHour(Integer.parseInt(date[3]));
        model.setMinute(branAdminConfigService.getNotificationMinute());
        return model;
    }

    @Override
    public void changeCheckinMessageJobPhoneNo(String fromPhone, String toPhone, String branCorpId, long checkinTime) throws ParseException {
        scheduleService.deleteCheckinNotification(fromPhone);
        CheckinMessageSendTimeModel sendTimeModel = calculateCorpCheckinMessageSendTime(checkinTime, branCorpId);
        scheduleService.scheduleCheckinNotification(branCorpId, toPhone, checkinTime, sendTimeModel.getYear(), sendTimeModel.getMonth(), sendTimeModel.getDay(), sendTimeModel.getHour(), sendTimeModel.getMinute());
    }

    @Override
    public void changeCheckinMessageJobSendTime(String phone, String branCorpId, long newCheckinTime) throws ParseException {
        CheckinMessageSendTimeModel sendTimeModel = calculateCorpCheckinMessageSendTime(newCheckinTime, branCorpId);
        scheduleService.rescheduleCheckinNotification(branCorpId, phone, newCheckinTime, sendTimeModel.getYear(), sendTimeModel.getMonth(), sendTimeModel.getDay(), sendTimeModel.getHour(), sendTimeModel.getMinute());
    }

    @Override
    public void deleteCheckinMessageJob(String phone) {
        scheduleService.deleteCheckinNotification(phone);
    }

    @Override
    public void deleteLeaveEmployee(IdVersionsCommand command) throws Exception {

        List<String> ids = command.getIds();
        Map<String, Long> map = command.getMap();

        log.info("deleteLeaveEmployee: " + ids.size());
        if (ids == null) {
            return;
        }

        List<LeaveEmployeeEntity> leaveEmployeeEntities = leaveEmployeeDao.findLeaveEmployeeByIds(ids);
        StringBuilder msg = new StringBuilder();
        msg.append("删除离职员工: ");
        msg.append("\n");
        if (leaveEmployeeEntities == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORP_LEAVE_EMPLOYEE_NOT_FOUND);
        }
        ModelCommand modelCommand = new ModelCommand();
        for (LeaveEmployeeEntity leaveEmployeeEntity : leaveEmployeeEntities) {
            // 删除离职员工之前删除自定义项的关联关系
            modelCommand.setId(leaveEmployeeEntity.getId());
            Criteria criteria = userDefinedDetailsDao.findByLeaveId(modelCommand);
            List<UserDefinedDetailsEntity> dels = criteria.list();
            log.debug("删除自定义项: " + dels.size());
            userDefinedDetailsDao.delete(dels);

            // 乐观锁
            TxVersionUtil.compireVersion(leaveEmployeeEntity.getTxVersion(), map.get(leaveEmployeeEntity.getId()));
            leaveEmployeeEntity.setUpdateUser(command.getUserId());
            leaveEmployeeEntity.setUpdateTime(System.currentTimeMillis());
            leaveEmployeeEntity.setIsDelete(1);
            leaveEmployeeEntity.setTxVersion(leaveEmployeeEntity.getTxVersion() + 1);
            BranUserEntity branUserEntity = branUserDao.findBranUsersById(leaveEmployeeEntity.getBranUserId());
            if (branUserEntity != null) {
                msg.append(branUserEntity.getRealName()).append(" , ");
            }
        }
        // 假删除
        leaveEmployeeDao.update(leaveEmployeeEntities);
        SysLogDao.SysLogExtInfo info = new SysLogDao.SysLogExtInfo();
        // 记录日志
        log.info("msg: " + msg);
        info.setMsg(msg.toString());
        branOpLogDao.success(BranOpLogEntity.OP_MODULE_LEAVE_EMPLOYEE,
                BranOpLogEntity.OP_TYPE_DELETE, command.getId(), info);


    }

    @Override
    public DistractResult location(String id, int type) throws Exception {
        DistractResult result = new DistractResult();
        List<ModelResult> models = new ArrayList<>();
        // 如果type类型错误
        if (!(type == 0 || type == 1)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        // 查询所有的省
        if (type == 0) {
            id = Constants.ROOT_DISTRACT;
        } else {
            // 判断参数
            if (StringUtils.isBlank(id)) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
            }
        }
        List<DistrictEntity> dis = districtDao.findChildrensById(id);

        if (dis == null || dis.isEmpty()) {
            return result;
        }

        dis.forEach(d -> {
            ModelResult model = new ModelResult(
                    d.getId(),
                    d.getDistrictName(),
                    d.getTxVersion()
            );
            models.add(model);
        });

        result.setModels(models);
        return result;
    }

    @Override
    public Map update(RosterCommand command) throws Exception {
        ExceptionModel exceptionModel = new ExceptionModel();
        // 判断id
        if (StringUtils.isBlank(command.getId())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        // 判断id是否存在于数据库中
        EmployeeEntity emp = employeeDao.findEmployeeById(command.getId(), command.getBranCorpId());
        if (emp == null) {
            throw new AryaServiceException(ErrorCode.CODE_CORPORATION_USER_NOT_FIND);
        }
        // 判断version
        TxVersionUtil.compireVersion(command.getVersion(), emp.getTxVersion());

//        if (StringUtils.isBlank(command.getUrgentContact())) {
//            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
//        }
//
//        if (StringUtils.isBlank(command.getUrgentContactPhone())) {
//            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
//        }

        // 检查银行卡号
        if (StringUtils.isNotBlank(command.getBankNum())) {
            if (!command.getBankNum().matches("\\d{1,21}")) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "银行卡号必须在1-21个全数字");
            }
        }

        // 检查身份证
        if (StringUtils.isBlank(command.getIdCardNo())) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证必填");
        }

//        log.info("检查省市区");
//        Assert.notBlank(command.getProvinceId(), "修改提示：请在【个人信息】中选择正确的【省】");
//        Assert.notBlank(command.getCityId(), "修改提示：请在【个人信息】中选择正确的【市】");
//        Assert.notBlank(command.getCountyId(), "修改提示：请在【个人信息】中选择正确的【区】");

        ProspectiveQueryCommand prospectiveQueryCommand = new ProspectiveQueryCommand();
        prospectiveQueryCommand.setCreateType(2);
        prospectiveQueryCommand.setTel(command.getRegisterAccount());
        prospectiveQueryCommand.setBranCorpId(command.getBranCorpId());
        List<ProspectiveEmployeeEntity> prospectiveEmployeeEntities = prospectiveEmployeeDao.findByQueryCommand(prospectiveQueryCommand);
        if (!ListUtils.checkNullOrEmpty(prospectiveEmployeeEntities)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "注册账号不能与待入职员工的手机号相同");
        }

        log.info("查询员工自己添加的待入职");
        prospectiveQueryCommand.setCreateType(1);
        prospectiveEmployeeEntities = prospectiveEmployeeDao.findByQueryCommand(prospectiveQueryCommand);
        if (!ListUtils.checkNullOrEmpty(prospectiveEmployeeEntities)) {
            log.info("待入职员工存在删除待入职员工");
            prospectiveEmployeeDao.delete(prospectiveEmployeeEntities);
            log.info("修改绑定状态,已绑定");
            emp.setIsBinding(1);
            BranUserEntity branUserEntity = branUserDao.findBranUserByPhoneNoAndCorpId(command.getRegisterAccount(), command.getBranCorpId());
            Assert.notNull(branUserEntity, "没有查询到企业用户");
            emp.setBranUserId(branUserEntity.getId());
        }

        EmpQueryCommand empQueryCommand = new EmpQueryCommand();
        empQueryCommand.setBranCorpId(command.getBranCorpId());
        empQueryCommand.setRegisterAccount(command.getRegisterAccount());
        List<EmployeeEntity> registerAccounts = employeeDao.findByQueryCommand(empQueryCommand);
        if (!ListUtils.checkNullOrEmpty(registerAccounts)) {
            command.setRegisterAccount(command.getRegisterAccount().trim());
            if (!command.getRegisterAccount().equals(emp.getRegisterAccount()) &&
                    registerAccounts.size() == 1 && registerAccounts.get(0).getRegisterAccount().equals(command.getRegisterAccount())) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "注册账号不能与花名册员工的注册账号相同");
            }
        }

        empQueryCommand.setRegisterAccount(null);
        empQueryCommand.setWorkSn(null);
        empQueryCommand.setIdCardNo(command.getIdCardNo().trim());
        empQueryCommand.setBranCorpId(command.getBranCorpId());

        registerAccounts = employeeDao.findByQueryCommand(empQueryCommand);
        if (!ListUtils.checkNullOrEmpty(registerAccounts)) {
            if (!command.getIdCardNo().equals(emp.getIdCardNo()) &&
                    registerAccounts.size() == 1 && registerAccounts.get(0).getIdCardNo().equals(command.getIdCardNo())) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证不能与花名册员工的身份证相同");
            }
        }
        empQueryCommand.setIdCardNo(command.getIdCardNo().trim());

        // 注册账号
        emp.setRegisterAccount(command.getRegisterAccount());
        // 姓名
        emp.setRealName(command.getRealName());
        emp.setIdCardNo(command.getIdCardNo());
        Assert.notBlank(command.getWorkSnPrefixId(), "工号前缀id必填");
        Assert.notBlank(command.getWorkSn(), "工号后缀不能为空");

        WorkSnPrefixEntity workSnPrefixEntity = workSnPrefixDao.findById(command.getWorkSnPrefixId());
        // 拼接工号
        StringBuilder updateWorkSn = new StringBuilder();
        if ("empty".equals(command.getWorkSnPrefixId())) {
            WorkSnFormatCommand workSnFormatCommand = new WorkSnFormatCommand();
            workSnFormatCommand.setWorkSnPrefixId(null);
            workSnFormatCommand.setWorkSnPrefixName("empty");
            workSnFormatCommand.setWorkSn(command.getWorkSn());
            workSnFormatCommand.setId(null);
            updateWorkSn.append(empHelper.getFormatWorkSn(workSnFormatCommand));
        } else {
            updateWorkSn.append(workSnPrefixEntity.getPrefixName());
            updateWorkSn.append(command.getWorkSn());
        }
        log.debug("更新的工号: " + updateWorkSn);
        // 判断工号是否与原来的相同
        // 如果不相同,则继续判断工号是否重复
        if (!updateWorkSn.equals(emp.getWorkSn())) {
            List<String> list = new ArrayList<>();
            list.add(updateWorkSn.toString());
            // 判断工号是否重复
            WorkSnPrefixQueryCommand workSnPrefixQueryCommand = new WorkSnPrefixQueryCommand();
            workSnPrefixQueryCommand.setWorkSn(list.get(0));
            workSnPrefixQueryCommand.setEmpId(emp.getId());
            workSnPrefixQueryCommand.setBranCorpId(command.getBranCorpId());
            if ("empty".equals(command.getWorkSnPrefixId())) {
                workSnPrefixQueryCommand.setWorkSnPrefixName("empty");
            }
            if (StringUtils.isNotBlank(command.getPrefixName())) {
                workSnPrefixQueryCommand.setWorkSnPrefixName(command.getPrefixName());
            }
            if (employeeDao.isWorkSnsBeenUsed(workSnPrefixQueryCommand)) {
                throw new AryaServiceException(ErrorCode.CODE_CORP_START_WORK_SN_HAS_BEEN_USED);
            }
            emp.setWorkSn(updateWorkSn.toString());
            if (workSnPrefixEntity == null) {
                emp.setWorkSnPrefixId(null);
                emp.setWorkSnPrefixName("empty");
            } else {
                emp.setWorkSnPrefixId(workSnPrefixEntity.getId());
                emp.setWorkSnPrefixName(workSnPrefixEntity.getPrefixName());
            }
            emp.setWorkSnSuffixName(Long.valueOf(command.getWorkSn()));
        }


        // 判断联系人与紧急联系人是否是同一个
        if (StringUtils.isNotBlank(command.getTelephone()) &&
                StringUtils.isNotBlank(command.getUrgentContactPhone()) &&
                command.getTelephone().trim().equals(command.getUrgentContactPhone().trim())) {
            exceptionModel.setError(ErrorCode.CODE_PARAMS_ERROR);
            exceptionModel.setMsg("联系人电话与紧急联系人一致,请重新修改");
            throw new AryaServiceException(exceptionModel);
        }

//        if (StringUtils.isBlank(command.getAddress())) {
//            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
//        }

        emp.setDepartmentId(command.getDepartmentId());
        emp.setDepartmentName(departmentDao.findCorpDepartmentByIdThrow(command.getDepartmentId(), command.getBranCorpId()).getDepartmentName());
        emp.setPositionId(command.getPositionId());
        emp.setPositionName(positionDao.findCorpPositionByIdThrow(command.getBranCorpId(), command.getPositionId()).getPositionName());
        //如果更新前后员工班组 所属 考勤机不统一。需要删除
        if (StringUtils.isNotBlank(emp.getWorkShiftId()) && StringUtils.isNotBlank(command.getWorkShiftId()) && !emp.getWorkShiftId().equals(command.getWorkShiftId())) {
            List<WorkAttendanceDeviceUserSynEntity> oldDevices = workAttendanceDeviceUserSynDao.findByEmpId(emp.getId());
            List<WorkAttendanceDeviceWorkShiftEntity> newDevices = workAttendanceDeviceWorkShiftDao.findByCorpIdAndWorkShiftId(command.getBranCorpId(), command.getWorkShiftId());

            Set<String> deviceNoset = new HashSet<>();
            Set<String> newDeviceNoset = new HashSet<>();
            newDevices.forEach(entity -> deviceNoset.add(entity.getDeviceNo()));
            oldDevices.forEach(entity -> newDeviceNoset.add(entity.getDeviceNo()));
            //如果原来的班组的关联有空余，则删除。
            if (!(deviceNoset.containsAll(newDeviceNoset) && newDeviceNoset.containsAll(deviceNoset))) {
                emp.setWorkAttendanceAddState(EmployeeEntity.WorkAttendanceAddState.initial);
            }
        }
        emp.setWorkShiftId(command.getWorkShiftId());
        WorkShiftEntity workShiftEntity = workShiftDao.findByIdNotDelete(command.getWorkShiftId());
        if (workShiftEntity != null) {
            emp.setWorkShiftName(workShiftEntity.getShiftName());
        }
        emp.setWorkLineName(workLineDao.findCorpWorkLineByIdThrow(command.getWorkLineId(), command.getBranCorpId()).getLineName());
        emp.setWorkLineId(command.getWorkLineId());

        emp.setSex(command.getSex());
        emp.setMarriage(command.getMarriage());
        emp.setNation(command.getNation());
        emp.setIdcardAddress(command.getIdcardAddress());
        emp.setTelephone(command.getTelephone());
        emp.setAddress(command.getAddress());

        if (StringUtils.isNotBlank(command.getProvinceId())) {
            emp.setAddProvinceCode(command.getProvinceId());
            emp.setAddProvinceName(districtDao.findDistrictById(command.getProvinceId()).getDistrictName());
        } else {
            emp.setAddProvinceCode(null);
            emp.setAddProvinceName(null);
        }

        if (StringUtils.isNotBlank(command.getCityId())) {
            emp.setAddCityCode(command.getCityId());
            emp.setAddCityName(districtDao.findDistrictById(command.getCityId()).getDistrictName());
        } else {
            emp.setAddCityCode(null);
            emp.setAddCityName(null);
        }

        if (StringUtils.isNotBlank(command.getCountyId())) {
            emp.setAddCountyCode(command.getCountyId());
            emp.setAddCountyName(districtDao.findDistrictById(command.getCountyId()).getDistrictName());
        } else {
            emp.setAddCountyCode(null);
            emp.setAddCountyName(null);
        }


        emp.setAddress(command.getAddress());
        emp.setUrgentContact(command.getUrgentContact());
        emp.setUrgentContactPhone(command.getUrgentContactPhone());
        // 班车
        emp.setBusAddress(command.getBusAddress());
        EmployeeDetailEntity employeeDetailEntity = new EmployeeDetailEntity();
        employeeDetailEntity.setInterviewDate(command.getInterviewDate());
        emp.setCheckinTime(command.getCheckinTime());
        emp.setStartTime(command.getStartTime());
        emp.setEndTime(command.getEndTime());
        if (command.getIsNolimit() == 1) {
            emp.setEndTime(EmployeeEntity.TIME_NO_LIMIT);
        }

        employeeDetailEntity.setSourceOfSupply(command.getSourceOfSupply());
        employeeDetailEntity.setEmployeeNature(command.getEmployeeNature());
        emp.setDetail(employeeDetailEntity);
        // 银行卡
        BankCardEntity bankCardEntity = new BankCardEntity();
        bankCardEntity.setBankAccount(command.getBankAccount());
        bankCardEntity.setBankNum(command.getBankNum());
        emp.setBankCardEntity(bankCardEntity);

        emp.setUpdateTime(System.currentTimeMillis());
        emp.setUpdateUser(command.getUserId());

        // 出生年月
        log.debug("出生年月HR编辑时: " + command.getBornDate());
        if (command.getBornDate() != null) {
            DateTime dateTime = new DateTime(command.getBornDate());
            DateTime cur = new DateTime();
            log.debug("出生年月HR填写格式化: " + DateTimeUtils.format(dateTime));
            emp.setBornDate(dateTime.toDate());
            DateTime curYearBirthday = new DateTime(cur.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0, 0);
            log.debug("生日日期: " + DateTimeUtils.format(curYearBirthday));
            emp.setCurYearBirthday(curYearBirthday.toDate());
            emp.setIsDisposed(0);
            // 计算年龄
            emp.setAge(SysUtils.getAgeByDate(new Date(command.getBornDate())));
            log.debug("年龄: " + emp.getAge());
        }

        // 修改自定义值
        List<UserDefinedDetailsResult> list = command.getUserDefinedDetailsResultList();
        if (list != null && !list.isEmpty()) {
            for (UserDefinedDetailsResult userDefinedDetailsCommand : list) {
                UserDefinedDetailsEntity userDefinedDetailsEntity = userDefinedDetailsDao.findByIdNotDelete(userDefinedDetailsCommand.getDetailsId());
                if (userDefinedDetailsEntity == null) {
                    continue;
                }
                userDefinedDetailsEntity.setColValue(userDefinedDetailsCommand.getColValue());
                userDefinedDetailsDao.update(userDefinedDetailsEntity);
            }
        }
        emp.setTxVersion(emp.getTxVersion() + 1);

        emp.setPoliticalStatus(command.getPoliticalStatus());
        emp.setProfessionalCategory(command.getProfessionalCategory());
        emp.setSocialSecurityType(command.getSocialSecurityType());
        emp.setDegreeOfEducation(command.getDegreeOfEducation());
        if (command.getGraduationTime() != null) {
            emp.setGraduationTime(new Date(command.getGraduationTime()));
        }
        emp.setGraduatedSchool(command.getGraduatedSchool());

        if (command.getExpireStartTime() == null) {
            if (command.getIsLongTerm() == 1) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证有效期开始时间必填");
            }

            if (command.getExpireEndTime() != null) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证有效期开始时间必填");
            }

        } else {
            if (command.getExpireEndTime() == null && command.getIsLongTerm() == 0) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证有效期结束时间必填");
            }

            if (command.getExpireEndTime() != null) {
                if (command.getIsLongTerm() == 0) {
                    if (command.getExpireStartTime() >= command.getExpireEndTime()) {
                        throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "身份证有效期开始时间不能大于结束时间");
                    }
                }
            }
        }

        String expireTime = "";

        if (command.getExpireStartTime() != null) {
            emp.setExpireStartTime(new Date(command.getExpireStartTime()));
            expireTime += SysUtils.getDateStringFormTimestamp(command.getExpireStartTime(), "yyyy.MM.dd");
        } else {
            emp.setExpireStartTime(null);
        }

        if (command.getExpireEndTime() != null) {
            emp.setExpireEndTime(new Date(command.getExpireEndTime()));
            expireTime += "-" + SysUtils.getDateStringFormTimestamp(command.getExpireEndTime(), "yyyy.MM.dd");
            if (command.getIsLongTerm() != null && command.getIsLongTerm() == 1) {
                expireTime += "-长期";
            }
        } else {
            emp.setExpireEndTime(null);
        }

        emp.setExpireTime(expireTime);
        log.info("expireTime: " + expireTime);
        emp.setIsLongTerm(command.getIsLongTerm());

        employeeDao.update(emp);
        ModelResult model = new ModelResult();
        model.setId(emp.getId());
        model.setVersion(emp.getTxVersion());
        Map<String, ModelResult> map = new HashMap<>();
        map.put("model", model);
        return map;
    }

    @Override
    public SelectModelResult<RosterResult> expiration(RosterCommand command) throws Exception {
        SelectModelResult<RosterResult> result = new SelectModelResult<>();
        List<RosterResult> models = new ArrayList<>();

        List<EmployeeEntity> emps = employeeDao.findExpirationPageList(command.getBranCorpId(),
                command.getPage(), command.getPageSize(), command.getUserId());
        emps.forEach(emp -> {
            RosterResult subResult = new RosterResult();
            subResult.setId(emp.getId());
            BranUserEntity user = branUserDao.findBranUsersById(emp.getBranUserId());
            if (user == null) {
                throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_USER_NOT_FOUND);
            }
            subResult.setName(user.getRealName());
            subResult.setVersion(emp.getTxVersion());
            subResult.setStartTime(emp.getStartTime());
            subResult.setEndTime(emp.getEndTime());
            models.add(subResult);
        });
        result.setModels(models);
        int total = employeeDao.findAllExpiration(command.getBranCorpId(), command.getUserId());
        result.setTotalPage(Utils.calculatePages(total, command.getPageSize()));
        result.setTotalCount(total);
        return result;
    }

    @Override
    public SelectModelResult<RosterResult> probation(RosterCommand command) throws Exception {
        SelectModelResult<RosterResult> result = new SelectModelResult<>();
        List<RosterResult> models = new ArrayList<>();

        List<EmployeeEntity> emps = employeeDao.findProbationPageList(command.getBranCorpId(),
                command.getPage(), command.getPageSize(), command.getUserId());

        emps.forEach(emp -> {
            RosterResult subResult = new RosterResult();
            subResult.setId(emp.getId());
            BranUserEntity user = branUserDao.findBranUsersById(emp.getBranUserId());
            if (user == null) {
                throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_USER_NOT_FOUND);
            }
            subResult.setName(user.getRealName());
            subResult.setVersion(emp.getTxVersion());
            subResult.setStartTime(emp.getStartTime());

            if (emp.getStartTime() > 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(emp.getStartTime());
                calendar.add(Calendar.MONTH, emp.getProbation());
                // 试用期到期减1天
                calendar.add(Calendar.DATE, -1);
                log.debug("试用期到期时间为: " + SysUtils.getDateStringFormTimestamp(calendar.getTimeInMillis()));
                subResult.setEndTime(calendar.getTimeInMillis());
            }
            models.add(subResult);
        });
        result.setModels(models);
        int total = employeeDao.findAllProbation(command.getBranCorpId(), command.getUserId());
        result.setTotalPage(Utils.calculatePages(total, command.getPageSize()));
        result.setTotalCount(total);
        return result;
    }

    @Override
    public SelectModelResult<ModelResult> probationProcess(IdVersionsCommand command) throws Exception {
        SelectModelResult<ModelResult> result = new SelectModelResult<>();
        List<ModelResult> models = new ArrayList<>();
        List<EmployeeEntity> list = employeeDao.findEmployeesByIds(command.getIds(), command.getBranCorpId());
        Map<String, Long> map = command.getMap();

        if (list != null) {
            list.forEach(emp -> {
                // 乐观锁
                TxVersionUtil.compireVersion(emp.getTxVersion(), map.get(emp.getId()));
                emp.setProbationhandled(1);
                emp.setTxVersion(emp.getTxVersion() + 1);
                ModelResult subResult = new RosterResult();
                subResult.setId(emp.getId());
                subResult.setVersion(emp.getTxVersion());
                models.add(subResult);
            });

            employeeDao.update(list);
            result.setModels(models);
        }
        return result;
    }

    @Override
    public EmployeeResult getId(String id, SessionInfo sessionInfo) throws Exception {
        EmployeeResult employeeResult = new EmployeeResult();
        if (StringUtils.isBlank(id)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }

        EmployeeEntity employeeEntity = employeeDao.findEmployeeById(id, sessionInfo.getCorpId());
        if (employeeEntity == null) {
            throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_USER_NOT_FOUND);
        }
//        BranUserEntity branUserEntity = branUserDao.findBranUsersById(employeeEntity.getBranUserId());
//        if (branUserEntity == null) {
//            throw new AryaServiceException(ErrorCode.CODE_INDUCTION_BRAN_USER_NOT_FOUND);
//        }


        employeeResult.setId(employeeEntity.getId());
        employeeResult.setRegisterAccount(employeeEntity.getRegisterAccount());
        employeeResult.setIsBinding(employeeEntity.getIsBinding());
        employeeResult.setVersion(employeeEntity.getTxVersion());
        employeeResult.setRealName(employeeEntity.getRealName());
        employeeResult.setIdCardNo(employeeEntity.getIdCardNo());
        if (StringUtils.isBlank(employeeEntity.getWorkSnPrefixId())) {
            employeeResult.setWorkSnPrefixId("empty");
            employeeResult.setWorkSn(empHelper.getFormatWorkSn(employeeEntity.getWorkSnFormatCommand()));
        } else {
            employeeResult.setWorkSnPrefixId(employeeEntity.getWorkSnPrefixId());
            int index = (employeeEntity.getWorkSn().indexOf(employeeEntity.getWorkSnPrefixName()));
            if (index != -1) {
                employeeResult.setWorkSn(employeeEntity.getWorkSn().substring(index + employeeEntity.getWorkSnPrefixName().length()));
                logger.info("workSn: " + employeeResult.getWorkSn());
            }

        }

        employeeResult.setPrefixName(employeeEntity.getWorkSnPrefixName());
        employeeResult.setDepartmentId(employeeEntity.getDepartmentId());
        employeeResult.setDepartmentName(employeeEntity.getDepartmentName());
        employeeResult.setPositionId(employeeEntity.getPositionId());
        employeeResult.setPositionName(employeeEntity.getPositionName());
        employeeResult.setWorkShiftId(employeeEntity.getWorkShiftId());
        employeeResult.setWorkShiftName(employeeEntity.getWorkShiftName());
        employeeResult.setWorkLineId(employeeEntity.getWorkLineId());
        employeeResult.setWorkLineName(employeeEntity.getWorkLineName());
        employeeResult.setSex(employeeEntity.getSex());
        employeeResult.setMarriage(employeeEntity.getMarriage());
        employeeResult.setNation(employeeEntity.getNation());
        employeeResult.setIdcardAddress(employeeEntity.getIdcardAddress());
        employeeResult.setTelephone(employeeEntity.getTelephone());
        employeeResult.setProvinceId(employeeEntity.getAddProvinceCode());
        employeeResult.setProvinceName(employeeEntity.getAddProvinceName());
        employeeResult.setCityId(employeeEntity.getAddCityCode());
        employeeResult.setCityName(employeeEntity.getAddCityName());
        employeeResult.setCountyId(employeeEntity.getAddCountyCode());
        employeeResult.setCountyName(employeeEntity.getAddCountyName());
        employeeResult.setAddress(employeeEntity.getAddress());
        employeeResult.setUrgentContact(employeeEntity.getUrgentContact());
        employeeResult.setUrgentContactPhone(employeeEntity.getUrgentContactPhone());
        employeeResult.setBusAddress(employeeEntity.getBusAddress());
        EmployeeDetailEntity detailEntity = employeeEntity.getDetail();
        if (detailEntity != null) {
            employeeResult.setInterviewDate(detailEntity.getInterviewDate());
            employeeResult.setSourceOfSupply(detailEntity.getSourceOfSupply());
            employeeResult.setEmployeeNature(detailEntity.getEmployeeNature());
        }
        employeeResult.setCheckinTime(employeeEntity.getCheckinTime());
        employeeResult.setStartTime(employeeEntity.getStartTime());
        employeeResult.setEndTime(employeeEntity.getEndTime());

        // 其他信息
        BankCardEntity bankCardEntity = employeeEntity.getBankCardEntity();
        if (bankCardEntity != null) {
            employeeResult.setBankAccount(bankCardEntity.getBankAccount());
            employeeResult.setBankNum(bankCardEntity.getBankNum());
        }

        // 生日日期
        if (employeeEntity.getBornDate() != null) {
            employeeResult.setBornDate(employeeEntity.getBornDate().getTime());
        }

        ModelCommand command = new ModelCommand();
        command.setId(employeeEntity.getId());
        List<UserDefinedDetailsResult> userDefinedDetailsResultList = userDefinedDetailsHelper.getUserDefinedColsForEmp(command);
        if (userDefinedDetailsResultList == null || userDefinedDetailsResultList.isEmpty()) {
            userDefinedDetailsResultList = new ArrayList<>();
            // 如果没找到 就查询公司
            UserDefinedCommand userDefinedCommand = new UserDefinedCommand();
            userDefinedCommand.setBranCorpId(sessionInfo.getCorpId());
            List<UserDefinedColsEntity> list = userDefinedColsDao.findByCorpId(userDefinedCommand).list();
            for (UserDefinedColsEntity userDefinedColsEntity : list) {
                UserDefinedDetailsEntity userDefinedDetailsEntity = new UserDefinedDetailsEntity();
                userDefinedDetailsEntity.setId(Utils.makeUUID());
                userDefinedDetailsEntity.setCreateUser(sessionInfo.getUserId());
                userDefinedDetailsEntity.setCreateTime(System.currentTimeMillis());
                userDefinedDetailsEntity.setEmployeeEntity(employeeEntity);
                userDefinedDetailsEntity.setUserDefinedColsEntity(userDefinedColsEntity);
                userDefinedDetailsDao.create(userDefinedDetailsEntity);
                UserDefinedDetailsResult userDefinedDetailsResult = new UserDefinedDetailsResult();
                userDefinedDetailsResult.setDetailsId(userDefinedDetailsEntity.getId());
                userDefinedDetailsResult.setColName(userDefinedColsEntity.getColName());
                userDefinedDetailsResult.setColValue(userDefinedDetailsEntity.getColValue());
                userDefinedDetailsResult.setType(userDefinedColsEntity.getType());
                userDefinedDetailsResultList.add(userDefinedDetailsResult);
            }

        } else {
            // 查看更新
            UserDefinedCommand userDefinedCommand = new UserDefinedCommand();
            userDefinedCommand.setBranCorpId(sessionInfo.getCorpId());
            userDefinedCommand.setUserId(sessionInfo.getUserId());
            userDefinedDetailsHelper.checkUpdate(userDefinedDetailsResultList, userDefinedCommand, employeeEntity, null, null);
        }

        employeeResult.setUserDefinedDetailsResultList(userDefinedDetailsResultList);
        // 政治面貌
        employeeResult.setPoliticalStatus(employeeEntity.getPoliticalStatus());
        employeeResult.setProfessionalCategory(employeeEntity.getProfessionalCategory());
        employeeResult.setSocialSecurityType(employeeEntity.getSocialSecurityType());
        employeeResult.setDegreeOfEducation(employeeEntity.getDegreeOfEducation());
        if (employeeEntity.getGraduationTime() != null) {
            employeeResult.setGraduationTime(employeeEntity.getGraduationTime().getTime());
        }
        employeeResult.setGraduatedSchool(employeeEntity.getGraduatedSchool());

        // 身份证有效期
        if (employeeEntity.getExpireStartTime() != null) {
            employeeResult.setExpireStartTime(employeeEntity.getExpireStartTime().getTime());
        }
        if (employeeEntity.getExpireEndTime() != null) {
            employeeResult.setExpireEndTime(employeeEntity.getExpireEndTime().getTime());
        }
        employeeResult.setIsLongTerm(employeeEntity.getIsLongTerm());


        return employeeResult;
    }

    /**
     * 首页显示已经完成入职流程的待入职员工
     *
     * @param command
     * @return
     */
    @Override
    public Map<String, Object> acceptOfferUsers(RosterCommand command) throws Exception {
        // 设置查询参数
        Map<String, Object> params = new HashMap<>();
        params.put("acceptOffer", Constants.TRUE);
        params.put("isDelete", Constants.FALSE);
        params.put("branCorpId", command.getBranCorpId());
        params.put("checkinComplete", 1);


        // 开始查询
        Criteria criteria = prospectiveEmployeeDao.findReturnCriteria(params)
                .add(Restrictions.or(Restrictions.eq("createType", Constants.HR_CREATE), Restrictions.isNull("createType")));
        Pager<ProspectiveEmployeeEntity> pager = prospectiveEmployeeDao.getPagerByCriteria(criteria,
                command.getPage(), command.getPageSize());


        // 设置相应参数
        params.clear();
        params.put("models", pager.getResult());
        params.put("totalPage", pager.getPage());
        params.put("totalCount", pager.getRowCount());
        return params;
    }

    @Override
    public List<QueryEmpResult> getAllEmpListByBranCorpId(SessionInfo sessionInfo) {
        return employeeDao.findByBranCorpId(sessionInfo.getCorpId())
                .stream()
                .map(one -> {
                    return new QueryEmpResult() {
                        {
                            this.convert(one);
                        }
                    };
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<QueryLeaveEmpResult> getAllLeaveEmpListByBranCorpId(SessionInfo sessionInfo) {
        return leaveEmployeeDao.findByBranCorpId(sessionInfo.getCorpId())
                .stream()
                .map(one -> {
                    return new QueryLeaveEmpResult() {
                        {
                            this.convert(one);
                        }
                    };
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProspectiveCheckResult> checkProspectiveEmployees(BaseCommand.BatchIds command, SessionInfo sessionInfo) {

        List<ProspectiveCheckResult> results = new ArrayList<>();

        for (BaseCommand.IDCommand idCommand : command.getBatch()) {
            ProspectiveEmployeeEntity prospectiveEmployeeEntity = prospectiveEmployeeDao.findProspectiveEmployeeById(idCommand.getId());
            if (prospectiveEmployeeEntity == null) {
                log.warn("没有查询到待入职员工", " id ", idCommand.getId());
                continue;
            }

            log.debug("checkProspectiveEmployees prospectiveRuleBuilder start");
            prospectiveRuleBuilder
                    .clear()
                    .addCheckEntity(prospectiveEmployeeEntity)
                    .addRule(prospectiveRuleBuilder.new RequireFieldRule())
                    .addRule(prospectiveRuleBuilder.new CheckinCompleteRule())
                    .addRule(prospectiveRuleBuilder.new UnregisteredRule())
                    .addRule(prospectiveRuleBuilder.new AppInfoUnfilledRule())
                    .addRule(prospectiveRuleBuilder.new CheckinDiffCorp())
                    .addRule(prospectiveRuleBuilder.new ProspectiveProFileNotComplete())
                    .addRule(prospectiveRuleBuilder.new InfoUnMatch())
                    .check()
                    .onCheckSuccess(p -> logger.debug("待入职员工可以成功入职: " + p.getId(), p.getFullName()))
                    .onCheckFail(p -> {
                        logger.debug("待入职员工入职失败: " + p.getId(), p.getFullName());
                        ProspectiveCheckResult prospectiveCheckResult = new ProspectiveCheckResult();
                        prospectiveCheckResult.setId(p.getId());
                        prospectiveCheckResult.setName(p.getFullName());
                        prospectiveCheckResult.setReason(prospectiveRuleBuilder.getErrorStr());
                        prospectiveCheckResult.setSuccessOrFail(1);
                        results.add(prospectiveCheckResult);
                    });
        }
        return results;
    }

    EmployeeDetailProfileResult generateProfileResult(AryaUserEntity aryaUserEntity, BranUserEntity branUserEntity,
                                                      String workSn, int marriage, String urgentContact,
                                                      String urgentContactPhone, String faceUrl) {
        EmployeeDetailProfileResult profileResult = new EmployeeDetailProfileResult();
        profileResult.setName(aryaUserEntity.getRealName());
        profileResult.setWorkSn(workSn);
        if (StringUtils.isNotBlank(aryaUserEntity.getRealName()) && !"-".equals(aryaUserEntity.getRealName())) {
            profileResult.setSex(EmployeeConstants.getSex(branUserEntity.getSex()));
        }
        if (!StringUtils.isAnyBlank(branUserEntity.getBirthday())) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                long birthDay = simpleDateFormat.parse(branUserEntity.getBirthday()).getTime();
                profileResult.setAge(Utils.calculateAge(birthDay));
                profileResult.setBirthday(birthDay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        profileResult.setFaceUrl(faceUrl);
        profileResult.setMarriage(EmployeeConstants.getMarriage(marriage));
        profileResult.setNation(branUserEntity.getNation());
        profileResult.setIdcardNo(aryaUserEntity.getIdcardNo());
        profileResult.setPhoneNo(aryaUserEntity.getPhoneNo());
        profileResult.setEmail(branUserEntity.getEmail());
        if (branUserEntity.getAddProvinceName() != null) {
            profileResult.setAddress(branUserEntity.getAddProvinceName());
        }
        if (branUserEntity.getAddCityName() != null) {
            profileResult.setAddress(profileResult.getAddress() + branUserEntity.getAddCityName());
        }
        if (branUserEntity.getAddCountyName() != null) {
            profileResult.setAddress(profileResult.getAddress() + branUserEntity.getAddCountyName());
        }
        if (StringUtils.isNotBlank(branUserEntity.getAddress())) {
            profileResult.setAddress(profileResult.getAddress() + branUserEntity.getAddress());
        }
        if (StringUtils.isNotBlank(branUserEntity.getIdcardAddress())) {
            profileResult.setOriginDistrict(branUserEntity.getIdcardAddress());
        }
        profileResult.setUrgentContact(urgentContact);
        profileResult.setUrgentContactPhone(urgentContactPhone);

//        profileResult.setSourceOfSupply();
//        profileResult.setEmployeeNature();
        if (branUserEntity.getBankCardEntity() != null) {
            profileResult.setBankNum(branUserEntity.getBankCardEntity().getBankNum());
            profileResult.setBankAccount(branUserEntity.getBankCardEntity().getBankAccount());
            profileResult.setBusAddress(branUserEntity.getBusAddress());
        }

//        profileResult.setInterviewDate();

        return profileResult;
    }

    EmployeeDetailProfileResult generateProfileResult(EmployeeEntity employeeEntity, String workSn, int marriage, String urgentContact, String urgentContactPhone, String faceUrl) {
        EmployeeDetailProfileResult profileResult = new EmployeeDetailProfileResult();
        profileResult.setName(employeeEntity.getRealName());
        profileResult.setWorkSn(workSn);
        if (StringUtils.isNotBlank(employeeEntity.getRealName()) && !"-".equals(employeeEntity.getRealName())) {
            profileResult.setSex(EmployeeConstants.getSex(employeeEntity.getSex()));
        }
        if (!StringUtils.isAnyBlank(employeeEntity.getBirthday())) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                long birthDay = simpleDateFormat.parse(employeeEntity.getBirthday()).getTime();
                profileResult.setAge(Utils.calculateAge(birthDay));
                profileResult.setBirthday(birthDay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        profileResult.setFaceUrl(faceUrl);
        profileResult.setMarriage(EmployeeConstants.getMarriage(marriage));
        profileResult.setNation(employeeEntity.getNation());
        profileResult.setIdcardNo(employeeEntity.getIdCardNo());
        profileResult.setPhoneNo(employeeEntity.getTelephone());
        profileResult.setEmail(employeeEntity.getEmail());
        if (employeeEntity.getAddProvinceName() != null) {
            profileResult.setAddress(employeeEntity.getAddProvinceName());
        }
        if (employeeEntity.getAddCityName() != null) {
            profileResult.setAddress(profileResult.getAddress() + employeeEntity.getAddCityName());
        }
        if (employeeEntity.getAddCountyName() != null) {
            profileResult.setAddress(profileResult.getAddress() + employeeEntity.getAddCountyName());
        }
        if (StringUtils.isNotBlank(employeeEntity.getAddress())) {
            profileResult.setAddress(profileResult.getAddress() + employeeEntity.getAddress());
        }

        if (StringUtils.isNotBlank(employeeEntity.getIdcardAddress())) {
            profileResult.setOriginDistrict(employeeEntity.getIdcardAddress());
        }
        // 银行卡信息
        if (employeeEntity.getBankCardEntity() != null) {
            profileResult.setBankNum(employeeEntity.getBankCardEntity().getBankNum());
            profileResult.setBankAccount(employeeEntity.getBankCardEntity().getBankAccount());
            profileResult.setBusAddress(employeeEntity.getBusAddress());
        }
        // 班车点
        profileResult.setBusAddress(employeeEntity.getBusAddress());
        // 其他信息
        if (employeeEntity.getDetail() != null) {
            profileResult.setSourceOfSupply(employeeEntity.getDetail().getSourceOfSupply());
            profileResult.setEmployeeNature(employeeEntity.getDetail().getEmployeeNature());
            profileResult.setInterviewDate(employeeEntity.getDetail().getInterviewDate());
        }
        profileResult.setUrgentContact(urgentContact);
        profileResult.setUrgentContactPhone(urgentContactPhone);
        return profileResult;
    }

    EmployeeDetailProfileResult generateProfileResult(LeaveEmployeeEntity employeeEntity, String workSn, int marriage, String urgentContact, String urgentContactPhone, String faceUrl) {
        EmployeeDetailProfileResult profileResult = new EmployeeDetailProfileResult();
        profileResult.setName(employeeEntity.getRealName());
        profileResult.setWorkSn(workSn);
        if (StringUtils.isNotBlank(employeeEntity.getRealName()) && !"-".equals(employeeEntity.getRealName())) {
            profileResult.setSex(EmployeeConstants.getSex(employeeEntity.getSex()));
        }
        if (!StringUtils.isAnyBlank(employeeEntity.getBirthday())) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                long birthDay = simpleDateFormat.parse(employeeEntity.getBirthday()).getTime();
                profileResult.setAge(Utils.calculateAge(birthDay));
                profileResult.setBirthday(birthDay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        profileResult.setFaceUrl(faceUrl);
        profileResult.setMarriage(EmployeeConstants.getMarriage(marriage));
        profileResult.setNation(employeeEntity.getNation());
        profileResult.setIdcardNo(employeeEntity.getIdCardNo());
        profileResult.setPhoneNo(employeeEntity.getTelephone());
        profileResult.setEmail(employeeEntity.getEmail());
        if (employeeEntity.getAddProvinceName() != null) {
            profileResult.setAddress(employeeEntity.getAddProvinceName());
        }
        if (employeeEntity.getAddCityName() != null) {
            profileResult.setAddress(profileResult.getAddress() + employeeEntity.getAddCityName());
        }
        if (employeeEntity.getAddCountyName() != null) {
            profileResult.setAddress(profileResult.getAddress() + employeeEntity.getAddCountyName());
        }

        if (employeeEntity.getAddress() != null) {
            profileResult.setAddress(profileResult.getAddress() + employeeEntity.getAddress());
        }


        if (StringUtils.isNotBlank(employeeEntity.getIdcardAddress())) {
            profileResult.setOriginDistrict(employeeEntity.getIdcardAddress());
        }
        profileResult.setBusAddress(employeeEntity.getBusAddress());
        BankCardEntity bankCardEntity = employeeEntity.getBankCardEntity();
        EmployeeDetailEntity employeeDetailEntity = employeeEntity.getDetail();
        if (bankCardEntity != null) {
            profileResult.setBankAccount(bankCardEntity.getBankAccount());
            profileResult.setBankNum(bankCardEntity.getBankNum());
        }
        if (employeeDetailEntity != null) {
            profileResult.setInterviewDate(employeeDetailEntity.getInterviewDate());
            profileResult.setSourceOfSupply(employeeDetailEntity.getSourceOfSupply());
            profileResult.setEmployeeNature(employeeDetailEntity.getEmployeeNature());
        }
        profileResult.setUrgentContact(urgentContact);
        profileResult.setUrgentContactPhone(urgentContactPhone);
        return profileResult;
    }


    List<EmployeeDetailCareerResult> generateCareerResult(List<CareerEntity> careerEntities) {
        List<EmployeeDetailCareerResult> careerResults = new ArrayList<>();
        for (CareerEntity careerEntity : careerEntities) {
            EmployeeDetailCareerResult careerResult = new EmployeeDetailCareerResult();
            careerResult.setStartTime(careerEntity.getStartTime());
            careerResult.setEndTime(careerEntity.getEndTime());
            careerResult.setWorkTime(Utils.calculateIntervalTime(careerEntity.getStartTime(), careerEntity.getEndTime()));
            careerResult.setCompanyName(careerEntity.getCompanyName());
            careerResult.setPositionName(careerEntity.getPosition());
            careerResults.add(careerResult);
        }
        return careerResults;
    }

    List<EmployeeDetailCareerResult> generateCareerResult1(List<CareerEmployeeEntity> careerEntities) {
        List<EmployeeDetailCareerResult> careerResults = new ArrayList<>();
        for (CareerEmployeeEntity careerEntity : careerEntities) {
            EmployeeDetailCareerResult careerResult = new EmployeeDetailCareerResult();
            careerResult.setStartTime(careerEntity.getStartTime());
            careerResult.setEndTime(careerEntity.getEndTime());
            careerResult.setWorkTime(Utils.calculateIntervalTime(careerEntity.getStartTime(), careerEntity.getEndTime()));
            careerResult.setCompanyName(careerEntity.getCompanyName());
            careerResult.setPositionName(careerEntity.getPosition());
            careerResults.add(careerResult);
        }
        return careerResults;
    }

    EmployeeDetailEducationResult generateEducationResult(EducationEntity educationEntity) {
        EmployeeDetailEducationResult educationResult = new EmployeeDetailEducationResult();
        educationResult.setStartTime(educationEntity.getStartTime());
        educationResult.setEndTime(educationEntity.getEndTime());
        educationResult.setSchoolName(educationEntity.getSchool());
        educationResult.setMajorName(educationEntity.getMajor());
        educationResult.setEducationName(EmployeeConstants.getEducationNameByCode(educationEntity.getEducation()));
        return educationResult;
    }

    EmployeeDetailEducationResult generateEducationResult1(EducationEmployeeEntity educationEntity) {
        EmployeeDetailEducationResult educationResult = new EmployeeDetailEducationResult();
        educationResult.setStartTime(educationEntity.getStartTime());
        educationResult.setEndTime(educationEntity.getEndTime());
        educationResult.setSchoolName(educationEntity.getSchool());
        educationResult.setMajorName(educationEntity.getMajor());
        educationResult.setEducationName(EmployeeConstants.getEducationNameByCode(educationEntity.getEducation()));
        return educationResult;
    }

    List<EmployeeDetailAttachmentResult> generateEmployeeAttachmentResult(String branUserId,
                                                                          String branCorpId,
                                                                          int employeeStatus,
                                                                          String idcardFrontFileName,
                                                                          String idcardBackFileName,
                                                                          String educationCertFileName,
                                                                          String leaveCertFileName,
                                                                          String idCardFaceName,
                                                                          String bankCardFileName) {
        List<EmployeeDetailAttachmentResult> results = new ArrayList<>();
        EmployeeDetailAttachmentResult result = new EmployeeDetailAttachmentResult();
        String idcardRoute = FileService.EMPLOYEE_PROSPECTIVE_IDACRD_IMG_ROUTE;
        String educationRoute = FileService.EMPLOYEE_PROSPECTIVE_EDUCATION_IMG_ROUTE;
        String leaveCertRoute = FileService.EMPLOYEE_PROSPECTIVE_DIMISSION_IMG_ROUTE;

        if (!StringUtils.isAnyBlank(idcardFrontFileName)) {
            result.setIdcardFront(StringUtil.isImageServerUrl(idcardFrontFileName)==true?idcardFrontFileName:fileService.getEmployeeAllKindImageURL(idcardRoute, idcardFrontFileName, branUserId));
        }
        if (!StringUtils.isAnyBlank(idcardBackFileName)) {
            result.setIdcardBack(StringUtil.isImageServerUrl(idcardBackFileName)==true?idcardBackFileName:fileService.getEmployeeAllKindImageURL(idcardRoute, idcardBackFileName, branUserId));
        }
        if (!StringUtils.isAnyBlank(educationCertFileName)) {
            result.setEducationCert(StringUtil.isImageServerUrl(educationCertFileName)==true?educationCertFileName:fileService.getEmployeeAllKindImageURL(educationRoute, educationCertFileName, branUserId));
        }
        if (!StringUtils.isAnyBlank(idCardFaceName)) {
            result.setIdcardFace(StringUtil.isImageServerUrl(idCardFaceName)==true?idCardFaceName:fileService.getEmployeeAllKindImageURL(idcardRoute, idCardFaceName, branUserId));
        }
        log.info("-----------------------------leaveCertRoute: " + leaveCertRoute);
        if (!StringUtils.isAnyBlank(leaveCertFileName)) {
            result.setLeaveCert(StringUtil.isImageServerUrl(leaveCertFileName)==true?leaveCertFileName:fileService.getEmployeeAllKindImageURL(leaveCertRoute, leaveCertFileName, branUserId));
        }
        if (!StringUtils.isAnyBlank(bankCardFileName)) {
            result.setBankCard(StringUtil.isImageServerUrl(bankCardFileName)==true?bankCardFileName:fileService.getEmpAllImageUrl(bankCardFileName, branUserId, "bank"));
        }
        results.add(result);
        return results;
    }

    List<EmployeeDetailAttachmentResult> generateEmployeeAttachmentResult(EmployeeEntity employeeEntity, String eduFileName) {
        List<EmployeeDetailAttachmentResult> results = new ArrayList<>();
        EmployeeDetailAttachmentResult result = new EmployeeDetailAttachmentResult();


        if (StringUtils.isNotBlank(employeeEntity.getIdcardFrontFileName())) {
            result.setIdcardFront(StringUtil.isImageServerUrl(employeeEntity.getIdcardFrontFileName())==true?employeeEntity.getIdcardFrontFileName():fileService.getEmpIdCardFontImg(employeeEntity.getIdcardFrontFileName(),
                    employeeEntity.getId(), employeeEntity.getBranCorpId()));
        }

        if (StringUtils.isNotBlank(employeeEntity.getIdcardBackFileName())) {
            result.setIdcardBack(StringUtil.isImageServerUrl(employeeEntity.getIdcardBackFileName())==true?employeeEntity.getIdcardBackFileName():fileService.getEmpIdCardBackImg(employeeEntity.getIdcardBackFileName(),
                    employeeEntity.getId(), employeeEntity.getBranCorpId()));

        }

        if (StringUtils.isNotBlank(employeeEntity.getIdcardFaceFileName())) {
            result.setIdcardFace(StringUtil.isImageServerUrl(employeeEntity.getIdcardFaceFileName())==true?employeeEntity.getIdcardFaceFileName():fileService.getEmpIdCardFaceImg(employeeEntity.getIdcardFaceFileName(), employeeEntity.getId(), employeeEntity.getBranCorpId()));

        }

        LeaveCertEmployeeEntity leaveCertEmployeeEntity = leaveCertEmpDao.findByEmpId(employeeEntity.getId(), employeeEntity.getBranUserId());
        if (leaveCertEmployeeEntity != null) {
            result.setLeaveCert(StringUtil.isImageServerUrl(employeeEntity.getLeaveCertFileName())==true?employeeEntity.getLeaveCertFileName():fileService.getEmpLeaveCertImg(leaveCertEmployeeEntity.getLeaveCertFileName(), employeeEntity.getId(), employeeEntity.getBranCorpId()));
        }

        if (StringUtils.isNotBlank(eduFileName)) {
            result.setEducationCert(StringUtil.isImageServerUrl(eduFileName)==true?eduFileName:fileService.getEmpEducationImg(eduFileName, employeeEntity.getId(), employeeEntity.getBranCorpId()));
        }
        BankCardEntity bankCardEntity = employeeEntity.getBankCardEntity();
        if (bankCardEntity != null) {
            if (StringUtils.isNotBlank(bankCardEntity.getBankCardUrl())) {
                result.setBankCard(StringUtil.isImageServerUrl(bankCardEntity.getBankCardUrl())==true?bankCardEntity.getBankCardUrl():fileService.getEmpIdCardBackImg(bankCardEntity.getBankCardUrl(),
                        employeeEntity.getId(), employeeEntity.getBranCorpId()));
            }
        }


        results.add(result);
        return results;
    }

    List<EmployeeDetailAttachmentResult> generateEmployeeAttachmentResult(LeaveEmployeeEntity employeeEntity, String eduFileName) {
        List<EmployeeDetailAttachmentResult> results = new ArrayList<>();
        EmployeeDetailAttachmentResult result = new EmployeeDetailAttachmentResult();

        if (StringUtils.isNotBlank(employeeEntity.getIdcardFrontFileName())) {
            result.setIdcardFront(fileService.getEmpIdCardFontImg(employeeEntity.getIdcardFrontFileName(),
                    employeeEntity.getId(), employeeEntity.getBranCorpId()));
        }

        if (StringUtils.isNotBlank(employeeEntity.getIdcardBackFileName())) {
            result.setIdcardBack(fileService.getEmpIdCardBackImg(employeeEntity.getIdcardBackFileName(), employeeEntity.getId(), employeeEntity.getBranCorpId()));

        }

        if (StringUtils.isNotBlank(employeeEntity.getIdcardFaceFileName())) {
            result.setIdcardFace(fileService.getEmpIdCardFaceImg(employeeEntity.getIdcardFaceFileName(), employeeEntity.getId(), employeeEntity.getBranCorpId()));

        }

        LeaveCertEmployeeEntity leaveCertEmployeeEntity = leaveCertEmpDao.findByLeaveId(employeeEntity.getId(), employeeEntity.getBranUserId());
        log.info("获取离职证明照:");
        log.info("employeeEntity.getId()" + employeeEntity.getId());
        log.info("employeeEntity.getBranCorpId()" + employeeEntity.getBranCorpId());

        if (leaveCertEmployeeEntity != null) {
            log.info("leaveCertEmployeeEntity不为空");
            log.info("leaveCertEmployeeEntity" + leaveCertEmployeeEntity.getId());
            result.setLeaveCert(fileService.getEmpLeaveCertImg(leaveCertEmployeeEntity.getLeaveCertFileName(), employeeEntity.getId(), employeeEntity.getBranCorpId()));
        }
        if (StringUtils.isNotBlank(eduFileName)) {
            result.setEducationCert(fileService.getEmpEducationImg(eduFileName, employeeEntity.getId(), employeeEntity.getBranCorpId()));
        }

        BankCardEntity bankCardEntity = employeeEntity.getBankCardEntity();
        if (bankCardEntity != null && StringUtils.isNotBlank(bankCardEntity.getBankCardUrl())) {
            result.setBankCard(fileService.getEmpIdCardBackImg(bankCardEntity.getBankCardUrl(), employeeEntity.getId(),
                    employeeEntity.getBranCorpId()));
        }


        results.add(result);
        return results;
    }


    private void setProperties(EmployeeExportResult employeeExportResult, BaseBranEntity baseBranEntity) {

        LeaveEmployeeEntity leaveEmployeeEntity = null;
        EmployeeEntity employeeEntity = null;
        AryaUserEntity aryaUserEntity = null;

        if (baseBranEntity instanceof LeaveEmployeeEntity) {
            leaveEmployeeEntity = (LeaveEmployeeEntity) baseBranEntity;

            employeeExportResult.setWorkShitName(leaveEmployeeEntity.getWorkShiftName());
            employeeExportResult.setWorkLineName(leaveEmployeeEntity.getWorkLineName());
            employeeExportResult.setWorkSn(leaveEmployeeEntity.getWorkSn());
            employeeExportResult.setPositionName(leaveEmployeeEntity.getPositionName());
            employeeExportResult.setDepartmentName(leaveEmployeeEntity.getDepartmentName());
            employeeExportResult.setCheckinTime(leaveEmployeeEntity.getCheckinTime());
            employeeExportResult.setStartTime(leaveEmployeeEntity.getStartTime());
            employeeExportResult.setEndTime(leaveEmployeeEntity.getEndTime());

            aryaUserEntity = aryaUserDao.findUsersByEmployeebranUserId(
                    leaveEmployeeEntity.getBranUserId());
        }

        if (baseBranEntity instanceof EmployeeEntity) {

            employeeEntity = (EmployeeEntity) baseBranEntity;
            employeeExportResult.setWorkShitName(employeeEntity.getWorkShiftName());
            employeeExportResult.setWorkLineName(employeeEntity.getWorkLineName());
            employeeExportResult.setWorkSn(employeeEntity.getWorkSn());
            employeeExportResult.setPositionName(employeeEntity.getPositionName());
            employeeExportResult.setDepartmentName(employeeEntity.getDepartmentName());
            employeeExportResult.setCheckinTime(employeeEntity.getCheckinTime());
            employeeExportResult.setStartTime(employeeEntity.getStartTime());
            employeeExportResult.setEndTime(employeeEntity.getEndTime());
            aryaUserEntity = aryaUserDao.findUsersByEmployeebranUserId(
                    employeeEntity.getBranUserId());
        }


        if (aryaUserEntity != null) {
            employeeExportResult.setName(aryaUserEntity.getRealName());
            employeeExportResult.setPhoneNo(aryaUserEntity.getPhoneNo());
            employeeExportResult.setIdCardNo(aryaUserEntity.getIdcardNo());
            BranUserEntity branUserEntity = branUserDao.findBranUsersByAryaUserId(aryaUserEntity.getId());
            if (branUserEntity != null) {
                employeeExportResult.setEmail(branUserEntity.getEmail());
                employeeExportResult.setSex(EmployeeConstants.getSex(branUserEntity.getSex()));
                employeeExportResult.setBirthday(branUserEntity.getBirthday());
                employeeExportResult.setNation(branUserEntity.getNation());
                employeeExportResult.setHomeTell(branUserEntity.getHomeTell());
                employeeExportResult.setIdcardAddress(branUserEntity.getIdcardAddress());
                employeeExportResult.setMarriage(EmployeeConstants.getMarriage(branUserEntity.getMarriage()));
                employeeExportResult.setUrgentContact(branUserEntity.getUrgentContact());
                employeeExportResult.setUrgentContactPhone(branUserEntity.getUrgentContactPhone());
                employeeExportResult.setAddress(setAddress(branUserEntity));
                if (StringUtils.isNotBlank(branUserEntity.getBirthday())) {
                    employeeExportResult.setAge(Utils.calculateAge(
                            SysUtils.getTimestampFormDateString(branUserEntity.getBirthday())));
                }

            }
        }
    }

    private File createFile(String dir, String name) {
        return new File(dir + name);
    }

    private File createFile(String name) {
        return new File(name);
    }

    private LeaveEmployeeEntity findLevelByBranUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("branUserId", userId);
        map.put("IS_DELETE", 0);
        return leaveEmployeeDao.findByUniqueParams(map);

    }

    private EmployeeEntity findEmpByBranUserId(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("branUserId", userId);
        map.put("IS_DELETE", 0);
        return employeeDao.findByUniqueParams(map);

    }

    public String setAddress(BranUserEntity branUserEntity) {
        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotBlank(branUserEntity.getAddProvinceName())) {
            result.append(branUserEntity.getAddProvinceName());
        }
        if (StringUtils.isNotBlank(branUserEntity.getAddCityName())) {
            result.append(branUserEntity.getAddCityName());
        }
        if (StringUtils.isNotBlank(branUserEntity.getAddCountyName())) {
            result.append(branUserEntity.getAddCountyName());
        }
        if (StringUtils.isNotBlank(branUserEntity.getAddress())) {
            result.append(branUserEntity.getAddress());
        }
        return result.toString();
    }
}
