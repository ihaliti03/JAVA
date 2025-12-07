package com.jobFinder.api.Pojo.Input;

public class WorkerInput {

    private String username;

    private AddressInput address;

    public WorkerInput() {
    }

    public String getUsername() {
        return username;
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
