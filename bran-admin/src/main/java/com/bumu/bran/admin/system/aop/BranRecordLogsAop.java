package com.bumu.bran.admin.system.aop;

import com.bumu.arya.model.SysLogDao;
import com.bumu.bran.common.annotation.RecordLogs;
import com.bumu.bran.employee.command.ModelCommand;
import com.bumu.bran.common.model.dao.BranOpLogDao;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 记录日志的aop
 * @author majun
 * @date 2016/12/21
 */
@Aspect
@Component
public class BranRecordLogsAop {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BranOpLogDao branOpLogDao;

	@Pointcut("@annotation(com.bumu.bran.common.annotation.RecordLogs)")
	private void recordLogs() {
	}

	/**
	 * 记录日志
	 *
	 * @param joinPoint
	 * @throws Throwable
	 */
	@AfterReturning("recordLogs()")
	public void recordLogs(JoinPoint joinPoint) throws Throwable {
		logger.debug("controllerAfterReturning ...");
		ModelCommand modelCommand = (ModelCommand) joinPoint.getArgs()[0];
		Signature signature = joinPoint.getSignature();
		Method method = ((MethodSignature) signature).getMethod();
		RecordLogs recordLogs = method.getAnnotation(RecordLogs.class);
		String logInfo = recordLogs.msg() + modelCommand.getName();
		logger.debug("记录日志: " + logInfo);
		// 设置日志参数
		String logId = branOpLogDao.success(
				recordLogs.model(),
				recordLogs.type(),
				modelCommand.getUserId(),
				new SysLogDao.SysLogExtInfo(logInfo)
		);
		logger.debug("logId: " + logId);
	}

}
