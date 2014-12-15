/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Observation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yann
 */
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
