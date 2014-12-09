/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto;

import ch.heigvd.amt.project1.model.Organization;
import java.sql.Timestamp;

/**
 *
 * @author Yann
 */
public class FactDTO {
    private Long id;
    private float value;
    private Timestamp date;
    private Organization organization;

    public FactDTO(Long id, float value, Timestamp date) {
        this.id = id;
        this.value = value;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
