package com.xo.common.nullObj;

/**
 * @author: xo
 * @DATE: 2020/9/25
 * @Description: Dependency
 **/
public class Dependency implements DependencyBase, Nullable {


    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public void operation() {
        System.out.print("Test!");
    }
}
