package com.bumu.arya.admin.common.service.impl;

import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.common.service.FileDownService;
import com.bumu.bran.common.Constants;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.util.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * @author yousihang
 * @date 2018/3/2
 */
@Service
public class FileDownServiceImpl implements FileDownService {


    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ExcelExportHelper excelExportHelper;

    @Autowired
    private AryaAdminConfigService config;


    @Override
    public String getExelDownUrl(Object list, String fileName,String fileTemple) {
        String url = "";
        try {
            File file = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName + ".xls");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            excelExportHelper.export(
                    config.getExportTemplatePath() + fileTemple,
                    fileName,
                    new HashMap() {{
                        put("list", list);
                    }},
                    fileOutputStream
            );

            FileUploadFileResult fileUploadFileResult = fileUploadService.uploadFile(
                    MultipartFileUtils.create(file),
                    0,
                    "xls",
                    null, null);

            url = fileUploadService.generateDownLoadFileUrl(
                    config.getConfigByKey("arya.admin.resource.server"),
                    Constants.EXCEL_LOW_FORMAT,
                    fileUploadFileResult.getId(),
                    0,
                    "xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

}
