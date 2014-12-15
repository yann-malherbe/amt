/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.FactSummary;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yann
 */
@Stateless
public class FactSummariesManager implements FactSummariesManagerLocal {

    @PersistenceContext
    public EntityManager em;

    @Override
    public FactSummary findFactSummaryById(long id) {
        return em.find(FactSummary.class, id);
    }

    @Override
    public List<FactSummary> findAllFactSummaries() {
        return em.createNamedQuery("findAllFactSummaries").getResultList();
    }

    @Override
    public List<FactSummary> findFactSummariesByOrganizationId(long id) {
        return em.createNamedQuery("findFactSummariesByOrganizationId").setParameter("id", id).getResultList();
    }

    @Override
    public List<FactSummary> findFactSummariesBySensorId(long id) {
        return em.createNamedQuery("findFactSummariesBySensorId").setParameter("id", id).getResultList();
    }

    @Override
    public FactSummary createFactSummary(FactSummary factSummary) {
        em.persist(factSummary);
        em.flush();
        return factSummary;
    }

    @Override
    public void updateFactSummary(FactSummary factSummary) {
        em.merge(factSummary);
        em.flush();
    }

    @Override
    public void deleteFactSummary(FactSummary factSummary) {
        em.remove(factSummary);
        em.flush();
    }
}
