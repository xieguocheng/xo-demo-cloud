package com.xo.common.nullObj;

/**
 * @author: xo
 * @DATE: 2020/9/25
 * @Description: Factory
 **/
public class Factory {

    /**
     * 在使用时，可以通过工厂调用方式来进行空对象的调用，也可以通过其他如反射的方式对对象进行调用（一般多耗时几毫秒）在此不进行详细叙述。
     * @param dependencyBase
     * @return
     */
    public static DependencyBase get(Nullable dependencyBase){
        if (dependencyBase == null){
            return new NullObject();
        }
        return new Dependency();
    }

}
