/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file FactCounterResource.java
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
import ch.heigvd.amt.project1.model.FactCounter;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.services.FactCountersManagerLocal;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
    public List<FactCounterDTO> getFactCounters(@DefaultValue("none") @QueryParam("filterBy") String filterBy,
            @DefaultValue("0") @QueryParam("filterId") long filterId) {
        List<FactCounter> result;
        List<FactCounterDTO> resultDTO = new LinkedList<>();

        switch (filterBy) {
            case "byOrganizationId":
                result = factCountersManager.findFactCountersByOrganizationId(filterId);
                break;
            case "bySensorId":
                result = factCountersManager.findFactCounterBySensorId(filterId);
                break;
            default:
                result = factCountersManager.findAllFactCounters();
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
        factCounter.setfDay(dto.getDate());
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
        dto.setDate(factCounter.getfDay());
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
