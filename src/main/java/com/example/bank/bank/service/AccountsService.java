package com.example.bank.bank.service;


import java.util.List;
import java.util.Optional;

import com.example.bank.bank.entity.accounts;

public interface AccountsService {
    void save(accounts account);
    List<accounts> findAll();
    accounts findById(int id);
    void deleteAccount(int id);
    accounts findAccountByAccountNumber(long accountNum);
    accounts findLastAccountNumber();
}
