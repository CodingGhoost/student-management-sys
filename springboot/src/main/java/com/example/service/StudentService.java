package com.example.service;

import com.example.entity.Account;

public interface StudentService {

    Account findByUsername(Account account);

    void register(Account account);
}
