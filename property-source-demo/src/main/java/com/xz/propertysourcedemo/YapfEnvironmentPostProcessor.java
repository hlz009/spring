package com.xz.propertysourcedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 这种适合在配置中心使用
 * 将配置中心的文件写入到EnvironmentPostProcessor中，
 * 各子项目中可以实现EnvironmentPostProcessor接口，获取配置
 * @author xiaozhi009
 * @time 2019年8月27日下午6:13:55
 */
@Slf4j
public class YapfEnvironmentPostProcessor implements EnvironmentPostProcessor {
	private PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		MutablePropertySources propertySources = environment.getPropertySources();
		Resource resource = new ClassPathResource("yapf.properties");
		try {
			PropertySource ps = loader.load("YetAnotherPropertiesFile", resource).get(0);
			propertySources.addFirst(ps);
		} catch (Exception e) {
			log.error("Exception!", e);
		}
	}
	
}
