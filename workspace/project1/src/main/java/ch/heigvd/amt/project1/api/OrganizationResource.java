/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 *
 * @project project1
 * @file OrganizationResource.java
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
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.facts.counters.FactCounterDTO;
import ch.heigvd.amt.project1.dto.facts.summaries.FactSummaryDTO;
import ch.heigvd.amt.project1.dto.organizations.OrganizationDTO;
import ch.heigvd.amt.project1.dto.organizations.OrganizationSimpleDTO;
import ch.heigvd.amt.project1.dto.sensors.SensorDTO;
import ch.heigvd.amt.project1.dto.users.UserWithoutPassDTO;
import ch.heigvd.amt.project1.model.FactCounter;
import ch.heigvd.amt.project1.model.FactSummary;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.model.User;
import ch.heigvd.amt.project1.services.OrganizationsManagerLocal;
import ch.heigvd.amt.project1.services.UsersManagerLocal;
import java.util.ArrayList;
import java.util.LinkedList;
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

@Path("organizations")
@Stateless
public class OrganizationResource {

    @EJB
    OrganizationsManagerLocal organizationsManager;

    @EJB
    UsersManagerLocal usersManager;

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
            result.add(toSimpleDTO(organization, true));
        }
        return result;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public OrganizationSimpleDTO createOrganization(OrganizationSimpleDTO dto) {
        Organization newOrganization = new Organization();
        User existing = null;

        if (dto.getContact() != null) {
            existing = usersManager.findUserById(dto.getContact().getId());
        }
        return toSimpleDTO(organizationsManager.createOrganization(toOrganization(dto, newOrganization, existing)), true);
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
        User user = usersManager.findUserById(dto.getContact().getId());
        user.setOrganization(existing);
        usersManager.updateUser(user);
        organizationsManager.updateOrganization(toOrganization(dto, existing, user));
        return toDTO(existing, true);
    }

    @Path("/{id}")
    @DELETE
    public void deleteOrganization(@PathParam("id") long id) {
        Organization existing = organizationsManager.findOrganizationById(id);
        organizationsManager.deleteOrganization(existing);
    }

    @Path("/{id}/users")
    @GET
    @Produces("application/json")
    public List<UserWithoutPassDTO> getOrganizationUsers(@PathParam("id") long id) {
        List<UserWithoutPassDTO> result = new LinkedList<>();
        Organization existing = organizationsManager.findOrganizationById(id);
        List<User> users = organizationsManager.findOrganizationUsers(existing);
        for (User user : users) {
            result.add(UserResource.toDTO(user, true));
        }
        return result;
    }

    @Path("/{id}/sensors")
    @GET
    @Produces("application/json")
    public List<SensorDTO> getOrganizationSensors(@PathParam("id") long id) {
        List<SensorDTO> result = new LinkedList<>();
        Organization existing = organizationsManager.findOrganizationById(id);
        List<Sensor> sensors = organizationsManager.findOrganizationSensors(existing);
        for (Sensor sensor : sensors) {
            result.add(SensorResource.toDTO(sensor, true));
        }
        return result;
    }

    @Path("/{id}/facts/counters")
    @GET
    @Produces("application/json")
    public List<FactCounterDTO> getOrganizationFactCounters(@PathParam("id") long id) {
        List<FactCounterDTO> result = new LinkedList<>();
        Organization existing = organizationsManager.findOrganizationById(id);
        List<FactCounter> factCounters = organizationsManager.findOrganizationFactCounters(existing);
        for (FactCounter factCounter : factCounters) {
            result.add(FactCounterResource.toDTO(factCounter, true));
        }
        return result;
    }

    @Path("/{id}/facts/summaries")
    @GET
    @Produces("application/json")
    public List<FactSummaryDTO> getOrganizationFactSummaries(@PathParam("id") long id) {
        List<FactSummaryDTO> result = new LinkedList<>();
        Organization existing = organizationsManager.findOrganizationById(id);
        List<FactSummary> factSummaries = organizationsManager.findOrganizationFactSummaries(existing);
        for (FactSummary factSummary : factSummaries) {
            result.add(FactSummaryResource.toDTO(factSummary, true));
        }
        return result;
    }

    protected static Organization toOrganization(OrganizationSimpleDTO dto, Organization organization, User contact) {
        organization.setName(dto.getName());
        if (dto.getContact() != null) {
            organization.setContact(contact);
        }
        return organization;
    }

    protected static Organization toOrganization(OrganizationDTO dto, Organization organization, User contact, List<Sensor> sensors, List<User> users) {
        organization.setName(dto.getName());
        if (dto.getContact() != null) {
            organization.setContact(contact);
        }
        if (dto.getSensors() != null) {
            organization.setSensors(sensors);
        }
        if (dto.getUsers() != null) {
            organization.setUsers(users);
        }
        return organization;
    }

    protected static OrganizationSimpleDTO toSimpleDTO(Organization organization, boolean doChild) {
        OrganizationSimpleDTO dto = new OrganizationSimpleDTO();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        if (organization.getContact() != null && doChild == true) {
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
            List<SensorDTO> sensorsDTO = new LinkedList<>();
            for (Sensor sensor : organization.getSensors()) {
                sensorsDTO.add(SensorResource.toDTO(sensor, false));
            }
            dto.setSensors(sensorsDTO);
        }

        if (organization.getUsers() != null && doChild == true) {
            List<UserWithoutPassDTO> usersDTO = new LinkedList<>();
            for (User user : organization.getUsers()) {
                usersDTO.add(UserResource.toDTO(user, false));
            }
            dto.setUsers(usersDTO);
        }
        return dto;
    }

}
