package com.xz.contexthierarchydemo.foo;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class FooAspect {
	@AfterReturning("bean(testBean*)")
	public void printAfter() {
		log.info("after hello()");
	}
}
