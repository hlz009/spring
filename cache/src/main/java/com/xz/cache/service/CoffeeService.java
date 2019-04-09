package com.xz.cache.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.xz.cache.mode.Coffee;
import com.xz.cache.repository.CoffeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames = "coffee")
public class CoffeeService {
	@Autowired
    private CoffeeRepository coffeeRepository;

    public Optional<Coffee> findOneCoffee(String name) {
    	ExampleMatcher matcher = ExampleMatcher.matching()
    			.withMatcher("name", exact().ignoreCase());
    	Optional<Coffee> coffee = coffeeRepository.findOne(
    			Example.of(Coffee.builder().name(name).build(), matcher));
    	log.info("Coffee Found: {}", coffee);
        return coffee;
    }

    @CacheEvict
    public void reloadCoffee() {
    }
    
//    @Cacheable  默认
    // 可配的参数有： condition：满足条件  key：缓存名
    @Cacheable(value="mycache", keyGenerator="CacheKeyGenerator")
    public List<Coffee> findAllCoffee() {
    	return coffeeRepository.findAll();
    }
}
