package com.xo.common.sql.mapper;


import com.xo.common.sql.bean.Student;
import com.xo.common.sql.core.IRowMapper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * User: XO
 * Date: 2020/10/12
 */
public class StudentRowMapper implements IRowMapper {

    public List<Student> mapping(ResultSet rs) throws Exception {
        List<Student> list = new ArrayList<Student>();
        while (rs.next()){
            String id = rs.getString("id");
            String name = rs.getString("name");
            Student student = new Student(id, name);
            list.add(student);
        }
        return list;
    }

}
