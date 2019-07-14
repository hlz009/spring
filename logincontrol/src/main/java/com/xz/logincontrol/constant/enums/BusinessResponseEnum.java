package com.xz.logincontrol.constant.enums;

import com.xz.logincontrol.exception.assertion.BusinessExceptionAssert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessResponseEnum implements BusinessExceptionAssert {
   BAD_LICENCE_TYPE(7001, "Bad licence type."),
   LICENCE_NOT_FOUND(7002, "Licence not found.")
   ;

   private int code;
   private String msg;
}
