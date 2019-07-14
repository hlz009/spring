package com.xz.logincontrol.exception.assertion;

import java.text.MessageFormat;

import com.xz.logincontrol.constant.IResponseEnum;
import com.xz.logincontrol.exception.ArgumentException;
import com.xz.logincontrol.exception.BaseException;

public interface CommonExceptionAssert extends IResponseEnum, Assert {
    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMsg(), args);
        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMsg(), args);
        return new ArgumentException(this, args, msg, t);
    }

}
