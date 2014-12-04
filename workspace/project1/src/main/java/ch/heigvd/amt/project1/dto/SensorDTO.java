/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto;

import ch.heigvd.amt.project1.model.Observation;
import java.util.LinkedList;

/**
 *
 * @author Yann
 */
public class SensorDTO {
    private Long id;
    private boolean open;
    private LinkedList<Observation> observations;

    public SensorDTO(Long id, boolean open, LinkedList<Observation> observations) {
        this.id = id;
        this.open = open;
        this.observations = observations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public LinkedList<Observation> getObservations() {
        return observations;
    }

    public void setObservations(LinkedList<Observation> observations) {
        this.observations = observations;
    }
    
    
}
