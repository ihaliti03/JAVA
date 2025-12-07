package com.example.ecom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity class representing a product in the e-commerce system.
 * Uses JPA for persistence and Lombok for boilerplate reduction.
 */
@Entity
@Data  // Generates getters, setters, toString, equals, hashCode
@AllArgsConstructor  // Generates constructor with all fields
@NoArgsConstructor   // Generates no-args constructor required by JPA
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String brand;
    private double price;
    private String category;
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;

    //image related fields
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageDate;
}
