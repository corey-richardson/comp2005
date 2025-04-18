package com.example.comp2005_api;

public class Employee extends Person
{
    // No additional fields needed from base class.

    public Employee() {
        // No argument constructor
    }

    public Employee(int id, String firstName, String lastName, String nhsNumber) {
        super.setId(id);
        super.setFirstName(firstName);
        super.setLastName(lastName);
    }
}
