package com.bumu.bran.admin.system.aop;

import com.bumu.arya.exception.ErrorCode;
import com.bumu.bran.employee.command.ModelCommand;
import com.bumu.exception.AryaServiceException;
import com.bumu.exception.Assert;
import com.bumu.exception.ExceptionModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * @author majun
 * @date 2016/12/21
 */
@Aspect
@Component
public class BranSetParamsAop {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("@annotation(com.bumu.bran.common.annotation.SetParams)")
	private void setParams() {
	}

	/**
	 * 设置属性
	 *
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("setParams()")
	public void setParams(JoinPoint joinPoint) throws Throwable {
		logger.debug("controllerBefore ...");
		ModelCommand modelCommand = (ModelCommand) joinPoint.getArgs()[0];
		BindingResult bindingResult = getBindingResult(joinPoint.getArgs());
		if (bindingResult != null) {
			Assert.paramsNotError(bindingResult, new ExceptionModel());
		}
		// 获取session中的数据
		Session session = SecurityUtils.getSubject().getSession();
		verifySession(session);
		String branCorpId = session.getAttribute("bran_corp_id").toString();
		String currentUserId = null;
		if (session.getAttribute("user_id") != null) {
			currentUserId = session.getAttribute("user_id").toString();
		}
		// 设置属性
		modelCommand.setUserId(currentUserId);
		modelCommand.setBranCorpId(branCorpId);
	}


	private void verifySession(Session session) {
		if (session.getAttribute("bran_corp_id") == null) {
			throw new AryaServiceException(ErrorCode.CODE_SYS_ERR, "请先登录");
		}
	}

	private BindingResult getBindingResult(Object[] args) {
		for (Object type : args) {
			if (type instanceof BindingResult) {
				return (BindingResult) type;
			}
		}
		return null;
	}
}
