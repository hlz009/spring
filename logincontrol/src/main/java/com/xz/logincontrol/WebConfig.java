package com.xz.logincontrol;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 优先Object类型的HttpMessageConverter
 * 否则系统默认的是StringHttpMessageConverter
 * @author xiaozhi009
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(0, new MappingJackson2HttpMessageConverter());
	}
}
