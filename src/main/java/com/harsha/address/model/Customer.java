package com.harsha.address.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String crole="USER";

    public Customer() {
    }

    public Customer(String name, String email, String password, String role) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.crole = role;
    }
}
