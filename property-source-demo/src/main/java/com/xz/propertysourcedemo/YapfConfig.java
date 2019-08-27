package com.xz.propertysourcedemo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 适合本地文件配置
 * @author xiaozhi009
 * @time 2019年8月27日下午6:44:02
 */
@Slf4j
@ConfigurationProperties(prefix = "xz")
@PropertySource("classpath:/yapf.properties")
@Component
public class YapfConfig implements ApplicationRunner {
	private String happy;
	public String getHappy() {
		return happy;
	}
	public void setHappy(String happy) {
		this.happy = happy;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("happy -> {}", happy);
		
	}
}
