package com.example.comp2005_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdmissionTest
{
    @Test
    void CreateAdmissionTest() {
        Admission admission = new Admission();
        admission.setId(1);
        admission.setPatientID(1);
        admission.setAdmissionDate("2024-12-06");
        admission.setDischargeDate("2024-12-08");

        assertNotNull(admission);
        assertEquals(1, admission.getId());
        assertEquals(1, admission.getPatientID());
        assertEquals("2024-12-06", admission.getAdmissionDate());
        assertEquals("2024-12-08", admission.getDischargeDate());
    }
}