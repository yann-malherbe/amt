/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.users.UserDTO;
import ch.heigvd.amt.project1.dto.users.UserWithoutPassDTO;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.User;
import ch.heigvd.amt.project1.services.UsersManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Yann
 */
@Path("users")
@Stateless
public class UserResource {

    @EJB
    UsersManagerLocal usersManager;

    @Context
    private UriInfo context;

    /**
     *
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    @GET
    @Produces("application/json")
    public List<UserDTO> getAllUsers() {
        List<User> users = usersManager.findAllUsers();
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            result.add(toDTO(user));
        }
        return result;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public UserDTO createUser(UserDTO dto) {
        User newUser = new User();
        return toDTO(usersManager.createUser(toUser(dto, newUser)));
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public UserDTO getUser(@PathParam("id") long id) {
        return toDTO(usersManager.findUserById(id));
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public UserDTO updateUser(@PathParam("id") long id, UserDTO dto) {
        User existing = usersManager.findUserById(id);
        usersManager.updateUser(toUser(dto, existing));
        return toDTO(existing);
    }

    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") long id) {
        User existing = usersManager.findUserById(id);
        usersManager.deleteUser(existing);
    }

    protected static User toUser(UserWithoutPassDTO dto, User user) {
        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        //user.setOrganization(dto.getOrganization());
        return user;
    }

    protected static User toUser(UserDTO dto, User user) {
        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setPass(dto.getPass());
        //user.setOrganization(dto.getOrganization());
        return user;
    }

    protected static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLogin(user.getLogin());
        //dto.setOrganization(user.getOrganization());
        return dto;
    }
}