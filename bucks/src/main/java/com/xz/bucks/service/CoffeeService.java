package com.xz.bucks.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.xz.bucks.mode.Coffee;
import com.xz.bucks.repository.CoffeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
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

    public List<Coffee> findAll() {
    	return coffeeRepository.findAll();
    }
}
