// Carry out unit testing on the Service Layer

package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTest
{
    private ApiHelper apiHelper;
    private PatientService service;

    private Patient[] mockPatients;
    private Admission[] mockAdmissions;

    @BeforeEach
    void setUp () {
        apiHelper = mock(ApiHelper.class);
        service = new PatientService(apiHelper);

        // Arrange: mock response
        Patient p1 = new Patient();
        p1.setId(1);
        p1.setFirstName("Viv");
        p1.setLastName("Robinson");
        p1.setNhsNumber("1113335555");

        Patient p2 = new Patient();
        p2.setId(2);
        p2.setFirstName("Heather");
        p2.setLastName("Carter");
        p2.setNhsNumber("2224446666");

        mockPatients = new Patient[] { p1, p2 };

        Admission a1 = new Admission();
        a1.setPatientID(1);
        Admission a2 = new Admission();
        a2.setPatientID(3);

        mockAdmissions = new Admission[] { a1, a2 };
    }

    @Test
    void getPatientsNeverAdmitted_returnsExpected() {
        // Arrange
        when(apiHelper.getAllPatients()).thenReturn(mockPatients);
        when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);
        // Act
        List<Patient> patients = service.getPatientsNeverAdmitted();
        // Assert
        assertEquals(1, patients.size());
        assertEquals("Heather", patients.get(0).getFirstName());
    }

    @Test
    void getPatientsNeverAdmitted_returnsAllWhenNoAdmissions() {
        when(apiHelper.getAllPatients()).thenReturn(mockPatients);
        when(apiHelper.getAllAdmissions()).thenReturn(new Admission[0]);
        List<Patient> patients = service.getPatientsNeverAdmitted();
        assertEquals(2, patients.size());
    }

    @Test
    void getPatientsNeverAdmitted_returnsEmptyWhenNoAdmissions() {
        when(apiHelper.getAllPatients()).thenReturn(new Patient[0]);
        when(apiHelper.getAllAdmissions()).thenReturn(new Admission[0]);
        List<Patient> patients = service.getPatientsNeverAdmitted();
        assertTrue(patients.isEmpty());
    }

    @Test
    void getPatientsNeverAdmitted_handlesErrorsGracefully() {
        when(apiHelper.getAllPatients()).thenThrow(new RuntimeException("Mock Error"));
        when(apiHelper.getAllAdmissions()).thenReturn(mockAdmissions);
        // Expected behaviour on error is to return an empty list
        List<Patient> patients = service.getPatientsNeverAdmitted();
        assertTrue(patients.isEmpty());
    }
}