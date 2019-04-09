package com.xz.nosql;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.UpdateResult;
import com.xz.nosql.converter.MoneyReadConverter;
import com.xz.nosql.model.Coffee;
import com.xz.nosql.repository.CoffeeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi009
 *
 */

@Component
@Slf4j
public class DemoRun implements CommandLineRunner {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CoffeeRepository coffeeRepository;
	
	@Bean
	public MongoCustomConversions mongoCustomConversions() {
		return new MongoCustomConversions(Arrays.asList(new MoneyReadConverter()));
	}

	@Override
	public void run(String... args) throws Exception {
		templateDemo();
//		repositoryDemo();
	}

	private void repositoryDemo() throws Exception {
		Coffee espresso = Coffee.builder()
				.name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.createTime(new Date())
				.updateTime(new Date()).build();
		Coffee latte = Coffee.builder()
				.name("latte")
				.price(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.createTime(new Date())
				.updateTime(new Date()).build();
		coffeeRepository.insert(Arrays.asList(espresso, latte));
		coffeeRepository.findAll(Sort.by("name"))
						.forEach(c-> log.info("Saved Coffe {}", c));
		Thread.sleep(2000);
		latte.setPrice(Money.of(CurrencyUnit.of("CNY"), 35.0));
		latte.setUpdateTime(new Date());
		coffeeRepository.save(latte);
		coffeeRepository.findByName("latte").forEach(c -> log.info("Coffee {}", c));
		coffeeRepository.deleteAll();
	}

	private void templateDemo() throws Exception {
		Coffee espresso = Coffee.builder().name("espresso")
				.price(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.createTime(new Date())
				.updateTime(new Date()).build();
		Coffee saved = mongoTemplate.save(espresso);
		log.info("Coffee {}", saved);

		List<Coffee> list = mongoTemplate.find(
				Query.query(Criteria.where("name").is("espresso")),
				Coffee.class);
		log.info("Find {} Coffee", list.size());
		
		Thread.sleep(2000); // wait for seeing updatetime
		UpdateResult result = mongoTemplate.updateFirst(
				Query.query(Criteria.where("name").is("espresso")), 
				new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30)),
				Coffee.class);
		//  也可以采用以下写法
//		Coffee queryEspresso = Coffee.builder().name("espresso").build();
//		UpdateResult result = mongoTemplate.updateFirst(
//				Query.query(Criteria.byExample(Example.of(queryEspresso))),
//				new Update().set("price", Money.ofMajor(CurrencyUnit.of("CNY"), 30)),
//				Coffee.class);

		log.info("Update {} Coffee", result.getModifiedCount());
		
		Coffee updateOne = mongoTemplate.findById(saved.getId(), Coffee.class);
		log.info("Update Result: {}", updateOne);
		mongoTemplate.remove(updateOne);
	}
}
