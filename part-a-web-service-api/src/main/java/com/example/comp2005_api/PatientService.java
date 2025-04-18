package com.example.comp2005_api;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
        try {
            Patient[] patients = apiHelper.getAllPatients();
            Admission[] admissions = apiHelper.getAllAdmissions();

            Set<Integer> admittedIds = Arrays.stream(admissions)
                    .map(Admission::getPatientID)
                    .collect(Collectors.toSet());

            return Arrays.stream(patients)
                    .filter(p -> !admittedIds.contains(p.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Handle gracefully
            return Collections.emptyList();
        }
    }

    // F2: A list of patients who were re-admitted withing 7 days of being discharged.
    public List<Patient> getPatientsReadmittedSevenDays() {
        Patient[] patients = apiHelper.getAllPatients();
        Admission[] admissions = apiHelper.getAllAdmissions();

        Set<Integer> readmittedIds = new HashSet<>();

        for (Patient p : patients) {
            // Get all admissions for a patient
            List<Admission> admissionsByPatient = new ArrayList<>();

            for (Admission a : admissions) {
                if (a.getPatientID() == p.getId()) {
                    admissionsByPatient.add(a);
                }
            }
            // Sort by admission date
            // https://www.w3schools.com/java/ref_arraylist_sort.asp
            admissionsByPatient.sort(Comparator.comparing(a -> LocalDateTime.parse(a.getAdmissionDate())));

            // Check for readmission within 7 days
            for (int i = 1; i < admissionsByPatient.size(); i++) {
                Admission previous = admissionsByPatient.get(i-1);
                Admission current = admissionsByPatient.get(i);

                if (previous.getDischargeDate() != null) {
                    LocalDateTime discharge = LocalDateTime.parse(previous.getDischargeDate());
                    LocalDateTime nextAdmit = LocalDateTime.parse(current.getAdmissionDate());

                    if (!nextAdmit.isBefore(discharge) && !nextAdmit.isAfter(discharge.plusDays(7))) {
                        readmittedIds.add(p.getId());
                        break;
                    }
                } else if (current.getAdmissionDate() != null) { // Patient is currently admitted
                    LocalDateTime nextAdmit = LocalDateTime.parse(current.getAdmissionDate());
                    readmittedIds.add(p.getId());
                    break;
                }
            }
        }

        List<Patient> readmitted_patients = new ArrayList<>();
        for (Patient p : patients) {
            if (readmittedIds.contains(p.getId())) {
                readmitted_patients.add(p);
            }
        }

        return readmitted_patients;
    }

    // F4: A list of patients who have had more than one member of staff.
    public List<Patient> getPatientsMultipleStaff() {
        // TODO
        return Collections.emptyList();
    }
}
