package com.xz.webdemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xz.webdemo.assit.MyInteceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	/**
	 * 添加拦截器interceptor 适合用于哪些链接中
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInteceptor())
		.addPathPatterns("/coffee/**").addPathPatterns("/order/**");
	}
}
