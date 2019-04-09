package com.xz.cache.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xz.cache.mode.Coffee;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>{
	 
}
