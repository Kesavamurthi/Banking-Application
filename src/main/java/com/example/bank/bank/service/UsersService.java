package com.example.bank.bank.service;

import com.example.bank.bank.entity.Users;

public interface UsersService {
    void save(Users user);
    Users findUsersByFirstName(String firstName);
    void delete(Users user);
}
