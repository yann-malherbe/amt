/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.services;

import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yann
 */
@Stateless
public class SensorsManager implements SensorsManagerLocal {

    @PersistenceContext
    public EntityManager em;

    @Override
    public Sensor findSensorById(long id) {
        return em.find(Sensor.class, id);
    }

    @Override
    public List<Sensor> findAllPublicSensors() {
        return em.createNamedQuery("findAllPublicSensors").getResultList();
    }

    @Override
    public Sensor createSensor(Sensor sensor) {
        em.persist(sensor);
        em.flush();
        return sensor;
    }

    @Override
    public void updateSensor(Sensor sensor) {
        em.merge(sensor);
        em.flush();
    }

    @Override
    public void deleteSensor(Sensor sensor) {
        em.remove(sensor);
        em.flush();
    }
}
