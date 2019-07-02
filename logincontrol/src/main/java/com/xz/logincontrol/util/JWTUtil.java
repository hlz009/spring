package com.xz.logincontrol.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

/**
 * 时间戳比较工具类
 * @author xiaozhi009
 *
 */
@Slf4j
public class JWTUtil {
	
	public static final long EXPIRE_TIME_MS = 604800*1000; // 7天
	public static final String SECRET = "SECRET";

	/**
	 * 校验token是否正确
	 * @param token  密钥
	 * @param secret 用户密码
	 * @return
	 */
	public static boolean vertify(String token, String secret) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 验证JWT是否被篡改，是则抛异常
			algorithm.verify(JWT.decode(token));
			return true;
		} catch (Exception e) {
			log.info("签名更改, {}", e.getMessage());
		}
		return false;
	}

	/**
	 * 获取token中的username信息（无需secret解密）
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch(JWTDecodeException e) {
			return null;
		}
	}

	public static Claim getClaim(String token, String key) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim(key);
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名，默认有效期7天
	 * @param username  用户名
	 * @param secret   用户密码
	 * @return
	 */
	public static String sign(String username, String secret) {
		try {
			Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME_MS);
			Algorithm algorithm = Algorithm.HMAC256(secret);
			// 附带username信息
			return JWT.create()
					.withClaim("username", username)
					.withClaim("createTime", System.currentTimeMillis())
					.withExpiresAt(date)
					.sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 验证token是否过期
	 * true-未过期
	 * @param token
	 * @return
	 */
	public static boolean vertifyExpire(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getExpiresAt().compareTo(new Date()) == -1;
	}
}
