package com.bumu.arya.admin.misc.service;

import com.bumu.arya.admin.misc.controller.command.CriminalBatchCommand;
import com.bumu.arya.admin.misc.controller.command.CriminalCommand;
import com.bumu.arya.admin.misc.result.CriminalBatchResult;
import com.bumu.arya.admin.misc.result.CriminalDownloadTemplateResult;
import com.bumu.arya.admin.misc.result.CriminalRecordExcelReadResult;
import com.bumu.arya.admin.misc.result.CriminalResult;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author majun
 * @date 2017/3/9
 */
@Transactional
public interface CriminalService {

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    CriminalBatchResult queryBatch(CriminalBatchCommand batch) throws Exception;

    CriminalResult queryOne(CriminalCommand param) throws Exception;

    HttpResponse download(HttpServletResponse httpServletResponse);

    String confirm(MultipartFile param, HttpServletResponse response) throws Exception;

    CriminalDownloadTemplateResult downloadLink(HttpServletRequest request, String urlPath);

    HttpResponse downloadExcelFile(String filename, HttpServletResponse httpServletResponse);

    int count();


}
