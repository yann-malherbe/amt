/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto;

import java.util.List;

/**
 *
 * @author Yann
 */
public class OrganizationDTO {
    private Long id;
    private String name;
    private List sensors;
    private List users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
        public List getSensors() {
        return this.sensors;
    }
    
    public void setSensors(List sensors) {
        this.sensors = sensors;
    }
    
    public List getUsers() {
        return this.users;
    }
    
    public void setUsers(List users) {
        this.users = users;
    }
}
