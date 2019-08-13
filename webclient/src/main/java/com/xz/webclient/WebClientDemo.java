package com.xz.webclient;

import java.util.concurrent.CountDownLatch;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.xz.webclient.model.Coffee;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Slf4j
public class WebClientDemo implements CommandLineRunner {
	@Autowired
	private WebClient webClient;

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8080").build();
	}

	@Override
	public void run(String... args) throws Exception {
		CountDownLatch cd1 = new CountDownLatch(2);
		
		webClient.get().uri("/coffee/{id}", 1)
					   .accept(MediaType.APPLICATION_JSON_UTF8)
					   .retrieve()
					   .bodyToMono(Coffee.class)
					   .doOnError(t -> log.error("Error:", t))
					   .doFinally(s -> cd1.countDown())
					   .subscribeOn(Schedulers.single())
					   .subscribe(c -> log.info("Coffee 1: {}", c));
	
		Mono<Coffee> americano = Mono.just(Coffee.builder()
											.name("americano")
											.price(Money.of(CurrencyUnit.of("CNY"), 25.00))
											.build());
		webClient.post().uri("/coffee")
						.body(americano, Coffee.class)
						.retrieve()
						.bodyToMono(Coffee.class)
						.doFinally(s -> cd1.countDown())
						.subscribeOn(Schedulers.single())
						.subscribe(c -> log.info("Coffee Created:{}", c));
		
		cd1.await();
		webClient.get().uri("/coffee/")
					   .retrieve()
					   .bodyToFlux(Coffee.class)
					   .toStream()
					   .forEach(c -> log.info("Coffee in List: {}", c));
	}
}
