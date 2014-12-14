/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto.facts.summaries;

import ch.heigvd.amt.project1.dto.organizations.OrganizationDTO;
import java.sql.Timestamp;

/**
 *
 * @author Yann
 */
public class FactSummaryDTO {

    private Long id;
    private Boolean open;
    private OrganizationDTO organization;
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

    public Boolean isOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
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

    public Timestamp getDay() {
        return this.day;
    }

    public void setDay(Timestamp day) {
        this.day = day;
    }
}