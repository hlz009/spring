package com.xz.logincontrol.constant.enums;

import com.xz.logincontrol.exception.BaseException;
import com.xz.logincontrol.exception.assertion.CommonExceptionAssert;
import com.xz.logincontrol.filter.ApiResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponseEnum implements CommonExceptionAssert {
	SUCCESS(0, "SUCCESS"),
	SERVER_BUSY(9998, "服务器繁忙"),
	SERVER_ERROR(9999, "网络异常");

	private int code;
	private String msg;

	public static void assertSuccess(ApiResult res) {
		SERVER_ERROR.assertNotNull(res);
		int code = res.getCode();
		if (CommonResponseEnum.SUCCESS.getCode() != code) {
			throw new BaseException(code, res.getMsg());
		}
	}
}
