package com.xz.logincontrol.exception.assertion;

import java.text.MessageFormat;

import com.xz.logincontrol.constant.IResponseEnum;
import com.xz.logincontrol.exception.BaseException;
import com.xz.logincontrol.exception.BusinessException;

/**
 * 业务异常断言
 * @author xiaozhi009
 * @time 2019年7月14日下午5:37:08
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {
	@Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMsg(), args);
        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMsg(), args);
        return new BusinessException(this, args, msg, t);
    }
}
