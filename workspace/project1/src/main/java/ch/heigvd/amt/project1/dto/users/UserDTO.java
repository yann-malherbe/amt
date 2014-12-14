/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.project1.dto.users;

import ch.heigvd.amt.project1.dto.organizations.OrganizationDTO;
import ch.heigvd.amt.project1.dto.organizations.OrganizationSimpleDTO;

/**
 *
 * @author Yann
 */
public class UserDTO {

    private Long id;
    private String login;
    private String name;
    private String pass;
    private OrganizationSimpleDTO organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public OrganizationSimpleDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationSimpleDTO organization) {
        this.organization = organization;
    }

}
