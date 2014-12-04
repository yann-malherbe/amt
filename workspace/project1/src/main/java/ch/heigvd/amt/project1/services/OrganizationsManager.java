/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Organization;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yann
 */
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
    }

    @Override
    public void deleteOrganization(Organization organization) {
        em.remove(organization);
    }
}
