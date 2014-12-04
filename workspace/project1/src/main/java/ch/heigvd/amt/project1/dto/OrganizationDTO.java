/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto;

import ch.heigvd.amt.project1.model.Fact;
import ch.heigvd.amt.project1.model.Sensor;
import ch.heigvd.amt.project1.model.User;
import java.util.LinkedList;

/**
 *
 * @author Yann
 */
public class OrganizationDTO {
    private Long id;
    private String name;
    private LinkedList<User> users;
    private LinkedList<Sensor> sensors;
    private LinkedList<Fact> facts;

    public OrganizationDTO(Long id, String name, LinkedList<User> users, LinkedList<Sensor> sensors, LinkedList<Fact> facts) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.sensors = sensors;
        this.facts = facts;
    }

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

    public LinkedList<User> getUsers() {
        return users;
    }

    public void setUsers(LinkedList<User> users) {
        this.users = users;
    }

    public LinkedList<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(LinkedList<Sensor> sensors) {
        this.sensors = sensors;
    }

    public LinkedList<Fact> getFacts() {
        return facts;
    }

    public void setFacts(LinkedList<Fact> facts) {
        this.facts = facts;
    }
    
    
}
