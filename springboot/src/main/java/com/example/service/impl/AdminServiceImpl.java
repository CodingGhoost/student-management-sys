package com.example.service.impl;

import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Account findByUsername(Account account) {
        Account a = adminMapper.findByUsername(account.getUsername());
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
}
