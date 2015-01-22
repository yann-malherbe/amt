/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 *
 * @project project1
 * @file FactCountersManager.java
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

import ch.heigvd.amt.project1.model.FactCounter;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Stateless
public class FactCountersManager implements FactCountersManagerLocal {
    
    @PersistenceContext
    public EntityManager em;
    
    @Override
    public FactCounter findFactCounterById(long id) {
        return em.find(FactCounter.class, id, LockModeType.PESSIMISTIC_WRITE);
    }
    
    @Override
    public List<FactCounter> findAllFactCounters() {
        return em.createNamedQuery("findAllFactCounters").setLockMode(LockModeType.PESSIMISTIC_WRITE).getResultList(); // For pesimist : .setLockMode(LockModeType.PESSIMISTIC_WRITE)
    }
    
    @Override
    public List<FactCounter> findFactCountersByOrganizationId(long id) {
        return em.createNamedQuery("findFactCountersByOrganizationId").setParameter("organizationId", id).setLockMode(LockModeType.PESSIMISTIC_WRITE).getResultList();
    }
    
    @Override
    public List<FactCounter> findFactCounterBySensorId(long id) {
        return em.createNamedQuery("findFactCountersBySensorId").setParameter("sensorId", id).setLockMode(LockModeType.PESSIMISTIC_WRITE).getResultList();
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
