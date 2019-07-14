package com.xz.logincontrol.constant;
/**
 * 异常返回码枚举接口
 * @author xiaozhi009
 *
 */
public interface IResponseEnum {
	/**
	 * 获取返回码
	 * @return  状态码
	 */
	int getCode();
	
	/**
	 * 获取返回信息
	 * @return 信息字符串
	 */
	String getMsg();
}
