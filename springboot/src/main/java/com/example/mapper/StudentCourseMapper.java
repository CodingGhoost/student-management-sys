package com.example.mapper;

import com.example.entity.StudentCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface StudentCourseMapper {

    @Insert("insert into student_course (course_name, code, student_id, course_id) " +
            "values (#{courseName}, #{courseCode}, #{studentId}, #{courseId})")
    void insert(StudentCourse studentCourse);

    @Select("select * from student_course where student_id = #{studentId} and course_id = #{courseId}")
    StudentCourse selectByCondition(StudentCourse studentCourse);
}
