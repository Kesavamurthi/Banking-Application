package com.example.bank.bank.service;

import com.example.bank.bank.entity.Authorities;
import com.example.bank.bank.entity.Users;

public interface AuthoritiesService {
    void save(Authorities authorities);
    Authorities findUsersByFirstName(String firstName);
    void delete(Authorities authorities);
}