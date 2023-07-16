package com.harsha.address.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SigninRequest {


    private String username;
    private String password;

    public SigninRequest() {
    }

    public SigninRequest(String name, String password) {
        this.username = name;
        this.password = password;
    }
}
