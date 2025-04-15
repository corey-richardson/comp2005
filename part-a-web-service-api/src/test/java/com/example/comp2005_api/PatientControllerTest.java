package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientControllerTest
{
    @Autowired
    private PatientController patientController;

    @MockitoBean
    private PatientService patientService;

    private List<Patient> mockPatients;

    @BeforeEach
    void setUp() {
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

        mockPatients = Arrays.asList(p1, p2);
    }

    @Test
    void getNeverAdmitted_shouldReturnExpectedPatients()
    {
        // Arrange
        when(patientService.getPatientsNeverAdmitted()).thenReturn(mockPatients); // nullptr
        // Act
        List<Patient> patients = patientController.getNeverAdmitted();
        // Assert
        assertEquals(2, patients.size());
        assertEquals("Viv", patients.get(0).getFirstName());
        assertEquals("Heather", patients.get(1).getFirstName());
    }
}
