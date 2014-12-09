/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto;

import java.sql.Timestamp;

/**
 *
 * @author Yann
 */
public abstract class ObservationSummaryDTO {
    
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

}
