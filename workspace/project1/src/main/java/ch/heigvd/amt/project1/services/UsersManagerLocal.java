/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Yann
 */
@Local
public interface UsersManagerLocal {
    
    public User findUserById(long id);

    public List<User> findAllUsers();

    public void createUser(User user);

    public void updateUser(User user);

    public void deleteUser(User user);

    void businessMethod();
    
}
