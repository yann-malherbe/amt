/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto.sensors;

import ch.heigvd.amt.project1.dto.organizations.OrganizationSimpleDTO;

/**
 *
 * @author Yann
 */
public class SensorDTO {
    private Long id;
    private String name;
    private String description;
    private String type;
    private boolean open;
    private OrganizationSimpleDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
  
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public boolean isOpen() {
        return this.open;
    }  
    
    public void setOpen(boolean open) {
        this.open = open;
    }

    public OrganizationSimpleDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationSimpleDTO organization) {
        this.organization = organization;
    }

}
