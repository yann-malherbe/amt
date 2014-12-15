/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.api;

import ch.heigvd.amt.project1.dto.observations.ObservationDTO;
import ch.heigvd.amt.project1.model.FactCounter;
import ch.heigvd.amt.project1.model.FactSummary;
import ch.heigvd.amt.project1.model.Observation;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.services.FactCountersManagerLocal;
import ch.heigvd.amt.project1.services.FactSummariesManagerLocal;
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

    @EJB
    FactCountersManagerLocal factCounterManager;

    @EJB
    FactSummariesManagerLocal factSummaryManager;

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
            result.add(toDTO(observation, true));
        }
        return result;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ObservationDTO createObservation(ObservationDTO dto) {
        Observation newObservation = new Observation();
        Sensor sensor = null;
        if (dto.getSensor() != null) {
            sensor = sensorsManager.findSensorById(dto.getSensor().getId());
        }
        newObservation = observationsManager.createObservation(toObservation(dto, newObservation, sensor));

        if (sensor != null) {
            List<FactCounter> factCounters = factCounterManager.findFactCounterBySensorId(newObservation.getSensor().getId());

            if (factCounters.isEmpty()) {
                FactCounter factCounter = new FactCounter();
                factCounter.setCount(1);
                factCounter.setSensor(sensor);
                factCounter.setfOpen(sensor.isfOpen());
                if (sensor.getOrganization() != null) {
                    factCounter.setOrganization(sensor.getOrganization());
                }
                factCounterManager.createFactCounter(factCounter);
            } else {
                FactCounter factCounter = factCounters.get(0);
                factCounter.setCount(factCounter.getCount() + 1);
                factCounterManager.updateFactCounter(factCounter);
            }

            List<FactSummary> factsummaries = factSummaryManager.findFactSummariesBySensorId(newObservation.getSensor().getId());

            if (factsummaries.isEmpty()) {
                FactSummary factsummary = new FactSummary();
                factsummary.setfDay(null);
                factsummary.setfAverage(newObservation.getfValue());
                factsummary.setfMin(newObservation.getfValue());
                factsummary.setfMax(newObservation.getfValue());
                factsummary.setSensor(sensor);
                factsummary.setfOpen(sensor.isfOpen());
                if (sensor.getOrganization() != null) {
                    factsummary.setOrganization(sensor.getOrganization());
                }
                factSummaryManager.createFactSummary(factsummary);
            } else {
                FactSummary factsummary = factsummaries.get(0);

                if (newObservation.getfValue() < factsummary.getfMin()) {
                    factsummary.setfMin(newObservation.getfValue());
                }

                if (newObservation.getfValue() > factsummary.getfMax()) {
                    factsummary.setfMax(newObservation.getfValue());
                }

                factsummary.setfAverage(((factCounters.get(0).getCount()-1)*factsummary.getfAverage()+newObservation.getfValue())/factCounters.get(0).getCount());

                factSummaryManager.updateFactSummary(factsummary);
            }
        }
        return toDTO(newObservation, true);
    }

    private Observation toObservation(ObservationDTO dto, Observation observation, Sensor sensor) {
        observation.setfDate(dto.getDate());
        observation.setfValue(dto.getValue());
        if (dto.getSensor() != null) {
            observation.setSensor(sensor);
        }
        return observation;
    }

    protected static ObservationDTO toDTO(Observation observation, boolean doChild) {
        ObservationDTO dto = new ObservationDTO();
        dto.setId(observation.getId());
        dto.setDate(observation.getfDate());
        dto.setValue(observation.getfValue());

        if (observation.getSensor() != null && doChild == true) {
            dto.setSensor(SensorResource.toDTO(observation.getSensor(), false));
        }
        return dto;
    }
}
