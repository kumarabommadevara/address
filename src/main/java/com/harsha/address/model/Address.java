package com.harsha.address.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "addresses")
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String city;
    private String streetName;
    private Integer zipCode;
    private String state;
    private String country;
    private Integer instructorId;

    public Address() {
    }

    public Address(String city, String streetName, Integer zipCode, String state, String country, Integer instructorId) {
        this.city = city;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
        this.instructorId = instructorId;
    }
}

