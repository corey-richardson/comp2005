package com.example.comp2005_api;

public class Patient extends Person
{
    private  String nhsNumber;

    public Patient() {
        // No argument constructor
    }

    public Patient(int id, String firstName, String lastName, String nhsNumber) {
        super.setId(id);
        super.setFirstName(firstName);
        super.setLastName(lastName);
        this.nhsNumber = nhsNumber;
    }

    public String getNhsNumber() { return  nhsNumber; }
    public void setNhsNumber(String nhsNumber) { this.nhsNumber = nhsNumber; }
}
