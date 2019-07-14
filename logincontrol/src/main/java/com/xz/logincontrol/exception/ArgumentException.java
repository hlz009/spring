package com.xz.logincontrol.exception;

import java.io.Serializable;

import com.xz.logincontrol.constant.IResponseEnum;

/**
 * 参数异常
 * 在处理业务过程中校验参数出现错误，可以抛出该异常
 * 编写公共代码时，对传入参数检查不通过，可以抛出该异常
 * @author xiaozhi009
 *
 */
public class ArgumentException extends BaseException implements Serializable {

	private static final long serialVersionUID = 1L;

	public ArgumentException(IResponseEnum responseEnum, Object[] args, String msg) {
		super(responseEnum, args, msg);
	}

    public ArgumentException(IResponseEnum responseEnum, Object[] args, String msg, Throwable cause) {
        super(responseEnum, args, msg, cause);
    }
}
