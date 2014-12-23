/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 *
 * @project project1
 * @file SensorsManager.java
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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
