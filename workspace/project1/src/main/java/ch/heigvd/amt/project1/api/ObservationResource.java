/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file ObservationResource.java
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

import ch.heigvd.amt.project1.dto.observations.ObservationDTO;
import ch.heigvd.amt.project1.model.FactCounter;
import ch.heigvd.amt.project1.model.FactSummary;
import ch.heigvd.amt.project1.model.Observation;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.services.FactCountersManagerLocal;
import ch.heigvd.amt.project1.services.FactSummariesManagerLocal;
import ch.heigvd.amt.project1.services.ObservationsManagerLocal;
import ch.heigvd.amt.project1.services.SensorsManagerLocal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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

    public ObservationResource() {
    }

    @GET
    @Produces("application/json")
    public List<ObservationDTO> getObservationsBySensorId(@DefaultValue("none") @QueryParam("filterBy") String filterBy,
            @DefaultValue("0") @QueryParam("filterId") long filterId) {
        List<Observation> result = null;
        List<ObservationDTO> resultDTO = new LinkedList<>();

        switch (filterBy) {
            case "bySensorId":
                result = observationsManager.findObservationsBySensorId(filterId);
                break;
            default:
                result = observationsManager.findAllObservations();
                break;
        }

        for (Observation observation : result) {
            resultDTO.add(toDTO(observation, true));
        }
        return resultDTO;
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
            factCounterManager(factCounters, newObservation, sensor);
            factSummaryManager(factSummaryManager.findFactSummariesBySensorId(newObservation.getSensor().getId()), factCounters, newObservation, sensor);
        }
        return toDTO(newObservation, true);
    }

    private void factCounterManager(List<FactCounter> factCounters, Observation observation, Sensor sensor) {
        if (factCounters.isEmpty()) {
            // First time we create the global counter
            newFactCounter(observation, sensor, true);

            // We create a daily counter too
            newFactCounter(observation, sensor, false);
        } // Counters already exist
        else {
            // We take the global counter
            FactCounter factCounterGlobal = null;
            for (FactCounter factCounter : factCounters) {
                if (factCounter.getfGlobal()) {
                    factCounterGlobal = factCounter;
                    break;
                }
            }
            if (factCounterGlobal != null) {
                factCounterGlobal.setCount(factCounterGlobal.getCount() + 1);
                factCounterGlobal.setfDay(observation.getfDate());
                factCounterManager.updateFactCounter(factCounterGlobal);
            }

            // We take the daily counter
            FactCounter factCounterDaily = factCounters.get(0);
            long dayMax = 0;

            for (FactCounter factCounter : factCounters) {
                if (dayMax < factCounter.getfDay() && !factCounter.getfGlobal()) {
                    dayMax = factCounter.getfDay();
                    factCounterDaily = factCounter;
                }
            }

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(new Timestamp(observation.getfDate()));
            cal2.setTime(new Timestamp(factCounterDaily.getfDay()));

            // It's the current day
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                factCounterDaily.setCount(factCounterDaily.getCount() + 1);
                factCounterDaily.setfDay(observation.getfDate());
                factCounterManager.updateFactCounter(factCounterDaily);
            } // It's a new day
            else {
                // We delete old observations
                observationsManager.deleteObservationsBySensorId(sensor.getId());
                newFactCounter(observation, sensor, false);
            }
        }
    }

    private void factSummaryManager(List<FactSummary> factsummaries, List<FactCounter> factCounters, Observation observation, Sensor sensor) {
        if (factsummaries.isEmpty()) {
            // First time we create the global summary
            newFactSummary(observation, sensor, true);

            // We create a daily summary too
            newFactSummary(observation, sensor, false);

        } else {
            // We take the global counter
            FactSummary factSummaryGlobal = null;
            for (FactSummary factSummary : factsummaries) {
                if (factSummary.getfGlobal()) {
                    factSummaryGlobal = factSummary;
                    break;
                }
            }
            // We take the global counter
            FactCounter factCounterGlobal = null;
            for (FactCounter factCounter : factCounters) {
                if (factCounter.getfGlobal()) {
                    factCounterGlobal = factCounter;
                    break;
                }
            }
            if (factSummaryGlobal != null && factCounterGlobal != null) {
                if (observation.getfValue() < factSummaryGlobal.getfMin()) {
                    factSummaryGlobal.setfMin(observation.getfValue());
                }
                if (observation.getfValue() > factSummaryGlobal.getfMax()) {
                    factSummaryGlobal.setfMax(observation.getfValue());
                }
                factSummaryGlobal.setfAverage(((factCounterGlobal.getCount() - 1) * factSummaryGlobal.getfAverage() + observation.getfValue()) / factCounterGlobal.getCount());
                factSummaryManager.updateFactSummary(factSummaryGlobal);
            }

            // We take the daily counter
            FactSummary factSummaryDaily = factsummaries.get(0);
            long dayMax = 0;

            for (FactSummary factSummary : factsummaries) {
                if (dayMax < factSummary.getfDay() && !factSummary.getfGlobal()) {
                    dayMax = factSummary.getfDay();
                    factSummaryDaily = factSummary;
                }
            }

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(new Timestamp(observation.getfDate()));
            cal2.setTime(new Timestamp(factSummaryDaily.getfDay()));

            // It's the current day
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                // We take the daily counter
                FactCounter factCounterDaily = factCounters.get(0);
                long dayMaxDaily = 0;

                for (FactCounter factCounter : factCounters) {
                    if (dayMaxDaily < factCounter.getfDay() && !factCounter.getfGlobal()) {
                        dayMaxDaily = factCounter.getfDay();
                        factCounterDaily = factCounter;
                    }
                }
                if (observation.getfValue() < factSummaryDaily.getfMin()) {
                    factSummaryDaily.setfMin(observation.getfValue());
                }
                if (observation.getfValue() > factSummaryDaily.getfMax()) {
                    factSummaryDaily.setfMax(observation.getfValue());
                }
                factSummaryDaily.setfAverage(((factCounterDaily.getCount() - 1) * factSummaryDaily.getfAverage() + observation.getfValue()) / factCounterDaily.getCount());

                factSummaryManager.updateFactSummary(factSummaryDaily);
            } // It's a new day
            else {
                newFactSummary(observation, sensor, false);
            }
        }
    }

    private void newFactCounter(Observation observation, Sensor sensor, boolean global) {
        FactCounter factCounter = new FactCounter();
        factCounter.setCount(1);
        factCounter.setfGlobal(global);
        factCounter.setfDay(observation.getfDate());
        factCounter.setSensor(sensor);
        factCounter.setfOpen(sensor.isfOpen());
        if (sensor.getOrganization() != null) {
            factCounter.setOrganization(sensor.getOrganization());
        }
        factCounterManager.createFactCounter(factCounter);
    }

    private void newFactSummary(Observation observation, Sensor sensor, boolean global) {
        FactSummary factsummary = new FactSummary();
        factsummary.setfDay(observation.getfDate());
        factsummary.setfAverage(observation.getfValue());
        factsummary.setfMin(observation.getfValue());
        factsummary.setfMax(observation.getfValue());
        factsummary.setSensor(sensor);
        factsummary.setfOpen(sensor.isfOpen());
        factsummary.setfGlobal(global);
        if (sensor.getOrganization() != null) {
            factsummary.setOrganization(sensor.getOrganization());
        }
        factSummaryManager.createFactSummary(factsummary);
    }

    private Observation toObservation(ObservationDTO dto, Observation observation, Sensor sensor) {
        observation.setfDate(dto.getDate());
        observation.setfValue(dto.getValue());
        observation.setfDate(dto.getDate());
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
