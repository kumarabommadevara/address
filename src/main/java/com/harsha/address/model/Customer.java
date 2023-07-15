package com.harsha.address.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="Customers")
public class Customer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;

    public Customer(String name, String email, String password) {

        this.name = name;
        this.email = email;
        this.password = password;
    }
}
