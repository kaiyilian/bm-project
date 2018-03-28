package com.bumu.bran.admin.employee_import.service;

import com.bumu.bran.admin.employee.result.ImportEmployeeResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.FileStrCommand;
import com.bumu.common.result.FileUploadFileResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author majun
 * @date 2017/11/9
 * @email 351264830@qq.com
 */
@Transactional
public interface EmployeeImportService {

    FileUploadFileResult download(SessionInfo sessionInfo, HttpServletResponse response) throws Exception;

    ImportEmployeeResult verify(MultipartFile file, SessionInfo sessionInfo) throws Exception;

    void confirm(FileStrCommand fileStrCommand, SessionInfo sessionInfo) throws Exception;
}
