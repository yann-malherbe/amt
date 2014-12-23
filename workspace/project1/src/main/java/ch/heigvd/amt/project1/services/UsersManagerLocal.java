/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 *
 * @project project1
 * @file UsersManagerLocal.java
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
import javax.ejb.Local;

@Local
public interface UsersManagerLocal {

    public User findUserById(long id);

    public List<User> findAllUsers();

    public User createUser(User user);

    public void updateUser(User user);

    public void deleteUser(User user);
}
