package com.xo.common.nullObj;

/**
 * @author: xo
 * @DATE: 2020/9/25
 * @Description: Test
 **/
public class Test {


    public static void test(DependencyBase dependencyBase){
        Factory.get(dependencyBase).operation();
    }


    public static void main(String[] args) {
        //参考：https://mp.weixin.qq.com/s/WBUlPBmuU4f2LqRgDul86Q
        test(null);
    }

}
