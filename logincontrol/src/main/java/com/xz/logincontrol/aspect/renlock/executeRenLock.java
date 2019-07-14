package com.xz.logincontrol.aspect.renlock;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//@Aspect
//@Component
//@Slf4j
public class executeRenLock {

	@Autowired
	private RedissonClient redisson;
	
	/**
	 * 切点，作用在@RenLock注解上
	 */
	@Pointcut("@annotation(com.xz.logincontrol.aspect.RenLock)")
	public void serviceAspect() {
	}

	@Before("serviceAspect()")
	public void beforeMethod(JoinPoint joinPoint) {
		RenLock renLock = getRenLockAnnotation(joinPoint);
		if (null == renLock) {
			throw new RuntimeException("renLock注解无有效值");
		}
	}

	@After("serviceAspect()")
	public void afterMethod(JoinPoint joinPoint) {
//		new 
	}
	
	private RenLock getRenLockAnnotation(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	Method method = signature.getMethod();
    	return method.getAnnotation(RenLock.class);
	}
}
