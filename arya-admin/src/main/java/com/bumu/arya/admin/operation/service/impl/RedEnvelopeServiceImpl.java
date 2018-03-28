package com.bumu.arya.admin.operation.service.impl;

import com.bumu.arya.admin.common.service.AryaAdminConfigService;
import com.bumu.arya.admin.operation.controller.command.RedEnvelopeCommand;
import com.bumu.arya.admin.operation.model.dao.mybatis.RedEnvelopeResultMybatisDao;
import com.bumu.arya.admin.operation.result.RedEnvelopeResult;
import com.bumu.arya.admin.operation.service.RedEnvelopeService;
import com.bumu.arya.command.PagerCommand;
import com.bumu.bran.common.Constants;
import com.bumu.bran.handler.ExportHandler;
import com.bumu.common.result.DownloadResult;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import com.bumu.common.service.FileUploadService;
import com.bumu.common.util.MultipartFileUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
@Service
public class RedEnvelopeServiceImpl implements RedEnvelopeService {

    private static Logger logger = LoggerFactory.getLogger(RedEnvelopeServiceImpl.class);

    @Autowired
    private RedEnvelopeResultMybatisDao redEnvelopeResultMybatisDao;

    @Autowired
    private ExportHandler exportHandler;

    @Autowired
    private AryaAdminConfigService aryaAdminConfigService;

    @Autowired
    private FileUploadService fileUploadService;


    @Override
    public Pager<RedEnvelopeResult> get(RedEnvelopeCommand redEnvelopeCommand, PagerCommand pagerCommand) {
        Page<RedEnvelopeResult> page = PageHelper.startPage(pagerCommand.getPage(), pagerCommand.getPage_size()).doSelectPage(() ->
                redEnvelopeResultMybatisDao.get(redEnvelopeCommand)
        );
        return new Pager(page.getPageSize(), (int) page.getTotal(), page.getResult());
    }

    @Override
    public DownloadResult export(RedEnvelopeCommand redEnvelopeCommand) throws Exception {

        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "领红包记录导出" + ".xls");
        logger.info("file: " + file.getPath());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        exportHandler.export(aryaAdminConfigService.getExportTemplatePath() + "arya-admin-redEnvelopeExport.xls",
                "红包记录",
                new HashedMap() {{
                    put("list", redEnvelopeResultMybatisDao.get(redEnvelopeCommand));
                }},
                fileOutputStream
        );

        FileUploadFileResult fileUploadFileResult = fileUploadService.uploadFile(
                MultipartFileUtils.create(file),
                0,
                "xls",
                null, null);

        String url = fileUploadService.generateDownLoadFileUrl(
                aryaAdminConfigService.getConfigByKey("arya.admin.resource.server"),
                Constants.EXCEL_LOW_FORMAT,
                fileUploadFileResult.getId(),
                0,
                "xls");

        logger.info("url: " + url);
        return new DownloadResult(url);
    }
}
