package com.xz.authWeb.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xz.authWeb.Enum.BasicClassType;
import com.xz.authWeb.util.signature.MyBeanUtil;
import com.xz.authWeb.util.signature.SignatureUtil;

/**
 * 签名切面
 * @author Huliangzhi
 * @date: 2019年12月5日 下午6:01:51
 */
@Aspect
@Component
public class SignAspect {
	
	@Autowired
	private SignatureUtil signToolManage;

	@Pointcut("@within(com.xz.authWeb.annotation.SignAnnotation)")
	public void serviceAspect() {
	}

	@Before("serviceAspect()")
	public void beforeMethod(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (null == args || args.length == 0) {
			// 无参数，默认暂时不校验
			return;
		}
//		String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames(); // 参数名
//		Map<String, Object> paraMap = generateMap(argNames, args);
//		Arrays.sort(argNames);
		StringBuilder buffer = new StringBuilder();
		String signature = null;
		for (Object arg: args) {
			if (arg instanceof HttpServletRequest) {
				signature = ((HttpServletRequest) arg).getHeader("signature");
			} else {
				// 参数
				if (null != arg) {
					if (isBasicClassType(arg)) {// 基本类型
						buffer.append(arg);
						buffer.append("|");
					} else {
						// 非基本类型，直接转成json字符串
						TreeMap<String, Object> treeMap = MyBeanUtil.transBean2TreeMap(arg);
						List<Object> ObjList = new ArrayList<>(treeMap.values());
						for (Object obj:ObjList) {
							buffer.append(obj);
							buffer.append("|");
						}
					}
				}
			}
		}
		if (null == signature) {
			// 如果沒有从request对象中Header拿
			HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			signature = request.getHeader("signature");
			if (null == signature) {
//				throw new BusinessException("签名不存在！",BusinessCodeEnum.UNAUTHORIZED.code);
				throw new RuntimeException("签名不存在！");
			}
		}
		check(signature, buffer.toString());
	}

	@After("serviceAspect()")
	public void afterMethod(JoinPoint joinPoint) {
	}
	
	private void check(String signData,String toPayData) {
		//验签
		try {
			signToolManage.verifySign(signData, toPayData, null);
			System.out.println("验签成功！");
		} catch (Exception e) {
			e.printStackTrace();
//			throw new BusinessException("签名验证不通过！",BusinessCodeEnum.UNAUTHORIZED.code);
			throw new RuntimeException("签名验证不通过！");
		}

	}

	private boolean isBasicClassType(Object obj) {
		if (null == obj) {
			return false;
		}
		for (BasicClassType basicClassType: BasicClassType.values()) {
			System.out.println(obj.getClass().getName());
			if (obj.getClass().getName().equals(basicClassType.getType())) {
				return true;
			}
		}
		return false;
	}

	private Map<String, Object> generateMap(String[] keys, Object[] values) {
		Map<String, Object> paraMap = new HashMap<>();
		for (int i=0, len = keys.length; i<len; i++) {
			paraMap.put(keys[i], values[i]);
		}
		return paraMap;
	}
}
