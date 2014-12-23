/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file ApplicationConfig.java
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
package ch.heigvd.amt.project1.api;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("/api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ch.heigvd.amt.project1.api.FactCounterResource.class);
        resources.add(ch.heigvd.amt.project1.api.FactSummaryResource.class);
        resources.add(ch.heigvd.amt.project1.api.ObservationResource.class);
        resources.add(ch.heigvd.amt.project1.api.OrganizationResource.class);
        resources.add(ch.heigvd.amt.project1.api.SensorResource.class);
        resources.add(ch.heigvd.amt.project1.api.UserResource.class);
    }
}
