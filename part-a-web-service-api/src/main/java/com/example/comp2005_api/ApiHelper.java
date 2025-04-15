package com.example.comp2005_api;

import org.springframework.web.client.RestTemplate;

public class ApiHelper
{
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/";

    public Admission[] getAllAdmissions() {
        String url = baseUrl + "Admissions";
        return restTemplate.getForObject(url, Admission[].class);
    }

    public Admission getAdmissionById(int id) {
        String url = baseUrl + "Admissions/" + id;
        return restTemplate.getForObject(url, Admission.class);
    }


    public Allocation[] getAllAllocations() {
        String url = baseUrl + "Allocations";
        return restTemplate.getForObject(url, Allocation[].class);
    }

    public Allocation getAllocationById(int id) {
        String url = baseUrl + "Allocations/" + id;
        return restTemplate.getForObject(url, Allocation.class);
    }


    public Employee[] getAllEmployees() {
        String url = baseUrl + "Employees/";
        return restTemplate.getForObject(url, Employee[].class);
    }

    public Employee getEmployeeById(int id) {
        String url = baseUrl + "Employees/" + id;
        return restTemplate.getForObject(url, Employee.class);
    }


    public Patient[] getAllPatients() {
        String url = baseUrl + "Patients";
        return restTemplate.getForObject(url, Patient[].class);
    }

    public Patient getPatientById(int id) {
        String url = baseUrl + "Patients/" + id;
        return restTemplate.getForObject(url, Patient.class);
    }
}

// Add Try-Catch logic
// Handle 404s