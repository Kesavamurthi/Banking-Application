package com.example.bank.bank.service;

import org.springframework.stereotype.Service;

import com.example.bank.bank.DAO.UsersDAO;
import com.example.bank.bank.entity.Users;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class UsersServiceImpl implements UsersService{

    private EntityManager entityManager;
    private UsersDAO usersDAO;

    public UsersServiceImpl(UsersDAO usersDAO,EntityManager theentityManager) {
        this.entityManager = theentityManager;
        this.usersDAO = usersDAO;
    }

    @Override
    public void save(Users user) {
        usersDAO.save(user);
    }

    @Override
    public void delete(Users user) {
        usersDAO.delete(user);
    }

    @Override
    public Users findUsersByFirstName(String firstName) {
        TypedQuery<Users> theQuery = entityManager.createQuery("FROM Users WHERE username=:theData",Users.class);
        theQuery.setParameter("theData", firstName);
        return theQuery.getSingleResult();
    }


}
