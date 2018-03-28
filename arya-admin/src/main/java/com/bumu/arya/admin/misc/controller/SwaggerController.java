package com.bumu.arya.admin.misc.controller;

import com.bumu.arya.response.HttpResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Swagger文档生成样例
 * Created by allen on 2017/3/5.
 */
@Controller
@Api(tags = {"Swagger样例"}) // value，且不能写中文，tags内容会显示为文档标题，其中至少包含一个英文单词
public class SwaggerController {

    /**
     * 测试接口是否可用
     *
     * @param a
     * @return
     */
    @ApiOperation(notes = "详细描述", value = "测试Swagger生成API文档是否工作")
    @RequestMapping(value = "/swagger/api", method = RequestMethod.GET)
    public String swaggerWorks(@ApiParam("输入参数a") @RequestParam("a") String a) {
        if ("a".equals(a)) {
            return "swagger/swagger_page";
        }

        return new String("error");
    }

    /**
     * 字符串参数，继承的返回值
     *
     * @param a
     * @return
     */
    @ApiOperation(notes = "详细描述", value = "测试Swagger生成API文档-字符串参数")
    @RequestMapping(value = "/swagger/api/string", method = RequestMethod.GET)
    public HttpResponse<SwaggerResponseInnerObject> swagger(@ApiParam("输入参数a") @RequestParam("a") String a,
                                                            @ApiParam("输入参数b") @RequestParam("b") int b) {

        return new HttpResponse<>();
    }

    /**
     * 对象参数，非继承的返回值
     *
     * @param cmd
     * @return
     */
    @ApiOperation(notes = "详细描述", value = "测试Swagger生成API文档-对象参数")
    @RequestMapping(value = "/swagger/api/command", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse<SwaggerResponseInnerObject> swaggerCommand(
            @ApiParam("输入参数cmd") @RequestBody SwaggerCommand cmd) {

        return new HttpResponse<>();
    }

    @ApiOperation(value = "表单-文件上传样例", consumes = "multipart/form-data")
    @RequestMapping(value = "/swagger/api/upload", consumes = {"multipart/form-data"}, method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse<Void> uploadFile(
            @RequestPart(value = "file_name") String name,
            @RequestPart(value = "file") MultipartFile idcardFrontFile){
        return new HttpResponse<>();
    }

    /**
     *
     */
    @ApiModel
    public static class SwaggerCommand {

        @ApiModelProperty("参数A")
        String a;

        @ApiModelProperty("参数B")
        int b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }


    @ApiModel
    public static class SwaggerResponseInnerObject {
        @ApiModelProperty(value = "返回值A")
        String a;

        @ApiModelProperty(value = "返回值B")
        String b;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }
}
