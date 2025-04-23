package com.example.comp2005_api;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

            Set<Integer> admittedPatientIds = new HashSet<>();
            for (Admission admission : admissions) {
                admittedPatientIds.add(admission.getPatientID());
            }

            List<Patient> neverAdmittedPatients = new ArrayList<>();
            for (Patient patient : patients) {
                if (!admittedPatientIds.contains(patient.getId())) {
                    neverAdmittedPatients.add(patient);
                }
            }

            return neverAdmittedPatients;

        } catch (Exception e) {
            // Handle gracefully
            return Collections.emptyList();
        }
    }


    // F2: A list of patients who were re-admitted withing 7 days of being discharged.
    public List<Patient> getPatientsReadmittedSevenDays() {
        try {
            Patient[] patients = apiHelper.getAllPatients();
            Admission[] admissions = apiHelper.getAllAdmissions();

            Map<Integer, List<Admission>> patientAdmissionLink = new HashMap<>();
            for (Admission admission : admissions) {
                int patientId = admission.getPatientID();
                patientAdmissionLink
                        .computeIfAbsent(patientId, k -> new ArrayList<>())
                        .add(admission);
            }

            Set<Integer> readmittedIds = new HashSet<>();
            for (Patient patient : patients) {
                int patientId = patient.getId();
                List<Admission> patientAdmissions = patientAdmissionLink.get(patientId);

                if (patientAdmissions == null || patientAdmissions.size() < 2) { // guard
                    continue;
                }

                patientAdmissions.sort(Comparator.comparing(a -> LocalDateTime.parse(a.getAdmissionDate())));

                for (int i = 1; i < patientAdmissions.size(); i++) {
                    Admission previous = patientAdmissions.get(i - 1);
                    Admission current = patientAdmissions.get(i);

                    if (previous.getDischargeDate() != null) {
                        LocalDateTime discharge = LocalDateTime.parse(previous.getDischargeDate());
                        LocalDateTime nextAdmit = LocalDateTime.parse(current.getAdmissionDate());

                        if (!nextAdmit.isBefore(discharge) && !nextAdmit.isAfter(discharge.plusDays(7))) {
                            readmittedIds.add(patient.getId());
                            break;
                        }
                    } else if (current.getAdmissionDate() != null) { // Patient is currently admitted
                        LocalDateTime nextAdmit = LocalDateTime.parse(current.getAdmissionDate());
                        readmittedIds.add(patient.getId());
                        break;
                    }
                }
            }

            List<Patient> readmitted_patients = new ArrayList<>();
            for (Patient patient : patients) {
                if (readmittedIds.contains(patient.getId())) {
                    readmitted_patients.add(patient);
                }
            }

            return readmitted_patients;

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


    // F4: A list of patients who have had more than one member of staff.
    public List<Patient> getPatientsMultipleStaff() {
        try {
            Patient[] patients = apiHelper.getAllPatients();
            Admission[] admissions = apiHelper.getAllAdmissions();
            Allocation[] allocations = apiHelper.getAllAllocations();

            // Link admissionId to patientId
            // Link patientId to ALL associated employees *UNIQUE*
            // WHERE COUNT(employees) > 1

            // https://stackoverflow.com/questions/2884068/what-is-the-difference-between-a-map-and-a-dictionary
            // https://stackoverflow.com/questions/1348199/what-is-the-difference-between-the-hashmap-and-map-objects-in-java
            Map<Integer, Integer> patientAdmissions = new HashMap<>();
            for (Admission admission : admissions) {
                patientAdmissions.put(admission.getId(), admission.getPatientID());
            }

            Map<Integer, Set<Integer>> patientEmployeeLink = new HashMap<>();
            // for each allocation
            // if patientAdmissions contains allocation.admissionId
            // add employeeId to Set
            for (Allocation allocation : allocations) {
                if (patientAdmissions.containsKey(allocation.getAdmissionId())) {
                    int patientId = patientAdmissions.get(allocation.getAdmissionId());
                    // https://www.baeldung.com/java-map-computeifabsent
                    patientEmployeeLink
                            .computeIfAbsent(patientId, k -> new HashSet<>()) // the mapping function is only called if
                                                                              // the mapping isn't present
                            .add(allocation.getEmployeeId());
                }
            }

            List<Patient> patientsWithMultipleStaff = new ArrayList<>();
            for (Patient patient : patients) {
                Set<Integer> employees = patientEmployeeLink.get(patient.getId());
                if (employees != null && employees.size() > 1) {
                    patientsWithMultipleStaff.add(patient);
                }
            }

            return patientsWithMultipleStaff;

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
