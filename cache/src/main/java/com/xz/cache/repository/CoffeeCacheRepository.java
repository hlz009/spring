package com.xz.cache.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.xz.cache.mode.CoffeeCache;

/**
 * 查询coffee缓存实体
 * @author xiaozhi009
 *
 */
public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache, Long>{
	Optional<CoffeeCache> findOneByName(String name);
}
