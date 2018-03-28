package com.bumu.bran.admin.corporation.controller;

import com.bumu.common.service.CommonFileService;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author majun
 * @date 2017/10/12
 * @email 351264830@qq.com
 */
@Api(value = "BranReadFileController", tags = "企业管理平台文件读取")
@Controller
@RequestMapping(value = "/admin/common/file")
public class BranReadFileController {

    @Autowired
    private CommonFileService commonFileService;

    /**
     * 读取文件
     *
     * @param bizId
     * @param fileId
     * @param httpServletResponse
     */
    @ApiOperation(httpMethod = "GET", notes = "读取图片文件", value = "根据url读取存储的图片")
    @RequestMapping(value = "retrieve", method = RequestMethod.GET)
    @ResponseBody
    public void getFile(@ApiParam("同一类图片存放的文件夹") @RequestParam("biz_id") String bizId,
                 @ApiParam("图片文件id") @RequestParam("file_id") String fileId,
                 @ApiParam("不同类型图片存放的文件夹") @RequestParam(value = "type") Integer type,
                 @RequestParam(value = "suffix", required = false, defaultValue = "jpg") String suffix,
                 HttpServletResponse httpServletResponse) {
        try {
            commonFileService.readFile(bizId, fileId, type, suffix, httpServletResponse);
        } catch (AryaServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用下载
     *
     * @param mimeType
     * @param fileId
     * @param httpServletResponse
     */
    @ApiOperation(httpMethod = "GET", notes = "下载", value = "根据url下载文件")
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public
    @ResponseBody
    void download(@ApiParam("下载的mime_type") @RequestParam("mime_type") String mimeType,
                  @ApiParam("文件id") @RequestParam("file_id") String fileId,
                  @ApiParam("不同类型存放的文件夹") @RequestParam(value = "type") Integer type,
                  @RequestParam(value = "suffix", required = false, defaultValue = "jpg") String suffix,
                  HttpServletResponse httpServletResponse) {
        try {
            commonFileService.download(mimeType, fileId, type, suffix, httpServletResponse);
        } catch (AryaServiceException e) {
            e.printStackTrace();
        }
    }


}
