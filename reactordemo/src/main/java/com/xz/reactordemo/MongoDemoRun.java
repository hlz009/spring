package com.xz.reactordemo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.xz.reactordemo.converter.MoneyReadConverter;
import com.xz.reactordemo.converter.MoneyWriteConverter;
import com.xz.reactordemo.mode.NCoffee;

import lombok.extern.slf4j.Slf4j;
import reactor.core.scheduler.Schedulers;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
@Slf4j
public class MongoDemoRun implements CommandLineRunner{

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	private CountDownLatch cd1 = new CountDownLatch(2);

	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter(), 
												new MoneyWriteConverter()));
	}

	@Override
	public void run(String... args) throws Exception {
//		test();
	}

	public void test() throws InterruptedException {
		startFromInsertion(() -> {
			log.info("Runnable");
			decreaseHighPrice();
		});
		log.info("after starting");
		cd1.await();
	}
	
	private void startFromInsertion(Runnable runnable) {
		mongoTemplate.insertAll(initCoffee())
						.publishOn(Schedulers.elastic())
						.doOnNext(c -> log.info("Next: {}", c))
						.doOnComplete(runnable)
						.doFinally(s -> {
							cd1.countDown();
							log.info("Finally 1, {}", s);
						})
						.count()
						.subscribe(c -> log.info("Insert {} records", c));
	}

	private void decreaseHighPrice() {
		mongoTemplate.updateMulti(query(where("price").gte(3000L)),
						new Update().inc("price", -500L)
							.currentDate("updateTime"), NCoffee.class)
						.doFinally(s -> {
							cd1.countDown();
							log.info("Finally 2, {}", s);
						})
						.subscribe(r -> log.info("Result is {}", r));
	}

	private List<NCoffee> initCoffee() {
		NCoffee espresso = NCoffee.builder()
								.name("espresso")
								.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
								.createTime(new Date())
								.updateTime(new Date())
								.build();
		NCoffee latte = NCoffee.builder()
				.name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.createTime(new Date())
				.updateTime(new Date())
				.build();

		return Arrays.asList(espresso, latte);
	}
}
