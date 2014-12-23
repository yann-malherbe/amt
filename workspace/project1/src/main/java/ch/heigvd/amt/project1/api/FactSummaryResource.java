/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file FactSummaryResource.java
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

import ch.heigvd.amt.project1.dto.facts.summaries.FactSummaryDTO;
import ch.heigvd.amt.project1.model.FactSummary;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.services.FactSummariesManagerLocal;
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

@Path("facts/summaries")
@Stateless
public class FactSummaryResource {

    @EJB
    FactSummariesManagerLocal factSummariesManager;

    @Context
    private UriInfo context;

    public FactSummaryResource() {
    }

    @GET
    @Produces("application/json")
    public List<FactSummaryDTO> getFactCounters(@DefaultValue("none") @QueryParam("order") String order,
            @DefaultValue("0") @QueryParam("id") long id) {
        List<FactSummary> result = null;
        List<FactSummaryDTO> resultDTO = new LinkedList<>();

        switch (order) {
            case "byOrganizationId":
                result = factSummariesManager.findFactSummariesByOrganizationId(id);
                break;
            case "bySensorId":
                result = factSummariesManager.findFactSummariesBySensorId(id);
                break;
            default:
                result = factSummariesManager.findAllFactSummaries();
                break;
        }
        for (FactSummary factSummary : result) {
            resultDTO.add(toDTO(factSummary, true));
        }
        return resultDTO;
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public FactSummaryDTO findFactCounter(@PathParam("id") long id) {
        return toDTO(factSummariesManager.findFactSummaryById(id), true);
    }

    @Path("/{id}")
    @DELETE
    public void deleteFactCounter(@PathParam("id") long id) {
        FactSummary existing = factSummariesManager.findFactSummaryById(id);
        factSummariesManager.deleteFactSummary(existing);
    }

    protected static FactSummary toFactSummary(FactSummaryDTO dto, FactSummary factSummary, Organization organization, Sensor sensor) {
        factSummary.setfOpen(dto.isOpen());
        factSummary.setfGlobal(dto.getGlobal());
        factSummary.setfMin(dto.getMin());
        factSummary.setfMax(dto.getMax());
        factSummary.setfAverage(dto.getAverage());
        factSummary.setfDay(dto.getDate());
        if (organization != null) {
            factSummary.setOrganization(organization);
        }
        if (sensor != null) {
            factSummary.setSensor(sensor);
        }
        return factSummary;
    }

    protected static FactSummaryDTO toDTO(FactSummary factSummary, boolean doChild) {
        FactSummaryDTO dto = new FactSummaryDTO();
        dto.setId(factSummary.getId());
        dto.setOpen(factSummary.getfOpen());
        dto.setGlobal(factSummary.getfGlobal());
        dto.setMin(factSummary.getfMin());
        dto.setMax(factSummary.getfMax());
        dto.setAverage(factSummary.getfAverage());
        dto.setDate(factSummary.getfDay());

        if (factSummary.getOrganization() != null && doChild == true) {
            dto.setOrganization(OrganizationResource.toDTO(factSummary.getOrganization(), false));
        }
        if (factSummary.getSensor() != null && doChild == true) {
            dto.setSensor(SensorResource.toDTO(factSummary.getSensor(), false));
        }
        return dto;
    }
}
