
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.facts.counters.FactCounterDTO;
import ch.heigvd.amt.project1.model.FactCounter;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.services.FactCountersManagerLocal;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Yann
 */
@Path("facts/numbers")
@Stateless
public class FactCounterResource {

    @EJB
    FactCountersManagerLocal factCountersManager;

    @Context
    private UriInfo context;

    public FactCounterResource() {
    }

    @GET
    @Produces("application/json")
    public List<FactCounterDTO> getFactCounters(@QueryParam("order") String order,
            @QueryParam("id") long id) {
        List<FactCounter> result = null;
        List<FactCounterDTO> resultDTO = new LinkedList<>();

        switch (order) {
            case "byOrganizationId":
                result = factCountersManager.findFactCountersByOrganizationId(id);
                break;

            case "bySensorId":
                result = factCountersManager.findFactCounterBySensorId(id);
                break;
        }
        for (FactCounter factCounter : result) {
            resultDTO.add(toDTO(factCounter, true));
        }
        return resultDTO;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public FactCounterDTO findFactCounter(@PathParam("id") long id) {
        return toDTO(factCountersManager.findFactCounterById(id), true);
    }

    @Path("/{id}")
    @DELETE
    public void deleteFactCounter(@PathParam("id") long id) {
        FactCounter existing = factCountersManager.findFactCounterById(id);
        factCountersManager.deleteFactCounter(existing);
    }

    protected static FactCounter toFactCounter(FactCounterDTO dto, FactCounter factCounter, Organization organization, Sensor sensor) {
        factCounter.setfOpen(dto.isOpen());
        factCounter.setCount(dto.getCount());
        factCounter.setfGlobal(dto.getGlobal());
        if (organization != null) {
            factCounter.setOrganization(organization);
        }
        if (sensor != null) {
            factCounter.setSensor(sensor);
        }
        return factCounter;
    }

    protected static FactCounterDTO toDTO(FactCounter factCounter, boolean doChild) {
        FactCounterDTO dto = new FactCounterDTO();
        dto.setId(factCounter.getId());
        dto.setOpen(factCounter.getfOpen());
        dto.setGlobal(factCounter.getfGlobal());
        dto.setCount(factCounter.getCount());
        if (factCounter.getOrganization() != null && doChild == true) {
            dto.setOrganization(OrganizationResource.toDTO(factCounter.getOrganization(), false));
        }
        if (factCounter.getSensor() != null && doChild == true) {
            dto.setSensor(SensorResource.toDTO(factCounter.getSensor(), false));
        }
        return dto;
    }

}
