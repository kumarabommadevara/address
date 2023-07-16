package com.harsha.address.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JwtResponse {

    private String jwt;
    private String username;
    private String email;
private String roles;

    public JwtResponse(String jwt, String username, String email, String roles) {
        this.jwt = jwt;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
