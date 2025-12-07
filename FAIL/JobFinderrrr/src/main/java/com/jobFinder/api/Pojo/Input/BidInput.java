package com.jobFinder.api.Pojo.Input;

import com.jobFinder.api.Pojo.Entity.Work;
import com.jobFinder.api.Pojo.Entity.Worker;

public class BidInput {

    private double price;

    public BidInput(){}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
