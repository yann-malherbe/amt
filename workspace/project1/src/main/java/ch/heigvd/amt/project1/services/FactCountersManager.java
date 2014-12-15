/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.FactCounter;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yann
 */
@Stateless
public class FactCountersManager implements FactCountersManagerLocal {

    @PersistenceContext
    public EntityManager em;

    @Override
    public FactCounter findFactCounterById(long id) {
        return em.find(FactCounter.class, id);
    }

    @Override
    public List<FactCounter> findFactCountersByOrganizationId(long id) {
        return em.createNamedQuery("findFactCountersByOrganizationId").setParameter("id", id).getResultList();
    }

    @Override
    public List<FactCounter> findFactCountersBySensorId(long id) {
        return em.createNamedQuery("findFactCountersBySensorId").setParameter("id", id).getResultList();
    }

    @Override
    public FactCounter createFactCounter(FactCounter factCounter) {
        em.persist(factCounter);
        em.flush();
        return factCounter;
    }

    @Override
    public void updateFactCounter(FactCounter factCounter) {
        em.merge(factCounter);
        em.flush();
    }

    @Override
    public void deleteFactCounter(FactCounter factCounter) {
        em.remove(factCounter);
        em.flush();
    }
}
