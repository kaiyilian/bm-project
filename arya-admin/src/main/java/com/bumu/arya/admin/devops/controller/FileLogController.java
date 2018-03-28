package com.bumu.arya.admin.devops.controller;

import com.bumu.arya.admin.devops.controller.command.OnlineDownloadCommand;
import com.bumu.arya.admin.devops.result.OnlineLogPageResult;
import com.bumu.arya.admin.devops.result.ProjectResult;
import com.bumu.arya.admin.devops.service.FileLogService;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.result.FileUploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Guchaochao
 * @date 2018/1/10
 */
@Api(tags = {"在线日志接口 FileLog"})
@Controller
@RequestMapping("admin/online/log")
public class FileLogController {

    @Autowired
    private FileLogService fileLogService;

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/project/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有项目")
    @ResponseBody
    public HttpResponse<List<ProjectResult>> projectList() {
        return new HttpResponse<>(fileLogService.projectList());
    }

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询项目下所有日志")
    @ResponseBody
    public HttpResponse<OnlineLogPageResult> list(
            @ApiParam("项目名") @RequestParam(value="projectCode", required = true) String projectCode,
            @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize
            ) throws Exception {
        return new HttpResponse<>(fileLogService.logPage(projectCode, page, pageSize));
    }


    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/download/url", method = RequestMethod.POST)
    @ApiOperation(value = "日志下载地址")
    @ResponseBody
    public HttpResponse<FileUploadFileResult> downloadUrl(@ApiParam @RequestBody OnlineDownloadCommand onlineDownloadCommand) throws Exception {
        return new HttpResponse<>(fileLogService.downloadUrl(onlineDownloadCommand));
    }

    @ApiOperation(value = "下载文件")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public HttpResponse<Void> download(@ApiParam("文件路径") @RequestParam(value = "filePath") String filePath,
                                       HttpServletResponse response) throws Exception{
        fileLogService.download(filePath, response);
        return new HttpResponse<>();
    }
}
