package com.bumu.bran.admin.system.aop;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.common.SessionInfo;
import com.bumu.exception.AryaServiceException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author majun
 * @date 2016/12/21
 */
@Aspect
@Component
public class GetSessionInfoAop {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.bumu.bran.common.annotation.GetSessionInfo)")
    private void getSessionInfo() {
    }

    /**
     * 设置属性
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("getSessionInfo()")
    public void getSessionInfo(JoinPoint joinPoint) throws Throwable {
        logger.debug("controllerBefore ...");
        Session session = SecurityUtils.getSubject().getSession();
        verifySession(session);
        setSessionInfo(getSessionInfo(joinPoint.getArgs()), session);
    }


    private void verifySession(Session session) {
        if (session == null
                || session.getAttribute("bran_corp_id") == null
                || session.getAttribute("user_id") == null) {
            throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请先登录");
        }
    }

    private SessionInfo getSessionInfo(Object[] args) {
        for (Object type : args) {
            if (type instanceof SessionInfo) {
                return (SessionInfo) type;
            }
        }
        throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "controller 参数错误, SessionInfo");
    }

    public void setSessionInfo(SessionInfo sessionInfo, Session session) {
        sessionInfo.setCorpId((String) session.getAttribute("bran_corp_id"));
        sessionInfo.setUserId((String) session.getAttribute("user_id"));
        logger.info("sessionInfo: " + sessionInfo);
    }
}
