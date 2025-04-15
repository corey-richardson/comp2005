package com.example.comp2005_api;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatientService
{
    private final ApiHelper apiHelper;

    public PatientService(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    // F1: A list of patients who have never been admitted
    public List<Patient> getPatientsNeverAdmitted() {
        Patient[] patients = apiHelper.getAllPatients();
        Admission[] admissions = apiHelper.getAllAdmissions();

        Set<Integer> admittedIds = Arrays.stream(admissions)
                .map(Admission::getPatientID)
                .collect(Collectors.toSet());

        return Arrays.stream(patients)
                .filter(p -> !admittedIds.contains(p.getId()))
                .collect(Collectors.toList());
    }

    // F2: A list of patients who were re-admitted withing 7 days of being discharged.
    public List<Patient> getPatientsReadmittedSevenDays() {
        // TODO
        return Collections.emptyList();
    }

    // F4: A list of patients who have had more than one member of staff.
    public List<Patient> getPatientsMultipleStaff() {
        // TODO
        return Collections.emptyList();
    }
}
