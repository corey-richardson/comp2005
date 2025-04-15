package com.example.comp2005_api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiHelper
{
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/";

    public ApiHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // https://stackoverflow.com/questions/450807/how-do-i-make-the-method-return-type-generic
    private <T> T handleRequest(String url, Class<T> responseType) {
        try {
            return restTemplate.getForObject(url, responseType);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw e;
        }
    }

    public Admission[] getAllAdmissions() {
        String url = baseUrl + "Admissions";
        return handleRequest(url, Admission[].class);
    }

    public Admission getAdmissionById(int id) {
        String url = baseUrl + "Admissions/" + id;
        return handleRequest(url, Admission.class);
    }


    public Allocation[] getAllAllocations() {
        String url = baseUrl + "Allocations";
        return handleRequest(url, Allocation[].class);
    }

    public Allocation getAllocationById(int id) {
        String url = baseUrl + "Allocations/" + id;
        return handleRequest(url, Allocation.class);
    }

    public Employee[] getAllEmployees() {
        String url = baseUrl + "Employees";
        return handleRequest(url, Employee[].class);
    }

    public Employee getEmployeeById(int id) {
        String url = baseUrl + "Employees/" + id;
        return handleRequest(url, Employee.class);
    }


    public Patient[] getAllPatients() {
        String url = baseUrl + "Patients";
        return handleRequest(url, Patient[].class);
    }

    public Patient getPatientById(int id) {
        String url = baseUrl + "Patients/" + id;
        return handleRequest(url, Patient.class);
    }
}
