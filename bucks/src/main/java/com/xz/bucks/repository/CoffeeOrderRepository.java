package com.xz.bucks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xz.bucks.mode.CoffeeOrder;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long>{
//	List<CoffeeOrder> findByCustomerOrderById(String customer);
//	// 查询Item下的name
//    List<CoffeeOrder> findByItems_Name(String name);
}
