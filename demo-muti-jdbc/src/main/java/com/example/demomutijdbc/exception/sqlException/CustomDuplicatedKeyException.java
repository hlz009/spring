package com.example.demomutijdbc.exception.sqlException;

import org.springframework.dao.DuplicateKeyException;

/**
 * 自定义主键重复异常
 * @author xiaozhi009
 *
 */
public class CustomDuplicatedKeyException extends DuplicateKeyException {

	private static final long serialVersionUID = 1L;

	public CustomDuplicatedKeyException(String msg) {
	    super(msg);
	}

    public CustomDuplicatedKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
