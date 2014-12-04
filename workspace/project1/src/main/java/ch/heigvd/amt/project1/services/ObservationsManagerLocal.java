/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Observation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Yann
 */
@Local
public interface ObservationsManagerLocal {
    
    public Observation findOrganizationById(long id);

    public List<Observation> findAllObservations();

    public Observation createObservation(Observation observation);

    public void updateObservation(Observation observation);

    public void deleteObservation(Observation observation);
}
