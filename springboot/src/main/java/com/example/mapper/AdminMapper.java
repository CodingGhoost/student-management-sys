package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper {
    @Select("select * from admins where username = #{username}")
    Admin findByUsername(String username);
}
