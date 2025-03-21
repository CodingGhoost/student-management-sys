package com.example.service.impl;

import com.example.entity.StudentCourse;
import com.example.exception.CustomException;
import com.example.mapper.StudentCourseMapper;
import com.example.service.StudentCourseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Override
    public void add(StudentCourse studentCourse) {
        StudentCourse course = studentCourseMapper.selectByCondition(studentCourse);
        if (course != null) {
            throw new CustomException("您已经选过这门课程！");
        }
        studentCourseMapper.insert(studentCourse);
    }

    @Override
    public PageInfo<StudentCourse> selectPage(Integer pageNum, Integer pageSize, StudentCourse studentCourse) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentCourse> dataList = studentCourseMapper.selectAll(studentCourse);
        return PageInfo.of(dataList);
    }

    @Override
    public void delete(Integer id) {
        studentCourseMapper.delete(id);
    }
}
