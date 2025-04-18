package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdmissionTest
{
    private static Admission admissionParameterless;
    private static Admission admissionConstructed;

    @BeforeAll
    static void setUp() {
        admissionParameterless = new Admission();
        admissionConstructed = new Admission(101, 202, "2025-04-01", "2025-04-10");
    }

    @Test
    void testParameterlessNotNull() {
        assertNotNull(admissionParameterless);
    }

    @Test
    void testConstructedNotNull() {
        assertNotNull(admissionConstructed);
    }

    @Test
    void testSetAndGetId() {
        admissionParameterless.setId(101);
        assertEquals(101, admissionParameterless.getId());
    }

    @Test
    void testSetAndGetPatientID() {
        admissionParameterless.setPatientID(202);
        assertEquals(202, admissionParameterless.getPatientID());
    }

    @Test
    void testSetAndGetAdmissionDate() {
        admissionParameterless.setAdmissionDate("2025-04-01");
        assertEquals("2025-04-01", admissionParameterless.getAdmissionDate());
    }

    @Test
    void testSetAndGetDischargeDate() {
        admissionParameterless.setDischargeDate("2025-04-10");
        assertEquals("2025-04-10", admissionParameterless.getDischargeDate());
    }

    @Test
    void constructedAdmissionTest() {
        assertEquals(101, admissionConstructed.getId());
        assertEquals(202, admissionConstructed.getPatientID());
        assertEquals("2025-04-01", admissionConstructed.getAdmissionDate());
        assertEquals("2025-04-10", admissionConstructed.getDischargeDate());
    }
}
