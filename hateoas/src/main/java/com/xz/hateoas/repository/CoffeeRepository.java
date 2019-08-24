package com.xz.hateoas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.xz.hateoas.model.Coffee;

@RepositoryRestResource(path = "/coffee")
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
	List<Coffee> findByNameInOrderById(List<String> list);
	Coffee findByName(String name);
}
