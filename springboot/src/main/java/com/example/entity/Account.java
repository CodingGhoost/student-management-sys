package com.example.entity;

import lombok.Data;


// 所有账号类型公共父类


@Data
public class Account {

    private String username;

    private String password;

    private String name;

    private String role;
}
