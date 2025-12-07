package com.example.studentservice.POJO.DTO;

public class School {

    private String name;
    private String location;
    private String principalName;

    public School() {
    }

    public School(int id, String name, String location, String principalName) {
        this.name = name;
        this.location = location;
        this.principalName = principalName;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrincipalName() {
        return principalName;
    }
    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}
