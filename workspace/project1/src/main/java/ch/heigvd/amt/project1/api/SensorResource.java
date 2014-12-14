/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.sensors.SensorDTO;
import ch.heigvd.amt.project1.dto.sensors.SensorSimpleDTO;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.model.User;
import ch.heigvd.amt.project1.services.OrganizationsManagerLocal;
import ch.heigvd.amt.project1.services.SensorsManagerLocal;
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
@Path("sensors")
@Stateless
public class SensorResource {

    @EJB
    SensorsManagerLocal sensorsManager;

    @EJB
    OrganizationsManagerLocal organizationsManager;

    @EJB
    UsersManagerLocal usersManager;

    @Context
    private UriInfo context;

    /**
     *
     * Creates a new instance of SensorResource
     */
    public SensorResource() {
    }

    @GET
    @Produces("application/json")
    public List<SensorDTO> getAllPublicSensors() {
        List<Sensor> sensors = sensorsManager.findAllPublicSensors();
        List<SensorDTO> result = new ArrayList<>();
        for (Sensor sensor : sensors) {
            result.add(toDTO(sensor, true));
        }
        return result;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public SensorDTO createSensor(SensorDTO dto) {
        Sensor newSensor = new Sensor();
        Organization organization = null;

        if (dto.getOrganization() != null) {
            organization = organizationsManager.findOrganizationById(dto.getOrganization().getId());
        }

        return toDTO(sensorsManager.createSensor(toSensor(dto, newSensor, organization, null)), true);
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public SensorDTO getSensor(@PathParam("id") long id) {
        return toDTO(sensorsManager.findSensorById(id), true);
    }

    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public SensorDTO updateSensor(@PathParam("id") long id, SensorSimpleDTO dto) {
        Sensor existing = sensorsManager.findSensorById(id);
        sensorsManager.updateSensor(toSensor(dto, existing));
        return toDTO(existing, true);
    }

    @Path("/{id}")
    @DELETE
    public void deleteUserDetails(@PathParam("id") long id) {
        Sensor existing = sensorsManager.findSensorById(id);
        sensorsManager.deleteSensor(existing);
    }

    protected static Sensor toSensor(SensorSimpleDTO dto, Sensor sensor) {
        sensor.setOpen(dto.isOpen());
        return sensor;
    }

    protected static Sensor toSensor(SensorDTO dto, Sensor sensor, Organization organization, User user) {
        sensor.setName(dto.getName());
        sensor.setDescription(dto.getDescription());
        sensor.setType(dto.getType());
        sensor.setOpen(dto.isOpen());
        if (dto.getOrganization() != null) {
            sensor.setOrganization(organization);
        }

        return sensor;
    }

    protected static SensorDTO toDTO(Sensor sensor, boolean doChild) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setName(sensor.getName());
        dto.setDescription(sensor.getDescription());
        dto.setType(sensor.getType());
        dto.setOpen(sensor.isOpen());
        if (sensor.getOrganization() != null && doChild == true) {
            dto.setOrganization(OrganizationResource.toSimpleDTO(sensor.getOrganization(), false));
        }

        return dto;
    }
}
