package com.bumu.arya.salary.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.salary.command.*;
import com.bumu.arya.salary.common.SalaryEnum;
import com.bumu.arya.salary.result.*;
import com.bumu.arya.salary.service.CustomerAccountService;
import com.bumu.arya.salary.service.CustomerService;
import com.bumu.arya.salary.service.SalaryFileService;
import com.bumu.common.SessionInfo;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.result.BaseResult;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import com.bumu.common.service.FileUploadService;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.bumu.function.VoConverter.logger;

/**
 * 客户管理
 * @Author: Gujianchao
 * @Description:
 * @Date： 2017/6/30
 */
@Controller
@Api(tags = {"客户管理customer"})
@RequestMapping(value = "/salary")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private SalaryFileService salaryFileService;

    @ApiOperation(value = "查询所有客户")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<CustomerResult>> getPageList(@ApiParam("查询条件") @RequestParam(value = "condition", required = false) String condition) {
        return new HttpResponse<>(customerService.getList(condition));
    }

    @ApiOperation(value = "客户详情")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/view", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<CustomerResult> view(@ApiParam("客户ID") @RequestParam(value = "id") String id) {
        return new HttpResponse<>(customerService.view(id));
    }

    @ApiOperation(value = "更新客户")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer", method = RequestMethod.PUT)
    @ParamCheck
    @ResponseBody
    public HttpResponse<BaseResult.IDResult> update(@ApiParam @Valid @RequestBody CustomerUpdateCommand customerUpdateCommand,
                                           BindingResult bindingResult, SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(customerService.update(customerUpdateCommand));
    }

    @ApiOperation(value = "获取发票项目")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/billProjects", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<SalaryEnum.BillProject>> billProjects() {
        return new HttpResponse<>(Arrays.asList(SalaryEnum.BillProject.values()));
    }

    @ApiOperation(value = "上传合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/uploadContract", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public HttpResponse<ContractUploadResult> uploadContract(@ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
                                                             @ApiParam("合同") @RequestParam(value = "file") MultipartFile file) {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        logger.info("上传文件后缀为-->" + suffix);
        ContractUploadResult contractUploadResult = salaryFileService.uploadCustomerContract(file);
        customerService.uploadContract(customerId, contractUploadResult);
        return new HttpResponse<>(contractUploadResult);
    }

    @ApiOperation(value = "获取合同列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/contract/list", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<ContractUploadResult>> contractList(@ApiParam("客户ID") @RequestParam(value = "customerId") String customerId) {
        return new HttpResponse<>(customerService.contractList(customerId));
    }

    @ApiOperation(value = "上传合同（新的）")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/contract/upload", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<List<ContractUploadResult>> uploadContracts(@ApiParam("合同") @RequestParam(value = "file[]") MultipartFile[] file,
                                                                    HttpServletRequest request) throws Exception{
        String customerId = request.getHeader("customerId");
        if (StringUtils.isAnyBlank(customerId)) {
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "未传递客户ID");
        }
        logger.info("上传文件数量-->" + file.length);
        List<ContractUploadResult> contractUploadResults = salaryFileService.uploadCustomerContracts(file, customerId);
        customerService.uploadContracts (customerId, contractUploadResults);
        //生成压缩文件
        salaryFileService.customerContractsZip(customerId);
        return new HttpResponse<>(contractUploadResults);
    }

    @ApiOperation(value = "删除合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/contract/delete", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<List<ContractUploadResult>> deleteContract(@ApiParam @Valid @RequestBody CustomerAccountDeleteCommand customerAccountDeleteCommand) throws Exception {
        customerService.deleteContract(customerAccountDeleteCommand.getCustomerId(), customerAccountDeleteCommand.getContractId());
        //生成压缩文件
        salaryFileService.customerContractsZip(customerAccountDeleteCommand.getCustomerId());
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "获取下载合同的地址")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/contract/download/url", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> downloadContract(@ApiParam("客户ID") @RequestParam(value = "customerId") String customerId) {
        //salaryFileService.downloadCustomerContract(fileId, suffix, customerId, response);
        FileUploadFileResult result = salaryFileService.downloadContractUrl(customerId);
        return new HttpResponse(result);
    }


    @RequestMapping(value = "/customer/contract/download", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Void> downloadContract(@RequestParam(value = "customerId") String customerId,
                                               HttpServletResponse response) {
        salaryFileService.downloadCustomerContract(customerId, response);
        return new HttpResponse();
    }

    @ApiOperation(value = "查看合同")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/contract/read", method = RequestMethod.GET)
    @ResponseBody
    public void readContract(@ApiParam("文件ID") @RequestParam(value = "fileId") String fileId,
                             @ApiParam("文件拓展") @RequestParam(value = "suffix") String suffix,
                             @ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
                             HttpServletResponse response) {
        salaryFileService.readCustomerContract(fileId, suffix, customerId, response);
    }

    @ApiOperation(value = "获取客户跟进记录List")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/customerFollows", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<List<CustomerFollowResult>> getCustomerFollows(
            @ApiParam(value = "客户ID") @RequestParam(value = "id") String id) {
        return new HttpResponse<>(customerService.followList(id));
    }

    @ApiOperation(value = "增加跟进记录")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/addFollowRecord", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> addFollowRecord(
            @ApiParam @Valid @RequestBody CustomerFollowCommand customerFollowCommand,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception {
        customerService.addFollow(customerFollowCommand);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "保存薪资计算规则")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/rule/save", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<BaseResult.IDResult> saveSalaryRule(
            @ApiParam @Valid @RequestBody CustomerSalaryRuleCommand customerSalaryRuleCommand,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception {
        return new HttpResponse<>(customerService.addRule(customerSalaryRuleCommand));
    }

    @ApiOperation(value = "获取薪资计算规则")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/rule/get", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<CustomerSalaryRuleResult> getSalaryRule(
            @ApiParam(value = "客户ID") @RequestParam(value = "customerId") String customerId){
        return new HttpResponse<>(customerService.getRule(customerId));
    }

    @ApiOperation(value = "账号充值")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/account/recharge", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> recharge(
            @ApiParam @Valid @RequestBody CustomerRechargeCommand customerRechargeCommand,
            BindingResult bindingResult,
            SessionInfo sessionInfo) throws Exception {
        customerService.recharge(customerRechargeCommand);
        return new HttpResponse<>();
    }

   /* @ApiOperation(value = "台账列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/accountPage", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Pager<CustomerAccountResult>> accountPage(
            @ApiParam("客户ID") @RequestParam(value = "customerId") String customerId,
            @ApiParam("查询条件-时间（年-月）") @RequestParam(value = "yearMonth") String yearMonth,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) throws Exception{
        return new HttpResponse<>(customerAccountService.pageAccount(customerId, yearMonth, page, pageSize));
    }

    @ApiOperation(value = "导出台账")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/account/export", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> accountExport(
            @ApiParam @Valid @RequestBody CustomerAccountExportCommand customerAccountExportCommand) throws Exception{
        return new HttpResponse(salaryFileService.exportAccount(customerAccountExportCommand.getCustomerId(), customerAccountExportCommand.getYearMonth()));
    }


    @ApiOperation(value = "更新台账信息")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/updateAccount", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> updateAccount(
            @ApiParam @Valid @RequestBody CustomerAccountUpdateCommand customerAccountUpdateCommand,
            BindingResult bindingResult,
            SessionInfo sessionInfo) {
        customerAccountService.updateAccount(customerAccountUpdateCommand);
        return new HttpResponse<>();
    }*/

    @ApiOperation(value = "客户列表（分页）")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/page", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Pager<CustomerResult>> customerPage(
            @ApiParam("查询条件-公司名称") @RequestParam(value = "condition", required = false) String condition,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        return new HttpResponse<>(customerService.customerPager(condition, page, pageSize));
    }

    @ApiOperation(value = "更新客户资料")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/update/detail", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> updateCustomerDetail(
            @ApiParam @Valid @RequestBody CustomerDetailUpdateCommand customerDetailUpdateCommand){
        customerService.updateDetail(customerDetailUpdateCommand);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(value = "导出")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/customer/export", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<FileUploadFileResult> export() throws Exception{
        return new HttpResponse(salaryFileService.exportCustomer());
    }

}
