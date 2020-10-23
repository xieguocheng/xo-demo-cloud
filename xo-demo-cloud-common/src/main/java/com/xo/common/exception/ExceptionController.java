package com.xo.common.exception;

import com.xo.common.utils.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class})
    public CommonResult handleParameterException(Exception e) {
        return CommonResult.requestError(String.format("参数错误：%s", e.getMessage()));
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        return getCommonResultByBindingResult(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public CommonResult handleBindException(BindException e) {
        BindingResult result = e.getBindingResult();
        return getCommonResultByBindingResult(result);
    }

    private CommonResult getCommonResultByBindingResult(BindingResult result) {
        FieldError error = result.getFieldError();
        if (error == null) {
            return CommonResult.requestError("参数验证失败");
        }
        String message = error.getDefaultMessage();
        if (StringUtils.isEmpty(message)) {
            return CommonResult.requestError("参数验证失败");
        }
        log.error("参数验证失败：{}", message);
        return CommonResult.requestError(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        if (StringUtils.isEmpty(message)) {
            return CommonResult.requestError("参数验证失败");
        }
        log.error("参数验证失败：{}", message);
        return CommonResult.requestError(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public CommonResult handleValidationException(ValidationException e) {
        log.error("参数验证失败：{}", e.toString());
        return CommonResult.requestError("参数验证失败");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法：{}", e.toString());
        return CommonResult.requestError("不支持当前请求方法");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public CommonResult handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("不支持当前媒体类型：{}", e.toString());
        return CommonResult.requestError("不支持当前媒体类型");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public CommonResult exception(Exception e) {
        log.error("出现异常", e);
        return CommonResult.serverError(String.format("出现异常：%s", e.getMessage()));
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MessageException.class)
    public CommonResult messageException(MessageException e) {
        return CommonResult.requestError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(FrontMessageException.class)
    public CommonResult frontMessageException(FrontMessageException e) {
        return CommonResult.serverError(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BadSqlGrammarException.class)
    public CommonResult badSqlGrammarException(BadSqlGrammarException e) {
        log.error("sql错误", e);
        return CommonResult.serverError("sql语法错误，请联系后端处理");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NeedBindWxException.class)
    public CommonResult needBindWxException(NeedBindWxException e) {
        return CommonResult.bindWx();
    }

}
