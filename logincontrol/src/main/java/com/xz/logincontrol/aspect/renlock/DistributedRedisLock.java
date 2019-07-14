package com.xz.logincontrol.aspect.renlock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DistributedRedisLock {

	@Autowired
	private RedissonClient redisson;
	/**
	 * 加锁
	 * @param lockName
	 * @return
	 */
    public RLock acquire(String key){
       //获取锁对象
        RLock mylock = redisson.getLock(key);
       //加锁，并且设置锁过期时间，防止死锁的产生
        mylock.lock(2, TimeUnit.MINUTES);
        log.error("======lock====== {} ", Thread.currentThread().getName());
       //加锁成功
        return  mylock;
    }

    public abstract void doService();

    /**
     * 释放锁
     * @param lockName
     */
    public void release(RLock mylock){
        if (mylock.isHeldByCurrentThread()) {
        	mylock.unlock();
            log.info("{} unlock", Thread.currentThread().getName());
        } else {
            log.info("{} already automatically release lock", Thread.currentThread().getName());
        }
    }

    public void run(String key) {
    	RLock mylock = acquire(key);
    	doService();
    	release(mylock);
    }
}