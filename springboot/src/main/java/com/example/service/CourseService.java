package com.example.service;

import com.example.entity.Course;
import com.github.pagehelper.PageInfo;

public interface CourseService {

    public PageInfo<Course> selectPage(Integer pageNum, Integer pageSize, String name);
}
