package com.example.comp2005_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;



@RestController
@RequestMapping("/api/Patients")
public class PatientController
{
    private final PatientService service;

    public PatientController(PatientService patientService) {
        this.service = patientService;
    }

    // F1 Endpoint
    @GetMapping("/never-admitted")
    public List<Patient> getNeverAdmitted() {
        return service.getPatientsNeverAdmitted();
    }

    // F2 Endpoint
    @GetMapping("/readmitted-within-7-days")
    public List<Patient> getReadmittedSevenDays() {
        return service.getPatientsReadmittedSevenDays();
    }

    // F4 Endpoint
    @GetMapping("/multiple-staff")
    public List<Patient> getMultipleStaff() {
        // TODO
        return service.getPatientsMultipleStaff();
    }
}
