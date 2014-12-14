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

/**
 *
 * @author Yann
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findFactSummariesByOrganizationId", query = "SELECT f FROM FactSummary f WHERE f.organization = :id"),
    @NamedQuery(name = "findFactSummariesBySensorId", query = "SELECT f FROM FactSummary f WHERE f.organization = :id")
})
public class FactSummary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean fOpen;
    @ManyToOne
    private Organization organization;
    private float fMin;
    private float fMax;
    private float fAverage;
    private Timestamp fDay;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOpen() {
        return fOpen;
    }

    public void setOpen(Boolean open) {
        this.fOpen = open;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public float getMin() {
        return this.fMin;
    }

    public void setMin(float min) {
        this.fMin = min;
    }

    public float getMax() {
        return this.fMax;
    }

    public void setMax(float max) {
        this.fMax = max;
    }

    public float getAverage() {
        return this.fAverage;
    }

    public void setAverage(float average) {
        this.fAverage = average;
    }

    public Timestamp getDay() {
        return this.fDay;
    }

    public void setDay(Timestamp day) {
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
