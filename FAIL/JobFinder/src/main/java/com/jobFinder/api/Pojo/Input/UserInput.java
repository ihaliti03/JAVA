package com.jobFinder.api.Pojo.Input;

import com.jobFinder.api.Pojo.Entity.Address;

public class UserInput {

    private String username;

    private AddressInput address;

    public UserInput() {}


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AddressInput getAddress() {
        return address;
    }

    public void setAddress(AddressInput address) {
        this.address = address;
    }
}
