package com.xo.common.utils;

import com.xo.common.enums.PosterTemplateXyNumberEnum;
import com.xo.common.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

@Slf4j
public class ExceptionUtils {

    /**
     * 对象相等
     */
    public static void isEqualsObject(Object obj1, Object obj2, String message) {
        if (Objects.equals(obj1, obj2)) {
            throw new MessageException(message);
        }
    }

    /**
     * 对象不相等
     */
    public static void isNotEqualsObject(Object obj1, Object obj2, String message) {
        if (!Objects.equals(obj1, obj2)) {
            throw new MessageException(message);
        }
    }

    /**
     * 对象是空
     **/
    public static void isNullObject(Object obj, String message) {
        if (StringUtils.isEmpty(obj)) {
            throw new MessageException(message);
        }
    }

    /**
     * 对象不是空
     */
    public static void isNotNullObject(Object obj, String message) {
        if (!StringUtils.isEmpty(obj)) {
            throw new MessageException(message);
        }
    }

    /**
     * 数组为空
     *
     * @param array
     * @param message
     * @return void
     * @author 小吴
     * @date 2020/02/19 0019 10:47
     */
    public static <T> void isNullArray(T[] array, String message) {
        if (array == null || array.length == 0) {
            throw new MessageException(message);

        }
    }

    /**
     * 集合为空
     **/
    public static void isEmptyCollection(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new MessageException(message);
        }
    }

    /**
     * 模板异常
     **/
    public static void posterTemplateTypeException(Integer type, String message) {
        throw new MessageException(EnumUtil.getByCode(type, PosterTemplateXyNumberEnum.class).getMessage() + message);
    }
}
