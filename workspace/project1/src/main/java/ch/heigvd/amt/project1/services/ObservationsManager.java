/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file ObservationsManager.java
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
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Observation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ObservationsManager implements ObservationsManagerLocal {
    
    @PersistenceContext
    public EntityManager em;
    
    @Override
    public Observation findOrganizationById(long id) {
        return em.find(Observation.class, id);
    }
    
    @Override
    public List<Observation> findAllObservations() {
        return em.createNamedQuery("findAllObservations").getResultList();
    }
    
    @Override
    public List<Observation> findObservationsBySensorId(long id) {
        return em.createNamedQuery("findObservationsBySensorId").setParameter("sensorId", id).getResultList();
    }
    
    @Override
    public void deleteObservationsBySensorId(long id) {
        List<Observation> observations = findObservationsBySensorId(id);
        for (Observation observation : observations) {
            deleteObservation(observation);
        }
    }
    
    @Override
    public Observation createObservation(Observation observation) {
        em.persist(observation);
        em.flush();
        return observation;
    }
    
    @Override
    public void updateObservation(Observation observation) {
        em.merge(observation);
        em.flush();
    }
    
    @Override
    public void deleteObservation(Observation observation) {
        em.remove(observation);
        em.flush();
    }
}
