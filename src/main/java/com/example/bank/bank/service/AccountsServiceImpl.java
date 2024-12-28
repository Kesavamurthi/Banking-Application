package com.example.bank.bank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bank.bank.DAO.AccountDAO;
import com.example.bank.bank.entity.accounts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@Service
public class AccountsServiceImpl implements AccountsService{

    private EntityManager theeEntityManager;
    static double balance;
    static long accnum;
    static long accountNumber = 202400;
    private AccountDAO accountDAO;

    public AccountsServiceImpl(AccountDAO accountDAO, EntityManager entityManager) {
        this.accountDAO = accountDAO;
        this.theeEntityManager = entityManager;
    }

    @Override
    public void save(accounts account) {
        accountDAO.save(account);
    }

    @Override
    public List<accounts> findAll() {
        List<accounts> accounts = accountDAO.findAll();
        return accounts;
    }

    @Override
    public accounts findById(int id) {
        Optional<accounts> account = accountDAO.findById(id);
        accounts accountnew = null;
        if(account.isPresent()){
            accountnew = account.get();
        }
        else{
            throw new RuntimeException("No user found");
        }
        return accountnew;
    }

    @Override
    public void deleteAccount(int id) {
        Optional<accounts> account = accountDAO.findById(id); 
        accounts foundacc = account.get();
        accountDAO.delete(foundacc);   
    }

    @Override
    public accounts findAccountByAccountNumber(long accountNum) {
        TypedQuery<accounts> thequery = theeEntityManager.createQuery("from accounts WHERE accountNumber=:thedata",accounts.class);
        thequery.setParameter("thedata", accountNum);
        return thequery.getSingleResult();
    }

    @Override
    public accounts findLastAccountNumber() {
        TypedQuery<accounts> theQuery = theeEntityManager.createQuery("FROM accounts ORDER BY id DESC LIMIT 1",accounts.class);
        List<accounts> acc = theQuery.getResultList();
        return acc.isEmpty() ? null : acc.get(0);
    }

    @Override
    public accounts findAccountByUsername(String username) {
        TypedQuery<accounts> thequery = theeEntityManager.createQuery("from accounts WHERE firstName=:thedata",accounts.class);
        thequery.setParameter("thedata", username);
        return thequery.getSingleResult();
    }

}
