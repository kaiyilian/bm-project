package com.bumu.arya.admin.operation.service.impl;

import com.bumu.arya.activity.command.EnrollRecordCommand;
import com.bumu.arya.activity.model.dao.EnrollRecordDao;
import com.bumu.arya.activity.result.EnrollRecordListResult;
import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.operation.service.EnrollRecordService;
import com.bumu.bran.common.Constants;
import com.bumu.bran.helper.ExcelExportHelper;
import com.bumu.common.result.DownloadResult;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.util.MultipartFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;


@Service
public class EnrollRecordServiceImpl implements EnrollRecordService {

    private static Logger logger = LoggerFactory.getLogger(EnrollRecordServiceImpl.class);

    @Autowired
    EnrollRecordDao enrollRecordDao;

    @Autowired
    private ExcelExportHelper excelExportHelper;

    @Autowired
    AryaAdminConfigService config;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public EnrollRecordListResult queryRecordList(EnrollRecordCommand command) {
       return  enrollRecordDao.queryRecordList(command);
    }

    @Override
    public DownloadResult exportOrders(EnrollRecordCommand command, HttpServletResponse response)  throws Exception {

            List<EnrollRecordListResult.EnrollRecordResult> list= enrollRecordDao.exportOrders(command);
            File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "活动报名" + ".xls");
            logger.info("file: " + file.getPath());
            FileOutputStream fileOutputStream = new FileOutputStream(file);

             excelExportHelper.export(
                config.getExportTemplatePath() + AryaAdminConfigService.ENROLL_RECORD_TEMPLATE_IMPORT,
                "活动报名",
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

        String url = fileUploadService.generateDownLoadFileUrl(
                config.getConfigByKey("arya.admin.resource.server"),
                Constants.EXCEL_LOW_FORMAT,
                fileUploadFileResult.getId(),
                0,
                "xls");

        logger.info("url: " + url);
        return new DownloadResult(url);

    }
}
