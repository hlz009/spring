package com.xz.webdemo.resposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xz.webdemo.mode.CoffeeOrder;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long>{
}
