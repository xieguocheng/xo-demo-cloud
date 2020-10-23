package com.xo.common.annotation;

import com.xo.common.utils.SendMailUtil;

import java.lang.annotation.*;

/**
 * @author: xo
 * @DATE: 2020/9/25
 * @Description: SendMailAnnotation
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SendMailAnnotation {

    /**
     * 发送对象(默认发送对象843781279@QQ.com）
     */
    String[]  to() default SendMailUtil.DEFAULT_RECEIVE;

    /**
     * 主题
     */
    String subject() default "";

    /**
     * 发送内容
     */
    String content()default "";





}
