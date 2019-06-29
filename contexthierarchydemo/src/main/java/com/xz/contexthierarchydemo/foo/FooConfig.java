package com.xz.contexthierarchydemo.foo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.xz.contexthierarchydemo.context.TestBean;

/**
 * 父上下文（parent application context）
 * (root WebApplication	)
 * @author xiaozhi009
 *
 */
@Configuration
@EnableAspectJAutoProxy   // 开启父上下文
public class FooConfig {
	@Bean
	public TestBean testBeanX() {
		return new TestBean("ParentBean");
	}

	@Bean
	public TestBean testBeanY() {
		return new TestBean("ParentBean");
	}

	// 父上下文增强
	@Bean
	public FooAspect fooAspect() {
		return new FooAspect();
	}
}
