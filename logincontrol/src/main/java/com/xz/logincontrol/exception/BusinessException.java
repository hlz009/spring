package com.xz.logincontrol.exception;

import java.io.Serializable;

import com.xz.logincontrol.constant.IResponseEnum;

/**
 * 业务异常
 * 业务处理时，出现异常，可以抛出该异常
 * @author xiaozhi009
 * @time 2019年7月8日下午2:44:59
 */
public class BusinessException extends BaseException implements Serializable {

	private static final long serialVersionUID = 1L;

	public BusinessException(IResponseEnum responseEnum, Object[] args, String msg) {
        super(responseEnum, args, msg);
    }

    public BusinessException(IResponseEnum responseEnum, Object[] args, String msg, Throwable cause) {
        super(responseEnum, args, msg, cause);
    }
}
