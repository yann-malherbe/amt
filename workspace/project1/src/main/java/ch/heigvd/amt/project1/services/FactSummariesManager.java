/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file FactSummariesManager.java
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

import ch.heigvd.amt.project1.model.FactSummary;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
