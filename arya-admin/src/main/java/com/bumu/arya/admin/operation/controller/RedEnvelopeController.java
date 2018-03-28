package com.bumu.arya.admin.operation.controller;

import com.bumu.arya.admin.operation.controller.command.RedEnvelopeCommand;
import com.bumu.arya.admin.operation.result.RedEnvelopeResult;
import com.bumu.arya.admin.operation.service.RedEnvelopeService;
import com.bumu.arya.command.MybatisPagerCommand;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.result.DownloadResult;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author majun
 * @date 2017/12/26
 * @email 351264830@qq.com
 */
@Api(value = "RedEnvelopeController", tags = {"红包领取记录查询"})
@RestController
@RequestMapping(value = "/admin/red/envelope")
public class RedEnvelopeController {

    private static Logger logger = LoggerFactory.getLogger(RedEnvelopeController.class);

    @Autowired
    private RedEnvelopeService redEnvelopeService;

    @ApiOperation(value = "红包领取查询")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @GetMapping
    public HttpResponse<Pager<RedEnvelopeResult>> get(
            @ApiParam(value = "开始时间") @RequestParam(value = "start", required = false) Long start,
            @ApiParam(value = "结束时间") @RequestParam(value = "end", required = false) Long end,
            @ApiParam(value = "红包余额") @RequestParam(value = "redEnvelopeBalance", required = false) String redEnvelopeBalance,
            @ApiParam(value = "手机号") @RequestParam(value = "phone", required = false) String phone,
            @ApiParam @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
    ) {
        logger.info("红包领取查询 controller");
        if (StringUtils.isNotBlank(redEnvelopeBalance)) {
            redEnvelopeBalance = redEnvelopeBalance.trim();
        }
        RedEnvelopeCommand redEnvelopeCommand = new RedEnvelopeCommand(start, end, redEnvelopeBalance, phone);
        MybatisPagerCommand mybatisPagerCommand = new MybatisPagerCommand(page, pageSize);
        return new HttpResponse(ErrorCode.CODE_OK, redEnvelopeService.get(redEnvelopeCommand, mybatisPagerCommand));
    }

    @ApiOperation(value = "红包领取导出")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @GetMapping(value = "export")
    public HttpResponse<DownloadResult> export(
            @ApiParam(value = "开始时间") @RequestParam("start") Long start,
            @ApiParam(value = "结束时间") @RequestParam("end") Long end,
            @ApiParam(value = "红包余额") @RequestParam("redEnvelopeBalance") String redEnvelopeBalance,
            @ApiParam(value = "手机号") @RequestParam("phone") String phone) throws Exception {
        logger.info("红包领取导出 controller");
        RedEnvelopeCommand redEnvelopeCommand = new RedEnvelopeCommand(start, end, redEnvelopeBalance, phone);
        return new HttpResponse(ErrorCode.CODE_OK, redEnvelopeService.export(redEnvelopeCommand));
    }
}
