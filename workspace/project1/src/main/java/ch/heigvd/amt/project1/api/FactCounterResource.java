
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.facts.counters.FactCounterDTO;
import ch.heigvd.amt.project1.model.FactCounter;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.services.FactCountersManagerLocal;
import ch.heigvd.amt.project1.services.OrganizationsManagerLocal;
import ch.heigvd.amt.project1.services.SensorsManagerLocal;
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
    public List<FactCounter> getFactCounters() {
        MultivaluedMap<String, String> queryParameters = context.getQueryParameters();
        String parameter = queryParameters.getFirst("byOrganizationId");
        List<FactCounter> result = null;

        if (parameter != null) {
            result = factCountersManager.findFactCountersByOrganizationId(Long.parseLong(parameter));
        } else {
            parameter = queryParameters.getFirst("bySensorId");
            if (parameter != null) {
                result = factCountersManager.findFactCountersBySensorId(Long.parseLong(parameter));
            }
        }
        return result;
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

    protected static FactCounter toFactCounter(FactCounterDTO dto, FactCounter factCounter, Organization organization) {
        factCounter.setOpen(dto.isOpen());
        factCounter.setCount(dto.getCount());
        if (organization != null){
            factCounter.setOrganization(organization);
        }
        return factCounter;
    }


    protected static FactCounterDTO toDTO(FactCounter factCounter, boolean doChild) {
        FactCounterDTO dto = new FactCounterDTO();
        dto.setId(factCounter.getId());
        dto.setOpen(factCounter.getOpen());
        dto.setCount(factCounter.getCount());
        if (factCounter.getOrganization() != null && doChild == true){
            dto.setOrganization(OrganizationResource.toDTO(factCounter.getOrganization(), false));
        }
        return dto;
    }

}
