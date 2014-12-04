/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yann
 */
@Stateless
public class UsersManager implements UsersManagerLocal {

    @PersistenceContext
    public EntityManager em;

    @Override
    public User findUserById(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAllUsers() {
        return em.createNamedQuery("findAllUsers").getResultList();
    }

    @Override
    public User createUser(User user) {
        em.persist(user);
        em.flush();
        return user;
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
    }

}
