package com.example.controller;

import com.example.common.Result;
import com.example.common.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

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
    public Result login(@RequestBody Account account) {
        Account dbAccount;
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            dbAccount = adminService.findByUsername(account);
        } else if (RoleEnum.STUDENT.name().equals(account.getRole())) {
            dbAccount = studentService.findByUsername(account);
        } else {
            return Result.error("角色错误");
        }

        return Result.success(dbAccount);
    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (ObjectUtils.isEmpty(account.getUsername()) || ObjectUtils.isEmpty(account.getPassword())) {
            // 账号或密码为空的情况
            return Result.error("账号或密码为空！");
        }
        studentService.register(account);
        return Result.success(account);
    }
}
