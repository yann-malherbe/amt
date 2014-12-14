/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto.observations;

import java.sql.Timestamp;

/**
 *
 * @author Yann
 */
public class ObservationDTO {
    private Long id;
    private Timestamp date;
    private float data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
    
}
