package com.xz.logincontrol.filter;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.redisson.api.RBucket;
import org.redisson.api.RDeque;
import org.redisson.api.RLock;
import org.springframework.util.Assert;

import com.xz.logincontrol.pojo.CurrentUser;
import com.xz.logincontrol.pojo.UserBO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueueKickOutFilter extends KickOutFilter{

	/** 
	 * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	 */
	private boolean kickoutAfter = false;
	/**
	 * 同一个账号的最大会话数
	 */
	private int maxSession = 1;

	public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }
    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

	@Override
	public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader("Authorization");
        UserBO currentSession = CurrentUser.get();
        Assert.notNull(currentSession, "currentSession cannot null");
        String username = currentSession.getUsername();
        String userKey = PREFIX + "deque_" + username;
        String lockKey = PREFIX_LOCK + username;

        RLock lock = redissonClient.getLock(lockKey);

        lock.lock(2, TimeUnit.SECONDS);

        try {
            RDeque<String> deque = redissonClient.getDeque(userKey);
            // 如果队列里没有此token，且用户没有被踢出；放入队列
            if (!deque.contains(token) && currentSession.isKickout() == false) {
                deque.push(token);
            }

            // 如果队列里的sessionId数超出最大会话数，开始踢人
            while (deque.size() > maxSession) {
                String kickoutSessionId;
                if (kickoutAfter) { // 如果踢出后者
                    kickoutSessionId = deque.removeFirst();
                } else { // 否则踢出前者
                    kickoutSessionId = deque.removeLast();
                }
                try {
                    RBucket<UserBO> bucket = redissonClient.getBucket(kickoutSessionId);
                    UserBO kickoutSession = bucket.get();

                    if (kickoutSession != null) {
                        // 设置会话的kickout属性表示踢出了
                        kickoutSession.setKickout(true);
                        bucket.set(kickoutSession);
                    }
                } catch (Exception e) {
                }
            }
            log.info(CurrentUser.get().isKickout()+"");
            // 如果被踢出了，直接退出，重定向到踢出后的地址
            if (currentSession.isKickout()) {
                // 会话被踢出了
                try {
                    // 注销
                    userService.logout(token);
                    // 如果信息放入session中也记得清空session
                    sendJsonResponse(response, 4001, "您的账号已在其他设备登录");
                } catch (Exception e) {
                }
                return false;
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("{} unlock", Thread.currentThread().getName());

            } else {
                log.info("{} already automatically release lock", Thread.currentThread().getName());
            }
        }
        return true;
	}
	
}
