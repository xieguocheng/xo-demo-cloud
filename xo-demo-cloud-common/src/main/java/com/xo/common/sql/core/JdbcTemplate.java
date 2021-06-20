package com.xo.common.sql.core;


import com.xo.common.sql.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * User: XO
 * Date: 2020/10/12
 */
public class JdbcTemplate {

    /**
     * 普通查询返回结果集合
     * @param sql
     * @param rowMapper
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T query(String sql, IRowMapper<T> rowMapper, Object ...params){
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn = JdbcUtil.getConnection();
            ps=conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1,params[i]);
            }
            rs = ps.executeQuery();
            return rowMapper.mapping(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(rs,ps,conn);
        }
        return null;
    }












}
