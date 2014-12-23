/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file ObservationsManagerLocal.java
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

import ch.heigvd.amt.project1.model.Observation;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ObservationsManagerLocal {
    
    public Observation findOrganizationById(long id);
    
    public List<Observation> findAllObservations();
    
    public List<Observation> findObservationsBySensorId(long id);
    
    public void deleteObservationsBySensorId(long id);
    
    public Observation createObservation(Observation observation);
    
    public void updateObservation(Observation observation);
    
    public void deleteObservation(Observation observation);
}
