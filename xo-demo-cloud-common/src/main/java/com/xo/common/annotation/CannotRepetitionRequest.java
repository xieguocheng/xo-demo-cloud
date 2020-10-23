package com.xo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jason
 * @description 防重复提交注解
 * @date 2019-09-18 11:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CannotRepetitionRequest {
    /**
     * 指定时间内不能重复提交，单位秒
     */
    int timeout() default 1;
}
