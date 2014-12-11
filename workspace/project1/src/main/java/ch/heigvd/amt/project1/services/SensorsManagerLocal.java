/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Sensor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Yann
 */
@Local
public interface SensorsManagerLocal {

    public Sensor findSensorById(long id);

    public List<Sensor> findAllPublicSensors();

    public Sensor createSensor(Sensor sensor);

    public void updateSensor(Sensor sensor);

    public void deleteSensor(Sensor sensor);

}
