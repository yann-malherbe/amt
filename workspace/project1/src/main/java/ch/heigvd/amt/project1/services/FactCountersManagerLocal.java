/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file FactCountersManagerLocal.java
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
import javax.ejb.Local;

@Local
public interface FactCountersManagerLocal {

    public FactCounter findFactCounterById(long id);

    public List<FactCounter> findAllFactCounters();

    public List<FactCounter> findFactCountersByOrganizationId(long id);

    public List<FactCounter> findFactCounterBySensorId(long id);

    public FactCounter createFactCounter(FactCounter fact);

    public void updateFactCounter(FactCounter fact);

    public void deleteFactCounter(FactCounter fact);
}
