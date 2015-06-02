package com.sel2in.smsWebSend.web.logConf;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

@Aspect
@Component
public class LoggerAspect {
	@Around("execution(public * com.sel2in.smsWebSend.web..*(..))")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(pjp.getTarget().getClass().getName());
		final Object[] args = pjp.getArgs();
		final String methodName = pjp.getSignature().getName();
		logger.log(Sel2inLogger.INFO, "Enter: " + methodName + "()");
		if (!isUtilMethod(methodName)) {
			logger.log(Sel2inLogger.INFO, methodName + "(): " + args);
		}
		final Object result = pjp.proceed();
		if (!isUtilMethod(methodName)) {
			logger.log(Sel2inLogger.INFO, methodName + "(): result = " + result);
		}
		logger.log(Sel2inLogger.INFO, "Exit: " + methodName + "()");
		return result;
	}

	private boolean isUtilMethod(String name) {
		return name.startsWith("get") || name.startsWith("set") || name.equals("toString") || name.equals("equals") || name.equals("hashCode");
	}
}
