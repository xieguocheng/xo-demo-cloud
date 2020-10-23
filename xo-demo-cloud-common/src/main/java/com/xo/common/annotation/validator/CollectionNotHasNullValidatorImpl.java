package com.xo.common.annotation.validator;

import com.xo.common.annotation.CollectionNotHasNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class CollectionNotHasNullValidatorImpl implements ConstraintValidator<CollectionNotHasNull, Collection> {

    private int value;

    //initialize和isValid，第一个是初始化方法，第二个是验证的逻辑方法，返回true,则验证通过，否则则不通过。
    //这个自定义注解逻辑处理类由于实现了ConstraintValidator接口，所以它默认被spring管理成bean,
    // 所以可以在这个逻辑处理类里面用@Autowired或者@Resources注入别的服务，而且不用在类上面用@Compent注解成spring的bean.

    @Override
    public void initialize(CollectionNotHasNull constraintAnnotation) {
        //传入value 值，可以在校验中使用
        this.value = constraintAnnotation.value();
    }

    public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
        if (CollectionUtils.isEmpty(collection)) {
            return true;
        }
        for (Object object : collection) {
            if (StringUtils.isEmpty(object)) {
                //如果集合中含有空元素，校验失败
                return false;
            }
        }
        return true;
    }
}