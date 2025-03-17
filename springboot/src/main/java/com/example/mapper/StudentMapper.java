package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.Select;

public interface StudentMapper {

    @Select("select * from students where username = ${username}")
    Student findByUsername(String username);
}
