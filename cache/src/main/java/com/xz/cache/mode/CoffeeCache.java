package com.xz.cache.mode;

import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于redis coffee缓存实体
 * @author xiaozhi009
 *
 */
@RedisHash(value="springbucks-coffee", timeToLive=60)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeCache {
	@Id
	private Long id;
	/**
	 * 	二级索引，比如存入hash值时第二层嵌套的数据
		"springbucks-coffee:4:idx"
		"springbucks-coffee:name:mocha"
		"springbucks-coffee:4:phantom"
	 */
	@Indexed
	private String name;
	private Money price;
}
