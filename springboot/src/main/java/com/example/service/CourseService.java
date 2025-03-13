package com.example.service;

import com.example.entity.Course;
import com.github.pagehelper.PageInfo;

public interface CourseService {

    PageInfo<Course> selectPage(Integer pageNum, Integer pageSize, Course course);

    void add(Course course);

    void update(Course course);

    void delete(Integer id);
}
