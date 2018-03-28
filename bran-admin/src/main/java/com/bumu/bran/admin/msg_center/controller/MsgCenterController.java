package com.bumu.bran.admin.msg_center.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.bran.admin.msg_center.service.MsgCenterService;
import com.bumu.bran.common.annotation.GetSessionInfo;
import com.bumu.bran.msgcenter.result.MsgCenterResult;
import com.bumu.common.SessionInfo;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author majun
 * @date 2017/6/18
 */
@Api(value = "MsgCenterController", tags = {"企业管理平台消息中心接口"})
@RestController
@RequestMapping(value = "/admin/msg_center")
public class MsgCenterController {

    private static Logger logger = LoggerFactory.getLogger(MsgCenterController.class);

    @Autowired
    private MsgCenterService msgCenterService;

    @ApiOperation(value = "分页查看消息")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.GET)
    @GetSessionInfo
    public HttpResponse<Pager<MsgCenterResult>> getPagerList(@ApiParam("当前页") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                             @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize,
                                                             SessionInfo sessionInfo)
            throws Exception {

        HttpResponse<Pager<MsgCenterResult>> httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, msgCenterService.getPagerList(page, pageSize, sessionInfo));
        logger.info("httpResponse: " + httpResponse.toString());
        return httpResponse;
    }


    @ApiOperation(value = "设置为已读状态")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "set/state",method = RequestMethod.PATCH)
    public HttpResponse setState(@ApiParam @Valid @RequestBody BaseCommand.IDCommand IDCommand)
            throws Exception {

        msgCenterService.setState(IDCommand);
        HttpResponse<Pager<MsgCenterResult>> httpResponse = new HttpResponse<>(ErrorCode.CODE_OK);
        logger.info("httpResponse: " + httpResponse.toString());
        return httpResponse;
    }


}
