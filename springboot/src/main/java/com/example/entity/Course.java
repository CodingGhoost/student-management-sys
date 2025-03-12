package com.example.entity;

import lombok.Data;

@Data
public class Course {

    // id
    private Integer id;

    // 课程名称
    private String name;

    // 课程编号
    private String code;

    // 课程描述
    private String description;

    // 课时
    private String lessons;

    // 任课老师
    private String instructor;

}
