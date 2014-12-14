/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.FactCounter;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Yann
 */
@Local
public interface FactCountersManagerLocal {

    public FactCounter findFactCounterById(long id);

    public List<FactCounter> findFactCountersByOrganizationId(long id);

    public List<FactCounter> findFactCountersBySensorId(long id);

    public FactCounter createFactCounter(FactCounter fact);

    public void updateFactCounter(FactCounter fact);

    public void deleteFactCounter(FactCounter fact);
}
