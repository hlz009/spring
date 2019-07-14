package com.xz.logincontrol.constant.enums;

import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * servlet异常类
 * @author xiaozhi009
 * @time 2019年7月14日下午7:43:25
 */
@Getter
@AllArgsConstructor
public enum ServletResponseEnum {
    MethodArgumentNotValidException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    MethodArgumentTypeMismatchException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    MissingServletRequestPartException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    MissingPathVariableException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    BindException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    MissingServletRequestParameterException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    TypeMismatchException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    ServletRequestBindingException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    HttpMessageNotReadableException(4400, "", HttpServletResponse.SC_BAD_REQUEST),
    NoHandlerFoundException(4404, "", HttpServletResponse.SC_NOT_FOUND),
    NoSuchRequestHandlingMethodException(4404, "", HttpServletResponse.SC_NOT_FOUND),
    HttpRequestMethodNotSupportedException(4405, "", HttpServletResponse.SC_METHOD_NOT_ALLOWED),
    HttpMediaTypeNotAcceptableException(4406, "", HttpServletResponse.SC_NOT_ACCEPTABLE),
    HttpMediaTypeNotSupportedException(4415, "", HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE),
    ConversionNotSupportedException(4500, "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    HttpMessageNotWritableException(4500, "", HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    AsyncRequestTimeoutException(4503, "", HttpServletResponse.SC_SERVICE_UNAVAILABLE)
    ;
	
	private int code;
	private String msg;
	/** HTTP 状态码*/
	private int statusCode;
}
