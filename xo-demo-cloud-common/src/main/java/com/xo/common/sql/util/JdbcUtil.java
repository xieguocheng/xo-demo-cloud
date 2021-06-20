package com.xo.common.sql.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * User: XO
 * Date: 2020/10/12
 */
public class JdbcUtil {


//    //数据库地址
//    private static String URL="jdbc:mysql://127.0.0.1:3306/xo?serverTimezone=GMT%2B8&autoReconnect=true&useUnicode=true&characterEncoding=utf-8&&allowMultiQueries=true&useSSL=true";
//    //取得驱动程序
//    private static String DRIVER="com.mysql.jdbc.Driver";
//    //取得用户
//    private static String USER="root";
//    //登录密码
//    private static String PASSWORD="123456";

    private JdbcUtil(){

    }

    private static Properties properties = null;

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = loader.getResourceAsStream("db.properties");
            properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("DRIVER"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(properties.getProperty("URL"), properties.getProperty("USER"), properties.getProperty("PASSWORD"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(ResultSet rs, Statement st, Connection conn) {
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(st!=null){
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    if(conn!=null){
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


}


