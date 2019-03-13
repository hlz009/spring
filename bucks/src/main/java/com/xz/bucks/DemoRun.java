package com.xz.bucks;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.xz.bucks.mode.Coffee;
import com.xz.bucks.mode.CoffeeOrder;
import com.xz.bucks.mode.OrderState;
import com.xz.bucks.service.CoffeeOrderService;
import com.xz.bucks.service.CoffeeService;

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
public class DemoRun implements CommandLineRunner {
	@Autowired
	private CoffeeService coffeeService;
	@Autowired
	private CoffeeOrderService orderService;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		testOrder();
	}

	private void testOrder() {
		log.info("All Coffee: {}", coffeeService.findAll());

		Optional<Coffee> latte = coffeeService.findOneCoffee("Latte");
		if (latte.isPresent()) {
			CoffeeOrder order = orderService.createOrder("Li Lei", latte.get());
			log.info("Update INIT to PAID: {}", orderService.updateState(order, OrderState.PAID));
			log.info("Update PAID to INIT: {}", orderService.updateState(order, OrderState.INIT));
		}
	}

//	private String getJoinedOrderId(List<CoffeeOrder> list) {
//		return list.stream().map(order -> order.getId().toString())
//				.collect(Collectors.joining(","));
//	}
}
