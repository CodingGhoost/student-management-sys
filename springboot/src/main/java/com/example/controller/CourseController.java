package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.service.CourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize,
                             Course course) {
        PageInfo<Course> coursePage = courseService.selectPage(pageNum, pageSize, course);
        return Result.success(coursePage);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Course course) {
        courseService.add(course);
        return Result.success(course);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Course course) {
        courseService.update(course);
        return Result.success(course);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        courseService.delete(id);
        return Result.success(id);
    }

}
