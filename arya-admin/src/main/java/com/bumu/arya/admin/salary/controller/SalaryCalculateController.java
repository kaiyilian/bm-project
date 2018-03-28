package com.bumu.arya.admin.salary.controller;

import com.bumu.arya.admin.corporation.result.OrganizationTreeResult;
import com.bumu.arya.admin.salary.controller.command.*;
import com.bumu.arya.admin.salary.result.SalaryCalculateImportResult;
import com.bumu.arya.admin.salary.result.SalaryCalculateListResult;
import com.bumu.arya.admin.corporation.service.CorporationService;
import com.bumu.arya.admin.salary.service.SalaryFileService;
import com.bumu.arya.admin.system.service.OpLogService;
import com.bumu.arya.admin.salary.service.SalaryCalculateService;
import com.bumu.arya.common.OperateConstants;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.model.AryaDepartmentDao;
import com.bumu.arya.model.CorporationDao;
import com.bumu.arya.model.entity.AryaDepartmentEntity;
import com.bumu.arya.model.entity.CorporationEntity;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 薪资计算
 *
 * @author CuiMengxin
 * @date 2016/3/23
 */
@Deprecated
@Api(value = "SalaryCalculateController", tags = {"薪资管理SalaryCalculateManager"})
@Controller
public class SalaryCalculateController {
    Logger log = LoggerFactory.getLogger(SalaryCalculateController.class);

    @Autowired
    SalaryFileService fileService;

    @Autowired
    SalaryCalculateService salaryCalculateService;

    @Autowired
    CorporationDao corporationDao;

    @Autowired
    OpLogService opLogService;

    @Autowired
    CorporationService corporationService;

    @Autowired
    AryaDepartmentDao aryaDepartmentDao;

