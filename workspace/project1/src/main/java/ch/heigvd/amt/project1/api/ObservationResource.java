/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.observations.ObservationDTO;
import ch.heigvd.amt.project1.model.Observation;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.services.ObservationsManagerLocal;
import ch.heigvd.amt.project1.services.SensorsManagerLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Yann
 */
@Path("observations")
@Stateless
public class ObservationResource {

    @EJB
    ObservationsManagerLocal observationsManager;
    
    @EJB
    SensorsManagerLocal sensorsManager;

    @Context
    private UriInfo context;

    /**
     *
     * Creates a new instance of ObservationResource
     */
    public ObservationResource() {
    }

    @GET
    @Produces("application/json")
    public List<ObservationDTO> getAllObservations() {
        List<Observation> observations = observationsManager.findAllObservations();
        List<ObservationDTO> result = new ArrayList<>();
        for (Observation observation : observations) {
            result.add(toDTO(observation));
        }
        return result;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ObservationDTO createObservation(ObservationDTO dto) {
        Observation newObservation = new Observation();
        Sensor sensor = null;
        if (dto.getSensor() != null){
            sensor = sensorsManager.findSensorById(dto.getSensor().getId());
        }
        return toDTO(observationsManager.createObservation(toObservation(dto, newObservation, sensor)));
    }

    private Observation toObservation(ObservationDTO dto, Observation observation, Sensor sensor) {
        observation.setId(dto.getId());
        observation.setfDate(dto.getDate());
        observation.setfData(dto.getData());
        if (observation.getSensor() != null){
            observation.setSensor(sensor);
        }
        return observation;
    }

    protected static ObservationDTO toDTO(Observation observation) {
        ObservationDTO dto = new ObservationDTO();
        dto.setId(observation.getId());
        dto.setDate(observation.getfDate());
        dto.setData(observation.getfData());
        
        if (observation.getSensor() != null){
            dto.setSensor(SensorResource.toDTO(observation.getSensor(), false));
        }
        return dto;
    }
}
