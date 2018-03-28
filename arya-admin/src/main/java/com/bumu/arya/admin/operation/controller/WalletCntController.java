package com.bumu.arya.admin.operation.controller;

import com.bumu.arya.admin.operation.result.WalletUserCntResult;
import com.bumu.arya.admin.operation.service.WalletCntService;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.result.FileUploadFileResult;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
@Api(value = "WalletCntController", tags = {"钱包统计记录查询"})
@RestController
@RequestMapping(value = "/admin/wallet/cnt")
@ResponseBody
public class WalletCntController {

    private static Logger logger = LoggerFactory.getLogger(WalletCntController.class);

    @Autowired
    private WalletCntService walletCntService;

    @ApiOperation(value = "查询钱包人员信息")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/user/pager", method = RequestMethod.GET)
    public HttpResponse<Pager<WalletUserCntResult>> get(
            @ApiParam(value = "查询条件") @RequestParam(value = "param", required = false) String param,
            @ApiParam @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        logger.info("钱包用户统计查询~~~~");
        return new HttpResponse(ErrorCode.CODE_OK, walletCntService.userCntPager(param, pageSize, page));
    }

    @ApiOperation(value = "获取导出文件地址")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/export/url", method = RequestMethod.GET)
    public HttpResponse<Pager<FileUploadFileResult>> export(
            @ApiParam(value = "查询条件") @RequestParam(value = "param", required = false) String param) {
        return new HttpResponse(ErrorCode.CODE_OK, walletCntService.export());
    }
}
