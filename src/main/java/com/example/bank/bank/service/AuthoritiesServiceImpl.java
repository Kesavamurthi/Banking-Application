package com.example.bank.bank.service;

import org.springframework.stereotype.Repository;

import com.example.bank.bank.DAO.AuthoritiesDAO;
import com.example.bank.bank.entity.Authorities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class AuthoritiesServiceImpl implements AuthoritiesService {

    private EntityManager entityManager;
    private AuthoritiesDAO authoritiesDAO;

    public AuthoritiesServiceImpl(AuthoritiesDAO authoritiesDAO, EntityManager theentityManager) {
        this.authoritiesDAO = authoritiesDAO;
        this.entityManager = theentityManager;
    }

    @Override
    public void save(Authorities authorities) {
        authoritiesDAO.save(authorities);
    }

    @Override
    public Authorities findUsersByFirstName(String firstName) {
        TypedQuery<Authorities> theqQuery = entityManager.createQuery("FROM Authorities WHERE username=:theData",Authorities.class);
        theqQuery.setParameter("theData", firstName);
        return theqQuery.getSingleResult();
    }

    @Override
    public void delete(Authorities authorities) {
        authoritiesDAO.delete(authorities);
    }

    

}
