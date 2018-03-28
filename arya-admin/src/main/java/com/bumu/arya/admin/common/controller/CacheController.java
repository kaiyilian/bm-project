package com.bumu.arya.admin.common.controller;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.service.RedisService;
import com.bumu.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author yousihang
 * @date 2018/1/10
 */
@Controller
public class CacheController {

    private static Logger logger = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private RedisService redisService;

    /**
     * 查询key长度
     *
     * @param request
     * @param response
     * @param key
     * @param type     :list set str map
     */
    @RequestMapping(value = "/public/cache/data/length")
    @ResponseBody
    public HttpResponse<String> multipleData(HttpServletRequest request, HttpServletResponse response, String key, String type) {
        String length = "0";
        Map<String,Object> result =new HashMap<>();

        try {

            if (!StringUtil.isAllNotEmpty(key, type)) {
                return new HttpResponse(ErrorCode.CODE_PARAMS_ERROR);
            }

            if ("list".equals(type)) {
                length = redisService.llen(key) + "";
            } else if ("map".equals(type)) {
                length = redisService.hlen(key) + "";
            } else if ("str".equals(type)) {
                length = redisService.get(key);
            } else if ("set".equals(type)) {
                length = redisService.scard(key) + "";
            }
            result.put("lenth",length);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
          return new HttpResponse(result);
    }

}
