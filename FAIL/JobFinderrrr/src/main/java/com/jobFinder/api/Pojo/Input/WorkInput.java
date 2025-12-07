package com.jobFinder.api.Pojo.Input;

import com.jobFinder.api.Pojo.Entity.Address;

public class WorkInput {

    private String title;
    private String description;
    private boolean available;

    private Address address;

    public WorkInput() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
