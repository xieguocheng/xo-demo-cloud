package com.xo.common.annotation;



import com.xo.common.annotation.validator.CollectionNotHasNullValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author NING MEI
 */
@Target({ANNOTATION_TYPE, METHOD, ElementType.FIELD}) //注解用于什么地方
@Retention(RUNTIME) //什么时候使用该注解
@Documented //注解是否将包含在JavaDoc中
@Constraint(validatedBy = CollectionNotHasNullValidatorImpl.class)// 此处指定了注解的实现类为ListNotHasNullValidatorImpl
public @interface CollectionNotHasNull {


    /**
     * 添加value属性，可以作为校验时的条件,若不需要，可去掉此处定义
     */
    int value() default 0;

    String message() default "集合中不能含有空元素";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 定义List，为了让Bean的一个属性上可以添加多套规则
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        CollectionNotHasNull[] value();
    }
}