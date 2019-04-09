package com.xz.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import com.xz.cache.service.CoffeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @EnableJpaRepositories 在同一个项目中springboot会自动配置，不需手动添加该注解
 * 如果我们需要定义Entity和Repository不在应用程序所在包及其子包，
 * 就需要使用@EntityScan和@EnableJpaRepositories。
 *
 * @author xiaozhi009
 *
 */

@Component
@Slf4j
@EnableCaching(proxyTargetClass = true)
public class DemoRun implements CommandLineRunner {
	@Autowired
	private CoffeeService coffeeService;

	@Override
	public void run(String... args) throws Exception {
//		testRun();
	}

	private void testRun() throws InterruptedException {
		log.info("Count: {}", coffeeService.findAllCoffee().size());
		for (int i = 0; i < 5; i++) {
			log.info("Reading from cache.");
			coffeeService.findAllCoffee();
		}
		Thread.sleep(5000);
		log.info("Reading after refresh.");
		coffeeService.findAllCoffee().forEach(c -> log.info("Coffee {}", c.getName()));
	}

}
