package com.xz.nosql.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.xz.nosql.model.Coffee2;
import com.xz.nosql.repository.CoffeeRepository2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames = "coffee") // 重复查询时，从缓存中查找
// cacheNames 指的是设定的缓存范围
public class CoffeeService {
	@Autowired
    private CoffeeRepository2 coffeeRepository;

    public Optional<Coffee2> findOneCoffee(String name) {
    	ExampleMatcher matcher = ExampleMatcher.matching()
    			.withMatcher("name", exact().ignoreCase());
    	Optional<Coffee2> coffee2 = coffeeRepository.findOne(
    			Example.of(Coffee2.builder().name(name).build(), matcher));
    	log.info("Coffee Found: {}", coffee2);
        return coffee2;
    }

    public List<Coffee2> findAllCoffee() {
    	return coffeeRepository.findAll();
    }
}
