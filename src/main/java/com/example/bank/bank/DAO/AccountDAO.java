package com.example.bank.bank.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bank.bank.entity.accounts;

@Repository
public interface AccountDAO extends JpaRepository<accounts,Integer>{

}
