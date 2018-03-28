package com.bumu.arya.admin.common.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.service.CommonFileService;
import com.bumu.common.service.FileUploadService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传通用接口
 * Created by allen on 16/11/15.
 */
@Api(value = "FileUploadController", tags = "通用Common")
@Controller
public class FileUploadController {

    private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    CommonFileService commonFileService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "上传图片文件", consumes = "multipart/form-data", value = "临时文件都统一储存在temp目录下")
    @RequestMapping(value = "admin/common/file/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public HttpResponse<FileUploadFileResult> uploadFile(
            @ApiParam("上传的图片文件")
            @RequestPart(value = "file", required = false) MultipartFile file,
            @ApiParam("不同类型图片的存放目录 0: 通用图片 1：福库商品图片 2：福库底图 3:印章图片 4:电子合同模板文件 ")
            @RequestParam(value = "type", required = false, defaultValue = "0") Integer type,
            @ApiParam(value = "文件的后缀", examples = @Example({@ExampleProperty(value = "html"), @ExampleProperty(value = "jpg")}))
            @RequestParam(value = "suffix", required = false, defaultValue = "jpg") String suffix,
            @ApiParam(value = "图片大小的限制, 图片的长度, 只针对图片")
            @RequestParam(value = "filter_height", required = false) Integer filterHeight,
            @ApiParam(value = "图片大小的限制, 图片的宽度, 只针对图片")
            @RequestParam(value = "filter_width", required = false) Integer filterWidth
    ) {
        try {
            logger.info("param check");
            if (file == null) {
                throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR, "请上传文件");
            }
            return new HttpResponse(fileUploadService.uploadFile(file, type, suffix, filterHeight, filterWidth));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode(), e.getMessage());
        }
    }

    /**
     * 读取文件
     *
     * @param bizId
     * @param fileId
     * @param httpServletResponse
     */
    @ApiOperation(httpMethod = "GET", notes = "读取图片文件", value = "根据url读取存储的图片")
    @RequestMapping(value = "admin/common/file/retrieve", method = RequestMethod.GET)
    public
    @ResponseBody
    void getFile(@ApiParam("同一类图片存放的文件夹") @RequestParam("biz_id") String bizId,
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
    @RequestMapping(value = "admin/common/file/download", method = RequestMethod.GET)
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
