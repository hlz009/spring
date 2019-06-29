package com.xz.bucks.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class RecordAspect {
	
	@Around("repositoryOps()")
	public Object logTimeCosts(ProceedingJoinPoint pjp) throws Throwable{
		long startTime = System.currentTimeMillis();
		String name = "-";
		String result = "Y";
		try {
			name = pjp.getSignature().toShortString();
			return pjp.proceed();
		} catch (Throwable t) {
			result = "N";
			throw t;
		} finally {
			long endTime = System.currentTimeMillis();
			log.info("{};{};{}ms", name, result, endTime-startTime);
		}
	}
	
	@Pointcut("execution(* com.xz.bucks.repository..*(..))")
	private void repositoryOps() {
	}
}
