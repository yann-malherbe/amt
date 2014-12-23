/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file SensorResource.java
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

import ch.heigvd.amt.project1.dto.sensors.SensorDTO;
import ch.heigvd.amt.project1.dto.sensors.SensorSimpleDTO;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.Sensor;
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
        sensorsManager.createSensor(toSensor(dto, newSensor, organization));
        if (organization != null) {
            List<Sensor> sensors = organization.getSensors();
            sensors.add(newSensor);
            organization.setSensors(sensors);
        }

        return toDTO(sensorsManager.createSensor(toSensor(dto, newSensor, organization)), true);
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
        sensor.setfOpen(dto.isOpen());
        return sensor;
    }

    protected static Sensor toSensor(SensorDTO dto, Sensor sensor, Organization organization) {
        sensor.setName(dto.getName());
        sensor.setDescription(dto.getDescription());
        sensor.setType(dto.getType());
        sensor.setfOpen(dto.isOpen());
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
        dto.setOpen(sensor.isfOpen());
        if (sensor.getOrganization() != null && doChild == true) {
            dto.setOrganization(OrganizationResource.toSimpleDTO(sensor.getOrganization(), false));
        }

        return dto;
    }
}
