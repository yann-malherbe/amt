/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.UserDTO;
import ch.heigvd.amt.project1.dto.UserWithPassDTO;
import ch.heigvd.amt.project1.model.User;
import ch.heigvd.amt.project1.services.UsersManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    public User createUser(UserWithPassDTO dto) {
        User newUser = new User();
        return usersManager.createUser(toUser(dto, newUser));
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public UserDTO getUserDetails(@PathParam("id") long id) {
        User user = usersManager.findUserById(id);
        return toDTO(user);
    }

    private User toUser(UserWithPassDTO dto, User user) {
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setPass(dto.getPass());
        user.setOrganization(dto.getOrganization());
        return user;
    }

    private User toUser(UserDTO dto, User user) {
        user.setLogin(dto.getLogin());
        user.setName(dto.getName());
        user.setOrganization(dto.getOrganization());
        return user;
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLogin(user.getLogin());
        dto.setOrganization(user.getOrganization());
        return dto;
    }

    /*
     @Path("/{id}")
     @GET
     @Produces("application/json")
     public EmployeeDTO getEmployeeDetails(@PathParam("id") long id) {
     Employee employee = employeesManager.findEmployeeById(id);
     return toDTO(employee);
     }

     @POST
     @Consumes("application/json")
     public long createEmployee(EmployeeDTO dto) {
     Employee newEmployee = new Employee();
     long id = employeesManager.createEmployee(toEmployee(dto, newEmployee));
     return id;
     }

     @Path("/{id}")
     @PUT
     @Consumes("application/json")
     public void updateEmployee(@PathParam("id") long id, EmployeeDTO dto) {
     Employee existing = employeesManager.findEmployeeById(id);
     employeesManager.updateEmployee(toEmployee(dto, existing));
     }

     @Path("/{id}")
     @DELETE
     public void deleteEmployee(@PathParam("id") long id) {
     employeesManager.deleteEmployee(id);
     }

     private Employee toEmployee(EmployeeDTO dto, Employee original) {
     original.setFirstName(dto.getFirstName());
     original.setLastName(dto.getLastName());
     original.setEmail(dto.getEmail());
     return original;
     }

     private EmployeeDTO toDTO(Employee employee) {
     EmployeeDTO dto = new EmployeeDTO();
     dto.setId(employee.getId());
     dto.setFirstName(employee.getFirstName());
     dto.setLastName(employee.getLastName());
     dto.setEmail(employee.getEmail());
     return dto;
     }
     */
}
