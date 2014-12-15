/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto.organizations;

import ch.heigvd.amt.project1.dto.sensors.SensorDTO;
import ch.heigvd.amt.project1.dto.users.UserDTO;
import ch.heigvd.amt.project1.dto.users.UserWithoutPassDTO;
import java.util.List;

/**
 *
 * @author Yann
 */
public class OrganizationDTO {

    private Long id;
    private String name;
    private UserWithoutPassDTO contact;
    private List<SensorDTO> sensors;
    private List<UserWithoutPassDTO> users;

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

    public UserWithoutPassDTO getContact() {
        return contact;
    }

    public void setContact(UserWithoutPassDTO contact) {
        this.contact = contact;
    }

    public List<SensorDTO> getSensors() {
        return this.sensors;
    }

    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }

    public List<UserWithoutPassDTO> getUsers() {
        return this.users;
    }

    public void setUsers(List<UserWithoutPassDTO> users) {
        this.users = users;
    }
}
