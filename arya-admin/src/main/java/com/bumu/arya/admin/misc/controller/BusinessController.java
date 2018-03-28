package com.bumu.arya.admin.misc.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.misc.model.BusinessPostilCommand;
import com.bumu.arya.misc.result.BusinessResult;
import com.bumu.arya.response.HttpResponse;
import com.bumu.arya.misc.service.BusinessService;
import com.bumu.common.annotation.ParamCheck;
import com.bumu.common.command.BaseCommand;
import com.bumu.common.result.Pager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 客户商机
 *
 * @author majun
 * @date 2017/6/12
 */
@Api(value = "Business", tags = {"客户商机BusinessRestController"})
@RestController
@RequestMapping(value = "/admin/business")
public class BusinessController {

    private static Logger logger = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessService businessService;

    @ApiOperation(value = "分页查询客户商机列表")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(method = RequestMethod.GET)
    public HttpResponse<Pager<BusinessResult>> getListByKeyWord(@ApiParam(value = "关键字: 企业名称或者姓名")
                                                                @RequestParam(value = "key_word", required = false) String keyWord,
                                                                @RequestParam(value = "customer_from", required = false) Integer customerFrom,
                                                                @ApiParam(value = "处理结果 0:意向用户 1:无效用户 2:洽谈中 不传:全部") @RequestParam(value = "result", required = false) Integer result,
                                                                @ApiParam("当前页") @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                @ApiParam("一页数量") @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize)
            throws Exception {
        HttpResponse<Pager<BusinessResult>> httpResponse = new HttpResponse<>(ErrorCode.CODE_OK, businessService.getPageListByKeyWord(keyWord, customerFrom, result, page, pageSize));
        logger.info("httpResponse: " + httpResponse.toString());
        return httpResponse;
    }

    @ApiOperation(value = "删除客户商机")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "del", method = RequestMethod.POST)
    @ParamCheck
    public HttpResponse<Void> del(@Valid @ApiParam @RequestBody BaseCommand.BatchIds ids, BindingResult bindingResult) throws Exception {
        businessService.del(ids);
        return new HttpResponse();
    }

    /**
     * @param ids
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "确认处理")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    @ParamCheck
    public HttpResponse<Void> confirm(@Valid @ApiParam @RequestBody BaseCommand.BatchIds ids, BindingResult bindingResult) throws Exception {
        businessService.confirm(ids);
        return new HttpResponse();
    }


    @ApiOperation(value = "添加批注,局部更新")
    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "postil", method = RequestMethod.PATCH)
    @ParamCheck
    public HttpResponse<Void> postil(@Valid @ApiParam @RequestBody BusinessPostilCommand businessPostilCommand, BindingResult bindingResult) throws Exception {
        logger.info("参数: " + businessPostilCommand.toString());
        businessService.postil(businessPostilCommand);
        return new HttpResponse();
    }
}
