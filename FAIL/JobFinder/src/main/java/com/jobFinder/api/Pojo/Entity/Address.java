package com.jobFinder.api.Pojo.Entity;

import javax.persistence.*;

@Entity
@Table (name = "address")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    public Address() {}

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
