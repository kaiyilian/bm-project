package com.bumu.arya.admin.operation.controller;

import com.bumu.arya.Utils;
import com.bumu.arya.admin.operation.controller.command.NewsCommand;
import com.bumu.arya.admin.operation.controller.command.NewsResult;
import com.bumu.arya.admin.operation.controller.command.NewsCom;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.news.model.entity.NewsEntity;
import com.bumu.arya.news.result.NewsArrayResult;
import com.bumu.arya.news.service.NewsService;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新闻Controller
 */
@Controller
@Api(value = "新闻Controller")
@RequestMapping(value = "/admin/news")
public class NewsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NewsService newsService;

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加新闻")
    @ResponseBody
    public HttpResponse<NewsResult> getNew(@ApiParam("title，type,imgSrc,uri为必须传") @RequestBody NewsCommand newsCommand) throws Exception {
        if(newsCommand.getImgSrc() == null || newsCommand.getType() == null || newsCommand.getTitle() == null || newsCommand.getUri() == null){
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        String id = Utils.makeUUID();

        if(newsCommand.getSource_url() == null || newsCommand.getSoureceName() == null){
            NewsEntity newsEntity=new NewsEntity(id, newsCommand.getTitle(), newsCommand.getUri(), newsCommand.getImgSrc(),
                    newsCommand.getType(),null,null,0);
            newsService.addNew(newsEntity);
        }else {
            NewsEntity newsEntity = new NewsEntity(id, newsCommand.getTitle(), newsCommand.getUri(), newsCommand.getImgSrc(),
                    newsCommand.getType(), newsCommand.getSoureceName(), newsCommand.getSource_url(), 0);
            newsService.addNew(newsEntity);
        }
        NewsEntity newsEntity = newsService.getId(id);
        NewsResult newsResult =new NewsResult();
        if(newsEntity != null ){
            newsResult.setId(newsEntity.getId());
        }
        return new HttpResponse<NewsResult>(ErrorCode.CODE_OK, newsResult);
    }

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除新闻新闻")
    @ResponseBody
    public HttpResponse deleteNew(@ApiParam @RequestBody NewsCom newsCom) throws Exception {
        List<String> ids = newsCom.getIds();
        newsService.deleteNew(ids);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取")
    @ResponseBody
    public HttpResponse<NewsEntity> getById(@ApiParam("修改新闻的id") @RequestParam(value="id",required = true) String id) throws Exception {
        NewsEntity newsEntity=newsService.getId(id);
        HttpResponse<NewsEntity> httpResponse=new HttpResponse<NewsEntity>(newsEntity);
        return httpResponse;
    }

    @ApiResponse(code = 200, message = "成功", response = HttpResponse.class)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改新闻新闻")
    @ResponseBody
    public HttpResponse updateNew(@ApiParam("title，type,imgSrc,uri为必须传") @RequestBody NewsCommand newsCommand) throws Exception {
        if(newsCommand.getImgSrc() == null || newsCommand.getType() == null || newsCommand.getTitle() == null || newsCommand.getUri() == null){
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        NewsEntity newsEntity=newsService.getId(newsCommand.getId());
        newsEntity.setImgSrc(newsCommand.getImgSrc());
        newsEntity.setIsChoose(newsCommand.getIsChoose());
        newsEntity.setTitle(newsCommand.getTitle());
        newsEntity.setType(newsCommand.getType());
        newsEntity.setUri(newsCommand.getUri());
        if(newsCommand.getSource_url() == null || newsCommand.getSoureceName() == null){
            newsEntity.setSourceName(null);
            newsEntity.setSource_url(null);
        }else {
            newsEntity.setSourceName(newsCommand.getSoureceName());
            newsEntity.setSource_url(newsCommand.getSoureceName());
        }
        newsService.update(newsEntity);
        return new HttpResponse<>(ErrorCode.CODE_OK);
    }

    @ApiResponse(code = 200, message = "成功", response = NewsEntity.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询所有分类的新闻")
    public HttpResponse<List<NewsArrayResult>> list() {
        return new HttpResponse(newsService.newsList(null));
    }

    /*@ApiResponse(code = 200, message = "成功", response = NewsEntity.class)
    @RequestMapping(value = "/app/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询App端显示的新闻")
    public HttpResponse<List<NewsArrayResult>> appList(
            @ApiParam("每种分类显示的最多新闻数，如果为空，则显示所有") @RequestParam(value = "topNewNumber") Integer topNewNumber) {
        return new HttpResponse(newsService.newsList(topNewNumber));
    }*/
}
