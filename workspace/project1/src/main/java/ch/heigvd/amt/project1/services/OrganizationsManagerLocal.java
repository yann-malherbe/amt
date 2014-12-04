/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Organization;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Yann
 */
@Local
public interface OrganizationsManagerLocal {

    public Organization findOrganizationById(long id);

    public List<Organization> findAllOrganizations();

    public Organization createOrganization(Organization organization);

    public void updateOrganization(Organization organization);

    public void deleteOrganization(Organization organization);
}
