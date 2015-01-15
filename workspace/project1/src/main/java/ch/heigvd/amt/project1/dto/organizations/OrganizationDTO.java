/**
 *******************************************************************************
 *
 * HEIG-VD - Haute Ecole d'Ingénierie et de Gestion du Canton de Vaud - School
 * of Business and Engineering Vaud
 *
 *******************************************************************************
 * 
 * @project project1
 * @file OrganizationDTO.java
 *
 * @author Magali Froehlich
 * @author Yann Malherbe
 * @author Cédric Rudareanu
 *
 * @date Dec 20, 2014
 *
 *******************************************************************************
 *
 * @version 1.0
 *
 *******************************************************************************
 */
package ch.heigvd.amt.project1.dto.organizations;

import ch.heigvd.amt.project1.dto.sensors.SensorDTO;
import ch.heigvd.amt.project1.dto.users.UserWithoutPassDTO;
import java.util.List;

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
