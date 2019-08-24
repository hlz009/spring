package com.xz.sessionredis.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = {"myCache"})
@Service
public class TestService {
	@Cacheable(key = "targetClass + methodName +#p0")
    public String getV(String uid) {
		//TODO 查询数据库
		System.out.println("查询数据库");
        return "emp";
    }
}
