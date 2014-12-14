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
import ch.heigvd.amt.project1.services.OrganizationsManagerLocal;
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

    @EJB
    OrganizationsManagerLocal organizationsManager;

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
            result.add(toDTO(user, true));
        }
        return result;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public UserDTO createUser(UserDTO dto) {
        User newUser = new User();
        Organization organization = organizationsManager.findOrganizationById(dto.getOrganization().getId());
        return toDTO(usersManager.createUser(toUser(dto, newUser, organization)), true);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public UserDTO getUser(@PathParam("id") long id) {
        return toDTO(usersManager.findUserById(id), true);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public UserDTO updateUser(@PathParam("id") long id, UserDTO dto) {
        User existing = usersManager.findUserById(id);
        Organization organization = organizationsManager.findOrganizationById(dto.getOrganization().getId());
        usersManager.updateUser(toUser(dto, existing, organization));
        return toDTO(existing, true);
    }

    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") long id) {
        User existing = usersManager.findUserById(id);
        usersManager.deleteUser(existing);
    }

    protected static User toUser(UserWithoutPassDTO dto, User user, Organization organization) {
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        if (dto.getOrganization() != null) {
            user.setOrganization(OrganizationResource.toOrganization(dto.getOrganization(), organization, user));
        }
        return user;
    }

    protected static User toUser(UserDTO dto, User user, Organization organization) {
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setPass(dto.getPass());
        if (dto.getOrganization() != null) {
            user.setOrganization(OrganizationResource.toOrganization(dto.getOrganization(), organization, user));
        }
        return user;
    }

    protected static UserDTO toDTO(User user, boolean doChild) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLogin(user.getLogin());
        if (user.getOrganization() != null && doChild == true) {
            dto.setOrganization(OrganizationResource.toSimpleDTO(user.getOrganization(), false));
        }
        return dto;
    }
}
