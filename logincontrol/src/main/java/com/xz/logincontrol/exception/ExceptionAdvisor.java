package com.xz.logincontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.xz.logincontrol.filter.ApiResult;

@ControllerAdvice
public class ExceptionAdvisor {
	@ResponseBody
	@ExceptionHandler(value=Exception.class)
	@ResponseStatus
	public ApiResult exceptionHandler(Exception e) {
		return new ApiResult(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
	}

	@ResponseBody
	@ExceptionHandler(value=RuntimeException.class)
	@ResponseStatus
	public ApiResult formatCheckExceptionHandler(RuntimeException e) {
		return new ApiResult(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
	}
}
