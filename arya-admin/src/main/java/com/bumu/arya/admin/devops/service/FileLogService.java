package com.bumu.arya.admin.devops.service;

import com.bumu.arya.admin.devops.controller.command.OnlineDownloadCommand;
import com.bumu.arya.admin.devops.result.OnlineLogPageResult;
import com.bumu.arya.admin.devops.result.ProjectResult;
import com.bumu.common.result.FileUploadFileResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Guchaochao
 * @date 2018/1/10
 */
public interface FileLogService {

    /**
     * 获取所有的项目的List
     * @return
     */
    List<ProjectResult> projectList();

    /**
     *
     * @return
     */
    OnlineLogPageResult logPage(String projectCode, Integer page, Integer pageSize) throws Exception;

    /**
     * 获取下载日志的地址
     * @param onlineDownloadCommand
     * @return
     * @throws Exception
     */
    FileUploadFileResult downloadUrl(OnlineDownloadCommand onlineDownloadCommand) throws Exception;

    /**
     * 下载
     * @param path
     * @throws Exception
     */
    void download(String path, HttpServletResponse response) throws Exception;


}
