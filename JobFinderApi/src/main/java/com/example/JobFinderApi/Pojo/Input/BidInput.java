package com.example.JobFinderApi.Pojo.Input;

import java.time.LocalDateTime;

public class BidInput {

    private double price;
    private LocalDateTime createdAt;

    public BidInput() {}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
