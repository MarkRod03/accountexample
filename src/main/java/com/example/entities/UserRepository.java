package com.example.entities;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

   @Autowired
   private EntityManager entityManager;

   //Add account to database
   @Transactional
   public User create(User user) {
        entityManager.persist(user);
        return user;
   }

   //Find User for comparing password for login
   public User findByUsername(String username) {
    TypedQuery<User> q = entityManager.createQuery("Select u FROM User u " + "WHERE u.username = :username", User.class);
    q.setParameter("username", username);
    try {
        User user = q.getSingleResult();
        return user;
    } catch (NoResultException e) {
        return null;
    }
  }


    //Update user credentials
    @Transactional
   public User update(User user) {
        entityManager.merge(user);
        return user;
   }
}