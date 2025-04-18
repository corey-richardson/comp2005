package com.example.comp2005_api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PatientTest
{
    private static Patient patientParameterless;
    private static Patient patientConstructed;

    @BeforeAll
    static void setUp() {
        patientParameterless = new Patient();
        patientConstructed = new Patient(1, "Robin", "Hood", "12345678");
    }

    @Test
    void testParameterlessNotNull() {
        assertNotNull(patientParameterless);
    }

    @Test
    void testConstructedNotNull() {
        assertNotNull(patientConstructed);
    }

    @Test
    void testSetAndGetId() {
        patientParameterless.setId(10);
        assertEquals(10, patientParameterless.getId());
    }

    @Test
    void testSetAndGetFirstName() {
        patientParameterless.setFirstName("Robin");
        assertEquals("Robin", patientParameterless.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        patientParameterless.setLastName("Hood");
        assertEquals("Hood", patientParameterless.getLastName());
    }

    @Test
    void testSetAndGetNhsNumber() {
        patientParameterless.setNhsNumber("12345678");
        assertEquals("12345678", patientParameterless.getNhsNumber());
    }

    @Test
    void constructedPatientTest() {
        assertEquals(1, patientConstructed.getId());
        assertEquals("Robin", patientConstructed.getFirstName());
        assertEquals("Hood", patientConstructed.getLastName());
        assertEquals("12345678", patientConstructed.getNhsNumber());
    }
}
