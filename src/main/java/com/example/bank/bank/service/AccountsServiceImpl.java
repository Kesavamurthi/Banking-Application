package com.example.bank.bank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bank.bank.DAO.AccountDAO;
import com.example.bank.bank.DAO.AuthoritiesDAO;
import com.example.bank.bank.DAO.UsersDAO;
import com.example.bank.bank.entity.Authorities;
import com.example.bank.bank.entity.Users;
import com.example.bank.bank.entity.accounts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@Service
public class AccountsServiceImpl implements AccountsService{

    private EntityManager theeEntityManager;
    static double balance;
    static long accnum;
    static long accountNumber = 202400;
    
    private UsersService usersService;
    private AccountDAO accountDAO;
    private UsersDAO usersDAO;
    private AuthoritiesService authoritiesService;
    private AuthoritiesDAO authoritiesDAO;

    public AccountsServiceImpl(AuthoritiesDAO authoritiesDAO, AuthoritiesService theauthoritiesService, AccountDAO accountDAO, EntityManager entityManager, UsersDAO theusersDAO,UsersService theusersService) {
        this.accountDAO = accountDAO;
        this.theeEntityManager = entityManager;
        this.usersDAO = theusersDAO;
        this.usersService = theusersService;
        this.authoritiesService = theauthoritiesService;
        this.authoritiesDAO = authoritiesDAO;
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
        Users user = new Users();
        user = usersService.findUsersByFirstName(foundacc.getFirstName());
        Authorities authorities = new Authorities();
        authorities = authoritiesService.findUsersByFirstName(foundacc.getFirstName());
        authoritiesDAO.delete(authorities);
        usersDAO.delete(user);
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
