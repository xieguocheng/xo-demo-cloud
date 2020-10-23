package com.xo.common.annotation;

import java.lang.annotation.*;

/**
 * @author: xo
 * @DATE: 2020/9/3
 * @Description: TbSyncLog
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TbSyncLogAnnotation {

    /**
     * 类型:1-爬取 2-api同步 3-御善房
     */
    int type();

    /**
     * 表名
     */
    String tableName() ;



}
