package com.xz.cache;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;

import com.xz.cache.converter.BytesToMoneyConverter;
import com.xz.cache.converter.MoneyToBytesConverter;

import io.lettuce.core.ReadFrom;

@Configuration
public class RedisConfig {
	/**
	 * 返回的泛型类型T
	 * 可以扩展定义
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, T> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	/**
	 * 设置读取redis节点的类型
	 * @return
	 */
	@Bean
	public LettuceClientConfigurationBuilderCustomizer customizer() {
		// 优先读主节点
		return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);
	}

	/**
	 * 自定义映射类型
	 * @return
	 */
	@Bean
	public RedisCustomConversions redisCustomConversions() {
		return new RedisCustomConversions(
				Arrays.asList(new MoneyToBytesConverter(), new BytesToMoneyConverter()));
	}

}
