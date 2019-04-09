package com.xz.nosql.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.xz.nosql.model.Coffee;

public interface CoffeeRepository extends MongoRepository<Coffee, String>{
	List<Coffee> findByName(String name);
}
