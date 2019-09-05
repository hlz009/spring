package com.xz.webdemo.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.xz.webdemo.service.CoffeeService;

/**
 * 新建coffee健康检测
 * @author xiaozhi009
 * @time 2019年9月2日下午6:14:04
 */
@Component
public class CoffeeHealthIndicator implements HealthIndicator{
	@Autowired
	private CoffeeService coffeeService;

	@Override
	public Health health() {
		long count = coffeeService.getCoffeeCount();
		Health health;
		if (count > 0) {
			health = Health.up().withDetail("count", count)
					.withDetail("message", "We have enough coffee.")
					.build();
		} else {
			health = Health.up().withDetail("count", count)
					.withDetail("message", "We have no coffee.")
					.build();			
		}
		return health;
	}

}
