package com.bumu.bran.admin.esalary.controller;

import com.bumu.arya.command.MybatisPagerCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.esalary.command.ESalaryHistoryCommand;
import com.bumu.bran.esalary.command.ESalaryIdCommand;
import com.bumu.bran.esalary.command.ESalaryUserCommand;
import com.bumu.bran.esalary.command.ESalaryValidityCommand;
import com.bumu.bran.esalary.result.BranMongoSalaryInfoResult;
import com.bumu.bran.esalary.result.ESalaryEmpUserInfoResult;
import com.bumu.bran.esalary.result.ESalaryInfoIdResult;
import com.bumu.bran.esalary.result.ESalaryValidityResult;
import com.bumu.bran.esalary.service.ESalaryService;
import com.bumu.common.SessionInfo;
import com.bumu.common.result.DownloadResult;
import com.bumu.common.result.Pager;
import com.bumu.payroll.result.PayrollHistoryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by stone on 17-7-18.
 *
 * @update majun
 */
@Controller
@Api(tags = {"电子工资单Payroll"})
@RequestMapping(value = "/admin/salary")
public class ESalaryController {

    Logger log = LoggerFactory.getLogger(ESalaryController.class);


    @Autowired
    ESalaryService eSalaryService;

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
//    @ResponseBody
//    @ApiOperation(value = "登录")
//    public HttpResponse<BranCorpESalaryUserResult> login(
//            HttpServletRequest request,
//            @ApiParam("登录的信息 ,字需要上传用户的 手机号(phone_num) 和 验证码(verifiCode) 即可")
//            @RequestBody SigninCommand signinCommand) {
//        String ip = getIP(request);
//        return new HttpResponse<>(eSalaryService.login(signinCommand, ip));
//    }

//    @RequestMapping(value = "/sendcode", method = RequestMethod.POST)
//    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
//    @ResponseBody
//    @ApiOperation(notes = "发送验证码", value = "发送验证码")
//    public HttpResponse<Boolean> sendCode(
//            HttpServletRequest request,
//            @ApiParam("获取手机号码，只需要上传手机号即可(phone_num)") @RequestBody SmsCaptchaCommand phone) {
//
//        String ip = getIP(request);
//
//
//        return new HttpResponse<>(eSalaryService.sendCode(phone, ip));
//    }

//    @RequestMapping(value = "/getuser/info", method = RequestMethod.POST)
//    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
//    @ResponseBody
//    @ApiOperation(notes = "获取hr登录用户信息", value = "获取hr登录用户信息")
//    public HttpResponse<BranCorpESalaryUserResult> getUsrInfo(
//            @ApiParam("上传相关的id，这个接口上传登录用户的id(salaryCorpUserId)")
//            @RequestBody ESalaryIdCommand eSalaryIdCommand) {
//
//
//        return new HttpResponse<>(eSalaryService.isLogin(eSalaryIdCommand));
//    }


    private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

//    @RequestMapping(value = "/islogin", method = RequestMethod.POST)
//    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
//    @ResponseBody
//    @ApiOperation(notes = "判断用户是否存在", value = "判断用户是否存在")
//    public HttpResponse<BranCorpESalaryUserResult> isLogin(
//            @ApiParam("上传相关的id，这个接口上传登录用户的id(salaryCorpUserId)")
//            @RequestBody ESalaryIdCommand eSalaryIdCommand) {
//
//
//        return new HttpResponse<>(eSalaryService.isLogin(eSalaryIdCommand));
//    }


