package com.example.service.impl;

import com.example.common.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Student;
import com.example.exception.CustomException;
import com.example.mapper.StudentMapper;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Account findByUsername(Account account) {
        Account a = studentMapper.findByUsername(account.getUsername());
        if (a == null) {
            // 用户不存在
            throw new CustomException("账号或密码错误");
        }

        if (!account.getPassword().equals(a.getPassword())) {
            // 密码错误
            throw new CustomException("账号或密码错误");
        }
        return a;
    }

    @Override
    public void register(Account account) {
        String username = account.getUsername();
        if (studentMapper.findByUsername(username) != null) {
            // 用户已经存在
            throw new CustomException("账号已存在");
        }

        Student student = new Student();
        student.setUsername(username);
        student.setPassword(account.getPassword());
        student.setName(username); // 注册时使用学号作为学生默认姓名
        student.setRole(RoleEnum.STUDENT.name());

        studentMapper.insert(student);
    }
}
