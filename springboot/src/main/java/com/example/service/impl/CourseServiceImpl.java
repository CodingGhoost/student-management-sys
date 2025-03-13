package com.example.service.impl;

import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.example.service.CourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public PageInfo<Course> selectPage(Integer pageNum, Integer pageSize, Course course) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> dataList = courseMapper.selectAll(course);
        return PageInfo.of(dataList);
    }

    @Override
    public void add(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void update(Course course) {
        courseMapper.update(course);
    }
}