    @ApiOperation(httpMethod = "POST", notes = "上传excel文件文件", consumes = "multipart/form-data", value = "临时文件都统一储存在temp目录下")
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @GetSessionInfo
    public HttpResponse<ESalaryInfoIdResult> uploadExcel(
            @ApiParam("上传的图片文件")
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "suffix", required = false, defaultValue = "xls") String suffix,
            @ApiParam("该薪资的年月（2015-09）") @RequestParam(value = "time", required = true) String time,
            @ApiParam("工资单名称") @RequestParam(value = "salaryName", required = false, defaultValue = "工资单") String salaryName,
            @ApiParam("子公司名称") @RequestParam(value = "childCorpName", required = false) String childCorpName, SessionInfo sessionInfo) throws Exception {

        HttpResponse<ESalaryInfoIdResult> httpResponse = new HttpResponse<>(eSalaryService.uploadExcel(file, suffix, time, salaryName, childCorpName, sessionInfo));
        log.info("httpResponse: " + httpResponse.toJson());
        return httpResponse;
    }


    @RequestMapping(value = "/getuser", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "获取这次导入的员工", value = "获取这次导入的员工")
    @GetSessionInfo
    public HttpResponse<ESalaryEmpUserInfoResult> getEmpUsers(
            @ApiParam("上传相关的id，这个接口上传导入薪资的id(salaryInfoId)")
            @RequestBody ESalaryIdCommand eSalaryIdCommand, SessionInfo sessionInfo) {

        return new HttpResponse<>(eSalaryService.getEmpUser(eSalaryIdCommand));
    }

    @RequestMapping(value = "/getsalary", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "获取一个员工的薪资内容", value = "获取一个员工的薪资内容")
    @GetSessionInfo
    public HttpResponse<BranMongoSalaryInfoResult> getSalaryInfo(
            @ApiParam("上传相关的id，这个接口上传导入薪资的id(salaryInfoId) 员工id(salaryUserId)") @RequestBody ESalaryIdCommand eSalaryIdCommand, SessionInfo sessionInfo) {
        return new HttpResponse<>(eSalaryService.getSalaryInfo(eSalaryIdCommand));
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "发送本次导入薪资信息到用户", value = "发送本次导入薪资信息到用户")
    @GetSessionInfo
    public HttpResponse<ESalaryInfoIdResult> sendSalaryInfo(
            @ApiParam("上传相关的id，这个接口上传导入薪资的id(salaryInfoId) " +
                    "hr名称（salaryCorpUserName） " +
                    "公司名称（salaryCorpName）  " +
                    "登录用户的id （salaryCorpUserId）")
            @RequestBody ESalaryIdCommand eSalaryIdCommand, SessionInfo sessionInfo) throws Exception{
        return new HttpResponse<>(eSalaryService.sendSalaryInfo(eSalaryIdCommand, sessionInfo));
    }

//    @RequestMapping(value = "/send/imitation", method = RequestMethod.POST)
//    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
//    @ResponseBody
//    @ApiOperation(notes = "模拟发送工资条", value = "模拟发送工资条")
//    public HttpResponse<ESalaryInfoIdResult> sendSalaryInfoImitation(
//            @ApiParam("上传相关的id，"+
//                    "登录用户的id （salaryCorpUserId）")
//            @RequestBody ESalaryIdCommand eSalaryIdCommand) {
//        return new HttpResponse<>(eSalaryService.sendSalaryInfoImitation(eSalaryIdCommand));
//    }


    //获取发送历史
