package com.bumu.bran.admin.salary.aop;

import com.bumu.arya.model.AryaUserDao;
import com.bumu.arya.model.entity.AryaUserEntity;
import com.bumu.bran.admin.system.command.CorpModel;
import com.bumu.bran.common.Constants;
import com.bumu.bran.helper.PushHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author majun
 * @date 2016/11/4
 * 薪资切面
 */
@Aspect
@Component
public class SalaryAop {

    private Logger logger = LoggerFactory.getLogger(SalaryAop.class);

    @Autowired
    private PushHelper pushHelper;

    @Autowired
    private AryaUserDao aryaUserDao;

    @Pointcut("execution(* com.bumu.bran.admin.salary.service.impl.SalaryServiceImpl.release*(..))")
    private void pushRelease() {
    }

    @AfterReturning(value = "pushRelease()")
    public void sendPush(JoinPoint joinPoint) throws SchedulerException {
        logger.info("SalaryAspect ... start");
        CorpModel corpModel = (CorpModel) joinPoint.getArgs()[0];
        if (corpModel.getAryaUserIds() == null || corpModel.getAryaUserIds().isEmpty()) {
            logger.info("SalaryAspect ... list is null or empty");
            return;
        }

        logger.info("查询arya用户");
        List<AryaUserEntity> list = new ArrayList<>();
        for (String id : corpModel.getAryaUserIds()) {
            logger.info("用户id: " + id);
            list.add(aryaUserDao.findUserById(id));
        }

        logger.info("开始发送推送");
        pushHelper.sendPush(
                list,
                "薪资账单提醒",
                "发工资啦,薪资账单已生成啦,立即前往查看吧",
                Constants.SALARY_NOTICE_JUMP_QUERY_PAGE

        );
    }

}
