package com.bumu.arya.salary.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.model.SalaryCalculatePoolModel;
import com.bumu.arya.salary.result.CustomerResult;
import com.bumu.arya.salary.result.ErrLogResult;
import com.bumu.arya.salary.result.SalaryCalculateListResult;
import com.bumu.arya.salary.result.SalaryUserResult;
import com.bumu.arya.salary.service.CustomerService;
import com.bumu.arya.salary.service.SalaryCalculateService;
import com.bumu.arya.salary.service.SalaryFileService;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import com.bumu.common.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.List;

import static com.bumu.function.VoConverter.logger;

/**
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@Controller
@Api(tags = {"薪资计算culculate"})
@RequestMapping(value = "/salary/calculate")
public class SalaryCalculateController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private SalaryCalculateService salaryCalculateService;

    @Autowired
    private SalaryFileService salaryFileService;

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "查询所有客户")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/customer", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<CustomerResult>> getPageList(@ApiParam("查询条件") @RequestParam(value = "condition", required = false) String condition) {
        return new HttpResponse<>(customerService.getList(condition));
    }

    @ApiOperation(value = "预览计算")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/preview", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<SalaryCalculateListResult> preview(
            @ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam("结算周期(1：月  2：周期)") @RequestParam(value = "settlementInterval") Integer settlementInterval,
            @ApiParam("年份") @RequestParam(value = "year") Integer year,
            @ApiParam("月份") @RequestParam(value = "month") Integer month,
            @ApiParam("批次") @RequestParam(value = "week") Long week,
            @ApiParam("薪资文件") @RequestParam("file") MultipartFile file,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize, HttpSession session) throws Exception {
        if (StringUtils.isAnyBlank(customerId)) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CUSTOMER_IS_NULL);
        }
        // 检查导入批次/月
        CustomerSalaryCulateCommand customerSalaryCulateCommand = new CustomerSalaryCulateCommand();
        customerSalaryCulateCommand.init(customerId, settlementInterval, year, month, week);
        salaryCalculateService.checkImportWeek(customerSalaryCulateCommand);
        //文件保存本地
        String fileName = System.currentTimeMillis() + "_" + file.getName();
        salaryFileService.saveSalaryCalculateExcelFile(fileName, file);
        logger.info("导入【薪资计算】上传文件成功" + file.getName() + "。");
        File salaryFile = salaryFileService.readSalaryCalculateExcelFile(fileName);
        //导入
        SalaryCalculatePoolModel salaryCalculatePoolModel = salaryCalculateService.importBumuExcel(salaryFile, customerSalaryCulateCommand);
        //计算
        SalaryCalculateListResult salaryCalculateListResult = salaryCalculateService.calculate(customerSalaryCulateCommand, salaryCalculatePoolModel, pageSize, page);
        //统计
        salaryCalculateService.countSalaryCalculat(salaryCalculateListResult, salaryCalculatePoolModel);
        return new HttpResponse<>(salaryCalculateListResult);
    }

    @ApiOperation(value = "导入")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/importSalary", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> importSalary(
            @ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam("结算周期(1：月  2：周期)") @RequestParam(value = "settlementInterval" ) Integer settlementInterval,
            @ApiParam("年份") @RequestParam(value = "year") Integer year,
            @ApiParam("月份") @RequestParam(value = "month") Integer month,
            @ApiParam("批次") @RequestParam(value = "week") Long week,
            @ApiParam("薪资文件") @RequestParam("file") MultipartFile file) throws Exception {
        if (StringUtils.isAnyBlank(customerId)) {
            throw new AryaServiceException(ErrorCode.CODE_SALARY_CUSTOMER_IS_NULL);
        }
        CustomerSalaryCulateCommand customerSalaryCulateCommand = new CustomerSalaryCulateCommand();
        customerSalaryCulateCommand.init(customerId, settlementInterval, year, month, week);
        // 检查导入批次/月
        salaryCalculateService.checkImportWeek(customerSalaryCulateCommand);
        // 文件保存本地
        String fileName = System.currentTimeMillis() + "_" + file.getName();
        salaryFileService.saveSalaryCalculateExcelFile(fileName, file);
        logger.info("导入【薪资计算】上传文件成功" + file.getName() + "。");
        File salaryFile = salaryFileService.readSalaryCalculateExcelFile(fileName);
        //导入
        SalaryCalculatePoolModel salaryCalculatePoolModel = salaryCalculateService.importBumuExcel(salaryFile, customerSalaryCulateCommand);
        //计算
        SalaryCalculateListResult salaryCalculateListResult = salaryCalculateService.calculate(customerSalaryCulateCommand, salaryCalculatePoolModel, null, null);
        //2DB
        salaryCalculateService.saveData(salaryCalculateListResult, salaryCalculatePoolModel, customerSalaryCulateCommand, salaryFile);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "查询")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/query", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<SalaryCalculateListResult> querySalary(
            @ApiParam("查询条件") @RequestParam(value = "condition") String condition,
            @ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam("结算周期(1：月  2：周期)") @RequestParam(value = "settlementInterval") Integer settlementInterval,
            @ApiParam("年份") @RequestParam(value = "year") Integer year,
            @ApiParam("月份") @RequestParam(value = "month") Integer month,
            @ApiParam("批次") @RequestParam(value = "week") Long week,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) throws Exception {
        return new HttpResponse<>(salaryCalculateService.query(condition, customerId, settlementInterval, year, month, week , pageSize, page));
    }

    /**
     * 下载薪资导入模板
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "下载模板")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/file/download/template", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Void> downloadSalaryTemplate(@ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
                                                       HttpServletResponse response)throws Exception{
        salaryFileService.downTemplate(customerId, response);
        return new HttpResponse();
    }

    /**
     * 下载薪资导入模板
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取下载模板URL")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/file/download/template/url", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> downloadSalaryTemplateUrl(@ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
                                                     HttpServletResponse response)throws Exception{
        return new HttpResponse(salaryFileService.templateFile(customerId));
    }


    /**
     * 获取个人资料
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取个人资料")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/user/info", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<SalaryUserResult> salaryUserInfo(@ApiParam("ID") @RequestParam(value = "id") String id) {
        return new HttpResponse<>(salaryCalculateService.userInfo(id));
    }

    /**
     * 更新薪资所属的个人资料
     *
     * @param command
     * @return
     */
    @ApiOperation(value = "更新个人资料")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/user/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> updateSalaryUserInfo(@ApiParam @Valid @RequestBody UpdateSalaryUserInfoCommand command) {
        salaryCalculateService.updateUser(command);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    /**
     * @param salaryDeleteCommand
     * @return
     */
    @ApiOperation(value = "删除薪资记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> deleteSalary(
            @ApiParam @Valid @RequestBody SalaryDeleteCommand salaryDeleteCommand) {
        salaryCalculateService.deleteSalarys(salaryDeleteCommand);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    /**
     * @param salaryServiceCommand
     * @return
     */
    @ApiOperation(value = "确认扣款")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/deduct", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> deduct(
            @ApiParam @Valid @RequestBody SalaryServiceCommand salaryServiceCommand){
        salaryCalculateService.deduct(salaryServiceCommand);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    /**
     * 导出薪资Excel
     *
     */
    @ApiOperation(value = "导出（包含三种导出）")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/file/export", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> exportSalary(
            @ApiParam @Valid @RequestBody SalaryExportCommand salaryExportCommand) throws Exception {
        return new HttpResponse<>(salaryFileService.exportSalaryFile(salaryExportCommand));
    }

    @ApiOperation(value = "下载导出文件")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/file/retrieve", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Void> retrieve(@ApiParam("文件ID") @RequestParam(value = "fileId") String fileId,
                                       @ApiParam("拓展类型") @RequestParam(value = "type") String type,
                                       @ApiParam("导出分类") @RequestParam(value = "exportType") SalaryEnum.exportType exportType,
                                       @ApiParam("客户ID") @RequestParam(value = "customerId", required = false)  String customerId,
                                       HttpServletResponse response) {
        salaryFileService.readSalaryFile(fileId, type, exportType, customerId, response);
        return new HttpResponse<>();
    }

    /**
     * 错误信息
     * @param condition 查询条件
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "错误信息")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/errLog/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Pager<ErrLogResult>> errLogList(@ApiParam("查询信息（客户名称/城市）") @RequestParam(value = "condition", required = false) String condition,
                                                        @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) throws Exception {
        return new HttpResponse(salaryCalculateService.errLogPage(condition, pageSize, page));
    }

    /**
     * @param errLogDeleteCommand
     * @return
     */
    @ApiOperation(value = "删除错误信息")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/errLog/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> errLogDelete(@ApiParam @Valid @RequestBody ErrLogDeleteCommand errLogDeleteCommand){
        salaryCalculateService.errLogDelete(errLogDeleteCommand);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "保存错误信息")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/errLog/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> errLogUpdate(@ApiParam @Valid @RequestBody ErrLogUpdateCommand errLogUpdateCommand){
        salaryCalculateService.errLogUpdate(errLogUpdateCommand);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }


    @ApiOperation(value = "错误记录导出")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/errLog/export", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> errLogExport(@ApiParam @Valid @RequestBody ErrLogExportCommand errLogExportCommand) {
        return new HttpResponse<>(salaryFileService.exportErrLog(errLogExportCommand));
    }

    @ApiOperation(value = "获取批次")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/base/weeks", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<Long>> weekList(
            @ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam("年份") @RequestParam(value = "year") Integer year,
            @ApiParam("月份") @RequestParam(value = "month") Integer month){
        return new HttpResponse<>(salaryCalculateService.getWeeks(customerId, year, month));
    }

    @RequestMapping(value = "/refresh/detail/data")
    @ResponseBody
    public HttpResponse<Void> refreshDetailData(HttpServletRequest request){
        salaryCalculateService.refreshDetailData(request);
        return new HttpResponse<>();
    }

}
