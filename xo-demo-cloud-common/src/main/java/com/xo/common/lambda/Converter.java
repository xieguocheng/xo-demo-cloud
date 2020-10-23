package com.xo.common.lambda;

/**
 * @author: xo
 * @DATE: 2020/9/21
 * @Description: F:from,T:to
 **/
@FunctionalInterface
public interface Converter<F, T> {


    /**
     * 函数式接口，类型转换
     * @param from
     * @return
     */
    T convert(F from);

}
