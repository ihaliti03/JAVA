package com.example.WebApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Product {
    @Id
    private int prodId;
    private String prodName;
    private double price;

    public Product(int prodId, String prodName, double price) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.price = price;
    }

    public Product() {}

    public int getProdId() {return prodId;}
    public String getProdName() {return prodName;}
    public double getPrice() {return price;}

    public void setProdId(int prodId) {this.prodId = prodId;}
    public void setProdName(String prodName) {this.prodName = prodName;}
    public void setPrice(double price) {this.price = price;}

    @Override
    public String toString() {
        return "Product{" +
                "prodId=" + prodId +
                ", prodName='" + prodName + '\'' +
                ", price=" + price +
                '}';
    }
}
