package com.example.comp2005_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientTest
{
    @Test
    void CreatePatientTest() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("Robin");
        patient.setLastName("Hood");
        patient.setNhsNumber("01010001");

        assertNotNull(patient);
        assertEquals(1, patient.getId());
        assertEquals("Robin", patient.getFirstName());
        assertEquals("Hood", patient.getLastName());
        assertEquals("01010001", patient.getNhsNumber());
    }
}