/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto;

import ch.heigvd.amt.project1.model.Organization;


/**
 *
 * @author Yann
 */
public abstract class FactDTO {
    
    private Long id;
    private Boolean open;
    private Organization organization;

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
    
    public Organization getOrganization() {
        return this.organization;
    }
    
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
