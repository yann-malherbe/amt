/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and fOpen the template in the editor.
 */
package ch.heigvd.amt.project1.model;

import java.io.Serializable;
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
    @NamedQuery(name = "findFactCountersByOrganizationId", query = "SELECT f FROM FactCounter f WHERE f.organization = :id"),
    @NamedQuery(name = "findFactCountersBySensorId", query = "SELECT f FROM FactCounter f WHERE f.organization = :id")
})
public class FactCounter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean fOpen;
    @ManyToOne
    private Organization organization;
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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