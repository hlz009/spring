package com.xz.hateoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xz.hateoas.model.CoffeeOrder;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long>{
	
}
