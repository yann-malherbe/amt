/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto.organizations;

import ch.heigvd.amt.project1.dto.users.UserDTO;

/**
 *
 * @author Yann
 */
public class OrganizationSimpleDTO {

    private Long id;
    private String name;
    private UserDTO contact;

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

    public UserDTO getContact() {
        return contact;
    }

    public void setContact(UserDTO contact) {
        this.contact = contact;
    }

}