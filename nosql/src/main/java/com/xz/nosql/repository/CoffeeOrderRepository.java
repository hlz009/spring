package com.xz.nosql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xz.nosql.model.CoffeeOrder;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long>{
	
}
