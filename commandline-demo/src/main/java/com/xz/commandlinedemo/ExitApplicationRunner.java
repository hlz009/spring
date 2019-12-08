package com.xz.commandlinedemo;

import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(3)
@Slf4j
public class ExitApplicationRunner implements ApplicationRunner, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		int code = SpringApplication.exit(applicationContext);
		log.info("Exit with {}.", code);
		System.exit(code);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) 
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}