package com.xz.logincontrol.exception;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.xz.logincontrol.constant.enums.ArgumentResponseEnum;
import com.xz.logincontrol.constant.enums.CommonResponseEnum;
import com.xz.logincontrol.constant.enums.ServletResponseEnum;
import com.xz.logincontrol.filter.ApiResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理器
 * @author xiaozhi009
 * @time 2019年7月14日下午7:46:04
 */
@Slf4j
@ControllerAdvice
public class ExceptionAdvisor {
	private final static String ENV_PROD = "prod";
	/**
	 * 当前环境
	 */
	@Value("${spring.profile.active}")
	private String profile;
	
//	@ResponseBody
//	@ExceptionHandler(value=Exception.class)
////	@ResponseStatus // 该注解加上会默认加上500的错误
//	public ApiResult exceptionHandler(Exception e) {
//		return new ApiResult(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
//	}

	@ResponseBody
	@ExceptionHandler(value=RuntimeException.class)
	public ApiResult formatCheckExceptionHandler(RuntimeException e) {
		return new ApiResult(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
	}


    /**
     * 自定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ApiResult handleBaseException(BaseException e) {
        return new ApiResult(e.getResponseEnum().getCode(), e.getMessage(), null);
    }

    /**
     * 业务异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ApiResult handleBusinessException(BaseException e) {
        return new ApiResult(e.getResponseEnum().getCode(), e.getMessage(), null);
    }

    /**
     * Controller上一层相关异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    @ResponseBody
    public ApiResult handleServletException(Exception e) {
        int code = CommonResponseEnum.SERVER_ERROR.getCode();
        try {
            ServletResponseEnum servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
            code = servletExceptionEnum.getCode();
        } catch (IllegalArgumentException e1) {
            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResponseEnum.class.getName());
        }

        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
            code = CommonResponseEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
            return new ApiResult(code, baseException.getMessage());
        }
        return new ApiResult(code, e.getMessage());
    }

    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ApiResult handleBindException(BindException e) {
        log.error("参数绑定校验异常", e);
        return new ApiResult(ArgumentResponseEnum.VALID_ERROR.getCode(), ArgumentResponseEnum.VALID_ERROR.getMsg(), 
        		e.getBindingResult());
    }

    /**
     * 参数校验(Valid)异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = {
    		MethodArgumentNotValidException.class,
    		})
    @ResponseBody
    public ApiResult handleValidException(MethodArgumentNotValidException e) {
        log.error("参数绑定校验异常", e);
        return new ApiResult(ArgumentResponseEnum.VALID_ERROR.getCode(), ArgumentResponseEnum.VALID_ERROR.getMsg(), 
        		e.getBindingResult());
    }

    @ExceptionHandler(value = {
    		ArgumentException.class,
    		})
    @ResponseBody
    public ApiResult handleValidException(ArgumentException e) {
        log.error("参数校验异常", e);
        return new ApiResult(e.getResponseEnum().getCode(), e.getResponseEnum().getMsg(), 
        		wrapperBindingResult((BindingResult) e.getArgs()[0]));
    }
    
    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private String wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder data = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
        	data.append(", ");
            if (error instanceof FieldError) {
            	data.append(((FieldError) error).getField()).append(": ");
            }
            data.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return data.substring(2);
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult handleException(Exception e) {
        log.error(e.getMessage(), e);

        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
            int code = CommonResponseEnum.SERVER_ERROR.getCode();
            BaseException baseException = new BaseException(CommonResponseEnum.SERVER_ERROR);
            return new ApiResult(code, baseException.getMessage());
        }

        return new ApiResult(CommonResponseEnum.SERVER_ERROR.getCode(), e.getMessage());
    }
}
