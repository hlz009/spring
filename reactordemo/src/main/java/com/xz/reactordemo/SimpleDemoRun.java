package com.xz.reactordemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class SimpleDemoRun implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
//		test();
	}
	
	public void test() throws InterruptedException {
		Flux.range(1, 6)
			.doOnRequest(n -> log.info("Request {} number", n))
			.publishOn(Schedulers.elastic()) // 
			.doOnComplete(() -> log.info("Publisher COMPLETE 1"))
			.map(i -> {
				log.info("Publish {}, {}", Thread.currentThread(), i);
				return 10/(i-3);
//				return i;
			})
			.doOnComplete(() -> log.info("Publisher COMPLETE 2"))
			.onErrorResume(e -> {
				log.error("Exception {}", e.toString());
				return Mono.just(-1);
			})
//			.onErrorReturn(-1)
			.subscribe(i -> log.info("Subscribe {} : {}", Thread.currentThread(), i), 
					e -> log.error("error {}", e.toString()),
					() -> log.info("Subscriber COMPLETE")//,
//					s -> s.request(4)
			);
		Thread.sleep(2000);//  等待2秒，所有结果会返回
	}
}
