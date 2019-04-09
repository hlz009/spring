package com.xz.cache;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.xz.cache.mode.Coffee;
import com.xz.cache.service.CoffeeService3;

import lombok.extern.slf4j.Slf4j;

/**
 * redisRepository实例
 * @author xiaozhi009
 *
 */
@Component
@Slf4j
public class DemoRedisRepositoryRun implements CommandLineRunner {
	@Autowired
	private CoffeeService3 coffeeService;

	@Override
	public void run(String... args) throws Exception {
		Optional<Coffee> c = coffeeService.findSimpleCoffeeFromCache("mocha");
		log.info("Coffee {}", c);

		for (int i = 0; i < 5; i++) {
			c = coffeeService.findSimpleCoffeeFromCache("mocha");
		}

		log.info("Value from Redis: {}", c);
	}
}
