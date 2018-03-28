package com.bumu.bran.admin.salary.valid;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.arya.response.HttpResponse;
import com.bumu.common.TokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author majun
 * @date 2016/9/21
 */
public class RepeatSubmitIntercept extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(RepeatSubmitIntercept.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("进入token拦截器...");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
        if (annotation != null) {
            String key = annotation.key();
            boolean isVerify = annotation.verify();
            HttpSession session = request.getSession();
            if (isVerify) {
                if (TokenHelper.verify(request, key)) {
                    TokenHelper.cleanToken(session, key);
                    return true;
                }
                TokenHelper.cleanToken(session, key);
                PrintWriter writer = response.getWriter();
                String str = new String("请不要重复提交".getBytes("UTF-8"), "ISO8859-1");
                writer.println(new HttpResponse(ErrorCode.CODE_SYS_ERR, str).toJson());
                writer.flush();
                return false;

            } else {
                // 生成
                TokenHelper.generateToken(session, key);
                return true;
            }
        }
        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
