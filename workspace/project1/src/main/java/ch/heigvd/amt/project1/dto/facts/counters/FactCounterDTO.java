/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file FactCounterDTO.java
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
package ch.heigvd.amt.project1.dto.facts.counters;

import ch.heigvd.amt.project1.dto.organizations.OrganizationDTO;
import ch.heigvd.amt.project1.dto.sensors.SensorDTO;

public class FactCounterDTO {

    private Long id;
    private Boolean open;
    private Boolean global;
    private OrganizationDTO organization;
    private SensorDTO sensor;
    private int count;
    private long date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public OrganizationDTO getOrganization() {
        return this.organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long day) {
        this.date = day;
    }
}
