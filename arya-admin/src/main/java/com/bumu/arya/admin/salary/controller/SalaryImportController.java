/**
 * Created by bumu-zhz on 2015/11/9.
 * Edit by cmx 2015/11/25
 */
package com.bumu.arya.admin.salary.controller;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.salary.controller.command.ExecuteSalaryImportCommand;
import com.bumu.arya.admin.salary.result.SalaryImportResult;
import com.bumu.arya.admin.salary.result.SalaryImportResult.SalaryErrorMsgBean;
import com.bumu.arya.admin.salary.result.SalaryImportResult.SalaryOutputBean;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.salary.service.ImportSalaryFileService;
import com.bumu.arya.admin.salary.service.SalaryService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @deprecated
 */
@Controller
public class SalaryImportController {
	Logger log = LoggerFactory.getLogger(SalaryImportController.class);

    @Autowired
    ImportSalaryFileService importSalaryFileService;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    AryaAdminConfigService configService;

    @RequestMapping(value = "admin/salary/import/index", method = RequestMethod.GET)
    public String soinInfoImport() {
        return "salary/salary_import";
    }


    /**
     * 下载薪资导入模板
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "admin/salary/import/template/download", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse downloadSalaryTemplate()throws Exception{
        return new HttpResponse(importSalaryFileService.downloadTemplate());
    }

    /**
     * 读取模板文件
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "admin/salary/import/template/file", method = RequestMethod.GET)
    public
    @ResponseBody
    void downloadFile(HttpServletResponse response) throws Exception{
       importSalaryFileService.downloadFile(response);
    }

    @RequestMapping(value = "admin/salary/import/verify", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<SalaryImportResult> verifySalaryFile(HttpServletRequest request) {
        SalaryImportResult result = new SalaryImportResult();
        List<SalaryOutputBean> excelDatas = null;
        List<SalaryErrorMsgBean> errorMsgs = new ArrayList<>();

        String companyId = request.getParameter("corporation_id");
        if (StringUtil.isEmpty(companyId)) {
            importSalaryFileService.putMsgList(errorMsgs, "", "请选择公司");
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        if (multipartFile != null && (multipartFile.getOriginalFilename().contains(".xls")
                || multipartFile.getOriginalFilename().contains(".xlsx"))) {
            try {
                excelDatas = importSalaryFileService.readSalaryExcel(multipartFile.getInputStream());
                importSalaryFileService.checkInsert(excelDatas, errorMsgs);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                importSalaryFileService.putMsgList(errorMsgs, "", "上传文件异常");
                return new HttpResponse<>(result, ErrorCode.CODE_SALARY_IMPORT_READ_UPLOAD_FILE_FAILD);
            }
        } else {
            importSalaryFileService.putMsgList(errorMsgs, "", "请选择文件");
        }
        if (errorMsgs != null && errorMsgs.size() > 0) {
            result.setMsgs(errorMsgs);
            excelDatas = null;
            return new HttpResponse<>(result, ErrorCode.CODE_SALARY_IMPORT_FAILD);
        } else {
            try {
                // 转存文件
                String fileName = Utils.makeUUID();
                multipartFile.transferTo(new File(configService.getSalaryUploadPath() + fileName));
                result.setFileId(fileName);
                result.setExcelDatas(excelDatas);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new HttpResponse(ErrorCode.CODE_SALARY_IMPORT_FILE_SAVE_FAILD);//参保信息文件转存失败
            }
        }
        return new HttpResponse<>(result);
    }

    @RequestMapping(value = "admin/salary/import/execute", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<SalaryImportResult> confirm(@RequestBody ExecuteSalaryImportCommand command) {
        SalaryImportResult result = new SalaryImportResult();
        String filePath = configService.getSalaryUploadPath() + command.getFileId();
        try {
            File sourceFile = new File(filePath);
            InputStream inputStream = new FileInputStream(sourceFile);
            List<SalaryOutputBean> excelDatas = importSalaryFileService.readSalaryExcel(inputStream);
            if (excelDatas != null && excelDatas.size() > 0 && !StringUtil.isEmpty(command.getCorporationId())) {
                List<SalaryErrorMsgBean> errorMsgBeans = salaryService.insert(command.getCorporationId(), excelDatas);
                if (errorMsgBeans == null || errorMsgBeans.size() == 0) {
                } else {
                    result.setMsgs(errorMsgBeans);
                    return new HttpResponse<>(result, ErrorCode.CODE_SALARY_IMPORT_SAVE_DB_FAILD);
                }
            } else {
                return new HttpResponse<>(result, ErrorCode.CODE_SALARY_IMPORT_DONT_HAVE_DATA);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new HttpResponse<>(result, ErrorCode.CODE_SALARY_IMPORT_READ_SAVED_FILE_FAILD);
        }
        return new HttpResponse<>(result);
    }
}
