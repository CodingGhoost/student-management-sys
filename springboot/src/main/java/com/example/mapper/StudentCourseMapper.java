package com.example.mapper;

import com.example.entity.StudentCourse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentCourseMapper {

    @Insert("insert into student_course (course_name, course_code, student_id, course_id) " +
            "values (#{courseName}, #{courseCode}, #{studentId}, #{courseId})")
    void insert(StudentCourse studentCourse);

    @Select("select * from student_course where student_id = #{studentId} and course_id = #{courseId}")
    StudentCourse selectByCondition(StudentCourse studentCourse);

//    @Select("select * from student_course where course_name like concat('%', #{courseName}, '%') and course_code like concat('%', #{courseCode}, '%')" +
//            "and student_id = #{studentId}")
    List<StudentCourse> selectAll(StudentCourse studentCourse);

    @Delete("delete from student_course where id = #{id}")
    void delete(Integer id);
}
