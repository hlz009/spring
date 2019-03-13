package com.xz.bucks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xz.bucks.mode.Coffee;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>{
	 
}