//    @RequestMapping(value = "/history", method = RequestMethod.POST)
//    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
//    @ResponseBody
//    @ApiOperation(notes = "获取当前用户所有导入薪资条的历史", value = "获取当前用户所有导入薪资条的历史")
//    @GetSessionInfo
//    public HttpResponse<ESalaryInfoResult> getHistory(
//            @ApiParam("上传相关的id，这个接口上传 登录用户的id （salaryCorpUserId）")
//            @RequestBody ESalaryHistoryCommand eSalaryIdCommand, SessionInfo sessionInfo) {
//        ESalaryInfoResult eSalaryInfoResult = eSalaryService.salaryHistory(eSalaryIdCommand, sessionInfo);
//
//        return new HttpResponse<>(eSalaryInfoResult);
//    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "获取当前用户所有导入薪资条的历史", value = "获取当前用户所有导入薪资条的历史")
    @GetSessionInfo
    public HttpResponse<Pager<PayrollHistoryResult>> getHistory(
            @ApiParam @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize,
            SessionInfo sessionInfo) throws Exception {

        HttpResponse httpResponse = new HttpResponse(ErrorCode.CODE_OK, eSalaryService.getHistorySalaries(new MybatisPagerCommand(page, pageSize), sessionInfo));

        log.info("httpResponse.toJson(): " + httpResponse.toJson());

        return httpResponse;
    }

    //搜索
    //获取发送历史
    @RequestMapping(value = "/history/getempuser", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "通过id 获取一个用户的信息", value = "通过id 获取一个用户的信息")
    @GetSessionInfo
    public HttpResponse<ESalaryEmpUserInfoResult.UserInfo> getOnlyEmpUser(
            @ApiParam("上传相关的id，这个接口上传 登录用户的id （empUserId）")
            @RequestBody ESalaryHistoryCommand eSalaryIdCommand, SessionInfo sessionInfo) {
        return new HttpResponse<>(eSalaryService.getOnlyUser(eSalaryIdCommand));
    }


    //删除
    //获取全部的薪资内容
    @RequestMapping(value = "/history/getalluser", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "获取这一批次全部导入的用户", value = "获取这一批导入的全部用户")
    @GetSessionInfo
    public HttpResponse<ESalaryEmpUserInfoResult> getAllUser(
            @ApiParam("上传相关的id，这个接口上传 上传对应月份薪资条的ID （salaryInfoId）")
            @RequestBody ESalaryHistoryCommand eSalaryIdCommand, SessionInfo sessionInfo) {
        return new HttpResponse<>(eSalaryService.getAllUser(eSalaryIdCommand));
    }


    @RequestMapping(value = "/history/user/delete", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "删除员工", value = "删除员工")
    @GetSessionInfo
    public HttpResponse<Boolean> deleteUser(
            @ApiParam("上传相关的id，这个接口上传 上传对应月份薪资条的ID （salaryInfoId）, 需要删除的用户id 的 集合(empId[])")
            @RequestBody ESalaryUserCommand eSalaryIdCommand, SessionInfo sessionInfo) {
        return new HttpResponse<>(eSalaryService.deleteUser(eSalaryIdCommand));
    }

    @RequestMapping(value = "/validity", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "设置有效时间", value = "设置有效时间")
    @GetSessionInfo
    public HttpResponse validity(
            @ApiParam("上传相关的id，这个接口上传 登录用户的id （salaryCorpUserId）")
            @RequestBody ESalaryValidityCommand eSalaryIdCommand, SessionInfo sessionInfo) {
        log.info("电子工资单设置有效时间  start");
        eSalaryService.validity(eSalaryIdCommand, sessionInfo);
        log.info("电子工资单设置有效时间  end");
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }


    @RequestMapping(value = "/validity/get", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "获取有效期设置的时间", value = "获取有效期设置的时间")
    @GetSessionInfo
    public HttpResponse<ESalaryValidityResult> getValidity(
            @ApiParam("上传相关的id，这个接口上传 登录用户的id （salaryCorpUserId）")
            @RequestBody ESalaryHistoryCommand eSalaryIdCommand, SessionInfo sessionInfo) throws IOException {
        log.info("获取有效期设置的时间  start");
        ESalaryValidityResult validity = eSalaryService.getValidity(eSalaryIdCommand, sessionInfo);
        HttpResponse<ESalaryValidityResult> eSalaryValidityResultHttpResponse = new HttpResponse<>(validity);
        log.info("获取有效期设置的时间  end");
        log.info("返回结果   --->  " + eSalaryValidityResultHttpResponse.toJson());
        return eSalaryValidityResultHttpResponse;
    }

    @RequestMapping(value = "/change/old/date", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse setOldDate(SessionInfo sessionInfo) {
        log.info(">>>>>>><<<<<<<修改全部老数据工资单签收状态为已签收     start");
        eSalaryService.setOldDate();
        log.info(">>>>>>><<<<<<<修改全部老数据工资单签收状态为已签收     end");
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @ResponseBody
    @ApiOperation(notes = "导出签收情况表", value = "导出签收情况表")
    @GetSessionInfo
    public HttpResponse<DownloadResult> exportExcel(
            @ApiParam("上传相关的id，这个接口上传 上传对应月份薪资条的ID （salaryInfoId）, 需要删除的用户id 的 集合(empId[])")
            @RequestBody ESalaryUserCommand eSalaryIdCommand, SessionInfo sessionInfo
    ) throws Exception {
        log.info("导出工资单签收情况---->   start");
        DownloadResult downloadResult = eSalaryService.exportExcel(eSalaryIdCommand);
        HttpResponse<DownloadResult> downloadResultHttpResponse = new HttpResponse<>(downloadResult);
        log.info("导出工资单签收情况---->   end");
        log.info("返回内容---->  " + downloadResultHttpResponse.toJson());

        return downloadResultHttpResponse;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    @GetSessionInfo
    public HttpResponse<DownloadResult> download(
            @ApiParam("下载的mime_type") @RequestParam("mime_type") String mimeType,
            @ApiParam("id") @RequestParam("file_id") String fileId,
            @ApiParam("文件名称") @RequestParam("file_name") String fileName,
            @RequestParam(value = "suffix", required = false, defaultValue = "xls") String suffix,
            HttpServletResponse httpServletResponse, SessionInfo sessionInfo
    ) {
        log.info("导出工资单签收情况---->   start");
        eSalaryService.download(fileId, httpServletResponse, mimeType, suffix, fileName);
        log.info("导出工资单签收情况---->   end");

        return null;
    }

}
