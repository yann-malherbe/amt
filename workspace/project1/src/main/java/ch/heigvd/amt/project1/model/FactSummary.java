package ch.heigvd.amt.project1.model;

import java.io.Serializable;
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
    @NamedQuery(name = "findAllFactSummaries", query = "SELECT f FROM FactSummary f"),
    @NamedQuery(name = "findFactSummariesByOrganizationId", query = "SELECT f FROM FactSummary f WHERE f.organization.id = :id"),
    @NamedQuery(name = "findFactSummariesBySensorId", query = "SELECT f FROM FactSummary f WHERE f.sensor.id = :id")
})
public class FactSummary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean fGlobal;
    private Boolean fOpen;
    @ManyToOne
    private Organization organization;
    @OneToOne
    private Sensor sensor;
    private float fMin;
    private float fMax;
    private float fAverage;
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

    public float getfMin() {
        return this.fMin;
    }

    public void setfMin(float min) {
        this.fMin = min;
    }

    public float getfMax() {
        return this.fMax;
    }

    public void setfMax(float max) {
        this.fMax = max;
    }

    public float getfAverage() {
        return this.fAverage;
    }

    public void setfAverage(float average) {
        this.fAverage = average;
    }

    public long getfDay() {
        return this.fDay;
    }

    public void setfDay(long day) {
        this.fDay = day;
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
        if (!(object instanceof FactSummary)) {
            return false;
        }
        FactSummary other = (FactSummary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.amt.project1.model.ObservationSummary[ id=" + id + " ]";
    }

}
