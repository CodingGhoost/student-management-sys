package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    private AdminService adminService;

    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin dbAdmin = adminService.findByUsername(admin);
        return Result.success(dbAdmin);
    }

}
