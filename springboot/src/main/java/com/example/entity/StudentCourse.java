package com.example.entity;

import lombok.Data;

@Data
public class StudentCourse {

    private Integer id;

    private String courseName;

    private String courseCode;

    private Integer studentId;

    private Integer courseId;
}
