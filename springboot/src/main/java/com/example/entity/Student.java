package com.example.entity;

import lombok.Data;

@Data
public class Student extends Account {

    // id
    private Integer id;

    // 手机号码
    private String phone;

    // 邮箱
    private String email;

    // 性别
    private String sex;

    // 出生日期
    private String birthDate;

    // 头像（url链接）
    private String avatar;
}
