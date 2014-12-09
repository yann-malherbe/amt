
package ch.heigvd.amt.project1.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Yann
 */
@Entity
public class ObservationSummary extends Fact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float min;
    private float max;
    private float average;
    private Timestamp day;
    
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public float getMin() {
        return this.min;
    }
    
    public void setMin(float min) {
        this.min = min;
    }
    
    public float getMax() {
        return this.max;
    }
    
    public void setMax(float max) {
        this.max = max;
    }
    
    public float getAverage() {
        return this.average;
    }
    
    public void setAverage(float average) {
        this.average = average;
    }
    
    private Timestamp getDay() {
        return this.day;
    }
    
    private void setDay(Timestamp day) {
        this.day = day;
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
        if (!(object instanceof ObservationSummary)) {
            return false;
        }
        ObservationSummary other = (ObservationSummary) object;
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
