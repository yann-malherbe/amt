/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file FactSummariesManagerLocal.java
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
import javax.ejb.Local;

@Local
public interface FactSummariesManagerLocal {

    public FactSummary findFactSummaryById(long id);
    
    public List<FactSummary> findAllFactSummaries();
    
    public List<FactSummary> findFactSummariesByOrganizationId(long id);
    
    public List<FactSummary> findFactSummariesBySensorId(long id);
    
    public FactSummary createFactSummary(FactSummary fact);
    
    public void updateFactSummary(FactSummary fact);
    
    public void deleteFactSummary(FactSummary fact);
}
