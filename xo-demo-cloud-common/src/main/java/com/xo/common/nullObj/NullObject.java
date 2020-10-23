package com.xo.common.nullObj;

/**
 * @author: xo
 * @DATE: 2020/9/25
 * @Description: 这是空对象，对原有对象的行为进行了空实现。
 **/
public class NullObject implements DependencyBase{

    @Override
    public void operation() {
        // do nothing
    }

    @Override
    public boolean isNull() {
        return true;
    }

}