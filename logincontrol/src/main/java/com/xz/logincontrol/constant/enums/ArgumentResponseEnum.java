package com.xz.logincontrol.constant.enums;

import com.xz.logincontrol.exception.assertion.ArgumentExceptionAssert;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 参数校验异常返回结果
 * @author xiaozhi009
 * @time 2019年7月14日下午6:04:46
 */
@Getter
@AllArgsConstructor
public enum ArgumentResponseEnum implements ArgumentExceptionAssert {
	VALID_ERROR(6000, "参数校验异常");

	private int code;
	private String msg;
}
