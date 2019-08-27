package com.xz.autoconfiguredemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.xz.greeting.GreetingApplication;

@SpringBootApplication
/** 不加ComponentScan默认只搜索当前目录下的文件
 *  如果是自动配置时会自动扫描，只需要将路径放入到spring.factories中 */
//@ComponentScan("com.xz.*")
public class AutoconfigureDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoconfigureDemoApplication.class, args);
	}

	// 创建自定义的bean
	@Bean
	public GreetingApplication greetingApplication() {
		return new GreetingApplication("calvin");
	}
}
