package com.example.mapper;

import com.example.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface CourseMapper {

    @Select("select * from courses where name like concat('%', #{name} ,'%')" +
            "and code like concat('%', #{code}, '%') " +
            "and instructor like concat('%', #{instructor}, '%') order by id desc")
    List<Course> selectAll(Course course);

    @Insert("insert into courses (name, code, lessons, description, instructor) " +
            "values (#{name}, #{code}, #{lessons}, #{description}, #{instructor})")
    void insert(Course course);

    @Update("update courses set name = #{name}, code = #{code}, lessons = #{lessons}," +
            "description = #{description}, instructor = #{instructor} where id = #{id}")
    void update(Course course);

    @Delete("delete from courses where id = #{id}")
    void delete(Integer id);
}
