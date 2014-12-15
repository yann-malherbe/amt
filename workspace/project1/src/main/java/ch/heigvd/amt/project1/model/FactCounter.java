/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and fOpen the template in the editor.
 */
package ch.heigvd.amt.project1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author Yann
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findFactCountersByOrganizationId", query = "SELECT f FROM FactCounter f WHERE (f.organization.id = :organizationId)"),
    @NamedQuery(name = "findFactCountersBySensorId", query = "SELECT f FROM FactCounter f WHERE (f.sensor.id = :sensorId)")
})
public class FactCounter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean fOpen;
    private Boolean fGlobal;
    @ManyToOne
    private Organization organization;
    @OneToOne
    private Sensor sensor;
    private int count;
    private long fDay;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getfOpen() {
        return fOpen;
    }

    public void setfOpen(Boolean open) {
        this.fOpen = open;
    }

    public Boolean getfGlobal() {
        return fGlobal;
    }

    public void setfGlobal(Boolean fGlobal) {
        this.fGlobal = fGlobal;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getfDay() {
        return fDay;
    }

    public void setfDay(long fDay) {
        this.fDay = fDay;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FactCounter)) {
            return false;
        }
        FactCounter other = (FactCounter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.amt.project1.model.ObservationCounter[ id=" + id + " ]";
    }
}