    /**
     * 订单计算页面
     *
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/index", method = RequestMethod.GET)
    public String getSalaryCalculatePage() {
        return "salary/calculate";
    }

    /**
     * 上传文件并计算薪资
     *
     * @param groupId
     * @param departmentId
     * @param settlementInterval
     * @param yearMonth
     * @param week
     * @param file
     * @param session
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/upload2Calculate", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<SalaryCalculateListResult> uploadAndCalculateSalary(
            @RequestParam(value = "group_id", required = false) String groupId,
            @RequestParam(value = "department_id", required = false) String departmentId,
            @RequestParam("settlement_interval") Integer settlementInterval,
            @RequestParam("year_month") String yearMonth,
            @RequestParam(value = "week", required = false) Integer week,
            @RequestParam("salary_file") MultipartFile file,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "page_size") Integer pageSize,
            HttpSession session) {
        try {
            String organizationName = "";
            if (StringUtils.isNotBlank(departmentId)) {
                AryaDepartmentEntity aryaDepartmentEntity = aryaDepartmentDao.findDepartmentById(departmentId);
                organizationName = aryaDepartmentEntity.getName();
            } else if (StringUtils.isNotBlank(groupId)) {
                CorporationEntity corporation = corporationDao.find(groupId);
                organizationName = corporation.getName();
            } else {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_GROUP_OR_DEPARTMENT_NOT_FIND);
            }
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String fileName = organizationName + dateFormater.format(date);
            //转存文件
            fileService.saveSalaryCalculateExcelFile(fileName, file);
            opLogService.successLog(OperateConstants.OP_TYPE_SALARY_UPLOAD_SUCCESS, new StringBuffer("【薪资计算】上传文件" + fileName + "。"), log);
            log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",上传文件成功:" + fileName + "。");
            String[] yearMonthStr = yearMonth.split("-");
            if (yearMonthStr.length != 2) {
                return new HttpResponse<>();
            }
            int year = Integer.parseInt(yearMonthStr[0]);
            int month = Integer.parseInt(yearMonthStr[1]);
            if (settlementInterval == 1) {
                //计算周期为周
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始计算周薪：" + yearMonth + "-" + week + "。");
                return new HttpResponse<>(salaryCalculateService.calculateSalary(groupId, departmentId, year, month, week, fileName, page, pageSize));
            } else {
                //计算周期为月
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始计算月薪：" + yearMonth + "。");
                return new HttpResponse<>(salaryCalculateService.calculateSalary(groupId, departmentId, year, month, null, fileName, page, pageSize));
            }
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 获取薪资计算规则类型
     *
     * @param groupId
     * @param departmentId
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/getRuleType",method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getCalculateRuleType(@RequestParam(value = "group_id", required = false) String groupId,
                                      @RequestParam(value = "department_id", required = false) String departmentId){
        try {
            return new HttpResponse(salaryCalculateService.getSalaryCalculateRuleType(groupId,departmentId));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 导入薪资
     *
     * @param command
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/import", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<SalaryCalculateImportResult> importSalary(@RequestBody SalaryCalculateImportCommand command,
                                                           HttpSession session) {
        try {
            String[] yearMonthStr = command.getYearMonth().split("-");
            if (yearMonthStr.length != 2) {
                return new HttpResponse<>();
            }
            int year = Integer.parseInt(yearMonthStr[0]);
            int month = Integer.parseInt(yearMonthStr[1]);
            if (command.getSettlementInterval() == 1) {
                //导入周期为周
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始导入周薪：" + command.getYearMonth() + "-" + command.getWeek() + "。");
                return new HttpResponse<>(salaryCalculateService.importCalculateSalary(session.getAttribute("user_id").toString(), command.getGroupId(), command.getDepartmentId(),year, month, command.getWeek(), command.getFileName()));
            } else {
                //导入周期为月
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始导入月薪：" + command.getYearMonth() + "。");
                return new HttpResponse<>(salaryCalculateService.importCalculateSalary(session.getAttribute("user_id").toString(), command.getGroupId(), command.getDepartmentId(),year, month, null, command.getFileName()));
            }
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 查询
     *
     * @param groupId
     * @param settlementInterval
     * @param yearMonth
     * @param week
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/query", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<SalaryCalculateListResult> querySalary(@RequestParam(value = "group_id", required = false) String groupId,
                                                        @RequestParam(value = "department_id", required = false) String departmentId,
                                                        @RequestParam("settlement_interval") Integer settlementInterval,
                                                        @RequestParam("year_month") String yearMonth,
                                                        @RequestParam(value = "week", required = false) Integer week,
                                                        @RequestParam(value = "key_word",required = false) String keyWord,
                                                        @RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "page_size") Integer pageSize,
                                                        HttpSession session) {
        try {
//			departmentId = "06e7a6bc648b4ef7ad1cc4e20be03855";
            String[] yearMonthStr = yearMonth.split("-");
            if (yearMonthStr.length != 2) {
                return new HttpResponse<>();
            }
            int year = Integer.parseInt(yearMonthStr[0]);
            int month = Integer.parseInt(yearMonthStr[1]);
            if (settlementInterval == 1) {
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始查询周薪：" + yearMonth + "-" + week + "。");
                return new HttpResponse<>(salaryCalculateService.queryCalculateSalary(groupId, departmentId, year, month, week, keyWord, page, pageSize));
            } else {
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始查月薪：" + yearMonth + "。");
                return new HttpResponse<>(salaryCalculateService.queryCalculateSalary(groupId, departmentId, year, month, null, keyWord, page, pageSize));
            }
        } catch (AryaServiceException e) {
            return new HttpResponse<>(e.getErrorCode());
        }
    }

    /**
     * 导出薪资Excel
     *
     * @param groupId
     * @param settlementInterval
     * @param yearMonth
     * @param week
     * @param response
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/export", method = RequestMethod.GET)
    public
    @ResponseBody
    void exportSalary(@RequestParam(value = "group_id", required = false) String groupId,
                      @RequestParam(value = "department_id", required = false) String departmentId,
                      @RequestParam("settlement_interval") Integer settlementInterval,
                      @RequestParam("year_month") String yearMonth,
                      @RequestParam(value = "week", required = false) Integer week,
                      HttpServletResponse response,
                      HttpSession session) {
        try {
            String[] yearMonthStr = yearMonth.split("-");
            if (yearMonthStr.length != 2) {
                return;
            }
            int year = Integer.parseInt(yearMonthStr[0]);
            int month = Integer.parseInt(yearMonthStr[1]);
            if (settlementInterval == 1) {
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始导出周薪：" + yearMonth + "-" + week + "。");
                salaryCalculateService.exportSalary(session.getAttribute("user_id").toString(), groupId, departmentId, year, month, week, response);
            } else {
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始导出月薪：" + yearMonth + "。");
                salaryCalculateService.exportSalary(session.getAttribute("user_id").toString(), groupId, departmentId, year, month, null, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 导出统计结果Excel
     *
     * @param groupId
     * @param settlementInterval
     * @param yearMonth
     * @param week
     * @param response
     * @param session
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/statistics/export", method = RequestMethod.GET)
    public
    @ResponseBody
    void exportStatistics(@RequestParam(value = "group_id", required = false) String groupId,
                          @RequestParam(value = "department_id", required = false) String departmentId,
                          @RequestParam("settlement_interval") Integer settlementInterval,
                          @RequestParam("year_month") String yearMonth,
                          @RequestParam(value = "week", required = false) Integer week,
                          HttpServletResponse response,
                          HttpSession session) {
        try {
            String[] yearMonthStr = yearMonth.split("-");
            if (yearMonthStr.length != 2) {
                return;
            }
            int year = Integer.parseInt(yearMonthStr[0]);
            int month = Integer.parseInt(yearMonthStr[1]);
            if (settlementInterval == 1) {
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始导出周统计：" + yearMonth + "-" + week + "。");
                salaryCalculateService.exportStatistics(session.getAttribute("user_id").toString(), groupId, departmentId, year, month, week, response);
            } else {
                log.info("【薪资计算】用户：" + session.getAttribute("user_id").toString() + ",开始导出月统计：" + yearMonth + "。");
                salaryCalculateService.exportStatistics(session.getAttribute("user_id").toString(), groupId, departmentId, year, month, null, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取计算规则
     *
     * @param groupId
     * @param departmentId
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/rule", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getCalculateRule(@RequestParam(value = "group_id", required = false) String groupId,
                                  @RequestParam(value = "department_id", required = false) String departmentId,
                                  @RequestParam(value = "rule_type",required = false) Integer ruleType) {
        try {
            return new HttpResponse(salaryCalculateService.getSalaryCalculateRule(groupId, departmentId,ruleType));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 新增计算规则
     *
     * @param command
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/rule/add", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse addCalculateRule(@RequestBody CreateSalaryCalculateRuleCommand command) {
        try {
            if (StringUtils.isNotBlank(command.getGroupId())) {
                return new HttpResponse(salaryCalculateService.addGroupSalaryCalculateRule(command));
            } else if (StringUtils.isNotBlank(command.getDepartmentId())) {
                return new HttpResponse(salaryCalculateService.addDepartmentSalaryCalculateRule(command));
            } else {
                throw new AryaServiceException(ErrorCode.CODE_SALARY_CALCULATE_CORP_AND_DEPARTMENT_EMPTY);
            }
        } catch (AryaServiceException e) {
            log.error(e.getMessage(), e);
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 更新计算规则
     *
     * @param command
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/rule/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse updateCalculateRule(@RequestBody UpdateSalaryCalculateRuleCommand command) {
        try {
            return new HttpResponse(salaryCalculateService.updateSalaryCalculateRule(command));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 删除计算规则
     *
     * @param command
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/rule/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse deleteCalculateRule(@RequestBody UpdateSalaryCalculateRuleCommand command) {
        try {
            salaryCalculateService.deleteSalaryCalculateRule(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 删除薪资记录
     *
     * @param command
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse deleteCalculatedSalary(@RequestBody DeleteCalculatedSalaryCommand command) {
        try {
            Integer ruleType = 1;
            salaryCalculateService.deleteCalculatedSalary(command.getDeleteIds(),ruleType);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 获取薪资计算的组织树
     *
     * @return
     */
    @Deprecated
    @ApiOperation(httpMethod = "GET",notes = "获取薪资计算的组织树",value = "薪资计算的组织树")
    @RequestMapping(value = "/admin/salary/calculate/organization/tree", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<OrganizationTreeResult> getSalaryCalculateOrganizationTree() {
        try {
            return new HttpResponse(corporationService.generateSalaryOrganizationTree());
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 更新薪资所属的个人资料
     *
     * @param command
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/user/info/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse updateSalaryUserInfo(@RequestBody UpdateSalaryUserInfoCommand command) {
        try {
            salaryCalculateService.updateSalaryUserInfo(command);
            return new HttpResponse();
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 查询集团或部门某月的批次列表
     *
     * @param groupDepartmentId
     * @param year
     * @param month
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/admin/salary/calculate/batch/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse getGroupOrDepartmentYearMonthBatchList(@RequestParam("group_department_id") String groupDepartmentId,
                                                        @RequestParam Integer year,
                                                        @RequestParam Integer month) {
        try {
            return new HttpResponse(salaryCalculateService.getSalaryBatchNoList(groupDepartmentId, year, month));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }
}
