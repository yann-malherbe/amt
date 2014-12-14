/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.facts.summaries.FactSummaryDTO;
import ch.heigvd.amt.project1.model.FactSummary;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.services.FactSummariesManagerLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Yann
 */
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
    public List<FactSummary> getFactCounters() {
        MultivaluedMap<String, String> queryParameters = context.getQueryParameters();
        String parameter = queryParameters.getFirst("byOrganizationId");
        List<FactSummary> result = null;

        if (parameter != null) {
            result = factSummariesManager.findFactSummariesByOrganizationId(Long.parseLong(parameter));
        } else {
            parameter = queryParameters.getFirst("bySensorId");
            if (parameter != null) {
                result = factSummariesManager.findFactSummariesBySensorId(Long.parseLong(parameter));
            }
        }
        return result;
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

    protected static FactSummary toFactSummary(FactSummaryDTO dto, FactSummary factSummary, Organization organization) {
        factSummary.setOpen(dto.isOpen());
        factSummary.setMin(dto.getMin());
        factSummary.setMax(dto.getMax());
        factSummary.setAverage(dto.getAverage());
        factSummary.setDay(dto.getDay());
        if (organization != null){
            factSummary.setOrganization(organization);
        }
        return factSummary;
    }


    protected static FactSummaryDTO toDTO(FactSummary factSummary, boolean doChild) {
        FactSummaryDTO dto = new FactSummaryDTO();
        dto.setId(factSummary.getId());
        dto.setOpen(factSummary.getOpen());
        dto.setMin(factSummary.getMin());
        dto.setMax(factSummary.getMax());
        dto.setAverage(factSummary.getAverage());
        dto.setDay(factSummary.getDay());
        
        if (factSummary.getOrganization() != null && doChild == true){
            dto.setOrganization(OrganizationResource.toDTO(factSummary.getOrganization(), false));
        }
        return dto;
    }
}