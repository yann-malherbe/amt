/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto.facts.counters;

import ch.heigvd.amt.project1.dto.organizations.OrganizationDTO;

/**
 *
 * @author Yann
 */
public class FactCounterDTO {

    private Long id;
    private Boolean open;
    private OrganizationDTO organization;
    private int count;

    public Long getId() {
        return id;
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
        return this.organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
