package com.xz.webdemo.service;

import java.util.List;

import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.xz.webdemo.mode.Coffee;
import com.xz.webdemo.resposity.CoffeeRepository;

@Service
public class CoffeeService {
	@Autowired
    private CoffeeRepository coffeeRepository;

	public Coffee saveCoffee(String name, Money price) {
	    return coffeeRepository.save(Coffee.builder().name(name).price(price).build());
	}

    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public Coffee getCoffee(Long id) {
    	return coffeeRepository.getOne(id);
    }

    public Coffee getCoffee(String name) {
    	return coffeeRepository.findByName(name);
    }

    public List<Coffee> getCoffeeByName(List<String> names) {
    	return coffeeRepository.findByNameInOrderById(names);
    }

    public Long getCoffeeCount() {
    	return coffeeRepository.count();
    }
}
