package com.example.service;

import com.example.entity.Account;
import com.example.entity.Student;
import com.github.pagehelper.PageInfo;

public interface StudentService {

    Account findByUsername(Account account);

    void add(Student student);

    void register(Account account);

    void deleteById(Integer id);

    void update(Student student);

    PageInfo<Student> selectPage(Integer pageNum, Integer pageSize, Student student);
}
