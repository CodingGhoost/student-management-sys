package com.example.controller;

import com.example.common.Result;
import com.example.entity.StudentCourse;
import com.example.service.StudentCourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentCourse")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @PostMapping("/add")
    public Result add(@RequestBody StudentCourse studentCourse) {
        studentCourseService.add(studentCourse);
        return Result.success(studentCourse);
    }

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             StudentCourse studentCourse) {
        PageInfo<StudentCourse> studentCoursePage = studentCourseService.selectPage(pageNum, pageSize, studentCourse);
        return Result.success(studentCoursePage);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        studentCourseService.delete(id);
        return Result.success(id);
    }

}
