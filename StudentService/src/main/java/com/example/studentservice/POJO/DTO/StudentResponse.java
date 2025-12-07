package com.example.studentservice.POJO.DTO;

public class StudentResponse {

    private int id;
    private String name;
    private String surname;
    private int age;
    private School school;

    public StudentResponse() {
    }

    public StudentResponse(int id, String name, String surname, int age, School school) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.school = school;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
