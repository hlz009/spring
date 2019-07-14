package com.xz.logincontrol.exception;

import java.io.Serializable;

import com.xz.logincontrol.constant.IResponseEnum;

/**
 * 校验异常
 * 调用接口时，参数格式不合法可以抛出该异常
 * @author xiaozhi009
 * @time 2019年7月8日下午2:47:49
 */
public class ValidationException extends BaseException implements Serializable {
	private static final long serialVersionUID = 1L;

    public ValidationException(IResponseEnum responseEnum, Object[] args, String msg) {
        super(responseEnum, args, msg);
    }

    public ValidationException(IResponseEnum responseEnum, Object[] args, String msg, Throwable cause) {
        super(responseEnum, args, msg, cause);
    }	
}
