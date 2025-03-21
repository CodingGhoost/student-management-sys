package com.example.service.impl;

import com.example.entity.StudentCourse;
import com.example.exception.CustomException;
import com.example.mapper.StudentCourseMapper;
import com.example.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    public void add(StudentCourse studentCourse) {
        StudentCourse course = studentCourseMapper.selectByCondition(studentCourse);
        if (course != null) {
            throw new CustomException("您已经选过这门课程！");
        }
        studentCourseMapper.insert(studentCourse);
    }
}
