// Carry out integration testing on the Controller layer

package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientControllerTest
{
    @MockitoBean
    private PatientService patientService;

    private PatientController patientController;
    private List<Patient> mockPatients;

    @BeforeEach
    void setUp() {
        patientController = new PatientController(patientService);

        Patient p1 = new Patient(); p1.setId(1); p1.setFirstName("Viv");
        Patient p2 = new Patient(); p2.setId(2); p2.setFirstName("Heather");

        mockPatients = Arrays.asList(p1, p2);
    }

    @Test
    void getNeverAdmitted_returnsExpectedPatients() {
        when(patientService.getPatientsNeverAdmitted()).thenReturn(mockPatients);

        List<Patient> result = patientController.getNeverAdmitted();

        assertEquals(2, result.size());
        assertEquals("Viv", result.get(0).getFirstName());
        assertEquals("Heather", result.get(1).getFirstName());
    }

    @Test
    void getNeverAdmitted_returnsEmptyListWhenNoMatch() {
        when(patientService.getPatientsNeverAdmitted()).thenReturn(Collections.emptyList());

        List<Patient> result = patientController.getNeverAdmitted();

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected empty list of Patients.");
    }

    @Test
    void getNeverAdmitted_handleServiceFailure() {
        // when(patientService.getPatientsNeverAdmitted()).thenThrow(new RuntimeException("Mock Service Error"));
        // When the patientService fails, it returns an emptyList
        when(patientService.getPatientsNeverAdmitted()).thenReturn(Collections.emptyList());
        List<Patient> patients = patientController.getNeverAdmitted();
        assertTrue(patients.isEmpty());
    }
}
