package com.xz.cache.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xz.cache.mode.CoffeeOrder;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long>{
	
}
