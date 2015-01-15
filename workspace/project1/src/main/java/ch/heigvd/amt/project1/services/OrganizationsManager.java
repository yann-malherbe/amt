/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 *
 * @project project1
 * @file OrganizationsManager.java
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
import ch.heigvd.amt.project1.model.FactSummary;
import ch.heigvd.amt.project1.model.Organization;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrganizationsManager implements OrganizationsManagerLocal {

    @PersistenceContext
    public EntityManager em;

    @Override
    public Organization findOrganizationById(long id) {
        return em.find(Organization.class, id);
    }

    @Override
    public List<Organization> findAllOrganizations() {
        return em.createNamedQuery("findAllOrganizations").getResultList();
    }

    @Override
    public Organization createOrganization(Organization organization) {
        em.persist(organization);
        em.flush();
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        em.merge(organization);
        em.flush();
    }

    @Override
    public void deleteOrganization(Organization organization) {
        em.remove(organization);
        em.flush();
    }

    @Override
    public List<User> findOrganizationUsers(Organization organization) {
        return em.createNamedQuery("findOrganizationUsers").setParameter("organisationId", organization).getResultList();
    }

    @Override
    public List<Sensor> findOrganizationSensors(Organization organization) {
        return em.createNamedQuery("findOrganizationSensors").setParameter("organisationId", organization).getResultList();
    }

    @Override
    public List<FactCounter> findOrganizationFactCounters(Organization organization){
        return em.createNamedQuery("findOrganizationFactCounters").setParameter("organisationId", organization).getResultList();
    }

    @Override
    public List<FactSummary> findOrganizationFactSummaries(Organization organization){
        return em.createNamedQuery("findOrganizationFactSummaries").setParameter("organisationId", organization).getResultList();
    }
}
