package com.example.service;

import com.example.entity.Account;
import com.example.entity.Admin;

public interface AdminService {

    Account findByUsername(Account account);
}
