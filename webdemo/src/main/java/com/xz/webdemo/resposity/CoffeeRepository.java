package com.xz.webdemo.resposity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xz.webdemo.mode.Coffee;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>{
	List<Coffee> findByNameInOrderById(List<String> list);
    Coffee findByName(String name);
}
