package com.xz.contexthierarchydemo.context;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.xz.contexthierarchydemo.foo.FooConfig;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RunDemo implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		// 父上下文
		ApplicationContext fooContext = new AnnotationConfigApplicationContext(FooConfig.class);
		// 子上下文
		ClassPathXmlApplicationContext barContext = new ClassPathXmlApplicationContext(
				new String[] {"applicationContext.xml"}, fooContext);
		TestBean bean = fooContext.getBean("testBeanX", TestBean.class);
		bean.hello();

		log.info("=============");

		bean = barContext.getBean("testBeanX", TestBean.class);
		bean.hello();

		bean = barContext.getBean("testBeanY", TestBean.class);
		bean.hello();
	}
	
}
