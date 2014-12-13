/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.organizations.OrganizationDTO;
import ch.heigvd.amt.project1.dto.organizations.OrganizationSimpleDTO;
import ch.heigvd.amt.project1.dto.sensors.SensorDTO;
import ch.heigvd.amt.project1.dto.users.UserDTO;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.model.User;
import ch.heigvd.amt.project1.services.OrganizationsManagerLocal;
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
@Path("organizations")
@Stateless
public class OrganizationResource {

    @EJB
    OrganizationsManagerLocal organizationsManager;

    @Context
    private UriInfo context;

    public OrganizationResource() {
    }

    @GET
    @Produces("application/json")
    public List<OrganizationSimpleDTO> getAllOrganizations() {
        List<Organization> organizations = organizationsManager.findAllOrganizations();
        List<OrganizationSimpleDTO> result = new ArrayList<>();
        for (Organization organization : organizations) {
            result.add(toSimpleDTO(organization));
        }
        return result;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public OrganizationSimpleDTO createOrganization(OrganizationSimpleDTO dto) {
        Organization newOrganization = new Organization();
        return toSimpleDTO(organizationsManager.createOrganization(toOrganization(dto, newOrganization)));
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public OrganizationDTO getOrganization(@PathParam("id") long id) {
        return toDTO(organizationsManager.findOrganizationById(id), true);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public OrganizationDTO updateOrganization(@PathParam("id") long id, OrganizationSimpleDTO dto) {
        Organization existing = organizationsManager.findOrganizationById(id);
        organizationsManager.updateOrganization(toOrganization(dto, existing));
        return toDTO(existing, true);
    }

    @Path("/{id}")
    @DELETE
    public void deleteOrganization(@PathParam("id") long id) {
        Organization existing = organizationsManager.findOrganizationById(id);
        organizationsManager.deleteOrganization(existing);
    }

    protected static Organization toOrganization(OrganizationSimpleDTO dto, Organization organization) {
        organization.setName(dto.getName());
        if (dto.getContact() != null) {
            organization.setContact(UserResource.toUser(dto.getContact(), new User()));
        }
        return organization;
    }

    protected static Organization toOrganization(OrganizationDTO dto, Organization organization) {
        organization.setName(dto.getName());
        if (dto.getContact() != null) {
            organization.setContact(UserResource.toUser(dto.getContact(), new User()));
        }
        organization.setSensors(dto.getSensors());
        organization.setUsers(dto.getUsers());
        return organization;
    }

    protected static OrganizationSimpleDTO toSimpleDTO(Organization organization) {
        OrganizationSimpleDTO dto = new OrganizationSimpleDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        if (organization.getContact() != null) {
            dto.setContact(UserResource.toDTO(organization.getContact(), false));
        }
        return dto;
    }

    protected static OrganizationDTO toDTO(Organization organization, boolean doChild) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());

        if (organization.getContact() != null && doChild == true) {
            dto.setContact(UserResource.toDTO(organization.getContact(), false));
        }

        if (organization.getSensors() != null && doChild == true) {
            ArrayList<SensorDTO> sensorsDTO = new ArrayList<>();
            for (Sensor sensor : (List<Sensor>) organization.getSensors()) {
                sensorsDTO.add(SensorResource.toDTO(sensor, false));
            }
            dto.setSensors(sensorsDTO);
        }

        if (organization.getUsers() != null && doChild == true) {
            List<UserDTO> usersDTO = new ArrayList<>();
            for (User user : (List<User>) organization.getUsers()) {
                usersDTO.add(UserResource.toDTO(user, false));
            }
            dto.setUsers(usersDTO);
        }

        return dto;
    }

}
