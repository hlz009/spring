package com.xz.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

/**
 * 自定义redis缓存后缀设置规则
 * @Cacheable()中指定参数key
 * @author xiaozhi009
 *
 */
@Component("CacheKeyGenerator")
public class CacheKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
//		return new SimpleKeyGenerator();
		return "";// 什么规则都没有，返回""即可
	}

}
