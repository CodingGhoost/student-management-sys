package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StudentMapper {

    @Select("select * from students where username like concat('%', #{username} ,'%')" +
            "and name like concat('%', #{name}, '%') order by id desc")
    List<Student> selectAll(Student student);

    @Select("select * from students where username = #{username}")
    Student findByUsername(String username);

    @Insert("insert into students (username, password, name, phone, email, sex, birth_date, avatar, role)" +
            "values (#{username}, #{password}, #{name}, #{phone}, #{email}, #{sex}, #{birthDate}, #{avatar}, #{role})")
    void insert(Student student);



    @Delete("delete from students where id = #{id}")
    void deleteById(Integer id);

    @Update("update students set username = #{username}, password = #{password}, name = #{name}, phone = #{phone}" +
            ", email = #{email}, sex = #{sex}, birth_date = #{birthDate}, avatar = #{avatar}, role = #{role} where id = #{id}")
    void update(Student student);
}
