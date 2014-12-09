/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto;

import ch.heigvd.amt.project1.model.Observation;
import ch.heigvd.amt.project1.model.Organization;
import java.util.LinkedList;

/**
 *
 * @author Yann
 */
public class SensorDTO {
    private Long id;
    private boolean open;
    private Organization organization;

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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
