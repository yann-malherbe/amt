/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file OrganizationsManagerLocal.java
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

import ch.heigvd.amt.project1.model.Organization;
import java.util.List;
import javax.ejb.Local;

@Local
public interface OrganizationsManagerLocal {
    
    public Organization findOrganizationById(long id);
    
    public List<Organization> findAllOrganizations();
    
    public Organization createOrganization(Organization organization);
    
    public void updateOrganization(Organization organization);
    
    public void deleteOrganization(Organization organization);
}
