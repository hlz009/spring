package com.xz.logincontrol.constant;

/**
 * 字典接口
 * @author xiaozhi009
 *
 */
public interface Dictionary {
	String getCode();
	String getName();
	
	default boolean equalsCode(String code) {
		return getCode().equals(code);
	}
}
