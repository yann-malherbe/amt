/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 *
 * @project project1
 * @file SensorsManagerLocal.java
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

import ch.heigvd.amt.project1.model.Sensor;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SensorsManagerLocal {

    public Sensor findSensorById(long id);

    public List<Sensor> findAllPublicSensors();

    public Sensor createSensor(Sensor sensor);

    public void updateSensor(Sensor sensor);

    public void deleteSensor(Sensor sensor);
}
