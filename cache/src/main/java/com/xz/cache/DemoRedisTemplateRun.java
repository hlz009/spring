package com.xz.cache;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xz.cache.mode.Coffee;
import com.xz.cache.service.CoffeeService2;

import lombok.extern.slf4j.Slf4j;

/**
 * redisTemplate实例
 * @author xiaozhi009
 *
 */
@Component
@Slf4j
public class DemoRedisTemplateRun implements CommandLineRunner {
	@Autowired
	private CoffeeService2 coffeeService;

	@Override
	public void run(String... args) throws Exception {
		Optional<Coffee> c = coffeeService.findOneCoffee("mocha");
		log.info("Coffee {}", c);

		for (int i = 0; i < 5; i++) {
			c = coffeeService.findOneCoffee("mocha");
		}

		log.info("Value from Redis: {}", c);	}

}
