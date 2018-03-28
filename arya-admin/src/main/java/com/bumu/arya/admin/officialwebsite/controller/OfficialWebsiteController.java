package com.bumu.arya.admin.officialwebsite.controller;

import com.bumu.arya.Utils;
import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.exception.AryaServiceException;
import com.bumu.officialwebsite.command.*;
import com.bumu.officialwebsite.model.entity.NewsInfoEntity;
import com.bumu.officialwebsite.model.entity.RecruitInfoEntity;
import com.bumu.officialwebsite.result.NewsInfoDayResult;
import com.bumu.officialwebsite.result.NewsInfoResult;
import com.bumu.officialwebsite.result.RecruitInfoResult;
import com.bumu.officialwebsite.service.NewsInfoService;
import com.bumu.officialwebsite.service.RecruitInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @zhaozx
 * @create 2018-03-06 15:51
 **/
@Api(value = "officialWebsite",tags = "官网首页")
@Controller
@RequestMapping(value = "/admin")
public class OfficialWebsiteController {
    @Autowired
    private NewsInfoService newsInfoService;
    @Autowired
    private RecruitInfoService recruitInfoService;


    @ApiOperation(httpMethod = "POST",notes = "保存招聘",value = "保存招聘")
    @RequestMapping(value = "/officialwebsite/recruit/save", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse saveRecruit(@ApiParam("jobName，jobDuty,jobRequire,为必须传") @RequestBody RecruitInfoCommand recruitInfoCommand) {
        if(StringUtils.isBlank(recruitInfoCommand.getJobDuty())||
                StringUtils.isBlank(recruitInfoCommand.getJobName())||StringUtils.isBlank(recruitInfoCommand.getJobRequire())){
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        RecruitInfoEntity entity = new RecruitInfoEntity();
        entity.setId(Utils.makeUUID());
        entity.setCreateTime(new Date());
        entity.setJobDuty(recruitInfoCommand.getJobDuty());
        entity.setJobName(recruitInfoCommand.getJobName());
        entity.setJobRequire(recruitInfoCommand.getJobRequire());
        entity.setUpdateTime(new Date());
        recruitInfoService.addRecruitInfo(entity);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(httpMethod = "POST",notes = "更新招聘",value = "更新招聘")
    @RequestMapping(value = "/officialwebsite/recruit/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse updateRecruit(@ApiParam("id,jobName，jobDuty,jobRequire,为必须传") @RequestBody RecruitInfoCommand recruitInfoCommand) {
        if(StringUtils.isBlank(recruitInfoCommand.getJobDuty())||StringUtils.isBlank(recruitInfoCommand.getId())||
                StringUtils.isBlank(recruitInfoCommand.getJobName())||StringUtils.isBlank(recruitInfoCommand.getJobRequire())){
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        recruitInfoService.updateRecruitInfo(recruitInfoCommand);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(httpMethod = "POST",notes = "删除招聘",value = "删除招聘")
    @RequestMapping(value = "/officialwebsite/recruit/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse deleteRecruit(@ApiParam("id为必须传") @RequestBody IdsCommand idsCommand) {
        if(idsCommand.getIds()==null||idsCommand.getIds().size()==0){
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        recruitInfoService.delRecruitInfo(idsCommand);
        return new HttpResponse(ErrorCode.CODE_OK);
    }


    @ApiOperation(httpMethod = "POST",notes = "保存新闻",value = "保存新闻")
    @RequestMapping(value = "/officialwebsite/news/save", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse saveNews(@ApiParam("title，publishTime，author，newsContent为必须传") @RequestBody NewsInfoCommand newsInfoCommand) {
        if(StringUtils.isBlank(newsInfoCommand.getTitle())||newsInfoCommand.getPublishTime()==null
                ||StringUtils.isBlank(newsInfoCommand.getAuthor())||StringUtils.isBlank(newsInfoCommand.getNewsContent())){
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        NewsInfoEntity entity = new NewsInfoEntity();
        entity.setId(Utils.makeUUID());
        entity.setCreateTime(new Date());
        entity.setPublishTime(new Date(newsInfoCommand.getPublishTime()));
        entity.setTitle(newsInfoCommand.getTitle());
        entity.setUpdateTime(new Date());
        entity.setAuthor(newsInfoCommand.getAuthor());
        entity.setNewsContent(newsInfoCommand.getNewsContent());
        newsInfoService.addNewsInfo(entity);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(httpMethod = "POST",notes = "更新新闻",value = "更新新闻")
    @RequestMapping(value = "/officialwebsite/news/update", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse updateNews(@ApiParam("id，title，publishTime，author，newsContent,为必须传") @RequestBody NewsInfoCommand newsInfoCommand) {
        if(StringUtils.isBlank(newsInfoCommand.getTitle())||newsInfoCommand.getPublishTime()==null
                ||StringUtils.isBlank(newsInfoCommand.getAuthor())||StringUtils.isBlank(newsInfoCommand.getNewsContent())||
                StringUtils.isBlank(newsInfoCommand.getId())){
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        newsInfoService.updateNewsInfo(newsInfoCommand);
        return new HttpResponse(ErrorCode.CODE_OK);
    }

    @ApiOperation(httpMethod = "POST",notes = "删除新闻",value = "删除新闻")
    @RequestMapping(value = "/officialwebsite/news/delete", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpResponse deleteNews(@ApiParam("id为必须传") @RequestBody IdsCommand idsCommand) {
        if(idsCommand.getIds()==null||idsCommand.getIds().size()==0){
            throw new AryaServiceException(ErrorCode.CODE_PARAMS_ERROR);
        }
        newsInfoService.delNewsInfo(idsCommand);
        return new HttpResponse(ErrorCode.CODE_OK);
    }


    /**
     * 新闻列表
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "新闻列表", value = "新闻列表")
    @RequestMapping(value = "/officialwebsite/news/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<NewsInfoResult> getNewsRecords(
            @ApiParam(value = "页码") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "每页数量") @RequestParam(value = "page_size", required = false) Integer pageSize, HttpServletResponse response) {
        try {
            if (page == null) {
                page = 1;
            }
            page = page - 1;

            if (pageSize == null) {
                pageSize = 10;
            }
            NewsQueryCommand queryCommand = new NewsQueryCommand();
            queryCommand.setPage(page);
            queryCommand.setPageSize(pageSize);
            return new HttpResponse(newsInfoService.findByPage(queryCommand));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

    /**
     * 招聘列表
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "招聘列表", value = "招聘列表")
    @RequestMapping(value = "/officialwebsite/recruit/list", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<RecruitInfoResult> getRecruitRecords(
            @ApiParam(value = "页码") @RequestParam(value = "page", required = false) Integer page,
            @ApiParam(value = "每页数量") @RequestParam(value = "page_size", required = false) Integer pageSize,HttpServletResponse response
            ) {
        try {
            if (page == null) {
                page = 1;
            }
            page = page - 1;

            if (pageSize == null) {
                pageSize = 10;
            }
            RecruitQueryCommand queryCommand = new RecruitQueryCommand();
            queryCommand.setPage(page);
            queryCommand.setPageSize(pageSize);
            return new HttpResponse(recruitInfoService.findByPage(queryCommand));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }


    /**
     * 新闻列表
     * @param id
     * @return
     */
    @ApiOperation(httpMethod = "GET", notes = "新闻详情", value = "新闻详情")
    @RequestMapping(value = "/officialwebsite/news/id", method = RequestMethod.GET)
    public
    @ResponseBody
    HttpResponse<NewsInfoDayResult> getNewsById(
            @ApiParam(value = "新闻ID") @RequestParam(value = "id", required = false) String id) {
        try {
            return new HttpResponse(newsInfoService.findById(id));
        } catch (AryaServiceException e) {
            return new HttpResponse(e.getErrorCode());
        }
    }

}
