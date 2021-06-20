package com.xo.common.sql;


import com.xo.common.sql.bean.Student;
import com.xo.common.sql.core.JdbcTemplate;
import com.xo.common.sql.mapper.StudentRowMapper;

import java.util.List;

/**
 * User: XO
 * Date: 2020/10/12
 */
public class Test {

    public static Student get(String id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        List<Student> list = (List<Student>) JdbcTemplate.query(sql, new StudentRowMapper(),id);
        return list.size() > 0 ? list.get(0) : null;

    }

    public static void main(String[] args) throws Exception {
        Student student = get("1");
        System.out.println(student);
    }

}
