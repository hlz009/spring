package com.xz.cache.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.xz.cache.mode.Coffee;
import com.xz.cache.mode.CoffeeCache;
import com.xz.cache.repository.CoffeeCacheRepository;
import com.xz.cache.repository.CoffeeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * 演示redisRepository
 * @author xiaozhi009
 *
 */

@Slf4j
@Service
public class CoffeeService3 {
	@Autowired
    private CoffeeRepository coffeeRepository;
	@Autowired
	private CoffeeCacheRepository coffeeCacheRepository;


	public List<Coffee> findAllCoffee() {
		return coffeeRepository.findAll();
	}

    public Optional<Coffee> findSimpleCoffeeFromCache(String name) {
    	Optional<CoffeeCache> cached = coffeeCacheRepository.findOneByName(name);
        if (cached.isPresent()) {
        	CoffeeCache coffeeCache = cached.get();
        	Coffee coffee = Coffee.builder()
        			.name(coffeeCache.getName())
        			.price(coffeeCache.getPrice())
        			.build();
        	log.info("Coffee {} found in cache.", coffeeCache);
        	return Optional.of(coffee);
        }
        Optional<Coffee> coffee = findOneCoffee(name);
        coffee.ifPresent(c -> {
        	CoffeeCache coffeeCache = CoffeeCache.builder()
        			.id(c.getId())
        			.name(c.getName())
        			.price(c.getPrice())
        			.build();
        	log.info("Save Coffee {} to cache.", coffeeCache);
        	coffeeCacheRepository.save(coffeeCache);
        });
    	return coffee;
    }

    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", exact().ignoreCase());
        Optional<Coffee> coffee = coffeeRepository.findOne(
                Example.of(Coffee.builder().name(name).build(), matcher));
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }
}
