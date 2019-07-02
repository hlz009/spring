package com.xz.logincontrol.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xz.logincontrol.pojo.UserBO;
import com.xz.logincontrol.util.JWTUtil;

@Service
public class UserService {

	@Autowired
	private RedissonClient redissonClient;

    public String buildUserInfo(UserBO user) {
        String username = user.getUsername();
        String jwt = JWTUtil.sign(username, JWTUtil.SECRET);
        Assert.notNull(jwt, "jwt cannot null");
        RBucket rBucket = redissonClient.getBucket(jwt);
        rBucket.set(user, JWTUtil.EXPIRE_TIME_MS, TimeUnit.MILLISECONDS);
        return jwt;
    }

    public void logout(String jwt) {
        RBucket rBucket = redissonClient.getBucket(jwt);
        rBucket.delete();
    }
}
