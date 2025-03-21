package com.example.service;

import com.example.entity.StudentCourse;
import com.github.pagehelper.PageInfo;

public interface StudentCourseService {

    void add(StudentCourse studentCourse);

    PageInfo<StudentCourse> selectPage(Integer pageNum, Integer pageSize, StudentCourse studentCourse);

    void delete(Integer id);
}
