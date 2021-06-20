package com.xo.common.sql.core;

import java.sql.ResultSet;
import java.util.List;

/**
 * User: XO
 * Date: 2020/10/12
 */
public interface IRowMapper<T> {

    /**
     * 处理结果集
     * @param rs
     * @return
     * @throws Exception
     */
    T mapping(ResultSet rs) throws Exception;

}
