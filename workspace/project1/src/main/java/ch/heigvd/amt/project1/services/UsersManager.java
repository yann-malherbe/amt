/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 *
 * @project project1
 * @file UsersManager.java
 *
 * @author Magali Froehlich
 * @author Yann Malherbe
 * @author Cédric Rudareanu
 *
 * @date Dec 20, 2014
 *
 *******************************************************************************
 *
 * @version 1.0
 *
 *******************************************************************************
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        em.flush();
    }

    @Override
    public void deleteUser(User user) {
        em.remove(user);
        em.flush();
    }
}
