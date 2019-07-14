package com.xz.logincontrol.exception;

import java.io.Serializable;

import com.xz.logincontrol.constant.IResponseEnum;

import lombok.Getter;

/**
 * 基础异常类
 * @author xiaozhi009
 * @time 2019年7月8日下午2:37:09
 */
@Getter
public class BaseException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 返回码
	 */
	protected IResponseEnum responseEnum;
	/**
	 * 异常消息参数
	 */
	protected Object[] args;

	public BaseException(IResponseEnum responseEnum) {
	    super(responseEnum.getMsg());
	    this.responseEnum = responseEnum;
	}

	public BaseException(int code, String msg) {
		super(msg);
		this.responseEnum = new IResponseEnum() {
			@Override
			public String getMsg() {
				return msg;
			}
			
			@Override
			public int getCode() {
				return code;
			}
		};
	}

	public BaseException(IResponseEnum responseEnum, Object[] args, String msg) {
        super(msg);
        this.responseEnum = responseEnum;
        this.args = args;
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String msg, 
    		Throwable cause) {
        super(msg, cause);
        this.responseEnum = responseEnum;
        this.args = args;
    }
}
