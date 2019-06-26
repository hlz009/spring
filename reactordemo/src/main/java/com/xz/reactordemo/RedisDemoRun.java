package com.xz.reactordemo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.xz.reactordemo.mode.Coffee;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class RedisDemoRun implements CommandLineRunner{

	private static final String KEY = "COFFEE_MENU";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ReactiveStringRedisTemplate redisTemplate;

	@Bean
	ReactiveStringRedisTemplate reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		return new ReactiveStringRedisTemplate(factory);
	}
	
	@Override
	public void run(String... args) throws Exception {
//		test();
	}

	public void test() throws InterruptedException {
		ReactiveHashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
		CountDownLatch cd1 = new CountDownLatch(1);

		List<Coffee> list = jdbcTemplate.query("select * from t_coffee", 
				(rs, i) -> Coffee.builder().id(rs.getLong("id"))
										.name(rs.getString("name"))
										.price(rs.getLong("price"))
										.build());
		
		Flux.fromIterable(list)
			.publishOn(Schedulers.single())
			.doOnComplete(() -> log.info("list ok"))
			.flatMap(c -> {
				log.info("try to put {}, {}", c.getName(), c.getPrice().toString());
				return hashOps.put(KEY, c.getName(), c.getPrice().toString());
			})
			.doOnComplete(() -> log.info("Set ok"))
			.concatWith(redisTemplate.expire(KEY, Duration.ofMinutes(1)))
			.doOnComplete(() -> log.info("expire ok"))
			.onErrorResume(e -> {
				log.error("exception {}", e.getMessage());
				return Mono.just(false);
			})
			.subscribe(b -> log.info("Boolean:{}", b),
					e -> log.error("Exception {}", e.getMessage()), 
					() -> cd1.countDown());
		log.info("Waiting");
		cd1.await();
	}
}
